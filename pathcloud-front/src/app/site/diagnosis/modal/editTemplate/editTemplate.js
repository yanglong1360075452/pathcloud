//申请深切弹窗
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('EditDiagnosisTemplate', EditDiagnosisTemplate);

  /** @ngInject */
  function EditDiagnosisTemplate($uibModalInstance,$state,toastr,resolveData,DiagnosisService,toolService){
    var EditDiagnosisTpl = this;

    console.clear()
    console.info(resolveData);


    EditDiagnosisTpl.activeProject={};//用来保存当前选择的项 并将contentProject内容绑定到该对象上

    EditDiagnosisTpl.templateContent = angular.copy(resolveData.templateContentVO)||
      [{
        "projectName":"",//,#项目名称
        "projectContent":[],// #项目内容
        "other":"", //#其他
        "projectNameCheck":1, //#项目名勾选  0代表项目没勾选 1代表勾选
        "projectContentCheck":1 //#1代表复选 0代表单选
      }];

    var template={
      "name": resolveData.name,//#模板名字
      "parent":resolveData.parent,//#父ID
      "templateContentVO":EditDiagnosisTpl.templateContent ,
      "level":resolveData.level
    };

    EditDiagnosisTpl.addProjectName=function (index) {

      EditDiagnosisTpl.templateContent.push({
        "projectName":"",//,#项目名称
        "projectContentVO":[],// #项目内容
        "other":"", //#其他
        "projectNameCheck":false, //#是否 必填 true 必填
        "projectContentCheck":true //#是否 单选 true 单选
      });

      // todo 增加一个选择一个
      EditDiagnosisTpl.templateContent[EditDiagnosisTpl.templateContent.length-1].focus=true;
      EditDiagnosisTpl.choseTemplate( EditDiagnosisTpl.templateContent[EditDiagnosisTpl.templateContent.length-1],EditDiagnosisTpl.templateContent.length-1);

    };
    EditDiagnosisTpl.saveName=function (item) {
      item.focus=false;
      if(!item.projectName){
        EditDiagnosisTpl.templateContent.pop();

        EditDiagnosisTpl.choseTemplate( EditDiagnosisTpl.templateContent[EditDiagnosisTpl.templateContent.length-1],EditDiagnosisTpl.templateContent.length-1);
      }else{

        checkRepeatAndDelete(EditDiagnosisTpl.templateContent,"projectName")

      }
    };
    EditDiagnosisTpl.deleteProjectName=function (index) {
      EditDiagnosisTpl.templateContent.splice(index,1)
    };


    EditDiagnosisTpl.choseTemplate = function (item,$index) {//选择某一项的时候 数据保存？？
      if(!item.projectContentVO){ item.projectContentVO=[]; }

      EditDiagnosisTpl.projectContent=item;//选中的某个项目名的内容
      EditDiagnosisTpl.activeProject.index=$index;
      EditDiagnosisTpl.activeProject.projectContent=item;

    };
    if(EditDiagnosisTpl.templateContent.length){
      EditDiagnosisTpl.choseTemplate(EditDiagnosisTpl.templateContent[0],0); //选中第一条内容
    };

    EditDiagnosisTpl.saveContent=function (item) {
      item.check=false;
      if(!item.name){//当没输入内容的时候失焦时把这个删掉
        EditDiagnosisTpl.projectContent.projectContentVO.pop();
        console.error("请不要输入空")

      }else{//防止输入一样的名字

        checkRepeatAndDelete(EditDiagnosisTpl.projectContent.projectContentVO,"name")

      }
    };

    EditDiagnosisTpl.addProjectContent=function () {

      if (!EditDiagnosisTpl.projectContent.projectContentVO){return;}
      EditDiagnosisTpl.projectContent.projectContentVO.push(
        {"name":"","check": true}
      );

    };
    EditDiagnosisTpl.deleteProjectContent=function (index) {
      EditDiagnosisTpl.projectContent.projectContentVO.splice(index,1)
    };

     /* DiagnosisTpl.updateTemplate=function () {
        var templateContentVO=
          [{
            "projectName":"xxx",//,#项目名称
            "projectContent":["xxx","xxx"],// #项目内容
            "other":"xxx", //#其他
            "projectNameCheck":1, //#项目名勾选  0代表项目没勾选 1代表勾选
            "projectContentCheck":1 //#1代表复选 0代表单选
          }];
        DiagnosisService.updateTemplate(templateId,templateContent).then(function (res) {
          console.info(res);
        });
      };*/
    //确定
    EditDiagnosisTpl.ok = function(){

      // console.info(EditDiagnosisTpl.templateContent)
      for (var i=0;i<EditDiagnosisTpl.templateContent.length;i++){
        delete EditDiagnosisTpl.templateContent[i].focus;
        delete EditDiagnosisTpl.templateContent[i].projectContent;
      };

      DiagnosisService.updateDiagnoseTemplate(resolveData.id,template).then(function (res) {
        // console.info(res);
        toastr.success("保存成功");
        resolveData=EditDiagnosisTpl.templateContent;
        $uibModalInstance.close()

      },function (err) {
        console.info(err);
      });

    };

    //取消
    EditDiagnosisTpl.cancel = function(){
      $uibModalInstance.dismiss();
    };

    function checkRepeatAndDelete(arr,key) {
      var theDoubleObj = toolService.isExistInArrObj(arr,key)

      if(theDoubleObj){
        toastr.error(theDoubleObj.value,"重复数据");
        arr.splice(theDoubleObj.index,1);
        return true; //表示有重复
      }
    }
  }
})();
