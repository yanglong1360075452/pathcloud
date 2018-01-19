/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('EmbedSearchController', EmbedSearchController);

  /** @ngInject */
  function EmbedSearchController($scope,toastr,embedService,$timeout) {
    var eSearch=this;

    function init() {
      eSearch.tableHeaders=[
        {name:"病理号",order:1},{name:"蜡块号"},{name:"姓名",order:3},{name:"送检科室"},{name:"取材医生"},
        {name:"取材部位"},{name:"组织数"},{name:"取材标识"},{name:"包埋技术员"},{name:"包埋时间",order:10},
        {name:"包埋备注"},{name:"样本状态"}
      ];

      eSearch.defaultTime=1;//筛选的默认时间
      eSearch.filterData={
        page:1,
        length:20
      };

      eSearch.getDataList=function () {
        if(!eSearch.filterData.filter){
          delete eSearch.filterData.filter;
        };
        embedService.embedSearch(eSearch.filterData).then(function (res) {
          eSearch.data=res
        })
      };

      eSearch.getSortList = function (item) {
        if(!item.order) return;

        item.sort==='asc'?item.sort='desc':item.sort='asc';
        eSearch.filterData.sort = item.sort;
        eSearch.filterData.order = item.order;
        eSearch.getDataList();
      };

      // ### 获取包埋技术员
      embedService.embedPerson().then(function (res) {
        eSearch.embedPersonList=res;
      });
      $timeout(function () {
        eSearch.filterData.startTime=eSearch.startTime;
        eSearch.filterData.endTime=eSearch.endTime;
        eSearch.getDataList();
      },0)
    };
    init();


    // 筛选
    eSearch.query=function () {
      eSearch.filterData={
        page:1,
        length:20,
        status:eSearch.status,
        operatorId:eSearch.operatorId,
        startTime:eSearch.startTime,
        endTime:eSearch.endTime,
      };

      eSearch.getDataList();
    };
    // 查询
    eSearch.search=function () {

      if(!eSearch.searchStr){
        console.info('搜索条件')
        eSearch.defaultTime=1;//没有搜索内容的时候重置筛选条件
      }else{
        eSearch.defaultTime=5;//搜索时默认时间改成半年
        eSearch.status=null;
        eSearch.operatorId=null;
      }
      eSearch.filterData={
        page:1,
        length:20,
        filter:eSearch.searchStr,
      };

      $timeout(function () {
        eSearch.filterData.startTime=eSearch.startTime;
        eSearch.filterData.endTime=eSearch.endTime;
        console.info("搜索条件",eSearch.filterData);
        eSearch.getDataList();
      },0)

    }
    // Param | Type | Description
    //
    // page | Integer | 页数
    //
    // length | Integer | 每页记录数
    //
    // filter | String | 姓名或病理号
    //
    // startTime | Long | 开始时间
    //
    // endTime | Long | 结束时间
    //
    // status | Integer | 样本状态 14-带包埋，15-带切片
    //
    // operatorId | Long | 包埋技术员ID

  }
})();

