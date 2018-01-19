(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SpecialDyeCreateController',SpecialDyeCreateController )

  /** @ngInject */
  function SpecialDyeCreateController($rootScope,$filter,IhcService,MaterialService,toastr,$uibModal,$state,SystemSettingService,UserService){

    var specialDyeCreate = this;

    SystemSettingService.getSpecialDyeList().then(function(res){
      specialDyeCreate.specialDyeList=res;
      // specialDyeCreate.specialDyeList.unshift({ name: "白片", code: null });
    });//特染类别获取



    /*MaterialService.getDepartments().then(function(data) {
      specialDyeCreate.departmentList = data;
      specialDyeCreate.departmentList.unshift({ name: "请选择", value: undefined });
    }); //1.3 获取送检科室列表*/

    function init(){

      UserService.getUser($rootScope.user.id).then(function (result) {
        specialDyeCreate.userId = result.id;//当获取到用户信息时显示科室 不然页面抓取不到
        specialDyeCreate.departmentsDesc = result.departmentsDesc;
        specialDyeCreate.applyData.departments = result.departments;
      })

      specialDyeCreate.getDepartments = function(filter) {
        MaterialService.getDepartments(filter).then(function(data) {
          specialDyeCreate.departmentList = data;
          //pathological.departmentList.unshift({ name: "请选择", value: undefined });
        });
      }; //1.3 获取送检科室列表
      specialDyeCreate.getDepartments();

      specialDyeCreate.applyData={
        applyUser:$rootScope.user.firstName,
        applyTel:$rootScope.user.phone,
        ihcBlocks:[
        //   {
        //   serialNumber:"",
        //   subId:"",
        //   specialDye:1,
        //   ihcMarker:"",//特染要求
        //   note:"",
        // }
        ]
      };

    }


    init();




    //添加蜡块弹窗
    specialDyeCreate.myBlocks=function(){
      var modal={
          templateUrl: 'app/site/specialDye/modal/specialDyeBlocks.html',
          size:'lg',
          // backdrop: false,
          controller: 'SpecialDyeBlocksModalController',
          controllerAs: 'specialDyeBlocks',
          resolve: {
            checkedBlocks: function () {
              return specialDyeCreate.checkedBlocks||[]; //传回去
            },
          }
        };



      var myBlocksModal=$uibModal.open(modal);
      myBlocksModal.result.then(function(res){
        if(!res.length) return false;
        specialDyeCreate.checkedBlocks=res||[]; //存放从 modal 选择的蜡块  specialDyeCreate.checkedBlocks
        // console.info("将拿到的蜡块",specialDyeCreate.checkedBlocks);
        // console.info("要提交的蜡块",specialDyeCreate.applyData.ihcBlocks);


        // 将拿到的蜡块添加到表格里
        for(var i=0;i<specialDyeCreate.checkedBlocks.length;i++){
          var block=specialDyeCreate.checkedBlocks[i];  // 选中的蜡块

          var len = specialDyeCreate.applyData.ihcBlocks.length; //需要将length拿出来 不然随着push会一直增加
          var isNew = true;

          for(var j=0;j<len;j++){
            var item=specialDyeCreate.applyData.ihcBlocks[j]; // 要提交申请的蜡块
            if(block.pathologySerialNumber===item.serialNumber&&block.subId===item.subId&&block.specialDye===item.specialDye){
              isNew = false;
              break;//蜡块是否已经添加过了
            }
          }

          if(isNew){
            specialDyeCreate.applyData.ihcBlocks.push({
              serialNumber:block.pathologySerialNumber,
              subId:block.subId,
              specialDye:block.specialDye,
              ihcMarker:block.ihcMarker,//特染要求 转换成数组
              note:block.note
            });
          }

        }

      });

    };

    // 添加蜡块
    specialDyeCreate.addBlock=function(){
      specialDyeCreate.applyData.ihcBlocks.push({
        serialNumber:"",
        subId:"",
        specialDye:1,
        ihcMarker:"",//特染要求 提交时转换成数组
        note:"",
      });

    };

    //选择特染类别
    specialDyeCreate.specialDyeSelect=function(data){ //data 是从页面上传的

      angular.forEach(specialDyeCreate.specialDyeList,function(item,index,array){

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

    specialDyeCreate.deleteBlock=function(index){
      specialDyeCreate.applyData.ihcBlocks.splice(index,1);
    };

    //申请
    specialDyeCreate.apply=function(){

      if(specialDyeCreate.applyData.ihcBlocks.length){

        var double = false; //表示重复 先检查重复
        for(var i=0;i<specialDyeCreate.applyData.ihcBlocks.length;i++){
          var block=specialDyeCreate.applyData.ihcBlocks[i];  // table中的蜡块

          var len = specialDyeCreate.applyData.ihcBlocks.length; //需要将length拿出来 不然随着push会一直增加


          for(var j=i+1;j<len;j++){
            var item=specialDyeCreate.applyData.ihcBlocks[j]; // 要提交申请的蜡块
            if(block.serialNumber===item.serialNumber&&block.subId===item.subId&&block.specialDye===item.specialDye){
              toastr.error("有蜡块重复请检查",block.serialNumber+"-"+item.subId);
              console.info(block)

              double = true;
              // break;//蜡块是否已经添加过了
            }
          };

        }
        if(double){
          return; //没有重复的时候才到下一步
        };

        // ihcMarker 将特染要求转成数组 根据 ； 分割
        angular.forEach(specialDyeCreate.applyData.ihcBlocks, function(data,index,array){

          if(data.specialDye===null){ // 判断染色类别是白片的时候
            if(data.ihcMarker&&data.ihcMarker.split){
              data.ihcMarker.split(/[;；]/).forEach(function (marker,index,arr) {
                  marker="";
                  data.ihcMarker=arr;
              });
            };
            data.ihcMarker.splice(0,1)
            // console.info(data.ihcMarker)

          }else if(data.ihcMarker&&data.ihcMarker.split){
             var str = data.ihcMarker.charAt(data.ihcMarker.length - 1);
             if(str===";"||str==="；"){data.ihcMarker=data.ihcMarker.substring(0,data.ihcMarker.length-1)};//去掉最后一个 “；”
             data.ihcMarker = data.ihcMarker.split(/[;；]/) //
           }

        });

      // console.info(specialDyeCreate.applyData); return false;

        IhcService.post("/specialDye",specialDyeCreate.applyData).then(function(res){
          toastr.success("申请成功");
          init();
        },function(err){
          toastr.error(err.reason);
                  // ihcMarker 将特染要求转成数组 根据 ； 分割
          angular.forEach(specialDyeCreate.applyData.ihcBlocks, function(data,index,array){

            if(data.specialDye===null){
              data.ihcMarker=null;
              // data.ihcMarker = data.ihcMarker.join(";")
              // data.ihcMarker=data.ihcMarker.splice(0,1)

            }else if(data.ihcMarker){

              data.ihcMarker = data.ihcMarker.join(";") //
            }

          });
        })

      }else{
        toastr.error("请添加蜡块");
      }
    };








  }//end




})();
