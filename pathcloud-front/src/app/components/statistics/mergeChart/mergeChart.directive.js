(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('mergeChart',function (){
      return {
        restrict: 'E',
        templateUrl: 'app/components/statistics/mergeChart/mergeChart.html',
        scope: {
          chartTitle: '@',
          lineConfig:'=',//【深坑】指令传值：@只传字符串；=可以传对象
          getDataFun:'&'
        },
        controller: mergeChartController,
        controllerAs: 'mChart',
        bindToController: true
      };

      /** @ngInject */
      function mergeChartController($scope,StatisticService,$timeout) {
        var mChart = this;

        var date = new Date;
        var year = date.getFullYear();
        var month = date.getMonth() + 1;
        month = (month<10?"0" + month:month);

        //设置start时间戳
        mChart.setTime = function(){
          var str = "";
          if(mChart.timeFilter){
            str = year + '-01-01';
          }else{
            str = year + '-' + month + '-01';
          }
          str = str.replace(/-/g, '/'); // 将-替换成/，因为下面这个构造函数只支持/分隔的日期字符串
          mChart.start = new Date(str).getTime();//指定日期的时间戳
          mChart.getDataFun()(mChart.start,mChart.end).then(function(data){
            //console.warn("slide quality data:",data);
            drawChart(data);
          });
        }
        mChart.end = Date.now();//当前日期的时间戳
        mChart.setTime();

        function drawChart(object){
          //设置图表数据
          var dataShadow = [];
          for (var i = 0; i < object.data.length; i++) {
            dataShadow.push(object.yMax);
          }
          mChart.option = {
            xAxis: {
              data: object.dataAxis,
              axisLabel: {
                inside: false,
                interval:0,
                textStyle: {
                  color: '#000'
                }
              },
              axisTick: {
                show: false
              },
              axisLine: {
                show: false
              },
              z: 10
            },
            yAxis: {
              name:object.yName,
              nameLocation:'middle',
              nameGap:35,
              axisLine: {
                show: false
              },
              axisTick: {
                show: false
              },
              axisLabel: {
                textStyle: {
                  color: '#999'
                }
              }
            },
            dataZoom: [
              {
                type: 'inside'
              }
            ],
            series: [
              {
                type: 'bar',
                itemStyle: {
                  normal: {color: 'rgba(0,0,0,0.05)'}
                },
                barGap:'-100%',
                barCategoryGap:'40%',
                data: dataShadow,
                animation: false
              },
              {
                type: 'bar',
                itemStyle: {
                  normal: {
                    color: new echarts.graphic.LinearGradient(
                      0, 0, 0, 1,
                      [
                        {offset: 0, color: '#83bff6'},
                        {offset: 0.5, color: '#188df0'},
                        {offset: 1, color: '#188df0'}
                      ]
                    )
                  },
                  emphasis: {
                    color: new echarts.graphic.LinearGradient(
                      0, 0, 0, 1,
                      [
                        {offset: 0, color: '#2378f7'},
                        {offset: 0.7, color: '#2378f7'},
                        {offset: 1, color: '#83bff6'}
                      ]
                    )
                  }
                },
                data: object.data
              }
            ],
            grid:{
              top: '20'
            },
            tooltip:{
              trigger: 'item',
              formatter: "{b}:{c}%"
            }
          };

        };

      }
    })

})();
