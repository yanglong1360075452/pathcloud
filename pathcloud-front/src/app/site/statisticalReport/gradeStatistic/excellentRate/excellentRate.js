/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ExcellentStatisticController', ExcellentStatisticController);

  /** @ngInject */
  function ExcellentStatisticController($scope,CsvService,toastr,IhcService,$timeout){
    var mStatistic = this;
    mStatistic.workData = [];//工作量记录数据
    mStatistic.searchData = {};//查询条件

    //设置表格头部
    mStatistic.tableHeaders=[
      {name:"月份"},{name:"切片总数"},{name:"蜡块总数"},{name:"重取数"},{name:"重取率"},
      {name:"重切数"},{name:"重切率"},
      {name:"优良切片"},{name:"优良率"},
    ];



    //默认初始筛选条件数据
    function initFilter(){

      mStatistic.searchData={
        page:1,
        length:16,
        // specialDye:0
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
      if(!mStatistic.workData || !mStatistic.workData.length) return;

      CsvService.tableToExcel('#excellent-table', '优良率');

    };

    function query() {
      IhcService.get("/statistics/quality/good",mStatistic.searchData).then(function (res) {
        mStatistic.workData=res;
      });
    }

    //获取工作量列表
    mStatistic.getWorkDataList = function(type){
      query();
    };
    mStatistic.getWorkDataList();


  }
})();
