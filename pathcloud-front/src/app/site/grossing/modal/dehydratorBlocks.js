/**
 * Created by lenovo on 2016/5/13.
 */

(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('DehydratorBlocksController', DehydratorBlocksController);

  /** @ngInject */
  function DehydratorBlocksController($uibModalInstance,GrossingService,instrument,toastr){
    var dehydratorBlock=this;
  
    dehydratorBlock.pause = instrument.pause;

    function init() {
      dehydratorBlock.tableHeaders=[{name:"病理号"},{name:"蜡块号"},{name:"姓名"},{name:"组织数"},{name:"取材标识"},{name:"状态"},{name:"脱水机"},]

      dehydratorBlock.filter={
        page:1,
        length:10
      };
      dehydratorBlock.getBlocks=function () {
        GrossingService.dehydratorBlocks(instrument.id,dehydratorBlock.filter).then(function (res) {
          dehydratorBlock.data=res;
        });
      };
      dehydratorBlock.getBlocks();
    }
    init();

    dehydratorBlock.cancel=function(){
      $uibModalInstance.dismiss();
    };

  }
})();
