/**
 * Created by Administrator on 2016/12/21.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SampleSettingController', SampleSettingController);

  /** @ngInject */
  function SampleSettingController($scope,toastr,paramSettingService,toolService,PathologyService,IhcService) {
    var sampleSetting=this;
    sampleSetting.activeMenu=0;

    /*
      // 第一部分  拒收原因
    */

    sampleSetting.data={
      param: "rejectReason",
      // name:"信息不匹配"
    }
    sampleSetting.add=function () {
      paramSettingService.setting(sampleSetting.data).then(function (res) {
        toastr.success("添加成功！")
        sampleSetting.getRemarkList()
        sampleSetting.data.name=null;
      },function (err) {
        toastr.error(err)
      })
    };
    sampleSetting.delete=function (code) {
      var modal={
        modalTitle:"警告",
        modalContent:"您确定要删除这条内容吗？",
        size:"sm"
      }
      toolService.getModalResult(modal).then(function () {
        paramSettingService.deleteRejectReason(code).then(function (res) {
          toastr.success("删除成功！")
          sampleSetting.getRemarkList()
        },function (err) {
          toastr.error(err)
        })
      })

    };


    sampleSetting.getRemarkList=function () {
      paramSettingService.rejectReason().then(function (res) {
        sampleSetting.sampleRemarkLIst=res;
      },function (err) {
        toastr.error(err);
      })
    }
    sampleSetting.getRemarkList();


    /*
     第二部分----
    */

    sampleSetting.inspectCategory={};

    //  # 1-根据样本数量打印 2-只打一张
    sampleSetting.printCountList = [{name:"根据样本数量打印",code:'1'},{name:"只打一张",code:'2'}];
    IhcService.get("/paramSetting/printCount").then(function (res) {
      sampleSetting.printCount = res;
    });

    sampleSetting.setPintCount = function (code) {
      IhcService.put("/paramSetting/printCount/"+code).then(function () {
        toastr.success("设置成功")
      })
    };

    //  # 1-手动打印 2-自动打印
    sampleSetting.printTypeList = [{name:"手动打印",code:'1'},{name:"自动打印",code:'2'}];
    IhcService.get("/paramSetting/printType").then(function (res) {
      sampleSetting.printType = res;
    });
    sampleSetting.setPintType = function (code) {
      IhcService.put("/paramSetting/printType/"+code).then(function () {
        toastr.success("设置成功")
      })
    };


    /*
    * 第三部分 医院
    * */
    sampleSetting.inspectHospital = {};
    // sampleSetting.hospitalLevelList = ["三甲","三乙", "三丙","二甲","二乙","二丙","其它" ]
    sampleSetting.hospitalLevelList = [{name:"三甲",code:1}, {name:"三乙",code:2}, {name:"三丙",code:3}, {name:"二甲",code:4}, {name:"二乙",code:5}, {name:"二丙",code:6}, {name:"其它",code:7} ];
  
    
    
    sampleSetting.getInspectHospital = function () {
      IhcService.get("/paramSetting/inspectHospital").then(function (res) {
        sampleSetting.inspectHospitalList = res;
      })
    };
    sampleSetting.getInspectHospital();
  
    sampleSetting.addInspectHospital = function () {
      var data = {
        name: sampleSetting.inspectHospital.name,
        grade: sampleSetting.inspectHospital.grade,
      };
      IhcService.post("/paramSetting/inspectHospital", data).then(function (res) {
        
        toastr.success("操作成功");
        sampleSetting.getInspectHospital();
      },function (err) {
        toastr.error(err.reason);
      })
    };
  
    sampleSetting.deleteInspectHospital = function (code) {
      var modal={
        modalTitle:"警告",
        modalContent:"您确定要删除这条内容吗？",
        size:"sm"
      };
      toolService.getModalResult(modal).then(function () {
  
        IhcService.delete("/paramSetting/inspectHospital/"+code ).then(function (res) {
          toastr.success("删除成功！");
          sampleSetting.getInspectHospital();
        },function (err) {
          toastr.error(err.reason)
        })
      })
      
      
    }

  }
})();

