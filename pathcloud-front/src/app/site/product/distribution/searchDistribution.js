/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('searchDistributionController', searchDistributionController);

  /** @ngInject */
  function searchDistributionController($scope,$timeout,toastr,ProductService,MaterialService){

    var searchDistribution=this;

    function init() {
      MaterialService.getGrossingUser().then(function (data) {
        searchDistribution.grossingUserList = data;
      });
      MaterialService.getDepartments().then(function (data) {
        searchDistribution.departmentList = data;
      });

      searchDistribution.tableHeaders=[//派片记录表格头部
        {name:"病理号",order:1,class:"text-center"},{name:"蜡块号",class:"text-center"},{name:"玻片号",class:"text-center"},
        {name:"取材医生",class:"text-center"},{name:"送检科室",class:"text-center"}, {name:"制片确认人",class:"text-center"},
        {name:"制片确认时间",order:7,class:"text-center"}, {name:"收片医生",class:"text-center"}, {name:"派片时间",order:9,class:"text-center"},
      ];

      searchDistribution.defaultTime = 1;
      searchDistribution.filter={//查询玻片记录的条件
        page:1,
        length:20
      };
    }
    init();



    //获取已派片记录数据
    searchDistribution.getDistributed=function(){
      $timeout(function () {
        searchDistribution.filter.timeStart = searchDistribution.timeStart;
        searchDistribution.filter.timeEnd = searchDistribution.timeEnd;
        ProductService.getDistributedList(searchDistribution.filter).then(
          function(result){
            searchDistribution.distributedData=result.data;
            searchDistribution.total=result.total;
          }
        );
      })

    };
    searchDistribution.getDistributed();

    searchDistribution.query = function () {

      delete searchDistribution.filter.filter
      searchDistribution.getDistributed();
    };

    searchDistribution.search = function () {
      if(!searchDistribution.searchStr){
        searchDistribution.defaultTime = 1;
        delete searchDistribution.filter.filter
      }else {
        searchDistribution.defaultTime = 5;
      }

      searchDistribution.filter={//查询玻片记录的条件
        page:1,
        length:20,
        filter:searchDistribution.searchStr
      };
      searchDistribution.getDistributed();
    };

    searchDistribution.getSortList = function (item) {

      if(!item.order) return;

      item.sort==='asc'?item.sort='desc':item.sort='asc';
      searchDistribution.filter.sort = item.sort;
      searchDistribution.filter.order = item.order;
      searchDistribution.getDistributed();
    };


    /*//根据病理号查询玻片信息
    searchDistribution.getSlide=function(){
      ProductService.searchProductionList(searchDistribution.slideCondition).then(
        function(result){
          searchDistribution.slideData=result.data;
          searchDistribution.slideLength=result.total;
          if(!result.total){
            toastr.error("记录不存在！");
          }
          //console.log('获取根据病理号查询玻片信息',searchDistribution.slideData,searchDistribution.slideLength);
        }
      );
    };

    //输入回车后查询玻片信息
    searchDistribution.getQueryData = function (e) {
      var keycode = window.event?e.keyCode:e.which;
      if(keycode == 13){//回车
        if(searchDistribution.slideCondition.pathNo){
          searchDistribution.getSlide();
        }
      }
    };

    //清空查询玻片信息
    searchDistribution.clearSlide=function(){
      if(!searchDistribution.slideCondition.pathNo){
        searchDistribution.slideCondition={//查询玻片记录的条件
          page:1,
          length:10
        };
        searchDistribution.slideData=[];
      }else{
        return;
      }
    };*/

  }
})();

