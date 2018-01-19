/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('ProductService',['$http','$q',function($http,$q,$cookieStore){

      var url = '[api]';

      return{

        //### 1.1 获取制片确认的信息 POST /api/production
        getConfirmationList:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/production',
            data: data,
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 1.2 通过病理号，或病理号加蜡块号获取信息
        getConfirmationListByPathology:function(pathologySerialNumber,blockSubId){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/production/'+pathologySerialNumber,
            // params:{blockSubId:blockSubId}
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 1.3制片确认
        getProductionConfirm:function(data){
            var deferred = $q.defer();
            $http({
              method: 'post',
              url: url + '/production/confirm',
              data: data,
            }).then(function (result){
              console.log(result);
              if (result.data.code === 0)
                deferred.resolve(result.data.data);
              else
                deferred.reject(result.data.reason);
            });
            return deferred.promise;
          },

        //### 1.4 制片确认异常处理
        setProductionAbnormal:function(data){
            var deferred = $q.defer();
            $http({
              method: 'post',
              url: url + '/production/abnormal',
              data: data,
            }).then(function (result){
              console.log(result);
              if (result.data.code === 0)
                deferred.resolve(result.data.data);
              else
                deferred.reject(result.data.reason);
            });
            return deferred.promise;
          },

        //### 2.1 获取待派片记录 GET /api/distribute
        getDistributeList:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/distribute',
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

        //### 2.2 派片 POST /api/distribute
        doDistribute:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/distribute',
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

        //### 2.3 获取派片历史记录 GET /api/distribute/history
        getDistributedList:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/distribute/history',
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

        //### 2.4 根据病理号查询派片信息 GET /api/distribute/history/{pathNo}
        searchProductionList:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/distribute/history/'+ data.pathNo,
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

        // ### 3.1 样本状态统计 GET  /api/distribute/statistic
        statisticData:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/distribute/statistic',
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

        // ### 3.2 异常样本详细 GET /api/distribute/abnormal/{status}
        abnormalDataList:function(data,status){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/distribute/abnormal/'+status,
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

          // 3.3 样本状态异常处理
          // Method POST
          //
          // URL /api/distribute/abnormalBlockDeal
        // Body {
        // "blockId": 10,
        // "dealType": 1, #处理方式，1-通知技术员，2-标记为异常，申请重补取
        // "note": "", #备注
        // }

      dealAbnormalBlock:function(data){
        var deferred = $q.defer();
        $http({
          method: 'post',
          url: url + '/distribute/abnormalBlockDeal',
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
        getProductionScan:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/production/scan/result'
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        }


    }
    }]);
})();
