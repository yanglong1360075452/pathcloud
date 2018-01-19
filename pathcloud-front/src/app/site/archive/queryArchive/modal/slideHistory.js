(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SlideHistoryModalController',SlideHistoryModalController);

  /** @ngInject */
  function SlideHistoryModalController($uibModalInstance,toastr,historyData,slideData){

    var slideHistory = this;


    slideHistory.slideData = slideData;
    slideHistory.slideList = historyData;


    //取消按钮
    slideHistory.cancel = function(){
      $uibModalInstance.dismiss();
    }
  }//end CommonModalController
})();


