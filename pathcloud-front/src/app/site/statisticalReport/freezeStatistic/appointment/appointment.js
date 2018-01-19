/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('FreezeAppointmentController', FreezeAppointmentController);

  /** @ngInject */
  function FreezeAppointmentController($scope,toolService,toastr,StatisticService){
    var appointment = this;

    //默认初始筛选条件数据
    function init(){

      appointment.workData = [];//获取到的数据
      appointment.searchData = {
        instrumentId:1
      };//查询条件
      appointment.instrumentList = [{name:"切片机1",code:1},{name:"切片机2",code:2}];//查询条件

     // 时间日期筛选部分 ===start===
      var date = new Date();
      var nowYear=date.getFullYear();
      var yearArr=[];
      for(var i=2016;i<=nowYear;i++){
        yearArr.push(i);
      }
      $scope.yearArr=yearArr;
      $scope.monthArr=[1,2,3,4,5,6,7,8,9,10,11,12];
      appointment.searchData.year=date.getFullYear();
      appointment.searchData.month=date.getMonth()+1;
      // 时间日期筛选部分 ===end===

      // 冰冻预约表头
      appointment.tableHeaders=["8:00","9:00","10:00","11:00","12:00","13:00","14:00","15:00","16:00","17:00"];

    }
    init();

    // 导出
    appointment.export=function () {
      if(!appointment.workData) return;
      toolService.export(appointment.searchData,"/statistics/freeze/booking/export")
    };


    //获取工作量列表
    appointment.getWorkDataList = function(type){
      //console.log("searchData======",appointment.searchData);
      query();
    };
    appointment.getWorkDataList();

    function query() {
      // appointment.searchData.endTime=new Date($scope.selectYear,$scope.selectMonth).getTime();
      // appointment.searchData.startTime=new Date($scope.selectYear,$scope.selectMonth-1).getTime();
      console.info("appointment.searchData=====",appointment.searchData)

      //
      // var params={
      //   year:2017,
      //   month:3,
      // }

      StatisticService.freezeAppointmentStatistics(appointment.searchData).then(function (res) {
        appointment.workData=res;

      });
    }
  }
})();
