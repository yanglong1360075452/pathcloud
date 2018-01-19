/**
 * Created by lenovo on 2016/5/13.
 */

(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('LostGrossingBlocksController', LostGrossingBlocksController);

  /** @ngInject */
  function LostGrossingBlocksController($scope,$state,$uibModalInstance,GrossingService,data,toastr){
    var lostBlocks=this;

    // Param | Type | Description instrumentId | Long | 设备ID startTime | Long | 开始时间 endTime | Long | 结束时间 page | Integer | 页数 length | Integer | 每页记录数


    lostBlocks.lostBlocks = data.blocks; //data 里传 要脱水确认的数据

    lostBlocks.ok=function(){
      GrossingService.startDehydrate(data.data).then(
        function () {
          toastr.success("开始脱水")
          $uibModalInstance.close();
          $state.go('app.grossing',{},{reload:true})//脱水成功后刷新页面

        },function (err) {
          toastr.error(err);
        }
      )
    };
    lostBlocks.cancel=function(){
      $uibModalInstance.dismiss();
    };

  }
})();
