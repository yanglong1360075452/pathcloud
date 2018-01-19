/**
 * Created by Administrator on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('updateGradeController', updateGradeController);

  /** @ngInject */
  function updateGradeController(StatisticService,$uibModalInstance,data,toastr){
    var updateGrade=this;
    updateGrade.Title="编辑标准";
    updateGrade.data=data;
    //工位站列表
    updateGrade.stationList=[
      {name:'取材',value:1},
      {name:'脱水',value:3},
      {name:'包埋',value:5},
      {name:'切片',value:6},
      {name:'染色',value:7},
      {name:'封片',value:22}
    ];
    //合格标准列表
    updateGrade.validList=[
      {name:'1',value:1},
      {name:'2',value:2},
      {name:'3',value:3},
      {name:'4',value:4},
      {name:'5',value:5}
    ];

    updateGrade.cancel=function(){
      $uibModalInstance.dismiss();
    };
    updateGrade.ok=function(item){
      updateGrade.data.qualified=parseInt(updateGrade.data.qualified);
      StatisticService.updateQualityGrade(updateGrade.data).then(
        function(result){
          toastr.success("更新成功！");
          $uibModalInstance.close(true);
        },
        function(error){
          toastr.error("更新失败！");
        }
      );
    }

  }
})();
