/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SectionSettingController', SectionSettingController);

  /** @ngInject */
  function SectionSettingController($scope,toastr,paramSettingService, toolService, IhcService) {
    var sSetting=this;

    sSetting.data={
      param: "sectionRemark"
      // name:"信息不匹配"
    };
  
    sSetting.activeMenu = 0;
    sSetting.toolbar = [{name:"切片备注管理", code:"1"}, {name:"玻片打印设置", code:"2"}];
  
    sSetting.choseMenu = function (index) {
      sSetting.activeMenu = index;
      
      if(sSetting.activeMenu === 0){
        sSetting.add=function () {
          sSetting.disabled=true;
          paramSettingService.setting(sSetting.data).then(function (res) {
            toastr.success("新增成功");
            sSetting.getRemarkList();
            sSetting.data.name=null;
            sSetting.disabled=false;
          },function (err) {
            sSetting.disabled=false;
            toastr.error(err)
          })
        };
        sSetting.delete=function (code) {
          var modal={
            modalTitle:"删除确认",
            modalContent:"您确定要删除这条内容吗？",
            size:"sm"
          };
          toolService.getModalResult(modal).then(function () {
            paramSettingService.deleteSectionRemark(code).then(function (res) {
              toastr.success("删除成功")
              sSetting.getRemarkList()
            },function (err) {
              toastr.error(err)
            })
          })
      
        };
    
        sSetting.getRemarkList=function () {
          paramSettingService.sectionRemark().then(function (res) {
            sSetting.sectionRemarkLIst=res;
          },function (err) {
            toastr.error(err);
          })
        }
        sSetting.getRemarkList();
      }else {
        IhcService.get('/paramSetting/sectionPrintMedium').then(function (res) {
          sSetting.sectionPrintMedium = res //1 玻片打印机
        });
        IhcService.get('/paramSetting/sectionPrintWay').then(function (res) {
          sSetting.sectionPrintWay = res //1  自动打印
        });
  
        sSetting.selectPrintMedium = function (code) {
          IhcService.put('/paramSetting/sectionPrintMedium/'+ code).then(function (res) {
            sSetting.sectionPrintMedium = code;
            toastr.success("操作成功")
          },function () {
            toastr.error("操作失败")
          });
        };
  
        sSetting.selectPrintWay = function (code) {
          IhcService.put('/paramSetting/sectionPrintWay/'+ code).then(function (res) {
            sSetting.sectionPrintWay = code;
            toastr.success("操作成功")
          },function (err) {
            toastr.error("操作失败")
          });
        }
      }
    };
    
    
  }
})();

