/**
 * Created by zhanming on 16/4/12.
 * 父级作用域
 */
(function() {
    'use strict';

    angular
        .module('pathcloud')
        .directive('thfPathological', thfPathological);

    /** @ngInject */
    function thfPathological() {
        var directive = {
            restrict: 'E',
            scope: false, //其默认情况下就是false 表示继承关系
            templateUrl: 'app/components/pathologicalForm/pathologicalForm.html',
            //replace:true,
            //link: function (scope, elem, attrs) {
            //  console.log(elem);
            //},
            controller: PathologicalController,
            controllerAs: "pathological"
        };

        return directive;

        /** @ngInject */

        function PathologicalController($scope, $rootScope, SystemSettingService, $timeout, UserService, $filter, MaterialService,T,IhcService) {
            // console.log("$scope.data:=====",$scope.data)
            $scope.datePopup = false;
            var pathological = this;


            $scope.menopauseDateOptions = {
                maxDate:Date()
            };
            pathological.getSelectName = function(array, value) {
                    if (value != undefined) {
                        var data = _.find(array, function(o) {
                            // console.log(array, o.value, value)
                            // console.log((o.value + "" || o.code) == value)
                            if (o.value !== undefined)
                                return o.value == value
                            else
                                return o.code == value
                        });
                        // console.log(array[0].name, data);
                        if (data) {
                            return data.name === '请选择' ? "" : data.name
                        }
                    }
                }
                // "sex": 1,#性别 0-未知 1-男  2-女
                // "maritalStatus": 10,#婚否 10-未婚  20-已婚  30-丧偶  40-离婚  90-未知

            // 申请表单的必填项
            pathological.required = {};
            SystemSettingService.getApplicationRequired().then(function(res) {
                if (res.length) {
                    // var res =[{"name":1},{"name":2}];
                    angular.forEach(res, function(data, index, array) {
                        //data等价于array[index]
                        pathological.required[data.name] = data;
                        // console.log("申请表单的必填项",pathological.required);
                    });
                }

            })

            // "category":1#样本类别，1-大样本 2-小样本
            pathological.sexList = [/*{ name: T.T("请选择"), value: undefined },*/ { name: T.T("男"), value: 1 }, { name: T.T("女"), value: 2 } ];
            pathological.maritalStatusList = [{ name: T.T("请选择"), value: 90 }, { name: T.T("未婚"), value: 10 }, { name: T.T("已婚"), value: 20 }, { name: T.T("丧偶"), value: 30 }, { name: T.T("离婚"), value: 40 }, ];
            // pathological.categoryList = [{ name: T.T("大样本"), code: 1 }, { name: T.T("小样本"), code: 2 }];
            IhcService.get("/systemSetting/sampleCategory").then(function (res) { //样本类别改成获取后台
              pathological.categoryList = res;
            });
            pathological.gynaecologyList = [{ name: T.T("妇科"), value: true }, { name: T.T("非妇科"), value: false }];
            pathological.menopauseList = [{ name: T.T("请选择"), value: undefined }, { name: T.T("是"), value: true }, { name: T.T("否"), value: false }];
            pathological.departmentList = [{ name: T.T("请选择"), value: undefined }];
            
            pathological.getDepartments = function(filter) {
                    MaterialService.getDepartments(filter).then(function(data) {
                        pathological.departmentList = data;
                        //pathological.departmentList.unshift({  name: "请选择", value: undefined });
                    });
                } //1.3 获取送检科室列表

            pathological.getDepartments();
  
            pathological.inspectHospitalList = $rootScope.inspectHospitalList;
            IhcService.get("/paramSetting/inspectHospital").then(function (res) {
              pathological.inspectHospitalList = res; //用来防止刷新后申请的时候不显示防止 rootScope保存医院信息失败
            });




            pathological.data = {
                maritalStatus: 90,
                urgentFlag: false,
                gynaecology: false,
                // doctor: $rootScope.user.firstName,
                // doctorTel: $rootScope.user.phone,
                // departments: $rootScope.user.departments, //在个人信息里 需要调接口 获取
            };
            pathological.data.samples = [{
                name: "",
                category: 1
            }];

            //todo 默认申请表显示bug
            // UserService.getUser($rootScope.user.id).then(function (result) {
            //   pathological.data.departments = $rootScope.user.departments = result.departments;
            //   pathological.data.doctorTel = $rootScope.user.phone = result.phone;
            // });

            /*
             * “底部按钮显示与否”
             * 在指令里面访问父级作用域中的属性、方法，使用$scope.父级作用域名字.属性名/函数名
             * "pathological.position" 表示 判断父级scope是 applyPathological OR sampleRegistration
             */
            // pathological.position = "";
            // $scope.applyPathological=$scope.applyPathological||{};
            // $scope.sampleRegistration=$scope.sampleRegistration||{};
            // if($scope.applyPathological.position){
            //   pathological.position = true;
            // }else if($scope.sampleRegistration.position){
            //   pathological.position = false;
            // }
            // console.log($scope.applyPathological.position);

            /*
             *“添加样本记录”
             */
            var sampleObj = {}; //暂存本行的样本数据
            pathological.isShow = "true";

            if ($scope.applicationData) pathological.data = $scope.applicationData; //查看申请弹窗 数据赋值
            // console.log("---------application directive-------", $scope.applicationData)
            /*
             *方法一：“添加样本记录”（暂时用不到）
             */
            pathological.addSample = function(sample, index) {
                sampleObj.name = sample.name;
                sampleObj.category = sample.category;
                pathological.data.samples.push(sampleObj);
                // console.log("增加一条样本记录后，样本数据内容为 ", pathological.data.samples);
                if (pathological.data.samples.length > 0) {
                    pathological.btnFlag = true;
                }
            };

            /*
             *方法二：“添加样本记录”
             */
            pathological.addSample1 = function() {
                pathological.data.samples.push({ name: "", category: 1 });
                // console.log("增加一条样本记录后，样本数据内容为 ", pathological.data.samples);
            };

            /*
             *“删除样本记录”
             */
            pathological.delSample = function(index) {
                pathological.data.samples.splice(index, 1); //splice(index,num)删除指定位置元素 以及 删除个数
                // console.log("删除一条样本记录后，样本数据内容为 ", pathological.data.samples);
            };

            /*
             *“提交按钮”
             */
            pathological.submit = function() {
                // console.warn("日期格式化问题：menopauseEnd",$filter('date')(pathological.data.menopauseEnd,'yyyy-MM-dd'))
                // console.warn("日期格式化问题：menopauseEnd",pathological.data)
                // pathological.data.menopauseEnd=$filter('date')(pathological.data.menopauseEnd,'yyyy-MM-dd');
                
                var data = angular.copy(pathological.data);
                data.menopauseEnd = pathological.data.menopauseEnd && pathological.data.menopauseEnd.getTime() || null;
                console.error("申请表",data)
                $scope.submitApplication(data);
            }


        }
    }
})();
