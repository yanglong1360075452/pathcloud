/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('CreateMaterialController', CreateMaterialController);

  /** @ngInject */
  function CreateMaterialController(apiUrl,$scope,$state,$rootScope,$cookieStore,toolService,IhcService,videoService,$uibModal,ApplicationService,PathologyService,toastr,MaterialService,printerService,$timeout,photoService,$interval) {
    var createMaterial = this;

    var countByLetters=false;//是否用字母方式计数


    createMaterial.subIdList = [1,'A','A1','B1','C1'];
    createMaterial.defaultNormalBlockId = 1; //自动计算 分类【未实现】
    createMaterial.defaultFrozenBlockId = "F1"; //冰冻蜡块 初始蜡块号设置 后面接口调用

    function initFrozenSetting(){

      MaterialService.getCountType().then(function (data) { // 4.2 获取常规蜡块编码方式
        createMaterial.normalCountType = data;
        if(data == 1){
          countByLetters = false;
          createMaterial.defaultNormalBlockId = 1;
        }else {
          countByLetters = true;
          createMaterial.defaultNormalBlockId = "A";
        }
      });
      IhcService.get("/paramSetting/frozenCountType").then(function (result) { // 获取冰冻蜡块编码方式
        createMaterial.frozenCountType = result; //编码code 1-数字 2-字母
        if(result == 1){
          createMaterial.defaultFrozenBlockId = 1;
        }else {
          createMaterial.defaultFrozenBlockId = "F1";
        }
      });

      IhcService.get("/systemSetting/usingFrozen").then(function (result) {
        createMaterial.usingFrozen = result; // 0-不使用 1-使用
      });

      IhcService.get("/systemSetting/printFrozen").then(function (result) {
        createMaterial.printFrozen = result; //0-不打印 1-打印
      });

    }
    initFrozenSetting();



    //视频录制模块
    // videoService.init(
    //   {
    //
    //   }
    // )
    createMaterial.imgUrlHeader = apiUrl.substr(0,apiUrl.length-3);




    //点击其他地方收起模板选择
    function stopPropagation(e) {
      if (e.stopPropagation)
        e.stopPropagation();
      else
        e.cancelBubble = true;
    }

    createMaterial.closeTemplate=function () {
      if(createMaterial.open){
        createMaterial.open=false;
      }
    }

    $('#template').bind('click',function(e){
      stopPropagation(e);
    });

    createMaterial.changeTemplateStatus=function (e) {
      createMaterial.open = !createMaterial.open;
      stopPropagation(e);
    }


    //todo  huyu 12-30  拍照修改
    // var c = a.concat(b);
    createMaterial.imgList = [];
    createMaterial.perItemNum = 3; //每页图片数
    // createMaterial.photo=function () {
    //   if(!createMaterial.pathologicData) return;
    //
    //   var imgUrl=videoService.photo();
    //   //新增照片不每次都保存 在点击保存时一起保存
    //   // createMaterial.imgList.push(imgUrl);
    //   // createMaterial.pathologicData.media.images.push(imgUrl);
    //
    //   //每次拍照时的照片都保存
    //   // createMaterial.pathologicData.media.push({type:1,url:imgUrl});
    //   var base64 = imgUrl.split(",")[1];
    //   toolService.fileUpload(base64,1,createMaterial.pathologicData.id).then(function (res) {
    //     res[0].url = imgUrl;
    //     // console.log(res[0])
    //     createMaterial.pathologicData.media.push(res[0]);
    //
    //   })
    // }


    //2017-12-11 图像采集
    photoService.clearLocalPhoto();
    function insertPhoto() {
      photoService.getLocalPhotoUrlList().then(function (res) {
        console.info("抓取到的本地的照片",res);
        angular.forEach(res, function (item) {
          if(createMaterial.pathologicData.media.length >= 10){
            toastr.error("最多只能保存10张照片,请删掉一些照片后再重拍");
          }else {
            createMaterial.pathologicData.media.push({
              url: item
            });
            //  上传本地图片到服务端
            photoService.getBase64(item).then(function (Base64) {
              // debugger
              if (!createMaterial.pathologicData ) return;
              toolService.fileUpload(Base64, 1, createMaterial.pathologicData.id).then(function (res) {
                var length =  createMaterial.pathologicData.media.length;
                createMaterial.pathologicData.media[length-1].id = res[0].id;
              });
            });
          }
        });

      });
    }

    //定时
    var insertPhotoInterval = $interval(function () {
      if(createMaterial.pathologicData.id){
        insertPhoto();
      }
    }, 3000);//3秒刷新一次重新获取照片

    $scope.$on('$destroy', function () {
      $interval.cancel(insertPhotoInterval);
      photoService.clearLocalPhoto();
    });

    createMaterial.selectPhotoResult ={};
    //选择图片
    createMaterial.selectPhoto = function(url,index,id){
      createMaterial.selectPhotoResult.url = url;
      createMaterial.selectPhotoResult.index = index;
      createMaterial.selectPhotoResult.id = id;
      createMaterial.photoShow = true;
    };

    //删除图片
    createMaterial.deletePhoto = function(imgIndex,id){
     if(id&&createMaterial.photoShow){
       var delImg = createMaterial.pathologicData.media.splice(imgIndex,1)[0];
       toolService.fileDelete(delImg.id);
       createMaterial.photoShow = false;
     }else{
       toastr.error('图片暂时不能删除，请稍候');
     }
    };


    //放大图片
    createMaterial.showBigImg=function (imgUrl) {
      photoService.enlargePhoto(imgUrl);
    };

    $timeout(function () {
      createMaterial.containerWidth = $('#img-container').width();
      createMaterial.imgStyle={width:createMaterial.containerWidth/createMaterial.perItemNum+'px',height:' 60px',float:'left'};
    });


   // initVideo();

    // createMaterial.enlarge = function () {
    //   var modalInstance = $uibModal.open({
    //     templateUrl: 'app/site/modal/showVideo.html',
    //     controller: 'ShowVideoController',
    //     size: 'lg',
    //     resolve: {
    //
    //       position:function () {
    //         return 1;
    //       },
    //       pathologicalId:function () {
    //         return createMaterial.pathologicData.id;
    //       }
    //     }
    //   });
    //
    //   modalInstance.result.then(function () {
    //     videoService.showVideo(createMaterial.pathologicData.id).then(function (res) {
    //       createMaterial.pathologicData.media=res;
    //
    //       // console.error("获取拍照的图片信息",createMaterial.pathologicData.media);
    //     });
    //   })
    // }


    //todo 12-30 huyu  之前数据都是保存在全局 没按每个病理数据存  每条数据应在 getOne() 时保存到 createMaterial.pathologicData 中
    function init(){
      //2, 科室模板，最近常用模板
      MaterialService.getGrossingUser().then(function (data) {
        createMaterial.grossingUserLIst=data;
      }); //1.2 获取取材医生列表
      MaterialService.getDepartments().then(function (data) {
        createMaterial.departmentList=data;
      });//1.3 获取送检科室列表
      MaterialService.getTemplateUsed(1).then(function (data) {
        createMaterial.recentTemplate=data;
      });//2.5 常用模板

      //3, 取材保存部分
      MaterialService.getBiaoshi().then(function (data) {
        createMaterial.biaoshiList=data;
      });// 4.1 获取取材标识

      MaterialService.getBlockUnit().then(function (data) {
        createMaterial.blockUnitList=data;
      });// 4.3 获取组织数单位
      // createMaterial.pathologicData={blocks:[]};
      createMaterial.defaultTime=1;//设置默认筛选时
      createMaterial.defaultItem=0;//设置默认选择病理列表第一个
      createMaterial.activeDdepartment=1;//设置默认选中模板
      createMaterial.basketNumber=$rootScope.basketNumber;
      createMaterial.searchData={};
      createMaterial.filterData={
        createTimeStart:new Date().setDate(new Date().getDate()-1),
        sort:null,
        order:null,
        page:1,
        length:17
      };

      createMaterial.tableHeaders=[{name:"病理号",class:"text-center"}, {name:"姓名",class:"text-center"},{name:"状态",class:"text-center"}];
      createMaterial.statusList=[{name:"未登记",value:1},{name:"已登记",value:2},{name:"已拒绝",value:3},{name:"已撤销",value:4}];
      createMaterial.sampleStatusList=[{name:"待取材",value:10},{name:"待重补取",value:110}, {name:"待取材确认",value:11},];
      createMaterial.autoPrintList=[{name:"自动打印",value:true},{name:"手动打印",value:false}];

      createMaterial.autoPrint=false;//是否自动打印
      createMaterial.allChecked=false;//是否点击了全选

      IhcService.get("/paramSetting/grossingNote").then(function (result) {
        createMaterial.noteList = result;
      })

    }
    init();


    createMaterial.search=function (e) {
      var serach = {
        filter:createMaterial.searchData.filter,
        createTimeStart:new Date().setMonth(new Date().getMonth()-6),
        createTimeEnd:Date.now(),
        length:10,
        page:createMaterial.filterData.page,
      };

      if(createMaterial.searchData.filter){
        createMaterial.defaultTime=5;//有搜索内容是默认是搜索半年内 但只有显示效果没选中值
        createMaterial.filterData.createTimeStart=new Date().setMonth(new Date().getMonth()-6);//防止只有显示效果没选中值
      }else {
        createMaterial.defaultTime=1;//显示最近一天
        createMaterial.filterData.createTimeStart=new Date().setDate(new Date().getDate()-1);//防止只有显示效果没选中值
      }
      // console.log(createMaterial.searchData.filter) //
      if(e){
        var keycode = window.event?e.keyCode:e.which;
        if(keycode==13){
          if(createMaterial.searchData.filter){
            // console.log(serach) //
            if(createMaterial.searchData.filter){
              createMaterial.filterData.filter=angular.copy(createMaterial.searchData.filter)
            }
            createMaterial.defaultItem=0;//设置默认选择病理列表第一个
            getPathologicDataList(serach);
          }else {
            $state.go('app.createMaterial',{},{reload: true});// 当没有搜索内容时 显示默认筛选条件
          }
        }
      }else {
        if(createMaterial.searchData.filter){
          // console.log(serach) //
          if(createMaterial.searchData.filter){
            createMaterial.filterData.filter=angular.copy(createMaterial.searchData.filter)
          }
          createMaterial.defaultItem=0;//设置默认选择病理列表第一个
          getPathologicDataList(serach);
        }else {
          $state.go('app.createMaterial',{},{reload: true});// 当没有搜索内容时 显示默认筛选条件
        }
      }
    }//search 默认半年内的数据
    createMaterial.filter=function () {
      console.log('filter数据');
      console.log(createMaterial.filterData);
      createMaterial.filterData.filter=null;
      // console.log("searchData======筛选",createMaterial.filterData)
      createMaterial.defaultItem=0;
      getPathologicDataList(createMaterial.filterData);
    };// 筛选

    createMaterial.getDataList=function () {

      getPathologicDataList(createMaterial.filterData);

    };//获取病理列表


    createMaterial.getOne=function (item, index) {
      photoService.clearLocalPhoto();
      createMaterial.activeDdepartment=1;//每次设置默认选中模板

      createMaterial.imgList=[];//保存每次拍的新照片
      createMaterial.newSlider=true;

      // 获取当前的那个病理信息
      IhcService.get("/pathology/"+item.id).then(function (res) {
        var item = res;
        createMaterial.pathologicData = res;

        /*
        * 获取的病理信息 处理得到添加蜡块号是的冰冻号列表
        * createMaterial.frozenNumbers ： [{ number //原始冰冻号，frozenNumber: //截取后的冰冻号 打印冰冻号用}]     冰冻号下拉列表
        *
        * createMaterial.numbers : {number: frozenNumber} //存的一个 原始冰冻号 跟截取后的冰冻号的键值对  打印时获取 打印的冰冻号
        * */
        createMaterial.numbers = {};

        if(res.afterFrozen) {
          createMaterial.frozenNumbers = [];

          angular.forEach( res.frozenNumbers, function (item) {
            var frozenNumber = item.slice(0,1) + item.slice(-4); //截取冰冻号： 第一个字母 加上后四位  从后端返回的冰冻号数组里 选择一个冰冻号

            createMaterial.frozenNumbers.push(
              {
                number: item, //原始冰冻号
                frozenNumber: frozenNumber //截取后的冰冻号
              }
            );

            createMaterial.numbers[item] = frozenNumber; //通过冰冻号获取 截取后的冰冻号

          });

          createMaterial.frozenNumber = createMaterial.frozenNumbers[createMaterial.frozenNumbers.length-1].number //显示的是5位冰冻号 传到后端的是原始冰冻号 默认添加最后一个
        }

        if(!item.blocks){
          item.blocks=[];
        }

        if(index){
          createMaterial.defaultItem=index; //设置默认 选中哪一天数据 默认第一条
        }
        createMaterial.active=item.id; //设置点击active 选中高亮

        // createMaterial.pathologicData=angular.copy(item)||{};

        //createMaterial.pathologicData.media=[]; // //huyu 12-30  储存 视频  照片  一个展示 一个保存
        if(createMaterial.pathologicData.reGrossing || createMaterial.pathologicData.afterFrozen ){
          createMaterial.pathologicData.biaoshiDisabled=true;//用来控制当该病理为重补取时取材标识不可编辑
        }
        /*//指定取材医生 取消不能修改限制
        if(createMaterial.pathologicData.assignGrossing){
          createMaterial.pathologicData.grossingDoctor.id=createMaterial.pathologicData.assignGrossing;
          createMaterial.pathologicData.grossingDoctorDisabled=true;//用来控制当该病理已经指定取材医生后不可编辑
        }*/

        // createMaterial.pathologicData.media.newImg=[];
        // createMaterial.pathologicData.moveDistance=120; //储存图片slider 变量

        //huyu 12-30  取材页面图片数据
        videoService.showVideo(item.id,1).then(function (result) {
          createMaterial.pathologicData.media=result;
          createMaterial.photoShow = false;
          // console.error("获取拍照的图片信息",createMaterial.pathologicData.media);
        });

        if(createMaterial.pathologicData.departments){
          createMaterial.choseDdepartment(createMaterial.pathologicData.departments);
        }//获取该科室模板
      });



    };//点击表格获取病理详情
    createMaterial.view=function (serialNumber) {
      ApplicationService.getOneByPathologyNo(serialNumber).then(function (result) {
        $uibModal.open({
          templateUrl: 'app/site/modal/applicationModal.html',
          size:'lg',
          // backdrop: false,
          controller: 'ApplicationModalController',
          controllerAs: 'applicationMod',
          resolve: {
            modalTitle: function () {
              return "病理申请表";
            },
            ApplicationData:result[0]
          }
        })
      },function (error) {
        toastr.error(error)//通过病理号查申请单号是否存在
      })

    }; //查看申请单

    //获取系统设置的模板类型
    IhcService.get('/paramSetting/grossingTemplate').then(function(res){
      if(res==null){
        createMaterial.templateType = 0;
      }else{
        createMaterial.templateType = res;
      }
    });

    createMaterial.stopPropagation = function (e) {
      if (e.stopPropagation) e.stopPropagation();
      else e.cancelBubble = true;
    };
    createMaterial.choseDdepartment=function (parent) {
      createMaterial.activeDdepartment=parent;
      MaterialService.getTemplate(parent,1).then(function (data) {
        createMaterial.templateList=data;
      });
    };    //2.1 获取科室模板
    createMaterial.choseTemplate=function (item) {
      // console.warn(createMaterial.pathologicData)
      createMaterial.pathologicData.jujianNote=item.content;
      createMaterial.activeTemplate=item.id;
      MaterialService.setTemplateUsed(item.id,1).then(function () {
        MaterialService.getTemplateUsed(1).then(function (data) {
          createMaterial.recentTemplate=data;
        });//2.5 常用模板
      });
    };    //选模板
    createMaterial.choseDdepartment(1);



    createMaterial.chkAll=function () { //勾选
      var len= createMaterial.pathologicData.blocks.length;//初始化数据長度
      if(createMaterial.allChecked==true){
        for (var i=0;i<len;i++){
          // console.log('list-----------list',createMaterial.pathologicData.blocks[i])
          // createMaterial.pathologicData.blocks[i].checked=true;
          if(createMaterial.pathologicData.blocks[i].status&&createMaterial.pathologicData.blocks[i].status>11){
            //控制状态不是待取材确认的时候全选不选择 蜡块号状态不是待取材确认的
          }else {
            createMaterial.pathologicData.blocks[i].checked=true;
          }
        }

      }else{
        for (var i=0;i<len;i++){
          createMaterial.pathologicData.blocks[i].checked=false;
        }
      }
    };//全选
    createMaterial.add=function (n) { //添加

      if(n){//直接调用传参数  有参数说明是手动打印
        add(1) //添加一个
      }else {

        var biaoshiList = angular.copy(createMaterial.biaoshiList);//标识下拉
        var biaoshiDisabled;//用来控制当该病理为重补取时取材标识不可编辑
        var biaoshi = 1;//批量添加默认的标识

        if(createMaterial.pathologicData.reGrossing && createMaterial.pathologicData.afterFrozen){
          biaoshiDisabled = false;
          biaoshi=2;
          //当重补取跟冰冻同时存在时 只能选择 重补取跟冰冻标识 还是说都能选
          // biaoshiList =[{code: 2, name: "重补取"},{code: 5, name: "冻后"}]

        }else if(createMaterial.pathologicData.reGrossing){
          biaoshiDisabled = true;
          biaoshi=2;
        }else if(createMaterial.pathologicData.afterFrozen){  //冻后跟冰冻是同时出现

          biaoshi=5;
        }else if(createMaterial.pathologicData.inspectCategory === 3){
          // toastr.error("测试冰冻蜡块号修改");
          biaoshi=4;
        }

        var textInputModal = $uibModal.open({
          templateUrl: 'app/site/drawMaterial/modal/inputModal.html',
          controller: 'MaterialInputModalController',
          controllerAs: 'inputMod',
          size:'sm',
          // backdrop: false,
          resolve: {
            modal: {
              modalTitle: "请输入增加的个数：",
              biaoshiList:biaoshiList,
              biaoshi:biaoshi,
              biaoshiDisabled:biaoshiDisabled
            }
          }
        });
        textInputModal.result.then(function(res) {
          add(res.total, res.biaoshi)//从弹窗传参数  todo 再传一个默认标识
        })

      }

    };

    createMaterial.delete=function () {
      var noChecked=true;
      for (var i=0;i<createMaterial.pathologicData.blocks.length;i++){
        if(createMaterial.pathologicData.blocks[i].checked==true){
          noChecked=false;
        }
      };
      if(noChecked){
        toolService.getTipResult({
          modalTitle:"提示",
          modalContent:"请至少选择删除一条取材记录！",
          size:'sm'
        })
        return false;
      }//判断是否有选择要删除的。


      var modal = {
        modalTitle:"警告",
        modalContent:"确定要删除已选中的蜡块吗？",
        size:'sm',
      };
      toolService.getModalResult(modal).then(function (n) {
        for (var i=0;i<createMaterial.pathologicData.blocks.length;i++){
          // console.log('list-----------list',createMaterial.materialList)
          if(createMaterial.pathologicData.blocks[i].checked==true){
            if(createMaterial.pathologicData.blocks[i].status && createMaterial.pathologicData.blocks[i].status > 11 ){
              toastr.error("有蜡块已经保存不可删除。")
              return false
            }
            createMaterial.pathologicData.blocks.splice(i,1);
            i--;
          }//subId需要重新设置。

        }// 这里已完成删除

      })

    }; // 删除方法12-29更新

    //撤单
    createMaterial.cancel = function () {

      if(createMaterial.pathologicData.status !== 10) return;
      var id = createMaterial.pathologicData.id;

      var textareaMod = $uibModal.open({
        templateUrl: 'app/site/drawMaterial/modal/cancel.html',
        controller: 'CancelCreateMaterialModalController',
        controllerAs: 'textMod',
        // backdrop: false,
        resolve: {
          resolveData: {
            modalTitle: "撤销病理检查：",
            selectTitle: "是否撤销病理号 "+createMaterial.pathologicData.serialNumber +"？",
            warning:"撤销后将终止该病理号的制片流程。",
            textareaTtitle: "撤销原因：",
          }
        }
      });
      textareaMod.result.then(function(reason) {
        IhcService.put("/grossing/"+ id ,reason).then(function () {
          createMaterial.getDataList();
          toastr.success("撤单成功")
        })
      })
    };

    // 保存按钮
    createMaterial.save=function (){

      //判断是否有重复的蜡块号
      var exitSubId = isExistInObj(createMaterial.pathologicData.blocks,"subId")
      if(exitSubId){
        return toastr.error("蜡块号"+exitSubId+"重复")
      }

      if(!createMaterial.pathologicData.grossingDoctor.id){
        return toastr.error("请选择取材医生")
      }

      //判断蜡块号不能为空
      for(var i=0;i<createMaterial.pathologicData.blocks.length;i++){
        if(createMaterial.pathologicData.blocks[i]=="")
          return toastr.error("蜡块号不能为空")
      }
      //  保存拍照信息 createMaterial.pathologicData.media.images
      // console.info(createMaterial.pathologicData.media.newImg)
      // console.info("imgList",createMaterial.imgList)

      // if(createMaterial.imgList&&createMaterial.imgList.length>0){
      //   // console.log(createMaterial.photoList[0].imageList)
      //   for(var i=0;i<createMaterial.imgList.length;i++){
      //     var base64 = createMaterial.imgList[i].split(",")[1];
      //     // console.error("base64",base64)
      //     toolService.fileUpload(base64,1,createMaterial.pathologicData.id).then(function () {
      //
      //     })
      //   }
      // }

      var oldBlocksData=angular.copy(createMaterial.pathologicData.blocks); //【需要保存的蜡块】
      for(var i=0;i<oldBlocksData.length;i++){

        if(oldBlocksData[i].status&&oldBlocksData[i].status>11){
          oldBlocksData.splice(i,1); //删除状态不是待取材待取材确认的 【蜡块的状态是取材确认后的就不能删除了】
          i--;
        }else {
          oldBlocksData[i] = getCheckedBlock(oldBlocksData[i]);
        }
      }

      var saveData={
          jujianNote:createMaterial.pathologicData.jujianNote,
          bingdongNote:createMaterial.pathologicData.bingdongNote,
          operatorId:createMaterial.pathologicData.grossingDoctor.id,
          secOperatorId:$rootScope.user.id,
          blocks:oldBlocksData,
          print:false
        };

      // console.log("要保存的数据是----",saveData,createMaterial.basketNumber)
      MaterialService.saveMaterial(saveData,createMaterial.pathologicData.id).then(function () {
        toastr.success('保存成功！');

        if ($rootScope.basketNumber!=createMaterial.basketNumber){
          $cookieStore.put('basketNumber',createMaterial.basketNumber);
          $rootScope.basketNumber=createMaterial.basketNumber;
        }//保存脱水篮编号到cookie


        // $state.go('app.createMaterial',{},{reload: true});
        // createMaterial.pathologicData={blocks:[]};
        if(createMaterial.filterData.status!=10){
          createMaterial.defaultItem++;
          if(createMaterial.defaultItem===createMaterial.pathologicDataList.length){
            createMaterial.defaultItem--;
          }
        }
        getPathologicDataList(createMaterial.filterData);

      },function (err) {
        toastr.error("保存失败",err);
        // 保存失败是时材记录的旧数据被删掉了
        // createMaterial.pathologicData=oldPathologicData;
      });
    };

    function getCheckedBlock(item) {
      // 【过滤掉不需要保持的数据】
      return {
        "id":item.id, //#打印过有ID需传此项
        "print":item.print, //#打印次数
        "biaoshi": item.biaoshi,
        "subId": item.subId, //#玻片号
        "bodyPart": item.bodyPart,//"输尿管切缘",
        "count": item.count, //#组织数
        "unit": item.unit, //#块，堆
        "basketNumber": item.basketNumber, //#冰冻切片机ID
        "note": item.note, //#取材备注
        "number": item.number //#冰冻号
      }
    }

    // 打印按钮
    createMaterial.print= function () {// print操作 手动打印  还有个自动打印的 autoPrint

      var printBlocks = [];
      // 包埋盒打印内容：病理号+蜡块号+组织数+取材备注+二维码，二维码内容包含病理号+蜡块号
      var pathologyNumber=createMaterial.pathologicData.serialNumber;
      var printPathologyNumbers = []; //传给labwriter
      var printContents = [];//传给labwriter

      for(var i=0;i<createMaterial.pathologicData.blocks.length;i++){  //循环需要打印的列表
        var item = createMaterial.pathologicData.blocks[i];
        // console.info(item)
        if(item.checked){
          item.print += 1;
          // if(item.id){
          //   //后端记录打印次数需要id
          //   printIds.push(item.id)
          // }

          printBlocks.push(getCheckedBlock(item)); //【获取要打印的蜡块数组】
          printPathologyNumbers.push(pathologyNumber+'$'+item.subId); //新的打印传参数
          var content = [];
          content.push(item.bodyPart, "（"+item.count+"）", item.note);
          if(item.biaoshi === 5 && createMaterial.printFrozen == 1){ //设置是否打印冰冻号
            var frozenNumber = createMaterial.numbers[item.number];//打印到包埋盒上的截取后的冰冻号
            content.push(frozenNumber)// 冻后的才需要打印冰冻号
          }

          printContents.push(content.join(";"));// 新的打印传参数 types

        }
      }

      if(printPathologyNumbers.length){
        // todo 自动 打印功能
        printAndSave(printBlocks, function () {
          print(printPathologyNumbers.join(),printContents.join());// labwriter 打印蜡块包埋盒
        })
      }

      // // console.info("要打印的数据", printPathologyNumbers, printContents);
      // if(printPathologyNumbers.length){
      //   print(printPathologyNumbers.join(),printContents.join());// labwriter 打印蜡块包埋盒
      //   IhcService.post("/grossing/print/34",printIds) //给后端传打印记录
      // }

    };

    /*计算限制取材部位 bodypart 字符串的长度*/
    createMaterial.strLength=function (str,maxLen) {
      var w = 0;
      var tempCount = 0;
      //length 获取字数数，不区分汉子和英文
      for (var i=0; i<str.bodyPart.length; i++) {
        //charCodeAt()获取字符串中某一个字符的编码
        var c = str.bodyPart.charCodeAt(i);

        //判断当有 $ 的时候删掉
        var a = str.bodyPart.charAt(i);
        if(a=="$"){
          str.bodyPart = str.bodyPart.substr(0,i);
        }

        //单字节加1
        if ((c >= 0x0001 && c <= 0x007e) || (0xff60<=c && c<=0xff9f)) {
          w++;
        } else {
          w+=2;
        }
        if (w > maxLen) {
          str.bodyPart = str.bodyPart.substr(0,i);
          break;
        }
      }
    };
    /*限制 蜡块号输入 */
    createMaterial.subIdPattern=function (item,maxLen) {
      var str = item.subId;
      if(!str) return false;

      str=str.replace(/[^\w\.\/]/ig,'');
      // str=str.replace(/[^0-9a-zA-Z]+/,'');
      //小写转大写
      str = str.toUpperCase();
      item.subId = str;
      console.info(str)
      return str;

      //判断是否为英文
      if(!/^([A-Za-z]+\s?)*[A-Za-z]$/.test(str)){
        return str = str.substr(0, str.length - 1);
      }

      str=str.replace(/[^/w=@&]|_/ig,'');
      if(str == ''){
        return false;
      }
    };

    createMaterial.selectBiaoshi = function (material) {
      if(material.biaoshi == 5){
        blockItem.number = ""
      }
    };

    //1，获取病理信息部分
    getPathologicDataList(createMaterial.filterData);

    function getPathologicDataList(data) {
      
      if(!createMaterial.searchData.filter){
        delete createMaterial.searchData.filter;
      };


      if(createMaterial.status == 110){ //筛选重补取条件  默认时不传 选择其他状态时也不能传
        createMaterial.filterData.reGrossing = true;
        createMaterial.filterData.status = null;
      }else if(createMaterial.status){
        createMaterial.filterData.reGrossing = false;
        createMaterial.filterData.status = createMaterial.status;
      }else {
        delete createMaterial.filterData.reGrossing;
        createMaterial.filterData.status = createMaterial.status;
      }


      PathologyService.getPathologyList(data).then(function (result) {
        createMaterial.pathologicDataList=result.data;
        createMaterial.totalItems=result.total;
        createMaterial.searchData.filter="";//清空搜索框内容
        // console.log("获取样本列表数据：-----",result);
        if(result.total<1) {createMaterial.pathologicData={}; return;}

        var item=createMaterial.pathologicDataList[createMaterial.defaultItem];
        createMaterial.getOne(item);

      },function (err) {
        if(err.code===2){
          toastr.error(err.reason,"输入的病理号格式不正确")
        }else {
          toastr.error(err.reason)
        }
        // console.log("获病理信息err---",err);
      })
    }



    function add(n, biaoshi) {
      /*
      * n：用来循环添加的次数
      * */
      console.info(biaoshi);
      var lastBlockIndex; //用来判断在哪个位置插入新蜡块 对分类递增的蜡块号都插入到一起

      var material={
        applyId: "",//重补取的蜡块要传
        // isNewAdd: true, //自定义用来让最新的蜡块focus
        // checked:true,
        print:0,
        biaoshi: biaoshi || 1, //批量添加是传的标识
        subId:createMaterial.defaultNormalBlockId,  //分类递增的话 标识对应的初始蜡块号 是多少这个很重要
        bodyPart:"",
        count:1,
        unit:1,//#块，堆
        basketNumber:null,
        number:"", //当取材的是冻后 需要传冰冻号到后端
        note:""
      };//每次增加一个蜡块数据

      material.basketNumber=createMaterial.basketNumber;
      if(!createMaterial.basketNumber||!createMaterial.pathologicData.grossingDoctor.id){
        toolService.getTipResult({
          modalTitle:"提示",
          modalContent:"请输入脱水篮编号，选择取材医生。",
          size:'sm'
        });
        return;
      }//当数据为空时不能增加


      // 判断默认添加是那种标识
      if(!biaoshi){
        if(createMaterial.pathologicData.reGrossing && createMaterial.pathologicData.afterFrozen){
          createMaterial.pathologicData.biaoshiDisabled=true;//用来控制当该病理为重补取时取材标识不可编辑
          material.biaoshi=2;
        }else {
          if(createMaterial.pathologicData.reGrossing){
            createMaterial.pathologicData.biaoshiDisabled=true;//用来控制当该病理为重补取时取材标识不可编辑
            material.biaoshi=2;
          }

          if(createMaterial.pathologicData.afterFrozen && !biaoshi){
            material.biaoshi = 5;
          }
        }
      }

      if(material.biaoshi == 5){
        material.number = createMaterial.frozenNumber  //【自动添加默认冰冻号】
      }else if(material.biaoshi == 2){
        material.applyId=createMaterial.pathologicData.applyId;
      }else {
        material.number = ""  // note  要是手动把冻后的改成常规的标识 要删掉冰冻号 createMaterial.selectBiaoshi()
      }

      function autoSubId(blocks) {
        //传入添加的所有蜡块 根据蜡块的取材标识判断接下来生成的蜡块号应该是多少

        var str;//存计算后的蜡块号
        //接收整个蜡块对象的最后一个 批量添加时


        var frozenBlocks = [];
        var normalBlocks = [];

        var lastFrozenBlock; //最后那个蜡块
        var lastNormalBlock; //最后那个蜡块

        /*
        //自动计算 分类【未实现】 应该动态生成一个标识对应的 蜡块号 表  哪几种标识的蜡块初始号一样的  将蜡块分类
        var defaultBlockId, sameTypeBlocks = [], returnStr, biaoshiKeyValue = {
          "A": [4], //冰冻
          "A1": [1], //冰冻
          1: [2,3], //常规等 数字递增  是否要列出所有的下面计算出错？
        };*/
        //自动部分init  拓展

        if(createMaterial.defaultNormalBlockId !== createMaterial.defaultFrozenBlockId){ //当常规的跟冰冻的不是同一类递增方式
          angular.forEach(blocks,function (item,index) {
            item.isNewAdd = false; //删除自定义属性 1

            if(item.biaoshi === 5){ //找冻后的同类
              frozenBlocks.push(item);
              lastFrozenBlock = item;
              lastBlockIndex = index + 1; //插入位置 现在只管冰冻的蜡块
            }else { //将其它的蜡块都是其它 要是不分类递增
              normalBlocks.push(item);
              lastNormalBlock = item;
            }

            /*//自动计算找同类 分类  【未实现】
            for(var key in biaoshiKeyValue){

              if(biaoshiKeyValue[key].indexOf(material.biaoshi) >= 0){ //这些都是同一类递增方式的
                sameTypeBlocks.push(item); //得到跟要添加标识是同一类递增方式的所有蜡块

                if(sameTypeBlocks.length){
                  defaultBlockId = item.subId; //得到初始蜡块号
                }else {
                  returnStr = key; //得到初始蜡块号 分类了找不到同类
                }

              }else {//没有分类
                // //找不到分类的默认数字递增 跟着最后一个蜡块号递增
                if(blocks.length){
                  defaultBlockId = createMaterial.pathologicData.blocks[createMaterial.pathologicData.blocks.length-1].subId; //得到找不到分类的新蜡块初始蜡块号
                }else {
                   returnStr = 1; //找不到分类的默认数字递增  没分类找不到同类
                }

              }
            }*/

          });

          /*//自动计算找同类 分类  【未实现】
          if(returnStr){
            return returnStr
          }else{
            str = defaultBlockId
          }*/


          if(material.biaoshi === 5){ //冰冻的

            if(lastFrozenBlock){
              str = lastFrozenBlock.subId
            }else {
              str = createMaterial.defaultFrozenBlockId; //没找到同类直接返回 默认的编号 （A）
              return str
            }
          }else { //常规

            if(lastNormalBlock){
              str = lastNormalBlock.subId
            }else {
              str = createMaterial.defaultNormalBlockId; //这里认为常规的蜡块号默认1
              return str
            }
          }

        }else { //不开启分类递增的话 默认递增

          if(blocks.length){
            str = blocks[blocks.length-1].subId;//获取最后一个蜡块号
          }else{
            return str = createMaterial.defaultNormalBlockId;
          }
        }




        //纯数字就自动加1    var reg = new RegExp("^[0-9]*$");
        var reg1= /^[0-9]*$/;

        //纯大写字母 判断纯大写字母
        var regA= /^[A-Z]+$/;

        // 字母加数字  1从最后一个字符往前判断 2截断 3前面的固定 4后面的数字递增    // var regA1=/^(([a-z]+[0-9]+)|([0-9]+[a-z]+))[a-z0-9]*$/i; //匹配数字字母混合的字符串
        var regA1 = /^[A-Z]+[0-9]+$/; //匹配大写字母开始数字结束
        // var endN = /\d+$/.exec("12abcde999"); //获取后面的数字   //var start = new RegExp(/^\d+/); 用来获取开始的数字
        // var startL = /[A-Z]+/.exec('SJA535'); //截取开始的字母
        // 匹配数字字母混合的字符串 数字开始字母结束
        var reg1A = /^[0-9]+[A-Z]+$/; //匹配数字开始字母结束

        if(reg1.test(str)){ //纯数字
          str = parseInt(str)+1;
          // console.info("纯数字",str)
          return str;
        }else if( regA.test(str) ){ //纯大写字母
          // 递增 convertToNumberingScheme(number)  fromLettersToNumber(str)
          var number = fromLettersToNumber(str);
          str = convertToNumberingScheme(parseInt(number)+1);//递增

          return str;
        }else if( regA1.test(str) ){ //大写字母开始加数字结束
          // console.info("字母加数字")
          var startL = /[A-Z]+/.exec(str); //截取开始的字母
          var endN = /\d+$/.exec(str); //获取后面的数字
          str = startL+(parseInt(endN)+1)

          // console.info("字母加数字",str)
          return str;
        }else if(reg1A.test(str)){
          var startN = /\d+/.exec(str); //截取开始的数字
          var endL = /[A-Z]+$/.exec(str); //获取后面的字母

          var number1A = fromLettersToNumber(endL[0]); //先把字母转成数字 然后做递增后转成字母
          number1A = convertToNumberingScheme(parseInt(number1A)+1);//递增
          str = startN+number1A;

          // console.info("数字-字母",str)
          return str;
        }else {
          return ""; //todo 特殊符号？？   var regA= /^[A-Za-z]+$/; 英文大小写？
        }

      }

      var printBlocks = [];// 存放新增的要打印的数据
      var printPathologyNumbers = []; //传给labwriter
      var printContents = [];//传给labwriter


      // 判断一次性要增加多少条记录
      for (var i=0;i<n;i++){
        material.subId=autoSubId(createMaterial.pathologicData.blocks); //自动增加 蜡块号 subId
        var newMaterial = angular.copy(material);

        // // 每次增加一个打印一个  还有个手动打印
        if(createMaterial.autoPrint){

          printPathologyNumbers.push(createMaterial.pathologicData.serialNumber+'$'+material.subId); //新的打印传参数
          var content = [];
          content.push(material.bodyPart, "（"+material.count+"）", material.note);
          if(material.biaoshi === 5){

            var frozenNumber = createMaterial.numbers[material.number];//打印到包埋盒上的截取后的冰冻号
            content.push(frozenNumber)// 冻后的才需要打印冰冻号
          }

          printContents.push(content.join(";"));// 新的打印传参数 types

        }

        if(createMaterial.autoPrint){
          newMaterial.print = 1; //显示打印次数
        }
        newMaterial.checked = true; //这个字段不能传到后端 是前端用来标识是否选中用
        newMaterial.isNewAdd = true; //这个字段不能传到后端 是前端用来标识是否选中用
        createMaterial.pathologicData.blocks.push(angular.copy(newMaterial));
        printBlocks.push(angular.copy(material)) //打印用

        /*// 当是冰冻的时候 把冰冻的蜡块都放到一起 显示连续
        if(material.biaoshi === 5){
          createMaterial.pathologicData.blocks.splice(lastBlockIndex || createMaterial.pathologicData.blocks.length, 0, angular.copy(material));
          lastBlockIndex ++
        }else {
          createMaterial.pathologicData.blocks.push(angular.copy(material));
        }*/

      }

      if(createMaterial.autoPrint && printPathologyNumbers.length){
        // 自动 打印功能
        printAndSave(printBlocks, function () {
          print(printPathologyNumbers.join(),printContents.join());// labwriter 打印蜡块包埋盒
        })
      }


    }


    function printAndSave(blocks, cb) {

      // 调后端的保存打印接口 记录打印次数
      var saveData={
        print:true, //#是否是打印操作 true-是 false-否
        blocks:blocks
      };
      // console.log("要保存的数据是----",saveData);
      MaterialService.saveMaterial(saveData, createMaterial.pathologicData.id).then(function () {

        toastr.success('打印成功！');

        cb && cb()

      },function (err) {
        toastr.error("打印失败",err.reason);
      });

    }
    function print(id,type) {
      /*
      * labwriter 打印到包埋盒上
      * */
      printerService.labWrite(id,type).then(function () {

      },function (err) {
        toastr.error("打印失败");
      })
    }

    function convertToNumberingScheme(number) { //数字转字母function  convertToNumberingScheme(number)  fromLettersToNumber(str)
      var baseChar = ("A").charCodeAt(0),
        letters  = "";
      do {
        number -= 1;
        letters = String.fromCharCode(baseChar + (number % 26)) + letters;
        number = (number / 26) >> 0;
      } while(number > 0);
      return letters;
    } // 数字 转 大写字母
    function fromLettersToNumber(str) {
      "use strict";
      var out = 0, len = str.length, pos = len;
      while (--pos > -1) {
        out += (str.charCodeAt(pos) - 64) * Math.pow(26, len - 1 - pos);
      }
      return out;
    } // 大写字母 转 数字

    //根据数组中对象的属性判断是否重复
    function isExistInObj(arr,key) {
      // console.log("根据数组中对象的属性判断是否重复")
      var hash = {},len = arr.length;
      while(len){
        len--;
        if(hash[arr[len][key]]){
          // console.log(hash)
          return arr[len][key];
        }else{
          hash[arr[len][key]] = arr[len][key];
        }
      }
      //console.log(hash)
      return false;
    }

    function createPathNoArrayByPrintTool(start,end) {
      var pathNoArr = [];

      if(start){
        var startSign = start.substring(0,1); //截取病理标识
        var startNo = parseInt(start.substring(1,9)); //截取数字部分
        pathNoArr.push(start);//拼接开始的病理号
        if(!end){
          createMaterial.startNo = startSign + (startNo+1);
          return pathNoArr
        }
      }

      if(end){
        var endSign = end.substring(0,1); //截取病理标识
        var endNo = parseInt(end.substring(1,9)); //截取数字部分
        pathNoArr.push(end);//拼接结束的病理号
        if(!start){
          createMaterial.endNo = endSign + (endNo+1); // 如果输入一个病理号 病理号自动加1
          return pathNoArr
        }
      }

      if(start && end){

        while ( startNo+1 < endNo ){

          var serialNumber = startSign + (startNo + 1);
          pathNoArr.push(serialNumber);
          startNo ++;

        }
        return pathNoArr; //返回病理数组
      }

    }
    function createBlocksByPrintTool(total) {

      var blocks = [];
      var blockNoStart = 1;
      while (total){
        if(countByLetters){
          // 通过字母递增生成蜡块号
          blocks.push(convertToNumberingScheme(blockNoStart));
        }else{
          // 通过数字递增生成蜡块号
          blocks.push(blockNoStart);
        }

        blockNoStart++;
        total --;
      }
      return blocks
    }



    //todo 打印工具 功能未完成  最新的南京口腔医院需求病理号是没有字母标识的
    createMaterial.printTool = function () {
      //应该先判断数据是否合法

      if(createMaterial.startNo){
        var start = createMaterial.startNo;
        var startSign = start.substring(0,1); //截取病理标识
        var startNo = parseInt(start.substring(1,9)); //截取数字部分
      }
      if(createMaterial.endNo){
        var end = createMaterial.endNo;
        var endSign = end.substring(0,1); //截取病理标识
        var endNo = parseInt(end.substring(1,9)); //截取数字部分
      }


      if(start && end){
        if(start === end){
          toastr.error("不要输入的相同病理号");
          return
        }

        if(startSign !== endSign){
          toastr.error("请输入同一个类型的病理号");
          return
        }
        if(startNo > endNo){
          toastr.error("输入的结束病理号太小");
          return
        }
      }


      if(!(start || end)){
        toastr.error("请输入病理号");
        return
      }

      if(!createMaterial.totalNo){
        toastr.error("请输入蜡块数");
        return
      }

      //生成后台的数据格式
      var printData = [];
      var pathNoArr = createPathNoArrayByPrintTool(start, end);
      var blocks = createBlocksByPrintTool(createMaterial.totalNo);

      angular.forEach(pathNoArr,function (item) {
        printData.push({
          pathNo:item,
          blockSubIds:blocks
        })
      });

      var data = {
        printData: printData,
        handle:false //默认false
      };
      IhcService.post("/grossing/before/print",data).then(function (res) {
        //判断各种情况  后端是否要处理不存在的情况？
        if(res.inExistencePathNos.length){
          //不存在的病理号

        }else if(res.hadBlocksPathNos.length){
          //已经有蜡块了

        }else if(res.printInfo.length){
          //重新获取数据

          //1，打印  后台生成成功了再打印出来 2，记录打印次数

        }
      })
    };



 /*   $rootScope.$on('$stateChangeStart',function (event, toState, toParams, fromState) {
      // console.error("会自动保存的")
      createMaterial.save()
    })*/

  }
})();

