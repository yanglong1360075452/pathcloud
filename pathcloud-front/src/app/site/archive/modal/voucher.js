(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('VoucherModalController',VoucherModalController);

  /** @ngInject */
  function VoucherModalController($rootScope,$uibModalInstance,toastr,printerService,$timeout,data){

    var voucher = this;

    voucher.borrower = data.borrower; //借阅人信息
    voucher.borrower.time = Date.now();

    // 玻片按病理号分组
    var slidesGroup = _.groupBy(data.slides,'serialNumber');

    // console.info("分组数据",groupJson);

    function decorate(groupJson) {
      var slides = [];
      for (var key in groupJson){
        slides.push({
          serialNumber:key,
          patientName:groupJson[key][0].patientName,
          total:groupJson[key].length,
        })
      }
      return slides
    }

    voucher.slides = decorate(slidesGroup);

    //确定按钮
    voucher.confirm = function (){
      printerService.printDom(document.getElementById("voucher").innerHTML,"打印玻片借阅凭证")
      // LODOP.ADD_PRINT_HTM("5", "5", "780", "98%", document.getElementById("vouncher").innerHTML);
      // LODOP.PREVIEW()
      $uibModalInstance.close()
    };


    //取消按钮
    voucher.cancel = function(){
      $uibModalInstance.dismiss();
    }
  }//end CommonModalController
})();


