/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  // angular
  //   .module('pathcloud')
  //   .filter('T', function ($translate) {
  //     return function (key) {
  //       console.info(key)
  //       console.info($translate.instant())
  //       if(key){
  //         return $translate.instant(key);
  //       }
  //     }
  //   });
  angular.module('pathcloud').factory('T', ['$translate', function($translate) {
    var T = {
      T:function(key) {
        if(key){
          return $translate.instant(key);
        }
        return key;
      }
    }
    return T;
  }]);
})();
