/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ReportTemplateController', ReportTemplateController);

  /** @ngInject */
  function ReportTemplateController($scope,$uibModal,toastr,DiagnosisService){
    var ReportTpl=this;

    var templateId;

    /*$scope.reportTemplate={
     logo:{name:"URL",check:"true"},
     title:{name:"四川大学华西医院",check:true},
     secTitle:"病理诊断报告",
     admissionNo:"161200001", //登记/住院号
     pathologySerialNumber:"P161200001",
     patientName:"病人甲",
     sex:"1", //#性别 0-未知 1-男  2-女
     age:"23",
     bedNo:"", //床号
     departments:"2", //
     applicationDoctor:"医生1",
     createTime:"1481686139000",
     room:"19护理单元",//住院患者位置
     eyeSight:"左肾及左肾肿瘤：肿块一个，大小14.5*10*6 cm，见包膜，多切面切开，切面灰黄灰红灰白实性质软，有油腻感，局炤呈暗红似出血。另见肾脏一枚，大小9*6*2 cm，输尿管长5 cm，直径0.5-0.8 cm，沿输尿管纵轴剪开，内壁光滑，未见明显肿物，肾盂稍扩张，未见肿块，肾皮质厚0.8-1 cm，髓质厚1.5 cm，肾皮质髓质未见其余肿块。",
     microSight:['/img/url','/img/url','/img/url',],//住院患者位置
     diagnosis:[{name:"送检部位", value:"胃"},{name:"内镜切除方式", value:"EMR"},{name:"组织学类型", value:"高级别上皮内瘤变"},],
      "diagnosticInformation":{"eye":{name:"肉眼所见","check": true},"micro":{name:"显微所见","check": true},"diagnosis":{name:"病理诊断","check": true}},

     };
    $scope.reportTemplate={
      // "name":"华西医院标题", //#大标题
      "reportTemplateVO": {
        // "imageURL":$scope.reportTemplate.reportTemplateVO.imageURL,
        // "imageURL":"D:\\tupian",
        // "reportSmallName":"nihao小标题",//#小标题
        "patientInformation":[{"name":"病人ID","check": true},{"name":"登记/住院号","check": true},{"name":"病理号","check": true},{"name":"门诊号","check": true},{"name":"姓名","check": true},{"name":"年龄","check": true},{"name":"性别","check": true},{"name":"床号","check": true},{"name":"申请科室","check": true},{"name":"样本接收时间","check": true},{"name":"申请医生","check": true},{"name":"住院患者位置","check": true},],//#病人信息，check=1代表勾选，保存。0不勾选不保存
        "diagnosticContent":[{"name":"肉眼所见","check": true},{"name":"显微所见","check": true},{"name":"病理诊断","check": true}],//#诊断内容，
        "diagnosticInformation":[{"name":"诊断医生","check": true},{"name":"诊断时间","check": true},{"name":"报告打印者","check": true},],//#诊断信息
        // "hospitalInformation":"华西医院医院信息",
        // "specialTip":"请问企鹅"
      },
      "level":1,//#层级关系
      "parent":0,//#父类ID ， 0就是没有父类
    };*/

    $("#logo").change(function () {
      var resultFile = document.getElementById("logo").files[0];
      var reader = new FileReader();
      reader.readAsDataURL(resultFile);
      reader.onload = function (e) {
        var urlData = this.result;
        if(urlData.length>1024*100){
          var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'app/site/diagnosis/modal/cutPic/cutPic.html',
            controller: 'cutPicController',
            controllerAs: 'cutPic',
            resolve:{
              urlData:function(){
                return urlData;
              }
            }
          });
          modalInstance.result.then(function(imgUrl){
            $scope.reportTemplate.reportTemplateVO.imageURL=imgUrl
          });
        }else{
          $scope.$apply(function () {
            $scope.reportTemplate.reportTemplateVO.imageURL=urlData

          })
        }

      }
    });


    // ReportTpl.get=function () {
      DiagnosisService.getReportTemplate().then(function (res) {
        // console.clear();
        console.info(res)
        $scope.reportTemplate=res[0];
        console.info($scope.reportTemplate)
        templateId=res[0].id;
      });//进入页面自动获取模板内容
    // }

    ReportTpl.save=function () {
      console.info($scope.reportTemplate)
      DiagnosisService.updateReportTemplate(templateId,$scope.reportTemplate).then(function (res) {
        toastr.success('保存成功')
        console.info(res)
      });
    }
    ReportTpl.create=function () {
      DiagnosisService.createReportTemplate($scope.reportTemplate).then(function (res) {
        console.info(res)
      });
    }



  }
})();

