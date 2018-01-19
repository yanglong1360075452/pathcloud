(function () {
  'use strict';

  angular
  .module('pathcloud')
  .config(routerConfig);

  /** @ngInject */
  function routerConfig($stateProvider, $urlRouterProvider) {
    $stateProvider
    .state('main', {
      url: '/',
      templateUrl: 'app/site/main/main.html',
      controller: 'MainController',
      controllerAs: 'main'
    })
    .state('app', {
      url: '/app',
      templateUrl: 'app/site/site.html',
      controller: 'SiteController',
      controllerAs: 'site'
    })
    .state('login', {
      url: '/login',
      templateUrl: 'app/site/login/login.html',
      controller: 'LoginController',
      controllerAs: 'login'
    })
    .state('app.home', {
      url: '/home',
      templateUrl: 'app/site/home/home.html',
      controller: 'HomeController',
      controllerAs: 'home',
      onEnter: function (IhcService, $rootScope) {
        // IhcService.get("/systemSetting/usingFrozen").then(function (result) {
        //     $rootScope.usingFrozen = result; // 0-不使用 1-使用
        //     // console.info("冰冻run", $rootScope.usingFrozen)
        //     return IhcService.get("/paramSetting/inspectHospital")
        //   })
      },
      resolve:{usingFrozen: function (IhcService, $rootScope) {
        // 该方法会注入一个值到controller 并且只有在请求成功后才会渲染页面
        return IhcService.get("/systemSetting/usingFrozen").then(function (res) {
          if(res == 0){
            $rootScope.usingFrozen = true
          }else {
            $rootScope.usingFrozen = false
          }
          // debugger
          return $rootScope.usingFrozen
        })

      }}
    })
    .state('app.notification', {
      url: '/notification',
      templateUrl: 'app/site/notification/notification.html',
      controller: 'NotificationController',
      controllerAs: 'notification'
    }) //系统通知
    .state('app.profile', {
      title: "个人资料",
      url: '/profile',
      templateUrl: 'app/site/profile/profile.html',
      controller: 'ProfileController',
      controllerAs: 'profile'
    }) //

    .state('app.test', {
      url: '/test',
      templateUrl: 'app/site/test/test.html',
      controller: 'TestController',
      controllerAs: 'test'
    })
    .state('app.applicationView', {
      title: "查看申请",
      url: '/applicationView',
      templateUrl: 'app/site/application/viewApplication.html',
      controller: 'ViewApplicationController',
      controllerAs: 'viewApplication'
    })
    .state('app.applicationCreate', {
      title: "创建申请",
      url: '/applicationCreate?{type}',
      templateUrl: 'app/site/application/applyPathological.html',
      controller: 'ApplicationCreateController',
      controllerAs: 'applicationCreate',

    })

    .state('app.specialDyeCreate', { //特染申请
      title: "创建申请",
      url: '/specialDyeCreate',
      templateUrl: 'app/site/specialDye/specialDyeCreate/specialDyeCreate.html',
      controller: 'SpecialDyeCreateController',
      controllerAs: 'specialDyeCreate',

    })
    .state('app.specialDyeSearch', {
      title: "查看申请",
      url: '/specialDyeSearch',
      templateUrl: 'app/site/specialDye/specialDyeSearch/specialDyeSearch.html',
      controller: 'SpecialDyeSearchController',
      controllerAs: 'specialDyeSearch'
    })

    .state('app.sampleRegistration', {
      title: "样本登记",
      url: '/sampleRegistration',
      templateUrl: 'app/site/sampleRegistration/sampleRegistration.html',
      controller: 'SampleRegistrationController',
      controllerAs: 'sampleRegistration'
    })
     .state('app.sampleConsultationApply', {
      title: "会诊登记",
      url: '/sampleConsultationApply',
      templateUrl: 'app/site/sampleRegistration/consultationApply/index.html',
      controller: 'ConsultationApplyController',
      controllerAs: 'consultation'
    })

    .state('app.sampleSearch', {
      title: "信息查询",
      url: '/sampleSearch',
      templateUrl: 'app/site/sampleRegistration/sampleSearch.html',
      controller: 'SampleSearchController',
      controllerAs: 'sampleSearch'

    })

    .state('app.sampleSetting', {
      title: "登记设置",
      url: '/sampleSetting',
      templateUrl: 'app/site/sampleRegistration/sampleSetting.html',
      controller: 'SampleSettingController',
      controllerAs: 'sampleSetting'
    })

    //取材路由设置
    .state('app.createMaterial', {
      title: "取材",
      url: '/createMaterial',
      templateUrl: 'app/site/drawMaterial/createMaterial/createMaterial.html',
      controller: 'CreateMaterialController',
      controllerAs: 'createMaterial'
    })
    .state('app.confirmMaterial', {
      title: "取材确认",
      url: '/confirmMaterial',
      templateUrl: 'app/site/drawMaterial/confirmMaterial/confirmMaterial.html',
      controller: 'ConfirmMaterialController',
      controllerAs: 'confirmMaterial'
    })
    .state('app.searchMaterial', {
      title: "信息查询",
      url: '/searchMaterial',
      templateUrl: 'app/site/drawMaterial/searchMaterial/searchMaterial.html',
      controller: 'SearchMaterialController',
      controllerAs: 'searchMaterial'
    })
    .state('app.configMaterial', {
      title: "取材设置",
      url: '/configMaterial',
      templateUrl: 'app/site/drawMaterial/configMaterial/configMaterial.html',
      controller: 'ConfigMaterialController',
      controllerAs: 'configMaterial'
    })

    .state('app.grossing', {
      title: "脱水",
      url: '/grossing',
      templateUrl: 'app/site/grossing/grossing/grossing.html',
      controller: 'GrossingController',
      controllerAs: 'grossing'
    })

    .state('app.grossingSetting', {
      title: "查看状态",
      url: '/setting',
      templateUrl: 'app/site/grossing/setting/setting.html',
      controller: 'GrossingSettingController',
      controllerAs: 'gSetting'
    })

    .state('app.grossingStatus', {
      title: "脱水设置",
      url: '/grossingStatus',
      templateUrl: 'app/site/grossing/status/status.html',
      controller: 'GrossingStatusController',
      controllerAs: 'gStatus'
    })

    //包埋
    .state('app.embed', {
      title: "包埋",
      url: '/embed',
      templateUrl: 'app/site/embed/embed.html',
      controller: 'EmbedController',
      controllerAs: 'embed'
    })
    .state('app.embedSearch', {
      title: "包埋搜索",
      url: '/embedSearch',
      templateUrl: 'app/site/embed/embedSearch.html',
      controller: 'EmbedSearchController',
      controllerAs: 'eSearch'
    })
    .state('app.embedSetting', {
      title: "包埋设置",
      url: '/embedSetting',
      templateUrl: 'app/site/embed/embedSetting.html',
      controller: 'EmbedSettingController',
      controllerAs: 'eSetting'
    })
    //切片
    .state('app.section', {
      title: "切片",
      url: '/section',
      templateUrl: 'app/site/section/section.html',
      controller: 'SectionController',
      controllerAs: 'section'
    })
    .state('app.sectionSearch', {
      title: "切片搜索",
      url: '/sectionSearch',
      templateUrl: 'app/site/section/sectionSearch.html',
      controller: 'SectionSearchController',
      controllerAs: 'sSearch'
    })
    .state('app.sectionSetting', {
      title: "切片设置",
      url: '/sectionSetting',
      templateUrl: 'app/site/section/sectionSetting.html',
      controller: 'SectionSettingController',
      controllerAs: 'sSetting'
    })
    //染色封片
    .state('app.createDye', {
      title: "染色封片",
      url: '/createDye',
      templateUrl: 'app/site/dye/createDye/createDye.html',
      controller: 'createDyeController',
      controllerAs: 'createDye'
    })
    .state('app.searchDye', {
      title: "染色搜索",
      url: '/searchDye',
      templateUrl: 'app/site/dye/searchDye/searchDye.html',
      controller: 'searchDyeController',
      controllerAs: 'searchDye'
    })
    .state('app.DyeSealing', {
      title: "封片",
      url: '/sealing',
      templateUrl: 'app/site/dye/sealing/sealing.html',
      controller: 'sealingController',
      controllerAs: 'sealing'
    })
    .state('app.DyeSetting', {
      title: "设置",
      url: '/dyeSetting',
      templateUrl: 'app/site/dye/setting/setting.html',
      controller: 'DyeSettingController',
      controllerAs: 'dyeSetting'
    })
    //免疫组化
    .state('app.ihcSearch', { //
      url: '/ihcSearch',
      templateUrl: 'app/site/ihc/ihcSearch/ihcSearch.html',
      controller: 'IhcSearchController',
      controllerAs: 'ihcSearch'
    })
    //免疫组化 打印
    .state('app.ihcPrint', { //
      url: '/ihcPrint',
      templateUrl: 'app/site/ihc/print/ihcPrint.html',
      controller: 'IhcPrintController',
      controllerAs: 'ihcPrint'
    })

/*    .state('app.ihcCreate', { //旧的免疫组化页面
      url: '/ihcCreate',
      templateUrl: 'app/site/ihc/ihcCreate/ihcCreate.html',
      controller: 'IhcCreateController',
      controllerAs: 'ihcCreate'
    })*/

    //制片确认
    .state('app.ProConfirmation', {
      title: "制片确认",
      url: '/proConfirmation',
      templateUrl: 'app/site/product/confirmation/confirmation.html',
      controller: 'ProConfirmationController',
      controllerAs: 'proConfirmation'
    })
    .state('app.ProDistribution', {
      title: "派片",
      url: '/proDistribution',
      templateUrl: 'app/site/product/distribution/distribution.html',
      controller: 'ProDistributionController',
      controllerAs: 'proDistribution'
    })
    .state('app.ProQualityGrade', {
      title: "质控评分",
      url: '/proQualityGrade',
      templateUrl: 'app/site/product/qualityGrade/qualityGrade.html',
      controller: 'ProQualityGradeController',
      controllerAs: 'proQualityGrade'
    })
    //诊断
    .state('app.CreateDiagnosis', {
      title: "诊断",
      url: '/createDiagnosis',
      templateUrl: 'app/site/diagnosis/createDiagnosis/createDiagnosis.html',
      controller: 'createDiagnosisController',
      controllerAs: 'createDiagnosis'
    })
    .state('app.SearchDiagnosis', {
      title: "信息查询",
      url: '/searchDiagnosis',
      templateUrl: 'app/site/diagnosis/searchDiagnosis/searchDiagnosis.html',
      controller: 'searchDiagnosisController',
      controllerAs: 'searchDiagnosis'
    })
    .state('app.collectDiagnosis', {
      title: "收藏",
      url: '/collectDiagnosis',
      templateUrl: 'app/site/diagnosis/collection/collection.html',
      controller: 'collectDiagnosisController',
      controllerAs: 'diagnose'
    })

    // 报告
    .state('app.report', {
      title: "报告",
      url: '/report',
      templateUrl: 'app/site/report/report.html',
      controller: 'ReportController',
      controllerAs: 'report'
    })
    .state('app.reportSignQuery', {
      title: "报告签收",
      url: '/signQuery',
      templateUrl: 'app/site/report/signQuery.html',
      controller: 'SignQueryController',
      controllerAs: 'signQuery'
    })

    .state('app.blockArchive', {
      title: "蜡块存档",
      url: '/blockArchive',
      templateUrl: 'app/site/archive/blockArchive/blockArchive.html',
      controller: 'BlockArchiveController',
      controllerAs: 'blockArchive'
    })
    .state('app.slideArchive', {
      title: "玻片存档",
      url: '/slideArchive',
      templateUrl: 'app/site/archive/slideArchive/slideArchive.html',
      controller: 'SlideArchiveController',
      controllerAs: 'slideArchive'
    })
    .state('app.borrowArchive', {
      title: "借阅登记",
      url: '/borrowArchive',
      templateUrl: 'app/site/archive/borrowArchive/borrowArchive.html',
      controller: 'BorrowArchiveController',
      controllerAs: 'borrowArchive'
    })
    .state('app.returnArchive', {
      title: "归还登记",
      url: '/returnArchive',
      templateUrl: 'app/site/archive/returnArchive/returnArchive.html',
      controller: 'ReturnArchiveController',
      controllerAs: 'returnArchive'
    })
    .state('app.queryArchive', {
      title: "信息查询",
      url: '/queryArchive',
      templateUrl: 'app/site/archive/queryArchive/queryArchive.html',
      controller: 'QueryrchiveController',
      controllerAs: 'queryArchive'
    })

    // 试剂管理
    .state('app.reagentState', {
      title: "库存",
      url: '/reagentState',
      templateUrl: 'app/site/reagent/state/index.html',
      controller: 'ReagentStateController',
      controllerAs: 'reagent'
    })
    .state('app.reagentStore', {
      title: "入库",
      url: '/reagentSore',
      templateUrl: 'app/site/reagent/store/index.html',
      controller: 'ReagentStoreController',
      controllerAs: 'reagent'
    })
    .state('app.reagentUse', {
      title: "使用记录",
      url: '/reagentUse',
      templateUrl: 'app/site/reagent/use/index.html',
      controller: 'ReagentUseController',
      controllerAs: 'reagent'
    })
    .state('app.reagentType', {
      title: "试剂",
      url: '/reagentType',
      templateUrl: 'app/site/reagent/type/reagentType.html',
      controller: 'ReagentTypeController',
      controllerAs: 'reagent'
    })

    // 系统设置
    .state('app.SettingDiagnosis', {
      title: "设置",
      url: '/settingDiagnosis',
      templateUrl: 'app/site/diagnosis/settingDiagnosis/settingDiagnosis.html',
      controller: 'settingDiagnosisController',
      controllerAs: 'settingDiagnosis'
    })
    //统计报表
    .state('app.AllStatistic', { //总览
      title: "总览",
      url: '/allStatistic',
      templateUrl: 'app/site/statisticalReport/allStatistic/allStatistic.html',
      controller: 'allStatisticController',
      controllerAs: 'allStatistic'
    })
    .state('app.WorkStatistic', { //工作量
      title: "工作量",
      url: '/workStatistic',
      templateUrl: 'app/site/statisticalReport/workStatistic/workStatistic.html',
      controller: 'workStatisticController',
      controllerAs: 'workStatistic'
    })
    .state('app.GradeStatistic', { //质控评分
      title: "质控评分",
      url: '/gradeStatistic',
      templateUrl: 'app/site/statisticalReport/gradeStatistic/scoreStatistic.html',
      controller: 'ScoreStatisticController',
      controllerAs: 'scoreStatistic'
    })
    .state('app.FreezeStatistic', { //冰冻预约
      title: "冰冻预约",
      url: '/freezeStatistic',
      templateUrl: 'app/site/statisticalReport/freezeStatistic/freezeStatistic.html',
      controller: 'FreezeStatisticController',
      controllerAs: 'fStatistic'
    })
    // .state('app.GradeStatistic', {//质控评分
    //   url: '/gradeStatistic',
    //   templateUrl: 'app/site/statisticalReport/gradeStatistic/gradeStatistic.html',
    //   controller: 'gradeStatisticController',
    //   controllerAs: 'gradeStatistic'
    // })
    // .state('app.SampleStatistic', {//样本统计
    //   url: '/sampleStatistic',
    //   templateUrl: 'app/site/statisticalReport/sampleStatistic/sampleStatistic.html',
    //   controller: 'sampleStatisticController',
    //   controllerAs: 'sampleStatistic'
    // })
    // .state('app.FeeStatistic', {//收费统计
    //   url: '/feeStatistic',
    //   templateUrl: 'app/site/statisticalReport/feeStatistic/feeStatistic.html',
    //   controller: 'feeStatisticController',
    //   controllerAs: 'feeStatistic'
    // })
    //病例查询
    .state('app.CreateQuery', {
      title: "病例查询",
      url: '/createQuery',
      templateUrl: 'app/site/caseQuery/createQuery/createQuery.html',
      controller: 'createQueryController',
      controllerAs: 'createQuery'
    })
    .state('app.QueryResearch', {
      title: "科研",
      url: '/queryResearch',
      templateUrl: 'app/site/caseQuery/queryResearch/queryResearch.html',
      controller: 'researchQueryController',
      controllerAs: 'researchQuery'
    })
    .state('app.QueryAdvice', {
      title: "医嘱",
      url: '/queryAdvice',
      templateUrl: 'app/site/caseQuery/adviceQuery/adviceQuery.html',
      controller: 'adviceQueryController',
      controllerAs: 'adviceQuery'
    })
    .state('app.QueryProStatus', {
      title: "样本状态",
      url: '/queryProStatus',
      templateUrl: 'app/site/caseQuery/status/status.html',
      controller: 'ProStatusController',
      controllerAs: 'proStatus'
    })

    //冰冻取材路由设置
    .state('app.freezeSlide', {
      title: "冰冻取材",
      url: '/freezeMaterial',
      templateUrl: 'app/site/freeze/freezeMaterial/createMaterial.html',
      controller: 'FreezeMaterialController',
      controllerAs: 'freeze'
    })
    .state('app.freezeSearch', {
      title: "信息查询",
      url: '/freezeSearch',
      templateUrl: 'app/site/freeze/freezeSearch/searchMaterial.html',
      controller: 'FreezeSearchController',
      controllerAs: 'freeze'
    })
    .state('app.freezeSetting', {
      title: "设置",
      url: '/freezeSetting',
      templateUrl: 'app/site/freeze/freezeSetting/configMaterial.html',
      controller: 'FreezeSettingController',
      controllerAs: 'freeze'
    })

    //系统管理
    .state('app.UserModule', {
      title: "用户管理",
      url: '/userModule',
      templateUrl: 'app/site/userManage/userManage/userManage.html',
      controller: 'userManageController',
      controllerAs: 'userManage'
    })
    .state('app.GradeModule', {
      title: "质控评分",
      url: '/gradeModule',
      templateUrl: 'app/site/userManage/gradeModule/gradeModule.html',
      controller: 'gradeModuleController',
      controllerAs: 'gradeModule'
    })
    .state('app.SystemModule', {
      title: "系统设置",
      url: '/systemModule',
      templateUrl: 'app/site/userManage/systemSetting/systemSetting.html',
      controller: 'systemSettingModuleController',
      controllerAs: 'systemModule'
    });

    $urlRouterProvider.otherwise('/login');
  }

})();
