/**
 * Created by Administrator on 2016/12/21.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('EmbedController', EmbedController);

  /** @ngInject */
  function EmbedController($timeout,$state,toastr,embedService,paramSettingService,toolService,IhcService,$uibModal,$filter) {
    var embed=this;
    // P16121600089 P16122000101  P16121500078  P16121500081
    embed.filter="";
    embed.remark={};

    //点击按钮之后搜索框获得焦点
    $timeout(function () {
      $("button").click(function () {
        $("#searchInput").focus();
      })
    },1000);
  
    //计算包埋进度
    function count() {
      embedService.embedCount().then(function (res) {
        embed.count=res;
        // "data": {
        //   "blockTotal":748, #蜡块总数
        //   "hadOperated":192, #已包埋数
        //   "todayTotal":15, #今日蜡块总数
        //   "todayOperated":1 #今日已包埋数
        // }
      })
    }
    count();
    
    //显示大图片
    embed.showBigImg = function () {
      var modalInstance = $uibModal.open({
        templateUrl: 'app/site/drawMaterial/modal/showImg.html',
        controller: 'ShowImgController',
        size: 'lg',
        resolve: {
          imgUrl: function () {
            return 'api/static/grossingConfirm/'+embed.blockInfo.blockId;
          }
        }
      });

    };
    // ###sprint 13  3.1 申请重补取 post("/embed/reGrossing"  {blockId note}
    embed.reGrossing = function () {
      if(!embed.blockInfo.blockId) return;
      var data = {
        blockId:embed.blockInfo.blockId,
        note:embed.remark.note
      };

      IhcService.post("/embed/reGrossing",data).then(function () {
        toastr.success("操作成功")
        $state.go('app.embed',{},{reload:true})
      },function (err) {
        toastr.error(err.reason)
      })
    };


    // 暂停包埋 sprint13_3.2 /embed/pause  {   "blockId": 1, #蜡块ID    "noteType":"备注类型",      "note":"备注"  }   3.3 取消暂停包埋 /api/embed/pause/cancel
    embed.pause = function () {
      embed.blockInfo.embedPause = !embed.blockInfo.embedPause;//让这个值不变 接口成功后赋值  也可用阻止默认事件
      var data = {
        blockId:embed.blockInfo.blockId,
        noteType:embed.remark.noteType,
        note:embed.remark.note,
      };
      if(embed.blockInfo.embedPause){
        IhcService.post("/embed/pause/cancel",data).then(function () {
          embed.blockInfo.embedPause = !embed.blockInfo.embedPause;
        })
      }else{
        IhcService.post("/embed/pause",data).then(function () {
          embed.blockInfo.embedPause = !embed.blockInfo.embedPause;
        })
      }
    };

    // 点击获取蜡块完成包埋按钮
    embed.getBlockInfoButton = function (item) {

      embed.filter = embed.blockInfo.pathologySerialNumber + item; //拼接病理号跟蜡块号
      embed.getBlockInfo();
      // embed.filter = ""; //在getBlockInfo()时清空
    };

    embed.getBlockInfo=function () {

      if(!embed.filter) { embed.blockInfo={};return;}
      embed.filterSave=embed.filter.replace("-","");//去掉 “-” 号  在get蜡块信息的时候用

      if(embed.blockInfo&&embed.blockInfo.blockId){
        if (embed.blockInfo.status==14){  //判断是否为待包埋
          embed.pending = true;// 防止用户点击多次
          embedService.confirmEmbed(embed.remark,embed.blockInfo.blockId).then(function (res) {
            toastr.success("包埋保存成功！");
            //自动进入下一条
            getBlockInfo()

          },function (err) {
            toastr.error(err,"包埋保存失败！");
            getBlockInfo()

          }).finally(function () {
            embed.pending = false
          })
        }else {
          getBlockInfo()
        }
      }else {
        getBlockInfo()
      }
      // getBlockInfo()
    };

    paramSettingService.embedRemark().then(function (res) {
      embed.embedRemarkLIst=res;
    },function (err) {
      toastr.error(err);
    });

    // 包埋确认按钮
    embed.confirmEmbed=function () {
      if(!embed.blockInfo.blockId) {
        return;
      }

      embedService.confirmEmbed(embed.remark,embed.blockInfo.blockId).then(function (res) {
        toastr.success("包埋保存成功！")
        // 在点击完成包埋按钮时判断当前病理号是否还有未包埋的蜡块
        getBlockInfo("finish");
        $state.go('app.embed',{},{reload:true})
      },function (err) {
        toastr.error(err)
      })
    };
    function getBlockInfo(a) {
      
      embed.remark={};

      var prePathId;
      if(a!=='ignore'&&embed.blockInfo&&embed.blockInfo.blockId){//每次先看页面上有没有保存上一次包埋的病理信息

        if(embed.filter.substring(0,9)!=embed.blockInfo.pathologySerialNumber){ //当前搜索的病理号跟上一个病理号比较  传入一个病理号的Id get的时候判断当前病理号是否处理完了。
          prePathId=embed.blockInfo.pathologyId;
          console.info("上一个病理的id",prePathId)
        }
      }
      // console.info("上一个病理的id",prePathId);

      //获取蜡块信息时 判断上一个病理号蜡块是否全部包埋
      embed.pending = true;
      embedService.getBlockInfo(embed.filterSave,prePathId)
      .then(function (res) { //传入要搜索的蜡块号 跟上一个病理Id

        // 返回的不是数组说明上一个病理的蜡块全部切完
        if(res.pathologyId){
          // 当点完成包埋时判断下要是没有未包埋的蜡块了 直接返回放在重复提示被包埋
          if(a === 'finish'){
            return
          }
          //判断不是包埋 并提示被谁包埋过
          if(res.status !==14 && !(a === 'ignore')){

            if(!res.embedOperateTime){
              toastr.error("蜡块状态不是待包埋");
              return
            }
            //重新打印 时要获取下蜡块下的玻片信息
            var content = "该蜡块于"+ $filter('date')(res.embedOperateTime,'yyyy-MM-dd HH:mm') + "被"+ res.embedOperatorDesc + "包埋，目前状态是" + res.statusDesc||res.statusName;
            toastr.error(content)
            /* toolService.getModalResult({modalTitle:"提示",modalContent:content,size:"sm"}).then(function () {

             // 该蜡块于2017/08/23 10:50被张三切片，目前状态是待染色

             });*/

          }

          embed.filter = '';
          embed.blockInfo=res;
          embed.remark.noteType=res.embedRemarkType;
          embed.remark.note=res.embedRemark;


        }else {
          // 返回的是数组提示上一个病理未包埋的蜡块
          //拼接 病理号跟蜡块号
          for (var i=0;i<res.length;i++){
            res[i]=embed.blockInfo.pathologySerialNumber+'-'+res[i];
          }

          res=res.join("，");

          toolService.getModalResult({modalTitle:"上一个病理号仍有蜡块未包埋，是否忽略",modalContent:"未包埋蜡块号："+res,size:"dialog"}).then(function () {
            // 点击忽略
            getBlockInfo("ignore")

          },function () {
            console.info("忽略蜡块");
          });
        }


      },function (err) {
        toastr.error(err,embed.filterCopy)
      }).finally(function () {
        embed.pending = false;
      });
      
      //计算包埋进度
      count()

    }
  }
})();

