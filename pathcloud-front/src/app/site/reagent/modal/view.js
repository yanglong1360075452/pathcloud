
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('ViewController', ViewHistoryController);

  /** @ngInject */
  function ViewHistoryController($uibModalInstance,data,IhcService){
    var view= this;
    view.tableHeaders =[{name:'使用人'},{name:'使用时间'},{name:'使用量'},{name:'使用备注'}];
    view.filter = {
      page:1,
      length:10
    };
    //获取使用详情
    view.getData = function(){
      IhcService.get('/store/record/'+data.id,view.filter).then(
        function(res){
          view.list = res.data;
          view.total = res.total;
        }
      );
    };
    view.getData();
    view.cancel = function(){
      $uibModalInstance.dismiss();
    };
  }
})();
