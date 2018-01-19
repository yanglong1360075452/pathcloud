(function () {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ProQualityGradeController', ProQualityGradeController);

  /** @ngInject */
  function ProQualityGradeController(StatisticService, toastr, IhcService, ProductService, DiagnosisService) {
    var proQualityGrade =this;
    // var scoreData = { grossing:0, dehydrate:0, embedding:0, sectioning:0, staining:0, sealing:0,note:'' };//测试数据 201700078-1-1
    proQualityGrade.max = 5;//星星的个数
    proQualityGrade.isReadonly = false;//控制只读状态
    proQualityGrade.scoreData =  {};
    proQualityGrade.setting = '0';//默认设置0


    proQualityGrade.tableHeaders=[
      {name:"工作站"},{name:"1分（1颗星）"},
      {name:"2 分（2颗星）"},{name:"3 分（3颗星）"},
      {name:"4 分（4颗星）"},{name:"5 分（5颗星）"},
      {name:"合格标准"}
    ];

    //点击搜索事件
    proQualityGrade.search = function(){
      try {
        var slideNo = proQualityGrade.searchStr.replace(/-/g,"");
        proQualityGrade.pathologyNumber = proQualityGrade.searchStr.split("-")[0]; // 分割出来病理号
        proQualityGrade.blockSubId = proQualityGrade.searchStr.split("-")[1]; // 分割出来蜡块号
        proQualityGrade.slideSubId = proQualityGrade.searchStr.split("-")[2]; // 分割出来玻片小号
      }catch (err){
        return
      }

      IhcService.get("/production/score/"+slideNo+"/"+proQualityGrade.slideSubId).then(function (res) {
        proQualityGrade.slideInfo = res;
        proQualityGrade.scoreData = {
          grossing: res.grossing,
          dehydrate: res.dehydrate,
          embedding: res.embedding,
          sectioning: res.sectioning,
          staining: res.staining,
          sealing: res.sealing,
          // note:''
        };
      },function (err) {
        toastr.error(err.reason||"记录不存在")
      })
    };
      //划过星星事件
    proQualityGrade.hoveringOver = function(value) {
      proQualityGrade.overStar = value;
      proQualityGrade.percent = 100 * (value / proQualityGrade.max);
    };

    //计算平均分
    proQualityGrade.calculateAverage = function(){
      var total = proQualityGrade.scoreData.grossing + proQualityGrade.scoreData.dehydrate +
        proQualityGrade.scoreData.embedding + proQualityGrade.scoreData.sectioning +
        proQualityGrade.scoreData.staining + proQualityGrade.scoreData.sealing;
      return total/6;
    };

    //获取已有的评分标准
    proQualityGrade.getGrade=function(){
      StatisticService.getQualityGrade().then(
        function(result){
          proQualityGrade.gradeData = result;
        }
      );
    };
    proQualityGrade.getGrade();
    //玻片评分事件
    proQualityGrade.slideScore = function(){

      proQualityGrade.scoreData.average = proQualityGrade.calculateAverage();//计算平均分
      // console.log("玻片ID",proQualityGrade.slideInfo.blockId,"评分对象",proQualityGrade.scoreData);
      IhcService.put("/production/score/"+proQualityGrade.slideInfo.blockId, proQualityGrade.scoreData).then(
        function(result){
          if(!result){
            toastr.success("评分成功！");
          }
        },
        function(error){
          toastr.error(error.reason||"评分失败，请重新尝试！");
        }
      );
    };
    proQualityGrade.submit = function(){
        // console.log(proQualityGrade.setting);
        // console.log(proQualityGrade.scoreData);
      console.log(proQualityGrade.setting);
      console.log('未处理');
      //当选择了重补取或重切
      if (proQualityGrade.setting !== '0'&& proQualityGrade.setting){
        debugger
        return
        var loseHandelDataList = [
          {
            "abnormalId": proQualityGrade.slideInfo.blockId, // 传的是玻片id  重切申请切完后用蜡块号去切片工作站重切一个新的玻片出来 重补取就到取材工作站能看到一个异常终止的蜡块
            "handle": parseInt(proQualityGrade.setting),
            // "note": ""
          }
        ];
        ProductService.setProductionAbnormal(loseHandelDataList).then(function () {
          toastr.success("申请成功")
        },function (error) {
          var err = "";
          if(error){
            err = error.reason
          }else {
            if(proQualityGrade.setting === '1'){
              err = "申请重取失败"
            }else{
              err = "申请重切失败"
            }
          }
          toastr.error(err);
        });
      }else{
        //评分操作
        proQualityGrade.slideScore()
      }

    }


    }

})();

