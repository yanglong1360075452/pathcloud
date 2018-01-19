/**
 * Created by lenovo on 2016/8/2.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('videoService',['$http','$q','$uibModal','$log',function($http,$q,$uibModal){
      var url = '[api]';
      var mediaSource = new MediaSource();
      mediaSource.addEventListener('sourceopen', handleSourceOpen, false);
      var mediaRecorder;
      var recordedBlobs;
      var sourceBuffer;

      var constraints = {
        audio: true,
        video: true
      };
      //参数设置
      var gumVideo,recordedVideo,canvas,imgContainer;
      return{
        photo:function (width,height) {
          var canvas = document.createElement('canvas');
          canvas.width=width||1024;
          canvas.height=height||768;
          var context = canvas.getContext('2d');//创建context对象该对象拥有绘制路径、矩形、远行、图片、字符的方法
          // console.info("",gumVideo)
          context.drawImage(gumVideo,0,0,width||1024,height||768);//context.drawImage(img,x,y,width,height)在画布上定位图像，并规定图像的宽度和高度
          //以下开始编 数据
          var imgData = canvas.toDataURL("image/jpeg", 1);//直接作用于canvas对象，将当前画布内容转换成一张图片
          //将图像转换为base64数据
          // console.log(imgData.length);
          var base64Data = imgData.split(",")[1];
          // console.log(base64Data);
          return imgData;
        },
        startRecording:function () {

        },
        stopRecording:function () {

        },
        init:function (option) {
          // window.isSecureContext could be used for Chrome
          var isSecureOrigin = location.protocol === 'https:' ||
            location.host.indexOf('localhost')==0;
            // if (!isSecureOrigin) {
            //   alert('getUserMedia() must be run from a secure origin: HTTPS or localhost.' +
            //     '\n\nChanging protocol to HTTPS');
            //   location.protocol = 'HTTPS';
            // }else{
            gumVideo=document.querySelector(option.gumVideo);
            recordedVideo=document.querySelector(option.recordedVideo);
            // canvas=document.querySelector('option.canvas');
            imgContainer=document.querySelector(option.imgContainer);
            navigator.getUserMedia = navigator.getUserMedia ||
              navigator.webkitGetUserMedia ||
              navigator.mozGetUserMedia;
            navigator.getUserMedia(constraints, successCallback, errorCallback);
          // }
        },

        // ### 4.3 取材页面图片数据接口 GET  /api/grossing/{id}/file  病理ID
        showVideo:function(id,operation,tag){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/grossing/'+id+'/file/'+operation,
            params:tag
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

      };


      function handleSourceOpen(event) {
        console.log('MediaSource opened');
        sourceBuffer = mediaSource.addSourceBuffer('video/webm; codecs="vp8"');
        console.log('Source buffer: ', sourceBuffer);
      }

      function successCallback(stream) {
        console.log('getUserMedia() got stream: ', stream);
        window.stream = stream;
        if (window.URL) {
          gumVideo.src = window.URL.createObjectURL(stream);
        } else {
          gumVideo.src = stream;
        }
      }

      function errorCallback(error) {
        console.log('navigator.getUserMedia error: ', error);
      }

    }]);
})();

