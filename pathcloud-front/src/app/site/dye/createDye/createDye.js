/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('createDyeController', createDyeController);

  /** @ngInject */
  function createDyeController($scope,$rootScope,$state,toastr,$uibModal,DyeService,toolService,$filter,IhcService){
    var createDye=this;
    createDye.checkedCount=0;  //计算已选中个数
    createDye.type = 0; //默认是不显示分页 按单个扫描玻片添加的流程走
    //获取当前用户待染色、全科待染色片数
    function getDyeCount(){
      DyeService.getDyeCount().then(function(res){
        createDye.currentUserCount = res.currentUserCount;
        createDye.totalCount = res.totalCount;
      });
      createDye.totalItems = createDye.currentUser?createDye.currentUserCount:createDye.totalCount;
    }
    getDyeCount();
    /*
      批量染色：createDye.type：1 显示分页组件
      扫描时判断 type === 0 是往createDye.dyeInfoList push 一条数据 type 为 1  时直接覆盖结果，并把type改成 0
      点击 type = 1 时获取后端的带分页都是数据
     */
    //todo 调用后端接口
    createDye.filter = {
      page: 1,
      length: 100
    };
    createDye.getUserData = function () {
      createDye.type = 1;
      createDye.currentUser = true;
      createDye.totalItems  = createDye.currentUserCount;
      if(createDye.totalItems>100){//点击用户待染片数（数目大于一百）出现弹窗
        batch();
      }else{
        createDye.filter.page =1;
        createDye.getData();
      }
    };
    createDye.getAllData = function () {
      createDye.type = 1;
      createDye.currentUser = false;
      createDye.totalItems  = createDye.totalCount;
      if(createDye.totalItems>100){//点击全科待染片数（数目大于一百）出现弹窗
        batch();
      }else{
        createDye.filter.page =1;
        createDye.getData();
      }
    };
    //批量染色
    function batch(){
      var modalDye = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/dye/modal/dye.html',
        controller: 'DyeController',
        controllerAs: 'dye'
      });
      modalDye.result.then(function (res) {
        if(res==0){
          if(!createDye.instrumentId){
            toastr.error("请选择染色机");
            return
          }
          var data={
            "instrumentId":createDye.instrumentId,
            "currentUser":createDye.currentUser
          };
          IhcService.post("/dye/confirm/batch",data).then(function (res) {
            toastr.success("染色保存成功");
            createDye.dyeInfoList = [];
            getDyeCount();
          },function(err){
            toastr.error(err.reason);
          });
        }else{
          createDye.filter.page =1;
          createDye.getData();
        }
      });
    }
    //获取染色列表信息
    createDye.getData = function () {
      createDye.type = 1;
      createDye.checkedCount = 0;
      createDye.allChecked = false;
      IhcService.get("/dye/list/16/"+createDye.currentUser,createDye.filter).then(function (res) {
        createDye.dyeInfoList = res.data;
      });
    };
    // todo 确认后刷新？ 当 createDye.type === 1; 才刷新

    var instrumentFilter = {
      page:1,
      length:100,
      status:0, //#0正常 1禁用 2报修
      type:1 //1-染色机 2-封片机
    };

    IhcService.get("/instrument",instrumentFilter).then(function (res) {
      createDye.instruments = res.data;
    });

    // 获取玻片信息  添加玻片
    createDye.dyeInfoList=[];
    createDye.getDyeInfo=function () {
      if(!createDye.slideNo) return false;

      if(createDye.slideNo.indexOf('%')>=0){
        toastr.error("输入有误")
        return false;
      }

      DyeService.getDyeInfo(createDye.slideNo).then(function (res) {
        // res.check=true; //默认获取就选中状态
        // createDye.dyeInfoList.push(res);
        if(!res) {

          return false;
        }else if(res.length === 0){
          toastr.error("该蜡块所有的玻片都已染色！");
          return;
        }else if(res.operator){
          var content = "该玻片于"+ $filter('date')(res.operatorTime,'yyyy-MM-dd HH:mm') + "被"+ res.operatorDesc + "染色，目前状态是" + res.statusDesc;
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
        if(createDye.type==1){
          createDye.dyeInfoList = res;
        }else{
          createDye.dyeInfoList=createDye.dyeInfoList.concat(res); //返回的结果是个数组
          createDye.dyeInfoList=_.unionBy(createDye.dyeInfoList,res,'id');//去掉重复的
        }
        createDye.type = 0;
        // console.info("已经添加玻片信息1",createDye.dyeInfoList)

        // 计算已选中
        createDye.checkedCount=0;  //计算已选中个数从0开始加
        angular.forEach(createDye.dyeInfoList,function(item,index,array){
          if(item.check){
            createDye.checkedCount+=1;
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
    createDye.chkAll=function () {
      if(createDye.allChecked){
        angular.forEach(createDye.dyeInfoList,function(item,index,array){
        //data等价于array[index]
          item.check=true;
        });
        createDye.checkedCount=createDye.dyeInfoList.length;  //计算已选中
      }else {
        angular.forEach(createDye.dyeInfoList,function(item,index,array){
          //data等价于array[index]
          item.check=false;
        });
        createDye.checkedCount=0; //计算已选中
      }
    };
    // 单选
    createDye.check=function (check) {
      console.log(check);
      if(check){
        createDye.checkedCount+=1;
      }else {
        createDye.checkedCount-=1;
      }
    };

    //开始染色
    createDye.startDye=function () {

      if(!createDye.instrumentId){
        toastr.error("请选择染色机");
        return
      }

      var idList=[];
      angular.forEach(createDye.dyeInfoList,function(item,index,array){
        if(item.check){
          idList.push(item.id);
        }
      });
      if(idList.length){

        var data = {
          slideIds:idList,
          // ignore:true,
          instrumentId:createDye.instrumentId
        };

        DyeService.startDye(data).then(function (result) {
          if(result&&result.length){

            var modalInstance = $uibModal.open({
              ariaLabelledBy: 'modal-title',
              ariaDescribedBy: 'modal-body',
              templateUrl: 'app/site/dye/modal/lostDyeSlides.html',
              controller: 'LostDyeSlidesController',
              controllerAs: 'lostSlides',
              size:"md",
              resolve:{
                data:{
                  lostSlides:result,
                  idList:idList,
                  text:{}
                }
              }

            });

            modalInstance.result.then(function (res) {
              data.ignore = true;
              DyeService.startDye(data).then(
                function () {
                  toastr.success("染色保存成功");
                  getDyeCount();
                  if(createDye.type==0){
                    createDye.slideNo=null;
                    createDye.dyeInfoList=[];
                  }else{
                    createDye.getData();
                  }
                },function (err) {
                  toastr.error(err);
                }
              )
            })
          }
          if(result === null) {
            toastr.success("染色保存成功");
            getDyeCount();
            if(createDye.type==0){
              createDye.slideNo=null;
              createDye.dyeInfoList=[];
            }else{
              createDye.getData();
            }
          }
        },function (err) {
          toastr.error(err);
        })
      }
    };

  }
})();

