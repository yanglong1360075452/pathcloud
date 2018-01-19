/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ReagentUseHistoryController', ReagentUseHistoryController);

  /** @ngInject */
  function ReagentUseHistoryController($scope,$timeout,toastr, IhcService,$uibModal){

    var history = this;

    function init() {
      history.typeList = [{code:1, name:"常规试剂"},{code:2, name:"免疫组化试剂"},{code:3, name:"危险品"},{code:4, name:"易燃易爆"},{code:10, name:"包埋盒"},{code:11, name:"常规玻片"}];
      history.tableHeaders=[//使用记录表格头部
        {name:"名称",order:1},{name:"类别"},{name:"产品编号"},{name:"订单号"},
        {name:"制造商"},{name:"生产批号",order:7},
        {name:"接收日期"}, {name:"失效期",order:9},
        {name:"最近使用",order:7},
         {name:"已使用量"}, {name:"剩余量",order:9},
        {name:"使用详情"},{name:"库存调整"}
      ];

      history.defaultTime = 1;
      history.filter={//查询库存记录的条件
        page:1,
        length:20
      };
    }
    init();



    //获取库存记录列表
    history.getData=function(){
       //调用库存记录接口
      IhcService.get("/store", history.filter).then(
        function(res){
          history.distributedData=res.data;
          history.total=res.total;
        }
      );
    };
    history.getData();

    history.query = function () {
      history.filter.startTime = history.timeStart;
      history.filter.endTime = history.timeEnd;
      delete history.filter.filter;
      history.getData();
    };

    history.search = function () {
      if(!history.searchStr){
        history.defaultTime = 1;
        delete history.filter.filter;
      }else {
        history.defaultTime = 5;
      }
      history.filter={//查询玻片记录的条件
        page:1,
        length:20,
        filter:history.searchStr
      };
      history.getData();
    };

    history.getSortList = function (item) {

      if(!item.order) return;

      item.sort==='asc'?item.sort='desc':item.sort='asc';
      history.filter.sort = item.sort;
      history.filter.order = item.order;
      history.getData();
    };

    //进行库存调整
    history.adjust = function(data){
      var modalAdjust = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/reagent/modal/adjust.html',
        controller: 'AdjustController',
        controllerAs: 'adjust',
        resolve: {
          data: function () {
            return data;
          }
        }
      });
      modalAdjust.result.then(function (res) {
        history.getData();
      });
    };
    //查看使用记录
    history.view = function(data){
     $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/reagent/modal/view.html',
        controller: 'ViewController',
        controllerAs: 'view',
        resolve: {
          data: function () {
            return data;
          }
        }
      });
    };
  }
})();

