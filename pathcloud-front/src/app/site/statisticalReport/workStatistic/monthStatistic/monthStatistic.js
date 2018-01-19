/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('MonthStatisticController', MonthStatisticController);

  /** @ngInject */
  function MonthStatisticController($scope,toolService,toastr,StatisticService){
    var mStatistic = this;
    mStatistic.workData = [];//工作量记录数据
    mStatistic.searchData = {};//查询条件

    //设置表格头部
    mStatistic.tableHeaders=[
      {name:"检查类别"},{name:"1月"},
      {name:"2月"},{name:"3月"},
      {name:"4月"},{name:"5月"},
      {name:"6月"},{name:"7月"},
      {name:"8月"},{name:"9月"},
      {name:"10月"},{name:"11月"},
      {name:"12月"},{name:"合计"}
    ];



    //默认初始筛选条件数据
    function initFilter(){
      // mStatistic.searchData.page = 1;//页数
      // mStatistic.searchData.length = 20;//每页记录数
      // mStatistic.searchData.station = null;//状态
      //工位列表
      mStatistic.stationList = [
        {name:"本院",value:1},
        {name:"分院",value:2},
        {name:"全部",value:3}
      ];

      mStatistic.searchData={
        page:1,
        length:16,
        station:1
      }

      // 时间日期筛选部分 ===start===
      var date = new Date();
      var nowYear=date.getFullYear();
      var yearArr=[];
      for(var i=2016;i<=nowYear;i++){
        yearArr.push(i);
      }
      $scope.yearArr=yearArr;
      $scope.selectYear=date.getFullYear();

      // 时间日期筛选部分 ===end===
    }
    initFilter();

    //导出病例信息表格
      
    mStatistic.export=function () {
      if(!mStatistic.workData) return;
      toolService.export(mStatistic.searchData,"/statistics/workload/monthInspectCategory/export")
    };

    function query() {
      mStatistic.searchData.endTime=new Date($scope.selectYear+1,0).getTime();
      mStatistic.searchData.startTime=new Date($scope.selectYear,0).getTime();
      StatisticService.monthInspectCategory(mStatistic.searchData).then(function (res) {
        mStatistic.workData=res;
        // console.info("mStatistic.searchData=====",mStatistic.searchData)
      });
    }


    //获取工作量列表
    mStatistic.getWorkDataList = function(type){
      console.log("获取月统计列表searchData======",mStatistic.searchData);
      query()
    };
    mStatistic.getWorkDataList();


  }
})();
