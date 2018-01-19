(function() {
  'use strict';
  
  angular
  .module('pathcloud')
  .filter('formatTime', function () {
    return function (longTime) {
      if(longTime===null) return '';
      // console.info("秒 转化为 日+小时+分+秒",longTime)
      //秒 转化为 日+小时+分+秒
      var time = parseFloat(longTime);
      if (time != null && time != ""){
        if (time < 60) {
          var s = time;
          time = s + '秒';
        } else if (time > 60 && time < 3600) {
          var m = parseInt(time / 60);
          var s = parseInt(time % 60);
          time = m + "分钟" + s + "秒";
        } else if (time >= 3600 && time < 86400) {
          var h = parseInt(time / 3600);
          var m = parseInt(time % 3600 / 60);
          var s = parseInt(time % 3600 % 60 % 60);
          time = h + "小时" + m + "分钟" + s + "秒";
        } else if (time >= 86400) {
          var d = parseInt(time / 86400);
          var h = parseInt(time % 86400 / 3600);
          var m = parseInt(time % 86400 % 3600 / 60)
          var s = parseInt(time % 86400 % 3600 % 60 % 60);
          // time = d + '天' + h + "小时" + m + "分钟" + s + "秒";
          time = d + '天' + h + "小时" + m + "分钟";
        }
      }else{
        
        time="";
      }
      return time;
    }
  })
  //毫秒 转化为 日+小时+分+秒
  .filter('formatMsec', function () {
    return function (longTime) {
      
      var time = parseFloat(longTime)/1000;
      if (time != null && time != ""){
        if (time < 60) {
          var s = time;
          time = s + '秒';
        } else if (time > 60 && time < 3600) {
          var m = parseInt(time / 60);
          var s = parseInt(time % 60);
          time = m + "分钟" + s + "秒";
        } else if (time >= 3600 && time < 86400) {
          var h = parseInt(time / 3600);
          var m = parseInt(time % 3600 / 60);
          var s = parseInt(time % 3600 % 60 % 60);
          time = h + "小时" + m + "分钟" ;
          // time = h + "小时" + m + "分钟" + s + "秒";
        } else if (time >= 86400) {
          var d = parseInt(time / 86400);
          var h = parseInt(time % 86400 / 3600);
          var m = parseInt(time % 86400 % 3600 / 60)
          var s = parseInt(time % 86400 % 3600 % 60 % 60);
          time = d + '天' + h + "小时" + m + "分钟";
          // time = d + '天' + h + "小时" + m + "分钟" + s + "秒";
        }
      }else{
        // alert("no time ??")
        time="";
      }
      return time;
    }
  })
  
})();
