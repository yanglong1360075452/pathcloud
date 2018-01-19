/**
 * Created by Administrator on 2016/12/5.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('allStatisticController', allStatisticController);

  /** @ngInject */
  function allStatisticController($state,toastr,$uibModal,apiUrl,StatisticService,$q,T ){
    var allStatistic=this;
    allStatistic.waitStyle = {
      width:'520px',
      height:'163px'
    };
    allStatistic.typeStyle = {
      width:'520px',
      height:'163px'
    };
    //饼状图指令参数
    allStatistic.lineConfig = {
      theme:'default',
      event: [{click:function(params){console.log(params);}}],
      dataLoaded:true
    };

    //"制片质量"
    allStatistic.getSlideData = function(start,end){
      var defer = $q.defer();
      //时间戳设置
      var filter = {
        startTime: start,
        endTime: end
      };
      var timeData = {
        yName:T.T('不合格率'),
        dataAxis:[T.T('取材'), T.T('脱水'), T.T('包埋'), T.T('切片'),T.T('染色'), T.T('封片')],
        rotate:-60
      };
      StatisticService.slideQuality(filter).then(
        function (result){
          var data;
          if(result.total){
            data = [
              (result.grossing/result.total).toFixed(2)*100, (result.dehydrate/result.total).toFixed(2)*100,
              (result.embedding/result.total).toFixed(2)*100,(result.sectioning/result.total).toFixed(2)*100,
              (result.staining/result.total).toFixed(2)*100,(result.sealing/result.total).toFixed(2)*100
            ];
          }else{
            data = [0,0,0,0,0,0];
          }
          timeData.data = data;
          defer.resolve(timeData);
        },function(err){
          defer.reject(err);
        }
      );
      //console.warn("data:",timeData);
      return defer.promise;
    };

    //"异常样本"
    allStatistic.getErrorData = function(start,end){
      var defer = $q.defer();
      //时间戳设置
      var filter = {
        startTime: start,
        endTime: end
      };
      StatisticService.error(filter).then(
        function (result){
          var data;
          if (result.total){
            data = [
              {value: result.error, name:T.T('异常率')},
              {value: result.total-result.error, name:T.T('正常率')}
            ];
          }else{
            data = [
              {value:0, name:T.T('异常率')},
              {value:100, name:T.T('正常率')}
            ];
          }
          defer.resolve(data);
        },function(err){
          defer.reject(err)
        }
      );
      return defer.promise;
    };

    //"重切率"
    allStatistic.getSectionData = function(start,end){
      var defer = $q.defer();
      //时间戳设置
      var filter = {
        startTime: start,
        endTime: end
      };
      StatisticService.reSection(filter).then(
        function (result){
          var data;
          if (result.total){
            data = [
              {value: result.reSection, name:T.T('重切率')},
              {value: result.total-result.reSection, name:T.T('正常率')}
            ];
          }else{
            data = [
              {value:0, name:T.T('重切率')},
              {value:100, name:T.T('正常率')}
            ];
          }
          defer.resolve(data);
        },function(err){
          defer.reject(err)
        }
      );
      return defer.promise;
    };

    //"重补取率"
    allStatistic.getGrossingData = function(start,end){
      var defer = $q.defer();
      //时间戳设置
      var filter = {
        startTime: start,
        endTime: end
      };
      StatisticService.reGrossing(filter).then(
        function (result){
          var data;
          if (result.total){
            data = [
              {value: result.reGrossing, name:T.T('重取率')},
              {value: result.total-result.reGrossing, name:T.T('正常率')}
            ];
          }else{
            data = [
              {value:0, name:T.T('重取率')},
              {value:100, name:T.T('正常率')}
            ];
          }
          defer.resolve(data);
        },function(err){
          defer.reject(err)
        }
      );
      return defer.promise;
    };

    //"平均等待时间"
    allStatistic.getWaitTimeData = function(){
      var defer = $q.defer();
      var timeData = {//等待时间数据
        yName:T.T('平均等待时间（h）'),
        dataAxis:[T.T('登记'),T.T('取材'),T.T('取材确认'),T.T('脱水'),T.T('包埋'),T.T('切片'),T.T('染色'),T.T('制片确认'),T.T('诊断'),T.T('报告')],
        rotate:-50
      };
      StatisticService.waitTime().then(
        function (result){
          var data;
          if (result){
            data = [
              Math.round(result.registerTime/3600),Math.round(result.grossingTime/3600),Math.round(result.grossingConfirmTime/3600),Math.round(result.dehydrateTime/3600),Math.round(result.embedTime/3600),
              Math.round(result.sectionTime/3600),Math.round(result.dyeTime/3600),Math.round(result.confirmTime/3600),Math.round(result.diagnoseTime/3600),Math.round(result.reportTime/3600)
            ];
          }else{
            data = [0,0,0,0,0,0,0,0,0,0];
          }
          timeData.data = data;
          defer.resolve(timeData);
        },function(err){
          defer.reject(err)
        }
      );
      return defer.promise;
    };

    //"检查类别"
    allStatistic.getInspectCategoryData = function(){
      var defer = $q.defer();
      var typeData = {//检查类别数据
        yName:T.T('病例数'),
        rotate:-50
      };
      var str = '2017-01-01';
      str = str.replace(/-/g, '/');
      var filter = {
        startTime: new Date(str).getTime(),
        endTime: Date.now()
      };
      StatisticService.inspectCategory(filter).then(
        function (result){
          var data = [];
          var dataAxis = [];
          if (result.length){
            for(var i=0;i<result.length;i++){
              data[i] = result[i].count;
              dataAxis[i] = T.T(result[i].inspectCategory.typeDesc);
            }
          }else{
            data = [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0];
            dataAxis = [T.T('常1规'), T.T('加快'), T.T('冷冻'), T.T('尸解'), T.T('细胞学'), T.T('细针'), T.T('体检'), T.T('会诊'),
              T.T('肝穿'),T.T('肾穿'),T.T('骨髓'),T.T('淋巴'),T.T('眼科'),T.T('肌肉'),T.T('前列腺'),T.T('ESD')];
          }
          //console.warn("typeData.data",data);
          typeData.data = data;
          typeData.dataAxis = dataAxis;
          defer.resolve(typeData);
        },function(err){
          defer.reject(err)
        }
      );
      return defer.promise;
    };

    //"病例数"
    allStatistic.getSpecialDyeData = function(){
      var defer = $q.defer();
      StatisticService.specialDye().then(
        function(result){
          var data ={
            normalData:[],
            specialData:[]
          };
          var validArray = [];
          if(result.length){
            for(var i=0;i<result.length;i++){
              validArray[i] = result[i].month;
            }
            var maxMonth = _.max(validArray);
            for(var j=0;j<maxMonth;j++){
              data.normalData[j] = 0;
              data.specialData[j] = 0;
            }
            for(var k=0;k<validArray.length;k++){
              data.normalData[validArray[k]-1] = result[k].normal;
              data.specialData[validArray[k]-1] = result[k].special;
            }
            console.warn("result:",result);
            console.warn("validArray:",validArray,"maxMonth:",maxMonth);
            console.warn("data.normalData:",data.normalData);
            console.warn("data.specialData:",data.specialData);
          }else{
            for(var i=0;i<13;i++){
              data.normalData[i] = 0;
              data.specialData[i] = 0;
            }
          }
          defer.resolve(data);
        },function(err){
          defer.reject(err)
        }
      );
      return defer.promise;
    };

    //"工作量排行"
    allStatistic.getWorkLoadData = function(start,end,workStation){
      var defer = $q.defer();
      //时间戳设置
      var filter = {
        startTime: start,
        endTime: end,
        workStation:workStation
      };
      StatisticService.workLoad(filter).then(
        function (result){
          var data;
          if (result){
            data = result;
          }
          defer.resolve(data);
        },function(err){
          defer.reject(err)
        }
      );
      return defer.promise;
    };

    //"指控评分排行"
    allStatistic.getScoreData = function(start,end){
      var defer = $q.defer();
      //时间戳设置
      var filter = {
        startTime: start,
        endTime: end
      };
      StatisticService.score(filter).then(
        function (result){
          var data;
          if (result){
            data = result;
          }
          defer.resolve(data);
        },function(err){
          defer.reject(err)
        }
      );
      return defer.promise;
    };


  }
})();

