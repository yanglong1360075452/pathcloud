(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('PrintRecordModalController',PrintRecordModalController);

  /** @ngInject */
  function PrintRecordModalController($uibModalInstance,toastr,IhcService,printerService,resolveData){

    var printModal = this;

    
    IhcService.get("/report/printRecordQuery", resolveData).then(function(res) {
      printModal.list = res;
    });


    //确定按钮  最后一步
    printModal.ok = function(){
      $uibModalInstance.close();
    };

    //取消按钮
    printModal.cancel = function(){
      $uibModalInstance.dismiss();
    }
  }//end CommonModalController
})();


