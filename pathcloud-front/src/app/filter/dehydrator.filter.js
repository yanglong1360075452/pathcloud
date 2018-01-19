/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .filter('dehydratorStatus', function () {
      return function (status,disabled) {
        var str='';

        if(status===1){
          str="报警";
        }
        if(disabled===true){
          str="不可用";
        }
        if(!str){
          str="正常";
        }

        return str;
      }
    })

})();
