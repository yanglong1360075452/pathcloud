/**
 * Created by Administrator on 2016/12/1.
 */
(function() {
    'use strict';

    angular
        .module('pathcloud')
        .directive('noteSelect', noteSelect);

    /** @ngInject */

    function noteSelect($compile,$timeout,MaterialService) {
        return {
            restrict: 'AE', //attribute or element
            scope: {
                // text: '=',//页面加载的时候把科室的值显示出来
                require: '=',//是否必填
                disable: '=',//是否必填
                value: '=',
            },
            template: '<div class="note-select"><input autocomplete="off" class="form-control" id="note"  name="note" ng-required="require" ng-disabled="disable"  ng-model="model"  ' +
                'ng-click = "hidden=!hidden" /></input>' +
                '<div class="hideWhenPrint select-item" ng-if="!hidden">' +
                '   <select class="form-control"  ng-model="x" multiple="1" size="10">' +
                '       <option ng-repeat="data in datas" ng-click="change(data)" ng-value="data.code" >{{data.name}}</option>' +
                '   </select>' +
                '</div></div>',
            //   replace: true,
            link: function($scope, elem, attr, ctrl) {

              // <input ng-model="item.note" type="text" ng-disabled="item.status&&item.status!=11" ng-click="showNote = !showNote" ng-change="showNote = false">
              //   <div class="note-select" ng-init="showNote = false">
              //   <i class="triangle" ng-click="showNote = !showNote"></i>
              //   <div class="note-content" ng-if="showNote">
              //   <p class="note-option" ng-click="item.note = note" ng-repeat="note in createMaterial.noteList track by $index">{{note}}</p>
              // </div>
              // </div>


                $scope.datas = [{name:"备注1"},{name:"备注2"},{name:"备注3"},{name:"备注4"},] ; //下拉框选项副本
                $scope.hidden = true; //选择框是否隐藏
                $scope.model = ''; //文本框数据


                //将下拉选的数据值赋值给文本框
                $scope.change = function(data) {
                    $scope.model = data.name;
                    // $scope.value = data.code;
                    $scope.hidden = true;
                }


            }
        };
    };

})();
