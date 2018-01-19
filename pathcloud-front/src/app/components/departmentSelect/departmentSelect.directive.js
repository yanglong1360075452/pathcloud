/**
 * Created by Administrator on 2016/12/1.
 */
(function() {
    'use strict';

    angular
        .module('pathcloud')
        .directive('departmentSelect', departmentSelect);

    /** @ngInject */

    function departmentSelect($compile,$timeout,MaterialService,$filter) {
        return {
            restrict: 'AE', //attribute or element
            scope: {
                text: '=',//页面加载的时候把科室的值显示出来
                require: '=',//是否必填
                value: '=',
            },
          template: '<div class="select-search"><input class="form-control" autocomplete="off" id="departments"  name="departments" ng-required="require" type = "text"  ng-model="model"  ng-readonly=true ' +
          'ng-click = "hidden = !hidden " value="{{value}}" placeholder="请选择"/></input>' +
          '<div class="hideWhenPrint select-item" ng-show="!hidden">' +
          '<input autocomplete="off" class="form-control" ng-blur="blurJudge()" auto-focus="{{!hidden}}" name="departments" ng-required="require" type = "text" ng-change="changeData()" ng-model="models" ' +
          ' value="{{values}}"/></input>' +
          '<select id="selectSearch" class="form-control"   ng-model="x" multiple="1" size="10">' +
          '<option ng-repeat="data in datas"  ng-click="change(data)" ng-value="data.code" >{{data.name}}</option> '+
          '   </select>' +
          '</div></div>',
          replace: true,
          link: function($scope, elem, attr, ctrl) {
            $scope.hidden = true; //选择框是否隐藏
            $scope.model = ''; //文本框数据
            $scope.x = null; //select数据 使用 mutiple属性 获取的值是数组

            //将下拉选的数据值赋值给文本框
            $scope.change = function(data) {
              // console.info("将下拉选的数据值赋值给文本框",$scope)
              $scope.value = parseInt($scope.x[0]);
              $scope.hidden = true;
              $scope.model = $filter('department')($scope.value);
              $scope.models = '';
              $scope.getData($scope.models);
            };
            //
            $scope.getData = function (filter) {
              MaterialService.getDepartments(filter).then(function(data) {
                $scope.datas = data;
              });
            };
            $scope.getData();
            $scope.blurJudge =function () {
              $timeout(function () {
               // console.info("指令里$scope.value",$scope.value)
                // if(!$scope.value){ //change 没有选值的时候情况 input 输入框内容
                //   $scope.model = "";
                  $scope.hidden = true;
              //   }
              //   $scope.model.filter();
              },500)
            };



            $scope.changeData = function () {
              // console.info("清空value")
              // $scope.value='';
              $scope.getData($scope.models);
              // $scope.getData({filter:$scope.model});
              $scope.hidden = false;
            };

            $scope.$watch('value',function (newValue, p2, p3) {
              $scope.model = $filter('department')($scope.value);
            })
          }
        };
    }

})();
