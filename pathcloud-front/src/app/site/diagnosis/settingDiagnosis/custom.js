(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('CustomTemplateController', CustomTemplateController);

  /** @ngInject */
  function CustomTemplateController($scope, $state, toastr, $timeout, DiagnosisService, IhcService, $uibModal, toolService) {
    var CustomTpl = this;

    //获得初始模板内容
    function init(){
      //获取一级目录
      DiagnosisService.getCustomTemplate(null,5,0).then(function (data) {
        CustomTpl.OneLevelList=data;
        if(data.length){//给第一项添加蓝色背景
          if(CustomTpl.activeTemplate==data.length) CustomTpl.activeTemplate=0;
          CustomTpl.choseTemplate(data[CustomTpl.activeTemplate||0],CustomTpl.activeTemplate||0);
        }
      });
     // 获取二级目录
      getTwoLevel(CustomTpl.activeTemplate||1);
    }
    init();

    //获取二级目录
    function getTwoLevel(parent){
      DiagnosisService.getCustomTemplate(parent,5,0).then(function (data) {
        CustomTpl.TwoLevelList=data;
        if(data.length){//给第一项添加蓝色背景
          if(CustomTpl.twoTemplate==data.length) CustomTpl.twoTemplate=0;
          CustomTpl.choseTwoTemplate(data[CustomTpl.twoTemplate||0],CustomTpl.twoTemplate||0);
        }
      });
    };

    //一级目录点击
    CustomTpl.choseTemplate=function (item,$index) {
      CustomTpl.activeTemplate=$index;
      CustomTpl.activeTemplateId=item.id;
      getTwoLevel(CustomTpl.activeTemplate);
    };
    //二级目录点击
    CustomTpl.choseTwoTemplate=function (item,$index) {
      CustomTpl.twoTemplate=$index;
      CustomTpl.twoTemplateId=item.id;
    };

    //点击 添加
    CustomTpl.addTemplate=function (list,level) {
      var template = {
        "name": "",//#模板名字
        "parent":level==0?null:CustomTpl.activeTemplate,//#父ID
        "category":0,
        "position":5,
        "content":"" ,
        "newItem":true,//判断是否为新添加的模板 需要保存时删掉
        "level":level
      };
      list.push(template);
      list[list.length-1].focus=true;
      level==0?CustomTpl.choseTemplate(list[list.length-1],list.length-1):CustomTpl.choseTwoTemplate(list[list.length-1],list.length-1);
    };

     //删除模板
    CustomTpl.deleteTemplate=function (id) {
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
    CustomTpl.saveTemplate=function (item,list) {
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
    CustomTpl.changeText = function(){
      if(CustomTpl.focusText){
        saveContent(CustomTpl.TwoLevelList[CustomTpl.twoTemplate]);
      }
      CustomTpl.focusText=!CustomTpl.focusText;
    };
  }
})();
