/**
 * Created by Administrator on 2017/6/6.
 */

(function() {
  'use strict';

  angular
  .module('pathcloud')
  .controller('QueryrchiveController', QueryrchiveController);

  /** @ngInject */
  function QueryrchiveController($scope,$interval,toastr,IhcService){
    var queryArchive=this;
    //二级导航 页面分了两个部分
    queryArchive.activeMenu=0;
    queryArchive.choseMenu=function (index) {
      queryArchive.activeMenu=index;
    };


  }
})();

