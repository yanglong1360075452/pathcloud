/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('searchDiagnosisController', searchDiagnosisController);

  /** @ngInject */
  function searchDiagnosisController($scope,$state,toastr,DiagnosisService,MaterialService,$timeout){
    var searchDiagnosis=this;

    searchDiagnosis.departmentsList = []; //送检科室列表
    searchDiagnosis.grossingUserList = []; //诊断列表
    searchDiagnosis.reportUserList = []; //报告医生列表
    searchDiagnosis.searchData = {};//只需申明一次
    searchDiagnosis.defaultTime = 1;

    // 筛选条件
  
    searchDiagnosis.outConsultList = [{code: true, name: "已申请"}, {code: false, name: "未申请"}];
    searchDiagnosis.specialTypeList = [{code:0, name:"常规"},{code:1, name:"冰冻"},{code:2, name:"免疫组化"},{code:3, name:"特染"}, {code:4, name:"会诊"} ];
    
    //样本状态列表
    searchDiagnosis.statusList = [
      {name:"待一级诊断",value:20},
      {name:"待二级诊断",value:21},
      {name:"待三级诊断",value:22},
      {name:"待会诊",value:23},
      {name:"已发报告",value:25},
      {name:"特殊染色",value:16},
      {name:"重补取",value:10},
      {name:"深切",value:15}
    ];
    
    
    //设置表格头部
    searchDiagnosis.tableHeaders=[
      {name:"病理号",order:1},{name:"姓名",order:2},{name:"检查类别"},
      {name:"送检科室",order:null},{name:"收片医生",order:null},{name:"一级诊断",order:null},
      {name:"二级诊断",order:null},{name:"三级诊断",order:null},
      {name:"审核医生",order:null},{name:"报告时间",order:8},
      {name:"状态",order:null}
    ];
    

   /* //时间格式设置
    var d = new Date();
    var lastDay = new Date().setDate(d.getDate()-1);
    var lastWeek = new Date().setDate(d.getDate()-7);
    var lastMonth = new Date().setMonth(d.getMonth()-1);
    var lastSixMonth = new Date().setMonth(d.getMonth()-6);

    //筛选时间列表
    searchDiagnosis.dateList = [
      {name:"最近1天", date:lastDay },
      {name:"最近1周", date:lastWeek },
      {name:"最近1个月", date:lastMonth },
      {name:"最近6个月", date:lastSixMonth}
    ];*/

    

    //获取送检科室列表
    function getDepartmentsList(){
      MaterialService.getDepartments().then(
        function(result){
          searchDiagnosis.departmentsList = result;
        }
      );
    };
    getDepartmentsList();

    //获取诊断医生列表
    function getGrossingUserList(){
      DiagnosisService.getDiagnosisDoctor().then(
        function(result){
          //console.log("获取取材医生列表数据：-----",result);
          searchDiagnosis.grossingUserList = result;
        }
      );
    }
    getGrossingUserList();

    //获取报告医生列表
    function getReportUserList(){
      DiagnosisService.getReportDoctor().then(
        function(result){
          //console.log("获取取材医生列表数据：-----",result);
          searchDiagnosis.reportUserList = result;
        }
      );
    }
    getReportUserList();

    //默认初始筛选条件数据
    function initFilter(){
      searchDiagnosis.defaultTime = 1;
      searchDiagnosis.searchData.page = 1;//页数
      searchDiagnosis.searchData.length = 20;//每页记录数
      searchDiagnosis.searchData.status = null;//状态  # 19-待一级诊断  20-待二级诊断  21-待三级诊断
      searchDiagnosis.searchData.departments = null;//送检科室
      searchDiagnosis.searchData.diagnoseDoctor = null;//诊断医生
      searchDiagnosis.searchData.reportDoctor = null;//报告医生
    }
    initFilter();

    //搜索
    searchDiagnosis.search = function (e) {
      if(searchDiagnosis.searchStr){
        searchDiagnosis.defaultTime = 5;
      }else {
        searchDiagnosis.defaultTime = 1;
      }
      searchDiagnosis.searchData = {
        page: 1,
        length: 20,
        filter: searchDiagnosis.searchStr
      };

      searchDiagnosis.getGrossingDataList();
    };
    //筛选
    searchDiagnosis.query = function (e) {

      delete searchDiagnosis.searchData.filter;
      searchDiagnosis.getGrossingDataList();
    };

    //排序
    searchDiagnosis.getSortList = function (item) {
      item.sort==='asc'?item.sort='desc':item.sort='asc';
      searchDiagnosis.searchData.sort = item.sort;
      searchDiagnosis.searchData.order = item.order;
      searchDiagnosis.getGrossingDataList();
    };

    //获取诊断记录列表
    searchDiagnosis.getGrossingDataList = function(type){
      //console.log("searchData======",searchDiagnosis.searchData);

      $timeout(function () {
        searchDiagnosis.searchData.createTimeStart=searchDiagnosis.startTime;
        searchDiagnosis.searchData.createTimeEnd=searchDiagnosis.endTime;
        DiagnosisService.getPathologyData(searchDiagnosis.searchData).then(
          function(result){
            searchDiagnosis.grossingData = result.data;
            searchDiagnosis.totalItems = result.total;
          })
      })

    };

    searchDiagnosis.getGrossingDataList();

  }
})();

