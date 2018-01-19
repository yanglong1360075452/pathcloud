/**
 * Created by lenovo on 2016/11/9.
 * ###染色工作台###
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('DyeService',['$http','$q',function($http,$q,$cookieStore){

      var url = '[api]';

      return{

        //### 1.1 根据玻片号查询待染色玻片信息 GET /api/dye/{slideNo}
        //参数：slideNo(病理号-蜡块号-玻片号)
        getDyeInfo:function(slideNo){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/dye/' + slideNo
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data); //提示参数错误
          });
          return deferred.promise;
        },

        //### 1.2 开始染色 POST /api/dye/confirm
        //参数：dyeArray(染色玻片ID数组)
        startDye:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/dye/confirm',
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

        //### 1.3 染色信息查询 GET api/dye/query
        getDyeData:function(data){
          var deferred = $q.defer();
          $http({
            method:'get',
            url:url + '/dye/query',
            params:data
          }).then(
            function(result){
              if(result.data.code === 0){
                deferred.resolve(result.data.data);
              }else{
                deferred.reject(result.data.reason);
              }
            }
          );
          return deferred.promise;
        },

        //### 1.4 根据玻片号查询待染色玻片信息 GET /api/dye/person
        getOperator:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/dye/person'
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        //### 1.5 获取当前用户，全科待染片数 GET /api/dye/summary
        getDyeCount:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/dye/summary/16'
          }).then(function (result){
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
