
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('AdjustController', AdjustHistoryController);

  /** @ngInject */
  function AdjustHistoryController($uibModalInstance,data,IhcService,toastr){
    var adjust= this;
    adjust.data = data;
    //调整库存
    adjust.ok = function(){
      var data = {
        id:adjust.data.id,
        adjust:adjust.adjust||'',
        note:adjust.note||''
      };
      IhcService.post('/store/record',data).then(function(){
        toastr.success("操作成功");
        $uibModalInstance.close();
      },function(err){
        toastr.error(err.reason);
      });
    };
    adjust.cancel = function(){
      $uibModalInstance.dismiss();
    };
  }
})();
