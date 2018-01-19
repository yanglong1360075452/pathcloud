/**
 * Created by Administrator on 2016/12/21.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('EmbedSettingController', EmbedSettingController);

  /** @ngInject */
  function EmbedSettingController($scope,toastr,paramSettingService,toolService) {
    var eSetting=this;

    eSetting.data={
        param: "embedRemark",
        // name:"信息不匹配"
    }
    eSetting.add=function ($event) {
      eSetting.disabled=true;
      paramSettingService.setting(eSetting.data).then(function (res) {
        toastr.success("新增成功")
        eSetting.getRemarkList();
        eSetting.data.name=null;
        eSetting.disabled=false;
      },function (err) {
        eSetting.disabled=false;
        toastr.error(err);
      });

      $event.stopPropagation();
    };
    eSetting.delete=function (code) {
      var modal={
        modalTitle:"警告",
        modalContent:"您确定要删除这条内容吗？",
        size:"sm"
      }
      toolService.getModalResult(modal).then(function () {
        paramSettingService.deleteEmbedRemark(code).then(function (res) {
          toastr.success("删除成功")
          eSetting.getRemarkList();
        },function (err) {
          toastr.error(err);
        })
      })

    }

    eSetting.getRemarkList=function () {
      paramSettingService.embedRemark().then(function (res) {
        eSetting.embedRemarkLIst=res;
      },function (err) {
        toastr.error(err);
      })
    }
    eSetting.getRemarkList();

  }
})();

