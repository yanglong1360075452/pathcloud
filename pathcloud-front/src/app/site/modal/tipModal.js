(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('TipModalController',TipModalController);

  /** @ngInject */
  function TipModalController($uibModalInstance,modalTitle,modalContent){

    var tipModal = this;
    tipModal.Title = modalTitle;
    tipModal.Content = modalContent;

    //取消按钮
    tipModal.cancel = function(){
      $uibModalInstance.dismiss();
    }
  }//end tipModalController
})();
