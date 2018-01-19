/**
 * Created by lenovo on 2016/12/22.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('embedService',['$http','$q',function($http,$q,$cookieStore){

      var url = '[api]';

      return{

        //### 2.1 获取蜡块信息 /api/embed/{blockSerialNumber}
        getBlockInfo:function(blockSerialNumber,prePathId){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/embed/'+blockSerialNumber,
            params:{
              blockSerialNumber:blockSerialNumber,
              prePathId:prePathId,
            }
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ### 2.2 包埋确认
        // * __URL__
        // /api/embed/confirm/{blockId}
        confirmEmbed:function(data,id){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/embed/confirm/'+id,
            data:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

      // /api/embed/count
        embedCount:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/embed/count',
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

      // ## 5 包埋信息查询
      embedSearch:function(param){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/embed/query',
            params:param
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        // ## 5 包埋信息查询
        embedPerson:function(param){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/embed/person',
            // params:param
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        }



      }
    }]);
})();
