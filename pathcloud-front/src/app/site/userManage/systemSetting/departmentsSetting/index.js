/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('DepartmentsTemplateController', DepartmentsTemplateController);

  /** @ngInject */
  function DepartmentsTemplateController($scope,$state,toastr,IhcService,MaterialService,$uibModal,toolService){
    var DepartmentsTpl=this;

    DepartmentsTpl.activeType={
      // id:"",
      // departmentCategory:"科室类别",
      // departmentName:"科室名称",
      // code:"1",
    };



    function getDepartmentType() {
      IhcService.get("/paramSetting/dc").then(function (res) {
        DepartmentsTpl.departmentTypeList = res;
        console.info('获取科室类别')
        if(DepartmentsTpl.activeType.index && DepartmentsTpl.activeType.index<DepartmentsTpl.departmentTypeList.length){
          DepartmentsTpl.activeType.departmentCategory = DepartmentsTpl.departmentTypeList[DepartmentsTpl.activeType.index].departmentCategory;
          DepartmentsTpl.activeType.id = DepartmentsTpl.departmentTypeList[DepartmentsTpl.activeType.index].id;
          DepartmentsTpl.departments= DepartmentsTpl.departmentTypeList[DepartmentsTpl.activeType.index].departments;

          DepartmentsTpl.choseType() //进入自动获取科室类别
        }else {//刚进页面没有获取第一个
          DepartmentsTpl.activeType.index = 0;
          DepartmentsTpl.choseType(DepartmentsTpl.departmentTypeList[0],0)
        }
      })
    }

    getDepartmentType(); //获取科室类别列表

    DepartmentsTpl.choseType =  function (item,index) {  //选择科室类别

      if(item){
        DepartmentsTpl.activeType.index  = index; //科室类别Id
        DepartmentsTpl.activeType.id = item.id; //科室类别Id
        DepartmentsTpl.activeType.departmentCategory = item.departmentCategory; //科室类别名
        DepartmentsTpl.departments = item.departments; //科室列表
      }else {
        //自动获取科室类别
        DepartmentsTpl.departments = DepartmentsTpl.departmentTypeList[DepartmentsTpl.activeType.index].departments; //科室列表
      }
      DepartmentsTpl.choseDdepartment();//默认选中科室
      console.info("选择科室类别",DepartmentsTpl.activeType,DepartmentsTpl.activeDepartment)
    };

    DepartmentsTpl.addType = function(data) {
      DepartmentsTpl.departmentTypeList.push({
        departmentCategory:'', //科室类别
        name:null, //科室名称
        newItem:true,//判断是否为新添加的模板 保存时删掉改属性
        focus:true, //
      });

      DepartmentsTpl.activeType.index = DepartmentsTpl.departmentTypeList.length-1;
      console.info("add start",DepartmentsTpl.activeType.index);
      DepartmentsTpl.activeDepartment.index=0;

      //增加一个后自动选择最后一个

    };

    DepartmentsTpl.saveType=function (item) {
      console.info(item)
      if(item.departmentCategory){
        delete item.focus;

        if(item.newItem){ //新数据就添加模板
          delete item.newItem;

          console.info("判断科室类别重复") // 区别是新增要去掉新增的那条 还是修改

          for (var i=0;i<DepartmentsTpl.departmentTypeList.length-1;i++){
            if(DepartmentsTpl.departmentTypeList[i].departmentCategory===item.departmentCategory){
              toastr.error("科室类别已存在");
              getDepartmentType();
              return false
            }
          }

          // return false; ,name:"科室名称"
          IhcService.post("/paramSetting/departmentSetting",{departmentCategory:item.departmentCategory,name:null}).then(function (res) {
            toastr.success("添加成功");
            getDepartmentType()

            // DepartmentsTpl.choseDdepartment(DepartmentsTpl.activeDepartment);//选科室
            // DepartmentsTpl.choseTemplate(DepartmentsTpl.templateList[DepartmentsTpl.DepartmentsTpl.activeTemplate],activeTemplateId);//选模板 todo

          },function (err) {
            toastr.error(err.reason);
            DepartmentsTpl.departmentTypeList.pop();

          });
        }else{ //修改科室类别功能
          var data = {id:item.id,departmentCategory :item.departmentCategory}; //要传的数据
          IhcService.put("/paramSetting/rename",data).then(function () {
            //console.log(" updateTemplate success")
            toastr.success("修改成功");
            getDepartmentType()

          },function (err) {
            toastr.error(err.reason);
            getDepartmentType()
          });
        }
      }else{
        if(item.newItem){
          DepartmentsTpl.departmentTypeList.pop();
          DepartmentsTpl.activeType.index--;
        }else{
          toastr.error('名称不能为空');
          item.focus=true;
        }
      }

    };

    DepartmentsTpl.deleteType = function() {
      // 科室类别ID
      if(DepartmentsTpl.activeType.id){
        toolService.getModalResult({modalTitle:"删除确认",size:'sm',modalContent:"是否确定删除？"})
        .then(function () {
          IhcService.delete("/paramSetting/department/"+DepartmentsTpl.activeType.id).then(function (res) {
            toastr.success("删除成功");
            getDepartmentType();
          },function (err) {
            if(err.code===18){
              toastr.error("该科室类别不能删除"); //删除失败
            }else {
              toastr.error(err.reason); //删除失败
            }

            getDepartmentType();
          });
        })
      }
    }



    DepartmentsTpl.choseDdepartment=function (item,index) {
      if(item){
        DepartmentsTpl.activeDepartment=item;
        DepartmentsTpl.activeDepartment.index=index;
      }else {
        DepartmentsTpl.activeDepartment=DepartmentsTpl.departments[0];
        DepartmentsTpl.activeDepartment.index=0;
        if(DepartmentsTpl.activeDepartment.name === null){
          DepartmentsTpl.activeDepartment.focus = true;
        }
      }

    };    //获取科室


    DepartmentsTpl.addDdepartment=function () {

      var department = {
        departmentCategory:DepartmentsTpl.activeType.departmentCategory, //科室类别
        "name": "",//#模板名字
        "newItem":true,//判断是否为新添加的模板 需要保存时删掉
        "focus":true
      };
      DepartmentsTpl.departments.push(department)
      DepartmentsTpl.activeDepartment.index = DepartmentsTpl.departments.length-1
      //增加一个后自动选择最后一个

    };

    DepartmentsTpl.saveDdepartment=function (item) {
      if(item.name){
        delete item.focus;
        if(item.newItem){ //新数据就添加模板
          delete item.newItem;
          var data = {departmentCategory:DepartmentsTpl.activeType.departmentCategory,name:item.name}; //新增


          IhcService.post("/paramSetting/departmentSetting",data).then(function (res) {
            toastr.success("添加成功");
            getDepartmentType();

          },function (err) {
            toastr.error(err.reason);
            DepartmentsTpl.departments.pop();

          });

        }else{ //旧数据更新模板

          var data = {id:DepartmentsTpl.activeType.id,code :item.code,name:item.name}; //修改
          IhcService.put("/paramSetting/edit",data).then(function () {
            //console.log(" updateTemplate success")
            toastr.success("修改成功");
            getDepartmentType()

          },function (err) {
            toastr.error(err.reason);
            getDepartmentType()

          });
        }
      }else{
        if(item.newItem){
          DepartmentsTpl.departments.pop();
        }else{
          toastr.error('科室名称不能为空');
          getDepartmentType()
          // item.focus=true;
        }
      }

    };


    DepartmentsTpl.deleteTemplate=function () {
      if(activeTemplateId){
        toolService.getModalResult({modalTitle:"删除确认",modalContent:"是否确定删除？"})
          .then(function () {
            DiagnosisService.deleteDiagnoseTemplate(activeTemplateId).then(function (res) {
              toastr.success("删除成功");
              getDepartmentType()

            });
          })
      }

    };


  }
})();

