
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('SealingController', SealingInstrumentController);

  /** @ngInject */
  function SealingInstrumentController($uibModalInstance){
    var sealing = this;
    sealing.ok = function(type){
      $uibModalInstance.close(type);
    };
  }
})();
