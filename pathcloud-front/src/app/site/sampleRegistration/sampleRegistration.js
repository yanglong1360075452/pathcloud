(function () {
  'use strict';

  angular
  .module('pathcloud')
  .controller('SampleRegistrationController', SampleRegistrationController);

  /** @ngInject */
  function SampleRegistrationController($scope, $rootScope, PathologyService, ApplicationService, $filter, $uibModal, toastr, $state, printerService, MaterialService, IhcService, $timeout) {

    var sampleRegistration = this;

    function init() {
      $scope.showOperation = false;
      $scope.hideSubmit = false;
      $scope.readonly = true;
      $scope.autoFocus = true;
      $scope.hideSampleTable = true;
      $scope.activePart = true;  //局部刷新指令页面 默认显示 提交成功后刷新

      sampleRegistration.applyType = null;
      sampleRegistration.serialNumber = null;
      IhcService.get("/systemSetting/pathNoRule").then(function (res) {
        if (res.letter == 0)
          sampleRegistration.showInspectTypeLetter = "";
      })

    }

    init();
    function formatDataForForm( result) {
      if (result.applyType === 1) {
        $scope.pathological.data = result;
        sampleRegistration.applyType = 1;
      } else if (result.applyType === 2) {
        sampleRegistration.applyType = 2; //科研
        $scope.pathologicalFreeze.data = result.research;
        $scope.pathological.data.id = $scope.pathologicalFreeze.data.id = result.id;
        $scope.pathological.data.departmentsDesc = $scope.pathologicalFreeze.data.departmentsDesc = result.departmentsDesc;
        $scope.pathological.data.departments = $scope.pathologicalFreeze.data.departments = result.departments;
        $scope.pathological.data.status = $scope.pathologicalFreeze.data.status = result.status;
        $scope.pathological.data.statusName = $scope.pathologicalFreeze.data.statusName = result.statusName;
        $scope.pathological.data.samples = $scope.pathologicalFreeze.data.samples = result.samples;
        $scope.pathological.data.serialNumber = $scope.pathologicalFreeze.data.serialNumber = result.serialNumber;
        $scope.pathological.data.pathologyStatus = $scope.pathologicalFreeze.data.pathologyStatus = result.pathologyStatus;
        $scope.pathological.data.pathologyId = $scope.pathologicalFreeze.data.pathologyId = result.pathologyId; //撤销登记用
        $scope.pathological.data.pathNo = $scope.pathologicalFreeze.data.pathNo = result.pathNo; //打印病理号用

        // console.info("病理信息",$scope.pathological.data)

        $scope.pathological.data.applyType = $scope.pathologicalFreeze.data.applyType = 2;
      }
    }

    //局部刷新指令页面
    $scope.refresh = function () {
      $scope.activePart = null;
      $timeout(function () {
        init();
        initForm()
      }, 30)
    };

    //搜索框内容变化时判断 是否可编辑
    sampleRegistration.hide = function () {

      if (sampleRegistration.serialNumber || sampleRegistration.applyType) {
        $scope.readonly = true;
        $scope.showOperation = false;
      } else {
        if ($scope.pathological.data.id) {
          $scope.readonly = true;
          $scope.showOperation = false;
        } else {
          $scope.readonly = false;
          $scope.showOperation = true;
        }
      }
    };

    // 获取检查类别列表
    PathologyService.inspectCategoryList().then(function (res) {
      sampleRegistration.inspectCategoryList = res;
      sampleRegistration.inspectCategory = 1;
    })
    // 1.2 获取取材医生列表
    MaterialService.getGrossingUser().then(function (data) {
      sampleRegistration.assignGrossingList = data;
    });

    //打印标签 判断自动 多张
    IhcService.get("/paramSetting/printCount").then(function (res) {
      sampleRegistration.printCount = res;
    });
    IhcService.get("/paramSetting/printType").then(function (res) {
      sampleRegistration.printType = res;
    });
    // 打印病理号到标签纸上
    function printSerialNumber(data) {
      if ($scope.pathological.data.serialNumber) {
        var count = 1;
        if (sampleRegistration.printCount == 1) {
          count = $scope.pathological.data.samples.length
        }
        for (var i = 0; i < count; i++) {
          // console.info(count)
          printerService.printData(data)
        }
      }
    }

    //页面上点击打印
    sampleRegistration.printSerialNumber = function (pathNo) {
      if (!$scope.pathological.data.pathNo) return;
      var data = {
        pathNo: $scope.pathological.data.pathNo,
        patientName: $scope.pathological.data.patientName,
        frozenNumber: $scope.pathological.data.number
      }
      printSerialNumber(data)
    };

    //获取上一条记录
    sampleRegistration.getLastOne = function () {
      $scope.readonly = true;
      $scope.showOperation = false;
      var time = Date.now();
      if (sampleRegistration.pathCreateTime) {
        time = sampleRegistration.pathCreateTime;
      }
      IhcService.get("/pathology/pre/" + time).then(function (result) {

        if (result) {
          sampleRegistration.pathCreateTime = result.pathCreateTime;//获取上一条记录传的时间参数
        }

        formatDataForForm( result);

        sampleRegistration.serialNumber = result.serialNumber;

      }, function (err) {
        toastr.error(err.reason, "操作失败！");
      })


    };
    // 获取病理信息
    sampleRegistration.getOnePathological = function () {

      // console.info($scope,sampleRegistration)
      if (!sampleRegistration.serialNumber) return;

      var pathology = true;//通过病理号还是申请号搜索
      if (sampleRegistration.serialNumber.slice(0, 2) === "AP" || sampleRegistration.serialNumber.slice(0, 2) === "SA") {
        pathology = false;
      }

      ApplicationService.getOne(sampleRegistration.serialNumber, pathology).then(
        function (result) {
          
          if(pathology){
            //当用病理号搜索的时候 返回的是一个数组
            result = result[0]
          }
          // result.menopauseEnd = $filter('date')(result.menopauseEnd, 'yyyy-MM-dd') //????

          sampleRegistration.pathCreateTime = result.pathCreateTime;//获取上一条记录传的时间参数

          // 获取申请号信息的时候判断  是科研的申请还是正常申请
          formatDataForForm( result)

        },
        function (err) {
          toastr.error(err, "操作失败！");
        }
      )
    };

    // 新建申请按钮 sampleRegistration.clear()
    sampleRegistration.clear = function () {
      // console.info($scope)
      sampleRegistration.applyType = null;
      sampleRegistration.serialNumber = null;
      $scope.autoFocus = !$scope.autoFocus; //auto-focus

      $scope.showOperation = true;
      $scope.hideSubmit = true;
      $scope.readonly = false;
      initForm()

    };

    //撤销登记
    sampleRegistration.cancel = function () {
      IhcService.post("/pathology/cancel/" + $scope.pathological.data.pathologyId).then(function (res) {
        toastr.success("操作成功！");
        $state.go("app.sampleRegistration", {}, {reload: true});
      }, function (err) {
        toastr.error(err.reason, "操作失败！");
      })

    };
    // 登记
    sampleRegistration.register = function () {
      $scope.autoFocus = !$scope.autoFocus; //auto-focus

      if (!$scope.pathological.data.id) {
        $scope.pathological.data.menopauseEnd = $scope.pathological.data.menopauseEnd && $scope.pathological.data.menopauseEnd.getTime()
        ApplicationService.createApplication($scope.pathological.data).then(
          function (result) { //成功
            console.log("病例申请表提交成功pathological submit success！", result);
            sampleRegistration.test_serialNumber = result.serialNumber;//测试用申请单号

            // toastr.success("病例申请表提交成功！",{autoDismiss:true,preventOpenDuplicates:false,progressBar:false,timeOut:2000});
            // result.menopauseEnd = $filter('date')(result.menopauseEnd, 'yyyy-MM-dd');
            $scope.pathological.data = result; //将申请成功的数据赋值到表格中
            $scope.pathological.data.id = result.id; //登记前申请先获取id

            registerAndprint();

          },
          function (err) { //失败
            console.log("pathological submit error", err);
            toastr.error(err, "操作失败！");
            $scope.pathological.checkErr = err + "!";
          })
      } else if ($scope.pathological.data.id) {
        // alert('有id可以登记')
        registerAndprint()

      }

    };
    // 拒收
    sampleRegistration.reject = function () {
      if ($scope.pathological.data.status != 1) return;
      $scope.autoFocus = !$scope.autoFocus; //auto-focus
      var textareaMod = $uibModal.open({
        templateUrl: 'app/site/sampleRegistration/modal/textareaModal.html',
        controller: 'TextareaModalController',
        controllerAs: 'textMod',
        // backdrop: false,
        resolve: {
          resolveData: {
            modalTitle: "拒收原因：",
            selectTitle: "拒收原因：",
            textareaTtitle: "拒收说明：",
          }
        }
      });
      textareaMod.result.then(function (reason) {
        PathologyService.rejectPathology($scope.pathological.data.id, reason).then(
          function (result) {
            toastr.success("操作成功！");
            $state.go("app.sampleRegistration", {}, {reload: true});
          },
          function (err) {
            toastr.error(err, "操作失败")
          }
        )
      })

    };

    function registerAndprint() {
      var registerData = {
        applicationId: $scope.pathological.data.id,
        inspectCategory: sampleRegistration.inspectCategory,
        assignGrossing: sampleRegistration.assignGrossing,
        samples: $scope.pathological.data.samples,
        id: $scope.pathological.data.pathologyId
      };

      PathologyService.registerPathology(registerData).then(
        function (result) {
          toastr.success("病例登记成功！");
          // console.log("病例登记成功！",result);

          sampleRegistration.test_pathNo = result.serialNumber;//测试用病理号

          // $scope.refresh();//刷新申请单部分页面
          $timeout(function () {
            $state.go("app.sampleRegistration", {}, {reload: true});
          }, 70);

          if (sampleRegistration.printType == 2) {
            var data = {
              pathNo: result.serialNumber,
              patientName: result.patientName,
              frozenNumber: result.number
            }
            printSerialNumber(data) //打印病理号

            $timeout(function () {
              $state.go("app.sampleRegistration", {}, {reload: true});
            }, 70);
          }

          /*//打印申请单跟样本标签 无效
           printerService.printLabel(result.serialNumber, function() {
           printerService.printLabel(result.samples);
           $state.go("app.sampleRegistration", {}, { reload: true });
           })*/

        },
        function (err) { //失败
          console.log("pathological submit error", err);
          toastr.error(err, "操作失败！");
          $scope.pathological.checkErr = err + "!";
        }
      )
    }

    function initForm() { //初始化表单内容
      $scope.pathological.data = {
        samples: [{name: "", category: 1}],
        maritalStatus: 90,
        urgentFlag: false,
        gynaecology: false,
        // departments: $rootScope.user.departments,
        // doctor: $rootScope.user.firstName,
        // doctorTel: $rootScope.user.phone
      };
    }

    //打印图表函数
    // function printTable(serialNumber,cb) {
    //   $scope.showOperation = false;
    //   if($('.modal:first-child')[0]){
    //     $('.modal:first-child')[0].scrollTop = 0;
    //   }else{
    //     window.scrollTop=0;
    //   }
    //   $timeout(function () {
    //     //通过css打印
    //     //var strStyleCSS="<link type='text/css' rel='stylesheet' href= '"+$('link:last-child')[0].href+"'>";
    //     //strStyleCSS+="<style>" +
    //     //  "#pathologicalForm table textarea {resize: none;width: 100%;height: 100%;vertical-align: middle;overflow-y: visible;}" +
    //     //  "</style>"
    //     //console.log(strStyleCSS)
    //
    //     //通过转canvas转img打印
    //
    //     var width = $('#pathologicalForm').width();
    //     var height = $('#pathologicalForm').height();
    //     //console.log(width,height)
    //
    //     html2canvas($('#pathologicalForm'), {
    //       width: width * 2,
    //       height: height * 2,
    //       background: "rgba(255,255,255,1)",
    //       onrendered: function (canvas) {
    //         var imgSrc = canvas.toDataURL("image/jpg");
    //         //console.log(imgSrc);
    //         //applicationMod.img=imgSrc;
    //         var img = "<img width='800' src='" + canvas.toDataURL("image/png") + "'>"
    //         //console.log(img)
    //         printerService.printTable(img, serialNumber);
    //         cb && cb();
    //       }
    //     });
    //   })
    // }

  } //end ApplyPathologicalController
})();
