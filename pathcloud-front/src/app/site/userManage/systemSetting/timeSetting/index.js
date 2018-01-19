/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('TimeTemplateController', TimeTemplateController);

  /** @ngInject */
  function TimeTemplateController($scope,$state,toastr,SystemSettingService){
    var TimeTpl=this;

    function init() {
      /* 时间范围*/
      SystemSettingService.getAlarmTime("queryTimeRange").then(function (res) { //get
        TimeTpl.timeRangeList=res||[];
        angular.forEach(res,function (item,index,arr) {
          if(item.checked){
            TimeTpl.checkedTime = item.code
          }
        })
      });

      //报警等待时间设置
      /*TimeTpl.operatioinList={
        grossing:{
          label:"待取材",
          url:"prepareGrossingAlarm",
          model:"",
          init:function (url) {
            // return 110;
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.grossingTime = res;
              TimeTpl.operatioinList.grossing.model = res;
            })
          },
        },
        grossingConfirm:{
          label:"待取材确认",
          url:"prepareGrossingAlarm",
        }
      };*/

      TimeTpl.operatioinList=[
        {
          label:"待取材",
          // modelStr:"grossingTime",
          model:"",
          url:"prepareGrossingAlarm",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
                // TimeTpl.grossingTime = res;
              TimeTpl.operatioinList[0].model=res;

            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        },
        {
          label:"待取材确认",
          model:"",
          url:"prepareGrossingConfirmAlarm",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.operatioinList[1].model=res;
            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        },{
          label:"待脱水",
          model:"",
          url:"prepareDehydrateAlarm",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.operatioinList[2].model=res;
            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        },{
          label:"待包埋",
          model:"",
          url:"prepareEmbedAlarm",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.operatioinList[3].model=res;
            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        },{
          label:"待切片",
          model:"",
          url:"prepareSectionAlarm",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.operatioinList[4].model=res;
            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        },{
          label:"待染色",
          model:"",
          url:"prepareDyeAlarm",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.operatioinList[5].model=res;
            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        },{
          label:"待制片确认",
          model:"",
          url:"prepareConfirmAlarm",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.operatioinList[6].model=res;
            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        }

      ];

      TimeTpl.reportOperatioinList=[
        {
          label:"常规染色",
          model:"",
          url:"reportDeadline",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.reportOperatioinList[0].model=res;
            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        },
        {
          label:"冰冻切片",
          model:"",
          url:"freezeReportDeadline",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.reportOperatioinList[1].model=res;
            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        },{
          label:"免疫组化",
          model:"",
          url:"ihcReportDeadline",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.reportOperatioinList[2].model=res;
            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        },{
          label:"特殊染色",
          model:"",
          url:"specialDyeReportDeadline",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.reportOperatioinList[3].model=res;
            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        },{
          label:"脱钙标本",
          model:"",
          url:"decalcifyDeadline",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.reportOperatioinList[4].model=res;
            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        },{
          label:"疑难病例",
          model:"",
          url:"difficultDeadline",
          init:function (url) {
            SystemSettingService.getAlarmTime(url).then(function (res) {
              TimeTpl.reportOperatioinList[5].model=res;
            })
          },
          put:function (url,code) {
            putAlarmTime(url,code)
          }
        }
      ];
    }

    init();

    TimeTpl.putTimeRange=function (code) {
      SystemSettingService.putAlarmTime("queryTimeRange",code).then(function (res) {
        toastr.success("修改成功")
      })
    };
    function putAlarmTime(url,code) {
      SystemSettingService.putAlarmTime(url,code).then(function (res) {
        toastr.success("修改成功");
      },function (err) {
        toastr.error("修改失败",err);
      });
    }

  }
})();

