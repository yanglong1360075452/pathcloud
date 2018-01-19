/**
 * Created by Administrator on 2017/6/7 sprint12 API
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('QuerySlideController', QuerySlideController);

  /** @ngInject */
  function QuerySlideController($scope,toastr,$uibModal,IhcService) {
    var querySlide=this;

    //设置表格头部
    querySlide.tableHeaders = [
      {name:"病理号",order:1},{name:"蜡块号"},{name:"玻片号"},{name:"试剂抗体"},{name:"患者姓名",order:5},{name:"晾片架编号",order:6},{name:"晾片技术员"},
      {name:"晾片日期",order:8},{name:"抽屉编号",order:9},{name:"存档技术员",order:10},{name:"存档日期",order:11},{name:"玻片状态"},{name:"借阅历史"}
    ];

    function init() {

      querySlide.filterData={
        page:1,
        length:20
      };

    }
    init();


    querySlide.getDataList=function () {
      if(!querySlide.filterData.filter){
        delete querySlide.filterData.filter;
      };

      IhcService.get("/archiving/slide/slidesArchivingInfo",querySlide.filterData).then(
        function (result) {
          querySlide.data=result;
        },function (err) {
          toastr.error(err.reason)
        }
      )
    };
    querySlide.getDataList();

    querySlide.history = function (item) {
      IhcService.get("/archiving/slide/brrowHistory",{blockArchiveId:item.blockArchiveId}).then(
        function (result) {
          if(result.length>0){
            var modalInstance = $uibModal.open({
              // animation: $ctrl.animationsEnabled,
              ariaLabelledBy: 'modal-title',
              ariaDescribedBy: 'modal-body',
              templateUrl: 'app/site/archive/queryArchive/modal/slideHistory.html',
              controller: 'SlideHistoryModalController',
              controllerAs: 'slideHistory',
              size:"lg",
              resolve:{
                historyData:function () {
                  return result;
                },
                slideData:function () {
                  return {
                    serialNumber:item.serialNumber,
                    blockSubId:item.blockSubId,
                    slideSubId:item.slideSubId,
                    ihcMarker:item.ihcMarker
                  };
                }
              }
            });

          }else {
            toastr.error("暂无借阅信息")
          }


        },function (err) {
          toastr.error(err.reason)
        }
      )
    }

    querySlide.getSortList = function (item) {
      if(!item.order) return;

      item.sort==='asc'?item.sort='desc':item.sort='asc';
      querySlide.filterData.sort = item.sort;
      querySlide.filterData.order = item.order;
      querySlide.getDataList();
    }

    // 筛选
    querySlide.query=function () {
      delete querySlide.filterData.filter;
      querySlide.getDataList();
    };

    // 查询
    querySlide.search=function () {
      querySlide.filterData={
        page:1,
        length:20,
        filter:querySlide.searchStr,
      };
      querySlide.getDataList();
    };



  }
})();

