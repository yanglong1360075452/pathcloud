(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('CommonModalController',CommonModalController);

  /** @ngInject */
  function CommonModalController($uibModalInstance,modalTitle,modalContent){

    var commonModal = this;
    commonModal.Title = modalTitle;
    commonModal.Content = modalContent;

    //确定按钮
    commonModal.ok = function (){
      $uibModalInstance.close();

    }

    //取消按钮
    commonModal.cancel = function(){
      $uibModalInstance.dismiss();
    }
  }//end CommonModalController
})();
