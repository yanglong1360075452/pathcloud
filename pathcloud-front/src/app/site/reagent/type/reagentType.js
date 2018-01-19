
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ReagentTypeController', ReagentTypeController);

  /** @ngInject */
  function ReagentTypeController($scope,IhcService,toastr,$uibModal,ReagentService){
    var reagent=this;
    reagent.type = [{name:'免疫组化试剂',code:'2'},{name:'危险品',code:'3'},{name:'易燃易爆',code:'4'}];
    reagent.preProcess =[{code:'2',name:'EDTA热修复'},{code:'3',name:'柠檬酸热修复'}];
    reagent.reagentTitle = [{name:'试剂类型'},{name:'试剂名称'},{name:'克隆号'},{name:'预处理'},{name:'阳性部位'},{name:'鉴别诊断'},{name:'操作'}];
    reagent.addReagent = {
      type:1,
      cloneNumber:'',
      preProcess:1,
      positivePosition:'',
      diagnose:''
    };
    //获取试剂列表
    reagent.filter= {
      page:1,
      length:15,
      category:1
    };
    reagent.getReagentList = function(){
      IhcService.get('/reagent',reagent.filter).then(function(res){
        reagent.list = res.data;
        reagent.totalItems = res.total;
      });
    };
    reagent.getReagentList();

    //搜索试剂名称获取列表
    reagent.getReagentInfo = function(){
      reagent.filter.filter=reagent.filterStr;
      reagent.getReagentList();
    };

    //点击对试剂进行修改
    reagent.amend = function(data){
      var modalEdit = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/reagent/modal/amendReagent.html',
        controller: 'AmendController',
        controllerAs: 'amend',
        resolve: {
          data: function () {
            return data;
          }
        }
      });
      modalEdit.result.then(function (res) {
        reagent.getReagentList();
      });
    };

    //填写试剂名称时检查名称是否重复
    $scope.$watch('reagent.addReagent.name',function(value){
      if(value){
        var data = {
          name:value
        };
        ReagentService.checkName(data).then(function (res) {
          if(res!==null){
            if(res.category ==1){
              reagent.checkButton = false;//重复
            }else{
              reagent.checkButton = true;
            }
          }else{
            reagent.checkButton = true;
          }
        });
      }
    });

    var addButton = true;
    //添加试剂耗材
    reagent.add = function(){
      var data ={
        name:reagent.addReagent.name,
        category:1,
        type:reagent.addReagent.type,
        cloneNumber:reagent.addReagent.cloneNumber,
        preProcess:reagent.addReagent.preProcess,
        positivePosition:reagent.addReagent.positivePosition,
        diagnose:reagent.addReagent.diagnose
      };
      if(addButton){
        addButton = false;//禁止重复点击请求
        IhcService.post('/reagent',data).then(function(){
          toastr.success("添加成功");
          addButton = true;
          reagent.addReagent = {
            type:1,
            cloneNumber:'',
            preProcess:1,
            positivePosition:'',
            diagnose:''
          };
          delete data.filter;
          reagent.getReagentList();
        },function (err) {
          addButton = true;
          toastr.error(err.reason);
        });
      }
    };
  }
})();
