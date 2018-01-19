/**
 * Created by lenovo on 2016/5/13.
 */
(function(){
    'use strict';
    angular
        .module('pathcloud')
        .controller('CancelCreateMaterialModalController', CancelCreateMaterialModalController);

    /** @ngInject */
    function CancelCreateMaterialModalController($scope,$uibModalInstance,resolveData,paramSettingService,toastr){
      var textMod=this;

      textMod.modalTitle=resolveData.modalTitle;
      textMod.selectTitle=resolveData.selectTitle;
      textMod.warning=resolveData.warning;
      textMod.textareaTtitle=resolveData.textareaTtitle;

      textMod.ok=function(){
        $uibModalInstance.close(textMod.rejectReason);
      };
      textMod.cancel=function(){
        $uibModalInstance.dismiss();
      };

    }
})();
