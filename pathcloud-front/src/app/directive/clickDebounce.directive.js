/**
 * Created by Administrator on 2016/12/1.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('clickDebounce', clickDebounce);

  /** @ngInject */
  function clickDebounce($timeout) {
    var delay = 1000;

    return {
      restrict: 'A',
      priority: -1,   // cause out postLink function to execute before native `ngClick`'s
                      // ensuring that we can stop the propagation of the 'click' event
                      // before it reaches `ngClick`'s listener
      link: function (scope, elem) {
        var disabled = false;

        function onClick(evt) {
          if (disabled) {
            // console.error("不要重复点击")
            evt.preventDefault();
            evt.stopImmediatePropagation();
          } else {
            disabled = true;
            $timeout(function () { disabled = false; }, delay, false);
          }
        }

        scope.$on('$destroy', function () { elem.off('click', onClick); });
        elem.on('click', onClick);
      }
    };

  }

})();
