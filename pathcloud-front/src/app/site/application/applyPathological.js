(function() {
    'use strict';

    angular
        .module('pathcloud')
        .controller('ApplicationCreateController', ApplicationCreateController)

    /** @ngInject */
    function ApplicationCreateController($scope,$rootScope, $filter, ApplicationService, toastr, IhcService, printerService, $state,$timeout) {

        var applicationCreate = this;

        IhcService.get("/paramSetting/applicationDefault").then(function (res) {
          if($state.params.type){
            $scope.activeMenu = $state.params.type;
          }else {
            $scope.activeMenu = res;
          }
        });
        $scope.activeMenu = $state.params.type || $scope.activeMenu || 1; //tab 切换  科研为0，常规1
        // $scope.readonly = false; //标志父作用域为哪个页面
        // $scope.showOperation = true; //病理申请返回数据
        // $scope.pathological = {}; //将申请成功的数据赋值到表格中
        function init() { //初始化页面数据
          $scope.readonly = false; //标志父作用域为哪个页面
          $scope.showOperation = true; //病理申请返回数据
          $scope.pathological = {}; //将申请成功的数据赋值到表格中
        }

        init()
        $scope.menuSwitch = function(index) { //tab 切换 开关
            $scope.activeMenu = index;
        };




       /* function initProfile() {
          UserService.getUser($rootScope.user.id).then(function (result) {
            // $scope.pathologicalFreeze.data.departments = result.departments;
            // $scope.pathologicalFreeze.data.departmentsDesc = result.departmentsDesc;
            // $scope.pathological.data.departments = result.departments;
            // $scope.pathological.data.departmentsDesc = result.departmentsDesc;
            $scope.pathological.data.departmentsDesc = $scope.pathologicalFreeze.data.departmentsDesc = result.departmentsDesc;
            $scope.pathological.data.departments = $scope.pathologicalFreeze.data.departments = result.departments;
          })
        }
        initProfile();*/



        // 临床确认按钮
        $scope.submitApplication = function(data) {
            // console.log("调用createApplication()", data);//OK
            ApplicationService.createApplication(data).then(
                function(result) { //成功
                    console.log("表格数据 返回----！", result,applicationCreate);
                    // 自动化测试用 显示病理号 申请单号
                    $scope.test_serialNumber = result.serialNumber; //申请号
                    toastr.success("病理申请表提交成功");

                    $scope.showOperation = false;
                    //result.menopauseEnd=$filter('date')(result.menopauseEnd,'yyyy-MM-dd')
                    $scope.pathological.data = result; //将申请成功的数据赋值到表格中

                    printerService.printLabel(result.samples);
                    console.info("初始化表格内容")
                    $scope.refresh(1)


                    // $timeout(function() {
                    //   $state.go("app.applicationCreate", { type: 1 }, { reload: true });
                    // }, 30)


                    //todo 暂时注释掉不打印申请单
                    // printerService.printPathologicalForm("#pathologicalForm", result.serialNumber, function() {
                    //     printerService.printLabel(result.samples);
                    //     $state.go("app.applicationCreate", { type: 0 }, { reload: true });
                    // })
                },
                function(err) { //失败
                    console.log("pathological submit erroe", err);
                    toastr.error("病例申请表提交失败！");
                    // $scope.pathological.checkErr = err + "!";
                })
        };

        $scope.refresh = function (index) {
          init();
          $scope.activeMenu = null;
          $timeout(function() {
            $scope.activeMenu = index;
          }, 30)
        }

    } //end ApplyPathologicalController





})();
