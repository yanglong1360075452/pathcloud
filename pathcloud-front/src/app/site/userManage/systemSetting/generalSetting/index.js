/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('GeneralTemplateController', GeneralTemplateController);

  /** @ngInject */
  function GeneralTemplateController(toastr,IhcService) {
    var GeneralTpl = this;
    //获取试剂用量配置
    IhcService.get('/systemSetting/reagentUsage').then(function(res){
      GeneralTpl.model=res;
    });
    //修改试剂用量
    GeneralTpl.changeData = function(){
      IhcService.put('/systemSetting/reagentUsage/'+GeneralTpl.model).then(function(res){
        toastr.success("修改成功");
      },function(err){
        toastr.error("修改失败",err);
      });
    };
  }
})();

