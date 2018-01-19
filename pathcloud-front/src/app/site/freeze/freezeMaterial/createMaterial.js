/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('FreezeMaterialController', FreezeMaterialController);

  /** @ngInject */
  function FreezeMaterialController(apiUrl,$scope,$state,$rootScope,$cookieStore,toolService,IhcService,$uibModal,ApplicationService,PathologyService,toastr,MaterialService,printerService,videoService,$timeout,photoService,$interval) {
    var freeze = this;


    var countByLetters=false;//是否用字母方式计数
    var materialId=0;// TODO 蜡块初始编号
    var rootSlideId;//初始玻片号
    freeze.pathologicDataList = []; //待取材列表

    freeze.imgUrlHeader = apiUrl.substr(0,apiUrl.length-3);

    //视频相关
    function initVideo() {
      var option={
        gumVideo:"#gumVideo"
      };
      videoService.init(option)
    }



    //系统设置 模板类型
    IhcService.get('/paramSetting/grossingTemplate').then(function(res){
      if(res==null){
        freeze.templateType = 0;
      }else{
        freeze.templateType = res;
      }
    });
    //点击其他地方收起模板选择
    freeze.stopPropagation = function (e) {
      if (e.stopPropagation) e.stopPropagation();
      else e.cancelBubble = true;
    };
    //点击其他地方收起模板选择
    function stopPropagation(e) {
      // console.error("点击外部？")
      if (e.stopPropagation)
        e.stopPropagation();
      else
        e.cancelBubble = true;
    }

    freeze.closeTemplate=function () {
      if(freeze.open){
        freeze.open=false;
      }
    };

    $('#template').bind('click',function(e){
      // console.error("点击内部？")
      stopPropagation(e);
    });

    freeze.changeTemplateStatus=function (e) {
      freeze.open = !freeze.open;
      stopPropagation(e);
    };


    //todo  huyu 12-30  拍照修改
    // var c = a.concat(b);
    freeze.imgList = [];
    freeze.perItemNum = 3; //每页图片数
    // freeze.photo=function () {
    //   if(!freeze.pathologicData) return;
    //
    //   var imgUrl=videoService.photo();
    //   var base64 = imgUrl.split(",")[1];
    //   toolService.fileUpload(base64,1,freeze.pathologicData.id).then(function (res) {
    //     res[0].url = imgUrl;
    //     // console.log(res[0])
    //     freeze.pathologicData.media.push(res[0]);
    //
    //   })
    // };
    // freeze.showBigImg=function (imgUrl,imgIndex) {
    //   var modalInstance = $uibModal.open({
    //     templateUrl: 'app/site/drawMaterial/modal/showImg.html',
    //     controller: 'ShowImgController',
    //     size: 'lg',
    //     resolve: {
    //       imgUrl: function () {
    //         return imgUrl;
    //       }
    //     }
    //   });
    //
    //   modalInstance.result.then(function (delFlag) {
    //     if(delFlag){
    //       var delImg=freeze.pathologicData.media.splice(imgIndex,1)[0];
    //       if(delImg.id){
    //         toolService.fileDelete(delImg.id)
    //       }
    //       // console.log(delImg);
    //       // var delImg=freeze.pathologicData.media.images.splice(imgIndex,1);
    //     }
    //   })
    // };
    // $timeout(function () {
    //   // ng-style="freeze.imgStyle"
    //   freeze.containerWidth = $('#img-container').width();
    //   freeze.imgStyle={width:freeze.containerWidth/freeze.perItemNum+'px',height:' 60px',float:'left'};
    // });
    // initVideo();
    //
    // freeze.enlarge = function () {
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
    //         return freeze.pathologicData.id;
    //       }
    //     }
    //   });
    //
    //   modalInstance.result.then(function () {
    //     videoService.showVideo(freeze.pathologicData.id).then(function (res) {
    //       freeze.pathologicData.media=res;
    //
    //       // console.error("获取拍照的图片信息",freeze.pathologicData.media);
    //     });
    //   })
    // };

    //2017-12-13  图像采集
    function insertPhoto() {
      photoService.getLocalPhotoUrlList().then(function (res) {
        if(!freeze.pathologicData||!freeze.pathologicData.media) return;
        // console.info("抓取到的本地的照片",freeze.pathologicData.media);
        angular.forEach(res, function (item) {
          if(freeze.pathologicData.media.length >= 10){
            toastr.error("最多只能保存10张照片,请删掉一些照片后再重拍");
          }else {
            freeze.pathologicData.media.push({
              url: item
            });
            //  上传本地图片到服务端
            photoService.getBase64(item).then(function (Base64) {
              // debugger
              if (!freeze.pathologicData) return;
              toolService.fileUpload(Base64, 35, freeze.pathologicData.id,freeze.number).then(function (res) {
                console.log('图片上传成功',freeze.pathologicData.media);
                var length =  freeze.pathologicData.media.length;
                freeze.pathologicData.media[length-1].id = res[0].id;
              });
            });
          }
        });
        //console.info(222, createMaterial.pathologicData.media)
        // debugger
      });
    }

     //定时
    var  insertPhotoInterval = $interval(function () {
      insertPhoto();
    }, 3000);//3秒刷新一次重新获取照片;
    $scope.$on('$destroy', function () {
      $interval.cancel(insertPhotoInterval);
      photoService.clearLocalPhoto();
    });

    freeze.selectPhotoResult ={};
    //选择图片
    freeze.selectPhoto = function(url,index,id){
      freeze.selectPhotoResult.url = url;
      freeze.selectPhotoResult.index = index;
      freeze.selectPhotoResult.id = id;
      freeze.photoShow = true;
    };

    //删除图片
    freeze.deletePhoto = function(imgIndex,id){
      if(id&&freeze.photoShow){
        var delImg = freeze.pathologicData.media.splice(imgIndex,1)[0];
        toolService.fileDelete(delImg.id);
        freeze.photoShow = false;
      }else{
        toastr.error('图片暂时不能删除，请稍候');
      }
    };

    //放大图片
    freeze.showBigImg=function (imgUrl) {
        photoService.enlargePhoto(imgUrl);
    };


    $timeout(function () {
      // ng-style="createMaterial.imgStyle"
      freeze.containerWidth = $('#img-container').width();
      freeze.imgStyle={width:freeze.containerWidth/freeze.perItemNum+'px',height:' 60px',float:'left'};
    });

    //todo 12-30 huyu  之前数据都是保存在全局 没按每个病理数据存  每条数据应在 getOne() 时保存到 freeze.pathologicData 中
    function init(){

      var instrumentFilter = {
        page:1,
        length:100,
        status:0, //#0正常 1禁用 2报修
        type:3 //1-染色机 2-封片机 3-冰冻切片机
      };

      IhcService.get("/instrument",instrumentFilter).then(function (res) {
        freeze.instruments = res.data;
      });

      //2, 科室模板，最近常用模板
      MaterialService.getGrossingUser().then(function (data) {
        freeze.grossingUserLIst=data;
      }); //1.2 获取取材医生列表
      MaterialService.getDepartments().then(function (data) {
        freeze.departmentList=data;
      });//1.3 获取送检科室列表
      MaterialService.getTemplateUsed(1).then(function (data) {
        freeze.recentTemplate=data;
      });//2.5 常用模板

      MaterialService.getBiaoshi().then(function (data) {
        freeze.biaoshiList=data;
      });// 4.1 获取取材标识


      MaterialService.getBlockUnit().then(function (data) {
        freeze.blockUnitList=data;
      });// 4.3 获取组织数单位
      // freeze.pathologicData={blocks:[]};
      freeze.defaultTime=1;//设置默认筛选时
      freeze.defaultItem=0;//设置默认选择病理列表第一个
      freeze.activeDdepartment=1;//设置默认选中模板
      freeze.basketNumber=$rootScope.basketNumber;
      freeze.searchData={};
      freeze.filterData={
        createTimeStart:new Date().setDate(new Date().getDate()-1),
        sort:null,
        order:null,
        page:1,
        length:17
      };

      freeze.tableHeaders=[{name:"编号",class:"text-center"}, {name:"姓名",class:"text-center"},{name:"状态",class:"text-center"}];
      freeze.sampleStatusList=[{name:"待取材",value:10}];
      freeze.autoPrintList=[{name:"自动打印",value:true},{name:"手动打印",value:false}];

      freeze.autoPrint=false;//是否自动打印
      freeze.allChecked=false;//是否点击了全选
      // todo 可下拉备注
      IhcService.get("/paramSetting/grossingNote").then(function (result) {
        freeze.noteList = result;
      })
      // freeze.noteList = ["备注1","备注2","备注3","备注4"] //

    }
    init();


    freeze.search=function (e) {

      freeze.searchData = {
        filter: freeze.searchStr,
        page:1
      };

      if(freeze.searchData.filter){
        freeze.defaultTime=5;//有搜索内容是默认是搜索半年内 但只有显示效果没选中值
      }else {
        freeze.defaultTime=1;//显示最近一天
        delete freeze.searchData.filter;
      }

      freeze.defaultItem=0;
      freeze.getDataList();
    };

    freeze.query=function () {

      delete freeze.searchData.filter;
      freeze.searchData.page = 1;
      freeze.defaultItem=0;
      freeze.getDataList();
    };// 筛选

    freeze.getDataList=function () {

      freeze.searchData.inspectCategory = 3; //区分冰冻取材
      freeze.searchData.length = 17;

      $timeout(function () {
        freeze.searchData.createTimeStart = freeze.startTime;
        freeze.searchData.createTimeEnd = freeze.endTime;
        IhcService.get("/frozen/prepare",freeze.searchData).then(function (result) {

          freeze.pathologicDataList=result.data;
          freeze.totalItems=result.total;
          if(result.total<1) {freeze.pathologicData={}; return;}

          var item=freeze.pathologicDataList[freeze.defaultItem];
          freeze.getOne(item);

        },function (err) {
          if(err.code===2){
            toastr.error(err.reason,"输入的病理号格式不正确")
          }else {
            toastr.error(err.reason)
          }
          // console.log("获病理信息err---",err);
        })
      },0)


    };//获取病理列表
    freeze.getDataList();

    function getFrozenSlides(number) {
      //获取取材记录 即冰冻的玻片信息 接口
      IhcService.get("/frozen/"+number).then(function (blocks) {
        freeze.pathologicData.slides = blocks || []; //取材记录
      });
    }

    freeze.getOne=function (item,index) {
      photoService.clearLocalPhoto();
      if(!item) return;

      freeze.deleteBlocks = []; //在保存的时候 传给后端哪些需要从数据库删掉 有id 的说明已经存到数据库了
      freeze.activeDdepartment=1;//每次设置默认选中模板
      freeze.number = item.number; //冰冻号
      freeze.active=item.id; //设置点击active 选中高亮
      freeze.status = item.status;

      materialId=0;// 玻片初始编号
      freeze.imgList=[];//保存每次拍的新照片
      freeze.newSlider=true;


      if(index){
        freeze.defaultItem=index; //设置默认选中 默认第一条
      }
      // 获取当前的那个病理信息
      IhcService.get("/pathology/"+item.causeId).then(function (res) {

        freeze.pathologicData = res; // *** 用来存储该病理号对应的所有信息

        getFrozenSlides(item.number); //获取该病理的取材记录信息

        // console.log(freeze.pathologicData)
       //freeze.pathologicData.media=[]; // //huyu 12-30  储存 视频  照片  一个展示 一个保存
        if(freeze.pathologicData.reGrossing){
          freeze.pathologicData.biaoshiDisabled=true;//用来控制当该病理为重补取时取材标识不可编辑
        }

        videoService.showVideo(res.id,35,{tag:freeze.number}).then(function (res) {
          freeze.photoShow = false;
          freeze.pathologicData.media=res;
          //console.error("获取获取拍照的图片信息",freeze.pathologicData.media);
        });

        if(freeze.pathologicData.departments){
          freeze.choseDdepartment(freeze.pathologicData.departments);
        }//获取该科室模板

      });

    };//点击表格获取病理详情

    freeze.view=function (serialNumber) {
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

    freeze.choseDdepartment=function (parent) {
      freeze.activeDdepartment=parent;
      MaterialService.getTemplate(parent,1).then(function (data) {
        freeze.templateList=data;
      });
    };    //2.1 获取科室模板
    freeze.choseTemplate=function (item) {
      // console.warn(freeze.pathologicData)
      freeze.pathologicData.bingdongNote=item.content;
      freeze.activeTemplate=item.id;
      MaterialService.setTemplateUsed(item.id,1).then(function () {
        MaterialService.getTemplateUsed(1).then(function (data) {
          freeze.recentTemplate=data;
        });//2.5 常用模板
      });
    };    //选模板
    freeze.choseDdepartment(1);



    freeze.chkAll=function () { //勾选
      var len= freeze.pathologicData.slides.length;//初始化数据長度
      if(freeze.allChecked === true){
        for (var i=0;i<len;i++){
          freeze.pathologicData.slides[i].checked=true;
        }

      }else{
        for (var i=0;i<len;i++){
          freeze.pathologicData.slides[i].checked=false;
        }
      }
    };//全选
    freeze.add=function (n) { //添加
      var modal = {
        modalTitle:"请输入增加的个数",
        size:'sm',
      };
      if(n){//直接调用传参数
        add(n)
      }else {
        toolService.getInputModal(modal).then(function (n) {
          // console.log(n)
          add(n)//从弹窗传参数
        })
      }

    };
    freeze.delete=function () {

      var noChecked=true;
      for (var i=0;i<freeze.pathologicData.slides.length;i++){
        if(freeze.pathologicData.slides[i].checked==true){
          noChecked=false;
        }
      }
      if(noChecked){
        toastr.error("请至少选择删除一条取材记录！");
        return false;
      }//判断是否有选择要删除的。


      var modal = {
        modalTitle:"警告",
        modalContent:"确定要删除已选中的蜡块吗？",
        size:'sm'
      };
      toolService.getModalResult(modal).then(function (n) {
        for (var i=0;i<freeze.pathologicData.slides.length;i++){

          if(freeze.pathologicData.slides[i].checked === true){
            if(freeze.pathologicData.slides[i].id){
              freeze.deleteBlocks.push(freeze.pathologicData.slides[i].id) //保存的时候
            }
            freeze.pathologicData.slides.splice(i,1);
            i--;
          }

        }// 这里已完成删除
      })


    }; // 删除方法12-29更新

    // 保存按钮
    freeze.save=function (){

      if(!freeze.basketNumber||!freeze.pathologicData.grossingDoctor.id){
        // toastr.error("请选择取材医生跟冰冻切片机")
        toolService.getTipResult({
          modalTitle:"提示",
          modalContent:"请选择冰冻切片机，选择取材医生。",
          size:'sm'
        });
        return;
      }//当数据为空时不能增加
      //  保存拍照信息 freeze.pathologicData.media.images
      // console.info(freeze.pathologicData.media.newImg)
      // console.info("imgList",freeze.imgList)

      // if(freeze.imgList&&freeze.imgList.length>0){
      //   // console.log(freeze.photoList[0].imageList)
      //   for(var i=0;i<freeze.imgList.length;i++){
      //     var base64 = freeze.imgList[i].split(",")[1];
      //     // console.error("base64",base64)
      //     toolService.fileUpload(base64,1,freeze.pathologicData.id).then(function () {
      //
      //     })
      //   }
      // }

      var saveData={
          print:false, //#是否是打印操作 true-是 false-否
          number:freeze.number, //#是否是打印操作 true-是 false-否
          bingdongNote:freeze.pathologicData.bingdongNote,
          operatorId:freeze.pathologicData.grossingDoctor.id,
          secOperatorId:$rootScope.user.id,
          blocks:[],
          deleteBlocks: freeze.deleteBlocks
        };
      angular.forEach(freeze.pathologicData.slides,function (item) {
        saveData.blocks.push(getCheckedSlide(item));
      });

      // console.log("要保存的数据是----",saveData);
      IhcService.post("/frozen/"+freeze.pathologicData.id,saveData).then(function () {

        toastr.success('保存成功！');
        freeze.getDataList()

      },function (err) {
        toastr.error("保存失败",err);
      });
    };
    // 打印按钮
    freeze.print= function () {// print操作 手动打印

      var printIds = [];
      var printSlides = [];
      // 包埋盒打印内容：病理号+蜡块号+组织数+取材备注+二维码，二维码内容包含病理号+蜡块号
      var pathologyNumber=freeze.pathologicData.serialNumber;

      var printPathologyNumbers = [];
      var printContents = [];

      for(var i=0;i<freeze.pathologicData.slides.length;i++){  //循环需要打印的列表
        var item = freeze.pathologicData.slides[i];
        // console.info(item)
        if(item.checked){

          //后端记录打印次数
          printSlides.push(getCheckedSlide(item));

          printPathologyNumbers.push(pathologyNumber+'$'+'0'+'$'+item.subId); //前端打印传参数1， id

          var content = [];
          content.push(item.bodyPart, "（"+item.count+"）", item.note, freeze.number);
          printContents.push(content.join(";"));// 前端打印传参数2 types

        }
      }


      // console.info("要打印的数据", printPathologyNumbers, printContents);
      //  后端保存打印记录也是 调的保存的接口 只不过可以只传 blocks， print
      if(printPathologyNumbers.length){
        // 手动冰冻取材打印 并保存到后端
        printAndSave(printSlides, function () {
          print(printPathologyNumbers.join(),printContents.join());  //labwriter 打印玻片号
        });

      }
    };


    /*计算限制取材部位 bodypart 字符串的长度*/
    freeze.strLength=function (str,maxLen) {
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
        };

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

    function add(n) {
      //定义初始的玻片号
      if( freeze.pathologicData.slides && freeze.pathologicData.slides.length === 0 ){
        rootSlideId = 1;
      }else {
        rootSlideId = parseInt(freeze.pathologicData.slides[freeze.pathologicData.slides.length-1].subId) + 1; //用来计算 新增的玻片编号
      }

      if(!freeze.basketNumber||!freeze.pathologicData.grossingDoctor.id){
        toolService.getTipResult({
          modalTitle:"提示",
          modalContent:"请选择冰冻切片机，选择取材医生。",
          size:'sm'
        });
        return;
      }//当数据为空时不能增加


      var newSlides = [];// 存放新增的要打印的数据
      var printPathologyNumbers = []; //传给labwriter
      var printContents = [];//传给labwriter

      // 判断一次性要增加多少条记录
      for (var i=0;i<n;i++){

        var material={
          print:0,
          biaoshi:4,
          subId:rootSlideId,
          bodyPart:"",
          count:1,
          unit:1,//#块，堆
          basketNumber:freeze.basketNumber,
          note:""
        };

        var newMaterial = angular.copy(material);
        // 每次增加一个打印一个  还有个手动打印
        if(freeze.autoPrint){

          // var printStr=freeze.pathologicData.serialNumber+'$'+material.subId;  //病理号分割符 用 $ 隔开自动打印时没有 material.bodyPart
          // var printType=material.bodyPart+";"+"("+material.count+"）"+";"+material.note; //自动打印

          newSlides.push(material);

          printPathologyNumbers.push(freeze.pathologicData.serialNumber+'$'+'0'+'$'+material.subId); // labwriter打印传参数1 id

          var content = [];
          content.push(material.bodyPart, "（"+material.count+"）", material.note, freeze.number);
          printContents.push(content.join(";"));// labwriter打印传参数 2 types

        }else {
          newMaterial.checked = true; //这个字段不能传到后端 是前端用来标识是否选中用
        }

        freeze.pathologicData.slides.push(newMaterial);
        rootSlideId++;
        // console.log("freeze.pathologicData.slides=========",freeze.pathologicData.slides);
      }

      //批量打印 自动打印
      if(freeze.autoPrint){
        printAndSave(newSlides,function () {
          // 后端打印保存成功后会记录打印次数  请求成功后在前端发请求到libwriter 打印机打印
          print(printPathologyNumbers.join(),printContents.join());// labwriter 打印蜡块包埋盒
        })
      }


    }

    function getCheckedSlide(item) {
      return {
        "id":item.id, //#打印过有ID需传此项
        "print":item.print, //#打印次数
        "biaoshi": item.biaoshi,
        "subId": item.subId, //#玻片号
        "bodyPart": item.bodyPart,//"输尿管切缘",
        "count": item.count, //#组织数
        "unit": item.unit, //#块，堆
        "basketNumber": item.basketNumber, //#冰冻切片机ID
        "note": item.note //#取材备注
      }
    }
    function printAndSave(printSlides, cb) {
      // 调后端的保存打印接口 记录打印次数
      var saveData={
        print:true, //#是否是打印操作 true-是 false-否
        number:freeze.number, //# 冰冻号
        blocks:printSlides
      };

      // console.log("要保存的数据是----",saveData);
      IhcService.post("/frozen/"+freeze.pathologicData.id,saveData).then(function () {

        toastr.success('保存成功！');
        getFrozenSlides(freeze.number);

        cb && cb()

      },function (err) {
        toastr.error("保存失败",err.reason);
      });
    }
    function print(id,type) {
      printerService.labWrite(id,type).then(function () {

      },function (err) {
        toastr.error(err);
      })
    } //蜡块编号打印

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


  }
})();


