/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SealingUseHistoryController', SealingUseHistoryController);

  /** @ngInject */
  function SealingUseHistoryController($scope,toolService,toastr,StatisticService,IhcService){
    var microcope = this;

    //默认初始筛选条件数据
    function init(){

      microcope.workData = [];//获取到的数据
      microcope.searchData = {
        type:2,//封片
        status:0,
        page:1,
        length:20
      };//查询条件
      microcope.status = [{name:"正常",code:0}/*,{name:"禁用",code:1},{name:"报修",code:2}*/,];


      var date = new Date();
      microcope.searchData.year=date.getFullYear();
      microcope.searchData.month=date.getMonth()+1;
      // 时间日期筛选部分 ===end===

      // // 冰冻预约表头
      microcope.tableHeaders=["封片机编号","使用人","使用时间","设备状态","备注"];

    }
    init();

    // 导出
    microcope.export=function () {
      if(!microcope.workData) return;
      toolService.export(microcope.searchData,"/instrumentTracking/export")
    };


    //获取工作量列表
    microcope.getWorkDataList = function(type){
      query();
    };
    microcope.getWorkDataList();

    function query() {

      console.info("microcope.searchData=====",microcope.searchData)

      IhcService.get("/instrumentTracking",microcope.searchData).then(function (res) {
        microcope.workData=res.data;
        microcope.total = res.total;
      })

    }
  }
})();
