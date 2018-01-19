(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SpecialDyeBlocksModalController',SpecialDyeBlocksModalController);

  /** @ngInject */
  function SpecialDyeBlocksModalController($uibModalInstance,toastr,SystemSettingService,$timeout,IhcService,checkedBlocks){

    var specialDyeBlocks = this;

    specialDyeBlocks.firstStep=true;
    // specialDyeBlocks.oldBlocks=oldBlocks; //存放 从新建页面传过来的已经选择过的蜡块
    // specialDyeBlocks.checkedBlocks=checkedBlocks||[]; //存放 所有选择的蜡块
    specialDyeBlocks.newBlockArry = []; //存放增加了染色类别后的蜡块数据
    //最新修改 分两步操作 每次打开弹窗都可以重新再选择蜡块 不需要从页面传已经选择的蜡块了.
    specialDyeBlocks.checkedBlocks=[];

    specialDyeBlocks.specialDyeTypeArry=[];//存放特染类别增加的所有项

    function init(){

      specialDyeBlocks.defaultTime=1;
      // specialDyeBlocks.params={}; 可防分页参数
      specialDyeBlocks.page=1;
      specialDyeBlocks.length=10;
      specialDyeBlocks.getBlocks();

      //二部分
      SystemSettingService.getSpecialDyeList().then(function(res){
        specialDyeBlocks.specialDyeList=res;
        // specialDyeBlocks.specialDyeList.unshift({ name: "白片", code: null });
      });//特染类别获取

    }

    specialDyeBlocks.select=function(){
      specialDyeBlocks.page=1;
      specialDyeBlocks.getBlocks();
    };
    //获取我的蜡块
    specialDyeBlocks.getBlocks=function(){  //用了日期选择指令 时间不能及时获取 用 timeout 延迟

      $timeout(function(){
        var url="/specialDye/blocks"; //6.5 查询我的蜡块
        var params={
          page:specialDyeBlocks.page||1,
          length:specialDyeBlocks.length||10,
          createTimeStart:specialDyeBlocks.startTime||Date.now(),
          createTimeEnd:specialDyeBlocks.endTime||Date.now(),
        };

        IhcService.get(url,params).then(function (res){
          // console.info(res);
          specialDyeBlocks.BlockList=res.data;
          specialDyeBlocks.total=res.total;
          specialDyeBlocks.page=res.page;

          //判断哪些被打了勾
          for(var i=0;i<specialDyeBlocks.BlockList.length;i++){
            var block=specialDyeBlocks.BlockList[i];
            if(!specialDyeBlocks.checkedBlocks.length) return false;
            angular.forEach(specialDyeBlocks.checkedBlocks,function(item,index,array){
              if(block.blockId===item.blockId){
                block.check=true;//翻页时给选过的打勾
              }
            });
          }

        })
      },0)

    };


    init();
    // 单选
    specialDyeBlocks.check=function (block) {
      console.log(block);
      if(block.check){
        //add
        //  block.check=true;
        specialDyeBlocks.checkedBlocks.push(block);
      }else {
        //delete
        // block.check=false;
        angular.forEach(specialDyeBlocks.checkedBlocks,function(item,index,array){
          if(block.blockId===item.blockId){
            specialDyeBlocks.checkedBlocks.splice(index,1);
          }
        });

      }
    };

    //全选 选择的是当页的数据
    specialDyeBlocks.chkAll = function () {
      if(specialDyeBlocks.allChecked){
        // console.info("点击全选");

        //判断哪些被打了勾 没勾的就存起来
        for(var i=0;i<specialDyeBlocks.BlockList.length;i++){
          var block=specialDyeBlocks.BlockList[i];

          // 循环当前页的数据跟所有的数据比较  判断specialDyeBlocks.checkedBlocks 有数据跟没有数据的情况

          if(specialDyeBlocks.checkedBlocks.length){  //当已经有选中了的
            var hasChecked=false; //flag
            angular.forEach(specialDyeBlocks.checkedBlocks,function(item,index,array){
              if(block.blockId===item.blockId){ //已经勾了
                hasChecked = true;
              }
            });
            if(!hasChecked){
              //把所有的循环一遍后判断当前页还没选择的存起来
              block.check=true;
              specialDyeBlocks.checkedBlocks.push(block);
            }
          }else{ //没一个选中的所有的都保存
            block.check=true;
            specialDyeBlocks.checkedBlocks.push(block);
          }
        }
        console.info("全选的",specialDyeBlocks.checkedBlocks);
      }else {
        console.info("取消全选");
        //判断哪些被打了勾 勾过的就删掉
        for(var i=0;i<specialDyeBlocks.BlockList.length;i++){
          var block=specialDyeBlocks.BlockList[i];
          if(!specialDyeBlocks.checkedBlocks.length) return false;
          angular.forEach(specialDyeBlocks.checkedBlocks,function(item,index,array){
            if(block.blockId===item.blockId){ //勾过了
              //删掉勾过的
              block.check=false;
              specialDyeBlocks.checkedBlocks.splice(index,1);
            }
          });
        }
      }
      // console.info("所有打钩的蜡块",specialDyeBlocks.checkedBlocks)
    };


    // 添加染色类别
    specialDyeBlocks.add=function(){
      specialDyeBlocks.specialDyeTypeArry.push({
        specialDye:1,
        ihcMarker:"",//特染要求
        note:"",
      });
    };

    // 选择染色类别  select
    specialDyeBlocks.specialDyeSelect = function (data) {
      angular.forEach(specialDyeBlocks.specialDyeList,function(item,index,array){
        if(data.specialDye===item.code){

          if(!(data.specialDye===1||data.specialDye===-1)){ //判断不是1免疫组化 跟空的
            data.ihcMarker = item.name;
          }else{
            data.ihcMarker = ""; //选择百片跟免疫组化时能编辑
          }
          // return;
        }
      });
    };

    //删除染色类别
    specialDyeBlocks.delete=function(index){
      specialDyeBlocks.specialDyeTypeArry.splice(index,1);
    };


    //上一步 下一步
    specialDyeBlocks.switch = function () {
      specialDyeBlocks.firstStep=!specialDyeBlocks.firstStep;
    };

    //确定按钮  最后一步
    specialDyeBlocks.addBlock = function (){

      /*// 不允许选同样的染色类别 todo
       angular.forEach(specialDyeBlocks.specialDyeTypeArry,function(subItem,index,array){
       if(data.specialDye===subItem.specialDye) { //判断染色类别是否已经选过了
       toastr.error("不要选择重复的染色类别")
       return false;
       }
       });*/

      //循环一遍选择的弹窗里的蜡块 给所有的蜡块都加上选择的染色类别 1_按染色类别分类
      /*for (var i=0;i<specialDyeBlocks.specialDyeTypeArry.length;i++){

        var special = specialDyeBlocks.specialDyeTypeArry[i];
        angular.forEach(specialDyeBlocks.checkedBlocks,function(item,index,array){

          item.specialDye=special.specialDye;
          item.ihcMarker=special.ihcMarker;
          item.note=special.note;

        });

        // 每一次循环数据翻一倍  按染色类别分类

        specialDyeBlocks.newBlockArry = specialDyeBlocks.newBlockArry.concat(angular.copy(specialDyeBlocks.checkedBlocks));
        console.info("添加染色类别后的数据",specialDyeBlocks.newBlockArry);

      }*/

      //循环一遍选择的弹窗里的蜡块 给所有的蜡块都加上选择的染色类别  2_按蜡块分类
      angular.forEach(specialDyeBlocks.checkedBlocks,function(item,index,array){

        for (var i=0;i<specialDyeBlocks.specialDyeTypeArry.length;i++){

          var special = specialDyeBlocks.specialDyeTypeArry[i];
          item.specialDye=special.specialDye;
          item.ihcMarker=special.ihcMarker;
          item.note=special.note;

          // 每一次循环数据翻一倍 按蜡块分类 先循环病理号 再加上染色类别
          specialDyeBlocks.newBlockArry.push(angular.copy(item));
        }

      });

      $uibModalInstance.close(specialDyeBlocks.newBlockArry);

    };

    //取消按钮
    specialDyeBlocks.cancel = function(){
      $uibModalInstance.dismiss();
    }
  }//end CommonModalController
})();


