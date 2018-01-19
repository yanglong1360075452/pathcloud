(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('IhcCreateController',IhcCreateController )

  /** @ngInject */
  function IhcCreateController($scope,$timeout,IhcService,toastr,toolService){

    var ihcCreate = this;

    function init(){
      ihcCreate.tableHeaders=[{name:"编号"},{name:"病理号",order:1},{name:"蜡块号"},{name:"特染类别"},{name:"申请人"},{name:"申请日期",order:5}];
      ihcCreate.defaultTime = 1;
      ihcCreate.filterData={
        page:1,
        length:20,
        filter: ihcCreate.searchStr,
        createTimeStart : ihcCreate.startTime,
        createTimeEnd : ihcCreate.endTime
      };

      ihcCreate.query();

    }

    ihcCreate.exportExl = function () {
      toolService.export(ihcCreate.filterData,"/ihc/export")
    };

    ihcCreate.search = function () {
      if(!ihcCreate.searchStr){
        ihcCreate.defaultTime=1;//没有搜索内容的时候重置筛选条件
      }else{
        ihcCreate.defaultTime=5;//搜索时默认时间改成半年
      }
      ihcCreate.filterData={
        filter:ihcCreate.searchStr,
      };
      ihcCreate.getData();
    };

    ihcCreate.query = function () {
      delete ihcCreate.filterData.filter;
      ihcCreate.getData();
    };

    ihcCreate.getData = function () {
      $timeout(function () {
        ihcCreate.filterData.createTimeStart = ihcCreate.startTime;
        ihcCreate.filterData.createTimeEnd = ihcCreate.endTime;

        ihcCreate.filterData.length = 20;

        IhcService.get("/ihc",ihcCreate.filterData)
        .then(function (result) {
          ihcCreate.info = {};
          ihcCreate.list = result.data;
          ihcCreate.total = result.total;
        })
      });
    };

    ihcCreate.getSortList = function (item) {
      if(!item.order) return;

      item.sort==='asc'?item.sort='desc':item.sort='asc';
      ihcCreate.filterData.sort = item.sort;
      ihcCreate.filterData.order = item.order;
      ihcCreate.query();
    };

    ihcCreate.submit = function () {

      IhcService.post("/ihc/confirm/"+ihcCreate.info.id)
        .then(function (result) {
          ihcCreate.query();

          toastr.success("特染确认成功");
        },function (err) {
          toastr.error(err.reason);
          ihcCreate.info = null;

        })
    }

    init();


  }//end


})();
