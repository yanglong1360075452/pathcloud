(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('customTemplate', customTemplate);

  /** @ngInject */
  function customTemplate(DiagnosisService) {
    return {
      restrict: 'E',
      templateUrl: 'app/components/template/template.html',
      scope: {
        content: '=',
        position:'@'
      },
      replace:true,
      link: function ($scope) {
        DiagnosisService.getCustomTemplate(null,$scope.position,0).then(function (data) {
          $scope.oneLevel = data;
          if (data.length) {//给第一项添加蓝色背景
            if ($scope.activeTemplate == data.length) $scope.activeTemplate = 0;
            $scope.chooseOneLevel(data[$scope.activeTemplate || 0], $scope.activeTemplate || 0);
            //获取二级目录
            getTwoLevel($scope.activeTemplateId);
          }
        });
        function getTwoLevel(parent){
          DiagnosisService.getCustomTemplate(parent,$scope.position,0).then(function (data) {
            $scope.twoLevel=data;
            if(data.length){//给第一项添加蓝色背景
              if($scope.twoTemplate==data.length) $scope.twoTemplate=0;
              $scope.twoTemplateId =data[$scope.twoTemplate||0].id;
              $scope.twoTemplate = $scope.twoTemplate||0;
            }
          });
        }
        //点击一级目录
        $scope.chooseOneLevel = function(item,$index){
          $scope.activeTemplate=$index;
          $scope.activeTemplateId=item.id;
          getTwoLevel($scope.activeTemplateId);
        };
        //点击二级目录 模板内容显示到病理诊断里
        $scope.chooseTwolevel = function (item,$index) {
          $scope.twoTemplate=$index;
          $scope.twoTemplateId=item.id;
          $scope.content= item.content;
        };
        //点击其他地方收起模板选择
      $scope.stopPropagation = function (e) {
          if (e.stopPropagation) e.stopPropagation();
          else e.cancelBubble = true;
        };
      }

  };
}
})();
