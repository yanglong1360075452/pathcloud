/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('GrossingService',['$http','$q',function($http,$q,$cookieStore){

      var url = '[api]';

      return{

        //脱水篮相关
        getUserBasketList:function(status){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/basket/grossing',
            params:{status:status}
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        getBasketList:function(status,params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/basket/dehydrate/'+status,
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

        //### 5.1 待脱水记录查询 // // GET /api/grossing/forDehydrate
        getDehydrateList:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/grossing/forDehydrate',
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

        //5.2 添加/更改脱水机 Method  POST URL /api/dehydrator
        addOrUpdateGrossing:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/dehydrator',
            data: data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //5.3 获取所有脱水机 Method GET URL /api/dehydrator/list
        getDehydratorList:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/dehydrator',
            params: params,
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //5.3 获取所有脱水机不加分页 Method GET URL /api/dehydrator
        getNoLengthDehydratorList:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/dehydrator/list',
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //5.4 删除脱水机 Method DELETE URL /api/dehydrator/{instrumentId}
        deleteGrossing:function(instrumentId){
            var deferred = $q.defer();
            $http({
              method: 'delete',
              url: url + '/dehydrator/' + instrumentId
            }).then(function (result){
              console.log(result);
              if (result.data.code === 0)
                deferred.resolve(result.data.data);
              else
                deferred.reject(result.data.reason);
            });
            return deferred.promise;
          },

        //5.5 获取所有脱水机状态摘要 Method GET URL /api/dehydrator/status
        getDehydratorAndStatusList:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/dehydrator/status'
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        getDehydratorError:function(instrumentId,params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/dehydrator/'+instrumentId+'/errmsg',
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

        //### 5.7 开始脱水  POST  /api/dehydrate/start
        removealarm:function(instrumentId){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/dehydrator/'+instrumentId+'/removealarm '
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.8 开始脱水  POST  /api/dehydrate/start
        startDehydrate:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/dehydrate/start',
            data: data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.10 结束脱水  POST  /api/dehydrate/end
        stopDehydrate:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/dehydrate/end',
            data: data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.11 校验脱水机编号 GET /api/dehydrator/check/name/{name}
        checkName:function(name){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/dehydrator/check/name/'+ name
          }).then(function (result){
            deferred.resolve(result.data.data);
          });
          return deferred.promise;
        },

        //### 5.12 校验脱水机序列号 GET /api/dehydrator/check/sn/{sn}
        checkSn:function(sn){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/dehydrator/check/sn/' + sn
          }).then(function (result){
            deferred.resolve(result.data.data);
          });
          return deferred.promise;
        },
        // 1.1 查看运行中脱水机蜡块信息  GET  /api/dehydrator/{instrumentId}/blocks
        dehydratorBlocks:function(instrumentId,filter){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/dehydrator/' + instrumentId +'/blocks',
            params:filter
          }).then(function (result){
            deferred.resolve(result.data.data);
          });
          return deferred.promise;
        },

        //### 5.13 结束脱水  POST  /api/dehydrate/end
        pauseDehydrate:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/dehydrate/pause',
            data: data
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
