/**
 * Created by Administrator on 2017/6/6.
 */

(function() {
  'use strict';

  angular
  .module('pathcloud')
  .controller('ReturnSlideController', ReturnSlideController);

  /** @ngInject */
  function ReturnSlideController($scope,$interval,toastr,IhcService){
    var returnSlide=this;

    function init() {
      returnSlide.slideFilter = {}; //页面传入的病理号
      returnSlide.productionList = [];
    }

    returnSlide.tableHeaders = [{name:"病理号"},{name:"蜡块号"},{name:"玻片号"},{name:"特染标记"},{name:"病人姓名"},
      {name:"晾片架编号"},{name:"抽屉编号"},{name:"借阅人"},{name:"借阅日期"},{name:"预计归还日期"},{name:"逾期天数"},{name:"删除"} ];

    returnSlide.getSlideData = function () {
      IhcService.get("/archiving/slide/backConfirm/"+returnSlide.slideFilter.serialNumber).then(
        function (result) {
          handelProductResult(result)

        },function (err) {
          toastr.error(err.reason)
        }
      )
    };

    returnSlide.confirm =function () {
      var idList = []; //#存档ID列表
      angular.forEach(returnSlide.productionList,function (item) {
        idList.push(item.borrowID)
      });
      IhcService.post("/archiving/slide/backConfirm",{borrows:idList}).then(
        function (result) {
          init();
          toastr.success("操作成功")
        },function (err) {
          toastr.error(err.reason)
        }
      )
    };

    //处理返回结果的去重 或排序
    function handelProductResult(result) {
      // console.info("handleProductResult===",result);
      var validResult = result.filter(function (item) {
        return item.slideId != null;
      });
      returnSlide.productionList=_.unionBy(returnSlide.productionList,validResult,'slideId');
    }
  }
})();

