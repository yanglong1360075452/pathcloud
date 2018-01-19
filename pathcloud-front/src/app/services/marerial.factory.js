/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
    'use strict';

    angular
        .module('pathcloud')
        .factory('MaterialService', ['$http', '$q', function($http, $q, $cookieStore) {

            var url = '[api]';
            return {

                //2.1 获取取材模板
                getTemplate: function(parent,position) {
                    var deferred = $q.defer();
                    $http({
                        method: 'get',
                        url: url + '/template',
                        params: { parent: parent, position: position }
                    }).then(function(result) {
                        console.log(result);
                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                //2.2 创建取材模板
                createTemplate: function(data) {
                    var deferred = $q.defer();
                    //todo 待删除

                    $http({
                        method: 'post',
                        url: url + '/template',
                        data: data
                    }).then(function(result) {
                        console.log(result);
                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                renameTemplate: function(templateId, data) {
                    var deferred = $q.defer();
                    $http({
                        method: 'put',
                        url: url + '/template/rename/' + templateId,
                        data: data
                    }).then(function(result) {
                        console.log(result);
                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                updateTemplate: function(templateId, content) {
                    var deferred = $q.defer();
                    $http({
                        method: 'put',
                        url: url + '/template/edit/' + templateId,
                        data: { content: content }
                    }).then(function(result) {
                        console.log(result);
                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                deleteTemplate: function(templateId) {
                    var deferred = $q.defer();
                    $http({
                        method: 'delete',
                        url: url + '/template/' + templateId
                    }).then(function(result) {
                        console.log(result);
                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                //### 2.5 常用模板 GET /api/template/used
                getTemplateUsed: function(data) {
                    var deferred = $q.defer();
                    $http({
                        method: 'get',
                        url: url + '/template/used',
                        params: { position: data }
                    }).then(function(result) {

                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },
                //### 2.6 常用模板设置 * __Method PUT   URL  /api/template/setting/{templateId}
                //   position | Integer | 模板位置 #1是取材模板 2是诊断模板 3是特染模板 4是报告摸板
                setTemplateUsed: function(templateId, position) {
                    var deferred = $q.defer();
                    $http({
                        method: 'put',
                        url: url + '/template/setting/' + templateId,
                        data: position
                    }).then(function(result) {
                        console.log(result);
                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },




                //1.2 获取取材医生列表 Method GET  URL /api/user/grossing
                getGrossingUser: function() {
                    var deferred = $q.defer();
                    $http({
                        method: 'get',
                        url: url + '/user/grossing',

                    }).then(function(result) {

                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                // 1.3 获取送检科室列表 Method GET URL /api/paramSetting/departments
                getDepartments: function(filter) {
                    var deferred = $q.defer();
                    $http({
                        method: 'get',
                        url: url + '/paramSetting/departments',
                        params: { filter: filter }
                    }).then(function(result) {
                        console.log(result);
                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                //1.4 取材记录查询 Method GET URL /api/grossing
                getGrossingList: function(params) {
                    var deferred = $q.defer();
                    $http({
                        method: 'get',
                        url: url + '/grossing',
                        params: params
                    }).then(function(result) {
                        console.log(result);
                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                // 1.5 取材保存  Method POST  URL /api/grossing/{id}  //id | int | 病理ID
                saveMaterial: function(data, id) {
                    var deferred = $q.defer();
                    $http({
                        method: 'post',
                        url: url + '/grossing/' + id,
                        data: data
                    }).then(function(result) {
                        console.log(result);
                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                //1.6 取材确认记录查询 Method GET URL /api/grossing/forConfirm
                getConfirmGrossingList: function(params) {
                    var deferred = $q.defer();
                    $http({
                        method: 'get',
                        url: url + '/grossing/forConfirm',
                        params: params
                    }).then(function(result) {
                        console.log(result);
                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                //2.1 获取取材确认脱水篮列表 Method GET URL /api/basket/grossing 11-待取材确认
                getGrossingBasketList: function(param) {
                    var deferred = $q.defer();
                    $http({
                        method: 'get',
                        url: url + '/basket/grossing',
                        param: param
                    }).then(function(result) {
                        console.log(result);
                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                //3.1 取材确认操作 Method POST URL /api/grossing/confirm
                confirmMaterial: function(data) {
                    var deferred = $q.defer();
                    $http({
                        method: 'post',
                        url: url + '/grossing/confirm',
                        data: data
                    }).then(function(result) {
                        console.log(result);
                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                // 4.1 获取取材标识列表  Method GET URL /api/paramSetting/blockBiaoshi
                getBiaoshi: function() {
                    var deferred = $q.defer();
                    $http({
                        method: 'get',
                        url: url + '/paramSetting/blockBiaoshi',

                    }).then(function(result) {

                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                // 4.2 获取蜡块编码方式 Method GET URL /api/paramSetting/blockCountType
                getCountType: function() {
                    var deferred = $q.defer();
                    $http({
                        method: 'get',
                        url: url + '/paramSetting/blockCountType',

                    }).then(function(result) {

                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                setCountType: function(code) {
                    var deferred = $q.defer();
                    $http({
                        method: 'put',
                        url: url + '/paramSetting/blockCountType/' + code,

                    }).then(function(result) {

                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                // 4.3 获取组织数单位 Method GET URL /api/paramSetting/blockUnit
                getBlockUnit: function() {
                    var deferred = $q.defer();
                    $http({
                        method: 'get',
                        url: url + '/paramSetting/blockUnit',
                    }).then(function(result) {

                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },


                //添加标示
                addBiaoshi: function(biaoShi) {
                    biaoShi.param = "blockBiaoshi";
                    console.log(biaoShi);
                    var deferred = $q.defer();
                    $http({
                        method: 'post',
                        url: url + '/paramSetting',
                        data: biaoShi
                    }).then(function(result) {

                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                //添加组织单位
                addBlockUnit: function(blockUnit) {
                    blockUnit.param = "blockUnit";
                    var deferred = $q.defer();
                    $http({
                        method: 'post',
                        url: url + '/paramSetting',
                        data: blockUnit
                    }).then(function(result) {

                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                delBiaoshi: function(code) {

                    var deferred = $q.defer();
                    $http({
                        method: 'delete',
                        url: url + '/paramSetting/blockBiaoshi/' + code
                    }).then(function(result) {

                        if (result.data.code === 0)
                            deferred.resolve(result.data.data);
                        else
                            deferred.reject(result.data.reason);
                    });
                    return deferred.promise;
                },

                delBlockUnit: function(code) {

                    var deferred = $q.defer();
                    $http({
                        method: 'delete',
                        url: url + '/paramSetting/blockUnit/' + code
                    }).then(function(result) {

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
