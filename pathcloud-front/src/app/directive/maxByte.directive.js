/*

(function() {
  'use strict';

  angular
  .module('pathcloud')
  .directive('ngInput', ngInput);

  /!** @ngInject *!/
  function ngInput($sce,$parse) {
    return {
      restrict: 'A',
      require: 'ngModel',
      link: function(scope, element, attrs, ctrl) {

        if (!ngModel) return; // do nothing if no ng-model
        element.bind('blur keyup change input', function() {
          scope.$apply(function() {
            ctrl.$setViewValue(element.html());
          });
        });

        /!*!// Listen for change events to enable binding
        element.on('blur keyup change', function() {
          scope.$evalAsync(read);
        });
        read(); // initialize

        // Write data to the model
        function read() {
          var html = element.html();
          // When we clear the content editable the browser leaves a <br> behind
          // If strip-br attribute is provided then we strip this out
          if ( attrs.stripBr && html == '<br>' ) {
            html = '';
          }
          ctrl.$setViewValue(html);
        }*!/

        // model -> view
        ctrl.$render = function() {
          element.html(ctrl.$viewValue);
        };

        /!*!// Specify how UI should be updated
        ctrl.$render = function() {
          element.html($sce.getTrustedHtml(ctrl.$viewValue || ''));
        };*!/




        /!*element.on('input',oninput);
         scope.$on('$destroy',function(){//销毁的时候取消事件监听
         element.off('input',oninput);
         });
        function oninput(event){
          console.info("执行了",event,this);
          console.info("执行了",attrs.ngModel);

          scope.$apply(attrs.ngInput)
          // console.info("执行了",$parse(attrs.ngModel)(scope));
          // if(value.length>2)value=value.slice(0,2)
          // scope.$apply(attrs.ngInput,{$event:event,$value:this.value});

        }*!/

        /!*!//做一个判断输入字节位数的指令
        console.info("做一个判断输入字节位数的指令",scope,element,attrs,ctrl)
        var w = 0;
        var tempCount = 0;
        var str= element.html()
        var maxLen= attrs.maxByte

        console.info("输入的值",element.value)

        maxByte(element.value,maxLen); //

        function maxByte(str,max) {
          //length 获取字数数，不区分汉子和英文
          for (var i=0; i<str.length; i++) {
            //charCodeAt()获取字符串中某一个字符的编码
            var c = str.charCodeAt(i);

            //判断当有 $ 的时候删掉
            var a = str.charAt(i);
            if(a=="$"){
              str = str.substr(0,i);
            };

            //单字节加1
            if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
              w++;
            } else {
              w+=2;
            }
            if (w > maxLen) {
              str = str.substr(0,i);
              break;
            }
          }

          return str
        }




        element.bind('keyup', function() {

          console.info("返回的值",maxByte(element.value,element[0].value,maxLen))
          scope.$apply(function() {
            console.info("返回的值22",maxByte(element.value,maxLen))
            var html=element.html();

            ctrl.$setViewValue(html);
          });
        });
        // view -> model
        element.bind('blur', function() {
          scope.$apply(function() {
            ctrl.$setViewValue(element.html());
          });
        });

        // model -> view
        ctrl.$render = function() {
          element.html(ctrl.$viewValue);
        };*!/

      }
    }

  }

})();
*/
