/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('DyeingTemplateController', DyeingTemplateController);

  /** @ngInject */
  function DyeingTemplateController($scope,$state,toastr,MaterialService,toolService,IhcService){
    var DyeingTpl=this;

    function init() {
      getDyeType();
  
      DyeingTpl.handleKeyDown = function (e) {
        var keyCode = window.event?e.keyCode:e.which;
        
        if(keyCode == 13 || keyCode == 229|| keyCode == 186){
          //debugger || keyCode == 32 空格 // 229 186 中英文 分号的keycode // /^[\s　]|[ ]$/.test("1 1")  this.replace(/(^\s*)|(\s*$)/g, ""); //去首尾空格 [；;\$\#]+ //不能包含[ ；# $]这些字符
          // if(DyeingTpl.marker === "") return;
          
          if(DyeingTpl.marker === ""||/[；;\$\#]/.test(DyeingTpl.marker)){
            console.log("输入不合法");
            toastr.error("输入不合法");
            return;
          }
          
          
          e.preventDefault();
          e.stopImmediatePropagation();
  
          //  校验试剂库是否存在该抗体  【抗体在试剂工作站中添加】
          IhcService.get("/reagent/name",{name:DyeingTpl.marker,type:2}).then(function (res) {
            if(res){
              if(DyeingTpl.marks.indexOf(DyeingTpl.marker) < 0){
                DyeingTpl.marks.push(DyeingTpl.marker);
              }
              DyeingTpl.marker = "";
              
            }else {
              toastr.error("试剂库不存在"+DyeingTpl.marker+"抗体");
              return false;
            }
          })
          
        }
      }

    }
    init();
    // todo 删除弹窗确认


    // 3.1 添加特染类型
    function addDyeType(){
      var data = {
        "name": DyeingTpl.activeType.name,
        "param": "specialDye"
      };
      IhcService.post("/systemSetting",data).then(function (res) {
        toastr.success("操作成功")
      },function (err) {
        toastr.error(err.reason)
      }).finally(function () {
        getDyeType()
      })
    }
    // 3.1 编辑特染类型
    function editDyeType(){
      var data = {
        "name": DyeingTpl.activeType.name,
        "param": "specialDye",
        "code":DyeingTpl.activeType.code
      };
      IhcService.put("/systemSetting/specialDye",data).then(function (res) {
        toastr.success("操作成功")
      },function (err) {
        toastr.error(err.reason)
      }).finally(function () {
        getDyeType()
      })
    }
    // 3.1 删除特染类型
    function deleteDyeType(){
      IhcService.delete("/systemSetting/specialDye/"+ DyeingTpl.activeType.code ).then(function (res) {
        toastr.success("操作成功")
      },function (err) {
        toastr.error(err.reason)
      }).finally(function (){
        getDyeType()
      })
    }
    // 3.3 查询特染类型
    function getDyeType(){
      IhcService.get("/systemSetting/specialDye").then(function (res) {
        DyeingTpl.dyeTypeList = res;

      },function (err) {
        toastr.error(err.reason)
      })
    }


    //选择染色类型
    DyeingTpl.choseDyeType = function (item,index) {
      DyeingTpl.activeType = item;
      DyeingTpl.activeType.index = index;
    };
    DyeingTpl.addDyeType=function (index) {

      var newType = {
        "name":"",
        focus:true
      };
      DyeingTpl.dyeTypeList.push(newType);
      DyeingTpl.activeType = newType;
      DyeingTpl.activeType.index = DyeingTpl.dyeTypeList.length;
      // todo 增加一个选择一个
    };
    DyeingTpl.saveDyeType=function (item) {
      item.focus=false;
      if(!item.name){
        DyeingTpl.dyeTypeList.pop();
        // todo 删除一个选择上一个
        DyeingTpl.activeType = DyeingTpl.dyeTypeList[DyeingTpl.dyeTypeList.length-1];

      }else{

        if(checkRepeatAndDelete(DyeingTpl.dyeTypeList,"name")){
          return //有重复
        }

        if(item.code){
          // 编辑
          editDyeType()
        }else {
          // 新增
          addDyeType()
        }
      }
    };
    DyeingTpl.deleteDyeType = function (index) {
      console.info("删除内容",DyeingTpl.dyeTypeList[index])
      if(!DyeingTpl.dyeTypeList[index].code){
        DyeingTpl.dyeTypeList.splice(index,1);//当它是新增的 还没保存到系统的时候 直接删除
        return;
      }
      var modal={
        modalTitle:"警告",
        modalContent:"您确定要删除这条内容吗？",
        size:"sm"
      };

      toolService.getModalResult(modal).then(function(){
        deleteDyeType()
      })
    };


    //获取科室
    MaterialService.getDepartments().then(function (departments) {
      DyeingTpl.departmentList=departments;
      DyeingTpl.choseMaterial(departments[0].code);
    });

    //选择科室
    DyeingTpl.choseMaterial=function (code) {
      
      DyeingTpl.marks = [];
      DyeingTpl.chosedMaterial=code;
      MaterialService.getTemplate(code,3).then(function (templates) {
        templates.forEach(function (item) {
          item.parent=code;
        });
        DyeingTpl.templateList=templates;
        DyeingTpl.choseTemplate(0); //在选择科室后自动选择第一个类别模板
      })
    };

    /*
    * 【写在最前】  显示的字段跟保存的字段并不是同一个 【所有页面显示保存都通过 DyeingTpl.marks 数组来中转】 在选择科室的时候要清空
    *
    * 获取模板内容是字符串 content: "[1, 2, 3]" 需要转成数组显示
    * 但是在编辑保存的时候 后端接收的是marks:[]
    *
    * */
    //选择模板 【选择类别的时候判断是否有marks 有就说明是本地缓存的数据 可以直接显示数字 默认后端返回的marks是 null 显示的字段是放在content 字段里】
    DyeingTpl.choseTemplate=function (index) {
      // debugger
      if( !(index>= 0) || !DyeingTpl.templateList[index]) return;
      DyeingTpl.chosedTemIndex=index;
      DyeingTpl.marks = DyeingTpl.templateList[index].marks ? DyeingTpl.templateList[index].marks:
        DyeingTpl.templateList[index].content ? JSON.parse(DyeingTpl.templateList[index].content) : [];
    };

    //增加模板test
    DyeingTpl.addTemplate=function () {
      // todo 修改模板内容 content 保存成一个数组字符串
      DyeingTpl.templateList.push(
        {name:"",marks:[], parent:DyeingTpl.chosedMaterial,level:1,newItem:true,position:3});
      DyeingTpl.templateList[DyeingTpl.templateList.length-1].focus=true;
      DyeingTpl.choseTemplate(DyeingTpl.templateList.length-1);
      // console.log("输出当前滚动条位置",window.x,window.y);
      window.scrollTo(window.x,window.y+25);
    };

    //保存模板
    DyeingTpl.saveTemplate=function (item) {
      if(item.name){
        delete item.focus;
        if(item.newItem){
          delete item.newItem;

          MaterialService.createTemplate(item).then(function (template) {
            // console.log(" createTemplate success")
            item.id=template.id;
            toastr.success("添加模板成功");

          },function (err) {
            toastr.error(err);
            item.newItem=true;
            item.focus=true;
          });
        }else{
          MaterialService.renameTemplate(item.id,item).then(function () {
            // console.log(" updateTemplate success")
            toastr.success("更新模板成功");

          },function (err) {
            toastr.error(err);
            item.focus=true;

          });
        }
      }else{
        if(item.newItem){
          DyeingTpl.templateList.pop();
        }else{
          toastr.error('模板名称不能为空');
          item.focus=true;
        }
      }

    };

    DyeingTpl.saveTemplateContent=function (item) {
      // console.log("DyeingTpl.chosedTemIndex",DyeingTpl.chosedTemIndex)
      IhcService.put("/template/edit/"+item.id,{ marks: item.marks })
      .then(function () {
        toastr.success("修改成功");
        // console.log("修改成功")
      })
    };

    //删除模板
    DyeingTpl.deleteTemplate=function () {
      if(DyeingTpl.templateList[DyeingTpl.chosedTemIndex]){
        toolService.getModalResult({modalTitle:"删除确认",modalContent:"是否确定删除该模板？"})
        .then(function () {
          MaterialService.deleteTemplate(DyeingTpl.templateList[DyeingTpl.chosedTemIndex].id)
          .then(function () {
            toastr.success("删除模板成功");

            DyeingTpl.templateList.splice(DyeingTpl.chosedTemIndex,1)
          })
        })
      }
    };

    DyeingTpl.changeText=function (){
      if(!DyeingTpl.templateList[DyeingTpl.chosedTemIndex]) return;
      
      if(DyeingTpl.focusText&&DyeingTpl.chosedTemIndex>=0){
        DyeingTpl.templateList[DyeingTpl.chosedTemIndex].content = DyeingTpl.marks; // 后端保存的是传marks 获取的时候显示的是 content 字段
        DyeingTpl.templateList[DyeingTpl.chosedTemIndex].marks = DyeingTpl.marks; // 后端保存的是传marks 获取的时候显示的是 content 字段
        DyeingTpl.saveTemplateContent(DyeingTpl.templateList[DyeingTpl.chosedTemIndex])
      }
      DyeingTpl.focusText=!DyeingTpl.focusText;
    };


    function checkRepeatAndDelete(arr,key) {
      var theDoubleObj = toolService.isExistInArrObj(arr,key);

      if(theDoubleObj){
        toastr.error(theDoubleObj.value,"重复数据");
        // console.info("重复染色类别",theDoubleObj)
        return true //有重复

      }else {
        return false //没重复
      }
    }
  }
})();

