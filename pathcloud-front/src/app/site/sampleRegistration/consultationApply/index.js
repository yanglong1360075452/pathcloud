
(function() {
  'use strict';
  
  angular
  .module('pathcloud')
  .controller('ConsultationApplyController', ConsultationApplyController);
  
  /** @ngInject */
  function ConsultationApplyController($scope,$state,toastr,ProductService){
    
    var consultation=this;
    consultation.activeMenu = 0;//设置选中菜单样式
    
    //切换tab
    consultation.choseMenu=function(index){
      proDistribution.activeMenu = index;
    };
    
  }
})();

