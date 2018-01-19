/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('GroupStatisticController', GroupStatisticController);

  /** @ngInject */
  function GroupStatisticController($scope,toolService,toastr,StatisticService){
    var gStatistic = this;
    gStatistic.workData = [];//工作量记录数据
    gStatistic.searchData = {};//查询条件

    //默认初始筛选条件数据
    function initFilter(){
      //设置表格头部
      gStatistic.tableHeaders=[
        {name:""},{name:""},{name:"1月"},
        {name:"2月"},{name:"3月"},
        {name:"4月"},{name:"5月"},
        {name:"6月"},{name:"7月"},
        {name:"8月"},{name:"9月"},
        {name:"10月"},{name:"11月"},
        {name:"12月"},{name:"合计"}
      ];
      // specialDye | int | 染色方式（0-HE, 1-免疫组化）
      gStatistic.specialDyeList = [
        {name:"HE",value:0},
        // {name:"免疫组化",value:1},
      ];
      gStatistic.searchData.filter=0;

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
    // var param=apiUrl+"/statistics/workload/groupInspectCategory/export?";
    // function formatParams(filter) {
    //   for (var c in filter) {
    //     if (filter[c]) {
    //       param=param+c+"="+filter[c]+"&";
    //     }
    //   }
    // }
    gStatistic.export=function () {
      if(!gStatistic.workData) return;
      toolService.export(gStatistic.searchData,"/statistics/workload/groupInspectCategory/export")
    };

    function query() {
      gStatistic.searchData.endTime=new Date($scope.selectYear+1,0).getTime();
      gStatistic.searchData.startTime=new Date($scope.selectYear,0).getTime();
      StatisticService.groupInspectCategory(gStatistic.searchData).then(function (res) {
        gStatistic.workData=res;
        // console.info("gStatistic.searchData=====",gStatistic.searchData)
      });
    }


    //获取工作量列表
    gStatistic.getWorkDataList = function(type){
      console.log("获取月统计列表searchData======",gStatistic.searchData);
      query()
    };
    gStatistic.getWorkDataList();


  }
})();
