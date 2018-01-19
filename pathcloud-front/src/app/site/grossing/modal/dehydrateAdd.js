/**
 * Created by lenovo on 2016/5/13.
 */

(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('DehydrateAddController', dehydrateAddController);

  /** @ngInject */
  function dehydrateAddController($uibModalInstance,GrossingService,$state,toastr,name){
    var dehydrateAdd = this;

    dehydrateAdd.addData = {};
    dehydrateAdd.addData.disabled = false;
    dehydrateAdd.addData.name=name;
    dehydrateAdd.snErr = "";
    dehydrateAdd.nameErr = "";

    dehydrateAdd.change = function(name){
      if(name){
        dehydrateAdd.addData.disabled = false;
      }else{
        dehydrateAdd.addData.disabled = true;
      }
    }

    //添加脱水机
    dehydrateAdd.ok = function(){
      //console.log("添加的脱水机数据：------",dehydrateAdd.addData);
      GrossingService.addOrUpdateGrossing(dehydrateAdd.addData).then(
        function (result){
          //console.log("添加脱水机OK：-----",result);
          toastr.success("添加脱水机成功!");
          $uibModalInstance.close();
          $state.go('app.grossingSetting',{},{reload:true});
        },
        function (error){
          //console.log("添加脱水机：-----",error);
          toastr.error(error);
        }
      );
    };

    dehydrateAdd.cancel = function(){
      $uibModalInstance.dismiss();
    };

    //5 检查序列号是否存在
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
    }

  }
})();
