/**
 * Created by Administrator on 2016/12/1.
 */
(function() {
    'use strict';

    angular
        .module('pathcloud')
        .directive('selectSearch', selectSearch);

    /** @ngInject */

    function selectSearch($compile,$timeout) {
        return {
            restrict: 'AE', //attribute or element
            scope: {
                text: '=',//页面加载的时候把科室的值显示出来
                require: '=',//必填
                value: '=',
                getData: '&',
                datas: '=',
            },
            template: '<div class="select-search"><input autocomplete="off" class="form-control" id="departments"  name="departments" ng-required="require" type = "text" ng-change="changeData()" ng-model="model"  ' +
                'ng-click = "hidden=!hidden" value="{{value}}" ng-blur="judgeValue()"/></input>' +
                '<div class="hideWhenPrint select-item" ng-if="!hidden">' +
                '   <select id="selectSearch" class="form-control"  ng-model="x" multiple="1" size="10">' +
                '       <option ng-repeat="data in datas" ng-click="change(data)" ng-value="data.code" >{{data.name}}</option>' +
                '   </select>' +
                '</div></div>',
            //   replace: true,
            link: function($scope, elem, attr, ctrl) {

                $scope.tempdatas = $scope.datas; //下拉框选项副本
                $scope.hidden = true; //选择框是否隐藏
                $scope.model = ''; //文本框数据
              // console.info("选择框是否隐藏",$scope.hidden)

                // 页面加载的时候把科室的值显示出来 个人信息页显示
                if($scope.text){
                  $scope.model = $scope.text
                }
                console.info("指令里$scope.text",$scope.text)
                // $timeout(function () {
                //   if($scope.text){
                //     $scope.model = $scope.text
                //   }
                // },100)



              $scope.judgeValue =function () {
                $timeout(function () {
                  console.info("指令里$scope.value",$scope.value)
                  if(!$scope.value){ //change 没有选值的时候情况 input 输入框内容
                    $scope.model = "";
                    $scope.hidden = true;
                  }
                },500)
              }

                //将下拉选的数据值赋值给文本框
                $scope.change = function(data) {

                    $scope.model = data.name;//页面显示的值
                    $scope.value = data.code;
                    $scope.hidden = true;
                }

              $scope.changeData = function () {
                // console.info("清空value")
                $scope.value='';
                $scope.getData({filter:$scope.model});
                $scope.hidden = false;

              }

            }
        };
    };

})();
