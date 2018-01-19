/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('workStatisticController', workStatisticController);

  /** @ngInject */
  function workStatisticController(){
    var workStatistic=this;
    workStatistic.activeMenu = 0;

    workStatistic.choseMenu = function(index){
      workStatistic.activeMenu = index;
    }

  }
})();

