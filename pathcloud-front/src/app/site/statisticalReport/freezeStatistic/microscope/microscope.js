/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('microscopeUseHistoryController', microscopeUseHistoryController);

  /** @ngInject */
  function microscopeUseHistoryController($scope,toolService,toastr,StatisticService,IhcService){
    var microcope = this;

    //默认初始筛选条件数据
    function init(){

      microcope.workData = [];//获取到的数据
      microcope.searchData = {
        instrumentId:1,
        page:1,
        length:20
      };//查询条件
      microcope.instrumentList = [{name:"显微镜1",code:1}];//查询条件

     // 时间日期筛选部分 ===start===
      var date = new Date();
      var nowYear=date.getFullYear();
      var yearArr=[];
      for(var i=2016;i<=nowYear;i++){
        yearArr.push(i);
      }
      $scope.yearArr=yearArr;
      $scope.monthArr=[1,2,3,4,5,6,7,8,9,10,11,12];
      microcope.searchData.year=date.getFullYear();
      microcope.searchData.month=date.getMonth()+1;
      // 时间日期筛选部分 ===end===

      // // 冰冻预约表头
      microcope.tableHeaders=["使用者姓名","员工号","联系方式","科室","开始使用时间","结束使用时间",];

    }
    init();

    // 导出
    microcope.export=function () {
      if(!microcope.workData) return;
      toolService.export(microcope.searchData,"/microscopeTracking/export")
    };


    //获取工作量列表
    microcope.getWorkDataList = function(type){
      //console.log("searchData======",microcope.searchData);
      query();
    };
    microcope.getWorkDataList();

    function query() {
      // microcope.searchData.endTime=new Date($scope.selectYear,$scope.selectMonth).getTime();
      // microcope.searchData.startTime=new Date($scope.selectYear,$scope.selectMonth-1).getTime();
      console.info("microcope.searchData=====",microcope.searchData)

      IhcService.get("/microscopeTracking",microcope.searchData).then(function (res) {
        microcope.workData=res.data;
        microcope.total = res.total;
      })

    }
  }
})();
