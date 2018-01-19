/**
 * Created by lenovo on 2016/5/13.
 */
(function () {
  'use strict';
  angular
    .module('pathcloud')
    .controller('LoseProductController', LoseProductController);

  /** @ngInject */
  function LoseProductController(loseData, allBlockIdList, ProductService, $uibModalInstance) {
    var loseProduct = this;
    var loseHandelDataList = [];
    var pathIdList = [];
    loseProduct.showIndex = 0;
    loseProduct.handelType = '0';

    loseProduct.loseData = _.compact(
      loseData.map(function (item) {
        item.lostSlides = (item.lostSlides || []).concat(item.lostBlocks || []);
        if (item.reGrossing && item.status === 10) {
          item.lostSlides = item.lostSlides || [];
          item.lostSlides.push({statusDesc:item.statusDesc,lastOperator:item.lastOperator,lastOperateTime:item.lastOperateTime});
        }

        pathIdList.push(item.pathId);

        return item;

      })
    );

    // loseProduct.loseData = loseData;

    loseProduct.ok = function () {
      // if(loseProduct.handelType==='0'){
      //   loseProduct.cancel();
      // }else if(loseProduct.showIndex<loseData.length){
      //   var lostItem= [];
      //   var index = loseProduct.showIndex;
      //
      //   loseProduct.loseData[loseProduct.showIndex].lostSlides.forEach(function (item) {
      //     lostItem.push(item.id);
      //   });
      //
      //   loseHandelData={
      //     "abnormalSlideIds":lostItem,
      //     "handle":parseInt(loseProduct.handelType),
      //     "note":loseProduct.note
      //   };
      //
      //   handelLose(loseHandelData,++index);
      //
      // }


      loseProduct.loseData.forEach(function (item) {
        item.lostSlides.forEach(function (subItem) {
          if (subItem.handelType !== '0') {
            loseHandelDataList.push({
              "abnormalId": subItem.id,
              "handle": parseInt(subItem.handelType),
              "note": subItem.note
            })
          }
        });

      });

      if (loseHandelDataList && loseHandelDataList.length > 0) {
        ProductService.setProductionAbnormal(loseHandelDataList).then(function () {
          $uibModalInstance.close(pathIdList);
        });
      } else {
        $uibModalInstance.close(pathIdList);

      }
    };

    loseProduct.cancel = function () {
      // handelLose();
      $uibModalInstance.dismiss();
    };

    // function handelLose(data, index) {
    //   loseProduct.handelType = '0';
    //   loseProduct.remark = "";
    //   // ProductService.setProductionAbnormal(data);
    //   // if(loseProduct.showIndex===loseData.length){
    //   //
    //   //   $uibModalInstance.close();
    //   // }
    //   ProductService.setProductionAbnormal(data).then(function () {
    //     loseProduct.showIndex = index;
    //     if (index === loseData.length) {
    //
    //       $uibModalInstance.close();
    //     }
    //   });
    //
    //
    // }
  }
})();
