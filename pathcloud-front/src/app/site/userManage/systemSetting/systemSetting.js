/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
  .module('pathcloud')
  .controller('systemSettingModuleController', systemSettingModuleController);

  /** @ngInject */
  function systemSettingModuleController($scope,$state,toastr){
    var systemModule=this;


    systemModule.activeMenu=0;
    systemModule.choseMenu=function (index) {
      systemModule.activeMenu=index;
    };


  }
})();

