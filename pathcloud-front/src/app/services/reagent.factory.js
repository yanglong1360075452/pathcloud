(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('ReagentService',['$http','$q',function($http,$q){
      var url = '[api]';
      return{
        checkName:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/reagent/name',
            params:data
          }).then(function (result){
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        }
      };
    }]);
})();
