/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('IhcPrintController', IhcPrintController);

  /** @ngInject */
  function IhcPrintController($scope,toastr,IhcService, printerService, $timeout) {
    var ihcPrint=this;

    function init() {

      ihcPrint.tableHeaders=[
        {name:"编号"},{name:"病理号"},{name:"蜡块号"},{name:"特检类别"},
        {name:"试剂/抗体名称"},{name:"申请医生"},
        {name:"备注"},{name:"打印次数"}
      ];

      ihcPrint.defaultTime=1;//筛选的默认时间
      ihcPrint.dyeCategory = 1; //默认免疫组化状态
      ihcPrint.filterData={
        page:1,
        length:20,
        // count: 1,
        specialDye: ihcPrint.dyeCategory
      };
      IhcService.get("/systemSetting/specialNumberPrint").then(function (result) {
        ihcPrint.printSpecialNumber = result;
      });
    }
    init();


    ihcPrint.getDataList=function () {

      if(!ihcPrint.searchStr){
        delete ihcPrint.filterData.filter;
      }
      $timeout(function () {
        ihcPrint.filterData.createTimeStart =ihcPrint.startTime;
        ihcPrint.filterData.createTimeEnd  =ihcPrint.endTime;
        IhcService.get("/ihc/printIhcs",ihcPrint.filterData).then(function (res) {
          ihcPrint.data=res
        })
      },0)

    };
    ihcPrint.getDataList();

    // 筛选
    ihcPrint.query=function () {
      ihcPrint.filterData.specialDye = ihcPrint.dyeCategory;
      ihcPrint.getDataList();
    };
    // 查询
    ihcPrint.search=function () {
      ihcPrint.defaultTime = 5;
      ihcPrint.filterData={
        page:1,
        length:20,
        filter:ihcPrint.searchStr
      };

      ihcPrint.getDataList();

    };

    ihcPrint.checkAll = function () {

      //当要全选中
      if(ihcPrint.allChecked){
        angular.forEach(ihcPrint.data.data, function (item) {
          item.check = true
        });
      }else{ //要全部取消
        angular.forEach(ihcPrint.data.data, function (item) {
          item.check = false
        });
      }

      count()

    };

    ihcPrint.checkItem = function (index,item) {
      count()
    };

    //每次从新计算选择id
    function count() {
      //每次从新计算选择id
      var checkedItems = [];
      angular.forEach(ihcPrint.data.data, function (item) {
        if(item.check){
          checkedItems.push({
            applyId: item.applyId,
            blockId: item.blockId,
            slideMarker: item.marker,
            specialDye: item.specialDye,
            slideId: item.slideId,
            number: item.number
          })
        }
      });

      if(checkedItems.length === ihcPrint.data.data.length){
        ihcPrint.allChecked = true
      }else {
        ihcPrint.allChecked = false
      }

      return checkedItems
    }


    ihcPrint.print = function () {

    //   [{
    //     "applyId":26,#特检申请ID(block_ihc)
    //   "blockId":38,#蜡块ID
    //   "slideMarker":"de",#标记物
    //   "specialDye":1,#特检类别
    //   "slideId":80 #玻片ID(如果已经打印过,此项有值)
    // }]

    //   打印过后 玻片号 其它要打印的信息

      var printData = count();
      if(printData.length < 1){toastr.error("请选择"); return false}

      IhcService.post("/ihc/print",printData).then(function (res) {
        toastr.success("操作成功");
        var printData = {
          id: [],
          type: []
        };
        angular.forEach(res, function (item, index) {
            var number = ihcPrint.printSpecialNumber==0?'':";"+item.number.slice(0,1) + item.number.slice(-4);//截取免疫组化号或特检号： 第一个字母 加上后四位
            var id=item.pathNo+"$"+item.blockSubId+"$"+item.slideSubId;
            var type = item.grossingBody + ";" + item.marker+ number;
            printData.id.push(id);
            printData.type.push(type)
        });
        //通过后端返回的玻片信息数据 打印到 labwrite 打印机中
        printerService.labWrite(printData.id.join(),printData.type.join()).then(function () { //加一个type

        });

        ihcPrint.getDataList();
      })

    };


  }
})();

