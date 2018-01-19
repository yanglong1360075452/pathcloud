/**
 * Created by lenovo on 2016/11/9.
 * ###染色工作台###
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('IhcService',['$http','$q',function($http,$q){

      var API = '[api]';

      return{


        // ### 6.4 查询特染申请
        myBlocks:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: API + '/ihc/blocks',
            params:params
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason); //提示参数错误
          });
          return deferred.promise;
        },

        get:function(url,params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: API + url,
            params:params
          }).then(function (result){
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data); //显示时获取 result.data.reason
          });
          return deferred.promise;
        },
        getLocal:function(url,params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url,
            params:params
          }).then(function (result){
            console.log("本地结果",result);
            if (result.data)
              deferred.resolve(result.data);
            else
              deferred.reject(result.data); //显示时获取 result.data.reason
          });
          return deferred.promise;
        },

        post:function(url,data,headers){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: API + url,
            data:data,
            headers:headers
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data);//显示时获取 result.data.reason
          });
          return deferred.promise;
        },
        put:function(url,data){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: API + url,
            data:data

          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data);//显示时获取 result.data.reason
          });
          return deferred.promise;
        },
        delete:function(url,data){
          var deferred = $q.defer();
          $http({
            method: 'delete',
            url: API + url,
            data:data,
            headers:{
              "Content-Type": 'application/json'
            }
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data);//显示时获取 result.data.reason
          });
          return deferred.promise;
        },




      }
    }]);
})();
