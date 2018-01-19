/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SpecialDyeSearchController', SpecialDyeSearchController);

  /** @ngInject */
  function SpecialDyeSearchController($scope,toastr,IhcService,$timeout,SystemSettingService) {
    var specialDyeSearch=this;

    function init() {

      //定义染色类别
      SystemSettingService.getSpecialDyeList().then(function(res){
        specialDyeSearch.dyeTypeList=res;
      });//特染类别获取

      //表格头部
      specialDyeSearch.tableHeaders = [
        {name:"病理号",order:1},{name:"蜡块号"},
        {name:"染色类别"},{name:"试剂/抗体名称",style:{"width": "200px"}},
        {name:"备注",style:{"width": "200px"}},{name:"申请人"},
        {name:"联系电话"},{name:"申请时间",order:8},
        {name:"状态"},{name:"操作"}
      ];

      specialDyeSearch.defaultTime=1;//筛选的默认时间
      specialDyeSearch.filterData={
        page:1,
        length:20,
        // dyeCategory:null
      };

      specialDyeSearch.getDataList=function () {
        if(!specialDyeSearch.filterData.filter){
          delete specialDyeSearch.filterData.filter;
        };

        IhcService.get("/specialDye/query",specialDyeSearch.filterData).then(function (res) {
          specialDyeSearch.data=res;
        })
      };

      //排序
      specialDyeSearch.getSortList = function (item) {
        if(!item.order) return;

        item.sort==='asc'?item.sort='desc':item.sort='asc';
        specialDyeSearch.filterData.sort = item.sort;
        specialDyeSearch.filterData.order = item.order;
        specialDyeSearch.getDataList();
      };

      specialDyeSearch.filterMarker=function (block) {
        var marker;
        if(block.dyeCategory===null){
          for(var i=0;i<block.ihcMarkers.length;i++){
            block.ihcMarkers[i]=';';
          };
          return block.ihcMarkers.join(" ");
        }else{
          return block.ihcMarkers.join(";");
        }
      };

      $timeout(function () {
        specialDyeSearch.filterData.createTimeStart=specialDyeSearch.startTime;
        specialDyeSearch.filterData.createTimeEnd=specialDyeSearch.endTime;
        specialDyeSearch.getDataList();
      },0)
    };
    init();


    // 撤销
    specialDyeSearch.cancel=function (ihcBlockId) {
      var url = "/specialDye/cancel/"+ihcBlockId;
      IhcService.post(url,{ihcBlockId:ihcBlockId}).then(function(){
        toastr.success("撤销成功");
        init();
      },function(err){
        toastr.error(err.reason);
      })
    };

    // 筛选
    specialDyeSearch.query=function () {
      specialDyeSearch.filterData={
        page:1,
        length:20,
        dyeCategory:specialDyeSearch.dyeType,
        createTimeStart:specialDyeSearch.startTime,
        createTimeEnd:specialDyeSearch.endTime,
      };

      specialDyeSearch.getDataList();
    };
    // 查询
    specialDyeSearch.search=function () {

      if(!specialDyeSearch.searchStr){
        console.info('搜索条件')
        specialDyeSearch.defaultTime=1;//没有搜索内容的时候重置筛选条件
      }else{
        specialDyeSearch.defaultTime=5;//搜索时默认时间改成半年
        specialDyeSearch.dyeCategory=null;

      }
      specialDyeSearch.filterData={
        page:1,
        length:20,
        filter:specialDyeSearch.searchStr,
      };
      if(!specialDyeSearch.searchStr){ delete specialDyeSearch.filterData.filter }

      $timeout(function () {
        specialDyeSearch.filterData.createTimeStart=specialDyeSearch.startTime;
        specialDyeSearch.filterData.createTimeEnd=specialDyeSearch.endTime;
        console.info("搜索条件",specialDyeSearch.filterData);
        specialDyeSearch.getDataList();
      },0)

    };


  }
})();

