(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('monthSelector', monthSelector);

  /** @ngInject */
  function monthSelector() {
    var directive = {
      restrict: 'E',
      scope: {
        month: '='
      },
      template:
      '<select class="form-control input-sm" ng-model="month" ng-options="item as item+vm.unit for item in vm.monthList "></select>' ,
      controller: function () {
        var vm = this;
        vm.monthList = [1,2,3,4,5,6,7,8,9,10,11,12];
        vm.unit = '月';
      },
      controllerAs: 'vm',
      bindToController:false  //ng-model 不绑定到 vm下
    };
    return directive;

  }

})();
