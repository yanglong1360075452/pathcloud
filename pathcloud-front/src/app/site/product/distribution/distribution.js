
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ProDistributionController', ProDistributionController);

  /** @ngInject */
  function ProDistributionController($scope,$state,toastr,ProductService){

    var proDistribution=this;
    proDistribution.activeMenu=1;//设置选中菜单样式

    //切换tab
    proDistribution.choseMenu=function(index){
      proDistribution.activeMenu=index;
    };

  }
})();

