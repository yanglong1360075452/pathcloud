
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('EditController', EditHistoryController);

  /** @ngInject */
  function EditHistoryController($uibModalInstance,data,IhcService,toastr,$scope,ReagentService){
    var edit= this;
    edit.statusList = [{code:1, name:"合格"},{code:2, name:"破损"}];
    edit.liquidType = [{code:2,name:'浓缩液'}];
    edit.countType = [{code:2,name:'盒'}];

    //数据初始
    edit.data = angular.copy(data);
    // console.log(data);
    //日期
    $scope.dateOptions1 = {
      dateDisabled: disabled1,
      maxDate: new Date()
    };
    $scope.dateOptions2 = {
      dateDisabled: disabled2
    };
    function disabled1(data) {
      if (!edit.data.expiryTime) return;
      var date = data.date,
        mode = data.mode;

      return mode === 'day' && (date.getTime() > edit.data.expiryTime);
    }
    function disabled2(data) {
      if (!edit.data.produceTime) return;

      var date = data.date,
        mode = data.mode;

      return mode === 'day' && (date.getTime() < edit.data.produceTime);
    }
    $scope.dateOptions3 = {
      dateDisabled: disabled3,
      maxDate: new Date()
    };
    $scope.dateOptions4 = {
      dateDisabled: disabled4
    };
    function disabled3(data) {
      if (!edit.data.preExperimentTime) return;
      var date = data.date,
        mode = data.mode;

      return mode === 'day' && (date.getTime() > edit.data.preExperimentTime);
    }
    function disabled4(data) {
      if (!edit.data.preparationTime) return;

      var date = data.date,
        mode = data.mode;

      return mode === 'day' && (date.getTime() < edit.data.preparationTime);
    }


    //通过试剂名称获得基本信息
    var dataName = {
      name:edit.data.reagentName
    };
    ReagentService.checkName(dataName).then(function (res) {
      edit.basic = res;
    });
    edit.cancel = function(){
      $uibModalInstance.dismiss();
    };
    //修改库存信息
    edit.ok = function(){
      var data ={
        id:edit.data.id,
        reagentId:edit.data.reagentId,
        orderNumber:edit.data.orderNumber,
        batchNumber:edit.data.batchNumber,
        productNumber:edit.data.productNumber,
        manufacturer:edit.data.manufacturer,
        articleNumber:edit.data.articleNumber,
        produceTime:edit.data.produceTime,
        expiryTime:edit.data.expiryTime,
        manufacturerPhone:edit.data.manufacturerPhone,
        spec:edit.data.spec,
        receiveStatus:edit.data.receiveStatus,
        preparation:edit.data.preparation,
        preparationTime:edit.data.preparationTime,
        preExperiment:edit.data.preExperiment,
        preExperimentTime:edit.data.preExperimentTime,
        preExperimentResult:edit.data.preExperimentResult,
        specification:edit.data.specification,
        type:edit.data.type,
        dilutionRateFront:edit.data.dilutionRateFront,
        dilutionRateRear:edit.data.dilutionRateRear,
        realCapacity:edit.data.realCapacity,
        count:edit.data.count,
        countUnit:edit.data.countUnit
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
