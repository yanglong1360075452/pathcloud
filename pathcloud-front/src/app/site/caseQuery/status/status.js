/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ProStatusController', ProStatusController);

  /** @ngInject */
  function ProStatusController($scope,$state,toastr,ProductService,$uibModal){
    var proStatus=this;

    proStatus.activeItem;//选中的 要处理 item
    proStatus.tableHeaders = [
      {name:"病理号"},{name:"蜡块号"},{name:"玻片号"},{name:"当前状态"},{name:"等待时间"},{name:"最后操作人"},{name:"最后操作时间"},{name:"处理"}
    ];

    // 样本状态统计


    proStatus.filter={
      // status:"",
      // errorTotal:"16",
      page:1,
      length:15

    };


    function init() {
      ProductService.statisticData().then(function (res) {
        proStatus.statisticDataList=res;
      });
    }
    init();

    proStatus.getOne=function (item) {
      proStatus.activeItem=item||{};
      proStatus.filter.totalError=item.errorTotal;//分页

      proStatus.changePage();
      // ProductService.abnormalDataList(proStatus.filter,item.status).then(function (res) {
      //   proStatus.abnormalDataList=res;
      //
      // });

    };
    proStatus.waitingTime=function (time) {
      var now = new Date().getTime()
      var date=(now-time)/1000;  //时间差的毫秒数
      // console.log(date)

      // var day = parseInt(date / (24*60*60));//计算整数天数
      // var afterDay = date - day*24*60*60;//取得算出天数后剩余的秒数
      // var hour = parseInt(afterDay/(60*60));//计算整数小时数
      // var afterHour = date - day*24*60*60 - hour*60*60;//取得算出小时数后剩余的秒数
      // var min = parseInt(afterHour/60);//计算整数分
      // var afterMin = date - day*24*60*60 - hour*60*60 - min*60;//取得算出分后剩余的秒数
      // console.log([day,hour,min,afterMin].join(':'));


      var hour = (date / 3600).toFixed(2);//;//
      return hour+"小时";
    };//计算状态表的等待时间
    proStatus.dealOne=function (item) {

      $uibModal.open({
        templateUrl: 'app/site/caseQuery/modal/sampleError.html',
        size:'sample-error',
        // backdrop: false,
        controller: 'SampleErrorController',
        controllerAs: 'SampleE',
        resolve: {
          resolveData: function () {
            return{
              modalTitle:"异常处理",
              pathId:item.pathologyId,
              blockId:item.blockId,
              pathologyNumber:item.pathologyNumber,
              subId:item.subId,
              status:item.status,
              statusName:item.statusName
            };
          }
        }

      }).result.then(function () {
        proStatus.changePage();
      })


    }; //处理
    proStatus.changePage=function () {
      ProductService.abnormalDataList(proStatus.filter,proStatus.activeItem.status).then(function (res) {
        proStatus.abnormalDataList=res;
        init();
      });
    }




  }
})();

