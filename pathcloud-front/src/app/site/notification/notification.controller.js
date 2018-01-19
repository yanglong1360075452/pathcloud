(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('NotificationController', NotificationController);

  /** @ngInject */
  function NotificationController(toastr,notificationService,$rootScope,$uibModal) {
    var notification = this;

    notification.filter={
      handle:false,
      "length": 10,
      "page": 1
    }

    function init() {
      getNotificationLIst()
    };
    init();

    // 格式化等待时间


    notification.getDataList=function () {
      getNotificationLIst()
    };

    notification.deal=function () {

      $uibModal.open({
        templateUrl: 'app/site/caseQuery/modal/sampleError.html',
        size:'sample-error',
        // backdrop: false,
        controller: 'SampleErrorController',
        controllerAs: 'SampleE',
        resolve: {
          resolveData: function () {
            // console.info(notification.dealData);
            return notification.dealData;

            // {
            //   modalTitle:"异常处理",
            //   pathId:item.pathologyId,
            //   blockId:item.blockId,
            //   pathologyNumber:item.pathologyNumber,
            //   subId:item.subId,
            //   status:item.status,
            //   statusName:item.statusName
            // };
          }
        }
        // resolve: {
        //   resolveData:{
        //     pathologyNumber:"P201610001",
        //     subId:"A",
        //     statusName:"待脱水",
        //   }
        // }
      }).result.then(function () {
        init();
      })


    }; //处理

    // notification.activeItem;//表示选择的那个对象 并将获取的详情 绑定到该对象上
    notification.viewDetail=function (item) {
      notification.activeItem=item;
      item.readFlag=true;
      detail()
    };
    notification.waitingTime=function (time) {
      // var day = parseInt(date / (24*60*60));//计算整数天数
      // var afterDay = date - day*24*60*60;//取得算出天数后剩余的秒数
      // var hour = parseInt(afterDay/(60*60));//计算整数小时数
      // var afterHour = date - day*24*60*60 - hour*60*60;//取得算出小时数后剩余的秒数
      // var min = parseInt(afterHour/60);//计算整数分
      // var afterMin = date - day*24*60*60 - hour*60*60 - min*60;//取得算出分后剩余的秒数
      // console.log([day,hour,min,afterMin].join(':'));

      // var hour = parseInt(time / (24*60*60)*24*1000);//

      var hour = (time / 3600000).toFixed(2);//

      return hour+"小时";
    };//计算状态表的等待时间

    function getNotificationLIst() {
      notificationService.getNotificationLIst(notification.filter).then(function (res) {
        notification.notificationList=res;
      },function (err) {

      })
    };//每次重新获取通知数

    function getNotification(){
      notificationService.getNotificationTotal().then(
        function (res) {
          $rootScope.totalNotification=res;
          // console.info(res);
        },function (err) {

        }
      )
    }
    function detail() {
      notificationService.getNotificationDetial(notification.activeItem.id).then(function (res) {
        // console.info(1111)
        notification.activeItem.data=res;

        notification.dealData={
          modalTitle:"异常处理",
          pathId:res.pathologyId,
          blockId:res.blockId,
          pathologyNumber:res.pathologyNumber,
          subId:res.blockNumber,
          status:res.status,
          statusName:res.sourceName,
          position:"notification"
        };
        getNotification();
        // timer.then(getNotification);

      })
    }
  }
})();
