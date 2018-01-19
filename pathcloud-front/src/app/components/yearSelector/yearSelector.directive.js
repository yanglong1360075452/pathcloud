(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('yearSelector', yearSelector);

  /** @ngInject */
  function yearSelector() {
    var directive = {
      restrict: 'E',
      scope: {
        year: '=',
      },
      template:
      "<select class='form-control input-sm' ng-model='vm.year' ng-options='year as year for year in vm.yearArr' '></select>" ,
      controller: YearSeletorController,
      controllerAs: 'vm',
      bindToController:true
    };
    return directive;

    /** @ngInject */
    function YearSeletorController($scope) {
      var vm=this;

      var date = new Date();
      var nowYear=date.getFullYear();
      var yearArr=[];
      for(var i=2016;i<=nowYear;i++){
        yearArr.push(i);
      }
      vm.yearArr=yearArr;

    }


  }

})();
