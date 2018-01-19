/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('PathologyService',['$http','$q',function($http,$q){

      var url = '[api]';
      return{

        //1.3 病理登记查询 GET url + '/pathology/sample'
        getSampleList:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/pathology/sample',
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

       // 2.1 创建病理档案 POST /api/pathology // {"applicationId": 1},#病理申请ID  assignGrossing
        registerPathology:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/pathology',
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

        // ## 3 检查类别设置
        // ### 3.1 获取检查类别列表  GET /api/systemSetting/inspectCategory
        inspectCategoryList:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/systemSetting/inspectCategory',
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
        // ### 3.2 添加检查类别 POST /api/systemSetting/inspectCategory
        inspectCategoryCreate:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/systemSetting/inspectCategory',
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
        
        // ### 3.3 删除检查类别  DELETE  /api/systemSetting/inspectCategory/{code}
        inspectCategoryDelete:function(code){
          var deferred = $q.defer();
          $http({
            method: 'delete',
            url: url + '/systemSetting/inspectCategory/'+code,
          }).then(function (result){
           
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
      
      
        // ### 1.6拒收病理样本登记  PUT  /api/application/reject/{id}
        // params:  id | LONG | 病理申请ID  rejectReason | String |拒收申请的原因
        rejectPathology:function(id,data){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/application/reject/'+id,
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

        // 1 取材
        // 1.1 获取病理列表 Method GET  URL /api/pathology

        getPathologyList:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/pathology',
            params:params
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data);
          });
          return deferred.promise;
        },





      }
    }]);
})();
