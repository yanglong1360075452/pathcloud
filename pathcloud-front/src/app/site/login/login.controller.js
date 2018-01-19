(function () {
  'use strict';

  angular
    .module('pathcloud')
    .controller('LoginController', LoginController);

  /** @ngInject */
  function LoginController(UserService, $state, $rootScope, $cookieStore, $cookies, toastr, DepartmentsService) {


    var login = this;
    login.data = {};
    login.data.username = "";
    login.data.password = "";
    login.checkErr = "";

    login.ifQrLogin = true;
    // 记住密码功能

    if ($cookieStore.get("remberPassword")) {

      // var rember=$cookieStore.get("remberPassword");
      var rember = $cookies.getObject("remberPassword");
      login.rember = rember.rember;
      login.data.username = window.atob(rember.username);
      login.data.password = window.atob(rember.password);

    }


    login.tab = function (e) {
      var keycode = window.event ? e.keyCode : e.which;//获取按键编码
      if (keycode == 13) {
        login.loginSystem();//如果等于回车键编码执行方法
      }
    };

    var permission = {
      0: {path: "app.UserModule"}, //系统管理
      1: {path: "app.applicationCreate"},//病理申请
      2: {path: "app.CreateDiagnosis"},//一级诊断
      4: {path: "app.createMaterial"},//取材
      8: {path: "app.grossing"},//脱水
      16: {path: "app.embed"},//包埋
      32: {path: "app.section"},//切片
      64: {path: "app.createDye"},//染色
      128: {path: "app.ProConfirmation"},//制片确认
      256: {path: "app.sampleRegistration"},//登记
      512: {path: "app.slideArchive"},//存档
      1024: {path: "app.CreateQuery"},//信息查询
      2048: {path: "app.report"},//报告
      4096: {path: "app.CreateDiagnosis"},//二级诊断
      8192: {path: "app.CreateDiagnosis"},//三级诊断
      16384: {path: "app.AllStatistic"},//统计报表
      32768: {path: "app.specialDyeCreate"},//特染
      65536: {path: "app.ihcCreate"},//免疫组化
      131072: {path: "app.freezeSlide"},//冰冻
      
    };

    function autoGo(code) {
      $state.go(permission[code].path)
    }//自动跳转到对应权限页面


    login.loginSystem = function () {

      if (login.data.username != "" && login.data.password != "") {
        // console.log("调用loginSystem()", login.data);//OK
        UserService.login(login.data).then(
          //成功
          function (result) {
            // console.log("登录成功！",result);
            toastr.success("登录成功！");

            if ($rootScope.version) {
              $rootScope.version = result.version;
            } else {
              $rootScope.version = result.version;
              $cookieStore.put('version', result.version);
            }

            $rootScope.user = result;

            DepartmentsService.store(); //全局存departments

            /*登陆后根据权限自动跳转*/
            if (result.permissionCodes.length === 1) {
              var code = result.permissionCodes[0];
              autoGo(code);
            } else {
              $state.go('app.home');
            }
            $cookieStore.put('userInfo', result);
            //console.log("登录用户信息====",$cookieStore.get('userInfo'));
            // 记住密码
            function saveUserPassword() {
              /*if (login.rember) {
               $cookieStore.put("remberPassword", {
               rember:login.rember,
               username: login.data.username,
               password: login.data.password
               });
               }
               else {
               $cookieStore.remove("remberPassword");
               };*/
  
              // atob 跟 btoa 都不支持中文，如需中文可借助 encodeURIComponent 和 decodeURIComponent 转义中文字符 window.btoa(encodeURIComponent('中文'));  decodeURIComponent(window.atob('JUU0JUI4JUFEJUU2JTk2JTg3'))
              if (login.rember) {
                var expireDate = new Date();
                expireDate.setDate(expireDate.getDate() + 30);//设置cookie保存30天
                $cookies.putObject("remberPassword", {
                  rember: login.rember,
                  username: window.btoa(login.data.username),
                  password: window.btoa(login.data.password)
                }, {'expires': expireDate});
              } else {
                $cookies.remove("remberPassword");
              }
            }

            saveUserPassword()
          },
          function (error) { //失败
            //console.log("error",error);
            login.checkErr = error;
          }
        )
      } else {
        login.checkErr = "用户名或密码不能为空！";
      }
    };

    login.qrcodeLogin = function () {
      UserService.loginByQrcode(login.data.qrCode).then(function (user) {
        login.data.username = user.username;
        login.data.password = user.password;
        login.loginSystem();
      })
    }
    // $(document).ready(function() {
    //   if ($.cookie("rmbUser") == "true") {
    //     $("#rmbUser").attr("checked", true);
    //     $("#wsc-username").val($.cookie("userName"));
    //     $("#wsc-password").val($.cookie("passWord"));
    //   }
    // });


  }//end LoginController
})();
