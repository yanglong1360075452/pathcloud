//申请重补取弹窗
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('againMaterialController', againMaterialController);

  /** @ngInject */
  function againMaterialController($uibModalInstance,$state,toastr,DiagnosisService,data){
    var againMaterial = this;
    data = angular.copy(data);//万全之策，但可能没有影响
    againMaterial.blockData = [];//蜡块信息数组
    againMaterial.serialNumber = data.serialNumber;//病理号
    againMaterial.patientName = data.patientName;//病人姓名
    againMaterial.note = data.note;//从诊断页面传过来的深切备注

    //根据病理ID申请重补取
    againMaterial.applyAgainMaterial = function(){
      DiagnosisService.applyAgainMaterial(data.id,againMaterial.note).then(
        function(result){
          toastr.success("申请重补取成功！");
          $uibModalInstance.close();
        },
        function(error){
          toastr.error("申请重补取失败！");
        }
      );
    };

    //确定
    againMaterial.ok = function(){
      againMaterial.applyAgainMaterial();
    };

    //取消
    againMaterial.cancel = function(){
      $uibModalInstance.dismiss();
    };

  }
})();
