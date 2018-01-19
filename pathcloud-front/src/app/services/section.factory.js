/**
 * Created by lenovo on 2016/12/22.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('sectionService',['$http','$q', 'printerService', function($http,$q, printerService){

      var url = '[api]';

      return{
        
        print: function (res, printType) {
          // 判断标签纸打印 还是玻片 labwriter 打印
  
          if(printType == 1){ //labWriter玻片打印
            var labWritePrintData = {
              id: [],
              type: []
            };
            angular.forEach(res, function (item, index) {
              var id=item.pathNo+"$"+item.blockSubId+"$"+item.slideSubId;
              var type = item.grossingBody + ";" + item.marker;
              labWritePrintData.id.push(id);
              labWritePrintData.type.push(type)
            });
            //通过后端返回的玻片信息数据 打印到 labwriter 打印机中
            printerService.labWrite(labWritePrintData.id.join(),labWritePrintData.type.join())
    
          }else {
            // 通过 纸制标签打印机打印  要打印 病理号
            angular.forEach(res, function (item, index) {
              var printData = {
                pathologySerialNumber: res.pathNo,
                subId: res.blockSubId,
                lastSlideSubId: res.slideSubId,
                bodyPart: res.grossingBody,
                marker: res.marker
              };
              printData.code = res.pathNo +"-"+res.blockSubId +"-" +res.slideSubId;//二维码内容
              // 标签打印一次打印一个标签 通过 html 打印
              printerService.printSlide(printData)
            })
    
          }
        },
        
        
        //### 3.1 获取蜡块信息 /api/section/{blockSerialNumber}

        getBlockInfo:function(blockSerialNumber,params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/section/'+blockSerialNumber,
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

        // ### 3.2 切片确认
        // * __URL__
        // /api/section/confirm/{blockId}

        confirmSection:function(data,id){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/section/confirm/'+id,
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

        confirmSpecialDyeSection:function(arr){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/section/confirm',
            data:arr
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

      // ### 3.3 获取待切片蜡块数

        sectionCount:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/section/count',
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ## 6 切片信息查询
        sectionSearch:function(param){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/section/query',
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
        // ## 7 切片技术员
        sectionPerson:function(param){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/section/person',
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

// ### 8.1 根据蜡块ID获取玻片信息
        // ##
        getSlidesInfo:function(blockId,param){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/section/'+blockId+'/slides',
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

      }
    }]);
})();
