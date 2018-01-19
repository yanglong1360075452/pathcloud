/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('settingDiagnosisController', settingDiagnosisController);

  /** @ngInject */
  function settingDiagnosisController($scope,$state,toastr){
    var settingDiagnosis=this;

   
    settingDiagnosis.activeMenu=0;
    settingDiagnosis.choseMenu=function (index) {
      settingDiagnosis.activeMenu=index;
    };


  }
})();

