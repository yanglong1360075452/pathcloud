/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('DiagnosisTemplateController', DiagnosisTemplateController);

  /** @ngInject */
  function DiagnosisTemplateController($scope,$state,toastr,$timeout,DiagnosisService,MaterialService,$uibModal,toolService){
    var DiagnosisTpl=this;
    var activeTemplateId;

    DiagnosisTpl.templateContentVO=[];

    function init() {

      DiagnosisService.getDiagnoseTemplate(1).then(function (res) {
        console.info(res);
      });
      //2, 科室模板，最近常用模板
      MaterialService.getDepartments().then(function (data) {
        DiagnosisTpl.departmentList=data;
      });//1.3 获取送检科室列表
      // MaterialService.getTemplateUsed().then(function (data) {
      //   DiagnosisTpl.recentTemplate=data;
      // });//2.5 常用模板

      DiagnosisTpl.choseDdepartment(DiagnosisTpl.activeDdepartment||1);//选科室
    }


    DiagnosisTpl.choseDdepartment=function (parent) {
      DiagnosisTpl.activeDdepartment=parent;
      DiagnosisTpl.templateContentVO={};//选择科室的时候清空
      DiagnosisService.getDiagnoseTemplate(parent).then(function (data) {
        // alert(1)
        DiagnosisTpl.templateList=data;
        if(data.length){
          if(DiagnosisTpl.activeTemplate==data.length) DiagnosisTpl.activeTemplate=0;
          DiagnosisTpl.choseTemplate(data[DiagnosisTpl.activeTemplate||0],DiagnosisTpl.activeTemplate||0);
        }
      });
    }    //获取科室模板

    DiagnosisTpl.choseTemplate=function (item,$index) {
      DiagnosisTpl.activeTemplate=$index;
      activeTemplateId=item.id;
      // console.clear()
      console.warn(item)
      DiagnosisTpl.templateContentVO=item.templateContentVO;
      DiagnosisTpl.template=item;//编辑时 传到弹窗中的数据

    }    //选模板


    DiagnosisTpl.addTemplate=function () {
      // configMaterial.templateList.push(
      //   {name:"",content:"", parent:configMaterial.chosedMaterial,level:1,newItem:true});
      // configMaterial.templateList[configMaterial.templateList.length-1].focus=true;
      // configMaterial.choseTemplate(configMaterial.templateList.length-1);
      // console.log("输出当前滚动条位置",window.x,window.y);
      // window.scrollTo(window.x,window.y+25);

      var template = {
        "name": "",//#模板名字
        "parent":DiagnosisTpl.activeDdepartment,//#父ID
        "templateContentVO": [
         /* {
          "projectName":"xxx",//,#项目名称
          "projectContentVO":[{"name":"xxx","check": 1},{"name":"xxx","check": 1}], //#项目内容check1代表复选,0单选
          "other":"xxx",// #其他
          "projectNameCheck":1 //#项目名勾选  0代表项目没勾选 1代表勾选
         }*/
        ],
        "newItem":true,//判断是否为新添加的模板 需要保存时删掉
        "level":1
      }
      DiagnosisTpl.templateList.push(template);
      DiagnosisTpl.templateList[DiagnosisTpl.templateList.length-1].focus=true;
      DiagnosisTpl.choseTemplate( DiagnosisTpl.templateList[DiagnosisTpl.templateList.length-1],DiagnosisTpl.templateList.length-1);

    };

    DiagnosisTpl.saveTemplate=function (item) {
      if(item.name){
        delete item.focus;
        if(item.newItem){ //新数据就添加模板
          delete item.newItem;

          DiagnosisService.createDiagnoseTemplate(item).then(function (template) {
            //console.log(" createTemplate success")
            // item.id=template.id; ？？
            toastr.success("添加模板成功");
            init()
            $timeout(function () {
              DiagnosisTpl.choseDdepartment(DiagnosisTpl.activeDdepartment);//选科室
              DiagnosisTpl.choseTemplate(DiagnosisTpl.templateList[DiagnosisTpl.DiagnosisTpl.activeTemplate],activeTemplateId);//选模板 todo
            })


          },function (err) {
            toastr.error(err);
            DiagnosisTpl.templateList.pop();
            // item.newItem=true;
            // item.focus=true;
          });
        }else{ //旧数据更新模板

          DiagnosisService.renameDiagnoseTemplate(item.id,item).then(function () {
            //console.log(" updateTemplate success")
            toastr.success("更新模板成功");

          },function (err) {
            toastr.error(err);
            // item.focus=true;

          });
        }
      }else{
        if(item.newItem){
          DiagnosisTpl.templateList.pop();
        }else{
          toastr.error('模板名称不能为空');
          item.focus=true;
        }
      }

    };

    DiagnosisTpl.edit=function () {
      var templateInstance = $uibModal.open({
        templateUrl: 'app/site/diagnosis/modal/editTemplate/editTemplate.html',
        size:'sample-error',
        // backdrop: false,
        controller: 'EditDiagnosisTemplate',
        controllerAs: 'EditDiagnosisTpl',
        resolve: {
          resolveData: function () {
            return DiagnosisTpl.template;
          }
        }
        // resolve: {
        //   resolveData:{
        //     pathologyNumber:"P201610001",
        //     subId:"A",
        //     statusName:"待脱水",
        //   }
        // }
      });
      templateInstance.result.then(function () {
        init();
        // DiagnosisTpl.choseDdepartment(DiagnosisTpl.activeDdepartment);//选科室
        // DiagnosisTpl.choseTemplate(DiagnosisTpl.activeTemplate);//选模板
      },function () {

      })
    };

    DiagnosisTpl.deleteTemplate=function () {
      if(activeTemplateId){
        toolService.getModalResult({modalTitle:"删除确认",modalContent:"是否确定删除该模板？"})
          .then(function () {
            DiagnosisService.deleteDiagnoseTemplate(activeTemplateId).then(function (res) {
              toastr.success("删除模板成功");
              init();
            });
          })
      }

    };




    init();
  }
})();

/*function renameTemplate(templateId,obj) {
  DiagnosisService.renameDiagnoseTemplate(templateId,obj).then(function (res) {
    console.info(res);
    toastr.success("添加模板成功");
  });
};*/
//保存模板
/*DiagnosisTpl.saveTemplateName=function (item) {
 var rename= {
 "name":item.name,//#模板名字
 "parent":item.parent,//#父类ID
 };
 renameTemplate(item.id,rename)

 }  //失去焦点后保存*/
