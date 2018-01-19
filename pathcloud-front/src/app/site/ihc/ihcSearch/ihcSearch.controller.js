/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('IhcSearchController', IhcSearchController);

  /** @ngInject */
  function IhcSearchController($scope,toastr,IhcService,$timeout) {
    var ihcSearch=this;

    function init() {
      
      ihcSearch.tableHeaders=[
        {name:"编号"},{name:"病理号"},{name:"蜡块号"},/*{name:"蜡块状态"},{name:"存放位置"},*/{name:"最近使用人"},
        {name:"最近使用时间"},{name:"特检类别"},{name:"申请医生"},{name:"申请时间"},/*{name:"缴费状态"},*/
        {name:"备注"},{name:"医嘱状态"}
      ];

      ihcSearch.defaultTime=1;//筛选的默认时间
      ihcSearch.dyeCategory = 1; //默认免疫组化状态
      ihcSearch.filterData={
        page:1,
        length:20,
        status: 1,
        dyeCategory: ihcSearch.dyeCategory,
        
      };
      
    }
    init();
  
  
    ihcSearch.getDataList=function () {
      
      if(!ihcSearch.searchStr){
        delete ihcSearch.filterData.filter;
      }
      $timeout(function () {
        ihcSearch.filterData.createTimeStart =ihcSearch.startTime;
        ihcSearch.filterData.createTimeEnd  =ihcSearch.endTime;
        IhcService.get("/ihc",ihcSearch.filterData).then(function (res) {
          ihcSearch.data=res
        })
      },0)
     
      
    };
    ihcSearch.getDataList();
    
    // 筛选
    ihcSearch.query=function () {
      ihcSearch.filterData.dyeCategory = ihcSearch.dyeCategory;
      ihcSearch.getDataList();
    };
    // 查询
    ihcSearch.search=function () {
      ihcSearch.defaultTime = 5;
      ihcSearch.filterData={
        page:1,
        length:20,
        filter:ihcSearch.searchStr
      };
  
      ihcSearch.getDataList();
      
    };
  
    ihcSearch.checkAll = function () {
    
      //当要全选中
      if(ihcSearch.allChecked){
        angular.forEach(ihcSearch.data.data, function (item) {
          item.check = true
        });
      }else{ //要全部取消
        angular.forEach(ihcSearch.data.data, function (item) {
          item.check = false
        });
      }
    
      count()
    
    };
  
    ihcSearch.checkItem = function (index,item) {
      count()
    };
  
    //每次从新计算选择id
    function count() {
      //每次从新计算选择id
      var checkedItems = [];
      angular.forEach(ihcSearch.data.data, function (item) {
        if(item.check){
          checkedItems.push(item.id)
        }
      });
    
      if(checkedItems.length === ihcSearch.data.data.length){
        ihcSearch.allChecked = true
      }else {
        ihcSearch.allChecked = false
      }
      
      return checkedItems
    }
  
    ihcSearch.delay = function () {
      //要确认的id 数组
      IhcService.post("/ihc/delay",count()).then(function (res) {
        toastr.success("操作成功！");
        ihcSearch.getDataList();
      },function (err) {
        toastr.error("操作失败",err.reason)
      })
    
    };
    ihcSearch.confirm = function () {
      //要确认的id 数组
      IhcService.post("/ihc/confirm",count()).then(function (res) {
        toastr.success("操作成功！");
        ihcSearch.getDataList();
      },function (err) {
        toastr.error("操作失败",err.reason)
      })
      
    };
  
  
  }
})();

