/**
 * Created by lenovo on 2016/5/13.
 */
(function(){
    'use strict';
    angular
        .module('pathcloud')
        .controller('TextareaModalController', TextareaModalController);

    /** @ngInject */
    function TextareaModalController($scope,$uibModalInstance,resolveData,paramSettingService,toastr){
      var textMod=this;
      textMod.modalTitle=resolveData.modalTitle;
      textMod.selectTitle=resolveData.selectTitle;
      textMod.textareaTtitle=resolveData.textareaTtitle;
      textMod.rejectReason={};//拒绝原因
      console.error(resolveData)

      paramSettingService.rejectReason().then(function (res) {
        textMod.rejectReasonLIst=res;
      },function (err) {
        toastr.error(err);
      })//拒绝原因列表

      textMod.ok=function(){
        
        $uibModalInstance.close(textMod.rejectReason);
      };
      textMod.cancel=function(){
        $uibModalInstance.dismiss();
      };

    }
})();
