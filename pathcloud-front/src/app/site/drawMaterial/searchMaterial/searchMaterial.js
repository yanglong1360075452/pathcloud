/**
 * Created by Administrator on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SearchMaterialController', SearchMaterialController);

  /** @ngInject */
  function SearchMaterialController($filter,IhcService,$uibModal,$log,PathologyService,MaterialService,$timeout) {
    //定义参数
    var searchMaterial = this;
    searchMaterial.departmentsList = []; //送检科室列表
    searchMaterial.grossingUserList = []; //取材医生列表
    searchMaterial.searchData = {};//只需申明一次
    searchMaterial.defaultTime = 1;//默认的时间范围


    //设置表格头部
    searchMaterial.tableHeaders=[
      {name:"病理号",order:1},{name:"姓名",order:2},
      {name:"送检科室",order:null},{name:"蜡块号",order:null},
      {name:"取材部位",order:null},{name:"组织数",order:null},
      {name:"取材标识",order:null},{name:"取材医生",order:null},
      {name:"记录人员",order:null},{name:"脱水篮编号",order:null},
      {name:"取材时间",order:11},{name:"状态",order:null}
    ];

    //样本状态列表
    searchMaterial.statusList = [
      {name:"待取材",value:10},
      {name:"待取材确认",value:11},
      {name:"待脱水",value:12},
      {name:"已撤销",value:41}
      //10-待取材   11-待取材确认   12-待脱水
    ];
  
    searchMaterial.inspectCategory = [{code:0, name:"常规"},{code:1, name:"冰冻"},{code:2, name:"免疫组化"},{code:3, name:"特染"}, {code:4, name:"会诊"} ];
    searchMaterial.consultStatus = [{code: true, name: "已申请"}, {code: false, name: "未申请" }];
    

    //获取送检科室列表
    function getDepartmentsList(){
      MaterialService.getDepartments().then(
        function(result){
          //console.log("获取送检科室列表数据：-----",result);
          searchMaterial.departmentsList = result;
        }
      );
    };
    getDepartmentsList();

    //获取取材医生列表
    function getGrossingUserList(){
      MaterialService.getGrossingUser().then(
        function(result){
          console.log("获取取材医生列表数据：-----",result);
          searchMaterial.grossingUserList = result;
        }
      );
    };
    getGrossingUserList();

    //获取取材记录员列表
    function getSecOperatorList(){
      MaterialService.getGrossingUser().then(
        function(result){
          //console.log("获取取材医生/取材记录员列表数据：-----",result);
          searchMaterial.secOperatorList = result;
        }
      );
    };
    getSecOperatorList();


    //默认初始筛选条件数据
    function initFilter(){
      // searchMaterial.searchData.timeEnd = new Date().getTime();
      searchMaterial.searchData.page = 1;
      searchMaterial.searchData.length = 20;
      searchMaterial.searchData.status = null;
      searchMaterial.searchData.departments = null;
      searchMaterial.searchData.operator = null;
      searchMaterial.searchData.secOperator = null;
    }
    initFilter();

    //获取筛选条件列表
    searchMaterial.getSortList = function (item) {
      if(!item.order) return;

      item.sort==='asc'?item.sort='desc':item.sort='asc';
      searchMaterial.searchData.sort = item.sort;
      searchMaterial.searchData.order = item.order;
      searchMaterial.getDataList();
    };


    searchMaterial.getDataList = function(){

      //当状态为待取材 筛选取材医生是无效的
      if(searchMaterial.searchData.status === 10){
        if(searchMaterial.searchData.operator||searchMaterial.searchData.secOperator){
          searchMaterial.grossingData = [];
          searchMaterial.totalItems = 0;
          return false;
        }
      }

      $timeout(function () {
        searchMaterial.searchData.timeStart=searchMaterial.startTime;
        searchMaterial.searchData.timeEnd=searchMaterial.endTime;
        MaterialService.getGrossingList(searchMaterial.searchData).then(
          function(result){
            searchMaterial.grossingData = result.data;
            searchMaterial.totalItems = result.total;
            // console.log("获取取材记录列表数据：-----",result)
          }
        )
      })
    };
    searchMaterial.getDataList(); //init
    // 筛选
    searchMaterial.query=function () {
      delete searchMaterial.searchData.filter;
      searchMaterial.getDataList();
    };
    // 查询
    searchMaterial.search=function () {

      if(!searchMaterial.searchStr){
        searchMaterial.defaultTime=1;//没有搜索内容的时候重置筛选条件
        delete searchMaterial.searchData.filter;
      }else{
        searchMaterial.defaultTime=5;//搜索时默认时间改成半年
      }
      searchMaterial.searchData={
        page:1,
        length:20,
        filter:searchMaterial.searchStr,
      };
      searchMaterial.getDataList();
    };

    searchMaterial.reasonModal = function (item) {

      IhcService.get("/grossing/"+item.pathologyId).then(function (res) {
        var data = {
          header:"撤销记录",
          timeLabel:"撤销时间",
          time:res.operationTime,
          operatorLabel:"撤销人",
          operator:res.operatorName,
          reasonLabel:"撤销原因",
          reason:res.note
        };
        $uibModal.open({
          templateUrl: 'app/site/modal/reasonModal.html',
          // size:'',
          // backdrop: false,
          controller: 'ReasonModalController',
          controllerAs: 'reasonMod',
          resolve: {
            data: function () {
              return data
            }
          }
        })

      })

    }



  }
})();

