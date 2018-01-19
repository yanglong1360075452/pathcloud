(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('MainController', MainController);

  /** @ngInject */
  function MainController($timeout, webDevTec, toastr) {
    var vm = this;
    vm.toast = {
      title: '',
      message: ''
    };
    vm.awesomeThings = [];
    vm.classAnimation = '';
    vm.creationDate = 1478056022354;
    vm.showToastr = showToastr;

    activate();

    function activate() {
      getWebDevTec();
      $timeout(function() {
        vm.classAnimation = 'rubberBand';
      }, 4000);
    }
    toastr.info("", {containerId:2})

    function showToastr() {

      toastr.success("click 来点击啊啊啊")
      // toastr.info('Fork <a href="https://github.com/Swiip/generator-gulp-angular" target="_blank"><b>generator-gulp-angular</b></a>',{preventOpenDuplicates:false});
      vm.classAnimation = '';
    }

    function getWebDevTec() {
      vm.awesomeThings = webDevTec.getTec();

      angular.forEach(vm.awesomeThings, function(awesomeThing) {
        awesomeThing.rank = Math.random();
      });
    }
  }
})();
