(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('FormworkController', FormworkController);

  /** @ngInject */
  function FormworkController($scope,IhcService,toastr) {
    var FormworkDiagnosis = this;
    IhcService.get('/paramSetting/diagnoseTemplate').then(function(res){
      if(res==null){
        FormworkDiagnosis.choose = 0;
      }else{
        FormworkDiagnosis.choose = res;
      }
      $scope.$watch('FormworkDiagnosis.choose',function(newValue,oldValue){
        IhcService.put('/paramSetting/diagnoseTemplate/'+newValue).then(function(){
          if(newValue!=oldValue){
            toastr.success('修改成功');
          }
        },function(err){
          toastr.error(err.reason);
        });
      });
    });
  }
})();
