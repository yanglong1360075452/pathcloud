(function () {
  'use strict';

  angular
  .module('pathcloud')
  .run(runBlock);

  /** @ngInject */
  function runBlock($log, $rootScope, toolService, $timeout, $cookieStore, T, IhcService, DepartmentsService) {
    console.log("run start");

    //全局保存user信息
    $rootScope.user = $cookieStore.get('userInfo') || {};
    $rootScope.basketNumber = $cookieStore.get('basketNumber') || "";
    $rootScope.lang = window.localStorage.lang || 'cn';

    $rootScope.$on('$stateChangeStart',
      function (event, toState, toParams, fromState, fromParams, options) {


        // PersonalInformation
        if (toState.name.indexOf('app.profile') >= 0) {
          if (!fromState.name || fromState.name == "app.home") {
            $rootScope.navbar = [{title: '个人信息', href: 'app.profile'}]
          }
        } else if (toState.name.indexOf('app.notification') >= 0) {
          if (!fromState.name || fromState.name == "app.home") {
            $rootScope.navbar = [{title: '系统通知', href: 'app.notification'}]
          }
        } else if (toState.name.indexOf('app.application') >= 0) {
          $rootScope.navbar = [{title: '创建申请', href: 'app.applicationCreate'}, {
            title: '查看申请',
            href: 'app.applicationView'
          }]
        } else if (toState.name.indexOf('app.specialDye') >= 0) {
          $rootScope.navbar = [{title: '创建申请', href: 'app.specialDyeCreate'}, {
            title: '查看申请',
            href: 'app.specialDyeSearch'
          }]
        } else if (toState.name.indexOf('app.sample') >= 0) {
          $rootScope.navbar = [{title: '样本登记', href: 'app.sampleRegistration'}, {title: '会诊登记', href: 'app.sampleConsultationApply'}, {title: '信息查询', href: 'app.sampleSearch'}, {title: '登记设置', href: 'app.sampleSetting'}]
        } else if (toState.name.indexOf('Material') >= 0) {
          $rootScope.navbar = [{title: '取材', href: 'app.createMaterial'}, {title: '取材确认', href: 'app.confirmMaterial'},
            {title: '信息查询', href: 'app.searchMaterial'}, {title: '取材设置', href: 'app.configMaterial'}]
        } else if (toState.name.indexOf('app.grossing') >= 0) {
          $rootScope.navbar = [{title: '脱水', href: 'app.grossing'}, {
            title: '查看状态',
            href: 'app.grossingStatus'
          }, {title: '脱水设置', href: 'app.grossingSetting'}]
        } else if (toState.name.indexOf('app.embed') >= 0) {
          $rootScope.navbar = [{title: '包埋', href: 'app.embed'}, {
            title: '信息查询',
            href: 'app.embedSearch'
          }, {title: '包埋设置', href: 'app.embedSetting'}]
        } else if (toState.name.indexOf('app.section') >= 0) {
          $rootScope.navbar = [{title: '切片', href: 'app.section'}, {
            title: '信息查询',
            href: 'app.sectionSearch'
          }, {title: '切片设置', href: 'app.sectionSetting'}]
        } else if (toState.name.indexOf('Dye') >= 0) {
          $rootScope.navbar = [{title: '染色', href: 'app.createDye'}, {
            title: '封片',
            href: 'app.DyeSealing'
          }, {title: '信息查询', href: 'app.searchDye'}, {title: '设置', href: 'app.DyeSetting'}]
        } else if (toState.name.indexOf('app.Pro') >= 0) {
          $rootScope.navbar = [{title: '制片确认', href: 'app.ProConfirmation'}, {title: '派片', href: 'app.ProDistribution'},{title: '质控检查', href: 'app.ProQualityGrade'}]
        } else if (toState.name.indexOf('Diagnosis') >= 0) {
          $rootScope.navbar = [{title: '诊断', href: 'app.CreateDiagnosis'}, {title: '信息查询', href: 'app.SearchDiagnosis'}, {title: '收藏', href: 'app.collectDiagnosis'}, {title: '设置', href: 'app.SettingDiagnosis'}]
        } else if (toState.name.indexOf('freeze') >= 0) {
          $rootScope.navbar = [{title: '冰冻取材', href: 'app.freezeSlide'}, {title: '信息查询', href: 'app.freezeSearch'}, {title: '设置', href: 'app.freezeSetting'}]
        } else if (toState.name.indexOf('report') >= 0) {
          $rootScope.navbar = [{title: '报告打印', href: 'app.report'}, {title: '签收确认', href: 'app.reportSignQuery'}]
        } else if (toState.name.indexOf('ihc') >= 0) {
          $rootScope.navbar = [{title: '申请列表', href: 'app.ihcSearch'},{title: '打印', href: 'app.ihcPrint'}]
        } else if (toState.name.indexOf('Archive') >= 0) {
          $rootScope.navbar = [/*{title: '蜡块存档', href: 'app.blockArchive'},*/{
            title: '玻片存档',
            href: 'app.slideArchive'
          }, {title: '借阅登记', href: 'app.borrowArchive'}, {title: '归还登记', href: 'app.returnArchive'}, {
            title: '信息查询',
            href: 'app.queryArchive'
          },]
        } else if (toState.name.indexOf('reagent') >= 0) {
          /*试剂管理*/
          $rootScope.navbar = [{title: '库存记录', href: 'app.reagentState'}, {title: '入库', href: 'app.reagentStore'}, {title: '使用记录', href: 'app.reagentUse'},{title: '试剂', href: 'app.reagentType'}]
        } else if (toState.name.indexOf('Statistic') >= 0) {
          $rootScope.navbar = [{title: '总览', href: 'app.AllStatistic'}, {
            title: '工作量',
            href: 'app.WorkStatistic'
          }, {title: '质控评分', href: 'app.GradeStatistic'}, {title: '仪器设备', href: 'app.FreezeStatistic'},
            /* {title: '样本统计', href: 'app.SampleStatistic'},{title: '收费统计', href: 'app.FeeStatistic'}*/
          ]
        } else if (toState.name.indexOf('Query') >= 0) { //
          $rootScope.navbar = [{title: '病例查询', href: 'app.CreateQuery'}, {
            title: '科研',
            href: 'app.QueryResearch'
          }, {title: '医嘱', href: 'app.QueryAdvice'}, {title: '样本状态', href: 'app.QueryProStatus'}]
        } else if (toState.name.indexOf('Module') >= 0) { //
          $rootScope.navbar = [{title: '用户管理', href: 'app.UserModule'}, {
            title: '质控评分',
            href: 'app.GradeModule'
          }, {title: '文件管理', href: 'app.home'}, {title: '系统设置', href: 'app.SystemModule'}]
        } else {
          $rootScope.navbar = []
        }

        // console.warn("什么是toState",toState.name)
        // console.warn("什么是toParams",toParams)
        // console.warn("什么是fromState",fromState)
        // console.warn("什么是fromParams",fromParams)
        // console.warn("什么是options",options)

        // event.preventDefault();
        // transitionTo() promise will be rejected with
        // a 'transition prevented' error
      });

    $rootScope.$on('$stateChangeSuccess', function (event, toState, toParams, fromState, fromParams) {
      $timeout(function () {
        if ($rootScope.version && $cookieStore.get('version') && ($rootScope.version != $cookieStore.get('version'))) {
          $cookieStore.put('version', "");
          location.reload();
          return;
        }
      }, 300)

    });

    // $rootScope.back = function() {//实现返回的函数
    //   var backInfo=toolService.getLocalStorageInfo('backInfo');
    //   $state.go(backInfo.previousState_name,backInfo.previousState_params);
    // };
    //   $log.debug('runBlock end');

    // 这个接口需要首先授权

    IhcService.get("/paramSetting/inspectHospital").then(function (res) {
      $rootScope.inspectHospitalList = res;
    });


    //全局保存所有科室名信息
    DepartmentsService.get()


  }

})();
