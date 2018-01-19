/**
 * Created by Administrator on 2016/11/9.
 */
(function(){
  'use strict';

  angular
    .module('pathcloud')
    .controller('userManageController', userManageController);

  /** @ngInject */
  function userManageController(){
    var userManage = this;
    userManage.activeMenu = 0;

    userManage.choseMenu = function(index){
      userManage.activeMenu = index;
    };

  }
})();
