/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ScoreStatisticController', ScoreStatisticController);

  /** @ngInject */
  function ScoreStatisticController($state,toastr,$uibModal,apiUrl){
    var scoreStatistic=this;
    scoreStatistic.activeMenu = 0;

    scoreStatistic.choseMenu = function(index){
      scoreStatistic.activeMenu = index;
    }

  }
})();

