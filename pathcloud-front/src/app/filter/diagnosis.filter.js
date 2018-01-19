/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .filter('diagnosisType',function(){
      return function (status) {
        var str='';
        if(status===19){
          str="一级诊断";
        }else if(status===20){
          str="二级诊断";
        }else if(status===21){
          str="三级诊断";
        }else{
          str="其他";
        }
        return str;
      }
    })

})();
