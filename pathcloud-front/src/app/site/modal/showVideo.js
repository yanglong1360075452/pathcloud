/**
 * Created by lenovo on 2016/5/13.
 */

(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('ShowVideoController', ShowVideoController);

  /** @ngInject */
  function ShowVideoController($scope,$state,$uibModalInstance,videoService,toolService,$timeout,position,pathologicalId){

    // console.info("路由",$state.current.name);

    var option = { gumVideo:"#Video" };
    $timeout(function () {
      videoService.init(option);
    })

    if($state.current.name === "app.confirmMaterial" ){ //取材确认页面拍照
      $scope.photo = function(){
        var imgUrl=videoService.photo();

        $uibModalInstance.close(imgUrl);

        /*// var photoUrl = imgUrl;//页面显示的地址
        // var base64 = imgUrl.split(",")[1];
        // var basketStr = pathologicalId;

        /!*!// 取材确认图片上传 sprint13 /api/file/grossingConfirm/{basketNum}
        toolService.fileUpload(base64,"grossingConfirm",basketStr).then(function () {

        },function () {
          toastr.error("照片上传失败")
        })*!/

        // var imgUrl = videoService.photo(538,389);
        // toolService.fileUpload(imgUrl.split(",")[1],position,pathologicalId)*/
      }
    }else{
      $scope.photo = function(){
        var imgUrl = videoService.photo(538,389);
        toolService.fileUpload(imgUrl.split(",")[1],position,pathologicalId)
      }
    }



    $scope.cancel=function () {
      $uibModalInstance.close();

    }

  }
})();
