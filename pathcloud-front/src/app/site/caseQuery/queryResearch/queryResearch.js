/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
    'use strict';

    angular
        .module('pathcloud')
        .controller('researchQueryController', researchQueryController);

    /** @ngInject */
    function researchQueryController($scope, $rootScope, $state, DiagnosisService, MaterialService, toolService, PathologyService, QueryService, $timeout, printerService,videoService,SystemSettingService) {
        var researchQuery = this;

        //搜索结果表单显示部分
        researchQuery.tableHeaders = [
            { name: "病理号",order:1 }, { name: "申请人" },{name:"状态"}, { name: "联系电话" }, { name: "申请者身份" }, { name: "导师" }, { name: "科室" }, { name: "课题类型" }, { name: "项目代码" }, { name: "申请日期",order:10 }
        ];


        // 查询功能部分
        function init() {

            // 查询条件
            researchQuery.filter = {
                length:10,
                page:1,
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
            researchQuery.fieldTypeList = [{ code: 1, name: "病理号" }, { code: 2, name: "病人ID" }, { code: 3, name: "住院号" }, { code: 4, name: "病人姓名" }, { code: 5, name: "诊断内容" }, /*{code:6,name:"诊断项目"},*/ { code: 7, name: "巨检描述" }, { code: 8, name: "显微所见" }, ]
            researchQuery.pathStatus = [{ code: 24, name: "已发报告" }, { code: 0, name: "未发报告" }];

            SystemSettingService.getSpecialDyeList().then(function(res){
              researchQuery.specialDyeList=res;
            });//特染类别获取

            MaterialService.getDepartments().then(function(res) {
                //console.log("获取送检科室列表数据：-----",result);
                researchQuery.departmentsList = res;
            });
            // 获取检查类别列表
            PathologyService.inspectCategoryList().then(function(res) {
                researchQuery.inspectCategoryList = res;
            });
                // 2.2 获取送检医生列表
            QueryService.inspectDoctorList().then(function(res) {
                researchQuery.inspectDoctorList = res;
            });
          // 接收日期
          $scope.dateOptions1 = {
            dateDisabled: disabled1,
            maxDate: new Date()
          };
          $scope.dateOptions2 = {
            dateDisabled: disabled2,
            maxDate: new Date()
          };

          function disabled1(data) {
            if (!researchQuery.receiveEndTime) return;
            var date = data.date,
              mode = data.mode;

            return mode === 'day' && (date.getTime() > researchQuery.receiveEndTime);
          }

          function disabled2(data) {
            if (!researchQuery.receiveStartTime) return;

            var date = data.date,
              mode = data.mode;

            return mode === 'day' && (date.getTime() < researchQuery.receiveStartTime);
            // return mode === 'day' && (date.getDay() === 0 || date.getDay() === 6);
          }

        };
        init();

        researchQuery.getData = function () {
          QueryService.queryResearch(researchQuery.filter).then(function(res) {
            researchQuery.queryResultList = res.data;
            researchQuery.total = res.total;
            researchQuery.filter.page = res.page;
            console.info(researchQuery.filter);
            researchQuery.exportFilter=angular.copy(researchQuery.filter)
          })
        };
        //排序
        researchQuery.getSortList = function (item) {

          if(!item.order) return;

          item.sort==='asc'?item.sort='desc':item.sort='asc';
          researchQuery.filter.sort = item.sort;
          researchQuery.filter.order = item.order;
          researchQuery.getData();
        };
        // 搜索
        researchQuery.search = function() {

          if(!researchQuery.filter.filter){
            researchQuery.queryResultList={};
            return;
          }

          researchQuery.getData();

        };

        // 筛选查询开始
        researchQuery.query = function() {
          // console.info("筛选")
          if (researchQuery.receiveStartTime && researchQuery.receiveEndTime) {

            researchQuery.filter.applyTimeStart  = new Date(researchQuery.receiveStartTime).getTime();
            researchQuery.filter.applyTimeEnd  = new Date(researchQuery.receiveEndTime).getTime() + 86400000;
            delete researchQuery.filter.filter;
            researchQuery.getData()

          } else {
            toolService.getTipResult({ modalTitle: "提示", modalContent: "请选择一个日期范围!", size: "sm" }).then(function() {

            });
          }
        };
        // 清除按钮
        researchQuery.clear = function() {
            $state.go('app.researchQuery', {}, { reload: true })
        };


        //导出病例信息表格

        researchQuery.export = function() {
          console.info("导出问题测试",researchQuery.queryResultList)
            if (researchQuery.queryResultList&&researchQuery.queryResultList.length) {
              toolService.export(researchQuery.exportFilter,"/query/research/export")
            };

        };


        //表格显示部分开始 从查询结果选择一条数据
        var activeOne;
        researchQuery.getOne = function(item) {

            researchQuery.applicationTabActive = false; //选中申请信息tab ng-if 起效的条件

            $rootScope.activeQueryOne = researchQuery.activeOne = activeOne = item;
            console.log("researchQuery.activeTab===",researchQuery.activeTab);
            // researchQuery.reportTab()


            switch (researchQuery.activeTab) {

                case 1:
                    $timeout(function() {
                        researchQuery.applicationTab()
                    }, 0);
                    break;
                case 2:
                    researchQuery.sampleTab()
                    break;
                case 3:
                    researchQuery.specialDyeTab()
                    break;
                case 4:
                    researchQuery.diagnosisTab()
                    break;
                default:
                    $timeout(function() {
                        researchQuery.applicationTab()
                    }, 0);

            }

        };


        // TAB切换部分开始
        researchQuery.stopPropagation=function (e) {
          e.stopPropagation();
        };
        // 病理报告 部分


        // 申请信息 部分
        researchQuery.applicationTab = function() {

            if (!activeOne) return;
            researchQuery.applicationTabActive = true; //选中申请信息tab

            // 病理申请表
            //   ApplicationService.getOneByPathologyNo(activeOne.serialNumber).then(function (res) {
            //     // $scope.pathological={};
            //     // $scope.pathological.data=res;
            //     // $scope.applicationData=res;
            //   })

        };
        // TAB 3 样本信息
        // 基本信息
        researchQuery.sampleTab = function() {
            $scope.sample = 1; //选择第一个子TAB
            if (!activeOne) return;
            QueryService.sampleBasicInfo(activeOne.id).then(function(res) {
                console.log(res)
                researchQuery.basicInfo = res;
            })
        };
        // 取材信息
        researchQuery.sampleMaterialInfo = function() {
            $scope.sample = 2; //选择第二个子TAB
            if (!activeOne) return;
            QueryService.sampleMaterialInfo(activeOne.id).then(function(res) {
                //console.log(res)
                researchQuery.materialInfo = res;
            })
        };
        // 制片信息
        researchQuery.sampleConfirmInfo = function() {
            $scope.applicationTabActive = false; //选中申请信息tab
            $scope.sample = 3; //选择第二个子TAB
            if (!activeOne) return;
            QueryService.sampleConfirmInfo(activeOne.id).then(function(res) {
                // console.log(res)
                researchQuery.confirmInfo = res;
            })
        };
        // 特染信息 specialDyeInfo
      researchQuery.specialDyeTab = function() {
          $scope.applicationTabActive = false; //选中申请信息tab
          $scope.sample = 4; //选择第三个子TAB
          if (!activeOne) return;
          researchQuery.specialDyeSelect(researchQuery.specialDyeList[0].code,0) //默认选择的tab
        };
      researchQuery.specialDyeSelect = function(specialDye,index){
        researchQuery.specialDyeIndex=index;
          QueryService.specialDyeInfo(activeOne.id,{specialDye:specialDye}).then(function(res) {
            console.log("获取特染信息",res)
            researchQuery.specialDyeInfo = res;
          })
        };

        // 诊断信息
        researchQuery.diagnosisTab = function() {
            $scope.applicationTabActive = false; //选中申请信息tab
            researchQuery.diagnosisInfo={}
            DiagnosisService.getDiagnosisInfo(activeOne.id).then(function(res) {
                //console.info("历史诊断信息", res)
                researchQuery.diagnosisInfo = res;
            })
        }

        // 其他检查


    }
})();


// 前端angular 导出excel
/*researchQuery.exportCsv=function () {
 if(!researchQuery.queryResultList.length){
 return;
 };
 // 病人ID	病理号	姓名	性别	年龄	住院号	送检科室	送检医生	送检医院	接收日期	报告日期
 var header = ['序号', '病人ID', '病理号', '姓名', '性别', '年龄','住院号','送检科室','送检医生','送检医院','接收日期','报告日期'];
 var data = [];
 for (var i = 0; i < researchQuery.queryResultList.length; i++) {
 var item = researchQuery.queryResultList[i];

 data.push([i + 1, item.hisId, item.serialNumber, item.patientName, $filter('sex')(item.sex),item.age,item.admissionNo||'无',item.departmentsDesc||'无',item.inspectDoctor.firstName,
 item.inspectHospital,$filter('date')(item.receiveTime, "yyyy-MM-dd HH:mm")||'',$filter('date')(item.reportTime, "yyyy-MM-dd HH:mm")||'']);
 }
 CsvService.download(header, data, '病例信息表');
 }*/
