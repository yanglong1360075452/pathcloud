/**
 * Created by Administrator on 2016/12/5.
 */
(function () {
  'use strict';

  angular
    .module('pathcloud')
    .controller('createDistributionController', createDistributionController);

  /** @ngInject */
  function createDistributionController($scope, MaterialService, toastr, ProductService, DiagnosisService, $state, $timeout) {

    var createDistribution = this;
    createDistribution.getDistributeData = [];//待派片记录数据
    createDistribution.getDistributeLength = 0;//待派片记录长度
    createDistribution.checkedSlideLength = 0;//已选中的玻片个数
    createDistribution.totalSlideLength = 0;//玻片总个数
    createDistribution.checkBoxStatus = [];//每页待派片记录中checkbox的状态
    createDistribution.getCondition = {//待派片记录的条件
      page: 1,
      length: 20
    };
    /*createDistribution.doCondition = {//派片操作的条件
      distributeRecords: [],//派片记录
      distributeDoctor:""
    };*/
    createDistribution.distributeRecords = [];//中间数组
    createDistribution.doFlag = false;//派片按钮可用状态控制
    createDistribution.diagnosisUserList = [];//存储所有诊断医生
    createDistribution.showList = false;//诊断医生列表默认不显示


    // 取材医生
    function getGrossingUserList() {
      MaterialService.getGrossingUser().then(function (data) {
        createDistribution.grossingUserList = data;
      });
    }

    // 送检科室
    function getDepartments() {
      MaterialService.getDepartments().then(function (data) {
        createDistribution.departmentList = data;
      })
    }

    //获取诊断医生列表
    function getDiagnosisDoctor() {
      DiagnosisService.getDiagnosisDoctor().then(
        function (result) {
          createDistribution.diagnosisUserList = result;
        }
      );
    }

    getDepartments();
    getDiagnosisDoctor();
    getGrossingUserList();

    //控制诊断医生列表是否显示
    createDistribution.openList = function () {
      createDistribution.showList = !createDistribution.showList;
    };

    /*//选中诊断医生列表项 去掉手动输入收片医生的功能
    createDistribution.chooseItem = function (item) {
      createDistribution.doCondition.distributeDoctor = item;
      createDistribution.showList = !createDistribution.showList;
    };*/

    //获取待派片记录数据
    createDistribution.getDistribute = function () {
      ProductService.getDistributeList(createDistribution.getCondition).then(
        function (result) {
          createDistribution.getDistributeData = result.data;
          createDistribution.getDistributeLength = result.total;
          if (createDistribution.getDistributeData) {
            //console.log('获取待派片记录数据',createDistribution.getDistributeData,createDistribution.getDistributeLength);
            for (var i = 0; i < createDistribution.getDistributeData.length; i++) {
              createDistribution.totalSlideLength += createDistribution.getDistributeData[i].slideTotal;//存储待派片记录中玻片总个数
              createDistribution.checkBoxStatus[i] = false;//每页待派片记录中checkbox的状态默认设置为flase
              createDistribution.distributeRecords[i] = null;//派片记录数组每一项初始化为null
            }
  
            count()//每次从新计算选择玻片总数
          }
        }
      );
    };
    createDistribution.getDistribute();

    /*//复选框"checkboxAll"全选全不选操作
    var checkAll = document.getElementById("checkboxAll");
    var checkItemList = document.getElementsByName("checkboxItem");
    createDistribution.checkAll = function () {
      //console.log("---checkItemList---",checkItemList);
      if (checkAll.checked) {//总checkbox全选
        createDistribution.checkedSlideLength = createDistribution.totalSlideLength;//玻片个数为储存的总个数
        for (var i = 0; i < createDistribution.getDistributeData.length; i++) {
          createDistribution.checkBoxStatus[i] = true;//每页待派片记录中checkbox的状态设置为true
          checkItemList[i].checked = true;//实际页面中所有子checkbox选中
          //全选后派片记录的参数设置
          // var obj = {};
          // obj.confirmTime = createDistribution.getDistributeData[i].confirmTime;
          // obj.confirmUser = createDistribution.getDistributeData[i].confirmOperator;
          createDistribution.distributeRecords[i] = createDistribution.getDistributeData[i].pathId;
          createDistribution.doCondition.distributeRecords[i] = createDistribution.getDistributeData[i].pathId;

        }
      } else {//总checkbox全不选
        createDistribution.checkedSlideLength = 0;//玻片个数为0
        for (var i = 0; i < createDistribution.getDistributeData.length; i++) {
          createDistribution.checkBoxStatus[i] = false;//每页待派片记录中checkbox的状态设置为false
          checkItemList[i].checked = false;//实际页面中所有子checkbox不选
          //全不选后派片记录清空
          createDistribution.distributeRecords[i] = null;
          createDistribution.doCondition.distributeRecords[i] = null;

          // createDistribution.doCondition=[];
          // createDistribution.doCondition.distributeRecords=[];
        }
      }
  
      createDistribution.checkedSlideLength = count()//每次从新计算选择玻片总数
    };
  
    

    //复选框"checkItem"
    createDistribution.checkItem = function (index, item) {
      //console.log("---当前记录的数据---",index,item);
      var obj = {};
      createDistribution.checkBoxStatus[index] = !createDistribution.checkBoxStatus[index];
      //1、动态计算选中记录的玻片总和 以及 当前选中记录的制片确认时间和制片确认操作者ID
      if (createDistribution.checkBoxStatus[index]) {
        createDistribution.checkedSlideLength += item.slideTotal;
        obj.confirmTime = item.confirmTime;
        obj.confirmUser = item.confirmOperator;
        // createDistribution.distributeRecords[index] = obj;
        createDistribution.distributeRecords[index] = item.pathId;
      } else {
        createDistribution.checkedSlideLength -= item.slideTotal;
        createDistribution.distributeRecords[index] = null;
      }
      //2、判断若所有checkItem选中则checkboxAll选中，反之；//lodash函数 返回过滤假值后的数组
      var length = (_.compact(createDistribution.checkBoxStatus)).length;
      if (length === createDistribution.checkBoxStatus.length) {//全选
        checkAll.checked = true;
        createDistribution.allChecked = true;
        createDistribution.checkedSlideLength = createDistribution.totalSlideLength;
      } else if (length < createDistribution.checkBoxStatus.length) {
        checkAll.checked = false;
        createDistribution.allChecked = false;
      }
      if (!length) {//全不选
        createDistribution.checkedSlideLength = 0;
      }
  
      createDistribution.checkedSlideLength = count()//每次从新计算选择玻片总数
    };*/

    //派片操作
    createDistribution.doDistribute = function () {
     

      /*angular.forEach(createDistribution.diagnosisUserList,function (item) {
        
        if(item.firstName == createDistribution.doCondition.distributeDoctor){
          createDistribution.doCondition.doctorId = item.id;
        }
      });*/
      
      var data ={
        distributeRecords: count(),//派片记录
        distributeDoctor: createDistribution.doctor.firstName,
        doctorId:createDistribution.doctor.id
      }
  
      // console.info("要传的数据",data); return false;
      ProductService.doDistribute(data).then(
        
        function (result) {
          //console.log('派片成功！');
          toastr.success('派片成功！');

          $state.go('app.ProDistribution',{ },{reload:true});
          
        },
        function (error) {
          //console.log('派片失败！',error);
          toastr.error('派片失败！');
        }
      );
    };
  
    createDistribution.checkAll = function () {
  
      //当要全选中
      if(createDistribution.allChecked){
        angular.forEach(createDistribution.getDistributeData, function (item) {
          item.check = true
        });
      }else{ //要全部取消
        angular.forEach(createDistribution.getDistributeData, function (item) {
          item.check = false
        });
      }
  
      count()//每次从新计算选择玻片总数
      
    };
  
    createDistribution.checkItem = function (index,item) {
      count()//每次从新计算选择玻片总数
    };
  
    //计算玻片总数方法
    function count() {
      var totalCount = 0;
      // var checkedItems = createDistribution.doCondition.distributeRecords = [];
      var checkedItems = [];
      angular.forEach(createDistribution.getDistributeData, function (item) {
        if(item.check){
          totalCount = totalCount + item.slideTotal;
          checkedItems.push(item.pathId)
        }
      });
  
      if(checkedItems.length === createDistribution.getDistributeData.length){
        createDistribution.allChecked = true
      }else {
        createDistribution.allChecked = false
      }
      createDistribution.checkedSlideLength = totalCount//每次从新计算选择玻片总数
      return checkedItems
    }

  }
})();

