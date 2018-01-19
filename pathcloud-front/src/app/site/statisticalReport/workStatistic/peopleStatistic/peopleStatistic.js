/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('PeopleStatisticController', PeopleStatisticController);

  /** @ngInject */
  function PeopleStatisticController($scope,toolService,toastr,StatisticService,PathologyService){
    var pStatistic = this;
    pStatistic.workData = [];//工作量记录数据
    pStatistic.searchData = {};//查询条件


    //默认初始筛选条件数据
    function initFilter(){

      //设置表格头部
      pStatistic.tableHeaders=[
        {typeDesc:"用户名"},{typeDesc:"姓名",order:2}, {typeDesc:"工位"}
      ];

      // 获取检查类别列表  typeDesc
      PathologyService.inspectCategoryList().then(function (res) {
        pStatistic.tableHeaders=pStatistic.tableHeaders.concat(res,{typeDesc:"合计",order:3}) //动态的获取表格头部
        // console.info("获取检查类别列表",pStatistic.tableHeaders)
      })

      //工位列表
      pStatistic.stationList = [
        {name:"取材",value:1},
        {name:"脱水",value:3},
        {name:"包埋",value:5},
        {name:"切片",value:6}
      ];
      pStatistic.searchData={
        page:1,
        length:20,
        station:1
      };

     // 时间日期筛选部分 ===start===
      var date = new Date();
      var nowYear=date.getFullYear();
      var yearArr=[];
      for(var i=2016;i<=nowYear;i++){
        yearArr.push(i);
      }
      $scope.yearArr=yearArr;
      $scope.monthArr=[1,2,3,4,5,6,7,8,9,10,11,12];
      $scope.selectYear=date.getFullYear();
      $scope.selectMonth=date.getMonth()+1;
     // 时间日期筛选部分 ===end===
    }
    initFilter();

    //导出病例信息表格

    pStatistic.export=function () {
      if(!pStatistic.workData) return;
      toolService.export(pStatistic.searchData,"/statistics/workload/personInspectCategory/export")
    };

    function query() {
      pStatistic.searchData.endTime=new Date($scope.selectYear,$scope.selectMonth).getTime();
      pStatistic.searchData.startTime=new Date($scope.selectYear,$scope.selectMonth-1).getTime();
      console.info("pStatistic.searchData=====",pStatistic.searchData)
      StatisticService.personInspectCategory(pStatistic.searchData).then(function (res) {
        pStatistic.workData=res.data;
        pStatistic.totalItems=res.total;
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
      // console.log("searchData======",pStatistic.searchData);
      query()
    };
    pStatistic.getWorkDataList();

  }
})();
