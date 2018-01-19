//诊断报告符合率弹窗
(function(){
  'use strict';
  angular
  .module('pathcloud')
  .controller('CoincidenceModalController', CoincidenceModalController);
  
  /** @ngInject */
  function CoincidenceModalController($uibModalInstance,$state,toastr,DiagnosisService,resolveData){
    var CoincidenceMod = this;
  
    CoincidenceMod.diagnoseResult = resolveData.diagnoseResult; //【诊断结果】
    CoincidenceMod.frozenResult = "";//【冰冻诊断结果】
    
    if(resolveData.frozenResults.length){
      // debugger
      angular.forEach(resolveData.frozenResults,function (item) {
        CoincidenceMod.frozenResult += "<div>" + item + "</div>";
      })
    }
  
    CoincidenceMod.rate = 1;  //【默认的诊断符合度】
    
    
    //确定
    CoincidenceMod.ok = function(){
      $uibModalInstance.close(CoincidenceMod.rate)
    };
    
    //取消
    CoincidenceMod.cancel = function(){
      $uibModalInstance.dismiss();
    };
    
  }
})();
