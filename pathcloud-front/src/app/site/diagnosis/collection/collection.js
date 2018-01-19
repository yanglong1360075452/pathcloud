(function () {
  'use strict';
  
  angular
  .module('pathcloud')
  .controller('collectDiagnosisController', collectDiagnosisController);
  
  /** @ngInject */
  function collectDiagnosisController(toastr, IhcService, toolService, $timeout, $uibModal) {
    
    var diagnose = this;
    
    diagnose.checkedBlocks = []; //存放 选择的
    diagnose.length = 20; //每页的信息条数
    
    function init() {
      
      diagnose.tableHeaders = [//派片记录表格头部
        {name: "编号"}, {name: "姓名"}, {name: "性别"}, {name: "年龄"}, {name: "收藏标签"},
        {name: "查看权限"}, {name: "收藏日期"}, {name: "收藏备注"}
      ];
      diagnose.defaultTime = 1;
      diagnose.params = {
        page: diagnose.page || 1,
        length: diagnose.length
      };
      
      diagnose.typeList = [{name: "罕见", code: 1}, {name: "随访", code: 2}, {name: "科研", code: 3}, {name: "教学", code: 4}];
      diagnose.statusList = [{name: "我的收藏", code: 1}, {name: "他人收藏", code: 2}];
      
    }
    
    init();
    
    diagnose.getDataList = function (type) { // type：0 搜索 ，1：筛选  用了日期选择指令 时间不能及时获取 用 timeout 延迟
  
      diagnose.params = {
        page: diagnose.page || 1,
        length: diagnose.length,
        filter: diagnose.searchStr,
        startTime: diagnose.startTime || Date.now(),
        endTime: diagnose.endTime || Date.now(),
        label: diagnose.label, //label | String | 标签 1 代表罕见 2 随访 3科研 4教学
        permission: diagnose.permission,
        collect: diagnose.collect,
        
      };
      
      if (type === 1) { //筛选
        delete diagnose.params.filter;
      }
      
      if (type === 0) { //搜索
        
        if (!diagnose.searchStr) { //搜索内容空时
          diagnose.defaultTime = 1;
          diagnose.searchStr = undefined;
          delete diagnose.params.filter;
          
        } else {
          diagnose.defaultTime = 5;
          diagnose.params = {
            page: diagnose.page || 1,
            length: diagnose.length,
            filter: diagnose.searchStr,
            startTime: diagnose.startTime || Date.now(),
            endTime: diagnose.endTime || Date.now()
          }
        }
      }
      
      $timeout(function () {
        diagnose.params.startTime = diagnose.startTime;
        diagnose.params.endTime = diagnose.endTime;
        
        IhcService.get("/diagnose/collect", diagnose.params).then(function (res) {
          // console.info(res);
          diagnose.data = res.data;
          diagnose.total = res.total;
          diagnose.page = res.page;
          
        })
      }, 0)
      
    };
    
    diagnose.getDataList();
    
    diagnose.export = function () {
      toolService.export(diagnose.params,"/diagnose/collect/export")
    };
  
    diagnose.remove = function () {
      var labels = [];
      angular.forEach(diagnose.data, function (item, index) {
        if(item.check){
          labels.push(item.collectId)
        }
      });
      // debugger;
      IhcService.delete("/diagnose/collect",labels).then(function (res) {
        toastr.success("删除成功")
        diagnose.getDataList();
      },function (err) {
        toastr.error(err.reason)
      })
    };
    
    // 单选
    diagnose.check = function (block) {
      console.log(block);
      
    /*  if (block.check) {
        
        diagnose.checkedBlocks.push(block);
      } else {
        
        angular.forEach(diagnose.checkedBlocks, function (item, index, array) {
          if (block.blockId === item.blockId) {
            diagnose.checkedBlocks.splice(index, 1);
          }
          
        });
        
      }*/
    };
    
    // 全选
    diagnose.checkAll = function () {
      if (diagnose.allChecked) {
        angular.forEach(diagnose.data, function (item, index, array) {
          item.check = true;
          
        });
      } else {
        angular.forEach(diagnose.data, function (item, index, array) {
          item.check = false;
        });
      }
    }
    
  } //end CommonModalController
})();



