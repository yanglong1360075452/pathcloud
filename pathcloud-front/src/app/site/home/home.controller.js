(function () {
  'use strict';

  angular
  .module('pathcloud')
  .controller('HomeController', HomeController);

  /** @ngInject */
  function HomeController($rootScope, $scope, T, $http, $interval, IhcService,usingFrozen ) {
    var home = this;

    home.userAuth = $rootScope.user.permissionCodes;


    //console.log('permissionCodes',home.userAuth);
    home.menuList = [{
      href: "app.applicationCreate",
      imgSrc: "assets/images/logo-path-application.png",
      needAuth: [1],
      title: "病理申请"
    },

      {
        href: "app.sampleRegistration",
        imgSrc: "assets/images/logo-sample-register.png",
        needAuth: [256],
        title: "样本登记"
      },
      {
        href: "app.createMaterial",
        imgSrc: "assets/images/logo-material.png",
        needAuth: [4],
        title: "取材"
      },
      {
        href: "app.specialDyeCreate",
        imgSrc: "assets/images/logo-dye-application.png",
        needAuth: [32768],
        title: "染色申请"
      },
      {
        href: "app.grossing",
        imgSrc: "assets/images/logo-grossing.png",
        needAuth: [8],
        title: "脱水"
      },
      {
        href: "app.embed",
        imgSrc: "assets/images/logo-embed.png",
        needAuth: [16],
        title: "包埋"
      },
      {
        href: "app.section",
        imgSrc: "assets/images/logo-section.png",
        needAuth: [32],
        title: "切片"
      },
      {
        href: "app.createDye",
        imgSrc: "assets/images/logo-dye.png",
        needAuth: [64],
        title: "染色封片"
      },
      {
        href: "app.ProConfirmation",
        imgSrc: "assets/images/logo-slide-confirm.png",
        needAuth: [128],
        title: "制片确认"
      },
      {
        href: "app.CreateDiagnosis",
        imgSrc: "assets/images/logo-diagnosis.png",
        needAuth: [2, 4096, 8192],
        title: "病理诊断"
      },
      {
        href: "app.report",
        imgSrc: "assets/images/logo-report.png",
        needAuth: [2048],
        title: "报告"
      },
      {
        href: "app.slideArchive",
        imgSrc: "assets/images/logo-archive.png",
        needAuth: [512],
        title: "存档"
      },
      {
        href: "app.ihcSearch",
        imgSrc: "assets/images/logo-ihc.png",
        needAuth: [65536],
        title: "特检"
      },
      {
        href: "app.reagentState",
        imgSrc: "assets/images/logo-reagent.png",
        needAuth: [262144],
        title: "试剂耗材"
      },
      {
        href: "app.AllStatistic",
        imgSrc: "assets/images/logo-statistics.png",
        needAuth: [16384],
        title: "统计报表"
      },
      {
        href: "app.CreateQuery",
        imgSrc: "assets/images/logo-search.png",
        needAuth: [1024],
        title: "查询"
      },
      {
        href: "app.freezeSlide",
        imgSrc: "assets/images/logo-frozen.png",
        needAuth: [131072],
        title: "冰冻"
      },
      {
        href: "app.UserModule",
        imgSrc: "assets/images/logo-sys-manage.png",
        needAuth: [0],
        title: "系统管理"
      }
    ];


    home.checkAuth = function (item) {

      if(item.needAuth.length === 0){return false}
      // console.info("冰冻home", usingFrozen) //inject from route
      if(usingFrozen && item.title === "冰冻"){
        return true; //当不使用冰冻工作站的时候隐藏
      }

      return _.intersection(home.userAuth, item.needAuth).length === 0
    }

    //权限控制
    //   Admin(0,"系统管理"),
    //   Apply(1,"病理申请"),
    //   Diagnose(2,"病理诊断"),
    //   Grossing(4, "取材"),
    //   Dehydrate(8,"脱水"),
    //   Embedding(16,"包埋"),
    //   Slice(32,"切片"),
    //   Dye(64,"染色"),
    //   SlideMake(128,"制片确认"),
    //   Registration (256, "登记"),
    //   Archive (512, "存档"),
    //   InfoQuery (1024, "信息查询"),

  }
})();
