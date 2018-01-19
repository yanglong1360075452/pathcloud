/**
 * Created by Administrator on 2016/12/21.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SampleCategoryController', SampleCategoryController);

  /** @ngInject */
  function SampleCategoryController($scope,toastr,IhcService,toolService,PathologyService) {
    var sampleType=this;

    sampleType.sampleCategory='';

    // 获取检查类别列表
    function getsampleCategory() {
      IhcService.get("/systemSetting/sampleCategory").then(function (res) {
        sampleType.sampleCategoryList = res;
      })
    }
    getsampleCategory();

    // 添加检查类别
    sampleType.addSampleCategory=function () {
      IhcService.post("/systemSetting/sampleCategory",sampleType.sampleCategory).then(function (res) {
        toastr.success("添加成功！")
        getsampleCategory();
        sampleType.sampleCategory='';
      },function (err) {
        toastr.error(err.reason)
      })
    };

    sampleType.deleteSampleCategory=function (code) {
      var modal={
        modalTitle:"警告",
        modalContent:"您确定要删除这条内容吗？",
        size:"sm"
      };
      toolService.getModalResult(modal).then(function(){
        IhcService.delete("/systemSetting/sampleCategory/"+code).then(function (res) {
          toastr.success("删除成功！")
          getsampleCategory();
        },function (err) {
          toastr.error(err.reason)
        })
      })

    }


  }
})();

