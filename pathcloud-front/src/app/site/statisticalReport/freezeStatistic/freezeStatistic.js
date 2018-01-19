/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('FreezeStatisticController', FreezeStatisticController);

  /** @ngInject */
  function FreezeStatisticController($state,toastr,$uibModal,apiUrl){
    var fStatistic=this;
    fStatistic.activeMenu = 0;

    fStatistic.choseMenu = function(index){
      fStatistic.activeMenu = index;
    }

  }
})();

