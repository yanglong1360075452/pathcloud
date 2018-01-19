/**
 * Created by Administrator on 2016/12/21.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('InspectCategoryController', InspectCategoryController);

  /** @ngInject */
  function InspectCategoryController($scope,toastr,IhcService ,toolService,PathologyService) {
    var sampleSetting=this;

    /*
     第二部分--检查类别部分--
    */
    IhcService.get("/systemSetting/pathNoRule").then(function (res) {
      //检查类别字母
      if(res.letter == 0){
        sampleSetting.showLetter = false
      }else {
        sampleSetting.showLetter = true
      }
      
    })

    sampleSetting.inspectCategory={
      letter: ""
    };

    // 获取检查类别列表
    function getInspectCategory() {
      sampleSetting.letterList=['A','B','C','D','E','F','G','H','I','J','K','L','M','N','O','P','Q','R','S','T','U','V','W','X','Y','Z'];

      PathologyService.inspectCategoryList().then(function (res) {
        sampleSetting.inspectCategoryList=res;
        // console.info(sampleSetting.letterList);
        for(var i=0;i<res.length;i++){
          var letter=res[i].letter;
          for(var j=0;j<sampleSetting.letterList.length;j++){
            var letterList=sampleSetting.letterList[j];

            if(letter===letterList){
              sampleSetting.letterList.splice(j,1);
              j--;
              // console.info(sampleSetting.letterList)
            }
          }
        }
      })
    }
    getInspectCategory();

    // 添加检查类别
    sampleSetting.addInspectCategory=function () {
      PathologyService.inspectCategoryCreate(sampleSetting.inspectCategory).then(function (res) {
        toastr.success("添加成功！");
        getInspectCategory();
        sampleSetting.inspectCategory={};
      },function (err) {
        toastr.error(err)
      })
    };

    sampleSetting.deleteInspectCategory=function (code) {
      var modal={
        modalTitle:"警告",
        modalContent:"您确定要删除这条内容吗？",
        size:"sm"
      };
      toolService.getModalResult(modal).then(function(){
        PathologyService.inspectCategoryDelete(code).then(function (res) {
          toastr.success("删除成功！")
          getInspectCategory();
        },function (err) {
          toastr.error(err)
        })
      })

    }


  }
})();

