/**
 * Created by zhanming on 16/4/12.
 * 父级作用域
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('report', report);

  /** @ngInject */
  function report() {
    var directive = {
      restrict: 'E',
      scope: {
        reportData:"=",

      },//其默认情况下就是false 表示继承关系
      templateUrl: 'app/components/report/report.html',
      //replace:true,

      controller: ReportDirectiveController,
      controllerAs: "reportInfo",
      bindToController:true
    };

    return directive;

    /** @ngInject */

    function ReportDirectiveController($scope,$rootScope,$state,$timeout,printerService,toastr,DiagnosisService) {
      console.log("$scope.data:=====",$scope)

      var reportInfo = this;
      DiagnosisService.getReportTemplate().then(function (res) {
        $scope.reportTemplate=res[0];
      });//进入页面自动获取报告模板内容
      
      console.log("$scope.data:=====",reportInfo.reportData)



      // DiagnosisService.getReportInfo(reportInfo.pathId).then(function (res) {
      //   console.log("病理报告信息：",res)
      //   $scope.reportData=res;
      // })


    }
  }
})();
