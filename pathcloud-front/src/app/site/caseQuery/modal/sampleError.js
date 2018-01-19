/**
 * Created by lenovo on 2016/5/13.
 */
(function(){
    'use strict';
    angular
        .module('pathcloud')
        .controller('SampleErrorController', SampleErrorController);

    /** @ngInject */
    function SampleErrorController($scope,$uibModalInstance,resolveData,ProductService,toastr){
      var SampleE=this;

      $scope.resolveData=resolveData;

      SampleE.modalTitle=resolveData.modalTitle;
      SampleE.selectTitle=resolveData.selectTitle;
      SampleE.textareaTtitle=resolveData.textareaTtitle;
      // console.clear()
      console.info(resolveData)

      if(resolveData.status==10||resolveData.status==11){//待取材 待取材确认
        SampleE.dealTypeList=[
          // {name:"申请重补取",code:1},
          // {name:"申请重切",code:2},
          // {name:"异常终止",code:3},
          {name:"通知技术人员",code:4},
        ];
      }else if(resolveData.status==12||resolveData.status==14){ //待脱水 待包埋
        SampleE.dealTypeList=[
          {name:"申请重补取",code:1},
          // {name:"申请重切",code:2},
          // {name:"异常终止",code:3},
          {name:"通知技术人员",code:4},
        ];
      }else if(resolveData.status==15){ //待切片
        SampleE.dealTypeList=[
          {name:"申请重补取",code:1},
          // {name:"申请重切",code:2},
          // {name:"异常终止",code:3},
          {name:"通知技术人员",code:4}
        ];
      }else if(resolveData.status==16){ //待染色
        SampleE.dealTypeList=[
          {name:"申请重补取",code:1},
          {name:"申请重切",code:2},
          // {name:"异常终止",code:3},
          {name:"通知技术人员",code:4}
        ];
      }else if(resolveData.status==18){ //待制片确认
        SampleE.dealTypeList=[
          {name:"申请重补取",code:1},
          {name:"申请重切",code:2},
          // {name:"异常终止",code:3},
          {name:"通知技术人员",code:4}
        ];
      }

      SampleE.dealData={"pathId":resolveData.pathId,"blockId": resolveData.blockId};//异常处理传参方式
      if(resolveData.position==="notification"&&SampleE.dealTypeList){
        SampleE.dealTypeList.pop();
      };//判断当是从通知页面进入的 就不需要通知技术人员那一项


      // var a = {
      //   "blockId": 10,
      //   "dealType": 1, //#处理方式，1-通知技术员，2-标记为异常，申请重补取
      //   "note": "", // #备注
      // }

      SampleE.ok=function(){
        ProductService.dealAbnormalBlock(SampleE.dealData).then(function () {
          $uibModalInstance.close();
          toastr.success("处理成功");
        },function (err) {
          toastr.error(err)
        })
      };
      SampleE.cancel=function(){
        $uibModalInstance.dismiss();
      };

    }
})();
