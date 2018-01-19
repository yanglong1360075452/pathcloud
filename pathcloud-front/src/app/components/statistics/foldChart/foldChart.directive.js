(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('foldChart',function (){
      return {
        restrict: 'E',
        templateUrl: 'app/components/statistics/foldChart/foldChart.html',
        scope: {
          chartTitle: '@',
          lineConfig:'=',
          getDataFun:'&'
        },
        controller: foldChartController,
        controllerAs: 'fChart',
        bindToController: true
      };

      /** @ngInject */
      function foldChartController(StatisticService,$q,T) {
        var fChart = this;
        fChart.data = {};

        //获取"病例数"数据
        fChart.getDataFun().then(
          function(data){
            drawChart(data);
          }
        );
        function drawChart(data){
          fChart.option = {
            tooltip : {
              trigger: 'axis'
            },
            legend: {
              data:[
                {name:T.T('常规'), textStyle:{color:'#5b9bd5'}},
                {name:T.T("特染"), textStyle:{color:'#ed7d31'}}
              ],
              top:10,
              right:10
            },
            toolbox: {
              feature: {
                saveAsImage: {}
              }
            },
            grid: {
              left: '4%',
              right: '4%',
              bottom: '3%',
              containLabel: true
            },
            xAxis : [
              {
                type : 'category',
                boundaryGap : false,
                data : [T.T('1月'),T.T('2月'),T.T('3月'),T.T('4月'),T.T('5月'),T.T('6月'),T.T('7月'),T.T('8月'),T.T('9月'),T.T('10月'),T.T('11月'),T.T('12月')]
              }
            ],
            yAxis : {
              name:T.T('样本量（病例）'),
              nameLocation:'middle',
              nameGap:35,
              type : 'value',
            },
            series : [
              {
                name:T.T('特染'),
                type:'line',
                stack: '总量',
                itemStyle : {
                  normal : {
                    color:'#ed7d31',
                    lineStyle:{
                      color:'#ed7d31'
                    }
                  }
                },
                data:data.specialData
              }
              ,{
                name:T.T('常规'),
                type:'line',
                stack: '总量',
                itemStyle : {
                  normal : {
                    color:'#5b9bd5',
                    lineStyle:{
                      color:'#5b9bd5'
                    }
                  }
                },
                data:data.normalData
              }
            ]
          };
        };

      }
    })

})();
