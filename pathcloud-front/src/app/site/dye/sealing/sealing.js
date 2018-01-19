/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('sealingController', sealingController);

  /** @ngInject */
  function sealingController($scope,$rootScope,$state,toastr,$uibModal,DyeService,toolService,$filter,IhcService){
    var sealing=this;
    sealing.checkedCount=0;  //计算已选中个数

    sealing.type = 0; //默认是不显示分页 按单个扫描玻片添加的流程走

    //获取当前用户待封片、全科待封片片数
    function getSealingCount(){
      IhcService.get('/dye/summary/17').then(function(res){
        sealing.currentUserCount = res.currentUserCount;
        sealing.totalCount = res.totalCount;
      });
      sealing.totalItems = sealing.currentUser?sealing.currentUserCount:sealing.totalCount;
    }
    getSealingCount();
    //todo 调用后端接口
    sealing.filter = {
      page: 1,
      length: 100
    };
    sealing.getUserData = function () {
      sealing.type = 1;
      sealing.currentUser = true;
      sealing.totalItems  = sealing.currentUserCount;
      if(sealing.totalItems>100){
        batch();         //点击用户待封片数（数目大于一百）出现弹窗
      }else{
        sealing.filter.page =1;
        sealing.getData();
      }
    };
    sealing.getAllData = function () {
      sealing.type = 1;
      sealing.currentUser = false;
      sealing.totalItems  = sealing.totalCount;
      if(sealing.totalItems>100){
        batch();         //点击全科待封片数（数目大于一百）出现弹窗
      }else{
        sealing.filter.page =1;
        sealing.getData();
      }
  };
    //批量封片
    function batch(){
      var modalDye = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/dye/modal/sealing.html',
        controller: 'SealingController',
        controllerAs: 'sealing'
      });
      modalDye.result.then(function (res) {
        if(res==0){
          if(!sealing.instrumentId){
            toastr.error("请选择封片机");
            return;
          }
          var data={
            "instrumentId": sealing.instrumentId,
            "currentUser": sealing.currentUser
          };
          IhcService.post("/sealing/confirm/batch",data).then(function (res) {
            toastr.success("操作成功");
            sealing.dyeInfoList = [];
            getSealingCount();
          },function(err){
            toastr.error(err.reason);
          });
        }else{
          sealing.getData();
          sealing.filter.page =1;
        }
      });
    }
    //获取封片列表信息
    sealing.getData = function () {
      sealing.type = 1;
      sealing.checkedCount = 0;
      sealing.allChecked = false;
      IhcService.get("/dye/list/17/"+sealing.currentUser,sealing.filter).then(function (res) {
        sealing.dyeInfoList = res.data;
      });
    };
    var instrumentFilter = {
      page:1,
      length:100,
      status:0, //#0正常 1禁用 2报修
      type:2 //2-封片机
    };

    IhcService.get("/instrument",instrumentFilter).then(function (res) {
      sealing.instruments = res.data;
    });

    // 获取玻片信息  添加玻片
    sealing.dyeInfoList=[];
    sealing.getDyeInfo=function () {
      if(!sealing.slideNo) return false;

      if(sealing.slideNo.indexOf('%')>=0){
        toastr.error("输入有误")
        return false;
      }

      IhcService.get("/sealing/"+sealing.slideNo).then(function (res) {
        // res.check=true; //默认获取就选中状态
        // sealing.dyeInfoList.push(res);
        if(!res) {
          return false;
        }else if(res.length === 0){
          toastr.error("该蜡块所有的玻片都已封片！");
          return;
        }else if(res.operator){
          var content = "该玻片于"+ $filter('date')(res.operatorTime,'yyyy-MM-dd HH:mm') + "被"+ res.operatorDesc + "封片，目前状态是" + res.statusDesc;
          toastr.error(content);

          return false;
        }else if(res.pathNo) //返回的对象
        {
          var serialNumber = res.pathNo;
          if(res.blockSubNo){
            serialNumber += res.blockSubNo;
          }
          var content = serialNumber+"当前状态为："+res.statusDesc;
          toastr.error(content);

          return;
        }

        angular.forEach(res,function(data,index){
          data.check=true; //默认获取就选中状态
        });
        if(sealing.type==1){
          sealing.dyeInfoList = res;
        }else{
          sealing.dyeInfoList=sealing.dyeInfoList.concat(res); //返回的结果是个数组
          sealing.dyeInfoList=_.unionBy(sealing.dyeInfoList,res,'id');//去掉重复的
        }
        // console.info("已经添加玻片信息1",sealing.dyeInfoList)
        sealing.type = 0;
        // 计算已选中
        sealing.checkedCount=0;  //计算已选中个数从0开始加
        angular.forEach(sealing.dyeInfoList,function(item,index,array){
          if(item.check){
            sealing.checkedCount+=1;
          }
        });

      },function (err) {
        console.info(err)
        if(err.code==2){
          toastr.error("输入有误");
        }else{
          toastr.error(err.reason);
        }
      })
    };

    // 全选
    sealing.chkAll=function () {
      if(sealing.allChecked){
        angular.forEach(sealing.dyeInfoList,function(item,index,array){
        //data等价于array[index]
          item.check=true;
        });
        sealing.checkedCount=sealing.dyeInfoList.length;  //计算已选中
      }else {
        angular.forEach(sealing.dyeInfoList,function(item,index,array){
          //data等价于array[index]
          item.check=false;
        });
        sealing.checkedCount=0; //计算已选中
      }
    };
    // 单选
    sealing.check=function (check) {
      console.log(check);
      if(check){
        sealing.checkedCount+=1;
      }else {
        sealing.checkedCount-=1;
      }
    };

    //开始染色
    sealing.startDye=function () {

      if(!sealing.instrumentId){
        toastr.error("请选择封片机");
        return
      }

      var idList=[];
      angular.forEach(sealing.dyeInfoList,function(item,index,array){
        if(item.check){
          idList.push(item.id);
        }
      });
      if(idList.length){

        var data = {
          slideIds:idList,
          // ignore:true,
          instrumentId:sealing.instrumentId
        };

        IhcService.post("/sealing/confirm",data).then(function (result) {

          if(result&&result.length){

            var modalInstance = $uibModal.open({
              ariaLabelledBy: 'modal-title',
              ariaDescribedBy: 'modal-body',
              templateUrl: 'app/site/dye/modal/lostDyeSlides.html',
              controller: 'LostDyeSlidesController',
              controllerAs: 'lostSlides',
              size:"md",
              resolve:{
                data:function () {
                  return{
                    lostSlides:result,
                    idList:idList,
                    text:{
                      cancelText:"取消封片",
                      confirmText:"确认封片"
                    }
                  }
                }
              }

            });

            modalInstance.result.then(function (res) {
              data.ignore = true;
              IhcService.post("/sealing/confirm",data).then(
                function () {
                  toastr.success("操作成功");
                  getSealingCount();
                  if(sealing.type==0){
                    sealing.slideNo=null;
                    sealing.dyeInfoList=[];
                  }else{
                    sealing.getData();
                  }
                },function (err) {
                  toastr.error(err.reason);
                }
              )
            })
          }
          if(result === null) {
            toastr.success("操作成功");
            getSealingCount();
            if(sealing.type==0){
              sealing.slideNo=null;
              sealing.dyeInfoList=[];
            }else{
              sealing.getData();
            }
          }

        },function (err) {
          toastr.error(err.reason);
        })
      }

    };

  }
})();

