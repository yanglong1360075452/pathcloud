/**
 * Created by Administrator on 2016/12/5.
 */
(function () {
  'use strict';

  angular
  .module('pathcloud')
  .controller('ProConfirmationController', ProConfirmationController);

  /** @ngInject */
  function ProConfirmationController($scope, $interval, toastr, ProductService, $uibModal, toolService, $filter) {
    var proConfirmation = this;
    var totalList = [];

    function init() {
      proConfirmation.start = false;
      proConfirmation.data = {};
      //用于展示的数组
      proConfirmation.productionList = [];
      //用户搜索的数组
      proConfirmation.userSearchList = [];
      // 储存所有数据的数组
      totalList = [];

      //设置默认查询参数
      proConfirmation.searchData = {
        page: 1,
        length: 20,
        total: 0
      };
    }

    function sortArray(array) {
      var groupJson = _.groupBy(array, 'pathId');
      var list = [];
      for (var name in groupJson) {
        list = _.concat(list, _.sortBy(groupJson[name], "id"))
      }
      return list;
    }

    function handelProductResult(result) {
      var validResult = result.filter(function (item) {
        return item.pathId != null
      });
      totalList = _.unionBy(totalList, validResult, 'id');
      totalList = sortArray(totalList);
      proConfirmation.productionList = _.slice(totalList, 0, 20);
      proConfirmation.searchData.total = totalList.length;
    }

    function getScanResult() {
      ProductService.getProductionScan().then(function (result) {
        if (result) {
          console.log(toolService.getImgHeader());
          proConfirmation.scanPic = toolService.getImgHeader() + (result.image && result.image.imagePath);
          result.blocks = _.concat(result.blocks, proConfirmation.userSearchList);
          handelProductResult(result.blocks);

          if (result.errorBlocks) {
            toolService.getModalResult({
              modalTitle: "以下玻片状态不对",
              modalContent: "玻片号：" + result.errorBlocks.join(",")
            }).then(function () {

            }, function () {

            });
          }
        }
      })
    }

    //轮询扫描结果接口
    var scanInterval;

    function startScan() {
      // scanInterval = $interval(getScanResult,5000)
    }

    startScan();
    $scope.$on('$destroy', function () {
      $interval.cancel(scanInterval);
    });

    init();
    //设置表格头部
    proConfirmation.tableHeaders = [
      {name: "病理号"}, {name: "蜡块号"}, {name: "玻片号"}, {name: "申请类别"}, {name: "特染标记"}, {name: "取材标识"}, {name: "状态"}
    ];

    proConfirmation.changeScanStatus = function () {
      proConfirmation.start = !proConfirmation.start;
    };

    proConfirmation.getProductionDataByPathology = function () {

      ProductService.getConfirmationListByPathology(proConfirmation.data.pathologySerialNumber
        // ,proConfirmation.data.subId
      ).then(function (res) {

        if(!res) {
          return false;
        }else if(res.length === 0){
          toastr.error("该蜡块所有的玻片都已制片确认！");
          return;
        }else if(res.operator){
          var content = "该玻片于"+ $filter('date')(res.operatorTime,'yyyy-MM-dd HH:mm') + "被"+ res.operatorDesc + "制片确认，目前状态是" + res.statusDesc;
          toastr.error(content);

          return false;
        }else if(res.pathNo) //返回的对象
        {
          var serialNumber = res.pathNo;
          if(res.blockSubNo){
            serialNumber += res.blockSubNo;
          }
          var content = serialNumber+"当前状态为："+res.statusDesc;
          toastr.error(content);

          return;
        }

        proConfirmation.userSearchList.push(res);
        handelProductResult(res);

        proConfirmation.data.pathologySerialNumber = null; //获取数据后清空数据
      }, function (err) {
        toastr.error(err);
        proConfirmation.data.pathologySerialNumber = null; //获取数据后清空数据
      });

    };

    proConfirmation.changePage = function (page) {
      // console.log(page)
      proConfirmation.productionList = _.slice(totalList,
        (page - 1) * proConfirmation.searchData.length,
        page * proConfirmation.searchData.length);

    };

    proConfirmation.undo = function () {
      //用于展示的数组
      proConfirmation.productionList = [];
      // 储存所有数据的数组
      totalList = [];
      //玻片数
      proConfirmation.searchData.total = "";
    };

    proConfirmation.submit = function () {
      var jsonData = _.groupBy(totalList, 'pathId');
      var checkData = [];
      var allBlockIdList = [];
      for (var name in jsonData) {
        var blockIdList = [];
        jsonData[name].forEach(function (item) {
          blockIdList.push(item.id);
          allBlockIdList.push(item.id);
        });
        checkData.push({pathId: parseInt(name), slideIds: blockIdList});
      }
      console.info(checkData);
      ProductService.getProductionConfirm(checkData).then(function (data) {

        if (data && data.length > 0) {
          $interval.cancel(scanInterval);

          var modalInstance = $uibModal.open({
            // animation: $ctrl.animationsEnabled,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'app/site/product/modal/loseProduct.html',
            controller: 'LoseProductController',
            controllerAs: 'loseProduct',
            size: 'large',
            resolve: {
              loseData: function () {
                return data;
              },
              allBlockIdList: function () {
                return allBlockIdList;
              }
            }

          });
          modalInstance.result.then(function (pathIdList) {
            _.remove(checkData, function (item) {
              return pathIdList.indexOf(item.pathId) >= 0;
            });

            if (checkData && checkData.length > 0) {
              ProductService.getProductionConfirm(checkData).then(function () {
                init();
              });
            } else {
              init();
            }

          }).finally(function () {
            startScan();
          });
        } else {
          toastr.success("确认成功");
          init();
        }

      })
    };

    proConfirmation.getFilterList = function (e) {
      var keycode = window.event ? e.keyCode : e.which;
      if (keycode == 13) {
        proConfirmation.getProductionDataByPathology();
      }
    };

  }
})();

