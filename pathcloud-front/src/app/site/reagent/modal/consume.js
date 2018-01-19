
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('ConsumeController', cousumeCounsumablesController);

  /** @ngInject */
  function cousumeCounsumablesController($uibModalInstance,data,$scope,IhcService,toastr){
    var consume= this;
    consume.categoryList= [{code:10, name:"包埋盒"},{code:11, name:"常规玻片"},{code:12, name:"防脱玻片"}];
    consume.countType = [{code:2,name:'盒'}];
    consume.data = angular.copy(data);
    //日期设置
    $scope.dateOptions1 = {
      dateDisabled: disabled1,
      maxDate: new Date()
    };
    $scope.dateOptions2 = {
      dateDisabled: disabled2
    };
    function disabled1(data) {
      if (!consume.data.expiryTime) return;
      var date = data.date,
        mode = data.mode;

      return mode === 'day' && (date.getTime() > consume.data.expiryTime);
    }
    function disabled2(data) {
      if (!consume.data.produceTime) return;

      var date = data.date,
        mode = data.mode;

      return mode === 'day' && (date.getTime() < consume.data.produceTime);
    }


    consume.cancel = function(){
      $uibModalInstance.dismiss();
    };
    //修改库存
    consume.ok = function(){
      var data ={
        id:consume.data.id,
        reagentId:consume.data.reagentId,
        orderNumber:consume.data.orderNumber,
        batchNumber:consume.data.batchNumber,
        productNumber:consume.data.productNumber,
        manufacturer:consume.data.manufacturer,
        articleNumber:consume.data.articleNumber,
        produceTime:consume.data.produceTime,
        expiryTime:consume.data.expiryTime,
        manufacturerPhone:consume.data.manufacturerPhone,
        spec:'',
        receiveStatus:'',
        preparation:'',
        preparationTime:'',
        preExperiment:'',
        preExperimentTime:'',
        preExperimentResult:'',
        specification:consume.data.specification,
        type:'',
        dilutionRateFront:'',
        dilutionRateRear:'',
        realCapacity:'',
        count:consume.data.count,
        countUnit:consume.data.countUnit
      };
      IhcService.put('/store',data).then(function(res){
        toastr.success("操作成功");
        $uibModalInstance.close();
      },function(err){
        toastr.error(err.reason);
      });
    };
  }
})();
