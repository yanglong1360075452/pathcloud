//质控评分弹窗
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('qualityGradeController', qualityGradeController);

  /** @ngInject */
  function qualityGradeController($uibModalInstance,StatisticService,toastr,DiagnosisService,scoreData,slideId){
    var qualityGrade = this;
    //scoreData = { blockId:1, grossing:4, dehydrate:3, embedding:3, sectioning:5, staining:1, sealing:2,note:'' };//测试数据
    console.log("接收从诊断页面传过来的评分对象",scoreData);
    qualityGrade.tabFlag = 1;//控制tab显示
    qualityGrade.max = 5;//星星的个数
    qualityGrade.isReadonly = false;//控制只读状态
    qualityGrade.scoreData = {};//质控弹窗评分数据
    qualityGrade.slideId = slideId;//接收从诊断页面传过来的评分对象--玻片ID
    qualityGrade.scoreData.grossing = scoreData.grossing||0;//接收从诊断页面传过来的评分对象--取材
    qualityGrade.scoreData.dehydrate = scoreData.dehydrate||0;//接收从诊断页面传过来的评分对象--脱水
    qualityGrade.scoreData.embedding = scoreData.embedding||0;//接收从诊断页面传过来的评分对象--包埋
    qualityGrade.scoreData.sectioning = scoreData.sectioning||0;//接收从诊断页面传过来的评分对象--切片
    qualityGrade.scoreData.staining = scoreData.staining||0;//接收从诊断页面传过来的评分对象--染色
    qualityGrade.scoreData.sealing = scoreData.sealing||0;//接收从诊断页面传过来的评分对象--封片
    qualityGrade.scoreData.note = scoreData.note||'';//接收从诊断页面传过来的评分对象--备注

    //获取已有的评分标准
    qualityGrade.getGrade=function(){
      StatisticService.getQualityGrade().then(
          function(result){
          for(var i=0;i<result.length;i++){
            if(result[i].workstationName==='取材'){
              var str='1分（1颗星）：'+ result[i].oneScore +
                      '，2分（2颗星）：'+ result[i].twoScore +
                      '，3分（3颗星）：'+ result[i].threeScore +
                      '，4分（4颗星）：'+ result[i].fourScore +
                      '，5分（5颗星）：'+ result[i].fiveScore;
              qualityGrade.materialStr=str;
            }else if(result[i].workstationName==='脱水'){
              var str='1分（1颗星）：'+ result[i].oneScore +
                '，2分（2颗星）：'+ result[i].twoScore +
                '，3分（3颗星）：'+ result[i].threeScore +
                '，4分（4颗星）：'+ result[i].fourScore +
                '，5分（5颗星）：'+ result[i].fiveScore;
              qualityGrade.grossingStr=str;
            }else if(result[i].workstationName==='包埋'){
              var str='1分（1颗星）：'+ result[i].oneScore +
                '，2分（2颗星）：'+ result[i].twoScore +
                '，3分（3颗星）：'+ result[i].threeScore +
                '，4分（4颗星）：'+ result[i].fourScore +
                '，5分（5颗星）：'+ result[i].fiveScore;
              qualityGrade.embedStr=str;
            }else if(result[i].workstationName==='切片'){
              var str='1分（1颗星）：'+ result[i].oneScore +
                '，2分（2颗星）：'+ result[i].twoScore +
                '，3分（3颗星）：'+ result[i].threeScore +
                '，4分（4颗星）：'+ result[i].fourScore +
                '，5分（5颗星）：'+ result[i].fiveScore;
              qualityGrade.sectionStr=str;
            }else if(result[i].workstationName==='染色'){
              var str='1分（1颗星）：'+ result[i].oneScore +
                '，2分（2颗星）：'+ result[i].twoScore +
                '，3分（3颗星）：'+ result[i].threeScore +
                '，4分（4颗星）：'+ result[i].fourScore +
                '，5分（5颗星）：'+ result[i].fiveScore;
              qualityGrade.stainingStr=str;
            }else{
              var str='1分（1颗星）：'+ result[i].oneScore +
                '，2分（2颗星）：'+ result[i].twoScore +
                '，3分（3颗星）：'+ result[i].threeScore +
                '，4分（4颗星）：'+ result[i].fourScore +
                '，5分（5颗星）：'+ result[i].fiveScore;
              qualityGrade.sealingStr=str;
            }
          }
          qualityGrade.gradeData=result;
        }
      );
    };
    qualityGrade.getGrade();

    //tab切换事件
    qualityGrade.changeTabFlag = function(index){
      if(index===1){
        qualityGrade.tabFlag = 1;//tab:星星评分
      }else if(index===2){
        qualityGrade.tabFlag = 2;//tab:取材细则
      } else if(index===3){
        qualityGrade.tabFlag = 3;//tab:脱水细则
      } else if(index===4){
        qualityGrade.tabFlag = 4;//tab:包埋细则
      }else if(index===5){
        qualityGrade.tabFlag = 5;//tab:切片细则
      }else if(index===6){
        qualityGrade.tabFlag = 6;//tab:染色细则
      }else if(index===7){
        qualityGrade.tabFlag = 7;//tab:封片细则
      }
    };

    //划过星星事件
    qualityGrade.hoveringOver = function(value) {
      qualityGrade.overStar = value;
      qualityGrade.percent = 100 * (value / qualityGrade.max);
    };

    //计算平均分
    qualityGrade.calculateAverage = function(){
      var total = qualityGrade.scoreData.grossing + qualityGrade.scoreData.dehydrate +
        qualityGrade.scoreData.embedding + qualityGrade.scoreData.sectioning +
        qualityGrade.scoreData.staining + qualityGrade.scoreData.sealing;
      return total/6;
    }

    //玻片评分事件
    qualityGrade.slideScore = function(){
      qualityGrade.scoreData.average = qualityGrade.calculateAverage();//计算平均分
      console.log("玻片ID",qualityGrade.slideId,"评分对象",qualityGrade.scoreData);
      DiagnosisService.slideScore(qualityGrade.slideId,qualityGrade.scoreData).then(
        function(result){
          if(!result){
            toastr.success("评分成功！");
            $uibModalInstance.close(qualityGrade.scoreData);
          }
        },
        function(error){
          toastr.error("评分失败，请重新尝试！");
        }
      );
    };

    //确定
    qualityGrade.ok = function(){
      qualityGrade.slideScore();
    };

    //取消
    qualityGrade.cancel = function(){
      $uibModalInstance.dismiss();
    };

  }
})();
