/**
 * Created by lenovo on 2016/12/22.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('paramSettingService',['$http','$q',function($http,$q,$cookieStore){

      var url = '[api]';

      return{
        // ### 1.1.2 添加常用拒收原因
        // {
        //   "param": "rejectReason",
        //   "name":"信息不匹配" #拒收原因
        // }
      // {
      //   "param": "embedRemark",
      //   "name":"信息不匹配" #包埋备注
      // }
        setting:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/paramSetting',
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
        
        //### 1.1 拒收原因
        rejectReason:function(param){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/paramSetting/rejectReason',
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
        //### 1.2 包埋--常用包埋备注
        embedRemark:function(param){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/paramSetting/embedRemark',
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
        //### 1.3.1 获取切片备注列表
        sectionRemark:function(param){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/paramSetting/sectionRemark',
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

        // /api/paramSetting/rejectReason/{code}
        deleteRejectReason:function(code){
          var deferred = $q.defer();
          $http({
            method: 'delete',
            url: url + '/paramSetting/rejectReason/'+code
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // /api/paramSetting/rejectReason/{code}
        deleteEmbedRemark:function(code){
          var deferred = $q.defer();
          $http({
            method: 'delete',
            url: url + '/paramSetting/embedRemark/'+code
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        // 1.3.3 删除切片备注 /api/paramSetting/sectionRemark/{code}
        deleteSectionRemark:function(code){
          var deferred = $q.defer();
          $http({
            method: 'delete',
            url: url + '/paramSetting/sectionRemark/'+code
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
