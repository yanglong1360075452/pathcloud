//申请深切弹窗
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('applySpecialController', applySpecialController);

  /** @ngInject */
  function applySpecialController($rootScope,$uibModalInstance,$state,toastr,DiagnosisService,data,MaterialService,IhcService){
    var applySpecial = this;
    applySpecial.serialNumber = data.serialNumber;//病理号
    applySpecial.patientName = data.patientName;//病人姓名
    applySpecial.blockSubId = data.blockSubId;//蜡块号
    applySpecial.blockId = data.blockId;//蜡块ID
    applySpecial.specialHistoryResult = data.specialHistoryResult;//特检历史结果
    applySpecial.blockData = [];//蜡块信息数组
    applySpecial.pathid = data.pathid;//
    applySpecial.diagnoseDom = data.diagnoseDom;//
    applySpecial.canApplyIhc = data.canApplyIhc;
    applySpecial.ihcId = data.ihcId;
    applySpecial.canApplySpecialDye = data.canApplySpecialDye;
    applySpecial.specialDyeId = data.specialDyeId;
    
    function checkDuplicatedMarker(marker) {
      //特检申请之前 校验标记物是否已经重复
      DiagnosisService.checkSpecialApplyMarker(applySpecial.selectedBlock.id, marker).then(function (res) {
        if(res){
          toastr.error(marker+"重复申请")
        }else {
      
          if(applySpecial.markerList.indexOf(marker) < 0){
            applySpecial.markerList.push(marker);
          }
      
          applySpecial.marker = ""; //添加后清空输入框
          
        }
      });
    }
    
    function init() {
      applySpecial.markerList = [];
      applySpecial.handleKeyDown = function (e) {
        var keyCode = window.event?e.keyCode:e.which;
    
        // debugger // 229 186 中英文 分号的keycode
        if(keyCode == 13 || keyCode == 229|| keyCode == 186){
  
          if(applySpecial.marker === ""||/[；;\$\#]/.test(applySpecial.marker)){
            console.log("输入不合法");
            toastr.error("输入不合法");
            return;
          }
          
          e.preventDefault();
          e.stopImmediatePropagation();
  
          //  校验试剂库是否存在该抗体  【抗体在试剂工作站中添加】
          IhcService.get("/reagent/name",{name:applySpecial.marker,type:2}).then(function (res) {
            if(res){
              checkDuplicatedMarker(applySpecial.marker)
            }else {
              toastr.error("试剂库不存在"+applySpecial.marker+"抗体");
              return false;
            }
          })
          
        }
      }
    }
    init();

    //根据病理ID获取蜡块信息
    IhcService.get("/diagnose/"+data.pathid+'/dye').then(function (res){
      // console.info(res);
      applySpecial.blockData = res;
    });

    //通过诊断特检设置接口获取的特染类别
    IhcService.get("/systemSetting/specialDye").then(function (res) {
      applySpecial.specialDyeList = res;

    },function (err) {
      toastr.error(err.reason)
    });

    //获取科室
    MaterialService.getDepartments().then(function (departments) {
      applySpecial.departmentList=departments;
    });


    //通过页面显示隐藏 做选择抗体功能 【同时把输入框中手动输入的也自动勾上】
    applySpecial.showMarkerPart = false;
    applySpecial.showMarker = function () {
      applySpecial.showMarkerPart = !applySpecial.showMarkerPart;
      // applySpecial.department = null;
      // applySpecial.illnessType = null;
      // applySpecial.illnessList = null;
  
      angular.forEach(applySpecial.illnessList,function (item,index,arr) {
    
          if(applySpecial.markerList.indexOf(item.name) < 0 ){
            item.checked = false
          }else {
            item.checked = true
          }
        
      });
      
    };
    applySpecial.selectDepartment = function (code) {
      MaterialService.getTemplate(code,3).then(function (res) {
        applySpecial.illnessTypeList = res;
      })
    };
    // 【选择疾病类别】 【选择抗体的时候 把输入框 applySpecial.markerList:[]  中手动输入的也自动勾上】
    applySpecial.selectIllnessType = function(id){

      applySpecial.illnessList = []; //页面上可选择的标记物数组格式 { name：标记物，checked：true} 通过下面方法得到
      
      var marks = [];
      angular.forEach(applySpecial.illnessTypeList,function (item,index,arr) {
        // consle.info(arr)
        if(item.id === id && item.content){
          marks = JSON.parse(item.content)||[]; //得到标记物数组
        }
        
      });
      
      /*var illnessList = content.split(/[\r\n]/g);//定义选择的疾病
      // console.info("疾病列表",illnessList);
      // console.info("疾病列表",content.split("↵"));*/
      
      
      angular.forEach(marks,function (item,index,arr) {

        if(item === '' || /^\s+$/.test(item)){
          // arr.splice(index,1)
        }else {
          
          if(applySpecial.markerList.indexOf(item) < 0 ){
            applySpecial.illnessList.push({
              name:item,
              checked: true
            })
          }else {
            applySpecial.illnessList.push({
              name:item,
              checked: true
            })
            
          }
        }
      });
      // console.info(applySpecial.illnessList)
    };

    applySpecial.next = function () {
      
      
      // 将勾选的疾病传到textarea
      var str = "";
      angular.forEach(applySpecial.illnessList,function (item,index,arr) {

        if(item.checked){
          str += item.name+";";
          // todo 11-29 试剂抗体需求修改 存成一个数组
          if(applySpecial.markerList.indexOf(item.name) < 0){
            applySpecial.markerList.push(item.name);
          }
        }
      });
      
      if(str === ""){
        toastr.error("请选择标记物");
        return false
      }
      
      /*applySpecial.applyData.ihcBlocks[0].ihcMarker = str;*/
      applySpecial.showMarkerPart = false;//显示选择抗体页面 隐藏常规页面
    };

    // 【蜡块特检保存的数据格式】//接口可以批量特检 这个页面只能一次选择一个蜡块进行特检
    applySpecial.applyData={
      applyUser:$rootScope.user.firstName,
      applyTel:$rootScope.user.phone,
      department:data.department,//特染申请送检科室信息
      ihcBlocks:[
        {
          serialNumber:applySpecial.serialNumber,//病理号
          // subId:"",
          // specialDye:1,  //特检类别  1是免疫组化
          // ihcMarker:[],//特染要求 标记物
          // note:"",
        }
      ]
    };
  
    // 选择特染蜡块
    applySpecial.selectBlock = function () {
      console.info(applySpecial.selectedBlock);
      applySpecial.applyData.ihcBlocks[0].subId = applySpecial.selectedBlock.subId
    };
  
    // 免疫组化 点击抗体后选标记物
    applySpecial.checkIhcMarker = function (item) {
      if(!item.checked) return;
      // todo 待修改 11-29
      DiagnosisService.checkSpecialApplyMarker(applySpecial.selectedBlock.id, item.name).then(function (res) {
        if(res == true){
          item.checked = false;
          toastr.error("特检标记申请重复")
        }
      });
    };
    //选择特染类别
    applySpecial.specialDyeSelect=function(code){
      // console.info(applySpecial.applyData.ihcBlocks[0].specialDye)
      // console.info(code)
      applySpecial.markerList = [];
      
      // 除了选择的是免疫组化外 其它的特检类别直接就是标记物的名称  //这里不能用 3个等于号
      if(code == 1){
        //  sprint 21 让免疫组化的标记物只能勾选  2. 判断是否能选用那个标记物
        /*applySpecial.applyData.ihcBlocks[0].ihcMarker = ""; */
        
      }else {
        
        angular.forEach(applySpecial.specialDyeList,function(item,index,array){
          if(code == item.code){
  
            // //特检申请之前 校验标记物是否已经重复
            // DiagnosisService.checkSpecialApplyMarker(applySpecial.selectedBlock.id, item.name).then(function (res) {
            //   if(res){
            //     applySpecial.applyData.ihcBlocks[0].ihcMarker = "";
            //     toastr.error("重复申请")
            //   }else {
            //     applySpecial.applyData.ihcBlocks[0].ihcMarker = item.name;
            //   }
            // });
            
            // applySpecial.applyData.ihcBlocks[0].ihcMarker = item.name;
  
            applySpecial.markerList = [];
            checkDuplicatedMarker(item.name);
          }
        });
  
        
      }

    };
    

    //申请 【照着批量特检蜡块的接口写的 防止以后会做成批量申请的】
    applySpecial.apply=function(){

      if(applySpecial.applyData.ihcBlocks.length){

        var double = false; //表示重复 先检查重复
        for(var i=0;i<applySpecial.applyData.ihcBlocks.length;i++){
          var block=applySpecial.applyData.ihcBlocks[i];  // table中的蜡块

          var len = applySpecial.applyData.ihcBlocks.length; //需要将length拿出来 不然随着push会一直增加


          for(var j=i+1;j<len;j++){
            var item=applySpecial.applyData.ihcBlocks[j]; // 要提交申请的蜡块
            if(block.serialNumber===item.serialNumber&&block.subId===item.subId){
              toastr.error("有蜡块重复请检查",block.serialNumber+"-"+item.subId);
              // console.info(block)

              double = true;
              // break;//蜡块是否已经添加过了
            }
          }

        }
        if(double){
          return; //没有重复的时候才到下一步
        }

        // ihcMarker 将特染要求转成数组 根据 ； 分割
        angular.forEach(applySpecial.applyData.ihcBlocks, function(data,index,array){
          data.result = applySpecial.diagnoseDom; //特检的时候传诊断内容

          // if(data.ihcMarker && data.ihcMarker.split){
          //    var str = data.ihcMarker.charAt(data.ihcMarker.length - 1);
          //    if(str===";"||str==="；"){data.ihcMarker=data.ihcMarker.substring(0,data.ihcMarker.length-1)};//去掉最后一个 “；”
          //    data.ihcMarker = data.ihcMarker.split(/[;；]/)|| [] //
          //  }
  
          // 11-29 sprint 24 新改动
           if(!applySpecial.markerList.length){
            toastr.error("请选择抗体");
             return false
           }else {
             data.ihcMarker = applySpecial.markerList
           }

        });
        
        
  
        // 新的改动判断是 免疫组化的申请 还是特染的申请
      
        if( applySpecial.canApplyIhc && applySpecial.canApplySpecialDye && applySpecial.applyData.ihcBlocks[0].specialDye){
          if(applySpecial.applyData.ihcBlocks[0].specialDye == 1){
            applySpecial.applyData.id = applySpecial.ihcId
          }else if(applySpecial.applyData.ihcBlocks[0].specialDye > 1){
            applySpecial.applyData.id = applySpecial.specialDyeId
          }
        }if( applySpecial.canApplyIhc && applySpecial.applyData.ihcBlocks[0].specialDye == 1){
          applySpecial.applyData.id = applySpecial.ihcId
        }else if( applySpecial.canApplySpecialDye && applySpecial.applyData.ihcBlocks[0].specialDye > 1){
          applySpecial.applyData.id = applySpecial.specialDyeId
        }else {
          toastr.error("特检类别不匹配");
          return false
        }
        

      // console.error(applySpecial.applyData); return false;
        applySpecial.applyData.source = 1;
        IhcService.post("/specialDye",applySpecial.applyData).then(function(res){
          toastr.success("申请成功");
          $uibModalInstance.close();
        },function(err){
          toastr.error(err.reason);
                  // ihcMarker 将特染要求转成数组 根据 ； 分割
          /*angular.forEach(applySpecial.applyData.ihcBlocks, function(data,index,array){

            if(data.specialDye===null){
              data.ihcMarker=null;
              // data.ihcMarker = data.ihcMarker.join(";")
              // data.ihcMarker=data.ihcMarker.splice(0,1)

            }else if(data.ihcMarker){

              data.ihcMarker = data.ihcMarker.join(";") //
            }

          });*/
        })

      }else{
        toastr.error("请添加蜡块");
      }
    };
    

    //取消
    applySpecial.cancel = function(){
      $uibModalInstance.dismiss();
    };

  }
})();
