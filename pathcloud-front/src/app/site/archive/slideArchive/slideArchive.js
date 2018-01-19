/**
 * Created by Administrator on 2017/6/6.
 */

(function() {
  'use strict';

  angular
  .module('pathcloud')
  .controller('SlideArchiveController', SlideArchiveController);

  /** @ngInject */
  function SlideArchiveController($scope,$interval,toastr,ProductService,$uibModal,toolService,IhcService,T){
    var slideArchive=this;
    var totalList=[];

    //设置表格头部
    slideArchive.tableHeaders = [
      {name:"病理号"},{name:"蜡块号"},{name:"玻片号"},{name:"申请类别"},{name:"特染标记"},{name:"取材标识"},{name:"状态"}
    ];
    slideArchive.archivingMethodList = [{name:T.T('晾片'),code:31},{name:T.T('长期保存'),code:14}];

    function initSlide() {
      slideArchive.slideFilter = {};
      // slideArchive.start=false; //扫描功能
      slideArchive.data={};
      //用于展示的数组
      slideArchive.productionList=[];
      //用户搜索的数组
      slideArchive.userSearchList=[];
      // 储存所有数据的数组
      totalList=[];
      //设置默认查询参数
      slideArchive.searchData = {
        page:1,
        length:20,
        total:0
      };

      // 保存传的参数  "archivingNo":"C1"  "slideIds":[6] #玻片ID列表
      slideArchive.confirmData={archivingMethod:31}
    }

    //add slide
    slideArchive.getSlide = function () {
      IhcService.get("/archiving/slide/"+slideArchive.slideFilter.serialNumber,slideArchive.slideFilter).then(
        function (result) {
          slideArchive.userSearchList.push(result);
          handelProductResult(result);
        },function (err) {
          toastr.error(err.reason)
        }
      )
    };

    slideArchive.save = function () {

      slideArchive.confirmData.archiveSlides = formatConfrimData();

      IhcService.post("/archiving/slide/confirm",slideArchive.confirmData).then(
        function (data) {

          if(data && data.length > 0){

            var modalInstance = $uibModal.open({
              // animation: $ctrl.animationsEnabled,
              ariaLabelledBy: 'modal-title',
              ariaDescribedBy: 'modal-body',
              templateUrl: 'app/site/archive/modal/lostSlide.html',
              controller: 'LostSlideController',
              controllerAs: 'lostSlide',
              size:'lg',
              resolve:{
                lostData:function () {
                  return data;
                },
                // allDatas:function () {
                //   return allBlockIdList;
                // }
              }

            });
            modalInstance.result.then(function () {

              slideArchive.save()

            }).finally(function () {
              // startScan();

            });
          }else{
            toastr.success("保存成功");
            initSlide();
          }





        },function (err) {
          toastr.error(err.reason)
        }
      )
    };

    // todo 将玻片分组
    // archiveSlides: [ {pathId:1 ,slideids: [] }]
    function formatConfrimData() {

      var jsonData=_.groupBy(totalList,'pathId');
      var checkData=[];
      // var allBlockIdList = [];
      for(var name in jsonData){
        var blockIdList=[];
        jsonData[name].forEach(function (item) {
          blockIdList.push(item.slideId);
          // allBlockIdList.push(item.id);
        });
        checkData.push({pathId:parseInt(name),slideIds:blockIdList});
      }

      // console.info(checkData);
      return checkData;

    }

    function sortArray(array){
      var groupJson = _.groupBy(array,'blockSubId');
      var list = [];
      for(var name in groupJson){
        list = _.concat(list,_.sortBy(groupJson[name],"slideSubId"))
      }
      return list;
    }

    function handelProductResult(result) {
      console.info("handleProductResult===",result);

      if(!result||!result.length){
        toastr.error("该病理存档数据为空")
        return false;
      }

      var validResult = result.filter(function (item) {
        return item.slideId != null
      });
      totalList=_.unionBy(totalList,validResult,'slideId');
      totalList=sortArray(totalList);
      slideArchive.productionList=_.slice(totalList,0,20);
      slideArchive.searchData.total=totalList.length;
    }

    slideArchive.changePage=function (page) {
      // console.log(page)
      slideArchive.productionList=_.slice(totalList,
        (page-1)*slideArchive.searchData.length,
        page*slideArchive.searchData.length);

    };

    initSlide()

  }
})();

/*扫描例子*/
// function getScanResult() {
//   ProductService.getProductionScan().then(function (result) {
//     if(result){
//       console.log(toolService.getImgHeader());
//       slideArchive.scanPic = toolService.getImgHeader() + (result.image && result.image.imagePath);
//       result.blocks = _.concat(result.blocks,slideArchive.userSearchList);
//       handelProductResult(result.blocks);
//     }
//   })
// }

//轮询扫描结果接口
// var scanInterval ;

// function startScan() {
//   scanInterval = $interval(getScanResult,5000)
// }
// startScan();
// $scope.$on('$destroy',function(){
//   $interval.cancel(scanInterval);
// });
// slideArchive.changeScanStatus=function () {
//   slideArchive.start=!slideArchive.start;
// };
