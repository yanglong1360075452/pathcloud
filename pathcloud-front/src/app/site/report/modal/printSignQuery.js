(function() {
    'use strict';

    angular
        .module('pathcloud')
        .controller('PrintSignModalController', PrintSignModalController);

    /** @ngInject */
    function PrintSignModalController($uibModalInstance, toastr, IhcService, printerService, data) {

        var vm = this;

        vm.data = data;
        vm.today = new Date();
        var idList = _.compact(data.map(function (item) {
          
            return {
              pathId: item.id,
              specialApplyId: item.specialApplyId,
            }
          
        }));
        
            //确定按钮  最后一步
        vm.ok = function() {
            IhcService.post("/report/operate/25", idList).then(function(res) {
                // console.info(res);
                toastr.success("打印成功");
                $uibModalInstance.close();

            })
            printerService.printTable($("#signTable").html(), $("#signTable").height());
        };

        //取消按钮
        vm.cancel = function() {
            $uibModalInstance.dismiss();
        }
    } //end CommonModalController
})();
