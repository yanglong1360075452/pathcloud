/**
 * Created by lenovo on 2016/5/13.
 */

(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('DehydrateStopController', DehydrateStopController);

  /** @ngInject */
  function DehydrateStopController($uibModalInstance,GrossingService,$state,$timeout,data){
    var dehydrateStop=this;

    dehydrateStop.modalTitle = data.modalTitle;
    dehydrateStop.modalDescription = data.modalDescription;


    GrossingService.getDehydratorList({inUse:true,disabled:false,status:0}).then(function (result) {
      dehydrateStop.dehydratorList=result.data;
      console.log("正在使用脱水机列表",result)
    });

    dehydrateStop.check=function (item) {
      if(item.checked){
        item.checked=false;
      }else {
        item.checked=true;
      }
    };
    dehydrateStop.stop=function () {
      var instrumentId=[];//要结束的脱水机的id
      for(var i=0;i<dehydrateStop.dehydratorList.length;i++){
        var item=dehydrateStop.dehydratorList[i];
        if(item.checked){
          instrumentId.push(item.instrumentId);//脱水篮编号数组列表 POST
        }
      }
      if(!instrumentId.length){
        return false;
      }

      $uibModalInstance.close(instrumentId);

    };

    dehydrateStop.cancel=function(){
      $uibModalInstance.dismiss();
    };

  }
})();
