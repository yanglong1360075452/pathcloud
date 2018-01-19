/**
 * Created by lenovo on 2016/5/13.
 */

(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('ShowImgController', ShowImgController);

  /** @ngInject */
  function ShowImgController($scope,$state,$uibModalInstance,imgUrl){
      $scope.imgUrl=imgUrl;

      // console.info($state)
      if($state.current.name === "app.embed" ){
        $scope.hideDelete = true;
      }

      $scope.cancel=function () {
        $uibModalInstance.close();

      };
      $scope.deletePhoto = function () {
        $uibModalInstance.close(true);
      }
  }
})();
