/**
 * Created by Administrator on 2016/12/1.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('scanInput', scanInput);

  /** @ngInject */

  function scanInput() {
    var directive = {
      restrict: 'E',
      scope: {
        model: '=',
        getData: '&',
        placeholder: '@'
      },
      template: '<input id="scanInput" class="form-control" type="text" ng-model="scan.model" ng-keyup="scan.getFilterList($event)" auto-focus="true" placeholder="{{scan.placeholder}}"> ',
      controller: ScanInputController,
      controllerAs: 'scan',
      bindToController:true
    };
    return directive;

    /** @ngInject */

    function ScanInputController() {
      var scan = this;

      console.warn(scan.placeholder);
      scan.getFilterList=function (e) {
        var keyCode = window.event?e.keyCode:e.which;
        if(keyCode==13){
          scan.getData();
          $('#scanInput').focus();
          $('#scanInput').select();
        }
      };
    }

  }

})();
