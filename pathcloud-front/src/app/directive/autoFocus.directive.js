/**
 * Created by Administrator on 2016/12/1.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('autoFocus', autoFocus);

  /** @ngInject */
  function autoFocus($timeout) {
    return function (scope, element, attrs) {
      attrs.$observe('autoFocus', function (newValue) {
        console.log('new');
        console.log(newValue);
        // element[0].blur();
        // console.info("autofocus",newValue,attrs.autoFocus,attrs.autoFocus === true)
        if(newValue != "false"){
          $timeout(function () {
            // element[0].removeAttribute('autofocus');
            // element[0].setAttribute('autofocus','autofocus');
            element[0].focus();
          })


        }

      });
    }
  }

})();
