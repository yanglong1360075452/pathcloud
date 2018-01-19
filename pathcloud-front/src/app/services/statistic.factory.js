/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('StatisticService',['$http','$q',function($http,$q,$cookieStore){
    //统计报表接口规格
      var url = '[api]';

      return{

        //#### 1.1.1 制片质量 GET /api/statistics/slideQuality
        //请求参数 startTime:开始时间 endTime:结束时间
        slideQuality:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/slideQuality',
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

        //#### 1.1.2 检查类别 GET /api/statistics/inspectCategory
        inspectCategory:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/inspectCategory',
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

        //#### 1.1.3 重补取 GET /api/statistics/reGrossing
        //请求参数 startTime:开始时间 endTime:结束时间
        reGrossing:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/reGrossing',
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

        //#### 1.1.4 特染/常规 病例数统计 GET /api/statistics/specialDye
        //请求参数 无
        specialDye:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/specialDye'
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //#### 1.1.5 工作量统计 GET /api/statistics/workLoad
        //请求参数 startTime:开始时间 endTime:结束时间 workStation:工位  1-取材 3-脱水 5-包埋 6-切片
        workLoad:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/workLoad',
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

        //#### 1.1.6 平均等待时间统计 GET /api/statistics/waitTime
        //请求参数 无
        waitTime:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/waitTime'
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //#### 1.1.7 质控评分排行 GET /api/statistics/score
        //请求参数 startTime:开始时间 endTime:结束时间
        score:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/score',
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

        //#### 1.1.8 重切 GET /api/statistics/reSection
        //请求参数 startTime:开始时间 endTime:结束时间
        reSection:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/reSection',
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

        //#### 1.1.9 异常样本-蜡块 GET /api/statistics/error
        //请求参数 startTime:开始时间 endTime:结束时间
        error:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/errorBlock',
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

        //#### 1.2.1 按月统计 GET /statistics/workload/monthInspectCategory
        //请求参数 startTime:开始时间 endTime:结束时间 hospital:医院
        monthInspectCategory:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/workload/monthInspectCategory',
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

        //#### 1.2.2 按人统计 GET /statistics/workload/personInspectCategory
        //请求参数 page:页数 length:每页记录数 startTime:开始时间 endTime:结束时间 station:工位
        personInspectCategory:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/workload/personInspectCategory',
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

        // 1.2.3 按组统计  GET   statistics/workload/groupInspectCategory
        groupInspectCategory:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/workload/groupInspectCategory',
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

        /*质控评分部分*/
        // 1.3.2 按人统计 GET /statistics/quality/person
        qualityPersonStatistics:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/quality/person',
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

        // 1.3.1 按月统计 GET /statistics/quality/month
        qualityMonthStatistics:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/quality/month',
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

        /*冰冻预约部分*/
        freezeAppointmentStatistics:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/statistics/freeze/booking',
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

        //## 8 系统管理-质控评分
        //### 8.1 添加质控评分 POST /paramSetting/qualityScore
        addQualityGrade:function(data){
          var deferred = $q.defer();
          $http({
            method:'post',
            url:url + '/paramSetting/qualityScore',
            data:data
          }).then(
            function(result){
              if(result.data.code===0){
                deferred.resolve(result.data.data);
              }else{
                deferred.reject(result.data.reason);
              }
            }
          );
          return deferred.promise;
        },

        //### 8.2 查询质控评分 GET /paramSetting/qualityScore
        getQualityGrade:function(){
          var deferred = $q.defer();
          $http({
            method:'get',
            url:url + '/paramSetting/qualityScore'
          }).then(
            function(result){
              if(result.data.code===0){
                deferred.resolve(result.data.data);
              }else{
                deferred.reject(result.data.reason);
              }
            }
          );
          return deferred.promise;
        },

        //### 8.3 更改质控评分 PUT /paramSetting/qualityScore
        updateQualityGrade:function(data){
          var deferred = $q.defer();
          $http({
            method:'put',
            url:url + '/paramSetting/qualityScore',
            data:data
          }).then(
            function(result){
              if(result.data.code===0){
                deferred.resolve(result.data.data);
              }else{
                deferred.reject(result.data.reason);
              }
            }
          );
          return deferred.promise;
        },

        //### 8.4 删除质控评分 DELETE /paramSetting/qualityScore/{code}
        //参数：code | Integer | 每个工作站对应的一个code
        deleteQualityGrade:function(index){
          var deferred = $q.defer();
          $http({
            method:'delete',
            url:url + '/paramSetting/qualityScore/' + index
          }).then(
            function(result){
              if(result.data.code===0){
                deferred.resolve(result.data.data);
              }else{
                deferred.reject(result.data.reason);
              }
            }
          );
          return deferred.promise;
        }

      }
    }]);
})();
