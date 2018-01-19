/**
 * Created by zhanming on 16/4/15.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .config(function ($httpProvider) {
      $httpProvider.defaults.useXDomain = true;
      $httpProvider.interceptors.push('thfInterceptor');
    })
    .factory('thfInterceptor', function ($injector,$rootScope,$timeout) {


      // if(config.url.indexOf('.img')>0||config.url.indexOf('.png')>0){
      //   config.headers.Origin = window.location.hostname;
      //   console.log("image")
      // }
      var thfInterceptor = {
        request: function (config) {
          // console.log("请求时间",Date.now());
          //console.log(config.url);
          var apiUrl = $injector.get('apiUrl');
          var printUrl = $injector.get('printUrl');

          config.url = config.url.replace('[api]', apiUrl);
          config.url = config.url.replace('[printUrl]', printUrl);

          config.withCredentials = true;

          // console.log("config",config);
          // config.headers['Content-Type']='application/json';
          config.headers.Accept='application/json';



          return config;
        },
        response: function (response) {

          return response;
        },
        responseError: function (response) {
          // var toastr = $injector.get('toastr');
          // toastr.error("操作失败！")
          //console.log(response);


          var toastr = $injector.get('toastr');
          var $state = $injector.get('$state');

          if(response.status == 403){
            $state.go("app.home");
            toastr.error("您没有权限访问该页面","权限不足")
          }

          if (response.status == 401) {
            //console.log(response.status);
            //暂存登陆后重定向的地址
            var $rootScope = $injector.get('$rootScope');
            $rootScope.redirect = {
              name: $state.current.name === 'login' ? 'app.home' : $state.current.name,
              params: $state.params
            };

            $state.go("login");
          }
          return response;
        }
      };
      return thfInterceptor;
    });
})();

/*
// var requestList=[];

// function judgeStopLoading(response) {
//   $timeout(function () {
//     if (response.config&&
//       response.config.url.indexOf('.html')<0&&
//       response.config.url.indexOf('/notification/sum')<0&&
//       response.config.url.indexOf('/application')<0&&
//       response.config.url.indexOf('localhost')<0) {
//       var i=0;
//       requestList.forEach(function(url){
//         if(url===response.config.url)
//           requestList.splice(i,1);
//         i++
//       });
//
//       if(requestList.length === 0){
//         $rootScope.loading = false;
//       }
//     }
//
//   })
//
// }
/!*loading 加载 注释*!/
// if (config.url.indexOf('.html')<0&&
//   config.url.indexOf('/notification/sum')<0&&
//   config.url.indexOf('/application')<0&&
//   config.url.indexOf('localhost')<0) {
//   requestList.push(config.url);
//   $rootScope.loading = true;
// }*/
