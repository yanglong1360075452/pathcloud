/**
 * Created by Administrator on 2016/12/5.
 */
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('GrossingController', GrossingController);

  /** @ngInject */
  function GrossingController($rootScope,GrossingService,toastr,$uibModal,$state,toolService){
    var grossing=this;

    var baskets=[];//储存选中的脱水篮编号  开始脱水用
    var basketNumbers="";

    grossing.dehydrateParams={
      "basketNumbers": "", //脱水篮编号列表
      "page": 1,
      "length": 1000,
    };//点击添加时获取列表的参数

    grossing.activeMachine={};//定义一个选中的脱水机对象
    grossing.tableHeaders=[{name:"病理号",style:{"min-width":"110px"}},{name:"蜡块号"}, {name:"姓名"}, {name:"组织数"}, {name:"取材标识"},{name:"状态"}];

    GrossingService.getDehydratorList({disabled:false,length:99}).then(function (result) {
      grossing.dehydratorList=result.data;
      // console.log("脱水机列表",result)
    });//脱水机列表

    GrossingService.getBasketList(12,{length:99}).then(function (result) {
      grossing.grossingBasketList=result.data;
      // console.log("待脱水篮列表",result.data)
    });//待脱水篮列表

    grossing.check=function (item) {
      if(item.checked){
        item.checked=false;
      }else {
        item.checked=true;
      }
    };
    // status:0,normal , 1 stop
    grossing.checkDehydrateMachine=function (item) {
      if(item.inUse&&item.status!=1){return false;}//排除暂停的情况

      if(grossing.activeMachine.name==item.name){
        grossing.activeMachine={};
      }else {
        grossing.activeMachine=angular.copy(item);
      }
    };
    grossing.add=function (item) {
      var count=0;
      baskets=[];
      if(!grossing.grossingBasketList){
        return;
      };
      for(var i=0;i<grossing.grossingBasketList.length;i++){
        var item=grossing.grossingBasketList[i];
        if(item.checked){
          count+=item.blockCount;
          baskets.push(item.basketNumber);//脱水篮编号数组列表 POST
        };
      };
      basketNumbers=baskets.join();//脱水篮编号字符串列表 GET

      if(!count){
        // alert("您选择的脱水篮呢")// todo confirm modal
        toastr.warning("请选择脱水篮");
      }else if(count>(grossing.activeMachine.capacity-grossing.activeMachine.usedBlock)){//计算可用空间
        toastr.warning("您选择的包埋盒数过多:"+count);
        // alert("您选择的包埋盒数过多:"+count)// todo confirm modal
      }else if(!grossing.activeMachine.name){
        toastr.warning("请选择脱水机");
      }else {//可以脱水 获取要脱水篮中数据列表
        grossing.getDehydrateDataLIst();
      }

    };//添加
    grossing.getDehydrateDataLIst=function () {
      grossing.dehydrateParams.basketNumbers=basketNumbers;
      grossing.dehydrateParams.instrumentId=grossing.activeMachine.instrumentId;

      GrossingService.getDehydrateList(grossing.dehydrateParams).then(function (result) {
        grossing.dehydrateDataLIst=result;
        grossing.dehydratorName=grossing.activeMachine.name;//脱水机name
        grossing.instrumentId=grossing.activeMachine.instrumentId;//脱水机id
        grossing.baskets=baskets;//脱水篮编号
      })
    };//获取脱水篮中的数据 翻页
    grossing.cancel=function () {
      grossing.activeMachine={};//撤销选中的脱水机
      grossing.dehydratorName="";
      grossing.dehydrateDataLIst=null;
      grossing.dehydrateParams={
        "basketNumbers": "", //脱水篮编号列表
        "page": 1,
        "length": 1000,
      };
    };//撤销
    grossing.start=function () {
      // 请添加样本到脱水列表
      // if(!grossing.dehydrateDataLIst||!grossing.activeMachine.instrumentId||!grossing.baskets) {
      //   var modal={
      //     modalTitle:"提示",
      //     modalContent:"请添加样本到脱水列表",
      //     size:"sm",
      //   }
	  //
      //    toolService.getTipResult(modal).then(function(){});
      //    return false;
      // }

      if(!grossing.activeMachine.instrumentId){
        toastr.error("请选择脱水机");
        return
      }

      $rootScope.loading = true;
      grossing.inGrossing=true;//判断脱水按钮能不能点击
      var data={
        baskets:grossing.baskets,
        instrumentId:grossing.activeMachine.instrumentId, //只选脱水机就开始脱水
      };
      GrossingService.startDehydrate(data).then(
        function (result) {
        if(result&&result.length){
          $rootScope.loading = false;
          grossing.inGrossing=false;
          var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'app/site/grossing/modal/lostGrossingBlocks.html',
            controller: 'LostGrossingBlocksController',
            controllerAs: 'lostBlocks',
            size:"md",
            resolve:{
              data:{
                blocks:result,
                data:{
                  baskets:grossing.baskets,
                  instrumentId:grossing.instrumentId,
                  ignore:true
                },
              }
            }

          });
        }
        if(result == null){
          grossing.inGrossing=false;
          $rootScope.loading = false;
          toastr.success("操作成功")
          $state.go('app.grossing',{},{reload:true})//脱水成功后刷新页面
        }

      },
        function (err) {
        toastr.error(err)
      }).finally(
        function () {
        $rootScope.loading = false;
        grossing.inGrossing=false;
      })
    };// 开始脱水
    grossing.pause=function () {

      GrossingService.getDehydratorList({inUse:true,disabled:false}).then(function (result) {
        // console.log("判断是否有正在使用脱水机列表",result)

        if(result.total){
          var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'app/site/grossing/modal/dehydrateStop.html',
            controller: 'DehydrateStopController',
            controllerAs: 'dehydrateStop',
            size:"md",
            resolve : { // This fires up before controller loads and templates rendered
              data : function() {
                return {
                  modalTitle:"暂停脱水",
                  modalDescription:"选择需要暂停的脱水机",
                };
              }
            }

          });
          modalInstance.result.then(function (instrumentId) {
            if(!instrumentId) return;

            GrossingService.pauseDehydrate(instrumentId).then(function (result) {
              toastr.success("暂停脱水成功")
              $state.go('app.grossing',{},{reload:true});//成功后刷新页面
            },function (err) {
              toastr.error(err)
            })
          });
        }else {
          toastr.error("没有正在使用的脱水机");
        }
      });


    };// 结束脱水
    grossing.stop=function () {

      GrossingService.getDehydratorList({inUse:true,disabled:false}).then(function (result) {
        // console.log("判断是否有正在使用脱水机列表",result)

        if(result.total){
          var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'app/site/grossing/modal/dehydrateStop.html',
            controller: 'DehydrateStopController',
            controllerAs: 'dehydrateStop',
            size:"md",
            resolve : { // This fires up before controller loads and templates rendered
              data : function() {
                return {
                  modalTitle:"结束脱水",
                  modalDescription:"选择需要结束的脱水机",
                };
              }
            }

          });
          modalInstance.result.then(function (instrumentId) {
            if(!instrumentId) return;

            GrossingService.stopDehydrate({instrumentIds:instrumentId}).then(function (result) {
              /*GrossingService.getDehydratorList({inUse:true,disabled:false,length:99}).then(function (result) {
                dehydrateStop.dehydratorList=result.data;
                //console.log("正在使用脱水机列表",result)

                if(!dehydrateStop.dehydratorList.length){
                  $uibModalInstance.close();
                }
              });*/
              toastr.success("结束脱水成功")
              $state.go('app.grossing',{},{reload:true});//脱水成功后刷新页面
            })
          });
        }else {
          toastr.error("没有正在使用的脱水机");
        }
      });


    };// 结束脱水
  }

})();
