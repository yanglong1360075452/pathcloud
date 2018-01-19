/**
 * Created by Administrator on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SampleSearchController', SampleSearchController);

  /** @ngInject */
  function SampleSearchController($filter,IhcService,$uibModal,$timeout,PathologyService,ApplicationService) {
    var sampleSearch = this;

    function init() {
      sampleSearch.tableHeaders=[{name:"申请单号",order:1,sort:'asc'}, {name:"样本编号",order:2,sort:'asc'},{name:"病理号",order:3,sort:'asc'},
        {name:"样本名称",order:4,sort:'asc'},{name:"样本添加时间"},{name:"添加操作人"},{name:"样本删除时间"},{name:"删除操作人"},
        // {name:"病人姓名",order:5,sort:'asc'},{name:"检查项目",order:6,sort:'asc'},{name:"送检科室",order:7,sort:'desc'},
        // {name:"送检医生",order:8,sort:'asc'},{name:"送检医生电话",order:9,sort:'asc'},{name:"送检日期",order:10,sort:'asc'},
        {name:"指定取材医生"},{name:"状态",order:11,class:"text-center",sort:'asc'},
      ];
      sampleSearch.statusList=[{name:"未登记",value:1},{name:"已登记",value:2},{name:"已拒收",value:3},{name:"已撤销",value:41}/*,{name:"待取材",value:10}*//*,{name:"待取材确认",value:11},{name:"待脱水",value:12}*//*,{name:"已撤销",value:4}*/]
      sampleSearch.typeList = [{name:"常规",value:0},{name:"冰冻 F",value:1},{name:"会诊 H",value:4}];
      sampleSearch.defaultTime=1;//日期选择指令默认时间段 最近一天
      sampleSearch.searchData={
        // createTimeStart:lastDay,
        // createTimeEnd:new Date().getTime(), // 结束时间yyyy-MM-dd HH:mm:ss
        // filter:null,
        specialType:null,
        sort:null,
        order:null,
        page:1,
        length:20
      };
      sampleSearch.getDataList=function () {
        // console.log("搜索条件======",sampleSearch.searchData)

        PathologyService.getSampleList(sampleSearch.searchData).then(function (result) {
          sampleSearch.data=result.data;
          sampleSearch.totalItems=result.total;

        })
      };
      serach()
    }
    init();


    function serach() {
      $timeout(function () {
        sampleSearch.searchData.createTimeStart=sampleSearch.startTime;
        sampleSearch.searchData.createTimeEnd=sampleSearch.endTime;
        sampleSearch.getDataList();
      },0)
    }

    // 搜索框
    sampleSearch.search=function () {
      if(!sampleSearch.searchStr){
        // console.info('搜索条件')
        sampleSearch.defaultTime=1;//没有搜索内容的时候重置筛选条件
      }else{
        sampleSearch.defaultTime=5;//搜索时默认时间改成半年
      }
      sampleSearch.searchData={
        filter:sampleSearch.searchStr,
        page:1,
        length:20
      };
      if(!sampleSearch.searchData.filter){
        delete sampleSearch.searchData.filter;
      }
      serach()
    }

    //search filter 筛选
    sampleSearch.filter=function (e) {
      sampleSearch.searchData.filter=null;
      serach()
    };

    //sort
    sampleSearch.getSortList=function (item) {
      item.sort==='desc'?item.sort='asc':item.sort='desc';
      sampleSearch.searchData.sort = item.sort;
      sampleSearch.searchData.order = item.order;

      sampleSearch.getDataList()
    };

    sampleSearch.view=function (serialNumber,pathologyNumber) {
      ApplicationService.getOne(serialNumber).then(function (result) {
        result.pathologyNumber=pathologyNumber;
        console.warn("查看申请result",result)

        $uibModal.open({
          templateUrl: 'app/site/modal/applicationModal.html',
          size:'lg',
          // backdrop: false,
          controller: 'ApplicationModalController',
          controllerAs: 'applicationMod',
          resolve: {
            modalTitle: function () {
              return "病理申请表";
            },
            ApplicationData:result
          }
        })
      },function (error) {

      })
    };

    sampleSearch.reasonModal = function (item) {

      var data;

      if(item.status === 3){ //拒收
        data = {
          header:"拒收记录",
          timeLabel:"拒收时间",
          time:item.updateTime,
          operatorLabel:"拒收人",
          operator:item.updateByName,
          reasonLabel:"拒收原因",
          reason:item.rejectReason
        };
        reasonModal(data)
      }

      if(item.pathologyStatus === 41){// 已撤销

        IhcService.get("/grossing/"+item.pathologyId).then(function (res) {
          var data2 = {
            header:"撤销记录",
            timeLabel:"撤销时间",
            time:res.operationTime,
            operatorLabel:"撤销人",
            operator:res.operatorName,
            reasonLabel:"撤销原因",
            reason:res.note
          };
          reasonModal(data2)
        })
      }


    };

    function reasonModal(data) {
      $uibModal.open({
        templateUrl: 'app/site/modal/reasonModal.html',
        // size:'',
        // backdrop: false,
        controller: 'ReasonModalController',
        controllerAs: 'reasonMod',
        resolve: {
          data: function () {
            return data
          }
        }
      })
    }

  }
})();


// var d = new Date();
// var lastDay = new Date(d.getFullYear(),d.getMonth(),d.getDate()).getTime();
// var lastWeek = new Date(d.getFullYear(),d.getMonth(),d.getDate()-6).getTime();
// var lastMonth = new Date(d.getFullYear(),d.getMonth()-1,d.getDate()).getTime();
// var lastThreeMonth = new Date(d.getFullYear(),d.getMonth()-3,d.getDate()).getTime();
// var lastHalfYear = new Date(d.getFullYear(),d.getMonth()-6,d.getDate()).getTime();
//
// sampleSearch.dateList=[
//   {name:"最近1天", date:lastDay },
//   {name:"最近1周", date:lastWeek },
//   {name:"最近1个月", date:lastMonth },
//   {name:"最近3个月", date:lastThreeMonth},
//   {name:"最近半年", date:lastHalfYear},
//   // {name:"全部申请", date:null},
// ];
