//查看历史诊断弹窗
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('viewHistoryController', viewHistoryController);

  /** @ngInject */
  function viewHistoryController($uibModalInstance,$state,toastr,pathId,DiagnosisService,$filter){
    var viewHistory = this;
    viewHistory.activeBtn = 1;
    viewHistory.historyData = "";//存放历史记录数据
    viewHistory.diagnosisData = "";//存放诊断记录数据

    //tab切换
    viewHistory.changeTab = function(index){
      if(index===1){
        viewHistory.activeBtn = 1;
        viewHistory.lookDiagnosis();
      }else{
        viewHistory.activeBtn = 2;
        viewHistory.lookHistory();
      }
    };

    //查看诊断记录
    viewHistory.lookDiagnosis = function(){
      var str = '';
      DiagnosisService.getDiagnosisInfo(pathId).then(
        function(result){
          if(result){
            for(var i=0;i<result.length;i++){
              console.log("1111111",result[i]);
              str += '<div style="margin-bottom:10px;">' +
                "<div class='row'>" +
                "<div class='col-sm-4'>诊断医生 : " + result[i].operator.firstName + "</div>" +
                "<div class='col-sm-4'>诊断时间 : " + $filter('date')(result[i].operationTime,'yyyy-MM-dd') + "</div>" +
                "<div class='col-sm-4'>诊断类别 : " + result[i].operationDesc + '</div>' +
                "</div>" +
                "<div class='row'>" +
                "<div class='col-sm-4'>报告内容 : " + "</div>" +
                "<div class='col-sm-8'>" + result[i].diagnoseResult + "</div>" +
                "</div>" +
                "</div>";
            }
          }else{
            str = "暂无";
          }
          viewHistory.diagnosisData = str;
        },
        function(error){
          console.log("1111111",error);
          str = "暂无";
          viewHistory.diagnosisData = str;
        }
      );
    };
    viewHistory.lookDiagnosis();

    //查看历史记录
    viewHistory.lookHistory = function(){

    };

    //确定
    viewHistory.ok = function(){
      $uibModalInstance.close();
    };

    //取消
    viewHistory.cancel = function(){
      $uibModalInstance.dismiss();
    };

  }
})();
