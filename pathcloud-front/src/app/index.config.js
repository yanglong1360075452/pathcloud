(function() {
  'use strict';

  angular
    .module('pathcloud')
    .config(config)

  /** @ngInject */
  function config( toastrConfig,$translateProvider) {
    // Enable log
    // $logProvider.debugEnabled(true);

    // Set options third-party lib
    // toastrConfig.allowHtml = true;
    toastrConfig.timeOut = 2000;
    // toastrConfig.positionClass = 'toast-top-right';
    // toastrConfig.positionClass = 'sr-only';
    //  toastrConfig.preventDuplicates = true;  //一定时间内显示一次
    toastrConfig.preventOpenDuplicates = true; //防止重复点击时同一个提示重复显示
    // toastrConfig.progressBar = true;
    // toastrConfig.maxOpened=1;


    // console.info("翻译插件",$translateProvider)
    var lang = window.localStorage.lang||'cn';


    $translateProvider.useStaticFilesLoader({
      prefix: './assets/i18n/',
      suffix: '.json'
    });



    $translateProvider.preferredLanguage(lang);
    $translateProvider.use(lang);
    $translateProvider.fallbackLanguage(lang);

  }

})();
