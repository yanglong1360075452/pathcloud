(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('TestController', TestController);

  /** @ngInject */
  function TestController($uibModal,$log) {
    var test = this;
    test.text="aaa";
    test.testModal=function(){
      //console.log(index);
      var modalInstance = $uibModal.open({
        animation:true,
        templateUrl: 'app/site/userManage/modal/authorize.html',
        controller: 'AuthorizeController',
        controllerAs: 'authorize',
        backdrop:'static',
        size:'lg',
        resolve: {

        }
      });

      modalInstance.result.then(function (text) {
        test.text=text;
      }, function () {
        $log.info('Cancel input');
      });
    };
    test.testRegistModal=function(){
      //console.log(index);
      var modalInstance = $uibModal.open({
        animation:true,
        templateUrl: 'app/site/sampleRegistration/modal/printModal.html',
        controller: 'PrintModalController',
        controllerAs: 'printModal',
        backdrop:'static',
        size:'lg',
        resolve: {

        }
      });

      modalInstance.result.then(function (text) {
        test.text=text;
      }, function () {
        $log.info('Cancel input');
      });
    };
    test.testRegistModal();

    test.showModal=function(){
      //console.log(index);
      var modalInstance = $uibModal.open({
        animation:true,
        templateUrl: 'app/site/modal/textarea.html',
        controller: 'TextareaModalController',
        controllerAs: 'textMod',
        size: 'large',
        backdrop:'static',
        resolve: {
          text: function () {
            return  angular.copy(test.text);
          }
        }
      });

      modalInstance.result.then(function (text) {
        test.text=text;
      }, function () {
        $log.info('Cancel input');
      });
    }

  }
})();
