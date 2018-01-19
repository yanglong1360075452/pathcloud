//申请深切弹窗
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('diagnoseConsultApplyController', diagnoseConsultApplyController);

  /** @ngInject */
  function diagnoseConsultApplyController($uibModalInstance){
    var diagnose = this;

    //确定
    diagnose.ok = function(){
      $uibModalInstance.close(diagnose.note);
    };

    //取消
    diagnose.cancel = function(){
      $uibModalInstance.dismiss();
    };

  }
})();
