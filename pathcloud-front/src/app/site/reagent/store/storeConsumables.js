/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('reagentStoreConsumablesController', reagentStoreConsumablesController);

  /** @ngInject */
  function reagentStoreConsumablesController($scope,$state,toastr,IhcService,ReagentService){

    var reagent=this;
    reagent.categoryList = [{code:10, name:"包埋盒"},{code:11, name:"常规玻片"}];
    //数据初始化
    function init(){
      reagent.data = {
        productNumber:"",
        manufacturer:"",
        articleNumber:"",
        manufacturerPhone:"", //#制造商电话
        spec:"",
        receiveStatus:'',
        preparation:"",
        preparationTime:'',
        preExperiment:"",
        preExperimentTime:'',
        preExperimentResult:"",
        specification:'',
        type:'',
        dilutionRateFront:'',
        dilutionRateRear:'',
        realCapacity:'',
        countUnit:2
      };
    }

    init();
    //日期
    $scope.dateOptions1 = {
      dateDisabled: disabled1,
      maxDate: new Date()
    };
    $scope.dateOptions2 = {
      dateDisabled: disabled2
    };
    function disabled1(data) {
      if (!reagent.data.expiryTime) return;
      var date = data.date,
        mode = data.mode;

      return mode === 'day' && (date.getTime() > reagent.data.expiryTime);
    }
    function disabled2(data) {
      if (!reagent.data.produceTime) return;

      var date = data.date,
        mode = data.mode;

      return mode === 'day' && (date.getTime() < reagent.data.produceTime);
    }

    //根据名称获得耗材基本信息
    reagent.getReagentName = function(){
      reagent.nameCheck = false;
      if(reagent.basic){
        checkName();
      }
    };

    function checkName(){
      var data = {
        name:reagent.basic.name
      };
      ReagentService.checkName(data).then(function (res) {
        if(res!==null){
          if(res.category == 2){ //判断类别
            reagent.basic = res;
            reagent.nameCheck = true;
          }else{
            toastr.error('该名称不是耗材名称');
            reagent.basic = {};
          }
        }
      },function(err){
        toastr.error(err.reason);
        reagent.basic = {};
      });
    }

    function store(){
      var reagentData = angular.copy(reagent.data);
      reagentData.reagentId = reagent.basic.id;
      //日期转换成时间戳
      reagentData.produceTime = reagent.data.produceTime.getTime();
      reagentData.expiryTime = reagent.data.expiryTime.getTime() ;
      IhcService.post('/store', reagentData).then(function (res) {
        toastr.success("操作成功");
        reagent.basic = {};
        reagent.nameCheck = false;
        init();
      }, function (err) {
        toastr.error(err.reason);
      });
    }
    //耗材入库提交
    reagent.submit = function(){
      if(reagent.basic.id){
        store();//直接入库
      }else {
        var data ={
          name:reagent.basic.name,
          category:2,
          type:reagent.basic.type,
          cloneNumber:'',
          preProcess:'',
          positivePosition:'',
          diagnose:''
        };
        IhcService.post('/reagent', data).then(function () {//新增
          var data = {
            name:reagent.basic.name
          };
          ReagentService.checkName(data).then(function (res) {//获取id入库
                if (res !== null) {
                  reagent.basic = res;
                  store();
                }
              });
        },function(err){
          toastr.error(err.reason);
        });
      }
    };


  }
})();

