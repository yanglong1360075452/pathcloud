/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('searchDyeController', searchDyeController);

  /** @ngInject */
  function searchDyeController($scope,$timeout,toastr,DyeService,IhcService){
    var searchDye = this;
    searchDye.searchData = {};//筛选条件
    searchDye.operatorList = [];//染色技术员列表
    searchDye.dyeDataList = [];//染色记录
    searchDye.defaultTime = 1;

    var instrumentFilter = {
      page:1,
      length:100,
      status:0, //#0正常 1禁用 2报修
      // type:2 //2-封片机
    };
    IhcService.get("/instrument",instrumentFilter).then(function (res) {
      searchDye.instruments = res.data;
    });

    //点击按钮之后搜索框获得焦点
    $timeout(function () {
      $("button").click(function () {
        $("#searchInput").focus();
      })
    },1000);


    //表格头部
    searchDye.tableHeaders = [
      {name:"病理号",order:1},{name:"蜡块号"},
      {name:"玻片号"},{name:"取材医生"},{name:"取材标识"},
      // {name:"取材部位"},{name:"组织数"},
      {name:"染色技术员"},{name:"染色机"},{name:"染色时间",order:9},
      {name:"封片技术员"},{name:"封片机"},{name:"封片时间"},
      {name:"样本状态"}
    ];


    //样本状态列表
    searchDye.statusList = [
      {name:"待染色",value:16},
      {name:"待封片",value:17},
      {name:"待制片确认",value:18}
    ];

    //获取染色技术员
    searchDye.getOperator = function(){
      DyeService.getOperator().then(
        function(result){
          searchDye.operatorList = result;
        }
      );
    };
    searchDye.getOperator();

    //默认初始筛选条件数据
    function initFilter(){
      searchDye.defaultTime = 1;//开始时间
      // searchDye.searchData.endTime = new Date().getTime();//结束时间
      searchDye.searchData.page = 1;//页数
      searchDye.searchData.length = 20;//每页记录数
      searchDye.searchData.status = null;//状态  16-待染色，17-待制片确认
      searchDye.searchData.operatorId = null;//包埋技术员ID
    }
    initFilter();

    //根据搜索条件获得染色记录  时间要反正timeout里
    searchDye.getDyeData = function(){

      $timeout(function () {
        searchDye.searchData.startTime = searchDye.startTime;
        searchDye.searchData.endTime = searchDye.endTime;
        DyeService.getDyeData(searchDye.searchData).then(
          function(result){
            searchDye.dyeDataList = result.data;
            searchDye.dyeDataCount = result.total;
          }
        );
      })

    };
    searchDye.getDyeData();

    searchDye.getSortList = function (item) {
      if(!item.order) return;

      item.sort==='asc'?item.sort='desc':item.sort='asc';
      searchDye.searchData.sort = item.sort;
      searchDye.searchData.order = item.order;
      searchDye.getDyeData();
    };

    //输入 时间范围最大
    searchDye.search = function () {
      if(!searchDye.searchStr){
        searchDye.defaultTime=1;//没有搜索内容的时候重置筛选条件
        delete searchDye.searchData.filter;
      }else{
        searchDye.defaultTime=5;//搜索时默认时间改成半年
      }
      searchDye.searchData={
        page:1,
        length:20,
        filter:searchDye.searchStr
      };

      searchDye.getDyeData();
    };
    searchDye.query = function () {
        delete searchDye.searchData.filter;
        searchDye.getDyeData();
    };

  }
})();

