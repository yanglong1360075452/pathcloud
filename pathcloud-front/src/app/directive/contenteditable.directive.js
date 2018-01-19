/**
 * Created by Administrator on 2016/12/1.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('contenteditable', contenteditable);

  /** @ngInject */
  function contenteditable() {
    return {
      require: 'ngModel',
      link: function(scope, element, attrs, ctrl) {

        element.bind('keyup', function() {
          scope.$apply(function() {
            var html=element.html();

            ctrl.$setViewValue(html);
          });
        });
        // view -> model
        element.bind('blur', function() {
          scope.$apply(function() {
            ctrl.$setViewValue(element.html());
          });
        });

        // model -> view
        ctrl.$render = function() {
          element.html(ctrl.$viewValue);
        };

      }
    }

  }

})();
