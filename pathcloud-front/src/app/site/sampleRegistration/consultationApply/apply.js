/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('consultApplyTabController', consultApplyTabController);

  /** @ngInject */
  function consultApplyTabController($scope,$timeout,toastr,IhcService){

    var consult=this;

    function init() {
    
    }
    init();


    consult.submitData = {
      //   "patientName": "",
      //   "patientNo": "1",
      //   "admissionNo": "1",
      //   "age": 20,
      //   "sex": 1,
      //   "maritalStatus": 10,
      //   "originPlace": "上海市",
      //   "profession": "医生",
      //   "patientTel": "12333332222",
      //   "hospital": 1,
      //   "doctor": "送检医生",
      //   "doctorTel": 13277662222,
      //   "inspectionItem": "",
      //   "visitCat": "",
      //   "clinicalDiagnosis": "",
      //   "medicalSummary": "",
        "origins": [ // #原始蜡块/玻片信息
        {
          "pathNo": "", //#病理号
          "blockSubId":"", // #蜡块号
          "type":"" //#类型 1-玻片 2-蜡块
        }
      ]
    };
  
    /*consult.submit = function () {
      console.info(consult.submitData,consult.note); return
      
      IhcService.post("/application/consult", submitData).then(function (res) {
        
        var data = {
          "applicationId":res.id, // #申请ID
          "inspectCategory":8, //#检查类别  固定8
          "note":consult.note||"会诊目的和要求"
        };
        
        IhcService.post("/pathology", data)
        toastr.success("登记成功")
      })
      
      
    }*/
    
    

    

  }
})();

