(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('QueryApplicationController',QueryApplicationController );

  /** @ngInject */
  function QueryApplicationController($scope,ApplicationService,$rootScope,IhcService, $filter ){

    var QueryA = this;

    // console.log("临床$parent=-============",$scope)
    // $scope.showOperation=false;
    // $scope.hideSubmit=true;
    // $scope.readonly=true;

    //监听 从父controller传来的 事件
    $scope.$on('application', function(event,data, id) {
      $scope.application = data; //tab
      // console.log('ParentCtrl', data, id);	   //父级传的值

      // ### sprint7 3-22 5.3 根据病理ID获取特染申请信息   /api/query/specialDye/{pathId}
      // params specialDye =1 免疫组化 大于1就是特染
      var params = {specialDye: 1};
      if(data === 1){
        params.specialDye = 1;
      }else{
        params.specialDye = 2;
      }

      IhcService.get("/query/specialDye/"+id, params).then(function (res) {
        QueryA.specialDyeApplyInfo=res;
        // 通过filter 过滤 判断是否只显示 免疫组化的 或特染的数据
        // 除了免疫组化之外的都是特染
        QueryA.filter =  function(item) {
          if(data === 1){
            return item.specialDye == 1;
          } else{
            return item.specialDye > 1;
          }
        }

      })
    });

    //console.info("==========$rootScope.activeQueryOne==========",$rootScope.activeQueryOne)




  }
})();
