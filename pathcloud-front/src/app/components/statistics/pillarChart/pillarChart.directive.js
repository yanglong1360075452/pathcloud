(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('pillarChart',function (){
      return {
        restrict: 'E',
        templateUrl: 'app/components/statistics/pillarChart/pillarChart.html',
        scope: {
          chartTitle: '@',
          lineConfig:'=',
          lineStyle:'=',
          getDataFun:'&'
        },
        controller: pillarChartController,
        controllerAs: 'pillarChart',
        bindToController: true
      };

      /** @ngInject */
      function pillarChartController($scope,StatisticService,$timeout) {
        var pillarChart = this;

        pillarChart.getDataFun().then(function(data){
          //console.warn("data",data);
          drawChart(data);
        });

        function drawChart(object){
          //设置图表数据
          var dataShadow = [];
          for (var i = 0; i < object.data.length; i++) {
            dataShadow.push(object.yMax);
          }
          pillarChart.option = {
            xAxis: {
              data: object.dataAxis,
              axisLabel: {
                inside: false,
                interval:0,//'auto'（自动隐藏显示不下的） | 0（全部显示） |{number}（用户指定选择间隔）
                textStyle: {
                  color: '#000'
                },
                rotate:object.rotate
              },
              axisTick: {
                show: false,
                length:10
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
              { // For shadow
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
              formatter: "{b}:{c}"
            }
          };

        }

      }
    })

})();
