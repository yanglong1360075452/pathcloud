/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .filter('department', function ($rootScope,apiUrl) {


      var departments = $rootScope.departments || [];

      return function (key) {

        var departmentName;

        if(key){
          departments.map(function (item,index) {
            if(item.code === key){
              departmentName = item.name;
            }
          });
          return departmentName||""
        }
      }

    });

})();
