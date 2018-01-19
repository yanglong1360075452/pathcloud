(function () {
  'use strict';
  
  angular
  .module('pathcloud')
  .controller('createQueryController', createQueryController);
  
  /** @ngInject */
  function createQueryController($scope, $rootScope, $q, $state, toastr, DiagnosisService, MaterialService, toolService, PathologyService, QueryService, $timeout, printerService, ApplicationService, SystemSettingService, IhcService) {
    var createQuery = this;
    createQuery.activeMenu = 0;
    //搜索结果表单显示部分
    createQuery.tableHeaders = [
      {name: "病人ID"}, {name: "病理号", order: 2}, {
        name: "姓名",
        order: 3
      }, {name: "性别"}, {name: "年龄"}, {name: "住院号"}, {name: "送检科室"}, {name: "送检医生"}, {name: "送检医院"}, {
        name: "接收日期",
        order: 10
      }, {name: "报告日期", order: 11}, {name: "状态"},
    ];
    createQuery.tabHeaders = [{name: "病理报告"}, {name: "申请信息"}, {name: "样本信息"}, {name: "特检信息"}, {name: "诊断信息"}, {name: "其他检查"}]
    //此处用来显示报告的名称 从后端返回的数据列表没有对应的名称
    createQuery.reportType = {
      0: "常规病理报告",
      1: "冰冻报告",
      2: "免疫组化报告",
      3: "特染报告",
    };
    
    // 查询功能部分
    function init() {
      
      // 查询条件
      createQuery.filter = {
        length: 5,
        page: 1,
        // fieldContain | String | 包含字段
        // fieldExclusive | String | 不包含字段
        
        // fieldType | String | 字段类别
        
        // specialDye | Integer | 染色方式
        //
        // inspectionItem | Integer | 检查类别
        //
        // pathStatus | Integer | 样本状态
        //
        // departments | Integer | 送检科室
        //
        // inspectionDoctor | Long | 送检医生
        //
        // diagnoseDoctor | Long | 诊断医生
        //
        // receiveTimeStart: "1482219630000",  // | Long | 接收时间-开始时间
        //new Date('2017-02-06T16:00:00.000Z').getTime()
        // receiveTimeEnd: "897267661",     // | Long | 接收时间-结束时间
        //
        // reportTimeStart: "1484881154000",    // | Long | 报告时间-开始时间
        //
        // reportTimeEnd: "1484881154000"    // | Long | 报告时间-结束时间
      };
  
      // SystemSettingService.getSpecialDyeList().then(function (res) {
      //   createQuery.specialDyeList = res;
      // }); //特染类别获取 直接在下面写死 只显示免疫组化跟特染的
      
      createQuery.fieldTypeList = [{code: 1, name: "病理号"}, {code: 2, name: "病人ID"}, {code: 3, name: "住院号"}, {
        code: 4,
        name: "病人姓名"
      }, {code: 5, name: "诊断内容"}, /*{code:6,name:"诊断项目"},*/ {code: 7, name: "巨检描述"}, {code: 8, name: "显微所见"}]
      createQuery.specialDyeList = [{code: 1, name: "免疫组化"}, {code: 2, name: "特染"}];
      createQuery.pathStatus = [{code: 25, name: "已发报告"}, {code: 0, name: "未发报告"}];
      
      MaterialService.getDepartments().then(function (res) {
        //console.log("获取送检科室列表数据：-----",result);
        createQuery.departmentsList = res;
      });
      // 获取检查类别列表
      PathologyService.inspectCategoryList().then(function (res) {
        createQuery.inspectCategoryList = res;
      });
      // 2.2 获取送检医生列表
      QueryService.inspectDoctorList().then(function (res) {
        createQuery.inspectDoctorList = res;
      });
      // 2.6 获取报告医生列表
      
      // QueryService.diagnoseDoctorList().then(function (res) {
      //   createQuery.diagnoseDoctorList=res;
      // })
      DiagnosisService.getReportDoctor().then(
        function (res) {
          createQuery.diagnoseDoctorList = res;
        }
      );
      
      // 接收日期  uib-datepicker指令 option配置
      $scope.dateOptions1 = {
        dateDisabled: disabled1,
        maxDate: new Date()
      };
      $scope.dateOptions2 = {
        dateDisabled: disabled2,
        maxDate: new Date()
      };
      
      function disabled1(data) {
        if (!createQuery.filter.receiveTimeEnd_show) return;
        var date = data.date,
          mode = data.mode;
        
        return mode === 'day' && (date.getTime() > createQuery.filter.receiveTimeEnd_show);
      }
      
      function disabled2(data) {
        if (!createQuery.filter.receiveTimeStart) return;
        
        var date = data.date,
          mode = data.mode;
        
        return mode === 'day' && (date.getTime() < createQuery.filter.receiveTimeStart);
        // return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
      }
      
      //报告日期
      $scope.dateOptions3 = {
        dateDisabled: disabled3,
        maxDate: new Date()
      };
      $scope.dateOptions4 = {
        dateDisabled: disabled4,
        maxDate: new Date()
      };
      
      function disabled3(data) {
        if (!createQuery.filter.reportTimeEnd_show) return;
        var date = data.date,
          mode = data.mode;
        return mode === 'day' && (date.getTime() > createQuery.filter.reportTimeEnd_show);
      }
      
      function disabled4(data) {
        if (!createQuery.filter.reportTimeStart) return;
        var date = data.date,
          mode = data.mode;
        return mode === 'day' && (date.getTime() < createQuery.filter.reportTimeStart);
      }
      
      // 报告日期
    }
    
    init();
    
    createQuery.getData = function () {
      QueryService.query(createQuery.filter).then(function (res) {
        createQuery.queryResultList = res.data;
        createQuery.filter.page = res.page;
        createQuery.total = res.total;
        
        createQuery.exportFilter = angular.copy(createQuery.filter)
      })
    };
    //排序
    createQuery.getSortList = function (item) {
      
      if (!item.order) return;
      
      item.sort === 'asc' ? item.sort = 'desc' : item.sort = 'asc';
      createQuery.filter.sort = item.sort;
      createQuery.filter.order = item.order;
      createQuery.getData();
    };
    // 搜索
    createQuery.search = function () {
      createQuery.receiveTimeEnd_show = null;
      createQuery.reportTimeEnd_show = null;
      createQuery.filter = {
        filter: createQuery.searchStr
      };
      if (!createQuery.filter.filter) {
        createQuery.queryResultList = {};
        return;
      }
      
      createQuery.getData();
      
    }
    
    // 清除按钮
    createQuery.clear = function () {
      $state.go('app.CreateQuery', {}, {reload: true})
    };
    // 查询开始
    createQuery.query = function () {
      createQuery.filter.receiveTimeEnd = null;
      createQuery.filter.reportTimeEnd = null;
      createQuery.filter.filter = null; //查询时清空查询条件
      createQuery.searchStr = null; //查询时清空查询条件
      
      if (createQuery.filter.receiveTimeStart && createQuery.receiveTimeEnd_show || createQuery.filter.reportTimeStart && createQuery.reportTimeEnd_show) {
        if (createQuery.filter.receiveTimeStart)
          createQuery.filter.receiveTimeStart = new Date(createQuery.filter.receiveTimeStart).getTime();
        if (createQuery.receiveTimeEnd_show)
          createQuery.filter.receiveTimeEnd = new Date(createQuery.receiveTimeEnd_show).getTime() + 86400000;
        if (createQuery.filter.reportTimeStart)
          createQuery.filter.reportTimeStart = new Date(createQuery.filter.reportTimeStart).getTime();
        if (createQuery.reportTimeEnd_show)
          createQuery.filter.reportTimeEnd = new Date(createQuery.reportTimeEnd_show).getTime() + 86400000;
        
        createQuery.getData();
        
      } else {
        toolService.getTipResult({modalTitle: "提示", modalContent: "请选择一个日期范围!", size: "sm"}).then(function () {
        
        }, function () {
        
        });
      }
      
    };
    
    //导出病例信息表格
    createQuery.export = function () {
      if (createQuery.queryResultList && createQuery.queryResultList.length) {
        toolService.export(createQuery.exportFilter, "/query/export")
      }
    };
    
    //表格显示部分开始 从查询结果选择一条数据
    var activeOne;
    createQuery.getOne = function (item) {
      
      //同一个病理号会删出掉当前病理的照片
      if (activeOne) {
        if (activeOne.serialNumber === item.serialNumber) {
          return false;
        } else {
          $(".micro-img .img-group").remove(); //移除显微所见多余的图片
        }
      }
      
      // 选择不同的病理号清空下面tab数据  todo 修改查询显示
      createQuery.activeReport = null;
      createQuery.applicationFormData = null;
      
      $rootScope.activeQueryOne = createQuery.activeOne = activeOne = item;
      $scope.applicationTabActive = false; //选中申请信息tab ng-if 起效的条件
      
      //获取该病理所有的报告种类
      createQuery.reportTypeList = [];
      IhcService.get("/query/report/summary/" + activeOne.id).then(function (res) {
        createQuery.reportTypeList = res;
        
        createQuery.activeTab = 0; //选择不同病理时重置tab 到报告
        createQuery.reportTab();
        createQuery.activeReportTab = 0;
      });
      
    };
    
    // TAB切换部分开始
    createQuery.switchTab = function (index) {
      switch (createQuery.activeTab) {
        case 0:
          createQuery.reportTab();
          break;
        case 1:
          createQuery.activeApplicationTab = 0;
          createQuery.selectApplication();
          break;
        case 2:
          createQuery.sampleTab();
          break;
        case 3:
          createQuery.specialDyeTab();
          break;
        case 4:
          createQuery.diagnosisTab();
          break;
        default:
          createQuery.reportTab()
        
      }
    };
    createQuery.stopPropagation = function (e) {
      e.stopPropagation();
    };
    // 病理报告 部分
    createQuery.printReport = function (e) {
      
      if (!activeOne) return;
      printerService.printDom($('#createQuery .report-dom').html());
      
      // toolService.getHtmlImgByTagName('#createQuery .report-dom', 2, function (imgData, w, h) {
      //   printerService.printDomByImg(imgData, w, h);
      // });
      // e.stopPropagation();
      return false;
    };
    
    createQuery.getReportDom = function (item) {
      console.info(item)
      /*
       * 通过病理pathId 或 specialApplyId 获取报告的dom  * note 都传的话获取的 多个报告
       *  pathIds: pathId,pathId，
       * specialApplyIds: specialApplyId，specialApplyId
       * */
      createQuery.activeReport = null;
      
      var params = {};
      if (item.type === 0) {
        params.pathIds = item.pathId
      } else {
        params.specialApplyIds = item.specialApplyId
      }
      
      IhcService.get('/report/pic', params).then(function (results) {
        createQuery.activeReport = JSON.parse(results[0].reportPic).html;
      }, function (err) {
        toastr.error(err.reason);
        createQuery.activeReport = null
      })
    };
    
    createQuery.reportTab = function () {
      //病理报告tab  切换病理号的时候获取该病理报告tab信息 并显示第一个tab的报告内容
      console.info("病理报告report tab");
      
      if (!activeOne) return;
      
      if (createQuery.reportTypeList && createQuery.reportTypeList.length) {
        var tab = createQuery.reportTypeList[0];
        createQuery.getReportDom(tab);
      }
      
      /*$scope.reportData = {}; //通过report指令传值
       DiagnosisService.getReportInfo(activeOne.id).then(function (res) {
       // console.log("病理报告信息：",res)
       $scope.reportData = res; //通过report指令传值
       
       //巨检图像信息 取材页面拍的照片
       videoService.showVideo(activeOne.id).then(function (jujianImgList) {
       toolService.convertImgToSamllBase64(jujianImgList, 75, 50).then(function (imgList) {
       $scope.reportData.jujianImg = imgList;
       // console.info("获取拍照的图片信息",$scope.reportData.jujianImg);
       })
       });
       
       // $(".diagnosis").find(".img-group").remove();// ????
       // console.log("清空micro-img图片信息：",$(".micro-img"))
       $(".micro-img").empty(); // 清空micro-img图片信息
       // console.log("病理报告图片信息：",$(".diagnosis").find(".img-group"))
       $timeout(function () {
       // console.log("清空micro-img图片信息：",$(".micro-img"))
       $(".diagnosis").find(".img-group").remove().appendTo(".micro-img");
       }, 0)
       }, function () {
       $scope.reportData = {};
       })*/
      
      return false;
    };
    
    // 申请信息 部分
    
    createQuery.selectApplication = function () {
      if (!activeOne) return;
      
      //获取病理表单数据 控制显示隐藏
      $scope.showOperation = false;
      $scope.hideSubmit = true;
      $scope.readonly = true;
      
      ApplicationService.getOneByPathologyNo($rootScope.activeQueryOne.serialNumber).then(function (res) {
        // $scope.pathological={};
        createQuery.applicationList = res;
        // console.info("获取申请表单结束");
        // 获取申请表单结束 下一步选择要显示的申请单 默认0
        createQuery.activeApplicationTab = 0;
        createQuery.selectApplicationFrom(0)
        
      });
      
    };
    createQuery.selectApplicationFrom = function (index) {
      
      if (!createQuery.applicationList) return;
      
      createQuery.applicationFormData = createQuery.applicationList[index];
      // console.info("表单选择结束");
      
    };
    createQuery.specialApplicationTab = function (index) {
      
      if (!activeOne) return;
      // console.info(index);
      $scope.$broadcast('application', index, activeOne.id);
      
      // $scope.applicationTabActive = true; //选中申请信息tab
      
    };
    
    // TAB 3 样本信息
    // 基本信息
    createQuery.sampleTab = function () {
      $scope.sample = 1; //选择第一个子TAB
      if (!activeOne) return;
      QueryService.sampleBasicInfo(activeOne.id).then(function (res) {
        createQuery.basicInfo = res;
      });
      //获取冰冻号列表
      IhcService.get("/query/special/"+activeOne.serialNumber).then(function (res) {
        createQuery.frozenNmubers = res ||[]
      })
    };
    // 冰冻取材信息
    createQuery.frozenMaterial = function () {
      $scope.sample = 5; //选择冰冻TAB
      // IhcService.get("/frozen/"+createQuery.frozenNmubers[0])
      var promises = [];
      angular.forEach(createQuery.frozenNmubers,function (item) {
        //todo 获取取材信息 【冰冻取材玻片信息 先只显示取材信息不显示图片】
        promises.push(QueryService.sampleMaterialInfo(activeOne.id, item))
        
      });
      $q.all(promises).then(function (results) {
        var arr = [];
        angular.forEach(results, function (item) {
          arr.push(item.grossingInfo)
        });
        createQuery.frozenMaterialInfo = [].concat.apply([],arr);
      })
    };
    // 取材信息
    createQuery.sampleMaterialInfo = function () {
      $scope.sample = 2; //选择第二个子TAB
      if (!activeOne) return;
      QueryService.sampleMaterialInfo(activeOne.id).then(function (res) {
        createQuery.materialInfo = res;
      })
    };
    // 制片信息
    createQuery.sampleConfirmInfo = function () {
      $scope.applicationTabActive = false; //选中申请信息tab
      $scope.sample = 3; //选择第二个子TAB
      if (!activeOne) return;
      QueryService.sampleConfirmInfo(activeOne.id).then(function (res) {
        createQuery.confirmInfo = res;
      })
    };
    
    //存档信息  sprint 12  //5.1 玻片的借阅历史信息查询
    createQuery.sampleArchiveInfo = function () {
      $scope.sample = 4; //选择第一个子TAB
      if (!activeOne) return;
      // /query/archiving/{pid}
      IhcService.get("/query/archiving/" + activeOne.id).then(function (res) {
        // console.log(res)
        createQuery.archiveInfo = res;
      })
    };
    
    /* tab3 结束*/
    
    // 特染信息 specialDyeInfo
    createQuery.specialDyeIndex = 0; //默认显示特染里的HE染色 白片
    createQuery.specialDyeTab = function () {
      $scope.applicationTabActive = false; //选中申请信息tab
      $scope.sample = 4; //选择第三个子TAB
      if (!activeOne) return;
      createQuery.specialDyeSelect(createQuery.specialDyeList[0].code, 0) //白片 特染Tab的第一个
    };
    createQuery.specialDyeSelect = function (specialDye, index) {
      if (!activeOne) return;
      createQuery.specialDyeIndex = index;
      // console.info("特染信息index", createQuery.specialDyeIndex);
      QueryService.specialDyeInfo(activeOne.id, {specialDye: specialDye}).then(function (res) {
        // console.log("获取特染信息", res);
        createQuery.specialDyeInfo = res;
      })
    };
    
    // 诊断信息
    createQuery.diagnosisTab = function () {
      if (!activeOne) return;
      $scope.applicationTabActive = false; //选中申请信息tab
      createQuery.diagnosisInfo = {};
      
      DiagnosisService.getDiagnosisInfo(activeOne.id).then(function (res) {
        //console.info("历史诊断信息", res)
        createQuery.diagnosisInfo = res;
      })
    }
    
    // 其他检查
    
  }
})();

// 前端angular 导出excel  因为涉及到分页 后端导出
/*createQuery.exportCsv=function () {
 if(!createQuery.queryResultList.length){
 return;
 };
 // 病人ID	病理号	姓名	性别	年龄	住院号	送检科室	送检医生	送检医院	接收日期	报告日期
 var header = ['序号', '病人ID', '病理号', '姓名', '性别', '年龄','住院号','送检科室','送检医生','送检医院','接收日期','报告日期'];
 var data = [];
 for (var i = 0; i < createQuery.queryResultList.length; i++) {
 var item = createQuery.queryResultList[i];
 
 data.push([i + 1, item.hisId, item.serialNumber, item.patientName, $filter('sex')(item.sex),item.age,item.admissionNo||'无',item.departmentsDesc||'无',item.inspectDoctor.firstName,
 item.inspectHospital,$filter('date')(item.receiveTime, "yyyy-MM-dd HH:mm")||'',$filter('date')(item.reportTime, "yyyy-MM-dd HH:mm")||'']);
 }
 CsvService.download(header, data, '病例信息表');
 }*/
