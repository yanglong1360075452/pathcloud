/**
 * Created by zhanming on 16/4/12.
 * 父级作用域
 */
(function() {
    'use strict';

    angular
        .module('pathcloud')
        .directive('applicationResearch', applicationResearch);

    /** @ngInject */
    function applicationResearch() {
        var directive = {
            restrict: 'E',
            scope: false, //其默认情况下就是false 表示继承关系
            templateUrl: 'app/components/pathologicalForm/applicationResearch/applicationResearch.html',
            //replace:true,
            //link: function (scope, elem, attrs) {
            //  console.log(elem);
            //},
            controller: ApplicationResearchlController,
            controllerAs: "pathologicalFreeze"
        };

        return directive;

        /** @ngInject */

        function ApplicationResearchlController($scope, $rootScope, SystemSettingService, ApplicationService, $timeout, $state, toastr, MaterialService, printerService,UserService,T,IhcService) {
            // console.log("$scope.data:=====",$scope.data)
            $scope.datePopup = false;
            var pathologicalFreeze = this;

          function initProfile() {
            UserService.getUser($rootScope.user.id).then(function (result) {
              // pathologicalFreeze.departmentsDesc = result.departmentsDesc;


              pathologicalFreeze.data = {};
              // Object.assign(pathologicalFreeze.data,{
              //
              // })
              pathologicalFreeze.departmentsDesc = result.departmentsDesc||"";
              pathologicalFreeze.data.departments = result.departments;
              pathologicalFreeze.data.faculty = result.faculty;
              pathologicalFreeze.data.applicant = result.firstName;
              pathologicalFreeze.data.phone = result.phone
              pathologicalFreeze.data.specialty = result.specialty
              pathologicalFreeze.data.studentNumber = result.studentNumber
              pathologicalFreeze.data.taskType = result.taskType
              pathologicalFreeze.data.taskName = result.taskName
              pathologicalFreeze.data.tutor = result.tutor
              pathologicalFreeze.data.identity = result.identity
              pathologicalFreeze.data.unit = result.unit
              pathologicalFreeze.data.wno = result.wno

              pathologicalFreeze.data.funds = result.projectCode
              // pathologicalFreeze.data.applicant = $rootScope.user.firstName;
              pathologicalFreeze.data.samples = [{ name: "", category: 1 }];
              pathologicalFreeze.data.researchType = 1;


            })
          }


          if(!$scope.readonly){
            initProfile();
          }



            // pathologicalFreeze.data = {
            //   applicant: $rootScope.user.firstName,
            //   phone: $rootScope.user.phone,
            //   samples: [{ name: "", category: 1 }],
            //   researchType: 1, //默认送检类别 常规
            // };

            pathologicalFreeze.getSelectName = function(array, value) {
                  if (value != undefined) {
                      // console.log(array, value)
                      var data = _.find(array, function(o) {
                          if (o.value !== undefined)
                              return o.value === value
                          else
                              return o.code === value
                      });

                      if (data) {
                          return data.name === '请选择' ? "" : data.name
                      }
                  }
                  return ""
                }
                // "sex": 1,#性别 0-未知 1-男  2-女
                // "maritalStatus": 10,#婚否 10-未婚  20-已婚  30-丧偶  40-离婚  90-未知


            pathologicalFreeze.taskTypeList = [{ name: T.T("请选择"), value: undefined }, { name: "国家自然科学基金", value: 0 }, { name: "国家科技部课题", value: 1 }, { name: "省部级计划课题", value: 2 }, { name: "横向课题", value: 3 }, { name: "其他", value: 4 }, ];
            pathologicalFreeze.identityList = [{ name: T.T("请选择"), value: undefined }, { name: "本科生", value: 0 }, { name: "硕士研究生", value: 1 }, { name: "博士研究生", value: 2 }, { name: "博士后", value: 3 }, { name: "临床医生", value: 4 }, { name: "研究人员", value: 5 },{ name: "技术人员", value: 6 },{ name: "其他", value: 7 }, ];


            // pathologicalFreeze.departmentList =  $rootScope.departments;
            // pathologicalFreeze.getDepartments = function(filter) {
            //         MaterialService.getDepartments(filter).then(function(data) {
            //             pathologicalFreeze.departmentList = data;
            //             //pathologicalFreeze.departmentList.unshift({ name: "请选择", value: undefined });
            //         });
            //     } //1.3 获取送检科室列表
            // pathologicalFreeze.getDepartments();

            // pathologicalFreeze.categoryList = [{ name: T.T("大样本"), code: 1 }, { name: T.T("小样本"), code: 2 }];
            IhcService.get("/systemSetting/sampleCategory").then(function (res) { //样本类别改成获取后台
              pathologicalFreeze.categoryList = res;
            });
            pathologicalFreeze.researchTypeList = [{ name: T.T("常规染色"), value: 1 }, { name: T.T("冰冻预约"), value: 2 }];
            // pathologicalFreeze.data.researchType=1;




            /*
              冰冻部分 冰冻预约
            */

            pathologicalFreeze.myBookTime = []; //初始化 时间选择select
            //获取已经预定过的日期
            pathologicalFreeze.getFreezeBookedList = function() {
                var pickTime = pathologicalFreeze.freezeStartTime.getTime(); //通过日期选择选中的日期
                pathologicalFreeze.startTime = null; // 换日期后清空开始结束时间
                pathologicalFreeze.endTime = null;
                pathologicalFreeze.myBookTime = [];

                ApplicationService.getFreezeBookedList({
                    timeStart: pickTime,
                    timeEnd: pickTime + 86400000,
                }).then(function(res) {
                    pathologicalFreeze.orderList = res;

                    // if(!pathologicalFreeze.activeDate){
                    pathologicalFreeze.activeDate = pathologicalFreeze.orderList[0]; //默认是返回结果中的第一条数据
                    // }
                },function (err) {
                  toastr.error(err);
                  pathologicalFreeze.orderList=[];
                })
            };


            pathologicalFreeze.freeze = 0; // 默认常规显示 不显示冰冻预约
                // 冰冻预约 时间选择select
            pathologicalFreeze.timeList = [
                { name: "8:00", value: 16 }, { name: "8:30", value: 17 },
                { name: "9:00", value: 18 }, { name: "9:30", value: 19 },
                { name: "10:00", value: 20 }, { name: "10:30", value: 21 },
                { name: "11:00", value: 22 }, { name: "11:30", value: 23 },
                { name: "12:00", value: 24 }, { name: "12:30", value: 25 },
                { name: "13:00", value: 26 }, { name: "13:30", value: 27 },
                { name: "14:00", value: 28 }, { name: "14:30", value: 29 },
                { name: "15:00", value: 30 }, { name: "15:30", value: 31 },
                { name: "16:00", value: 32 }, { name: "16:30", value: 33 },
                { name: "17:00", value: 34 }, { name: "17:30", value: 35 },
                { name: "18:00", value: 36 }
            ];
            $scope.freezeDateOptions = {
                // dateDisabled: disabledDate,
                minDate: new Date()
            };
            // 冰冻预约表头
            pathologicalFreeze.orderHeaderList = ["8:00", "9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"];


            //防止选出多个日期  //TODO 让它直接在当前数组对象里处理 最后提交的时候取出需要的数据
            pathologicalFreeze.choseDate = function(item) {
                pathologicalFreeze.activeDate = item; //选择现在是在处理哪一条数据
                pathologicalFreeze.startTime = null;
                pathologicalFreeze.endTime = null;
            };

            //计算开始结束时间段  //todo 需要判断是哪台脱水机
            pathologicalFreeze.selectTime = function(startTime, endTime) {
                pathologicalFreeze.myBookTime = []; //自己选中的时间

                var start = parseInt(startTime); //开始的标识
                var end = parseInt(endTime);  ////结束的标识
                //把选中的时间 显示到表格
                if (!start && end) return false;

                if (end - start > 1) {
                    var len = end - start;
                    for (var i = 0; i < len; i++) {
                        pathologicalFreeze.myBookTime[i] = start + i;
                    }
                } else if (end - start === 1) {
                    pathologicalFreeze.myBookTime = [start];
                } else {
                    return false;
                }

                // console.info("我要传的时间数组",pathologicalFreeze.myBookTime);

                pathologicalFreeze.activeDate.cells = angular.copy(pathologicalFreeze.myBookTime);//给页面上每台脱水机数据绑定cells  提交的时候过滤掉 pathologicalFreeze.orderList 不需要的属性
                getBookedTime(); //把我要传的时间数组转换到表格里面显示
                // console.info("获取的冰冻机时间数组",pathologicalFreeze.orderList);

            };
            // // 把我要传的时间数组转换到表格里面显示
            function getBookedTime() {
                if (!pathologicalFreeze.activeDate) { return false; }

              pathologicalFreeze.activeDate.freezeDateError = false; //提示选择的日期是否有效
                console.info("把我要传的时间数组转换到表格里面显示", pathologicalFreeze.myBookTime);
                // 把我要传的时间数组转换到表格里面显示


                for (var i = 0; i < pathologicalFreeze.activeDate.bookingDto.length; i++) {
                    var time = pathologicalFreeze.activeDate.bookingDto[i]; //相当于遍历每一个小格子
                    time.checked = false; //没选的需要改成false

                    angular.forEach(pathologicalFreeze.myBookTime, function(data, index, array) { //遍历 我要传的时间数组 pathologicalFreeze.myBookTime 显示到表格上
                        if (time.timeflag === data) {
                            time.checked = true; //跟我选的时间 pathologicalFreeze.myBookTime check
                            if (time.booked) {
                                // pathologicalFreeze.
                              pathologicalFreeze.activeDate.freezeDateError = true; //日期选择无效时 按钮不能点击
                                toastr.error("您选择的日期范围无效请重新选择");
                                return false;
                            }
                        }
                    })
                }

                console.info("表格里面显示",pathologicalFreeze.activeDate);
                console.info("表格里面所有",pathologicalFreeze.orderList);
            }



            /*
             *“添加样本记录”
             */
            var sampleObj = {}; //暂存本行的样本数据
            pathologicalFreeze.isShow = "true";

            /*
             *方法二：“添加样本记录”
             */
            pathologicalFreeze.addSample = function() {
                pathologicalFreeze.data.samples.push({ name: "", category: 1 });
                console.log("增加一条样本记录后，样本数据内容为 ", pathologicalFreeze.data.samples);
            };

            /*
             *“删除样本记录”
             */
            pathologicalFreeze.delSample = function(index) {
                pathologicalFreeze.data.samples.splice(index, 1); //splice(index,num)删除指定位置元素 以及 删除个数
                console.log("删除一条样本记录后，样本数据内容为 ", pathologicalFreeze.data.samples);
            };

            /*
             *“提交按钮”
             */
            pathologicalFreeze.submit = function() {

                var freezeDateError = false; //局部定义 判断是否冰冻预约时间错误

                //判断是否选了冰冻预约
                if (pathologicalFreeze.data.researchType === 2) {

                    pathologicalFreeze.data.books=[]; //存放保存时要传的冰冻预约数据

                    if (pathologicalFreeze.myBookTime.length && pathologicalFreeze.freezeStartTime) {

                        // pathologicalFreeze.data.cells = []; //时间选择select
                        // pathologicalFreeze.data.freezeStartTime = pathologicalFreeze.freezeStartTime.getTime(); // 预约日期 todo 先传一个多余的字段

                        angular.forEach(pathologicalFreeze.orderList,function (item,index,arr) {

                          if(freezeDateError) {
                            return false;//冰冻预约时间选择错误
                          }

                          if(item.freezeDateError) {
                            freezeDateError = true;
                            return false;//冰冻预约时间选择错误
                          }

                          if(item.cells&&item.cells.length){ //防止传空
                            pathologicalFreeze.data.books.push(
                              {
                                freezeStartTime:item.date,
                                instrumentId:item.instrumentId,
                                cells:item.cells
                              }
                            );
                          }

                        });

                      // console.info(pathologicalFreeze.data); return false;

                    } else {
                        toastr.error("请选择冰冻预约时间");
                        return false;
                    }
                }

                // console.info("提交按钮")
                if(freezeDateError){
                  toastr.error("您选择的日期范围无效请重新选择");
                  return false;
                }
                ApplicationService.createApplicationResearch(pathologicalFreeze.data).then(function(res) {
                    toastr.success("申请成功");

                    // 自动化测试用 显示病理号 申请单号
                  $rootScope.test_serialNumber = res.serialNumber; //申请号


                    // todo 页面显示 申请成功的时间
                    pathologicalFreeze.data.books = res.books;

                    $timeout(function() {
                        printerService.printPathologicalForm("#pathologicalFreeze_form", res.serialNumber, function() {
                            printerService.printLabel(res.samples);
                            // $state.go("app.applicationCreate", { type: 0 }, { reload: true });
                            //打印科研表单后刷新页面
                            $scope.refresh(0)

                        });

                    }, 100)


                }, function(err) {
                    toastr.error(err);
                })

            };

            //查看申请弹窗
            if ($scope.applicationData) { pathologicalFreeze.data = $scope.applicationData; } //查看申请弹窗 数据赋值  放到最后防止值被 覆盖
            console.log("---------application directive-------", $scope.applicationData)


        }
    }
})();




