(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('tableChart', function () {
      return {
        restrict: 'E',
        templateUrl: 'app/components/statistics/tableChart/tableChart.html',
        scope: {
          chartTitle: '@',
          getDataFun:'&'
        },
        controller: tableChartController,
        controllerAs: 'tChart',
        bindToController: true
      };

      /** @ngInject */
      function tableChartController($scope,StatisticService) {
        var tChart = this;
        tChart.chartFilter = '1';
        tChart.sectionList = [{name:"取材",value:1},{name:"脱水",value:3},{name:"包埋",value:5},{name:"切片",value:6}];
        tChart.timeList = [
          //{name:"日排行",value:1},
          {name:"月排行",value:3}
        ];
        tChart.sectionData = [];//工作量排行记录
        tChart.workStation = 1;
        tChart.time = 3;

        var date = new Date;
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month = (month<10?"0" + month:month);
        var today = date.getDate();
        var nowTime = date.getTime();
        var day = date.getDay();
        var oneDayLong = 24*60*60*1000;

        //设置start时间戳
        tChart.setTime = function(){
          var str = "";
          //if(tChart.time==1){//日排行
          //  str = year + '-' + month + '-' + today;
          //  str = str.replace(/-/g, '/'); // 将-替换成/，因为下面这个构造函数只支持/分隔的日期字符串
          //  tChart.start = new Date(str).getTime();//指定日期的时间戳
          //}
          //else if(tChart.time==2){//周排行
          //  var mondayTime = nowTime - (day-1)*oneDayLong;
          //  var monday = new Date(mondayTime);
          //  str = monday.getFullYear() + '-' + (monday.getMonth()+1) + '-' + monday.getDate();
          //  tChart.start = new Date(str).getTime();
          //  //console.warn("work load data :","mondayTime :",mondayTime,"str :",str,"tChart.start :",tChart.start);
          //}
          //else{//月排行
            str = year + '-' + month + '-01';
            str = str.replace(/-/g, '/'); // 将-替换成/，因为下面这个构造函数只支持/分隔的日期字符串
            tChart.start = new Date(str).getTime();//指定日期的时间戳
          //}
          console.warn(" table str:",str);
          tChart.getDataFun()(tChart.start,tChart.end,tChart.workStation).then(function(data){
            tChart.sectionData = data;
          });
        }
        tChart.end = Date.now();//当前日期的时间戳
        tChart.setTime();

      };

    })

})();
