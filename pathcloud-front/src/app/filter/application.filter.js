/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .filter('applicationStatus', function () {
      return function (status) {
        switch (status) {
          case 1:
            return "未登记";
            break;
          case 2:
            return "已登记"
            break;
          case 3:
            return "已拒绝"
            break;
        }
      }
    })
    .filter('sex', function () {
      return function (sex) {
        switch (sex) {
          case 0:
            return "";
            break;
          case 1:
            return "男"
            break;
          case 2:
            return "女"
            break;
        }
      }
    })

})();
