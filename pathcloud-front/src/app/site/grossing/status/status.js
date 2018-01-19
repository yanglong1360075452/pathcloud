/**
 * Created by Administrator on 2016/12/5.
 */
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('GrossingStatusController', GrossingStatusController);

  /** @ngInject */
  function GrossingStatusController(GrossingService,toastr,$uibModal){
    var gStatus=this;

    gStatus.activeMenu=[true,false];
    gStatus.showPart=0;
    gStatus.choseMenu=function (index) {
      gStatus.activeMenu.forEach(function (boolean,i) {
        gStatus.activeMenu[i]=false;
      });
      gStatus.activeMenu[index]=true;
      gStatus.showPart=index;
    };

    //获取使用中脱水篮
    GrossingService.getBasketList(11).then(function (usingBasketList) {
      gStatus.usingBasketList=usingBasketList.data;
    });


    //使用中的脱水机
    GrossingService.getDehydratorAndStatusList().then(function (dehydratorList) {
      gStatus.dehydratorList=dehydratorList;

    });
    //脱水机中的蜡块
    // GrossingService.dehydratorBlocks(instrumentId).then(function (res) {
    //   gStatus.dehydratorBlocks=res;
    // });

    gStatus.showError=function (instrumentId,disabled){
      if(disabled){return;}
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/grossing/modal/dehydratorErr.html',
        controller: 'DehydratorErrController',
        controllerAs: 'dehydratorErr',
        size:"md",
        resolve:{
          instrumentId:instrumentId
        }
      });
      modalInstance.result.then(function () {
        //console.log('sure');
        //使用中的脱水机
        GrossingService.getDehydratorAndStatusList().then(function (dehydratorList) {
          gStatus.dehydratorList=dehydratorList;

        });
      });
    };
    gStatus.showBlocks=function (item){
      if(item.disabled){return;}
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/grossing/modal/dehydratorBlocks.html',
        controller: 'DehydratorBlocksController',
        controllerAs: 'dehydratorBlock',
        size:"md",
        resolve:{
          instrument:function () {
            return {
              id: item.instrumentId,
              pause: item.pause
            }
          }
        }
      });
      modalInstance.result.then(function () {
      
      });
    };

    // gStatus.dehydratorList=[
    //   {
    //     "instrumentId": 1,
    //     "name": "2",
    //     "sn": "xxx",
    //     "capacity": 222,
    //     "description": "yyy",
    //     "inUse": false,
    //     "usedBlock": 123,
    //     "status": 0,
    //     "errMsg": "",
    //     "latestErrTime": 127371828,
    //     "disabled": false,
    //   },
    //   {
    //     "instrumentId": 1,
    //     "name": "2",
    //     "sn": "xxx",
    //     "capacity": 222,
    //     "description": "yyy",
    //     "inUse": false,
    //     "usedBlock": 123,
    //     "status": 1,
    //     "errMsg": "zzz",
    //     "latestErrTime": 127371828,
    //     "disabled": false,
    //   },
    //   {
    //     "instrumentId": 1,
    //     "name": "2",
    //     "sn": "xxx",
    //     "capacity": 222,
    //     "description": "yyy",
    //     "inUse": false,
    //     "usedBlock": 123,
    //     "status": 1,
    //     "errMsg": "zzz",
    //     "latestErrTime": 127371828,
    //     "disabled": true,
    //   }
    // ]

  }

})();
