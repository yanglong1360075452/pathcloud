/**
 * Loading Directive
 * @see http://tobiasahlin.com/spinkit/
 */

angular
    .module('pathcloud')
    .directive('rdLoading', rdLoading);

function rdLoading() {
    var directive = {
        restrict: 'AE',
        template: '<div class="loading-bg"><div class="loading"><div class="double-bounce1"></div><div class="double-bounce2"></div></div></div>'
    };
    return directive;
};
