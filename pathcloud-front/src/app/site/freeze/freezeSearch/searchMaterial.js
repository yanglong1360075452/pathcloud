/**
 * Created by Administrator on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('FreezeSearchController', FreezeSearchController);

  /** @ngInject */
  function FreezeSearchController($filter,IhcService,$uibModal,$log,PathologyService,MaterialService,$timeout) {
    //定义参数
    var freeze = this;
    freeze.departmentsList = []; //送检科室列表
    freeze.grossingUserList = []; //取材医生列表
    freeze.searchData = {};//只需申明一次
    freeze.defaultTime = 1;//默认的时间范围


    //设置表格头部
    freeze.tableHeaders=[
      {name:"编号",order:null},
      {name:"病理号",order:1},{name:"姓名",order:2},
      {name:"送检科室",order:null},{name:"玻片号",order:null},
      {name:"取材部位",order:null},{name:"组织数",order:null},
      {name:"取材标识",order:null},{name:"取材医生",order:null},
      {name:"记录人员",order:null},{name:"冰冻切片机",order:null},
      {name:"取材时间",order:11},{name:"状态",order:null}
    ];

    //样本状态列表
    // 10-待取材   20-待一级诊断   21-待二级诊断 22-待三级诊断
    freeze.statusList = [
      {name:"待取材",value:10},
      {name:"待一级诊断",value:20},
      {name:"待二级诊断",value:21},
      {name:"待三级诊断",value:22}
    ];

    //获取送检科室列表
    function getDepartmentsList(){
      MaterialService.getDepartments().then(
        function(result){
          //console.log("获取送检科室列表数据：-----",result);
          freeze.departmentsList = result;
        }
      );
    }
    getDepartmentsList();

    //获取取材医生列表
    function getGrossingUserList(){
      MaterialService.getGrossingUser().then(
        function(result){
          console.log("获取取材医生列表数据：-----",result);
          freeze.grossingUserList = result;
        }
      );
    }
    getGrossingUserList();

    //获取取材记录员列表
    function getSecOperatorList(){
      MaterialService.getGrossingUser().then(
        function(result){
          //console.log("获取取材医生/取材记录员列表数据：-----",result);
          freeze.secOperatorList = result;
        }
      );
    };
    getSecOperatorList();


    //默认初始筛选条件数据
    function initFilter(){
      // freeze.searchData.timeEnd = new Date().getTime();
      freeze.searchData.page = 1;
      freeze.searchData.length = 20;
      freeze.searchData.status = null;
      freeze.searchData.departments = null;
      freeze.searchData.operator = null;
      freeze.searchData.secOperator = null;
    }
    initFilter();

    //获取筛选条件列表
    freeze.getSortList = function (item) {
      if(!item.order) return;

      item.sort==='asc'?item.sort='desc':item.sort='asc';
      freeze.searchData.sort = item.sort;
      freeze.searchData.order = item.order;
      freeze.getDataList();
    };


    freeze.getDataList = function(){

      //当状态为待取材 筛选取材医生是无效的
      if(freeze.searchData.status === 10){
        if(freeze.searchData.operator||freeze.searchData.secOperator){
          freeze.grossingData = [];
          freeze.totalItems = 0;
          return false;
        }
      }

      $timeout(function () {
        freeze.searchData.timeStart=freeze.startTime;
        freeze.searchData.timeEnd=freeze.endTime;
        IhcService.get("/frozen",freeze.searchData).then(
          function(result){
            freeze.grossingData = result.data;
            freeze.totalItems = result.total;
            // console.log("获取取材记录列表数据：-----",result)
          }
        )
      })
    };
    freeze.getDataList(); //init
    // 筛选
    freeze.query=function () {
      delete freeze.searchData.filter;
      freeze.getDataList();
    };
    // 查询
    freeze.search=function () {

      if(!freeze.searchStr){
        freeze.defaultTime=1;//没有搜索内容的时候重置筛选条件
        delete freeze.searchData.filter;
      }else{
        freeze.defaultTime=5;//搜索时默认时间改成半年
      }
      freeze.searchData={
        page:1,
        length:20,
        filter:freeze.searchStr,
      };
      freeze.getDataList();
    };

    freeze.reasonModal = function (item) {

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

