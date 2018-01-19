/**
 * Created by lenovo on 2016/5/13.
 */
(function(){
    'use strict';
    angular
        .module('pathcloud')
        .controller('ReasonModalController', ReasonModalController);

    /** @ngInject */
    function ReasonModalController($scope,$uibModalInstance,data,toastr){

      var reasonMod=this;

      reasonMod.modal={
        header:data.header||"撤销记录",
        timeLabel:data.timeLabel||"撤销时间",
        time:data.time||"",
        operatorLabel:data.operatorLabel||"撤销人",
        operator:data.operator||"",
        reasonLabel:data.reasonLabel||"撤销原因",
        reason:data.reason||""
      };

      console.info(reasonMod.modal)

      reasonMod.ok=function(){
        $uibModalInstance.close(reasonMod.rejectReason);
      };
      reasonMod.cancel=function(){
        $uibModalInstance.dismiss();
      };

    }
})();
