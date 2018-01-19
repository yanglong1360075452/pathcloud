/**
 * Created by Administrator on 2016/12/5.
 */
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('GrossingSettingController', GrossingSettingController);

  /** @ngInject */
  function GrossingSettingController($scope,GrossingService,toastr,toolService,$uibModal,$state){
    var gSetting=this;
    var numNameList=[];
    var startNumName;

    gSetting.grossingList = [];//获取所有的脱水机列表

    $scope.updateData = {};
    $scope.snErr = "";

    gSetting.activeMenu=[true,false];
    gSetting.showPart=0;

    gSetting.choseMenu=function (index) {
      gSetting.activeMenu.forEach(function (boolean,i) {
        gSetting.activeMenu[i]=false;
      });
      gSetting.activeMenu[index]=true;
      gSetting.showPart=index;
    };

    //1 获取所有的脱水机
    gSetting.getGrossing = function(){
      GrossingService.getDehydratorList({length:99}).then(
        function (result){
          gSetting.grossingList = result.data.sort(function(a, b) {
            return a.name - b.name
          });
          gSetting.grossingList.forEach(function (item) {
            var intName=parseInt(item.name);
            if(!isNaN(intName)){
              numNameList.push(intName);
            }

            if(numNameList[0]>1){
              startNumName=1;
            }else{
              for(var i=0;i<numNameList.length;i++){
                if(numNameList[i]!==i+1){
                  startNumName=i+1;
                  return;
                }else{
                  startNumName=i+2;
                }
              }
            }
          });
          //console.log("获取所有的脱水机",gSetting.grossingList);
        }
      );
    };
    gSetting.getGrossing();

    //2 打开添加脱水机弹窗
    gSetting.openAdd = function () {
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/grossing/modal/dehydrateAdd.html',
        controller: 'DehydrateAddController',
        controllerAs: 'dehydrateAdd',
        resolve:{
          name: startNumName
        }
      });
      modalInstance.result.then(function () {
        console.log('sure')
      });
    };

    //3.1 打开编辑脱水机弹窗
    gSetting.openUpdate = function(item){
      if(item.inUse){
        toastr.error("该脱水机正在使用中无法编辑！");
        return false;
      }
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/grossing/modal/dehydrateUpdate.html',
        controller: 'DehydrateUpdateController',
        controllerAs: 'dehydrateUpdate',
        resolve:{
          item: angular.copy(item)
        }
      });
      modalInstance.result.then(function () {
        console.log('sure')
      });
    };

    //4 删除脱水机
    gSetting.deleteGrossing = function(item){
      if(item.inUse||item.status === 2){
        toastr.error("该脱水机正在使用中无法删除！");
        return false;
      }
      toolService.getModalResult({modalTitle:"删除确认",modalContent:"是否确定删除该脱水机？"})
        .then(function () {
          GrossingService.deleteGrossing(item.instrumentId)
            .then(
              function(result){
              //console.log("删除脱水机OK：-----");
              toastr.success("删除脱水机成功！");
              $state.go('app.grossingSetting',{},{reload:true});
            },
            function(error){
              //console.log("删除脱水机error：-----");
              toastr.error(error);
            }
            )
        })


    };

  }

})();
