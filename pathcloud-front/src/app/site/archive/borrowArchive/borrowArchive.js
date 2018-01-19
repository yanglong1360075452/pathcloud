/**
 * Created by Administrator on 2017/6/6.
 */

(function() {
  'use strict';

  angular
  .module('pathcloud')
  .controller('BorrowArchiveController', BorrowArchiveController);

  /** @ngInject */
  function BorrowArchiveController($scope,$state,toastr,IhcService,$uibModal,printerService){
    var borrowArchive=this;
    //二级导航
    borrowArchive.activeMenu=1;
    borrowArchive.choseMenu=function (index) {
      borrowArchive.activeMenu=index;
    };

    //设置表格头部
    borrowArchive.slideTableHeaders = [
      {name:"病理号"},{name:"蜡块号"},{name:"玻片号"},{name:"特染标记"},{name:"患者姓名"},{name:"晾片架编号"},{name:"抽屉编号"},{name:"删除"}
    ];

    // "borrowType":1, #借阅类型 1-病人 2-医生 3-学生

    function init() {
      borrowArchive.productionList = [];
      borrowArchive.slideFilter = {};

      borrowArchive.option = {
        minDate: Date()
      };

      //保存数据
      borrowArchive.saveData = {borrowType:1,archiveIds:[]};
    }

    init();


    borrowArchive.getSlide = function () {
      IhcService.get("/archiving/slide/borrow/"+borrowArchive.slideFilter.serialNumber,borrowArchive.slideFilter).then(
        function (result) {
          if(result.length>1){
            var modalInstance = $uibModal.open({
              // animation: $ctrl.animationsEnabled,
              ariaLabelledBy: 'modal-title',
              ariaDescribedBy: 'modal-body',
              templateUrl: 'app/site/archive/modal/addSlide.html',
              controller: 'AddSlideModalController',
              controllerAs: 'addSlideModal',
              resolve:{
                allSlideList:function () {
                  return result;
                }
              }

            });
            modalInstance.result.then(function (res) {
              if(res&&res.length){
                handelProductResult(res) //处理弹窗里选择的数据
              }

            }).finally(function () {

            });
          }else {
            handelProductResult(result) //大于一条的不显示弹窗直接加到页面上
          }


        },function (err) {
          toastr.error(err.reason)
        }
      )
    };

    borrowArchive.save = function () {
      //获取 slideIds:[6] #玻片ID列表
      if(!borrowArchive.productionList.length) return false;
      borrowArchive.saveData.planBack = borrowArchive.saveData.planBack.getTime();
      angular.forEach(borrowArchive.productionList,function (item,index,arr) {
        borrowArchive.saveData.archiveIds.push(item.id)
      });

      IhcService.post("/archiving/slide/borrow",borrowArchive.saveData).then(
        function (res) {
          toastr.success("操作成功");
          //打印借阅凭证弹窗
          if(borrowArchive.isPrint){
            printVoucher()
          }
          init();
        },function (err) {
          toastr.error(err.reason)
        }
      );
      // console.info(borrowArchive.saveData);
    };

    function printVoucher() {

      var modalInstance = $uibModal.open({
        // animation: $ctrl.animationsEnabled,
        backdrop:false,
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/archive/modal/voucher.html',
        controller: 'VoucherModalController',
        controllerAs: 'voucher',
        size:"lg",
        resolve:{
          data:function () {
            return {
              slides:borrowArchive.productionList,
              borrower:borrowArchive.saveData
            }
          }
        }

      });
      modalInstance.result.then(function (res) {
        init();
      })
    }

    function handelProductResult(result) {
      console.info("handleProductResult===",result);
      var validResult = result.filter(function (item) {
        return item.slideId != null;
      });
      borrowArchive.productionList=_.unionBy(borrowArchive.productionList,validResult,'slideId');

      // totalList=_.unionBy(totalList,validResult,'slideId');
      // totalList=sortArray(totalList);
      // slideArchive.productionList=_.slice(totalList,0,20);
      // slideArchive.searchData.total=totalList.length;
    }


  }
})();

