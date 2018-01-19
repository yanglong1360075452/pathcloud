
(function() {
  'use strict';

  angular
  .module('pathcloud')
  .controller('ReagentStoreController', ReagentStoreController);

  /** @ngInject */
  function ReagentStoreController($scope,$state,toastr,ProductService){

    var reagent=this;
    reagent.activeMenu=1;//设置选中菜单样式

    //切换tab
    reagent.choseMenu=function(index){
      reagent.activeMenu=index;
    };

  }
})();

