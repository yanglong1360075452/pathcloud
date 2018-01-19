(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('scoreTable', function () {
      return {
        restrict: 'E',
        templateUrl: 'app/components/statistics/scoreTable/scoreTable.html',
        scope: {
          chartTitle: '@',
          getDataFun:'&'
        },
        controller: scoreTableController,
        controllerAs: 'scoreTable',
        bindToController: true
      };

      /** @ngInject */
      function scoreTableController($scope,StatisticService) {
        var scoreTable = this;
        scoreTable.chartFilter = '1';
        scoreTable.scoreData = [];//质控评分排行记录
        scoreTable.timeList = [
          //{name:"日排行",value:1},
          {name:"月排行",value:3}
        ];
        scoreTable.time = 3;//初始化筛选条件

        var date = new Date;
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month = (month<10?"0" + month:month);
        var today = date.getDate();
        var nowTime = date.getTime();
        var day = date.getDay();
        var oneDayLong = 24*60*60*1000;

        //设置start时间戳
        scoreTable.setTime = function(){
          var str = "";
          //if(scoreTable.time==1){//日排行
          //  str = year + '-' + month + '-' + today;
          //  str = str.replace(/-/g, '/'); // 将-替换成/，因为下面这个构造函数只支持/分隔的日期字符串
          //  scoreTable.start = new Date(str).getTime();//指定日期的时间戳
          //}
          //else if(scoreTable.time==2){//周排行
          //  var mondayTime = nowTime - (day-1)*oneDayLong;
          //  var monday = new Date(mondayTime);
          //  str = monday.getFullYear() + '-' + (monday.getMonth()+1) + '-' + monday.getDate();
          //  scoreTable.start = new Date(str).getTime();
          //}
          //else{//月排行
            str = year + '-' + month + '-01';
            str = str.replace(/-/g, '/'); // 将-替换成/，因为下面这个构造函数只支持/分隔的日期字符串
            scoreTable.start = new Date(str).getTime();//指定日期的时间戳
          //}
          scoreTable.getDataFun()(scoreTable.start,scoreTable.end).then(function(data){
            scoreTable.scoreData = data;
          });
        }
        scoreTable.end = Date.now();//当前日期的时间戳
        scoreTable.setTime();




      };

    })

})();
