/**
 * Created by lenovo on 2016/12/22.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('notificationService',['$http','$q',function($http,$q,$cookieStore){

      var url = '[api]';

      return{

        // ### 6.1 消息列表  GET  /api/notification
        getNotificationLIst:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/notification',
            params:params
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        // ### 6.2 消息详细  GET  /api/notification/{id}
        getNotificationDetial:function(id){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/notification/'+id,
            param:id
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ### 6.3 消息总数 get /api/notification/sum
        getNotificationTotal:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/notification/sum',
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },




      }
    }]);
})();