// pathologicalFreeze.orderList=[
//   {
//     date:"",
//     bookingDto:[
//       // {timeflag:0,checked:false,booked:false,person:{}}, //0:00  0:30   约定 一天24个小时 0点
//       // {index:1,checked:false,booked:false,person:{}}, //0:30  01:00
//       // {index:2,checked:false,booked:false},
//       // {index:3,checked:false,booked:false},
//       // {index:4,checked:false,booked:false}, // 2:00
//       // {index:5,checked:false,booked:false},
//       // {index:6,checked:false,booked:false},
//       // {index:7,checked:false,booked:false},
//       // {index:8,checked:false,booked:false}, //4:00
//       // {index:9,checked:false,booked:false},
//       // {index:10,checked:false,booked:false},
//       // {index:11,checked:false,booked:false},
//       // {index:12,checked:false,booked:false},
//       // {index:13,checked:false,booked:false},
//       // {index:14,checked:false,booked:false},
//       // {index:15,checked:false,booked:false},
//       // {index:16,checked:false,booked:false}, //8:00
//       // {index:17,checked:false,booked:false},
//       // {index:18,checked:false,booked:false},
//       // {index:19,checked:false,booked:false},
//       // {index:20,checked:false,booked:false},
//       // {index:21,checked:false,booked:false},
//       // {index:22,checked:false,booked:false},
//       // {index:23,checked:false,booked:false},
//       // {index:24,checked:false,booked:false},
//       // {index:25,checked:false,booked:false},
//       // {index:26,checked:false,booked:false},
//       // {index:27,checked:false,booked:false},
//       // {index:28,checked:false,booked:false},
//       // {index:29,checked:false,booked:false},
//       // {index:30,checked:false,booked:false},
//       // {index:31,checked:false,booked:false},
//       // {index:32,checked:false,booked:false},
//       // {index:33,checked:false,booked:false},
//       // {index:34,checked:false,booked:false},
//       // {index:35,checked:false,booked:false},  //17:30 ~ 18:00
//       // {index:36,checked:false,booked:false},  //18:00
//       // {index:22,checked:false,booked:false},
//     ],

//   },
// ];
