/**
 * Created by Administrator on 2016/12/5.
 */
(function () {
  'use strict';

  angular
  .module('pathcloud')
  .controller('createDiagnosisController', createDiagnosisController);

  /** @ngInject */
  function createDiagnosisController($rootScope, $timeout, $state, toastr, $uibModal, ApplicationService, DiagnosisService, $interval, photoService, MaterialService, QueryService, toolService, IhcService, $filter, $scope) {
    var createDiagnosis = this;
    createDiagnosis.delayTime = new Date().setHours(0, 0, 0, 0) + 86399999;
    createDiagnosis.specialUnitList = ["+", "++", "+++", "-", "--", "---", "α", "β", "γ"];//初始化小星星数组 1:空星 2:整星 3:半星
    createDiagnosis.photoTags = [{code:'2',value:"10x100 HE"},{code:'1',value:"10x40 HE"},{code:'3',value:"10x20 HE"},{code:'4',value:"10x10 HE"},{code:'5',value:"10x4 HE"},
      {code:'6',value:"10x100 IHC"},{code:'7',value:"10x40 IHC"},{code:'8',value:"10x20 IHC"},{code:'9',value:"10x10 IHC"},{code:'10',value:"10x4 IHC"},
      {code:'11',value:"10x100 冰冻"},{code:'12',value:"10x40 冰冻"},{code:'13',value:"10x20 冰冻"},{code:'14',value:"10x10 冰冻"},{code:'15',value:"10x4 冰冻"}
    ];//初始诊断图像采集图片标签tag【后端只保存 code1 的value，其它的都只是前端定义的】
    createDiagnosis.pathologicalData = {};//存储根据病理号或玻片号获取的数据
    createDiagnosis.operateNote = "";//存放重补取或深切备注
    createDiagnosis.percentage = 0;//计算已看玻片百分比
    createDiagnosis.iconList = [1, 1, 1, 1, 1];//初始化小星星数组 1:空星 2:整星 3:半星
    createDiagnosis.openFlag = false;//控制模板打开状态
    // createDiagnosis.imageList = [];//存储图片列表
    createDiagnosis.departmentsList = $rootScope.departments;//储存科室列表
    createDiagnosis.specialTypeList = [{code: 0, name: "常规"}, {code: 1, name: "冰冻"}, {code: 2, name: "免疫组化"}, {
      code: 3,
      name: "特染"
    }, {code: 4, name: "会诊"}];
    createDiagnosis.templatesList = [];//存储模板内容列表
    createDiagnosis.perItemNum = 3; //每页图片数
    createDiagnosis.newSlider = true;
    createDiagnosis.recentTemplate = [];// 最近常用模板
    createDiagnosis.imgUrlHeader = toolService.getImgHeader();
    createDiagnosis.diagnosisPermission = false;//诊断的权限
    createDiagnosis.reportPermission = false;//报告的权限
    createDiagnosis.canReport = true;//报告的权限
    createDiagnosis.sectionPermission = false;//深切的权限

    createDiagnosis.isReportShow = false;//报告按钮是否显示
    createDiagnosis.isButtonAble = {
      star: true,//质控评分不可用
      material: true,//重补取不可用
      section: true,//深切不可用
      special: true,//特染不可用
      diagnosis: true,//诊断不可用
      report: true,//报告不可用
      template: true//模板选择不可用
    };
    createDiagnosis.casesList = [{code: '1', name: '疑难病例'}];//选择病例类型
    // console.log($rootScope.user);

    // $scope.savedPhoto = [{url: 'http://localhost:8887/2017_12_06/artist5-3.jpg', saved: true,tag:1}]; //诊断的照片 【第一部分，在点击病理信息的时候获取照片的时候重新赋值 $scope.savedPhoto 】
    $scope.totalPhoto = []; //诊断的照片 可能有默认的
    photoService.clearLocalPhoto();
    photoService.initStasticPath();
    function insertPhoto() {

      photoService.getLocalPhotoUrlList().then(function (res) {
        // console.info("抓取到的本地的照片",res);
        angular.forEach(res, function (item) {

          var localPhoto = {
            url: item,
            tag: '1'
          };
          if($scope.totalPhoto.length >= 10){
            toastr.error("最多只能保存10张照片,请删掉一些照片后再重拍");
          }else {
          $scope.totalPhoto.push(localPhoto);
          autoSavePhoto(localPhoto);// 优化显示的是本地的图片URL 上传成功后会才有id
          }//限制最多10张照片
          /*return
          // 用自动上传后获取的在线图片地址代替本地的图片地址
          autoSavePhoto(item)*/

        });

        // $scope.localPhoto = _.takeRight($scope.picData, 3); //从数组右侧截取 3个
        // console.info(222, $scope.totalPhoto)

      });

    }// end insertPhoto

    // 启动一个定时器 【点击获取病理信息启动】
    var insertPhotoInterval = $interval(function () {
      if(createDiagnosis.pathologicalData.id){
        insertPhoto();
      }
    }, 3000); //插入照片定时器

    $scope.$on('$destroy', function () {
      // 保证interval已经被销毁
      $interval.cancel(insertPhotoInterval);
      // 清空本地文件
      photoService.clearLocalPhoto();
      photoService.initAutoPath() //防止在其它拍照工作站目录错误
    });

    $scope.deletePhoto = function (fileId, index) {
      // 删除功能照片列表
      DiagnosisService.deleteImages(fileId).then(function () {
        $scope.totalPhoto.splice(index, 1);
      },function (err) {
        toastr.error("删除照片失败")
      })
    };
    // 勾选哪张照片要插入报告 【镜下所见照片】 createDiagnosis.checkedPhotos = []
    $scope.insertPhoto = function (item) {

      //判断最多插入报告的图片不超过两张 【当已经选中了两个了后，再选择时判断当前这个 item 没有选过的照片就不让选了】
      for(var i=0,total = 0;i<$scope.totalPhoto.length;i++){
        if($scope.totalPhoto[i].check){
          total ++;
        }
        if(total >= 2){
          if(!item.check){

            toastr.error("最多插入两张照片到报告");
            return
          }
        }
      }

      //  插入报告功能 上传本地图片到服务端 获取服务的的图片url路径 插入服务端路径到 诊断内容 dom
      //  spirit 25 3.2 勾选图片
      var data = {
        "pathId":createDiagnosis.pathologicalData.id, //#病理ID 必填
        "fileId":item.id, //#文件ID 必填
        "specialId": createDiagnosis.pathologicalData.specialApplyId,//#特殊申请ID
        "check":!item.check //#是否勾选 必填 true-勾选 false-取消勾选
      };
      IhcService.put("/file/check",data).then(function (res) {
        item.check = !item.check;
      },function (err) {
        toastr.error("图片插入报告失败",err.reason)
      })
    };
    function autoSavePhoto(localPhoto) {

      // 抓取一张照片就上传一张照片
      photoService.getBase64(localPhoto.url).then(function (Base64) {
        if (!createDiagnosis.pathologicalData.id) return;

        toolService.fileUpload(Base64, 10, createDiagnosis.pathologicalData.id).then(function (res) {
          // debugger
          localPhoto.id = res[0].id;
          /*return;
          /!*res = [{
            id: 2928,
            saveName: "5d04db5225d14b1888e923c81d3e61d6.png",
            type: 1,
            path: "api/static/1/171211/bba5b181c82940da8317a6f9018ba942.png"
          }];*!/

          res[0].tag = 1;
          $scope.totalPhoto.push(res[0]);*/
        })
      });
    }

    //清空本地服务器上的图片文件
    createDiagnosis.deletePhoto = function () {
      photoService.clearLocalPhoto()
    };
    //清空本地服务器上的图片文件
    createDiagnosis.selectPhotoTag = function (item) {
      if(!item.id){
        toastr.error("图片还在上传中");
        return
      }

      IhcService.put("/file/tag/"+ item.id + "/" + item.tag).then(function (res) {
        /*修改成功*/
      },function (err) {
        item.tag = null; //修改 图片标签失败
        toastr.error("修改图片标签失败")
      })
    };
    //显示大图片
    $scope.showBigImg = function (url) {
      photoService.enlargePhoto(url)

    };

    //初始化清空
    function init() {
      createDiagnosis.serialNumber = "";
      createDiagnosis.pathologicalData = {result: {resultDom: "<div></div>"}};
      $("#test").empty();
      createDiagnosis.userSeeHtml = "";
      createDiagnosis.percentage = 0;
      createDiagnosis.iconList = [1, 1, 1, 1, 1];
      // createDiagnosis.imageList = [];
      justify(true);
    }

    //用户权限、病理状态、搜索条件 ==> 进行权限控制
    function justify(ifInit) {
      //1、根据用户权限判断报告按钮是否显示  2048:报告
      if (_.intersection($rootScope.user.permissionCodes, [4096, 8192]).length > 0) {
        createDiagnosis.isReportShow = true;
      }
      if (ifInit) {
        createDiagnosis.isButtonAble.material = true;
        createDiagnosis.isButtonAble.section = true;
        createDiagnosis.isButtonAble.special = true;
        createDiagnosis.isButtonAble.star = true;
        createDiagnosis.isButtonAble.template = true;
        createDiagnosis.isButtonAble.diagnosis = true;
        if (createDiagnosis.isReportShow) {
          createDiagnosis.isButtonAble.report = true;
        }
      } else {
        //冰冻 免疫组化 流程加入 todo 按钮的判断问题
        if (createDiagnosis.pathologicalData.specialApplyId) {
          if (createDiagnosis.isReportShow) {
            createDiagnosis.isButtonAble.report = false;

          }
          createDiagnosis.isButtonAble.diagnosis = false;
          createDiagnosis.isButtonAble.material = true;
          createDiagnosis.isButtonAble.section = true;
          createDiagnosis.isButtonAble.special = true;
          createDiagnosis.isButtonAble.star = true;

          if (createDiagnosis.pathologicalData.marker || createDiagnosis.pathologicalData.markers) {
            createDiagnosis.isButtonAble.material = false;
            createDiagnosis.isButtonAble.section = false;
            createDiagnosis.isButtonAble.special = false;
            createDiagnosis.isButtonAble.star = false;
            createDiagnosis.isButtonAble.template = false;

          }
          // console.info("特殊流程的判断结束",createDiagnosis.isButtonAble);
          return false;
        }

        //2、根据病理状态来判断按钮们是否可用
        if (createDiagnosis.pathologicalData.status < 20 || createDiagnosis.pathologicalData.status >= 24) {
          if (createDiagnosis.pathologicalData.status < 20) {
            var canSection = [];
            DiagnosisService.getBlockDataByPathology(createDiagnosis.pathologicalData.id).then(
              function (result) {
                canSection = result;
                console.log('canSection-canSection', canSection);
                //查询一下有没有可以进行深切的蜡块
                if (canSection.length) {
                  createDiagnosis.isButtonAble.section = false;
                }
              }
            );
          }
          if (createDiagnosis.pathologicalData.status === 24) {
            toastr.error("当前病理待出报告，无法进行其他操作！");
            if (createDiagnosis.pathologicalData.countWatchedSlide === createDiagnosis.pathologicalData.countSlides) createDiagnosis.isButtonAble.report = false;
          }
          createDiagnosis.isButtonAble.material = true;
          createDiagnosis.isButtonAble.special = true;
          createDiagnosis.isButtonAble.star = true;
          createDiagnosis.isButtonAble.template = true;
          createDiagnosis.isButtonAble.diagnosis = true;
        }
        else {//待一级诊断和未发报告之前的病理
          createDiagnosis.isButtonAble.material = false;
          createDiagnosis.isButtonAble.section = false;
          createDiagnosis.isButtonAble.special = false;
          createDiagnosis.isButtonAble.star = false;
          createDiagnosis.isButtonAble.template = false;
          createDiagnosis.isButtonAble.diagnosis = false;
          if (createDiagnosis.isReportShow) {
            createDiagnosis.isButtonAble.report = false;
          }
          //4、报告按钮可用
          if (createDiagnosis.pathologicalData.status !== 25 && createDiagnosis.isReportShow/* && (createDiagnosis.pathologicalData.countWatchedSlide === createDiagnosis.pathologicalData.countSlides)*/) {
            if (createDiagnosis.pathologicalData.status === 20 || createDiagnosis.pathologicalData.status === 21 || createDiagnosis.pathologicalData.status === 22 || createDiagnosis.pathologicalData.status === 24) {
              createDiagnosis.isButtonAble.report = false;
            }
          }

          //3、特殊案例分析 【权限跟病理状态是否匹配】 病理诊断过同级医生可做与不可做操作 4096:二级诊断 8192:三级诊断   病理状态 20 待一级诊断 21 待二级诊断 22 待三级诊断
          if ((createDiagnosis.pathologicalData.status === 21 && (_.intersection($rootScope.user.permissionCodes, [4096, 8192]).length === 0))
            || (createDiagnosis.pathologicalData.status === 22 && (_.intersection($rootScope.user.permissionCodes, [8192]).length === 0))
            || (createDiagnosis.pathologicalData.status > 22)) {
            createDiagnosis.isButtonAble.material = true;
            createDiagnosis.isButtonAble.section = true;
            createDiagnosis.isButtonAble.special = true;
            createDiagnosis.isButtonAble.star = true;
            createDiagnosis.isButtonAble.template = true;
            createDiagnosis.isButtonAble.diagnosis = true;
            createDiagnosis.isButtonAble.report = true;
          }

          //5、提交上级诊断按钮的可用状态与否
          if (_.intersection($rootScope.user.permissionCodes, [8192]).length || createDiagnosis.pathologicalData.status === 22) {
            createDiagnosis.isButtonAble.diagnosis = true;
          }
        }
        //console.log('createDiagnosis.isButtonAble',createDiagnosis.isButtonAble);
      }

    }

    //计算小星星平均分显示样式
    createDiagnosis.countStar = function () {
      /*
       *  1、把当前获取的平均分*2的结果进行四舍五入
       *  2、把第二步的结果模2
       *  3、判断上一步结果，若===0 则为整数颗星；若!==0 则含有半颗星
       *  4、整星个数：parseInt(createDiagnosis.average/2) 半星位置：createDiagnosis.iconList[starCount]
       */
      createDiagnosis.iconList = [1, 1, 1, 1, 1];
      var starCount = 0;//整颗星的个数
      var halfStarCount = 0;//半颗星的个数
      createDiagnosis.average = Math.round(createDiagnosis.pathologicalData.score.average * 2);
      if (createDiagnosis.average % 2 === 0) {
        starCount = createDiagnosis.average / 2;
        for (var i = 0; i < starCount; i++) {
          createDiagnosis.iconList[i] = 2;
        }
      } else {
        starCount = parseInt(createDiagnosis.average / 2);
        halfStarCount = 1;
        for (var i = 0; i < starCount; i++) {
          createDiagnosis.iconList[i] = 2;
        }
        createDiagnosis.iconList[starCount] = 3;
      }
      // console.log("小星星数组是：", createDiagnosis.iconList);
      return createDiagnosis.iconList;
    };

    $timeout(function () {
      createDiagnosis.containerWidth = $('#img-container').width();
      createDiagnosis.imgStyle = {
        width: createDiagnosis.containerWidth / createDiagnosis.perItemNum + 'px',
        padding: '0 5px',
        float: 'left'
      };
    });

    //获取病理图片
    function getMicroImages(id) {
      DiagnosisService.getMicroImages(id).then(function (imgResult) {
        // createDiagnosis.imageList = imgResult;
        $scope.totalPhoto = imgResult||[];
      });
    }

    //生成图片html
    function getImgHtml(imgList, className) {
      var html = "<div class='clearfix'>";
      if (imgList) {
        imgList.forEach(function (img) {
          var imgSrc = "";
          if (typeof img === "string") {
            if (img.length > 100) {
              imgSrc = img;

            } else {
              imgSrc = createDiagnosis.imgUrlHeader + img;

            }
          } else if (typeof img === "object") {
            imgSrc = createDiagnosis.imgUrlHeader + img.url;
          }

          html += "<img crossOrigin = 'anonymous' class=' " + (className || "jujianImg") + " ' src='" + imgSrc + "' >"
        });
      } else {
        html += "无";
      }
      return html + "</div>";
    }

    //根据病理号或玻片号获取信息
    createDiagnosis.getDataByPathology = function (item, index) {
      photoService.clearLocalPhoto();
      if (index >= 0) {
        createDiagnosis.index = index; //判断选中
      } else {
        createDiagnosis.index = null;
      }

      $scope.tab2Index = 0;

      if (item) {

        createDiagnosis.activePathologyItem = item; //暂存一个选中的病理 在操作成功后可 重新获取这个病理的信息
        createDiagnosis.type = item.type;

        if (item.type === 0) { //判断检查类别是不是常规
          createDiagnosis.serialNumber = item.serialNumber;
        } else {
          createDiagnosis.serialNumber = item.number; //通过冰冻号查询
          var params = {
            special: true
          }
        }

      } //获取表格里病理信息的时候用 参数

      if (!createDiagnosis.serialNumber) {//搜索条件为空
        createDiagnosis.isButtonAble.all = true;
        toastr.error("请输入病理号或病理号-蜡块号-玻片号！");
        return;
      }

      // special

      //var str=createDiagnosis.serialNumber.split("-");
      //createDiagnosis.serialNumber=str.join("");
      DiagnosisService.getDataByPathology(createDiagnosis.serialNumber, params).then(
        function (result) {
          createDiagnosis.serialNumber = null;// 不让点击表格时搜索框重复显示搜索内容

          //todo ？？保留先前同一个病理的诊断内容 interesting and important (the method of request)
          // if (result.applicationId === createDiagnosis.pathologicalData.applicationId) {
          //   result.result = createDiagnosis.pathologicalData.result.resultDom;
          // }

          createDiagnosis.pathologicalData = result; //获取病理信息
          // console.warn(result);
          createDiagnosis.casesType = result.label == null ? undefined : createDiagnosis.casesList[result.label - 1].code;//每次点击病理号 获取病例标注状态
          createDiagnosis.pathologicalData.delay = (result.estimateReportTime < new Date().setHours(0, 0, 0, 0)); //判断是否延期
          if (createDiagnosis.pathologicalData.status > 22) { //?????
            createDiagnosis.pathologicalData.delay = false;
            createDiagnosis.pathologicalData.delayDesc = createDiagnosis.pathologicalData.statusName
          } else {
            if (result.estimateReportTime < new Date().setHours(0, 0, 0, 0)) {
              createDiagnosis.pathologicalData.delayDesc = "延期";
            } else {
              createDiagnosis.pathologicalData.delayDesc = "正常待发";
            }
          }

          createDiagnosis.markers = []; //特检结果 填写 勾选
          if (result.markers) {
            if (result.specialResult) {
              var markers = result.specialResult.split("；");
              //todo 特检 每个标记物都选了单位 才能发报告
              var combineMarker = {};
              angular.forEach(markers, function (makrer, index) {
                // 去掉右括号

                var leftIndex = makrer.indexOf("(");
                var right = makrer.indexOf(")");

                var leftMarker = makrer.substring(0, leftIndex);
                var unit = makrer.substring(leftIndex + 1, right)

                combineMarker[leftMarker] = unit;

              });

              // return
            }
            // 重新构造一个对象数组
            angular.forEach(result.markers, function (item, index) {
              createDiagnosis.markers.push(
                {
                  name: item,
                  unit: combineMarker && combineMarker[item]
                }
              )
            })
          }

          // 获取特检历史信息 弹窗里显示
          IhcService.get("/diagnose/special/history/" + result.serialNumber).then(function (res) {
            if (!res) return;
            createDiagnosis.specialHistoryResult = res
          });

          // 获取历史诊断信息
          getDiagnosisHistory();
          //获取取材信息
          createDiagnosis.sampleMaterialInfo();

          createDiagnosis.pathologicalData.result = {resultDom: createDiagnosis.pathologicalData.result};
          toolService.convertImgToSamllBase64(result.grossingImages, 75, 50).then(function (imgDatas) {
            //console.warn("imgDatas",imgDatas);

            var imgHtml = "";
            if (imgDatas.length > 0) {
              if (typeof imgDatas === "string") {
                imgDatas = [imgDatas];
              }
              imgHtml = getImgHtml(imgDatas);
              /*
              * 目前需求 打印报告的时候不用打印出来取材的图片
              * */
            }

            //生成肉眼所见html
            createDiagnosis.userSeeHtml =
              "<div>" +
              // "<p>巨检描述:</p>" +
              "<p>" + (result.jujianNote || "") + "</p>" +
              // "<p>冰冻描述:</p>" +
              "<p>" + (result.bingdongNote || "") + "</p>" +
              "</div>";
  
            // "<p>巨检图像:</p>" +
            createDiagnosis.userSeeContent = createDiagnosis.userSeeHtml + "<p class='clearfix'>" + imgHtml + "</p>"
            
          });

          //计算进度条长度
          if (createDiagnosis.pathologicalData) {
            var percentage = (createDiagnosis.pathologicalData.countWatchedSlide / createDiagnosis.pathologicalData.countSlides) * 100;
            createDiagnosis.percentage = Math.round(percentage);//函数:Math.round() 作用:把一个数四舍五入为最接近的整数
          } else {
            createDiagnosis.percentage = 0;
          }
          //计算小星星显示
          if (!createDiagnosis.pathologicalData || !createDiagnosis.pathologicalData.score) {
            createDiagnosis.iconList = [1, 1, 1, 1, 1];
          } else {
            createDiagnosis.iconList = createDiagnosis.countStar();
          }
          //获取病理图片
          getMicroImages(result.id);
          if (!createDiagnosis.pathologicalData.result) {
            createDiagnosis.pathologicalData.result = {
              resultDom: ''
            }
          }

          // console.log('判断按钮点击状态');
          justify();

        },
        function (error) {
          init();

          if (error.code === 1) {
            toastr.error("该病理号由医生:" + error.data.firstName + "诊断");
          } else {
            toastr.error("该记录不存在或者输入条件格式不正确！");
          }

        }
      );
    };

    /*//设置视频参数
     createDiagnosis.initVideo = function () {
     var option = {gumVideo: "#gumVideo"};
     videoService.init(option);
     };

     //拍照功能
     createDiagnosis.photo = function () {
     if (!createDiagnosis.pathologicalData.id) return;
     var imgUrl = videoService.photo(538, 389);
     var length = createDiagnosis.imageList.push({url: imgUrl});
     toolService.fileUpload(imgUrl.split(",")[1], 10, createDiagnosis.pathologicalData.id).then(function (imgData) {
     createDiagnosis.imageList[length - 1].id = imgData[0].id
     })

     };

     //显示具体的图片
     createDiagnosis.showBigImg = function (imgData, imgIndex) {
     var modalInstance = $uibModal.open({
     templateUrl: 'app/site/drawMaterial/modal/showImg.html',
     controller: 'ShowImgController',
     size: 'lg',
     resolve: {
     imgUrl: function () {
     return (imgData.id ? createDiagnosis.imgUrlHeader : '') + imgData.url;
     }
     }
     });

     modalInstance.result.then(function (delFlag) {
     if (delFlag) {
     var delImg = createDiagnosis.imageList.splice(imgIndex, 1)[0];
     if (delImg.id) {
     DiagnosisService.deleteImages(delImg.id)
     }
     //console.log(delImg);
     }
     })
     };

     //显示具体的视频
     createDiagnosis.showBigVideo = function () {
     var modalInstance = $uibModal.open({
     templateUrl: 'app/site/modal/showVideo.html',
     controller: 'ShowVideoController',
     size: 'lg',
     resolve: {

     position: function () {
     return 10;
     },
     pathologicalId: function () {
     return createDiagnosis.pathologicalData.id;
     }
     }
     });

     modalInstance.result.then(function () {
     getMicroImages(createDiagnosis.pathologicalData.id)

     })
     };

     createDiagnosis.initVideo();*/

    /*//左侧诊断列表
     MaterialService.getDepartments().then(//不确定送检科室
     function (result) {
     createDiagnosis.departmentsList = result;
     }
     );*/
    createDiagnosis.sampleStatusList = [{name: "待诊断", code: 1}, {name: "即将延期", code: 2}, {name: "报告延期", code: 3}];
    createDiagnosis.defaultSelectTime = 1;
    createDiagnosis.filterData = {
      page: 1,
      length: 17,
      filter: null,
      createTimeStart: "",
      createTimeEnd: "",
      // departments:"",
      // delay:"",
    };
    function changeStatus(status) {
      if (status) createDiagnosis.status = status;
      switch (createDiagnosis.status) {
        case 1:
          createDiagnosis.filterData.delay = false;
          break;
        case 2:
          //即将延期
          createDiagnosis.filterData.delay = false;
          createDiagnosis.filterData.delayTime = createDiagnosis.delayTime; //filter 保持一致
          //createDiagnosis.filterData.departments = null;

          break;
        case 3:
          createDiagnosis.filterData.delay = true;
          break;
        default:
          delete  createDiagnosis.filterData.delay;
      }
    }
    // 获取诊断列表
    createDiagnosis.getDiagnosisList = function () {

      changeStatus();
      //获取延期报告总数
      DiagnosisService.getDelayTotal().then(function (res) {
        createDiagnosis.delayDiagnosisTotal = res;
      });
      //即将延期报告
      DiagnosisService.getDelayTotal({delayTime: createDiagnosis.delayTime}).then(function (res) {
        createDiagnosis.willDelayDiagnosisTotal = res;
      });
      $timeout(function () {

        DiagnosisService.getDiagnosisList(createDiagnosis.filterData).then(function (res) {
          createDiagnosis.diagnosisList = res.data;
          createDiagnosis.total = res.total;
          createDiagnosis.filterData.page = res.page;

          createDiagnosis.filterData.delayTime = null;

        }, function (err) {
          if (err.code = 1) {

          }

        })
      })
    };
    createDiagnosis.getDiagnosisList();

    /*// 搜索
     createDiagnosis.search = function () {
     createDiagnosis.defaultSelectTime=5;
     createDiagnosis.getDataByPathology();
     };*/
    // 筛选
    createDiagnosis.query = function () {

      createDiagnosis.getDiagnosisList();
    };

    //获取延期病理列表
    createDiagnosis.getDelayPathologic = function () {
      createDiagnosis.defaultSelectTime = 5;
      changeStatus(3);
      createDiagnosis.filterData.departments = null;
      createDiagnosis.getDiagnosisList();
    };
    //即将延期病理列表
    createDiagnosis.getWillDelayPathologic = function () {
      createDiagnosis.defaultSelectTime = 5;
      changeStatus(2);

      createDiagnosis.getDiagnosisList();
    };

    /* 中间 tab 部分*/
    // 历史诊断 createDiagnosis.getDiagnosisHistory =
    function getDiagnosisHistory() {
      createDiagnosis.activeHistoryTab = 0;//历史诊断的第一项
      if (!createDiagnosis.pathologicalData.id) return;
      var str = '';
      var pathId = createDiagnosis.pathologicalData.id;
      DiagnosisService.getDiagnosisInfo(pathId).then(
        function (result) {
          createDiagnosis.diagnosisHistoryData = result;
        },
        function (error) {

          str = "暂无";
          createDiagnosis.diagnosisData = str;
        }
      );
    }

    // 取材信息
    createDiagnosis.sampleMaterialInfo = function () {

      if (!createDiagnosis.pathologicalData.id) return;

      if (createDiagnosis.pathologicalData.type === 1) { //冰冻的取材记录
        // todo 当是冰冻取材的时候 显示冰冻取材的信息 取材玻片信息
        var number = createDiagnosis.pathologicalData.number;
      }

      //常规取材申请显示蜡块的取材信息
      QueryService.sampleMaterialInfo(createDiagnosis.pathologicalData.id, number).then(function (res) {
        //console.log(res)
        createDiagnosis.materialInfo = res;
      })

    };
    //模板部分
    //获取系统设置的模板类型
    IhcService.get('/paramSetting/diagnoseTemplate').then(function(res){
      if(res==null){
        createDiagnosis.templateType = 0;
      }else{
        createDiagnosis.templateType = res;
      }
    });

    //获取最近使用模板
    createDiagnosis.getRecentTemplate = function () {
      MaterialService.getTemplateUsed(2).then(
        function (result) {
          createDiagnosis.recentTemplate = result;
          //console.log("获取最近常用模板",createDiagnosis.recentTemplate);//输出结果
        }
      );
    };
    createDiagnosis.getRecentTemplate();

    //设置常用模板
    createDiagnosis.setRecentTemplate = function (id) {
      MaterialService.setTemplateUsed(id, 2).then(
        function (result) {
          createDiagnosis.getRecentTemplate();
        }
      );
    };

    //根据选中的送检科室获取诊断模板内容
    createDiagnosis.getTemplatesList = function (code) {
      createDiagnosis.activeDepartment = code;//选中的科室
      DiagnosisService.getDiagnoseTemplate(code).then(
        function (result) {
          createDiagnosis.templatesList = result;
          if (createDiagnosis.templatesList.length) {
            createDiagnosis.activeTemplate = createDiagnosis.templatesList[0].id;////默认选中第一个模板
          }
        }
      );
    };

    //选中指定的模板显示其具体内容
    createDiagnosis.getTemplatesData = function (id, item) {
      createDiagnosis.activeTemplate = id;//选中的模板名字
      if (!item.length) {
        toastr.error("当前模板的内容为空，请先去设置处填充该模板！");
        return;
      }
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/diagnosis/modal/templateInfo/templateInfo.html',
        controller: 'templateInfoController',
        controllerAs: 'templateInfo',
        resolve: {
          content: function () {
            return item;
          }
        }
      });
      modalInstance.result.then(function (data) {
        createDiagnosis.setRecentTemplate(id);
        //console.log("------data---------",data);
        //createDiagnosis.pathologicalData.result = createDiagnosis.pathologicalData.result.resultDom||{ resultDom:''};
        createDiagnosis.pathologicalData.result.resultDom = (createDiagnosis.pathologicalData.result.resultDom || '') + data;
        createDiagnosis.openFlag = false;
      });
    };

    //点击其他地方收起模板选择
    createDiagnosis.stopPropagation = function (e) {
      if (e.stopPropagation) e.stopPropagation();
      else e.cancelBubble = true;
    };

    //关闭模板选择
    createDiagnosis.closeTemplate = function () {
      if (createDiagnosis.openFlag) {
        createDiagnosis.openFlag = false;
      }
    };
    //控制模板选择打开状态  和 获取送检科室列表
    createDiagnosis.openTemplate = function (e, index) {
      createDiagnosis.openFlag = !index;
      createDiagnosis.stopPropagation(e);
      //获取送检科室列表
      if (createDiagnosis.openFlag&& createDiagnosis.templateType ==0) {
        if (createDiagnosis.pathologicalData.departments) {//送检科室确定
          var department = {
            code: createDiagnosis.pathologicalData.departments,
            name: createDiagnosis.pathologicalData.departmentsDesc
          };

          createDiagnosis.departmentsList = [];//清空该数组
          createDiagnosis.departmentsList.push(department);
          createDiagnosis.getTemplatesList(createDiagnosis.departmentsList[0].code);//默认选中第一个科室
        } else {
          MaterialService.getDepartments().then(//不确定送检科室
            function (result) {
              createDiagnosis.departmentsList = result;
              createDiagnosis.getTemplatesList(createDiagnosis.departmentsList[0].code);//默认选中第一个科室
            }
          );
        }
      }
    };
    //发送上级诊断
    createDiagnosis.submitUpConfirm = function () {
      DiagnosisService.confirmDiagnosis(createDiagnosis.pathologicalData.id,
        getTestText()
      ).then(//不确定送检科室
        function (result) {
          // init();
          refresh();
          toastr.success("提交上级诊断成功！");
        },
        function (error) {
          toastr.error("提交上级诊断失败，请检查后重新尝试！");
        }
      );
    };

    //打开插入样本名称 // 通过 获取取材信息 createDiagnosis.sampleMaterialInfo()=》; 得到 样本信息：createDiagnosis.materialInfo

    init();
    createDiagnosis.openInsertSampleNames = function () {

      if (!createDiagnosis.pathologicalData.serialNumber) {
        toastr.error("请输入病理号或病理号-蜡块号-玻片号！");
        return;
      }

      ApplicationService.getOneByPathologyNo(createDiagnosis.pathologicalData.serialNumber).then(function (res) {
        createDiagnosis.samples = res[0].samples;
        var sampleNames = '';

        angular.forEach(createDiagnosis.samples, function (item, arr, index) {
          sampleNames += "<div>" + item.name + "</div>"
        });

        if (!createDiagnosis.pathologicalData.result.resultDom) {
          createDiagnosis.pathologicalData.result.resultDom = '';
        }

        createDiagnosis.pathologicalData.result.resultDom += sampleNames;
        // console.info(createDiagnosis.pathologicalData.result.resultDom)
      })

    };

    //打开查看申请表弹窗 参数 serialNumber：病理申请号
    createDiagnosis.openViewApply = function () {
      if (!createDiagnosis.pathologicalData.serialNumber) {
        toastr.error("请输入病理号或病理号-蜡块号-玻片号！");
        return;
      }
      ApplicationService.getOneByPathologyNo(createDiagnosis.pathologicalData.serialNumber).then(
        function (result) {
          $uibModal.open({
            templateUrl: 'app/site/modal/applicationModal.html',
            size: 'lg',
            controller: 'ApplicationModalController',
            controllerAs: 'applicationMod',
            resolve: {
              modalTitle: function () {
                return "病理申请表";
              },
              ApplicationData: function () {
                return result[0];
              }
            }
          })
        },
        function (error) {
          toastr.error(error);
        });
    };

    //打开查看历史诊断弹窗
    createDiagnosis.openViewHistory = function () {
      if (!createDiagnosis.pathologicalData.serialNumber) {
        toastr.error("请输入病理号或病理号-蜡块号-玻片号！");
        return;
      }
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/diagnosis/modal/viewHistory/viewHistory.html',
        controller: 'viewHistoryController',
        controllerAs: 'viewHistory',
        size: 'lg',
        resolve: {
          pathId: function () {
            return createDiagnosis.pathologicalData.id;
          }
        }
      });
      modalInstance.result.then(function () {

      });
    };

    //打开收藏弹窗
    createDiagnosis.openCollcetModal = function () {
      if (!createDiagnosis.pathologicalData.serialNumber) {
        toastr.error("请输入病理号或病理号-蜡块号-玻片号！");
        return;
      }

      var resolveData;
      // "targetId":1,  //病理id或者特殊申请id
      // "category":2,  //类别 1代表常规 2代表特殊申请
      if (createDiagnosis.pathologicalData.specialApplyId) {
        resolveData = {
          targetId: createDiagnosis.pathologicalData.specialApplyId,
          category: 2
        };
      } else {
        resolveData = {
          targetId: createDiagnosis.pathologicalData.id,
          category: 1
        };
      }

      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/diagnosis/modal/collect/collect.html',
        controller: 'DiagnosisCollectModalController',
        controllerAs: 'collectModal',
        // size: 'lg',
        resolve: {
          data: function () {
            return resolveData
          }
        }
      });
      modalInstance.result.then(function () {

      });
    };

    //打开质控评分弹窗
    createDiagnosis.openQualityGrade = function () {
      if (!createDiagnosis.pathologicalData.serialNumber) {
        toastr.error("请输入病理号或病理号-蜡块号-玻片号！");
        return;
      }
      if (!createDiagnosis.pathologicalData.slideId) {
        toastr.error("无法对病理号进行评分，只能对指定的玻片进行评分！");
        return;
      }
      if (createDiagnosis.isButtonAble.star) {
        return;
      }
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/diagnosis/modal/qualityGrade/qualityGrade.html',
        controller: 'qualityGradeController',
        controllerAs: 'qualityGrade',
        resolve: {
          slideId: function () {
            return createDiagnosis.pathologicalData.slideId;
          },
          scoreData: function () {
            return angular.copy(createDiagnosis.pathologicalData.score);
          }
        }
      });
      modalInstance.result.then(
        function (data) {//close进入的事件
          createDiagnosis.pathologicalData.score = data;
          //console.log("关闭小星星评分弹窗后",createDiagnosis.pathologicalData.score);
          createDiagnosis.iconList = [1, 1, 1, 1, 1];
          createDiagnosis.iconList = createDiagnosis.countStar();
        }
      );
    };

    //打开申请重补取弹窗
    createDiagnosis.openAgainMaterial = function () {
      var data = {
        id: createDiagnosis.pathologicalData.id,
        serialNumber: createDiagnosis.pathologicalData.serialNumber,
        patientName: createDiagnosis.pathologicalData.patientName,
        note: createDiagnosis.operateNote
      };
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/diagnosis/modal/againMaterial/againMaterial.html',
        controller: 'againMaterialController',
        controllerAs: 'againMaterial',
        resolve: {
          data: function () {
            return data;
          }
        }
      });
      modalInstance.result.then(function () {
        refresh()
        // createDiagnosis.operateNote = "";//将重补取或深切备注内容清空
        // init();
        // createDiagnosis.serialNumber = "";
      });
    };

    //打开深切弹窗
    createDiagnosis.openHeavySection = function () {
      var data = {
        id: createDiagnosis.pathologicalData.id,//病理ID
        serialNumber: createDiagnosis.pathologicalData.serialNumber,//病理号
        patientName: createDiagnosis.pathologicalData.patientName,//病人姓名
        blockSubId: createDiagnosis.pathologicalData.blockSubId,//蜡块号
        blockId: createDiagnosis.pathologicalData.blockId,//蜡块ID
        note: createDiagnosis.operateNote
      }
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/diagnosis/modal/heavySection/heavySection.html',
        controller: 'heavySectionController',
        controllerAs: 'heavySection',
        resolve: {
          data: function () {
            return data;
          }
        }
      });
      modalInstance.result.then(function () {
        refresh()
        // createDiagnosis.operateNote = "";//将重补取或深切备注内容清空
        // createDiagnosis.serialNumber = "";
        // init();
      });
    };

    /*//打开查看显微图像弹窗
    // createDiagnosis.openInsertPhoto = function () {
    //   if (!createDiagnosis.pathologicalData.serialNumber) {
    //     toastr.error("请输入病理号或病理号-蜡块号-玻片号！");
    //     return;
    //   }
    //   if (createDiagnosis.isButtonAble.star) {
    //     return;
    //   }
    //   var modalInstance = $uibModal.open({
    //     ariaLabelledBy: 'modal-title',
    //     ariaDescribedBy: 'modal-body',
    //     templateUrl: 'app/site/diagnosis/modal/insertPhoto/insertPhoto.html',
    //     controller: 'insertPhotoController',
    //     controllerAs: 'insertPhoto',
    //     resolve: {
    //       pathId: function () {
    //         return createDiagnosis.pathologicalData.id;
    //       }
    //     }
    //   });
    //   modalInstance.result.then(function (insertInfo) {
    //     // {
    //     //   "resultDom":"", #诊断结果DOM结构
    //     //   "imageDesc":"heheheeeeeeee", #图像描述
    //     //   "position":1 #插入位置
    //     // }
    //     // createDiagnosis.imageList=insertInfo.imgList;
    //     //重新声明了一个同名对象
    //     getMicroImages(createDiagnosis.pathologicalData.id);
		//
    //     createDiagnosis.pathologicalData.result = {
    //       imageDesc: insertInfo.imageDesc,
    //       position: insertInfo.position,
    //       resultDom: createDiagnosis.pathologicalData.result.resultDom || ""
    //     };
    //     var imgHtmlData = "";
    //     if (insertInfo.imgList && insertInfo.imgList.length > 0) {
    //       imgHtmlData = "<div class='img-group'>" + getImgHtml(insertInfo.imgList, "xianwei") + "<div class='imageDesc'>" + (insertInfo.imageDesc || "") + "</div></div>"
    //     }
    //     if (insertInfo.position === '1') {
    //       createDiagnosis.pathologicalData.result.resultDom = imgHtmlData + (createDiagnosis.pathologicalData.result.resultDom || "");
    //     } else {
    //       createDiagnosis.pathologicalData.result.resultDom = (createDiagnosis.pathologicalData.result.resultDom || "") + imgHtmlData;
    //     }
    //   });
    // };*/

    //打开申请特染弹窗
    createDiagnosis.openApplySpecial = function () {
      var data = {
        applicationId: createDiagnosis.pathologicalData.applicationId,//申请ID
        serialNumber: createDiagnosis.pathologicalData.serialNumber,//病理号
        patientName: createDiagnosis.pathologicalData.patientName,//病人姓名
        blockSubId: createDiagnosis.pathologicalData.blockSubId,//蜡块号
        blockId: createDiagnosis.pathologicalData.blockId,//蜡块ID
        pathid: createDiagnosis.pathologicalData.id,//病理ID
        department: createDiagnosis.pathologicalData.department,
        diagnoseDom: createDiagnosis.pathologicalData.result.resultDom, //
        specialHistoryResult: createDiagnosis.specialHistoryResult, //特检历史信息
        canApplyIhc: createDiagnosis.pathologicalData.canApplyIhc,
        ihcId: createDiagnosis.pathologicalData.ihcId,
        canApplySpecialDye: createDiagnosis.pathologicalData.canApplySpecialDye,
        specialDyeId: createDiagnosis.pathologicalData.specialDyeId

      };
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/diagnosis/modal/applySpecial/applySpecial.html',
        controller: 'applySpecialController',
        controllerAs: 'applySpecial',
        resolve: {
          data: function () {
            return data;
          }
        }
      });
      modalInstance.result.then(function () {

      });
    };

    //提交上级诊断弹窗
    createDiagnosis.openDiagnosisType = function () {
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/diagnosis/modal/diagnosisType/diagnosisType.html',
        controller: 'diagnosisTypeController',
        controllerAs: 'diagnosisType'
      });
      modalInstance.result.then(
        function (data) {//获取弹窗里选择的诊断医生 id
          createDiagnosis.userId = data.userId;
          createDiagnosis.note = data.note;
          createDiagnosis.submitUpConfirm();
        }
      );
    };

    //提交外院会诊
    createDiagnosis.openConsultModal = function () {
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/diagnosis/modal/consultApply/consult.html',
        controller: 'diagnoseConsultApplyController',
        controllerAs: 'diagnose'
      });
      modalInstance.result.then(function (note) {

        var resolveData = {
          note: note
        }, id;

        if (createDiagnosis.pathologicalData.specialApplyId) {
          resolveData.special = true;
          id = createDiagnosis.pathologicalData.specialApplyId
        } else {
          resolveData.special = false;
          id = createDiagnosis.pathologicalData.id
        }

        IhcService.post("/diagnose/out/" + id, resolveData).then(function () {
          toastr.success("操作成功！");
          createDiagnosis.getDataByPathology(createDiagnosis.activePathologyItem)
        }, function (err) {
          toastr.success("操作失败！")
        })
      });
    };
    function getTestText() {
      var microDiagnose = $('#test .imageDesc').text().trim();
      var diagnose = $('#test div,#test p').not(".imageDesc,.img-group").text().trim();
      if (!microDiagnose && !diagnose) {
        diagnose = $('#test').text();
      }
      return {
        resultDom: createDiagnosis.pathologicalData.result.resultDom,
        specialResult: insertSpecialResult(), //常规的 跟病冻的不应该有内容
        outConsultResult: createDiagnosis.pathologicalData.outConsultResult,//外院会诊的诊断意见
        microDiagnose: microDiagnose,
        diagnose: diagnose,
        assignDiagnose: createDiagnosis.userId, //从获取诊断医生的回调里获取
        note: createDiagnosis.note //从获取诊断医生的回调里获取
      }
    }

    //打开报告弹窗
    createDiagnosis.openReportResult = function () {

      var checkPhotos = [];
      angular.forEach($scope.totalPhoto, function (item) {
        if(item.check){
          checkPhotos.push(item.url)
        }
      });
      var data = getTestText();
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/diagnosis/modal/reportResult/reportResult.html',
        controller: 'reportResultController',
        controllerAs: 'reportResult',
        size: 'lg',
        resolve: {
          resolveData: function () {
            // console.info("诊断的检查类别",createDiagnosis.pathologicalData)
            return {
              checkPhotos:checkPhotos,// 显示插入报告的照片
              inspectCategory: createDiagnosis.pathologicalData.inspectCategory,
              pathId: createDiagnosis.pathologicalData.id,
              type: createDiagnosis.pathologicalData.type, //后端返回的检查类别 1常规 3冰冻
              outConsult: createDiagnosis.pathologicalData.outConsult,
              outConsultResult: createDiagnosis.pathologicalData.outConsultResult,//外院会诊的诊断意见
              markers: createDiagnosis.pathologicalData.markers,//特检申请的标记物
              specialApplyId: createDiagnosis.pathologicalData.specialApplyId,
              userSeeHtml: createDiagnosis.userSeeHtml,
              diagnosis: data,
              label: createDiagnosis.casesType
              // userSeeHtml:createDiagnosis.userSeeHtml,
            }
          }
        }
      });
      modalInstance.result.then(function () {
        refresh()
      });
    };

    createDiagnosis.useMicroscope = function () {
      IhcService.post("/microscopeTracking", {})
    };

    /*
     * 特检结果部分 逻辑处理  要单独保存一份 todo
     * */
    function insertSpecialResult() {

      // 判断是会诊的
      if (createDiagnosis.pathologicalData.type === 4) {

        return createDiagnosis.pathologicalData.specialResult;

      } else if (createDiagnosis.pathologicalData.type === 0 || createDiagnosis.pathologicalData.type === 1) {

        // 常规 冰冻的没有
        return

      } else {
        if (!createDiagnosis.markers.length) return;

        var result = [];
        angular.forEach(createDiagnosis.markers, function (item, index) {
          if (item.unit) {
            result.push(item.name + '(' + item.unit + ')')
          }
        });
        var html = result.join("；");
        return html
      }

      // console.error(html,"未保存到后端")
    }

    createDiagnosis.save = function () {

      if (createDiagnosis.pathologicalData.type === 4 || createDiagnosis.pathologicalData.outConsult) {
        if (!createDiagnosis.pathologicalData.specialResult && !createDiagnosis.pathologicalData.outConsultResult) {
          toastr.error("请填写会诊内容"); //当是诊断的 会诊的申请的时候 可以不填写诊断内容但 一定要填会诊内容
          return false
        }
      } else {
        /*if (createDiagnosis.pathologicalData.type === 0 && !createDiagnosis.pathologicalData.result.resultDom) {
          toastr.error("请填写诊断内容"); //当是诊断的 会诊的申请的时候 可以不填写会诊内容但 一定要填诊断内容
          return false
        }*/
      }

      var pathId = createDiagnosis.pathologicalData.id;
      var microDiagnose = $('#test .imageDesc').text().trim();
      var diagnose = $('#test div,#test p').not(".imageDesc,.img-group").text().trim();
      if (!microDiagnose && !diagnose) {
        diagnose = $('#test').text();
      }

      var data = {
        "resultDom": createDiagnosis.pathologicalData.result.resultDom,  //#诊断结果DOM结构
        "diagnose": diagnose, //#诊断描述 用来保存诊断的文字内容
        "assignDiagnose": createDiagnosis.userId, //#指定诊断医生  现在没有这个
        "specialApplyId": createDiagnosis.pathologicalData.specialApplyId,  //#特殊申请ID(保存特殊申请病理需传此ID)
        "specialResult": insertSpecialResult(),  //#特殊结果(特检结果或会诊意见),
        "label": createDiagnosis.casesType,
        outConsultResult: createDiagnosis.pathologicalData.outConsultResult,//外院会诊的诊断意见
      };

      IhcService.post("/diagnose/save/" + pathId, data).then(function (res) {
        toastr.success("保存成功！")
      })

    };

    function refresh() {
      $state.go('app.CreateDiagnosis', {}, {reload: true})
    }

    init();
  }
})();

