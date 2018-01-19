/**
 * Created by Administrator on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('addGradeController', addGradeController);

  /** @ngInject */
  function addGradeController(StatisticService,$uibModalInstance,toastr) {
    var addGrade=this;
    addGrade.Title="创建新标准";
    addGrade.data={};
    //工位站列表
    addGrade.stationList=[
      {name:'取材',value:1},
      {name:'脱水',value:3},
      {name:'包埋',value:5},
      {name:'切片',value:6},
      {name:'染色',value:7},
      {name:'封片',value:22}
    ];
    //合格标准列表
    addGrade.validList=[
      {name:'1',value:1},
      {name:'2',value:2},
      {name:'3',value:3},
      {name:'4',value:4},
      {name:'5',value:5}
    ];

    addGrade.cancel=function(){
      $uibModalInstance.dismiss();
    };
    addGrade.ok=function(){
      addGrade.data.qualified=parseInt(addGrade.data.qualified);
      StatisticService.addQualityGrade(addGrade.data).then(
        function(result){
          toastr.success("创建新的评分标准成功！");
          $uibModalInstance.close(true);
        },
        function(error){
          toastr.error("该工位已存在，请设置其他工位！");
        }
      );

    }

  }
})();
