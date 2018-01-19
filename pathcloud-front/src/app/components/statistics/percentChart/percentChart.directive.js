(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('percentChart',function (){
      return {
        restrict: 'E',
        templateUrl: 'app/components/statistics/percentChart/percentChart.html',
        scope: {
          chartTitle: '@',
          lineConfig:'=',//【深坑】指令传值：@只传字符串；=可以传对象
          getDataFun:'&'
        },
        controller: percentChartController,
        controllerAs: 'pChart',
        bindToController: true
      };

      /** @ngInject */
      function percentChartController($scope,StatisticService,$timeout) {
        var pChart = this;

        var date = new Date;
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month = (month<10?"0" + month:month);

        //设置start时间戳
        pChart.setTime = function(){
          var str = "";
          if(pChart.timeFilter){
            str = year + '-01-01';
          }else{
            str = year + '-' + month + '-01';
          }
          str = str.replace(/-/g, '/'); // 将-替换成/，因为下面这个构造函数只支持/分隔的日期字符串
          //console.warn("str:",str);
          pChart.start = new Date(str).getTime();//指定日期的时间戳
          //console.warn("this is setTime()",str);
          pChart.getDataFun()(pChart.start,pChart.end).then(function(data){
            //console.warn("percentChart data:",data);
            drawChart(data);
          });
        }
        pChart.end = Date.now();//当前日期的时间戳
        pChart.setTime();

        function drawChart(data){
          pChart.option = {
            tooltip: {//提示框
              trigger: 'item',
              formatter: "{b}:({d}%)"
            },
            series: [//具体饼状图参数
              {
                name: pChart.chartTitle,
                type: 'pie',
                radius: ['50%', '70%'],
                avoidLabelOverlap: false,
                label: {
                  normal: {
                    show: false,
                    position: 'center'
                  },
                  emphasis: {
                    show: true,
                    textStyle: {
                      fontSize: '16',
                      fontWeight: 'bold'
                    }
                  }
                },
                data:data
              }
            ],
            color: ['#ed7d31','#5b9bd5']//设置饼状图颜色
          };

        };

      }
    })

})();
