/**
 * Created by zhanming on 16/4/12.
 * 父级作用域
 */
(function () {
  'use strict';
  
  angular
  .module('pathcloud')
  .directive('consultForm', consultForm);
  
  /** @ngInject */
  function consultForm() {
    var directive = {
      restrict: 'E',
      // scope: false, //其默认情况下就是false 表示继承关系
      scope: {
        data: "=",
        valid: "=",
        colspan: "=",
        showOperation: "=",
        read: "="
      },
      templateUrl: 'app/components/pathologicalForm/consultForm/consultForm.html',
      //replace:true,
      //link: function (scope, elem, attrs) {
      //  console.log(elem);
      //},
      controller: consultFormController,
      controllerAs: "consultForm",
      bindToController: true
    };
    
    return directive;
    
    /** @ngInject */
    
    function consultFormController($scope, $rootScope, $state,  $timeout, toastr, IhcService , T) {

     
      var consultForm = this;
      // console.log("$scope.data:=====",$scope.data)
      // console.log("consultForm.data:=====",consultForm.data)
      
      if(consultForm.data.note){
        consultForm.note = consultForm.data.note //查看申请单的时候显示备注用
      }
      
      
      consultForm.getSelectName = function (array, value) {
        if (value != undefined) {
          // console.log(array, value)
          var data = _.find(array, function (o) {
            if (o.value !== undefined)
              return o.value === value;
            else
              return o.code === value
          });
          
          if (data) {
            return data.name === '请选择' ? "" : data.name
          }
        }
        return ""
      };
      // "sex": 1,#性别 0-未知 1-男  2-女
      // "maritalStatus": 10,#婚否 10-未婚  20-已婚  30-丧偶  40-离婚  90-未知
      consultForm.sexList = [{ name: T.T("请选择"), value: undefined }, { name: T.T("男"), value: 1 }, { name: T.T("女"), value: 2 } ];
      consultForm.maritalStatusList = [{ name: T.T("请选择"), value: 90 }, { name: T.T("未婚"), value: 10 }, { name: T.T("已婚"), value: 20 }, { name: T.T("丧偶"), value: 30 }, { name: T.T("离婚"), value: 40 } ];
      consultForm.typeList = [{ name: T.T("玻片"), code: 1 }, { name: T.T("蜡块"), code: 2 }];
  
      consultForm.inspectHospitalList = $rootScope.inspectHospitalList;
      IhcService.get("/paramSetting/inspectHospital").then(function (res) {
        consultForm.inspectHospitalList = res; //用来防止刷新后申请的时候不显示防止 rootScope保存医院信息失败
      });
      
      
      /*
       *“添加样本记录”
       */
      
      consultForm.addSample = function () {
        consultForm.data.origins.push({pathNo: "", blockSubId: "", type: null})
      };
      
      /*
       *“删除样本记录”
       */
      consultForm.delSample = function (index) {
        consultForm.data.origins.splice(index, 1); //splice(index,num)删除指定位置元素 以及 删除个数
      };
      
      /*
       *“提交按钮”
       */
      consultForm.submit = function () {
  
        IhcService.post("/application/consult", consultForm.data).then(function (res) {
    
          var data = {
            "applicationId":res.id, // #申请ID
            "inspectCategory":8, //#检查类别  固定8
            "note":consultForm.note
          };
    
          IhcService.post("/pathology", data).then(function (result) {
            toastr.success("登记成功");
            $state.go("app.sampleConsultationApply",{},{reload: true})
          })
          
        })
      };
      
      
    }
  }
})();



