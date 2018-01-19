/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ScorePeopleStatisticController', ScorePeopleStatisticController);

  /** @ngInject */
  function ScorePeopleStatisticController($scope,toolService,toastr,StatisticService){
    var pStatistic = this;
    pStatistic.workData = [];//工作量记录数据
    pStatistic.searchData = {};//查询条件

    //设置表格头部
    pStatistic.tableHeaders=[
      {name:"用户名",order: 1},{name:"姓名",order: 2},
      {name:"工位",order: 3},{name:"蜡块数"},{name:"玻片数"},
      {name:"重取数",order: 5},{name:"重取率",order: 6},
      {name:"重切数",order: 7},{name:"重切率",order: 8},
      {name:"不合格数",order: 9},{name:"不合格率",order: 10},
      {name:"质控评分",order: 11}
    ];


    //默认初始筛选条件数据
    function initFilter(){
      // pStatistic.searchData.page = 1;//页数
      // pStatistic.searchData.length = 20;//每页记录数
      // pStatistic.searchData.station = null;//状态

      //工位列表
      pStatistic.stationList = [
        {name:"取材",value:1},
        {name:"脱水",value:3},
        {name:"包埋",value:5},
        {name:"切片",value:6}
      ];
      // specialDye | int | 染色方式（0-HE, 1-免疫组化）
      pStatistic.specialDyeList = [
        {name:"HE",value:0},
        // {name:"免疫组化",value:1},
      ];
      pStatistic.searchData={
        page:1,
        length:20,
        // station:1,
        specialDye:0
      }

     // 时间日期筛选部分 ===start===
      var date = new Date();
      var nowYear=date.getFullYear();
      var yearArr=[];
      for(var i=2016;i<=nowYear;i++){
        yearArr.push(i);
      }
      $scope.yearArr=yearArr;
      $scope.monthArr=[1,2,3,4,5,6,7,8,9,10,11,12];
      pStatistic.searchData.year=date.getFullYear();
      pStatistic.searchData.month=date.getMonth()+1;
     // 时间日期筛选部分 ===end===
    }
    initFilter();

    pStatistic.export=function () {
      if(!pStatistic.workData) return;
      toolService.export(pStatistic.searchData,"/statistics/quality/person/export")
    };

    function query() {
      // pStatistic.searchData.endTime=new Date($scope.selectYear,$scope.selectMonth).getTime();
      // pStatistic.searchData.startTime=new Date($scope.selectYear,$scope.selectMonth-1).getTime();
      console.info("pStatistic.searchData=====",pStatistic.searchData)
      StatisticService.qualityPersonStatistics(pStatistic.searchData).then(function (res) {
        pStatistic.workData=res.data;
        pStatistic.total=res.total;
      });
    }

    pStatistic.getSortList = function (item) {

      if(!item.order) return;
      item.sort==='asc'?item.sort='desc':item.sort='asc';
      pStatistic.searchData.sort = item.sort;
      pStatistic.searchData.order = item.order;
      query()
    };


    //获取工作量列表
    pStatistic.getWorkDataList = function(type){
      //console.log("searchData======",pStatistic.searchData);
      query();
    };
    pStatistic.getWorkDataList();

  }
})();

