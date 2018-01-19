//查看历史诊断弹窗
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('cutPicController', cutPicController);

  /** @ngInject */
  function cutPicController($uibModalInstance,$timeout,urlData){
    var cutPic = this;

    console.log(urlData);
    $timeout(function () {
        var $imageCropper = $('.image-editor');
        $imageCropper.cropit({
          imageState: {
            src: urlData,
            zoom: 0.6
            //	offset: { x: -126.43, y: -251.73 }
          }
        });
        $('.select-image-btn').click(function() {
          $('.cropit-image-input').click();
        });

        $('.rotate-cw-btn').click(function() {
          $imageCropper.cropit('rotateCW');
        });

        $('.close').click(function () {
          cutPic.cancel();
        });
    });



    //确定
    cutPic.ok = function(){
      var $imageCropper = $('.image-editor');

      var src = $imageCropper.cropit(
        'export',{
          type: 'image/jpeg',
          quality: .75,
          originalSize: false
        }
      );
      $uibModalInstance.close(src);

    };

    //取消
    cutPic.cancel = function(){
      $uibModalInstance.dismiss();
    };

  }
})();
