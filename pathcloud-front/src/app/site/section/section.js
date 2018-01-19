/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('SectionController', SectionController);

  /** @ngInject */
  function SectionController($timeout,$state,toastr, sectionService, paramSettingService,printerService,toolService, IhcService, $filter,$uibModal) {
    var section=this;
    section.filter="";
    section.remark={};
    section.printTypeList = [{name:"玻片",code:1},{name:"标签",code:2}];
    section.sectionPrintMedium = 1;
  
    IhcService.get('/paramSetting/sectionPrintMedium').then(function (res) {
      section.sectionPrintMedium = parseInt(res) //1 玻片打印机 [打印service 里有两种打印方法]
    });
    IhcService.get('/paramSetting/sectionPrintWay').then(function (res) {
      section.sectionPrintWay = res //1  自动打印
    });
    
    paramSettingService.sectionRemark().then(function (res) {
      section.sectionRemarkLIst=res;
    },function (err) {
      toastr.error(err);
    });
    // ### 3.3 获取待切片蜡块数
    function count() {
      sectionService.sectionCount().then(function (res) {
        section.count=res;
      })
    }
    count();
    

    //点击按钮之后搜索框获得焦点
    $timeout(function () {
      $("button").click(function () {
        $("#searchInput").focus();
      })
    },1000);
    

    //通过点击下方的玻片号按钮 显示待切片的数据完成切片操作
    section.getBlockInfoButton=function (item) {
      section.filter = section.blockInfo.pathologySerialNumber +"-"+ item; //拼接病理号跟蜡块号
      section.getBlockInfo();
    };
    
    // 获取蜡块/玻片信息
    section.getBlockInfo=function () {
      /*
      * 1，通过蜡块号搜索获取信息来切片 病理号-蜡块号
      *
      * 2，通过玻片号搜索玻片信息来切片 病理号-蜡块号-玻片号
      *
      * */
      
      if(!section.filter){
        section.blockInfo={};return;
      }else if(section.filter.indexOf("-")<0){
        toastr.error("输入的格式有误"); //不传- 的话没法分离出来 玻片号
        return;
      }

      
      var search = section.filter.split("-");
  
      section.slideSubId = "";
      section.filterStr = "";
      
      section.filterStr = search[0]+search[1]; //分离出 病理号 蜡块号  切片查询
      section.slideSubId = search[2]; //分离出玻片号 单独传到后台
    
      // console.info(section.filterStr, section.slideSubId)
      
      section.filterSave = section.filterStr;
      
      

      if(section.blockInfo&&section.blockInfo.blockId){//每次先看上一次的有没有
        if(section.blockInfo.status==15){

          section.confirmSection(); //切片保存上一个

        }else {
          getBlockInfo()
        }
      }else {
        getBlockInfo()
      }
    };

    
    // 完成切片 按钮
    section.confirmSection=function (from) {

      if(!section.blockInfo.blockId) return;
  
      function slideConfirm(from){ //通过传参数判断是不是页面收到点了打印
    
        var length=0;
        if(section.blockInfo.marker){
          length = section.blockInfo.marker.length;
          if(!length) return false;
        }
    
        var specialData=[]; //调接口确认时候传的数据
    
        //2017-4-26 切片改动 蜡块染色数据存放在 blockInfo.dyeApply [{},{}]  当是特检申请的蜡块有多个标记物时就要打印 多个玻片信息
        angular.forEach(section.blockInfo.dyeApply, function(item,index,arr){
          //循环标记物
          angular.forEach(item.sectionSlides, function(subItem,index,arr){
        
            // console.info("section.printResponse",section.printResponse)
            // 当蜡块信息中没有玻片id时 判断是否打印过了 要是打印过了 就到 section.printResponse 里获取
            if(!subItem.slideId){
              //  当第一次获取蜡块信息 是自动打印 后端就会生成玻片号 跟 slidId
              if(section.printResponse){ // 如果当前标记没有玻片id 就看是否有打印过的缓存 section.printResponse
                angular.forEach(section.printResponse, function (printItem) {
                  // 通过比较标记物获取玻片id
                  if(subItem.marker === printItem.marker){
                    subItem.slideId = printItem.slideId
                  }
                  // console.info(subItem)
                })
              }
            }
            
            specialData.push({
              blockId:  section.blockInfo.blockId,
              noteType: section.remark.noteType, //页面选择的备注类型
              note: section.remark.note,  //页面备注
              slideMarker: subItem.marker,
              slideId: subItem.slideId,
              specialDye: item.specialDye,
              applyId: item.applyId,
          
              number: section.blockInfo.number  //特殊申请号 传给后端就行了
            });
        
          });
      
        });
    
        // console.info(specialData)
        section.pending = true;
        sectionService.confirmSpecialDyeSection(specialData).then(function (res) {
          toastr.success("切片保存成功！");
          count();
          section.blockInfo.status=null;
          if(from){
            confirmCheck();// 切片保存成功后判断是否还有未切的蜡块
          }else {
            getBlockInfo();// 切片保存成功后获取新的数据
          }
        },function (err) {
          toastr.error(err,"切片保存失败");
        }).finally(function () {
          section.pending = false;
        })
    
    
      } //特染切片确认 传多个
      
      slideConfirm(from)
      
      
    };// 切片
    
    //完成切片时检查当前病理号是否还有未切片的蜡块
    function confirmCheck() {
      var params = {
        prePathId:section.blockInfo.pathologyId,
        slideSubId: section.slideSubId
      };
      sectionService.getBlockInfo(section.blockInfo.blockSerialNumber,params).then(function (res) {

        // console.info("完成切片时检查当前病理号是否还有未切片的蜡块")
        //判断是否有未切的蜡块  检查是否该病理下还有未切片的蜡块
        if(!res.pathologyId){

          //拼接 病理号跟蜡块号
          for (var i=0;i<res.length;i++){
            res[i]=section.blockInfo.pathologySerialNumber+'-'+res[i];
          }

          res=res.join("，");
          toolService.getModalResult({modalTitle:"当前病理号仍有蜡块未切片",modalContent:"未切片蜡块号："+res,size:"dialog"}).then(function () {
            // 点击忽略
            // getBlockInfo("ignore")
          },function () {
            // console.info("忽略蜡块");
          });

          return false;
        }
      }).finally(function () {
        section.blockInfo={};//清空页面
      })
    }

    // 打印按钮  手动点击页面上的打印
    section.print=function (index,specialDye) {

      // todo  调用后端返回的数据打印
      if(section.blockInfo.status===15){
        
        printSlide()
        
      }else{
        handlePrint() //切片后 通过调用后台的接口打印 弹窗里选择要打印的玻片
      }

    }; //section.print =======end



    function getBlockInfo(a) {
      
      section.printResponse = null; //调用后端打印后返回的打印数据
      
      // if(!section.filter) {section.blockInfo={};return;} //防止点完成切片后会再次获取该病理号信息
      //第一步 检查是否该病理下还有未切片的蜡块 确认跳过之后才打印
      // console.info("第一步 检查是否该病理下还有未切片的蜡块 确认跳过之后才打印");
      section.remark={};
      section.filterCopy=angular.copy(section.filterSave); //切片不存在时提示病理号

      section.currentPathologyNumber=section.filterSave.substring(0,9);//截取前9位为病理号 跟上一个病理比较用 获取当前页面上缓存的病理号

      var prePathId;
      if(a!='ignore'&&section.blockInfo&&section.blockInfo.blockId){//每次先看上一次的有没有

        if(section.currentPathologyNumber!=section.blockInfo.pathologySerialNumber){
          prePathId=section.blockInfo.pathologyId;
          // console.info(section.prePathId)
        }
      }
      
      var params = {
        prePathId:prePathId, //病理号 + 蜡块号
        slideSubId: section.slideSubId // 玻片号 表明搜索的是玻片号
      };
      section.pending = true;
      sectionService.getBlockInfo(section.filterSave,params).then(function (res) {

        // console.info("判断是否有未切的蜡块  检查是否该病理下还有未切片的蜡块")
        //判断是否有未切的蜡块  检查是否该病理下还有未切片的蜡块
        if(!res.pathologyId){

          //拼接 病理号跟蜡块号
          for (var i=0;i<res.length;i++){
            res[i]=section.blockInfo.pathologySerialNumber+'-'+res[i];
          }

          res=res.join("，");
          
          toolService.getModalResult({modalTitle:"上一个病理号仍有蜡块未切片，是否忽略",modalContent:"未切片蜡块号："+res,size:"dialog"}).then(function () {
            // 点击忽略
            getBlockInfo("ignore")

          },function () {
            console.info("忽略蜡块");
          });

          return false;
        }

        // console.info("第2步 处理一下得到的数据 在判断是否要重新打印 ");
        //处理一下得到的数据 在判断是否要重新打印
        section.blockInfo=res;
        section.remark.noteType=res.sectionRemarkType;
        section.remark.note=res.sectionRemark;
        
        //得到特染申请人
        if(res.specialDye&&res.dyeApply&&res.dyeApply.length){ //判断是不是特染 不是特染的没有特染申请人字段
          section.blockInfo.specialDyeApplicant = res.dyeApply[0].applicant && res.dyeApply[0].applicant.firstName
        }

        // 页面上显示的所有标记物 跟染色类别
        section.marker = []; //页面显示特检标记物
        section.dyeTypeList = []; //页面显示特检类别
        section.blockInfo.specialDyeRemark=[]; //备注
        section.slidePrintRequest = []; //玻片打印时 要传到后端的数据{ blockId ，clideId ，applyId }  //常规打印
        // section.specialSlidePrintRequest = []; //玻片打印是要传到后端的数据{ blockId ，clideId ，applyId }  //免疫组化打印 切片工作站不打印特检
        
        angular.forEach(section.blockInfo.dyeApply,function(item,index,arr){
          
          ////循环标记物 sectionSlides 存放特检标记物 玻片id [marker slideId]
          angular.forEach(item.sectionSlides, function (subItem) {
            if(subItem.marker===""){
              subItem.marker=";"
            } //白片的标记物用;标示
            section.marker.push(subItem.marker);
            
            if(item.specialDye === 0){
              section.slidePrintRequest.push({
                blockId: res.blockId,
                slideId: subItem.slideId,
                applyId: item.applyId
              })
            }/*else if(item.specialDye > 0){  // 【免疫组化 特检玻片的打印   目前不需要这个功能】
              section.specialSlidePrintRequest.push({
                blockId: res.blockId,
                slideId: subItem.slideId,
                applyId: item.applyId,
                slideMarker: subItem.marker,
                specialDye: item.specialDye
              })
            }*/
            
          });
          
          section.dyeTypeList.push(item.specialDyeDesc); //染色类别 页面显示
          section.blockInfo.specialDyeRemark.push(item.note);
        });

        //设置了自动打印 判断是否直接打印
        if(section.sectionPrintWay == 1){
          
          if(!params.slideSubId){ //扫描的不是玻片才打印
            
            if(res.status > 15 &&status!==40){
              printConfirm(res.status, res.specialDye);
            }else{
              printSlide();
            }
            
          }
          //  有玻片slideId 就说明是扫描的玻片
        }

      },function (err) {
        toastr.error(err,section.filterCopy)
      }).finally(function () {
        section.pending = false;
      });

    }

    
    // 提示是否需要打印
    function printConfirm(status, specialDye) { //在 print中 判断的话 status 不需要
      // console.info("提示是否需要打印");
      //重新打印 时要获取下蜡块下的玻片信息
      var content = "该蜡块于"+ $filter('date')(section.blockInfo.sectionOperateTime,'yyyy-MM-dd HH:mm') + "被"+ section.blockInfo.sectionOperatorDesc + "切片，目前状态是" + (section.blockInfo.statusDesc||section.blockInfo.statusName) +"，是否需要重新打印切片？";
      toolService.getModalResult({modalTitle:"提示",modalContent:content,size:"sm"}).then(function () {
        handlePrint(); //调后台返回的数据 在弹窗里进行选择打印
      });
    }
  
    function handlePrint(){
    
      //打开一个选择打印的弹窗
    
      var printModal={
        templateUrl: 'app/site/section/modal/printSlide.html',
        size:'lg',
        // backdrop: false,
        controller: 'SectionPrintModalController',
        controllerAs: 'printModal',
        resolve:{
          data:{
            blockId:section.blockInfo.blockId,
            pathologySerialNumber:section.blockInfo.pathologySerialNumber,
            subId:section.blockInfo.subId,
            slideInfo:section.blockInfo.pathologySerialNumber+"$"+section.blockInfo.subId+"$",
            bodyPart:section.blockInfo.bodyPart,
            printType:section.sectionPrintMedium //打印的方式
          
          }
        }
      
      };
      $uibModal.open(printModal);
    
    }
    
    function printSlide() {
      
      /*
      * 此函数用来生成蜡块的玻片号 获取要打印的数据 判断是否要打印  已经有了玻片信息的数据获取的时候 通过printConfirm 打印
      * @section.printResponse  缓存当前玻片号信息【是否后端已经生成过玻片数据 因为后端生成了玻片信息但是又不是自动打印的时候  再点击打印就要这个缓存的数据打印】
      * */
      
      
      
      // sprint21 ### 3.1 打印常规玻片  通过玻片号获取信息 后端记录打印次数 返回要打印的数据
      var slidePrintRequest = [];
      // var specialSlidePrintRequest = [];
      if(section.printResponse){
        // 当前页面已经打印过了一次后 页面缓存后端返回的打印数据到 section.printResponse  手动再点打印的话 传到后端记录打印次数
        angular.forEach(section.printResponse, function ( item ) {
          
          if(item.specialDye == 0){
            slidePrintRequest.push({
              blockId: section.blockInfo.blockId,
              slideId: item.slideId,
              applyId: item.applyId
            });
          }/*else if(item.specialDye > 0){ //【免疫组化特检玻片的打印    目前不需要这个功能】
            specialSlidePrintRequest.push({
              blockId: section.blockInfo.blockId,
              slideId: item.slideId,
              applyId: item.applyId,
              slideMarker: item.marker,
              specialDye: item.specialDye
            })
          }*/
        
          
        })
        
      }else{
  
        slidePrintRequest = section.slidePrintRequest; //获取常规的要打印的数据
        // specialSlidePrintRequest = section.specialSlidePrintRequest; //获取免疫组化要打印的数据
      }
  
      /*//判断是否有免疫组化的也通过切片工作站打印  先看缓存   【废弃的功能 目前不让打印特检的玻片】
      if(specialSlidePrintRequest.length){
        IhcService.post("/ihc/print",specialSlidePrintRequest).then(function (res) {
          sectionService.print(res, section.sectionPrintMedium);
          section.printResponse = res
        })
      }*/
      
      if(slidePrintRequest.length){
        IhcService.post('/section/print',slidePrintRequest).then(function (res) {
  
          toastr.success("打印玻片成功！");
          // 调用service 里的打印方法 传要打印的数据和打印类型 1 是玻片打印机打印
          sectionService.print(res, section.sectionPrintMedium);// 提取切片打印方法 到 sectionService
    
          // 保存后端返回的打印信息 在第一次扫描蜡块的时候 要是打印了就会生成玻片号 这时切片确认就要传玻片id 到后端
          section.printResponse = res;
        })
      }else {
        toastr.error("打印玻片失败！");
      }
      
      
    }
    
  
    

   
  }
})();



