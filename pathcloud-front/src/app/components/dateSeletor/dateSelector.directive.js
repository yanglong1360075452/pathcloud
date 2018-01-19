(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('dateSeletor', dateSeletor);

  /** @ngInject */
  function dateSeletor() {
    var directive = {
      restrict: 'E',
      scope: {
        startTime: '=',
        endTime: '=',
        type: '@',
        dateset: '=',
        change:'&'
      },
      template:
      "<select class='form-control input-sm' ng-model='vm.dateset' ng-options='item.code as item.name for item in vm.dateList' ng-change='vm.change()'></select>" ,
      controller: DateSeletorController,
      controllerAs: 'vm',
      bindToController:true,
    };
    return directive;

    /** @ngInject */
    function DateSeletorController($scope,SystemSettingService,T) {
      var vm=this;
      // vm.dateset=parseInt(vm.defaultTime);
      // alert(vm.dateset)
      var d = new Date();
      // vm.startTime=new Date().setDate(d.getDate()-1);//设置筛选的初始时间
      // vm.endTime=Date.now();//设置筛选的结束时间  不传结束时间就是默认当前的时间
      // console.log(vm.dateset,vm.defaultTime)
      
  
      var year = d.getFullYear();
      var month = d.getMonth();
      var today = d.getDate();
  
      var lastDay = new Date(year, month, today).getTime();
      var lastTwoDay = new Date(year, month, today-1).getTime();
      var lastThreeDay = new Date(year, month, today-2).getTime();
      var lastWeek =  new Date(year, month, today-6).getTime();
      var lastMonth =  new Date(year, month-1, today).getTime();
      var lastThreeMonth =  new Date(year, month-3, today).getTime();
      var lastSixMonth =  new Date(year, month-6, today).getTime();
      var lastYear =  new Date(year-1, month, today).getTime();
      var lastThreeYear =  new Date(year-3, month, today).getTime();
      var lastFiveYear =  new Date(year-5, month, today).getTime();
  
      var timeRange = lastSixMonth;  //最大的时间范围 默认是6 个月
      
      vm.dateList=[
        {name:T.T("最近1天"), code:1 },
        {name:T.T("最近2天"), code:2 },
        {name:T.T("最近3天"), code:3 },
        {name:T.T("最近1周"), code:4 },
        {name:T.T("最近1个月"), code:6 },
        {name:T.T("最近3个月"), code:7 },
        {name:T.T("最近6个月"), code:5 } //默认5 是最大的时间范围  在其它页面已经是这么使用了 不能轻易调整
      ];

      //调接口判断最大的时间范围
      SystemSettingService.getTimeRange().then(function (res) {
        angular.forEach(res,function (item,index,arr) {
          if(item.checked){
            vm.dateList[6].name=T.T(item.name);  //动态获取最大时间范围
  
            switch (item.code){
              case 1:
                timeRange= lastSixMonth; //6个月内
                break;
              case 2:
                timeRange= lastYear; //1年内
                break;
              case 3:
                timeRange= lastThreeYear; //3年内
                break;
              case 4:
                timeRange= lastFiveYear; //5年内
                break;
              default:
                timeRange;
                break;
            }

            // 根据code 判断最大是时间是多少
            /*switch (item.code){
              case 1:
                timeRange=new Date().setMonth(d.getMonth()-6); //6个月内
                break;
              case 2:
                timeRange=new Date().setYear(d.getFullYear()-1); //1年内
                break;
              case 3:
                timeRange=new Date().setYear(d.getFullYear()-3); //3年内
                break;
              case 4:
                timeRange=new Date().setYear(d.getFullYear()-5); //5年内
                break;
              default:
                timeRange=new Date().setMonth(d.getMonth()-6);
                break;
            }*/
          }
        })
      });


      // 用wacth代替ng-change ng-change反应不够及时 点击选择之后才会执行change方法
      $scope.$watch(function() { return vm.dateset }, function (newValue,oldValue) {
        if(newValue){
          switch (newValue){
            case 1:
              vm.startTime = lastDay; //最近一天  当天0点 到现在
              break;
            case 2:
              vm.startTime = lastTwoDay; //最近两天
              break;
            case 3:
              vm.startTime = lastThreeDay;  //最近三天
              break;
            case 4:
              vm.startTime = lastWeek;  //最近一周
              break;
            case 6:
              vm.startTime = lastMonth;
              break;
            case 7:
              vm.startTime = lastThreeMonth;
              break;
            case 5:
              vm.startTime=timeRange; //动态获取最大的时间范围
              break;
            default:
              vm.startTime = lastDay;
              break;
          }
          
          /*switch (newValue){
            case 1:
              vm.startTime=new Date().setDate(d.getDate()-1);
              break;
            case 2:
              vm.startTime=new Date().setDate(d.getDate()-7);
              break;
            case 3:
              vm.startTime=new Date().setMonth(d.getMonth()-1);
              break;
            case 4:
              vm.startTime=new Date().setMonth(d.getMonth()-3);
              break;
            case 5:
              vm.startTime=timeRange; //动态获取最大的时间范围
              break;
            default:
              vm.startTime=new Date().setDate(d.getDate()-1);
              break;
          }*/
          // alert(newValue,oldValue)
        }
      })

      // 当用$watch监控dateset 的时候change方法不需要了
      /*vm.changeDate=function (dateSet,fn) {
        switch (dateSet){
          case 1:
            vm.startTime=new Date().setDate(d.getDate()-1);
            break;
          case 2:
            vm.startTime=new Date().setDate(d.getDate()-7);
            break;
          case 3:
            vm.startTime=new Date().setMonth(d.getMonth()-1);
            break;
          case 4:
            vm.startTime=new Date().setMonth(d.getMonth()-3);
            break;
          case 5:
            vm.startTime=new Date().setMonth(d.getMonth()-6);
            break;
          default:
            vm.startTime=new Date().setDate(d.getDate()-1);
            break;
        };
        // vm.getData();

      }*/

      // alert(vm.dateset)
      // vm.changeDate(vm.dateset);
      // alert(vm.startTime)
    };


  }

})();
