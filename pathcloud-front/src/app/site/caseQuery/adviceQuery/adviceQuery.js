(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('adviceQueryController',adviceQueryController )

  /** @ngInject */
  function adviceQueryController(QueryService,$timeout,IhcService,toastr,toolService){

    var adviceQuery = this;

    function init(){
      adviceQuery.tableHeaders=[{name:"病理号",order:1},{name:"蜡块号"},{name:"病人姓名",order:3},{name:"医嘱类别"},{name:"申请医生"},{name:"申请日期",order:6},{name:"状态"}];
      adviceQuery.defaultTime = 1;
      adviceQuery.filterData={
        page:1,
        length:20,
        filter: adviceQuery.searchStr,
        createTimeStart : adviceQuery.startTime,
        createTimeEnd : adviceQuery.endTime
      };
      adviceQuery.applyTypeList=[
        {name:"深切",code:17},
        {name:"重补取",code:19},
        {name:"特检",code:30}
      ];
      adviceQuery.statusList = [
        {name:"待诊断",code:1},
      ]

      QueryService.diagnoseDoctorList().then(function (result) {
        adviceQuery.inspectionDoctorList = result;
      });

      adviceQuery.query();
    }

    adviceQuery.search = function () {
      if(!adviceQuery.searchStr){
        adviceQuery.defaultTime=1;//没有搜索内容的时候重置筛选条件

      }else{
        adviceQuery.defaultTime=5;//搜索时默认时间改成半年
      }

      adviceQuery.filterData = {
        filter:adviceQuery.searchStr,
        page:1,
        length:20,
      };
      adviceQuery.getDataList()
    };

    adviceQuery.query = function () {
      delete adviceQuery.filterData.filter;
      adviceQuery.getDataList()
    };

    adviceQuery.getDataList = function () {
      $timeout(function () {
        adviceQuery.filterData.applyTimeStart = adviceQuery.startTime;
        adviceQuery.filterData.applyTimeEnd = adviceQuery.endTime;
        IhcService.get("/query/advice",adviceQuery.filterData)
        .then(function (result) {
          adviceQuery.info = {}
          adviceQuery.list = result.data;
          adviceQuery.total = result.total;
        })
      })
    };

    adviceQuery.getSortList = function (item) {
      if(!item.order) return;

      item.sort==='asc'?item.sort='desc':item.sort='asc';
      adviceQuery.filterData.sort = item.sort;
      adviceQuery.filterData.order = item.order;
      adviceQuery.getDataList();
    };

    adviceQuery.submit = function () {

      IhcService.post("/ihc/confirm/"+adviceQuery.info.id)
        .then(function (result) {
          adviceQuery.query();

          toastr.success("特染确认成功");
        },function (err) {
          toastr.error(err.reason)
          adviceQuery.info = null;

        })
    };


    adviceQuery.getDetail =function (item) {
      IhcService.get("/query/advice/"+item.id,{blockId:item.blockId})
        .then(function (result) {
          adviceQuery.queryResultList = result
        })
    };



    init();


  }//end


})();