/*function newPrint() {
 var lastSlideSubId = sectionData.slideSubId; // 防止玻片号一直增加 应该暂存
 var id=section.blockInfo.pathologySerialNumber+"$"+section.blockInfo.subId+"$"+lastSlideSubId;
 var printSlideIds = [];//todo 后端保存打印记录
 
 if(!id) return;
 
 
 
 angular.forEach(section.blockInfo.dyeApply,function(item,index,arr){
 //循环标记物 当有标记物的时候 打印多张玻片
 angular.forEach(item.marker,function(marker,index,arr){
 var type="";
 if(section.blockInfo.bodyPart){
 type=section.blockInfo.bodyPart;
 // id=id+"$"+section.blockInfo.bodyPart; //加到id里传
 }
 if(marker){
 type= section.blockInfo.bodyPart+"$"+marker;
 }
 // console.info("待切片玻片号",id)
 // todo 判断是用什么方式打印 弹窗 handlePrint()统一修改
 if(section.printType === 1){ //玻片打印
 printerService.labWrite(id,type).then(function () { //加一个type
 
 });
 }else {
 // 通过标签打印机打印  要打印 病理号
 var printData = {
 pathologySerialNumber:section.blockInfo.pathologySerialNumber,
 subId:section.blockInfo.subId,
 lastSlideSubId:lastSlideSubId,
 bodyPart:section.blockInfo.bodyPart,
 marker:marker,
 
 };
 printData.code = section.blockInfo.pathologySerialNumber +"-"+section.blockInfo.subId +"-" +lastSlideSubId;//二维码内容
 printerService.printSlide(printData)
 }
 
 
 //循环一个玻片号就自动加一
 lastSlideSubId++;
 id=section.blockInfo.pathologySerialNumber+"$"+section.blockInfo.subId+"$"+lastSlideSubId;
 });
 
 });
 
 }// 2017-4-26 新的打印方法 待切片状态的蜡块通过这个打印*/
