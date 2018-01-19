/**
 * Created by Administrator on 2016/12/5.
 */
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('DyeSettingController', DyeSettingController);

  /** @ngInject */
  function DyeSettingController($scope,GrossingService,toastr,toolService,$uibModal,$state,IhcService){
    var dyeSetting=this;
    var numNameList=[];
    var startNumName;
    var type = 1;//所有操作前判断type 是染色还是封片

    dyeSetting.grossingList = [];//获取所有的机列表

    dyeSetting.activeMenu=[true,false];
    dyeSetting.showPart=0;

    dyeSetting.choseMenu=function (index) {
      dyeSetting.activeMenu.forEach(function (boolean,i) {
        dyeSetting.activeMenu[i]=false;
      });
      dyeSetting.activeMenu[index]=true;
      dyeSetting.showPart=index;
      type = index + 1;//所有操作前判断type

      dyeSetting.grossingList = [];//初始化页面数据
      dyeSetting.getGrossing();//重新获取
    };

    //1 获取所有的脱水机
    dyeSetting.getGrossing = function(){

      IhcService.get("/instrument",{type:type,length:99}).then(
        function (result){
          if(!result.data || result.data.length <1) {
            return
          }

          dyeSetting.grossingList = result.data.sort(function(a, b) {
            return a.name - b.name
          });
          /*dyeSetting.grossingList.forEach(function (item) {
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
          });*/
          //console.log("获取所有的脱水机",dyeSetting.grossingList);
        }
      );
    };
    dyeSetting.getGrossing();

    //2 打开添加弹窗
    dyeSetting.openAdd = function () {
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/dye/modal/instrument.html',
        controller: 'DyeInstrumentController',
        controllerAs: 'instrument',
        resolve:{
          data: {
            name:startNumName,
            type:type
          }
        }
      });
      modalInstance.result.then(function (res) {

        IhcService.post("/instrument",res).then(function () {
          dyeSetting.getGrossing()
          toastr.success("创建成功")
        },function (err) {
          toastr.error(err.reason)
        });

      });
    };

    //3.1 打开编辑弹窗
    dyeSetting.openUpdate = function(item){

      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/dye/modal/instrument.html',
        controller: 'DyeInstrumentController',
        controllerAs: 'instrument',
        resolve:{
          data: {
            id:item.id,
            name:item.name,
            sn:item.sn,
            description:item.description,
            status:item.status,
            type:item.status
          }
        }
      });
      modalInstance.result.then(function (res) {
        res.type = type;

        IhcService.put("/instrument",res).then(function () {
          dyeSetting.getGrossing()
          toastr.success("修改成功")
        },function (err) {
          toastr.error(err.reason)
        });
      });
    };

    //4 删除
    dyeSetting.deleteGrossing = function(item){

      toolService.getModalResult({modalTitle:"删除确认",modalContent:"是否确定删除？",size:'sm'})
        .then(function () {
          IhcService.delete("/instrument/"+item.id)
            .then(
              function(result){
                dyeSetting.grossingList = [];//初始化页面数据
                dyeSetting.getGrossing()
                toastr.success("删除成功！");

            },
            function(error){
              //console.log("删除脱水机error：-----");
              toastr.error(error.reason);
            }
            )
        })


    };

  }

})();
