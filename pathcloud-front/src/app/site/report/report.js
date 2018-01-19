(function () {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ReportController', ReportController);

  /** @ngInject */
  function ReportController(MaterialService, toastr, IhcService, printerService, $timeout, $uibModal) {

    var report = this;
    // console.info(report);


    report.checkedBlocks = []; //存放 选择的
    report.length = 20; //每页的信息条数


    function init() {

      report.defaultTime = 1;
      // report.printStatus = 0; //默认 未打印
      report.printStatusList = [{name: "未打印", code: false}, {name: "已打印", code: true}];
      report.reportStatusList = [{name: "正常", code: false}, {name: "延期", code: true}];
      MaterialService.getDepartments().then(function (data) {
        report.departmentList = data;
      })
    }
    init();


    report.getDataList = function (type) { // type：0 搜索 ，1：筛选  用了日期选择指令 时间不能及时获取 用 timeout 延迟

      var params = {
        page: report.page || 1,
        length: report.length,
        filter: report.searchStr,
        startTime: report.startTime || Date.now(),
        endTime: report.endTime || Date.now(),
        printStatus: report.printStatus, // | Integer //| 打印状态 0代表未打印，1代表已打印
        delay: report.reportStatus, // | Integer //| 报告状态 0是正常，1是延期
        departments: report.departments
      };

      if (type === 1) { //筛选
        delete params.filter;
      }
 
      if (type === 0) { //搜索

        if (!report.searchStr) { //搜索内容空时
          report.defaultTime = 1;
          report.searchStr = undefined;
          delete params.filter;

        } else {
          report.defaultTime = 5;
          params = {
            page: report.page || 1,
            length: report.length,
            filter: report.searchStr,
            startTime: report.startTime || Date.now(),
            endTime: report.endTime || Date.now(),
          }
        }
      }
  

      $timeout(function () {
        params.startTime = report.startTime;
        params.endTime = report.endTime;
        var url = "/report/query"; //6.5 查询我的蜡块
        IhcService.get(url, params).then(function (res) {
          // console.info(res);
          report.data = res.data;
          report.total = res.total;
          report.page = res.page;

        })
      }, 0)

    };

    report.getDataList();

    // 获取打印记录
    report.viewPrintRecord = function (item) { //传个id 到弹窗里面
      var printModal = {
        templateUrl: 'app/site/report/modal/printSlide.html',
        size: 'md',
        // backdrop: false,
        controller: 'PrintRecordModalController',
        controllerAs: 'printModal',
        resolve: {
          resolveData: function () {
            return {
              id: item.id,
              specialApplyId: item.specialApplyId
            };
          }
        }
      };
      $uibModal.open(printModal);
    };


    //打印签收单
    report.printSignQuery = function () {
      var modal = {
        templateUrl: 'app/site/report/modal/printSignQuery.html',
        size: 'md',
        // backdrop: false,
        controller: 'PrintSignModalController',
        controllerAs: 'vm',
        resolve: {
          data: function () {
            return report.data;
          }
        }
      };
      $uibModal.open(modal);
    };


    report.print = function () {
      var chosedIdList = _.compact(report.data.map(function (item) {
        if (item.check) {
          return {
            pathId: item.id,
            specialApplyId: item.specialApplyId
          }
        }
      }));
      // console.log("chosedIdList", chosedIdList);
      // sprint8 接口改动

      IhcService.post('/report/operate/23', chosedIdList).then(function (results) {
        var pathIds = [],
          specialApplyIds = [];
        
        angular.forEach(chosedIdList, function (item, index) {
          
          if(item.specialApplyId){
            specialApplyIds.push(item.specialApplyId)
          }else {
            pathIds.push(item.pathId)
          }
        });
        // debugger
        var params = {
          pathIds: pathIds.length ?　pathIds.join(): null,
          specialApplyIds: specialApplyIds.length ? specialApplyIds.join() : null
        };
        
        IhcService.get('/report/pic', params).then(function (results) {
          results.forEach(function (data, i) {
            if (data.reportPic) {
              var dataJson = JSON.parse(data.reportPic);

              $timeout(
                printReport(dataJson, i, results.length), 1000)

            }
            
          })
        })
      });

    };

    function printReport(dataJson, i, length) {
      console.log(i, length)
      // printerService.printDomByImg(dataJson.data, dataJson.width, dataJson.height);
      printerService.printPathologicalReport(dataJson.html);

      if (i === (length - 1)) {
        toastr.success("打印成功")
        report.getDataList();

        angular.forEach(report.data, function (item, index, array) {
          item.check = false;
        });
      }
    }
    


    // 单选
    report.check=function (block) {
     console.log(block);
    };

    // 全选
    report.checkAll = function () {
      if (report.allChecked) {
        angular.forEach(report.data, function (item, index, array) {
          item.check = true;
        });
      } else {
        angular.forEach(report.data, function (item, index, array) {
          item.check = false;
        });
      }
    }


  } //end CommonModalController
})();



