(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('IhcBlocksModalController',IhcBlocksModalController);

  /** @ngInject */
  function IhcBlocksModalController($uibModalInstance,MaterialService,$timeout,IhcService,oldBlocks){

    var ihcBlocks = this;

    ihcBlocks.oldBlocks=oldBlocks; //存放 从新建页面传过来的已经选择过的蜡块
  
    function init(){

      ihcBlocks.defaultTime=1;
      ihcBlocks.params={};



      $timeout(function(){
        ihcBlocks.myBlocks();
      },0)

    }

    //获取我的蜡块
    ihcBlocks.myBlocks=function(){
      var url="/ihc/blocks"; //6.5 查询我的蜡块
      var params={
        page:ihcBlocks.page||1,
        length:ihcBlocks.length||10,
        createTimeStart:ihcBlocks.startTime||Date.now(),
        createTimeEnd:ihcBlocks.endTime||Date.now(),
        
      };
      
      IhcService.get(url,params).then(function (res){
        console.info(res);
        ihcBlocks.total=res.total;
        ihcBlocks.page=res.page;


      })
    }

    init();

    //确定按钮
    ihcBlocks.addBlock = function (){
      $uibModalInstance.close();

    }

    //取消按钮
    ihcBlocks.cancel = function(){
      $uibModalInstance.dismiss();
    }
  }//end CommonModalController
})();
