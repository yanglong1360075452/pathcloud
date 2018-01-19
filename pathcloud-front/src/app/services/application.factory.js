/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('ApplicationService',['$http','$q',function($http,$q,$cookieStore){

      var url = '[api]';
      return{

        // /api/application/booking  5 冰冻预约查询
        getFreezeBookedList:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/application/booking',
            params:params
          }).then(function (result){
            // console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //1.1 创建病例申请表 POST url + '/application'
        createApplication:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/application',
            data:data
          }).then(function (result){
            // console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        //1.1 创建科研病例申请表 POST url + '/application'
        createApplicationResearch:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/application/research',
            data:data
          }).then(function (result){
            // console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //1.3 病理申请查询 GET url + '/application'
        getApplicationList:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/application',
            params:params
          }).then(function (result){
            // console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //1.2 获取单个病理申请信息// /api/application/{serialNumber}
        getOne:function(serialNumber,pathology){
          var deferred = $q.defer();
          if(pathology){
            serialNumber = "pathology/"+serialNumber;
          }
          $http({
            method: 'get',
            url: url + '/application/'+ serialNumber,
            // params:params
          }).then(function (result){
            // console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //1.5 撤销病理申请 /api/application/cancel/{id}  PUT
        cancelOne:function(id){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/application/cancel/'+id,
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        cancelFreeze:function(id){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/application/cancel/freeze/'+id,
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ### 1.8 根据病理号获取申请单信息
//       * __Method__
//       GET
//       * __URL__
//       /api/application/pathology/{pathologyNo}

        getOneByPathologyNo:function(pathologyNo){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/application/pathology/'+pathologyNo,
            // params:params
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
