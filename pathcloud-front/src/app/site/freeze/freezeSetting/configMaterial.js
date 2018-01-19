/**
 * Created by Administrator on 2016/12/5.
 */
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('FreezeSettingController', FreezeSettingController);

  /** @ngInject */
  function FreezeSettingController(MaterialService,toastr,toolService,$uibModal,  IhcService){
    var freeze=this;

    
    freeze.activeMenu=[true,false,false];
    freeze.showPart=0;

    freeze.choseMenu=function (index) {
      freeze.activeMenu.forEach(function (boolean,i) {
        freeze.activeMenu[i]=false;
      });
      freeze.activeMenu[index]=true;
      freeze.showPart=index;
    };

    /*//获取科室
    MaterialService.getDepartments().then(function (departments) {
      freeze.departmentList=departments;
      freeze.choseMaterial(departments[0].code);
    });

    //选择科室
    freeze.choseMaterial=function (code) {
      freeze.chosedMaterial=code;
      MaterialService.getTemplate(code,1).then(function (templates) {
        templates.forEach(function (item) {
          item.parent=code;
        })
        freeze.templateList=templates;
      })
    };

    //选择模板
    freeze.choseTemplate=function (index) {
      freeze.chosedTemIndex=index;
      // freeze.content=freeze.templateList[index]
    };

    //增加模板test
    freeze.addTemplate=function () {
      freeze.templateList.push(
        {name:"",content:"", parent:freeze.chosedMaterial,level:1,newItem:true,position:1});
      freeze.templateList[freeze.templateList.length-1].focus=true;
      freeze.choseTemplate(freeze.templateList.length-1);
      console.log("输出当前滚动条位置",window.x,window.y);
      window.scrollTo(window.x,window.y+25);
    };

    //保存模板
    freeze.saveTemplate=function (item) {
      if(item.name){
        delete item.focus;
        if(item.newItem){
          delete item.newItem;

          MaterialService.createTemplate(item).then(function (template) {
            console.log(" createTemplate success")
            item.id=template.id;
            toastr.success("添加模板成功");

          },function (err) {
            toastr.error(err);
            item.newItem=true;
            item.focus=true;
          });
        }else{
          MaterialService.renameTemplate(item.id,item).then(function () {
            console.log(" updateTemplate success")
            toastr.success("更新模板成功");

          },function (err) {
            toastr.error(err);
            item.focus=true;

          });
        }
      }else{
        if(item.newItem){
          freeze.templateList.pop();
        }else{
          toastr.error('模板名称不能为空');
          item.focus=true;
        }
      }

    };

    freeze.saveTemplateContent=function (item) {
      console.log("freeze.chosedTemIndex",freeze.chosedTemIndex)
      MaterialService.updateTemplate(item.id,item.content)
        .then(function () {
          toastr.success("修改成功");
          console.log("修改成功")
        })
    };

    //删除模板
    freeze.deleteTemplate=function () {
      if(freeze.templateList[freeze.chosedTemIndex]){
        toolService.getModalResult({modalTitle:"删除确认",modalContent:"是否确定删除该模板？"})
          .then(function () {
            MaterialService.deleteTemplate(freeze.templateList[freeze.chosedTemIndex].id)
              .then(function () {
                toastr.success("删除模板成功");

                freeze.templateList.splice(freeze.chosedTemIndex,1)
              })
          })
      }
    };

    freeze.changeText=function (){
      if(freeze.focusText&&freeze.chosedTemIndex>=0){
        freeze.saveTemplateContent(freeze.templateList[freeze.chosedTemIndex])
      }
      freeze.focusText=!freeze.focusText;
    };

*/


    /*
    *冰冻切片机部分
    */
    var type = 3;
    //1 获取所有的脱水机
    freeze.getGrossing = function(){

      IhcService.get("/instrument",{type:type,length:99}).then(
        function (result){
          if(!result.data || result.data.length <1) {
            return
          }

          freeze.grossingList = result.data.sort(function(a, b) {
            return a.name - b.name
          });
          //console.log("获取所有的脱水机",freeze.grossingList);
        }
      );
    };
    freeze.getGrossing();

    //2 打开添加弹窗
    freeze.openAdd = function () {
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/dye/modal/instrument.html',
        controller: 'DyeInstrumentController',
        controllerAs: 'instrument',
        resolve:{
          data: {
            name:"",
            type:type
          }
        }
      });
      modalInstance.result.then(function (res) {

        IhcService.post("/instrument",res).then(function () {
          freeze.getGrossing()
          toastr.success("创建成功")
        },function (err) {
          toastr.error(err.reason)
        });

      });
    };

    //3.1 打开编辑弹窗
    freeze.openUpdate = function(item){

      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/dye/modal/instrument.html',
        controller: 'DyeInstrumentController',
        controllerAs: 'instrument',
        resolve:{
          data: {
            id:item.id,
            name:item.name,
            sn:item.sn,
            description:item.description,
            status:item.status,
            type:item.status
          }
        }
      });
      modalInstance.result.then(function (res) {
        res.type = type;

        IhcService.put("/instrument",res).then(function () {
          freeze.getGrossing();
          toastr.success("修改成功")
        },function (err) {
          toastr.error(err.reason)
        });
      });
    };

    //4 删除
    freeze.deleteGrossing = function(item){

      toolService.getModalResult({modalTitle:"删除确认",modalContent:"是否确定删除？",size:'sm'})
      .then(function () {
        IhcService.delete("/instrument/"+item.id)
        .then(
          function(result){
            freeze.grossingList = [];//初始化页面数据
            freeze.getGrossing()
            toastr.success("删除成功！");

          },
          function(error){
            //console.log("删除脱水机error：-----");
            toastr.error(error.reason);
          }
        )
      })


    };


  }

})();
