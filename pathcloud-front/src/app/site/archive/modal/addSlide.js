(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('AddSlideModalController',AddSlideModalController);

  /** @ngInject */
  function AddSlideModalController($uibModalInstance,toastr,SystemSettingService,$timeout,IhcService,allSlideList){

    var addSlideModal = this;

    addSlideModal.slideList=allSlideList;
    addSlideModal.checkedBlocks=[];

    function init(){


    }



    init();

    //全选 选择的是当页的数据
    addSlideModal.chkAll = function () {
      if(addSlideModal.allChecked){
        // console.info("点击全选");

        //判断哪些被打了勾 没勾的就存起来
        for(var i=0;i<addSlideModal.slideList.length;i++){
          var slideItem = addSlideModal.slideList[i];
          slideItem.check = true;
        }

      }else {
        console.info("取消全选");
        //判断哪些被打了勾 勾过的就删掉
        for(var i=0;i<addSlideModal.slideList.length;i++){
          var slideItem=addSlideModal.slideList[i];
          slideItem.check = false;
        }
      }
      // console.info("所有打钩的蜡块",addSlideModal.checkedBlocks)
    };


    //确定按钮
    addSlideModal.confirm = function (){
      var selectSlide = [];
       angular.forEach(addSlideModal.slideList,function(item,index,array){
         if(item.check){
           delete item.check;
           selectSlide.push(item)
         }
       });
       // console.info(selectSlide)
       $uibModalInstance.close(selectSlide)
    };

    //取消按钮
    addSlideModal.cancel = function(){
      $uibModalInstance.dismiss();
    }
  }//end CommonModalController
})();


