(function() {
  'use strict';

  angular
    .module('pathcloud', ['ngAnimate', 'ngCookies', 'ui.router', 'ui.bootstrap' , 'toastr','monospaced.qrcode','ngSanitize','ng-echarts','pascalprecht.translate']);


})();


/*.config(['$translateProvider',function($translateProvider){ //i18n 翻译插件配置
 var lang = window.localStorage.lang||'cn';
 // $translateProvider.translations('en',{
 //   'profile':'Hello',
 // });
 //
 // $translateProvider.translations('cn',{
 //   'profile':'繁体你好',
 // });
 $translateProvider.preferredLanguage('cn');
 $translateProvider.fallbackLanguage('cn');

 $translateProvider.useStaticFilesLoader({
 prefix: 'i18n/',
 suffix: '.json'
 });


 // console.info("加载i18n json文件",a)
 }]);*/
