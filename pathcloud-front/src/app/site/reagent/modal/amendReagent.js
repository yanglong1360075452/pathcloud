
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('AmendController', AmendReagentController);

  /** @ngInject */
  function AmendReagentController($scope,$uibModalInstance,data,IhcService,toastr){
    console.log(data);
    var amend= this;
    amend.cancel = function(){
      $uibModalInstance.dismiss();
    };
    amend.ok = function(){
      //试剂修改提交
      var data = {
        id:amend.reagent.id,
        name:amend.reagent.name,
        category:1,
        type:amend.reagent.type,
        cloneNumber:amend.reagent.cloneNumber,
        preProcess:amend.reagent.preProcess,
        positivePosition:amend.reagent.positivePosition,
        diagnose:amend.reagent.diagnose
      };
      IhcService.put('/reagent',data).then(function(){
          toastr.success("修改成功");
          $uibModalInstance.close();
      },function(err){
        toastr.error(err.reason);
      });
    };
    amend.type = [{name:'免疫组化试剂',code:'2'}];
    amend.preProcess =[{code:'2',name:'EDTA热修复'},{code:'3',name:'柠檬酸热修复'}];
    amend.reagentTitle = [{name:'试剂类型'},{name:'试剂名称'},{name:'克隆号'},{name:'预处理'},{name:'阳性部位'},{name:'鉴别诊断'},{name:'操作'}];
    //获取试剂具体信息
    amend.reagent = angular.copy(data);
    amend.reagent.type =  data.type==1?1:amend.type[data.type-2].code;
    amend.reagent.preProcess =  data.preProcess==1?1:amend.preProcess[data.preProcess-2].code;

    //试剂名称校验
    amend.checkButton = true;
    $scope.$watch('amend.reagent.name',function(value){
      if(value){
        if(value !== data.name){//当前名称与修改后名称相同不需要校验
          IhcService.get('/reagent/'+value).then(function(res){
            if(res!==null){
              amend.checkButton = false;//重复
            }else{
              amend.checkButton = true;
            }
          });
        }else{
          amend.checkButton = true;
        }
      }else{
        amend.checkButton = false;
      }
    });

  }
})();
