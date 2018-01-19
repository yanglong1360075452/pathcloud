//申请重补取弹窗
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('DiagnosisCollectModalController', DiagnosisCollectModalController);

  /** @ngInject */
  function DiagnosisCollectModalController($uibModalInstance,IhcService,toastr,data){
    var collectModal = this;
  
    collectModal.typeList = [{name: "罕见", code: 1}, {name: "随访", code: 2}, {name: "科研", code: 3}, {name: "教学", code: 4}];
    collectModal.statusList = [{name: "仅自己可见", code: 1}, {name: "公开", code: 2}];
    
    collectModal.applycollectModal = function(){
  
      // "targetId":1,  //病理id或者特殊申请id
      //   "category":2,  //类别 1代表常规 2代表特殊申请
      //   "abels":[1,2,3,4],  //标签
      //   "permission":1, // 1 仅自己可见 2公开
      //   "note":"这是一个备注"
      var resolveData = {
        targetId: data.targetId,
        category: data.category,
        labels: [],
        permission: collectModal.permission,
        note: collectModal.note
      };
      
      angular.forEach(collectModal.typeList, function (item, index) {
        if(item.checked){
          resolveData.labels.push(item.code)
        }
      });
      
      if(resolveData.labels.length === 0){
        toastr.error("请选择一个收藏类别")
        return false
      }
      
      IhcService.post("/diagnose/collect", resolveData).then(
        function(result){
          toastr.success("收藏成功！");
          $uibModalInstance.close();
        },
        function(error){
          toastr.error("收藏失败！");
        }
      );
    };

    //确定
    collectModal.ok = function(){
      collectModal.applycollectModal();
    };

    //取消
    collectModal.cancel = function(){
      $uibModalInstance.dismiss();
    };

  }
})();
