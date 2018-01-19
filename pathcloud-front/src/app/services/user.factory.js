/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('UserService',['$http','$q',function($http,$q,$cookieStore){

      var url = '[api]';

      return{
        login:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/login',
            data:data,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            transformRequest: function(obj) {
              var str = [];
              for(var p in obj)
                str.push(encodeURIComponent(p) + "=" + encodeURIComponent(obj[p]));
              return str.join("&");
            },

          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.data);
          });
          return deferred.promise;
        },
        loginByQrcode:function(code){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/user/qrcode/'+code

          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.data);
          });
          return deferred.promise;
        },
        logout:function(){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/logout',
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        getRoleAuthList:function(filters){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/role',
            params:filters||{length:50}
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        getAuthList:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/permissions'
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        createRole:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/role',
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
        updateRole:function(id,data){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/role/'+id,
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
        deleteRole:function(id){
          var deferred = $q.defer();
          $http({
            method: 'delete',
            url: url + '/role/'+id
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        updateRoleList:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/role/setting',
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
        editUser:function(data,id){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/user/'+id,
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
        createUser:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/user',
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
        getUser:function(id){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/user/'+id,
            // params:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        getUserList:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/user',
            params:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        check:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/user/check/'+data,
            // params:data
          }).then(function (result){
            // console.log("check name result====",result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.data);
          });
          return deferred.promise;
        },
        changePwd:function(data){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/user/resetPassword',
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




      }
    }]);
})();
