/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SectionSearchController', SectionSearchController);

  /** @ngInject */
  function SectionSearchController($scope,toastr, IhcService, sectionService,$timeout) {
    var sSearch=this;
    
    function init() {
  
      sSearch.sectionPrintMedium = 1;
      sSearch.dyeCategory = 0;
      sSearch.tableHeaders=[
        {name:"病理号",order:1},{name:"蜡块号"},{name:"玻片号"},{name:"打印次数"},{name:"姓名",order:4},{name:"送检科室"},{name:"取材医生"},
        {name:"取材部位"},{name:"组织数"},{name:"取材标识"},{name:"切片技术员"},{name:"切片时间",order:11},
        {name:"切片备注"},{name:"样本状态"}
      ];

      sSearch.defaultTime=1;//筛选的默认时间
      sSearch.filterData={
        page:1,
        length:20,
        order:11,
        dyeCategory: sSearch.dyeCategory,
        sort: 'desc'
      };

      sSearch.getDataList=function () {
        //用了日期范围选择指令后 数据会有延迟问题
        $timeout(function () {
          sSearch.filterData.startTime=sSearch.startTime;
          sSearch.filterData.endTime=sSearch.endTime;
          sectionService.sectionSearch(sSearch.filterData).then(function (res) {
            sSearch.data=res;
          })
        },0)
        
      };
      sSearch.getDataList();

      // ### 获取切片技术员
      sectionService.sectionPerson().then(function (res) {
        sSearch.sectionPersonList=res;
      });
      //获取系统设置的打印方式 1 玻片打印机打印
      IhcService.get('/paramSetting/sectionPrintMedium').then(function (res) {
        sSearch.sectionPrintMedium = res
      });
      
    }
    init();


    // 筛选
    sSearch.query=function () {
  
      sSearch.filterData.status = sSearch.status;
      sSearch.filterData.dyeCategory = sSearch.dyeCategory;
      sSearch.filterData.operatorId = sSearch.operatorId;
      
      sSearch.getDataList();
    };
    sSearch.getSortList = function (item) {
      if(!item.order) return;

      item.sort==='asc'?item.sort='desc':item.sort='asc';
      sSearch.filterData.sort = item.sort;
      sSearch.filterData.order = item.order;
      sSearch.getDataList();
    };
    
    
    
    // 查询
    sSearch.search=function () {

      sSearch.filterData={
        page:1,
        length:20,
        filter:sSearch.searchStr
      };
      if(!sSearch.searchStr){
        
        delete sSearch.filterData.filter;
        sSearch.defaultTime=1;//没有搜索内容的时候重置筛选条件
      }else{
        sSearch.defaultTime=5;//搜索时默认时间改成半年
      }
  
      sSearch.getDataList();
  
    };
  
    sSearch.printSlide = function () {
  
      var printData = count();
      
      IhcService.post('/section/print',printData).then(function (res) {
        toastr.success("操作成功！");
        // 调用service 里的打印方法 传要打印的数据和打印类型 1 是玻片打印机打印
        sectionService.print(res, sSearch.sectionPrintMedium);
  
        sSearch.getDataList()
      },function () {
        toastr.error("操作失败")
      });
      
    };
    
    
    sSearch.checkAll = function () {
    
      //当要全选中
      if(sSearch.allChecked){
        angular.forEach(sSearch.data.data, function (item) {
          item.check = true
        });
      }else{ //要全部取消
        angular.forEach(sSearch.data.data, function (item) {
          item.check = false
        });
      }
    
      count()
    
    };
  
    sSearch.checkItem = function (index,item) {
      count()
    };
  
  
    // todo 切片查询工作站打印 常规的玻片信息 是否有蜡块跟玻片混合的数据 要怎么处理
    //每次从新计算选择
    function count() {
      //每次从新计算选择id
      
      var checkedItems = [];
      angular.forEach(sSearch.data.data, function (item) {
        if(item.check){
          checkedItems.push({
            blockId: item.blockId,
            slideId: item.id
          });
  
          // var data = {
          //   pathNo: item.serialNumber,
          //   blockSubId: item.subId,
          //   slideSubId: item.slideId,
          //   grossingBody: item.bodyPart,
          //   marker: "HE"
          // }
          
        }
      });
    
      if(checkedItems.length === sSearch.data.data.length){
        sSearch.allChecked = true
      }else {
        sSearch.allChecked = false
      }
    
      return checkedItems
    }

  }
})();

