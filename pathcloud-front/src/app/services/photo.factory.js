/**
 * Created by lenovo on 2016/8/2.
 */
(function () {
  'use strict';

  angular
  .module('pathcloud')
  .factory('photoService', ['$http', '$q', '$uibModal', 'apiUrl', '$window', 'webServerUrl','$filter', function ($http, $q, $uibModal, apiUrl, $window, webServerUrl,$filter) {
    // var url = '[api]';
    var funs;

    var localPhotoPath = webServerUrl+'/'+ $filter('date')(new Date(),'yyyy_MM_dd');
    var localPhotos = []; //缓存一个本地照片的列表 当获取到新拍的照片才加进去
    if($window.location.hostname === "localhost"){
      localPhotoPath = webServerUrl
    }

    return funs = {
      clearLocalPhoto: function () {
        // 本地照片清空的话缓存里的本地照片应该也一起清空
        localPhotos = [];
        $window.open("cleanimgfile:","_self");
      },
      initStasticPath: function () {
        localPhotoPath = webServerUrl
      },
      initAutoPath: function () {
        localPhotoPath = webServerUrl+'/'+ $filter('date')(new Date(),'yyyy_MM_dd');
        if($window.location.hostname === "localhost"){
          localPhotoPath = webServerUrl
        }
      },
      getBase64: function (url) {
        var deferred = $q.defer();
        var img = new Image();
        img.setAttribute('crossOrigin', 'anonymous');
        img.src = url;
        img.onload = function(res) {

          var canvas = document.createElement("canvas");
          canvas.width = 1200;
          canvas.height = 800;

          var ctx = canvas.getContext("2d");
          ctx.drawImage(this, 0, 0,1200,800);

          var dataURL = canvas.toDataURL("image/jpg");
          // console.info(dataURL)
          // var realData = window.atob(dataURL.split(',')[1]);
          // deferred.resolve(realData)
          deferred.resolve(dataURL.replace(/^data:image\/(png|jpg);base64,/, ""));

        };
        return deferred.promise;
      },
      enlargePhoto: function (url) {
        var modalInstance = $uibModal.open({
          templateUrl: 'app/site/drawMaterial/modal/showImg.html',
          controller: 'ShowImgController',
          size: 'lg',
          resolve: {
            imgUrl: function () {
              return url;
            }
          }
        });
        return modalInstance.result;
      },
      getLocalPhotoUrlList: function getHtml() {
        var deferred = $q.defer();

        var xhr = new XMLHttpRequest();
        xhr.open("GET", localPhotoPath, true);
        xhr.onload = function (res) {

          // debugger
          // console.log(xhr.responseText);
          var responseHtml = $(xhr.responseText); //根据日期目录获取本地照片名称
          var newPhotoUrls = [];

          responseHtml.find('a').each(function (index, item) {
            // if(index > 9) {return} //最新的照片是在最后面应该倒序
            var photoSrc = localPhotoPath+'/' + item.innerText;
            /*
            * localPhotos 当第一次获取本地的照片的时候后缓存到一个数组 localPhotos 里
            * 之后再抓取照片的时候 跟 localPhotos 比较判断是否是新拍的照片是新的就 push 进去
            *
            * */
            if(!localPhotos.length){
              newPhotoUrls.push(photoSrc);//抓取到的新的图片
              localPhotos.push(photoSrc); // 类似 http://localhost:8887/2017_12_6/artist4-7.jpg
            }else{
              if(localPhotos.indexOf(photoSrc) < 0){
                localPhotos.push(photoSrc); // 放到数组最后 超过10 就删除 第一个
                newPhotoUrls.push(photoSrc);//抓取到的新的图片
              }
              // // 保证本地加载的照片不超过10张 删除 第一个
              // if(localPhotos.length > 10 ){
              //   localPhotos.splice(0, 1)
              // }
            }

            /*if (index < 4) {
              var photoName = item.innerText;
              // photoUrls.push({
              //   url: localPhotoPath+photoName,
              // }) //得到本地图片的完整路径 并包装成对象的形式
              photoUrls.push(localPhotoPath+photoName) //得到本地图片的完整路径

              // if ($scope.picUrls.indexOf(photoName) < 0) {
              //   $scope.picUrls.unshift(photoName); //循环是最新的在最前面  把最新放到数组最后面
              //   this.loadPhoto(photoName).then(function (res) {
              //     deferred.resolve(base64); //加载图片base64
              //   });
              //
              // }
            }*/
          });

          deferred.resolve(newPhotoUrls); // 返回新的图片

        };

        xhr.send();

        return deferred.promise;
      },
      /*loadPhoto: function (url) {
       var deferred = $q.defer();
       var img = document.createElement('img');
       img.src = url;
       img.width = 120;
       img.setAttribute('crossOrigin', 'anonymous');
       img.onload = function () {
       var canvas = document.createElement("canvas");
       canvas.width = img.width;
       canvas.height = img.height;

       var ctx = canvas.getContext("2d");
       ctx.drawImage(img, 0, 0, img.width, img.height);

       var dataURL = canvas.toDataURL();
       // return dataURL

       // return dataURL.replace("data:image/png;base64,", "");
       deferred.resolve(dataURL);
       };
       return deferred.promise;
       },*/
    }// end functions

  }]);
})();
