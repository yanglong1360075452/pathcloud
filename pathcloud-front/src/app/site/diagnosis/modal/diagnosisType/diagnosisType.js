//申请重补取弹窗
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('diagnosisTypeController',diagnosisTypeController);

  /** @ngInject */
  function diagnosisTypeController($uibModalInstance,DiagnosisService,IhcService,$rootScope){
    var diagnosisType=this;
    diagnosisType.superDiagnosisUserList = [];
    diagnosisType.data={};
    
    //获取具有二级、三级诊断权限的医生
    function getSuperDiagnoseUser(){
      DiagnosisService.getSuperDiagnoseUser().then(
        function(result){
          diagnosisType.superDiagnosisUserList=result;
        }
      );
    }
    
    /*
     * /user/thirdDiagnose 获取三级诊断医生
     * 当用户有二级诊断权限的时候只显示 三级诊断医生
     * */
    if(_.intersection($rootScope.user.permissionCodes, [4096]).length > 0){
      IhcService.get("/user/thirdDiagnose").then(function (result) {
        diagnosisType.superDiagnosisUserList=result;
      })
    }else {
      getSuperDiagnoseUser();
    }
    
    

    //确定
    diagnosisType.ok=function(){
      $uibModalInstance.close(diagnosisType.data);
    };

    //取消
    diagnosisType.cancel=function(){
      $uibModalInstance.dismiss();
    };

  }
})();
