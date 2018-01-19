/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ReagentStateHistoryController', ReagentStateHistoryController);

  /** @ngInject */
  function ReagentStateHistoryController($scope,$timeout,toastr,IhcService,$uibModal){

    var reagent=this;

    function init() {

      reagent.tableHeaders=[//库存记录表格头部
        {name:"名称",order:1,class:"text-center"},{name:"类别",class:"text-center"},{name:"编号",class:"text-center"},
        {name:"制造商",class:"text-center"},{name:"货号",class:"text-center"}, {name:"规格",class:"text-center"},
        {name:"生产批号",order:7,class:"text-center"}, {name:"订单号",class:"text-center"}, {name:"接收日期",order:9,class:"text-center"},
        {name:"失效期",order:7,class:"text-center"}, {name:"最近使用",class:"text-center"}, {name:"库存量",order:9,class:"text-center"}, {name:"状态",class:"text-center"}
      ];
      reagent.typeList = [{code:1, name:"常规试剂"},{code:2, name:"免疫组化试剂"},{code:3, name:"危险品"},{code:4, name:"易燃易爆"},{code:10, name:"包埋盒"},{code:11, name:"常规玻片"}];
      reagent.statusList = [{code:1, name:"正常"},{code:2, name:"即将过期"},{code:3, name:"过期"},{code:4, name:"补货"}];

      reagent.defaultTime = 1;
      reagent.filter={//查询库存记录的条件
        page:1,
        length:20,
        year: new Date().getFullYear()
      };
    }
    init();

    //获取库存记录
    reagent.getData=function(){
        IhcService.get("/store", reagent.filter).then(
          function(result){
            reagent.distributedData=result.data;
            reagent.total=result.total;
          }
        );
    };
    reagent.getData();

    //筛选
    reagent.query = function () {
      delete reagent.filter.filter;
      reagent.getData();
    };

    //搜索
    reagent.search = function () {
      reagent.filter.filter = reagent.searchStr;
      delete reagent.filter.type;
      reagent.getData();
    };

    reagent.getSortList = function (item) {

      if(!item.order) return;

      item.sort==='asc'?item.sort='desc':item.sort='asc';
      reagent.filter.sort = item.sort;
      reagent.filter.order = item.order;
      reagent.getData();
    };

    //编辑库存
    reagent.edit = function(data){
      var modalEdit;
      if(data.reagentCategory==1){//根据类别弹窗
       modalEdit = $uibModal.open({
          ariaLabelledBy: 'modal-title',
          ariaDescribedBy: 'modal-body',
          templateUrl: 'app/site/reagent/modal/editHistory.html',
          controller: 'EditController',
          controllerAs: 'edit',
          size:'lg',
          resolve: {
            data: function () {
              return data;
            }
          }
        });
        modalEdit.result.then(function (res) {
          reagent.getData();//刷新列表
        });
      }else{
          modalEdit = $uibModal.open({
          ariaLabelledBy: 'modal-title',
          ariaDescribedBy: 'modal-body',
          templateUrl: 'app/site/reagent/modal/consume.html',
          controller: 'ConsumeController',
          controllerAs: 'consume',
          size:'lg',
          resolve: {
            data: function () {
              return data;
            }
          }
        });
        modalEdit.result.then(function (res) {
          reagent.getData();//刷新列表
        });
      }

    };


  }
})();

