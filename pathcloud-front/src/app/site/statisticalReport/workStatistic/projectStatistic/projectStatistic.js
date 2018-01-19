/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ProjectStatisticController', ProjectStatisticController);

  /** @ngInject */
  function ProjectStatisticController($scope,$state,toastr,$uibModal){
    var prStatistic = this;
    prStatistic.workData = [];//工作量记录数据
    prStatistic.searchData = {};//查询条件

    //设置表格头部
    prStatistic.tableHeaders=[
      {name:"用户名",order:1},{name:"姓名",order:2},
      {name:"工位",order:3},{name:"常规例数",order:5},
      {name:"冰冻例数",order:6},{name:"病理总数",order:7}
    ];

    //时间格式设置
    var d = new Date();
    var lastDay = new Date().setDate(d.getDate()-1);
    var lastWeek = new Date().setDate(d.getDate()-7);
    var lastMonth = new Date().setMonth(d.getMonth()-1);
    var lastSixMonth = new Date().setMonth(d.getMonth()-6);

    //筛选时间列表
    prStatistic.dateList = [
      {name:"最近1天", date:lastDay },
      {name:"最近1周", date:lastWeek },
      {name:"最近1个月", date:lastMonth },
      {name:"最近6个月", date:lastSixMonth}
    ];

    //工位列表
    prStatistic.stationList = [
      {name:"取材",value:1},
      {name:"脱水",value:2},
      {name:"包埋",value:3},
      {name:"切片",value:4}
    ];

    //默认初始筛选条件数据
    function initFilter(){
      prStatistic.searchData.createTimeStart = lastDay;//申请时间，开始时间
      prStatistic.searchData.createTimeEnd = new Date().getTime();//申请时间，结束时间
      prStatistic.searchData.page = 1;//页数
      prStatistic.searchData.length = 20;//每页记录数
      prStatistic.searchData.station = null;//状态
    }
    initFilter();

    //输入查询条件后回车事件
    prStatistic.getQueryData = function (e) {
      var keycode = window.event?e.keyCode:e.which;
      if(keycode == 13){//回车
        initFilter();
        if(prStatistic.searchData.filter){
          prStatistic.searchData.createTimeStart = lastSixMonth;
        }
        prStatistic.getWorkDataList();
      }
    };

    //获取筛选条件列表
    prStatistic.getSortList = function (item) {
      item.sort==='asc'?item.sort='desc':item.sort='asc';
      prStatistic.searchData.sort = item.sort;
      prStatistic.searchData.order = item.order;
      prStatistic.getWorkDataList();
    };

    //获取工作量列表
    prStatistic.getWorkDataList = function(type){
      console.log("searchData======",prStatistic.searchData);
      if(type){
        initFilter();
        prStatistic.searchData.createTimeStart = lastSixMonth;
      }

      prStatistic.workData = [{
        userName:'hello',
        firstName:'你好',
        station:'脱水',
        normalCount:10,
        iceCount:10,
        count:20
      }];
      prStatistic.totalItems = 1;

      //DiagnosisService.getPathologyData(prStatistic.searchData).then(
      //  function(result){
      //    prStatistic.grossingData = result.data;
      //    prStatistic.totalItems = result.total;
      //  }
      //);
    };
    prStatistic.getWorkDataList();


  }
})();
