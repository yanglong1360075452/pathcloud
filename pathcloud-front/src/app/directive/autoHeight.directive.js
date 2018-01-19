/**
 * Created by Administrator on 2016/12/1.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('autoHeight', autoHeight);

  /** @ngInject */
  function autoHeight($window) {
    return function (scope, element, attrs) {

      var winowHeight = $window.innerHeight; //获取窗口高度
      var headerHeight = 60;
      var footerHeight = 100;
      element.css('min-height', (winowHeight - headerHeight - footerHeight) + 'px');

    }
  }

})();
