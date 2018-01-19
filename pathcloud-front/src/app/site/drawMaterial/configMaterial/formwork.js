(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('FormworkMaterial', FormworkMaterial);

  /** @ngInject */
  function FormworkMaterial($scope,IhcService,toastr) {
    var FormworkMaterial = this;
    IhcService.get('/paramSetting/grossingTemplate').then(function(res){
      if(res==null){
        FormworkMaterial.choose = 0;
      }else{
        FormworkMaterial.choose = res;
      }
      $scope.$watch('FormworkMaterial.choose',function(newValue,oldValue){
        if(newValue){
          IhcService.put('/paramSetting/grossingTemplate/'+newValue).then(function(){
            if(newValue!=oldValue){
              toastr.success('修改成功');
            }
          },function(err){
            toastr.error(err.reason);
          });
        }
      });

    });
  }
})();
