/**
 * Created by zhanming on 16/4/12.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('thfHeader', thfHeader);

  /** @ngInject */
  function thfHeader() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/header/header.html',
      controller: HeaderController,
      controllerAs: "header"
    };

    return directive;

    /** @ngInject */

    function HeaderController($rootScope, $state, $uibModal, UserService, $log, notificationService ,$interval,$translate) {

        var header = this;
        $rootScope.$state=$state;
        // console.log("$state是什么=======:", $state)

      header.switching = function(lang){
          $translate.use(lang);
          window.localStorage.lang = lang;
          window.location.reload();
        };
      header.cur_lang = $translate.use();
      console.info("现在选择的语言",$translate.use())

        header.choose = function (title, href) {
          $rootScope.navbar.forEach(function (item) {
            if (item.title === title) {
              item.active = true;
            } else {
              item.active = false;
            }
          })
          $state.go(href)
        };

        console.log("$rootScope.user修改密码=======:", $rootScope.user)
        header.open = function () {
          var modalInstance = $uibModal.open({
            // animation: $ctrl.animationsEnabled,
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'app/site/userManage/modal/changePwd.html',
            controller: 'ChangePwdModalController',
            controllerAs: 'changePassword',
            resolve: {
              modalTitle: function () {
                return "修改密码";
              }
            }
            //controllerAs 这个的值要和controller中所写的var 名字 = this;一致
          });
          modalInstance.result.then(function () {

          }, function () {
            $log.info('Modal dismissed at: ' + new Date());
          });
        };

        header.checkOut = function () {
          //$cookieStore.remove('userInfo')
          UserService.logout().finally(function () {
            $state.go('login');
          })

        };

        // console.log("$rootScope.user用户信息=======:", $rootScope.user)
        header.getProfile = function () {
          // var userInfo = $cookieStore.get("userInfo");
          // console.log("userInfo=======", $rootScope.user)
          UserService.getUser($rootScope.user.id).then(function (result) {
            var userData = result;
            // console.log("userInfoDATA=======", result)
            var modalInstance = $uibModal.open({
              // animation: $ctrl.animationsEnabled,
              ariaLabelledBy: 'modal-title',
              ariaDescribedBy: 'modal-body',
              templateUrl: 'app/site/userManage/modal/user.html',
              controller: 'UserModalController',
              controllerAs: 'userMod',
              size: "lg",
              resolve: {
                modalTitle: function () {
                  return "用户信息";
                },
                method: function () {
                  return "get";
                },
                userData: function () {
                  return userData;
                }

              }
            });
            modalInstance.result.then(function () {

            }, function () {
              $log.info('Modal dismissed at: ' + new Date());
            });
          })

        };


     //系统通知
      var timer = $interval(function(){
        getNotification()
      },35*60*1000);
      getNotification();
      // timer.then(getNotification);
      function getNotification(){
        notificationService.getNotificationTotal().then(
          function (res) {
            $rootScope.totalNotification=res;
            // console.info(res);
          },function (err) {

          }
        )
      }




    }

  }
})();
