/**
 * Created by Administrator on 2017/6/6.
 */

(function() {
  'use strict';

  angular
  .module('pathcloud')
  .controller('ReturnArchiveController', ReturnArchiveController);

  /** @ngInject */
  function ReturnArchiveController($scope,$interval,toastr,IhcService){
    var returnArchive=this;
    //二级导航 页面分了两个部分
    returnArchive.activeMenu=0;
    returnArchive.choseMenu=function (index) {
      returnArchive.activeMenu=index;
    };


  }
})();

