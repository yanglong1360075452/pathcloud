/**
 * Created by Administrator on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('gradeModuleController', gradeModuleController);

  /** @ngInject */
  function gradeModuleController(toolService,$uibModal,$log,StatisticService,toastr) {
    var gradeModule=this;
    gradeModule.tableHeaders=[
      {name:"工作站"},{name:"1分（1颗星）"},
      {name:"2 分（2颗星）"},{name:"3 分（3颗星）"},
      {name:"4 分（4颗星）"},{name:"5 分（5颗星）"},
      {name:"合格标准"},{name:"操作"}
    ];
    gradeModule.gradeData=[];//存储获取的评分标准

    //获取已有的评分标准
    gradeModule.getGrade=function(){
      StatisticService.getQualityGrade().then(
        function(result){
          gradeModule.gradeData=result;
        }
      );
    };
    gradeModule.getGrade();

    //创建新标准
    gradeModule.addGrade=function(){
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/userManage/modal/addGrade.html',
        controller: 'addGradeController',
        controllerAs: 'addGrade',
      });
      modalInstance.result.then(
        function(data){
          gradeModule.getGrade();
          console.warn("add success");
        },
        function(){
          toastr.error("操作已取消！");
        }
      );
    };

    //编辑评分标准
    gradeModule.updateGrade=function(item){
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/userManage/modal/updateGrade.html',
        controller: 'updateGradeController',
        controllerAs: 'updateGrade',
        resolve:{
          data:function(){
            return angular.copy(item);
          }
        }
      });
      modalInstance.result.then(
        function(data){
          if(data){
            gradeModule.getGrade();
          }
        },
        function(){
          toastr.error("操作已取消！");
        }
      );
    };

    //删除评分标准
    gradeModule.deleteGrade=function(name){
      var modal={
        modalTitle:"警告",
        modalContent:"您确定要删除这条内容吗？",
        size:"sm"
      }
      toolService.getModalResult(modal).then(
        function(){
          StatisticService.deleteQualityGrade(name).then(
            function(result){
              toastr.success("删除成功!");
              gradeModule.getGrade();
            },
            function(error){
              toastr.error("删除失败！");
            }
          );
        },
        function(){
          toastr.error("操作已取消！");
        });
    };

  }
})();
