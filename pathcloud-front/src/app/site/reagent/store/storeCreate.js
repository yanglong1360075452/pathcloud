/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('reagentStoreCreateController', reagentStoreCreateController);

  /** @ngInject */
  function reagentStoreCreateController(toastr,IhcService,$scope,ReagentService){

    var reagent=this;
    function init() {
      reagent.statusList = [{code:1, name:"合格"},{code:2, name:"破损"}];
      reagent.liquidType = [{code:2,name:'浓缩液'}];
      reagent.countType = [{code:2,name:'盒'}];
      //初始化
      reagent.data = {
        productNumber:"",
        manufacturer:"",
        articleNumber:"",
        manufacturerPhone:"", //#制造商电话
        spec:"",
        receiveStatus:1, //#接受状态 ，1-合格，2-破损
        preparation:"",
        preExperiment:"",
        preExperimentResult:"",
        type:1, //#试剂类型 1-浓缩液 2-工作液
        dilutionRateFront:'', //#稀释比例-前面的数值
        dilutionRateRear:'', //#稀释比例-后面的数值
        realCapacity:'', //#真正计算容量  浓缩液稀释后的
        countUnit:1 //#数量单位 1-瓶 2-盒
      };
    }
    init();
    //规格切换
    $scope.$watch('reagent.data.type',function(newValue,oldValue){
      if(newValue!=oldValue){
        reagent.data.dilutionRateFront = '';
        reagent.data.dilutionRateRear = '';
        reagent.data.realCapacity = '';
      }
    });
    //计算稀释比例
    $scope.$watch('reagent.data.dilutionRateRear',function(){
      dilutionRate();
    });
    $scope.$watch('reagent.data.dilutionRateFront',function(){
      dilutionRate();
    });
    $scope.$watch('reagent.data.specification',function(){
      dilutionRate();
    });
    function dilutionRate(){
      if(reagent.data.dilutionRateRear!=''&&reagent.data.dilutionRateFront!=''&&reagent.data.specification!=''){
        reagent.data.realCapacity = (reagent.data.dilutionRateRear/reagent.data.dilutionRateFront)*reagent.data.specification;
      }
    }
    //日期设置
    // 生产日期，失效日期
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

    //制备日期，实验日期
    $scope.dateOptions3 = {
      dateDisabled: disabled3,
      maxDate: new Date()
    };
    $scope.dateOptions4 = {
      dateDisabled: disabled4
    };
    function disabled3(data) {
      if (!reagent.data.preExperimentTime) return;
      var date = data.date,
        mode = data.mode;

      return mode === 'day' && (date.getTime() > reagent.data.preExperimentTime);
    }
    function disabled4(data) {
      if (!reagent.data.preparationTime) return;

      var date = data.date,
        mode = data.mode;

      return mode === 'day' && (date.getTime() < reagent.data.preparationTime);
    }

    //通过试剂名称获得基本信息
    reagent.getReagentBasic = function(){
      reagent.nameCheck = false;
      if(reagent.basic.name){
        var data = {
          name:reagent.basic.name
        };
        ReagentService.checkName(data).then(function (res) {
          if(res!==null){
            if(res.category == 1){ //判断类别
              reagent.basic = res;
              reagent.nameCheck = true;
            }else{
              toastr.error('该名称不是试剂名称');
              reagent.basic = {};
            }
          }else{
            toastr.error('试剂名称不存在');
            reagent.basic = {};
          }
        },function(err){
          toastr.error(err.reason);
          reagent.basic = {};
        });
      }
    };
    //清空
    reagent.clear = function () {
      init();
      reagent.basic = {};
    };
    //提交
    var addButton = true;
    reagent.submit = function () {
      if (reagent.data.type == undefined) {//选择浓缩液时
        reagent.data.type = 1;
        reagent.data.dilutionRateFront = '';
        reagent.data.dilutionRateRear = '';
        reagent.data.realCapacity = '';
      }
      reagent.data.countUnit = reagent.data.countUnit ? reagent.data.countUnit : 1;
      reagent.data.receiveStatus = reagent.data.receiveStatus ? reagent.data.receiveStatus : 1;
      var data = angular.copy(reagent.data);
      data.reagentId = reagent.basic.id;
      //日期转换成时间戳
      data.produceTime = reagent.data.produceTime ? reagent.data.produceTime.getTime() : '';
      data.expiryTime = reagent.data.expiryTime ? reagent.data.expiryTime.getTime() : '';
      data.preparationTime = reagent.data.preparationTime ? reagent.data.preparationTime.getTime() : '';
      data.preExperimentTime = reagent.data.preExperimentTime ? reagent.data.preExperimentTime.getTime() : '';
      //调用入库接口
      if (addButton) {
        addButton = false;//禁止重复点击请求
        IhcService.post('/store', data).then(function (res) {
          addButton = true;
          toastr.success("操作成功");
          init();
          reagent.basic = {};
        }, function (err) {
          addButton = true;
          toastr.error(err.reason);
        });
      }
    };
  }
})();

