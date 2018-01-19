/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('StationSettingController', StationSettingController);

  /** @ngInject */
  function StationSettingController($scope,$state,toastr,IhcService){
    var Station=this;


    function init() {
      normalTrack();
      freezeTrack()

      /*
      * 冰冻工作站
      * */
      IhcService.get("/systemSetting/usingFrozen").then(function (result) {
        Station.usingFrozen = result;
      });

      IhcService.get("/systemSetting/printFrozen").then(function (result) {
        Station.printFrozen = result;
      });
      IhcService.get("/systemSetting/specialNumberPrint").then(function (result) {
        Station.printSpecialNumber = result;
      });
    }
    init();

    function normalTrack() {
      IhcService.get("/systemSetting/trackingList").then(function (res) {
        Station.trackingList = res
      })
    }
    function freezeTrack() {
      IhcService.get("/systemSetting/freezeTrackingList").then(function (res) {
        Station.freezeTrackingList = res
      })
    }

    Station.change = function () {
      //现在接口是传不追踪的就行 其它自动变成追踪的
      var codelist = [];
      angular.forEach(Station.trackingList,function (item,index) {
        if(!item.checked){
          codelist.push(item.code)
        }
      });
      var code = codelist.join(',')
      // console.info(codelist.join(','))
      IhcService.put("/systemSetting/trackingList",code).then(function (res) {
        toastr.success("修改成功");
        normalTrack()
      },function (err) {
        toastr.error("修改失败",err.reason)
      }).finally(function () {
        normalTrack()
      })
    };
    Station.changeFreeze = function () {
      //现在接口是传不追踪的就行 其它自动变成追踪的
      var codelist = [];
      angular.forEach(Station.freezeTrackingList,function (item,index) {
        if(!item.checked){
          codelist.push(item.code)
        }
      });

      var code = codelist.join(',')
      console.info(codelist.join(','))

      IhcService.put("/systemSetting/freezeTrackingList",code).then(function (res) {
        toastr.success("修改成功");
      },function (err) {
        toastr.error("修改失败",err.reason)
      }).finally(function () {
        freezeTrack()
      })
    };

    /*
    * 冰冻工作站
    * */
    Station.changeUsingFrozen = function (code) {
      IhcService.put("/systemSetting/usingFrozen/"+code).then(function (result) {
        toastr.success("修改成功");
        Station.usingFrozen = code;
      })
    };
    Station.changeFrozenPrint = function (code) {
      IhcService.put("/systemSetting/printFrozen/"+code).then(function (result) {
        toastr.success("修改成功");
        Station.printFrozen = code;
      })
    };

    Station.changeIhcPrint = function (code) {
      IhcService.put("/systemSetting/specialNumberPrint/"+code).then(function (result) {
        toastr.success("修改成功");
        Station.printSpecialNumber = code;
      })
    };

  }
})();

