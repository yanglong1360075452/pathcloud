/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ScoreMonthStatisticController', ScoreMonthStatisticController);

  /** @ngInject */
  function ScoreMonthStatisticController($scope,toolService,toastr,StatisticService){
    var mStatistic = this;
    mStatistic.workData = [];//工作量记录数据
    mStatistic.searchData = {};//查询条件

    //设置表格头部
    mStatistic.tableHeaders=[
      {name:"月份"},{name:"蜡块总数"},{name:"重取数"},{name:"重取率"},
      {name:"重切数"},{name:"重切率"},
      {name:"不合格数"},{name:"不合格率"},
    ];



    //默认初始筛选条件数据
    function initFilter(){

      mStatistic.searchData={
        page:1,
        length:16,
        specialDye:0
      }
      // specialDye | int | 染色方式（0-HE, 1-免疫组化）
      mStatistic.specialDyeList = [
        {name:"HE",value:0},
        // {name:"免疫组化",value:1},
      ];
      // 时间日期筛选部分 ===start===
      var date = new Date();
      var nowYear=date.getFullYear();
      var yearArr=[];
      for(var i=2016;i<=nowYear;i++){
        yearArr.push(i);
      }
      $scope.yearArr=yearArr;
      mStatistic.searchData.year=date.getFullYear();

      // 时间日期筛选部分 ===end===
    }
    initFilter();

    mStatistic.export=function () {
      if(!mStatistic.workData) return;

      toolService.export(mStatistic.searchData,"/statistics/quality/month/export")
    };

    function query() {
      StatisticService.qualityMonthStatistics(mStatistic.searchData).then(function (res) {
        mStatistic.workData=res;
        // console.info("mStatistic.searchData=====",mStatistic.searchData)
      });
    }

    //获取工作量列表
    mStatistic.getWorkDataList = function(type){
      //console.log("获取月统计列表searchData======",mStatistic.searchData);
      query();
    };
    mStatistic.getWorkDataList();


  }
})();
