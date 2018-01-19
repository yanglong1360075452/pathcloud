
(function() {
  'use strict';
  
  angular
  .module('pathcloud')
  .controller('ReagentStateController', ReagentStateController);
  
  /** @ngInject */
  function ReagentStateController($scope,$state,toastr,ProductService){
    
    var reagent=this;
    reagent.activeMenu = 0;//设置选中菜单样式
    
    //切换tab
    reagent.choseMenu=function(index){
      proDistribution.activeMenu = index;
    };
    
  }
})();

