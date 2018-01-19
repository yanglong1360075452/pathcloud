(function () {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SignQueryController', SignQueryController);

  /** @ngInject */
  function SignQueryController(MaterialService, toastr, IhcService, $state, $timeout, $uibModal) {

    var signQuery = this;
    // console.info(signQuery)


    signQuery.checkedBlocks = []; //存放 选择的
    signQuery.length = 20; //每页的信息条数


    function init() {

      signQuery.defaultTime = 1;
      // signQuery.printStatus = 0; //默认 未打印
      signQuery.operationList = [{name: "待签收", code: 25}, {name: "已签收", code: 26}];
      signQuery.reportStatusList = [{name: "正常", code: false}, {name: "延期", code: true}];
      signQuery.operation = 25;

    }
    init();


    signQuery.getDataList = function (type) { // type：0 搜索 ，1：筛选  用了日期选择指令 时间不能及时获取 用 timeout 延迟

      var params = {
        page: signQuery.page || 1,
        length: signQuery.length,
        filter: signQuery.searchStr,
        startTime: signQuery.startTime || Date.now(),
        endTime: signQuery.endTime || Date.now(),
        delay: signQuery.reportStatus, // | Integer //| 报告状态 0是正常，1是延期
        departments: signQuery.departments,
        operation: signQuery.operation
      };

      if (type === 1) { //筛选
        delete params.filter;
      }

      if (type === 0) { //搜索

        if (!signQuery.searchStr) { //搜索内容空时
          signQuery.defaultTime = 1;
          signQuery.searchStr = undefined;
          delete params.filter;

        } else {
          signQuery.defaultTime = 5;
          params = {
            page: signQuery.page || 1,
            length: signQuery.length,
            filter: signQuery.searchStr,
            startTime: signQuery.startTime || Date.now(),
            endTime: signQuery.endTime || Date.now(),
          }
        }
      }

      $timeout(function () {
        params.startTime = signQuery.startTime;
        params.endTime = signQuery.endTime;
        var url = "/report/signQuery";
        IhcService.get(url, params).then(function (res) {
          // console.info(res);
          signQuery.data = res.data;
          signQuery.total = res.total;
          signQuery.page = res.page;
        })
     })
    };

    signQuery.getDataList();


    signQuery.confirmSign = function () {
      var idList = _.compact(signQuery.data.map(function (item) {
        if (item.check) {
          return {
            pathId: item.pathId,
            specialApplyId: item.specialApplyId
          }
        }
      }));

      IhcService.post("/report/operate/26", idList).then(function (res) {
        toastr.success("签收成功");
        $state.go('app.reportSignQuery',{},{reload: true})
      }).catch(function (err) {
        toastr.error(err.message)
      })
    };




    // 全选
    signQuery.checkAll = function () {
      if (signQuery.allChecked) {
        angular.forEach(signQuery.data, function (item, index, array) {
          item.check = true;
        });
      } else {
        angular.forEach(signQuery.data, function (item, index, array) {
          item.check = false;
        });
      }
    }


  } //end CommonModalController
})();


