/**
 * Created by lenovo on 2016/5/13.
 */

(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('LostDyeSlidesController', LostDyeSlidesController);

  /** @ngInject */
  function LostDyeSlidesController($scope,$state,$uibModalInstance,DyeService,data,toastr){
    var lostSlides=this;


    lostSlides.lostSlides = data.lostSlides;
    lostSlides.confirmText = data.text.confirmText;
    lostSlides.cancelText = data.text.cancelText;

    lostSlides.ok=function(){
      $uibModalInstance.close();
    };
    lostSlides.cancel=function(){
      $uibModalInstance.dismiss();
    };

  }
})();
