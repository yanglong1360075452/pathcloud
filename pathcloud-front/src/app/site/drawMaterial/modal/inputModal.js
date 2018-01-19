/**
 * Created by lenovo on 2016/5/13.
 */
(function(){
    'use strict';
    angular
        .module('pathcloud')
        .controller('MaterialInputModalController', MaterialInputModalController);

    /** @ngInject */
    function MaterialInputModalController($scope,$uibModalInstance,modal){
      var inputMod=this;
      inputMod.modalTitle = modal.modalTitle;
  
      inputMod.biaoshiList = modal.biaoshiList;
      inputMod.biaoshi = modal.biaoshi;
      inputMod.biaoshiDisabled = modal.biaoshiDisabled;
      
      // 当病理同时有重补取 跟冰冻标识的时候
      
      // inputMod.text=text;
      inputMod.enter = function (e) {
  
        var keyCode = window.event?e.keyCode:e.which;
        
        if(keyCode == 13){
          inputMod.ok()
        }
      };

      inputMod.ok=function(){
          $uibModalInstance.close({
            total:inputMod.text,
            biaoshi: inputMod.biaoshi
          });
      };
      inputMod.cancel=function(){
          $uibModalInstance.dismiss();
      };

    }
})();
