
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('DyeController', DyeInstrumentController);

  /** @ngInject */
  function DyeInstrumentController($uibModalInstance){
    var dye = this;
    dye.ok = function(type){
      $uibModalInstance.close(type);
    };
  }
})();
