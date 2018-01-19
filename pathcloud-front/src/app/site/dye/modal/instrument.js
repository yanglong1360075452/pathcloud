/**
 * Created by lenovo on 2016/5/13.
 */

(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('DyeInstrumentController', DyeInstrumentController);

  /** @ngInject */
  function DyeInstrumentController($uibModalInstance,GrossingService,$state,toastr,data){
    var dehydrateAdd = this;

    dehydrateAdd.addData = data ||{};
    dehydrateAdd.snErr = "";
    dehydrateAdd.nameErr = "";


    //添加脱水机
    dehydrateAdd.ok = function(){
      $uibModalInstance.close(dehydrateAdd.addData);
    };

    dehydrateAdd.cancel = function(){
      $uibModalInstance.dismiss();
    };

/*    //5 检查序列号是否存在
    dehydrateAdd.checkSn = function(sn){
      dehydrateAdd.snErr = "";
      GrossingService.checkSn(dehydrateAdd.addData.sn).then(
        function (result){
          //console.log('0000000000000000-------ADD SN',result);
          if(result){
            dehydrateAdd.snErr = "该序列号已存在！";
          }
        }
      );
    };

    //6 检查编号是否存在
    dehydrateAdd.checkName = function(){
      dehydrateAdd.nameErr = "";
      GrossingService.checkName(dehydrateAdd.addData.name).then(
        function (result){
          //console.log('0000000000000000-------ADD NAME',result);
          if(result){
              dehydrateAdd.nameErr = "该编号已存在！";
          }

        }
      );
    }*/

  }
})();
