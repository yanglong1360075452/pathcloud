/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('QueryService',['$http','$q',function($http,$q,$cookieStore){

      var url = '[api]';
      return{

        // 2 病例查询

        // 2.1 按条件查询 GET  /api/query
        query:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/query',
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
        // ## 5 病例查询--科研查询 GET  /api/query/research
        queryResearch:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/query/research',
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
        // 2.1 按条件查询 GET  /api/query/export
        export:function(params){
          // $http.get(url + '/query/export',{params:params} ).success(function(data,status,headers,congfig){
          //   console.info(data)
          // }).error(function (err) {
          //   console.info(err)
          // })

          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/query/export',
            params:params,
            headers: {'Content-Type': 'application/octet-stream'}
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // 2.2 获取送检医生列表 GET /api/query/inspect
        inspectDoctorList:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/query/inspect',
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

       // ### 2.3 根据病理ID获取样本信息-基本信息 GET query/sample/{pathId}/base
        sampleBasicInfo:function(pathId){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/query/sample/'+pathId+ '/base/',

          }).then(function (result){

            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

      // ### 2.4 根据病理ID获取样本信息-取材信息
        sampleMaterialInfo:function(pathId, number){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/query/sample/'+pathId+ '/grossing',
            params:{
              number: number
            }
          }).then(function (result){

            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        // ### 2.5 根据病理ID获取样本信息-制片信息
        sampleConfirmInfo:function(pathId){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/query/sample/'+pathId+ '/confirm/',

          }).then(function (result){

            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        // todo ### 存档信息
        sampleSaveInfo:function(pathId){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/query/sample/'+pathId+ '/grossing/',

          }).then(function (result){

            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ### 2.6 获取诊断医生列表 GET  /api/user/superDiagnose
        diagnoseDoctorList:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/user/superDiagnose/',
            // params:params
          }).then(function (result){
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ### 9.1 根据病理ID获取特染信息
        specialDyeInfo:function(pathId,params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/query/specialDye/'+pathId+'/confirm',
            params:params
          }).then(function (result){
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
