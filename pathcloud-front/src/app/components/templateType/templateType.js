(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('templateType', templateType);

  /** @ngInject */
  function templateType(DiagnosisService,toastr,IhcService,toolService) {
    return {
      restrict: 'E',
      templateUrl: 'app/components/templateType/templateType.html',
      scope: {
        position:'@'
      },
      replace:true,
      link: function ($scope) {
        //获得初始模板内容
        function init(){
          //获取一级目录
          DiagnosisService.getCustomTemplate(null,$scope.position,0).then(function (data) {
            $scope.OneLevelList=data;
            if(data.length){//给第一项添加蓝色背景
              if($scope.activeTemplate==data.length) $scope.activeTemplate=0;
              $scope.choseTemplate(data[$scope.activeTemplate||0],$scope.activeTemplate||0);
              // 获取二级目录
              getTwoLevel($scope.activeTemplateId);
            }
          });

        }
        init();

        //获取二级目录
        function getTwoLevel(parent){
          DiagnosisService.getCustomTemplate(parent,$scope.position,0).then(function (data) {
            $scope.TwoLevelList=data;
            if(data.length){//给第一项添加蓝色背景
              if($scope.twoTemplate==data.length) $scope.twoTemplate=0;
              $scope.choseTwoTemplate(data[$scope.twoTemplate||0],$scope.twoTemplate||0);
            }
          });
        };

        //一级目录点击
        $scope.choseTemplate=function (item,$index) {
          $scope.activeTemplate=$index;
          $scope.activeTemplateId=item.id;
          getTwoLevel($scope.activeTemplateId);
        };
        //二级目录点击
        $scope.choseTwoTemplate=function (item,$index) {
          $scope.twoTemplate=$index;
          $scope.twoTemplateId=item.id;
        };

        //点击 添加
        $scope.addTemplate=function (list,level) {
          var template = {
            "name": "",//#模板名字
            "parent":level==0?null:$scope.activeTemplateId,//#父ID
            "category":0,
            "position":$scope.position,
            "content":"" ,
            "newItem":true,//判断是否为新添加的模板 需要保存时删掉
            "level":level
          };
          list.push(template);
          list[list.length-1].focus=true;
          level==0?$scope.choseTemplate(list[list.length-1],list.length-1):$scope.choseTwoTemplate(list[list.length-1],list.length-1);
        };

        //删除模板
        $scope.deleteTemplate=function (id) {
          if(id){
            toolService.getModalResult({modalTitle:"删除确认",modalContent:"是否确定删除该模板？"})
              .then(function () {
                DiagnosisService.deleteCustomTemplate(id).then(function (res) {
                  toastr.success("删除模板成功");
                  init();
                });
              });
          }
        };
        //保存模板
        $scope.saveTemplate=function (item,list) {
          if(item.name){
            delete item.focus;
            if(item.newItem){ //新数据就添加模板
              delete item.newItem;
              DiagnosisService.customDiagnoseTemplate(item).then(function (template) {
                toastr.success("添加模板成功");
                init();
              },function (err) {
                toastr.error(err);
                list.pop();
              });
            }else{ //重命名
              DiagnosisService.renameCustomTemplate(item.id,item).then(function () {
                toastr.success("更新模板成功");
              },function (err) {
                toastr.error(err);
              });
            }
          }else{
            if(item.newItem){
              list.pop();
            }else{
              toastr.error('模板名称不能为空');
              item.focus=true;
            }
          }
        };

        function saveContent(item){
          var data = {
            content:item.content
          };
          IhcService.put('/template/edit/'+item.id,data)
            .then(function () {
              toastr.success("修改成功");
            },function(err){
              toastr.error(err.reason);
            });
        };
        //保存内容
        $scope.changeText = function(){
          if($scope.focusText){
            saveContent($scope.TwoLevelList[$scope.twoTemplate]);
          }
          $scope.focusText=!$scope.focusText;
        };
      }

    };
  }
})();
