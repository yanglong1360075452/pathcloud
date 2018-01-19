(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('departmentSelector', departmentSelector);

  /** @ngInject */
  function departmentSelector() {
    var directive = {
      restrict: 'E',
      scope: {
        type: '@',
        departmentset: '=',
        change: '&'
      },
      template:
        "<select class='form-control input-sm' ng-model='vm.departmentset' ng-options='item.code as item.name for item in vm.departmentList' ng-change='vm.change()'>" +
        "<option value=''>{{'送检科室'|translate}}</option></select>",
      controller: departmentSelectorController,
      controllerAs: 'vm',
      bindToController: true
    };
    return directive;

    /** @ngInject */
    function departmentSelectorController($rootScope) {
      var vm = this;
      vm.departmentList=$rootScope.departments;
    }
  }
})();
