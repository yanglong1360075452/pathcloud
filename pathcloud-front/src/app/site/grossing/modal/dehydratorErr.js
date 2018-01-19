/**
 * Created by lenovo on 2016/5/13.
 */

(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('DehydratorErrController', DehydratorErrController);

  /** @ngInject */
  function DehydratorErrController($scope,$uibModalInstance,GrossingService,instrumentId,toastr){
    var dehydratorErr=this;

    // Param | Type | Description instrumentId | Long | 设备ID startTime | Long | 开始时间 endTime | Long | 结束时间 page | Integer | 页数 length | Integer | 每页记录数


    dehydratorErr.filterData={
      instrumentId:instrumentId,// | Long | 设备ID
      // startTime: //| Long | 开始时间
      // endTime: //| Long | 结束时间
      length:10, //| Integer | 每页记录数
      page:1, //| Integer | 页数
    }



    //获取报警信息入口
    dehydratorErr.getErrmsg=function () {
      if (dehydratorErr.receiveStartTime)
        dehydratorErr.filterData.startTime = new Date(dehydratorErr.receiveStartTime).getTime();
      if (dehydratorErr.receiveEndTime)
        dehydratorErr.filterData.endTime = new Date(dehydratorErr.receiveEndTime).getTime() + 86400000;

      GrossingService.getDehydratorError(instrumentId,dehydratorErr.filterData).then(function (errmsg) {
        dehydratorErr.errmsg=errmsg;
        //console.log("dehydratorErr.errmsg",dehydratorErr.errmsg)
      });
    }
    dehydratorErr.getErrmsg();

    //todo delete
    // dehydratorErr.errmsg=[
    //   {
    //     "instrumentId": 1,
    //     "name": "2",
    //     "sn": "xxx",
    //     "errMsg": "zzz",
    //     "occurTime": 127371828,
    //   }
    // ];
    // 接收日期
    $scope.dateOptions1 = {
      dateDisabled: disabled1,
      maxDate: new Date()
    };
    $scope.dateOptions2 = {
      dateDisabled: disabled2,
      maxDate: new Date()
    };

    function disabled1(data) {
      if (!dehydratorErr.receiveEndTime) return;
      var date = data.date,
        mode = data.mode;

      return mode === 'day' && (date.getTime() > dehydratorErr.receiveEndTime);
    }

    function disabled2(data) {
      if (!dehydratorErr.receiveStartTime) return;

      var date = data.date,
        mode = data.mode;

      return mode === 'day' && (date.getTime() < dehydratorErr.receiveStartTime);
      // return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
    }

    dehydratorErr.ok=function(){
      GrossingService.removealarm(instrumentId).then(
        function () {
          toastr.success("解除报警成功")
          $uibModalInstance.close();

        },function (err) {
          toastr.error(err);
        }
      )
    };
    dehydratorErr.cancel=function(){
      $uibModalInstance.dismiss();
    };

  }
})();
