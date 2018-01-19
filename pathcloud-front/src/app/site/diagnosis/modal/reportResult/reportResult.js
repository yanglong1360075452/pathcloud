//查看历史诊断弹窗
(function () {
  'use strict';
  angular
  .module('pathcloud')
  .controller('reportResultController', reportResultController);
  
  /** @ngInject */
  function reportResultController($scope, $rootScope, $uibModal, $uibModalInstance, printerService, toastr, DiagnosisService, resolveData, pdfService, $timeout, toolService) {
    var reportResult = this;
  
    toolService.convertImgToSamllBase64(resolveData.checkPhotos, 300, 200).then(function (imgDatas) {
      $scope.reportInfo.checkPhotos = imgDatas;
      console.info($scope.reportInfo);
    });
    
    $scope.reportInfo = resolveData;
    console.info($scope.reportInfo);
    
    // $timeout(function () {
    //   $(".diagnosis").find(".img-group").remove().appendTo(".micro-img")
    // }, 0);
    //
    // console.info("病理诊断dom对象：",$(resolveData.diagnosis.resultDom).remove(".img-container"))
    // console.info("病理诊断dom对象：",$(resolveData.diagnosis.resultDom).find(".img-container").appendTo(".modal-body"))
    // console.info("病理诊断dom对象：",$(resolveData.diagnosis.resultDom).find(".img-container").append(".report-template"))
    
    //确定
    DiagnosisService.getReportTemplate().then(function (res) {
      // console.clear();
      // console.info(res)
      $scope.reportTemplate = res[0];
      // console.info($scope.reportTemplate)
      // templateId=res[0].id;
    }); //进入页面自动获取模板内容
    
    DiagnosisService.getReportInfo(resolveData.pathId, {specialApplyId: resolveData.specialApplyId}).then(function (res) {
      // console.info(res)
      reportResult.reportData = res; //【页面报告内容】
      reportResult.reportData.reportTime = Date.now();
    }, function (err) {
      reportResult.disableBtn = true;
    });
    
    reportResult.ok = function () {
      
      // console.info(resolveData, resolveData.type === 4, !resolveData.diagnosis.specialResult)
      if (resolveData.type === 4 || resolveData.outConsult) {
        if (!resolveData.diagnosis.specialResult && !resolveData.diagnosis.outConsultResult) {
          toastr.error("会诊内容不能为空");
          return false
        }
        
      } else {
        // 会诊的发报告的时候 可以不传诊断内容
        if (!resolveData.diagnosis.resultDom) {
          toastr.error("诊断内容不能为空");
          return false
        }
      }
      
      if (resolveData.markers && resolveData.markers.length) {
        //判断 当是特检申请的时候 特检标记物一定要全选 才能发报告  不是常规 冰冻 会诊
        var markers = resolveData.diagnosis.specialResult.split("；");
        if (markers.length !== resolveData.markers.length) {
          toastr.error("请填写所有特检结果");
          return false
        }
      }
  
  
      // todo 冰冻诊断符合度coincidence rate 仅用于当前病理号有冰冻诊断的情况下
      if(reportResult.reportData.frozenResults && reportResult.reportData.frozenResults.length && resolveData.type === 0 ){
        var checkCoincidenceModal = $uibModal.open({
          templateUrl: 'app/site/diagnosis/modal/reportResult/modal/coincidenceRate.html',
          size:'lg',
          // backdrop: false,
          controller: 'CoincidenceModalController',
          controllerAs: 'CoincidenceMod',
          resolve: {
            resolveData: function () {
              // console.info($scope.reportInfo.diagnosis.resultDom + $scope.reportInfo.diagnosis.specialResult||"" + $scope.reportInfo.diagnosis.outConsultResult||"")
             
              if(!$scope.reportInfo.diagnosis.outConsultResult){
                $scope.reportInfo.diagnosis.outConsultResult = ""
              }
              if(!$scope.reportInfo.diagnosis.specialResult){
                $scope.reportInfo.diagnosis.specialResult = ""
              }
              // debugger
              return {
                diagnoseResult: $scope.reportInfo.diagnosis.resultDom + $scope.reportInfo.diagnosis.specialResult + $scope.reportInfo.diagnosis.outConsultResult,
                frozenResults: reportResult.reportData.frozenResults || []
              };
            }
          }
        });
        checkCoincidenceModal.result.then(function (frozenCoincidence) {
  
          resolveData.diagnosis.coincidence = frozenCoincidence; //传到诊断符合率到后端
          confirm()
        })
      }else {
        confirm()
      }
      
      
    };
    
    /*
    * https://segmentfault.com/a/1190000009211079
    * 参考
    * */
    /*reportResult.demo = function() {
      html2canvas($('#reportResult .modal-body'), {
        onrendered:function(canvas) {
      
          var contentWidth = canvas.width;
          var contentHeight = canvas.height;
      
          //一页pdf显示html页面生成的canvas高度;
          var pageHeight = contentWidth / 595.28 * 841.89;
          //未生成pdf的html页面高度
          var leftHeight = contentHeight;
          //pdf页面偏移
          var position = 0;
          //a4纸的尺寸[595.28,841.89]，html页面生成的canvas在pdf中图片的宽高
          var imgWidth = 555.28;
          var imgHeight = 555.28/contentWidth * contentHeight;
      
          var pageData = canvas.toDataURL('image/jpeg', 1.0);
      
          var pdf = new jsPDF('', 'pt', 'a4');
          //有两个高度需要区分，一个是html页面的实际高度，和生成pdf的页面高度(841.89)
          //当内容未超过pdf一页显示的范围，无需分页
          if (leftHeight < pageHeight) {
            pdf.addImage(pageData, 'JPEG', 20, 0, imgWidth, imgHeight );
          } else {
            while(leftHeight > 0) {
              pdf.addImage(pageData, 'JPEG', 20, position, imgWidth, imgHeight)
              leftHeight -= pageHeight;
              position -= 841.89;
              //避免添加空白页
              if(leftHeight > 0) {
                pdf.addPage();
              }
            }
          }
      
          pdf.save('content.pdf');
        }
      })
    }*/
    
    
    
    function confirm() {
  
      resolveData.diagnosis.again = true; //冰冻报告发完后要不要走常规流程
      // 跟医院有关 可能有的医院冰冻后还走常规流程 报告后病理就是待取材状态了
      // 传true的时候就发完冰冻报告后还是要走常规流程
      
      toolService.getHtmlImgByTagName('#reportResult .report-template', 2, function (imgData, w, h) {
        resolveData.diagnosis.reportPic = JSON.stringify({html: $('#reportResult .modal-body').html()});
        
        resolveData.diagnosis.specialApplyId = resolveData.specialApplyId;// 特殊申请的id
  
        /*// 未生成PDF
        $timeout(function () {
          debugger
          pdfService.buildByHtml(imgData, w, h, reportResult.reportData.pathNo + "诊断报告", resolveData.pathId); //保存报告的PDF

          toastr.success('发送报告成功！');
          $uibModalInstance.close();
        }, 100)
        return*/
        
        DiagnosisService.sendReport(resolveData.pathId, resolveData.diagnosis).then(function () {
          
          $timeout(function () {
            
            pdfService.buildByHtml(imgData, w, h, reportResult.reportData.pathNo + "诊断报告", resolveData.pathId); //保存报告的PDF
            
            toastr.success('发送报告成功！');
            $uibModalInstance.close();
          }, 100)
          
        }, function (err) {
          toastr.error(err)
        })
      });
      
    }
    
    //取消
    reportResult.cancel = function () {
      $uibModalInstance.dismiss();
    };
    
  }
})();
