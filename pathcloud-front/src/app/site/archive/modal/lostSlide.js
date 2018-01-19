/**
 * Created by lenovo on 2016/5/13.
 */
(function () {
  'use strict';
  angular
    .module('pathcloud')
    .controller('LostSlideController', LostSlideController);

  /** @ngInject */
  function LostSlideController(lostData, ProductService, $uibModalInstance) {
    var lostSlide = this;
    var loseHandelDataList = [];
    var pathIdList = [];
    lostSlide.showIndex = 0;
    lostSlide.handelType = 3;  //处理异常

    lostSlide.lostData = lostData;

    //   lostSlide.lostData = _.compact(
    //   lostData.map(function (item) {
    //     item.lostSlides = (item.lostSlides || []).concat(item.lostBlocks || []);
    //     if (item.reGrossing && item.status === 10) {
    //       item.lostSlides = item.lostSlides || [];
    //       item.lostSlides.push({statusDesc:item.statusDesc,lastOperator:item.lastOperator,lastOperateTime:item.lastOperateTime});
    //     }
	//
    //     pathIdList.push(item.pathId);
	//
    //     return item;
	//
    //   })
    // );

    // lostSlide.lostData = lostData;

    lostSlide.ok = function () {

      lostSlide.lostData.forEach(function (subItem) {
        if (subItem.handelType !== '0') {
          loseHandelDataList.push({
            "abnormalId": subItem.id,
            "handle": parseInt(subItem.handelType),
            "note": subItem.note
          })
        }
      });

      if (loseHandelDataList && loseHandelDataList.length > 0) {
        ProductService.setProductionAbnormal(loseHandelDataList).then(function () {
          $uibModalInstance.close(pathIdList);
        });
      } else {
        $uibModalInstance.close(pathIdList);

      }
    };

    lostSlide.cancel = function () {
      // handelLose();
      $uibModalInstance.dismiss();
    };

  }
})();
