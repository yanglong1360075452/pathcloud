/**
 * Created by lenovo on 2016/5/13.
 */
(function(){
    'use strict';
    angular
        .module('pathcloud')
        .controller('InputModalController', InputModalController);

    /** @ngInject */
    function InputModalController($scope,$uibModalInstance,modalTitle){
      var inputMod=this;
      inputMod.modalTitle=modalTitle;
      // inputMod.text=text;
      inputMod.enter = function (e) {
  
        var keyCode = window.event?e.keyCode:e.which;
        
        if(keyCode == 13){
          inputMod.ok()
        }
      };

      inputMod.ok=function(){
          $uibModalInstance.close(inputMod.text);
      };
      inputMod.cancel=function(){
          $uibModalInstance.dismiss();
      };

    }
})();
