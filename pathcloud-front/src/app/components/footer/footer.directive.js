/**
 * Created by zhanming on 16/4/12.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('thfFooter', thfFooter);

  /** @ngInject */
  function thfFooter() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/footer/footer.html'
    };

    return directive;
  }

})();
