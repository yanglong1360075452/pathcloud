//申请深切弹窗
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('heavySectionController', heavySectionController);

  /** @ngInject */
  function heavySectionController($uibModalInstance,$state,toastr,DiagnosisService,data){
    var heavySection = this;
    data = angular.copy(data);//万全之策，但可能没有影响
    heavySection.serialNumber = data.serialNumber;//病理号
    heavySection.patientName = data.patientName;//病人姓名
    heavySection.blockSubId = data.blockSubId;//蜡块号
    heavySection.blockId = data.blockId;//蜡块ID
    heavySection.blockData = [];//蜡块信息数组
    heavySection.note = data.note;//从诊断页面传过来的重补取备注

    //根据病理ID获取蜡块信息
    heavySection.getBlockInfo = function(){
      DiagnosisService.getBlockDataByPathology(data.id).then(
        function(result){
          for(var i=0;i<result.length;i++){
            var obj = {};
            obj.value = result[i].id;
            obj.name = result[i].subId;
            heavySection.blockData.push(obj);
          }
        }
      );
    };
    heavySection.getBlockInfo();

    //根据蜡块ID申请深切
    heavySection.applyHeavySection = function(){
      DiagnosisService.applyHeavySection(heavySection.blockId,heavySection.note).then(
        function(result){
          toastr.success("申请深切成功！");
          $uibModalInstance.close();
        },
        function(error){
          toastr.error("申请深切失败！");
        }
      );
    };

    //确定
    heavySection.ok = function(){
      heavySection.applyHeavySection();
    };

    //取消
    heavySection.cancel = function(){
      $uibModalInstance.dismiss();
    };

  }
})();
