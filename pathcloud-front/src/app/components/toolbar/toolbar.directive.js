/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';
  angular
    .module('pathcloud')
    .directive('toolbar', toolbar);
  /** @ngInject */

  function toolbar() {
    var directive = {
      transclude: true,
      template: '<div class="toolbar"><div class=" container"><div class="toolbar-container" ng-transclude></div></div></div>',
      restrict: 'E'

    };
    return directive;
  };

})();
