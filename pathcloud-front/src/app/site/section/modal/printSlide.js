(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SectionPrintModalController',SectionPrintModalController);

  /** @ngInject */
  function SectionPrintModalController($uibModalInstance,toastr,sectionService,printerService,data, IhcService){

    var printModal = this;
    var blockId = data.blockId;
    var slideInfo = data.slideInfo;
    var bodyPart = data.bodyPart;
    var pathologySerialNumber = data.pathologySerialNumber;
    var subId = data.subId;
    var printType = data.printType;//1 通过玻片打印 2 通过标签打印

    function init(){

      printModal.checkedSlides=[]; //存放选择的玻片信息
      printModal.params={
        page:1,
        length:5
      };

      printModal.getSlidesInfo = function () {
        sectionService.getSlidesInfo(blockId,printModal.params).then(function(res){
          printModal.slideList = res.data;
          printModal.total = res.total;
          printModal.params.page = res.page;

          //判断哪些被打了勾
          for(var i=0;i<printModal.slideList.length;i++){
            var block=printModal.slideList[i];
            if(!printModal.checkedSlides.length) return false;
            angular.forEach(printModal.checkedSlides,function(item,index,array){
              if(block.id===item.id){
                block.check=true;//翻页时给选过的打勾
              }
            });
          }
        })
      };

      printModal.getSlidesInfo();

    }

    init();

    // 单选
    printModal.check=function (block) {
      console.log(block);
      if(block.check){
        //delete
        block.check=false;
        angular.forEach(printModal.checkedSlides,function(item,index,array){
          if(block.id===item.id){
            printModal.checkedSlides.splice(index,1);
          }
        });
      }else {
        //add
        block.check=true;
        printModal.checkedSlides.push(block);
      }
    };

    //全选 选择的是当页的数据
    printModal.checkAll = function () {

        //判断哪些被打了勾 没勾的就存起来  slideList 当页数据
        for(var i=0;i<printModal.slideList.length;i++){
          var block=printModal.slideList[i];

          // 循环当前页的数据跟所有的数据比较  判断printModal.checkedSlides 有数据跟没有数据的情况

          if(printModal.checkedSlides.length){  //当已经有选中了的
            var hasChecked=false; //flag
            angular.forEach(printModal.checkedSlides,function(item,index,array){
              if(block.id===item.id){ //已经勾了
                hasChecked = true;
              }
            });
            if(!hasChecked){
              //把所有的循环一遍后判断当前页还没选择的存起来
              block.check=true;
              printModal.checkedSlides.push(block);
            }
          }else{ //没一个选中的所有的都保存
            block.check=true;
            printModal.checkedSlides.push(block);
          }
        }
        console.info("全选的checkedSlides",printModal.checkedSlides);

      // console.info("所有打钩的蜡块",printModal.checkedSlides)
    };

    //反选
    printModal.checkOther = function () {

      //判断哪些被打了勾 没勾的就存起来  slideList 当页数据
      for(var i=0;i<printModal.slideList.length;i++){
        var block=printModal.slideList[i];

        // 循环当前页的数据跟所有的数据比较  判断printModal.checkedSlides 有数据跟没有数据的情况

        if(printModal.checkedSlides.length){  //当已经有选中了的
          var hasChecked=false; //flag
          angular.forEach(printModal.checkedSlides,function(item,index,array){
            if(block.id===item.id){ //已经勾了
              hasChecked = true;
            }
          });
          if(!hasChecked){
            //把所有的循环一遍后判断当前页还没选择的存起来
            block.check=true;
            printModal.checkedSlides.push(block);
          }
        }else{ //没一个选中的所有的都保存
          block.check=true;
          printModal.checkedSlides.push(block);
        }
      }
      console.info("全选的checkedSlides",printModal.checkedSlides);

      // console.info("所有打钩的蜡块",printModal.checkedSlides)
    };

    //取消全选
    printModal.cancelAll = function () {

        console.info("取消全选");
        //判断哪些被打了勾 勾过的就删掉
        for(var i=0;i<printModal.slideList.length;i++){

          var block = printModal.slideList[i];
          if(block.check){
            block.check=false;
            angular.forEach(printModal.checkedSlides,function(item,index,array){
              if(item.id===block.id){
                printModal.checkedSlides.splice(index,1);
              }
            });
          }

        }

      // console.info("所有打钩的蜡块",printModal.checkedSlides)
    };

    // 打印按钮
    printModal.printAll=function(){
      
      var slideIds = [];
      
      angular.forEach(printModal.checkedSlides,function(item,index,array){
  
        slideIds.push(item.id);
        
        var slideSubId=item.subId;//玻片小号在变
        var type=bodyPart||"";
        if(item.marker){
          type= type+"$"+item.marker;
        }
        var id=slideInfo+slideSubId;
        
        
        // 判断是用什么方式打印
        if(printType == 1){
          printerService.labWrite(id,type).then(function () { //加一个type

          })
        }else{
          var data = {
            pathologySerialNumber:pathologySerialNumber,
            subId:subId,
            bodyPart:bodyPart,
            code:pathologySerialNumber+"-"+subId+"-"+slideSubId
          };
          printerService.printSlide(data)
        }
        
      });
  
      // 记录打印次数 有玻片号的时候 传ID 数组
      IhcService.post("/grossing/print/37",slideIds)
    };


    //确定按钮  最后一步
    printModal.ok = function(){
      $uibModalInstance.close();
      printModal.printAll();
    };

    //取消按钮
    printModal.cancel = function(){
      $uibModalInstance.dismiss();
    }
  }//end CommonModalController
})();


