//查看历史诊断弹窗
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('insertPhotoController', insertPhotoController);

  /** @ngInject */
  function insertPhotoController($uibModalInstance,$uibModal,pathId,$timeout,DiagnosisService,toastr,toolService){
    var insertPhoto = this;
    insertPhoto.imgUrlHeader = toolService.getImgHeader();
    insertPhoto.data={position:'1'};

    DiagnosisService.getMicroImages(pathId).then(function (result) {
      insertPhoto.imgList = result;
      console.warn(insertPhoto.imgList)
    });

    insertPhoto.perItemNum = 2; //每页图片数

    $timeout(function () {
      insertPhoto.containerWidth = $('#img-container').width();
      insertPhoto.imgStyle={width:insertPhoto.containerWidth/insertPhoto.perItemNum+'px',padding:'0 5px'};
    });

    insertPhoto.openDrawModal=function () {
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/diagnosis/modal/drawPhoto/drawPhoto.html',
        controller: 'drawPhotoController',
        controllerAs: 'drawPhoto',
        resolve:{
          imgList:function(){
            return insertPhoto.imgList;
          }
        }
      });
      modalInstance.result.then(function(){

      });
    };

    function getSmallImg(src,width,height,scale,cb) {

      var image = new Image();
      image.crossOrigin = "anonymous";

      image.onload = function () {
        var canvas = document.createElement('CANVAS'),
          ctx = canvas.getContext('2d');
        var w = image.naturalWidth,
          h = image.naturalHeight;
        canvas.width = width;
        canvas.height = height;

        ctx.drawImage(image, 0, 0, w, h, 0, 0, width, height);

        cb(canvas.toDataURL('image/jpeg',(scale||0.9)));

      };

      image.src = src;

    }

    //确定P17000293
    insertPhoto.ok = function(){
      var imgDataList = [];
      // $('#img-container img').each(function (index,elem) {
      //   getSmallImg(elem,100,80,0.9,function (imgData) {
      //     imgDataList.push(imgData)
      //   })
      //
      // });

      var times = insertPhoto.imgList.length;
      var index = 0;

      function loopGetImg() {
        var url = "";
        var item = insertPhoto.imgList[index];
        if(item&&item.checked){
          if(item.url.length<1000){
            url = insertPhoto.imgUrlHeader+item.url+"?temp="+Date.now();
          }else{
            url = item.url;
          }
          getSmallImg(url,100,80,0.9,function (imgData) {
            imgDataList.push(imgData);
            judgeEnd();
          });
        }else {
          judgeEnd();
        }

        function judgeEnd() {
          if(++index<times){
            loopGetImg();
          }else{
            insertPhoto.data.imgList=imgDataList;

            $uibModalInstance.close(insertPhoto.data);
          }
        }

      }
      loopGetImg();

    };


    //取消
    insertPhoto.cancel = function(){
      $uibModalInstance.dismiss();
    };

  }
})();
