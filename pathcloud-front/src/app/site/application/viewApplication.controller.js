/**
 * Created by Administrator on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ViewApplicationController', ViewApplicationController);

  /** @ngInject */
  function ViewApplicationController(ApplicationService,$filter,toastr,printerService,$uibModal,IhcService,toolService,$timeout) {
    var viewApplication = this;

    // createTimeStart | String | 申请时间，开始时间, yyyy-MM-dd HH:mm:ss
    // createTimeEnd | String | 申请时间，结束时间yyyy-MM-dd HH:mm:ss

    IhcService.get("/systemSetting/sampleCategory").then(function (res) { //样本类别改成获取后台
      viewApplication.categoryList = res;
    });

    viewApplication.statusList=[{name:"未登记",value:1},{name:"已登记",value:2},{name:"已拒收",value:3},{name:"已撤销",value:4},{name:"已结束",value:30}]
    viewApplication.defaultTime = 3;
    viewApplication.searchData={
      // createTimeStart:lastMonth,
      // createTimeEnd:d.getTime(), // 结束时间yyyy-MM-dd HH:mm:ss
      filter:null,
      sort:null,
      order:null,
      page:1,
      length:10
    };


    viewApplication.getDataList = function(){

      $timeout(function () {
        viewApplication.searchData.createTimeStart=viewApplication.startTime;
        viewApplication.searchData.createTimeEnd=viewApplication.endTime;
        ApplicationService.getApplicationList(viewApplication.searchData).then(function (result) {
          viewApplication.data=result.data;
          viewApplication.totalItems=result.total;
        })
      })
    };
    viewApplication.getDataList()
    // 筛选
    viewApplication.query=function () {
      delete viewApplication.searchData.filter;
      viewApplication.getDataList();
    };
    // 查询
    viewApplication.search=function () {

      if(!viewApplication.searchStr){
        viewApplication.defaultTime=1;//没有搜索内容的时候重置筛选条件
        delete viewApplication.searchData.filter;
      }else{
        viewApplication.defaultTime=5;//搜索时默认时间改成半年
      }
      viewApplication.searchData={
        page:1,
        length:20,
        filter:viewApplication.searchStr,
      };
      viewApplication.getDataList();
    }






    // viewApplication 查看申请
    viewApplication.view=function (serialNumber) {
      ApplicationService.getOne(serialNumber).then(function (result) {
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
    //cancel 撤销病理申请

      viewApplication.cancel=function (id,researchType) {
        var modal={
          size:"sm",
          modalTitle:"警告！",
          modalContent:"确定要撤销吗？",
        };
        toolService.getModalResult(modal).then(
          function () {
            //todo
            if(researchType===2){
              ApplicationService.cancelFreeze(id).then(function () {
                toastr.success("撤销成功");
                viewApplication.getDataList();
              },function (err) {
                toastr.error(err);
              })
            }else{
              ApplicationService.cancelOne(id).then(function () {
                toastr.success("撤销成功");
                viewApplication.getDataList();
              },function (err) {
                toastr.error(err);
              })
            }

          }),function () {

        }

      }

    //打印标签
    viewApplication.printLabel=function(labelList){
        console.log(labelList);
      printerService.printLabel(labelList);
    };


  }
})();


// Date.prototype.format = function(format) {
//   var o = {
//     "M+" : this.getMonth() + 1, // month
//     "d+" : this.getDate(), // day
//     "h+" : this.getHours(), // hour
//     "m+" : this.getMinutes(), // minute
//     "s+" : this.getSeconds(), // second
//     "q+" : Math.floor((this.getMonth() + 3) / 3), // quarter
//     "S" : this.getMilliseconds()
//     // millisecond
//   }
//   if (/(y+)/.test(format))
//     format = format.replace(RegExp.$1, (this.getFullYear() + "")
//       .substr(4 - RegExp.$1.length));
//   for ( var k in o)
//     if (new RegExp("(" + k + ")").test(format))
//       format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k]
//         : ("00" + o[k]).substr(("" + o[k]).length));
//   return format;
// }
// var begin=new Date();
// var end=new Date();
// new Date(begin.setMonth((new Date().getMonth()-1)));
// var begintime= begin.Format("yyyy-MM-dd");
// var endtime=end.Format("yyyy-MM-dd");
// $('#txtBeginVisitTime').val(begintime);
// $('#txtEndVisitTime').val(endtime);
/*viewApplication.getDataList=function () {
 if(!viewApplication.searchData.filter){
 delete viewApplication.searchData.filter;
 }
 $timeout(function () {
 ApplicationService.getApplicationList(viewApplication.searchData).then(function (result) {
 viewApplication.data=result.data;
 viewApplication.totalItems=result.total;
 // console.log("申请列表的请求结果",result)
 })
 })
 // console.log("搜索的数据是searchData======",viewApplication.searchData)

 };

 viewApplication.getDataList()


 //search filter
 viewApplication.getFilterList=function (e) {
 var keycode = window.event?e.keyCode:e.which;
 if(keycode==13){
 viewApplication.getDataList()
 }
 };

 //sort
 viewApplication.getSortList=function (item) {
 item.sort==='asc'?item.sort='desc':item.sort='asc';
 viewApplication.searchData.sort = item.sort;
 viewApplication.searchData.order = item.order;

 viewApplication.getDataList()
 };*/
