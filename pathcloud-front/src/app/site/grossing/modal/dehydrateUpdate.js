/**
 * Created by lenovo on 2016/5/13.
 */

(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('DehydrateUpdateController', DehydrateStopController);

  /** @ngInject */
  function DehydrateStopController($uibModalInstance,GrossingService,item,toastr,$state){
    var dehydrateUpdate = this;
    dehydrateUpdate.copySn = angular.copy(item.sn);
    dehydrateUpdate.copyName = angular.copy(item.name);
    dehydrateUpdate.item = item;
    dehydrateUpdate.updateItem = {};
    dehydrateUpdate.snErr = "";
    dehydrateUpdate.nameErr = "";

    console.log("传过来的参数",item);
    dehydrateUpdate.change = function(name){
      if(name===1){
        dehydrateUpdate.item.disabled = false;
      }else if(name===2){
        dehydrateUpdate.item.disabled = true;
      }
    }

    //编辑脱水机
    dehydrateUpdate.ok = function(){
      dehydrateUpdate.updateItem.instrumentId = item.instrumentId;
      dehydrateUpdate.updateItem.name = dehydrateUpdate.item.name;
      dehydrateUpdate.updateItem.sn = dehydrateUpdate.item.sn;
      dehydrateUpdate.updateItem.description = dehydrateUpdate.item.description;
      dehydrateUpdate.updateItem.capacity = dehydrateUpdate.item.capacity;
      dehydrateUpdate.updateItem.disabled = dehydrateUpdate.item.disabled;
      dehydrateUpdate.updateItem.status = dehydrateUpdate.item.status;

      GrossingService.addOrUpdateGrossing(dehydrateUpdate.updateItem).then(
        function (result){
          //console.log("修改脱水机成功：-----",result);
          toastr.success("修改脱水机成功！");
          $uibModalInstance.close();
          $state.go('app.grossingSetting',{},{reload:true});
        },
        function (error){
          //console.log("修改脱水机失败：-----",error);
          toastr.error(error);
        }
      );

    };
    dehydrateUpdate.cancel = function(){
      $uibModalInstance.dismiss();
    };


    //5 检查序列号是否存在
    dehydrateUpdate.checkSn = function(){
      dehydrateUpdate.snErr = "";
      GrossingService.checkSn(dehydrateUpdate.item.sn).then(
        function (result){

          if(result){
            if(dehydrateUpdate.item.sn!==dehydrateUpdate.copySn){
              dehydrateUpdate.snErr = "该序列号已存在！";
            }
          }
        }
      );
    };

    //6 检查编号是否存在
    dehydrateUpdate.checkName = function(){
      dehydrateUpdate.nameErr = "";
      GrossingService.checkName(dehydrateUpdate.item.name).then(
        function (result){
          //console.log('111111111111-------update NAME',result);
          if(result){
            if(dehydrateUpdate.item.name!==dehydrateUpdate.copyName){
              dehydrateUpdate.nameErr = "该编号已存在！";
            }
          }

        }
      );
    };

  }
})();
