/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .filter('hospital', function ($rootScope,apiUrl) {


      var hospitals = $rootScope.inspectHospitalList || [];

      return function (key) {

        var hospitalName;

        if(key){
          hospitals.map(function (item,index) {
            if(item.code === key){
              hospitalName = item.name;
            }
          });
          return hospitalName||""
        }
      }

    });

})();
