/**
 * Created by lenovo on 2016/8/2.
 */
(function() {
    'use strict';

    angular
        .module('pathcloud')
        .factory('DepartmentsService', ['$http', '$q','$rootScope','toolService', function($http, $q,$rootScope, toolService) {

            var _fun = {};

            function getAndStore(){
              $http({
                method: 'GET',
                url: '[api]' + '/paramSetting/departments',
              }).success(function (res) {
                // console.info("存储科室",res)
                $rootScope.departments = res.data;
                toolService.setLocalStorageInfo('departments',$rootScope.departments);
              });
            }

            _fun.get = function () {
              var departments=toolService.getLocalStorageInfo('departments');
              // console.info("存储科室",departments)
              if(departments){
                $rootScope.departments = departments;
              }else{
                getAndStore()
              }

            };

            _fun.store = function () {
              getAndStore()
            };


            return _fun


        }]);
})();
