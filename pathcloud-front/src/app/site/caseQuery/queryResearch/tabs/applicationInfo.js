(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('QueryResearchApplicationController',QueryResearchApplicationController );

  /** @ngInject */
  function QueryResearchApplicationController($scope,ApplicationService,$rootScope,$timeout,IhcService){

    var QueryA = this;

    console.log("$parent=-============",$scope)
    $scope.showOperation=false;
    $scope.hideSubmit=true;
    $scope.readonly=true;

    console.info("==========$rootScope.activeQueryOne==========",$rootScope.activeQueryOne)

    ApplicationService.getOneByPathologyNo($rootScope.activeQueryOne.pathNo).then(function (res) {
      console.log("科研申请表=-============",$scope)

      // $scope.ihcForm.data=res.research;// 通过$scope 给指令的controller传值 指令上绑定的值有问题

      $timeout(function(){
        // $scope.pathologicalFreeze = {};
        $scope.pathologicalFreeze.data=res.research;
        // console.info($scope.pathologicalFreeze,ApplicationData.applyType)
        $scope.pathologicalFreeze.data.departmentsDesc=res.departmentsDesc;
        $scope.pathologicalFreeze.data.samples=res.samples;
        $scope.pathologicalFreeze.data.id=res.id;

      },0)


      $scope.applicationData=res;
    });

    // ### sprint7 3-22 5.3 根据病理ID获取特染申请信息   /api/query/specialDye/{pathId}
    IhcService.get("/query/specialDye/"+$rootScope.activeQueryOne.id).then(function (res) {
      QueryA.specialDyeApplyInfo=res;
    })



  }
})();
