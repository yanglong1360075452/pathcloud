/**
 * Created by zhanming on 16/4/12.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('sliderContainer', sliderContainer);

  /** @ngInject */
  function sliderContainer() {
    var directive = {
      restrict: 'E',
      templateUrl: 'app/components/slider/slider-container.html',
      transclude:true,
      scope:{
        new:"=",
        scrollWidth:"=",
        perItemNum:"=",
        totalNum:"="
      },
      // controller: sliderContainerController,
      // controllerAs: "sliderC",
      // bindToController:true
      link : function (scope, tElement, tAttrs) {
        // var i = 0;
        var container = $(tElement).find('.slider-container');
        scope.$watch('totalNum',function (newValue,oldValue) {
          if(newValue){
            scope.pages = Math.ceil(scope.totalNum/scope.perItemNum);
            // scope.right() //体验不好
          }
        });
        var currentPage=1;
        var scrollLength=0;


        scope.$watch('new', function (newValue,oldValue, scope) {

          if(scope.new==true){
            container.css("transform","translateX(0px)");
            scope.new=false;
            scrollLength=0;
            currentPage=1;
          }
        });


        scope.right=function () {

          if (currentPage < scope.pages) {

            currentPage++;
            scrollLength-=scope.scrollWidth;
            container.css("transform","translateX("+ scrollLength + "px)");

          }
        };

        scope.left=function () {
          if (currentPage >1) {

            currentPage--;
            scrollLength+=scope.scrollWidth;

            container.css("transform","translateX("+ scrollLength + "px)");

          }
        };

      }
    };
    return directive;
    
  }




})();
