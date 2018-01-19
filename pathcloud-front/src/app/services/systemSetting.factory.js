/**
 * Created by lenovo on 2016/12/22.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('SystemSettingService',['$http','$q',function($http,$q,$cookieStore){

      var url = '[api]';

      return{

        //### sprint9  获取最大默认搜索时间范围 list
        getTimeRange :function(param){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/systemSetting/queryTimeRange',
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

         //### sprint9  通用获取
        getAlarmTime :function(path){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/systemSetting/'+path,

          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        //### sprint9  通用修改
        putAlarmTime :function(path,time){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/systemSetting/'+path+'/'+time,
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### sprint7  2.1 获取病理申请单必填项  /api/systemSetting/applicationRequired

        getApplicationRequired :function(param){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/systemSetting/applicationRequired',
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


        //  特染类别获取
        getSpecialDyeList:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/systemSetting/specialDye',

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
