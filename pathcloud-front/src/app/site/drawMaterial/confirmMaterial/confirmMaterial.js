/**
 * Created by Administrator on 2016/11/9.
 * 取材确认页面
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ConfirmMaterialController', ConfirmMaterialController);

  /** @ngInject */
  function ConfirmMaterialController($rootScope, $scope, $state,IhcService,toolService,$uibModal,$interval,photoService,toastr,MaterialService,videoService) {
    //参数设置
    var confirmMaterial = this;
    confirmMaterial.checkBox = [];//存放勾选与否的脱水篮数组
    confirmMaterial.basketStr = "";//当前勾选的脱水篮
    // confirmMaterial.basketList = [];//用了判断哪些脱水篮选中了 拍照功能新增修改

    //设置表格头部
    confirmMaterial.tableHeaders = [
      {name:"病理号",order:1},{name:"姓名",order:2},
      {name:"送检科室",order:3},{name:"蜡块号",order:4},
      {name:"取材部位",order:5},{name:"组织数",order:6},
      {name:"取材标识",order:7},{name:"取材医生",order:8},
      {name:"记录人员",order:9},{name:"脱水篮编号",order:10},
      {name:"取材时间",order:11}
    ];

    //设置默认查询参数
    confirmMaterial.searchData = {
      secOperator:$rootScope.user.id,//当前记录员ID
      sort:"asc",
      page:1,
      length:20,
      order:1,
      basketNumbers:null
    };

    /*//新增拍照功能 选一个就拍一个 拍照跟确认是分开的 拍照的时候只能选择一个
    // 拍照功能开始 每个脱水栏只能对应一张照片 重拍则替代
    function initVideo() {
      var option={
        gumVideo:"#gumVideo"
      };
      videoService.init(option)
    }
    initVideo()*/
    
    // 通过佳能相机软件拍照上传
    photoService.clearLocalPhoto();
    function insertPhoto() {
      // $scope.localPhotos = [];// 每次抓取的本地照片
      photoService.getLocalPhotoUrlList().then(function (res) {
        // console.info("抓取到的本地的照片",res);
        if(res.length){
          confirmMaterial.photoUrl = res[res.length - 1];
  
          photoService.getBase64(confirmMaterial.photoUrl).then(function (Base64) {
            confirmMaterial.base64 = Base64
          });
        }
      });
    
    }// end insertPhoto
  
    var insertPhotoInterval = $interval(function () {
      insertPhoto();
    },3000);
  
    $scope.$on('$destroy', function () {
      // 保证interval已经被销毁
      $interval.cancel(insertPhotoInterval);
      photoService.clearLocalPhoto()
    });

    confirmMaterial.enlarge = function () {
      photoService.enlargePhoto(confirmMaterial.photoUrl)
/*      confirmMaterial.photoUrl = "";
      var modalInstance = $uibModal.open({
        templateUrl: 'app/site/modal/showVideo.html',
        controller: 'ShowVideoController',
        size: 'lg',
        resolve: {
          position:function () {
            // return 1;
          },
          pathologicalId:function () {
            return confirmMaterial.basketStr;//传选择的脱水篮
          }
        }
      });
      modalInstance.result.then(function (imgUrl) {
       /!* if(!imgUrl){return};
        // todo 弹窗内拍照功能 从弹窗返回
        confirmMaterial.photoUrl = imgUrl;//页面显示的地址
        confirmMaterial.base64 = imgUrl.split(",")[1];*!/

      }).finally(function () {
        // 蜡块的照片地址  不显示拍的照片的话用不到
        // confirmMaterial.photoUrl = 'api/static/grossingConfirm/'+confirmMaterial.grossingData[0].blockId;
      })*/
    };

    //获取取材确认拍照设置  判断是否必须拍照 1 必须拍照
    IhcService.get("/paramSetting/grossingConfirmPhoto").then(function (res) {
      confirmMaterial.grossingConfirmPhoto = res; //1 必须拍照
    },function (err) {
      toastr.error(err.reason)
    });

    /*// 拍照生成需要上传的base64文件
    confirmMaterial.photo=function () {
      var imgUrl=videoService.photo();
      confirmMaterial.photoUrl = imgUrl;//页面显示的地址
      confirmMaterial.base64 = imgUrl.split(",")[1];

    };*/

    //获取蜡块列表数据  获取脱水篮信息列表后获取拍摄的照片 通过其中一个蜡块id 获取
    confirmMaterial.getDataList = function () {
      // 在获取脱水篮数据时清空拍照数据
      photoService.clearLocalPhoto();
      confirmMaterial.photoUrl = null;//清空图片链接
      confirmMaterial.base64 = null;//清空图片上传的base64
      confirmMaterial.searchData.basketNumbers = confirmMaterial.basketStr;
      MaterialService.getConfirmGrossingList(confirmMaterial.searchData).then(
        function(result){
          confirmMaterial.grossingData = result.data;
          confirmMaterial.total = result.total;
          confirmMaterial.totalPathology = result.totalPathology;

          // 蜡块的照片地址
          // confirmMaterial.photoUrl = 'api/static/grossingConfirm/'+confirmMaterial.grossingData[0].blockId;
        })
    };

    //获取脱水篮信息列表
    confirmMaterial.getGrossingBasketList = function(){
      var status = 11;//取材记录状态为“待取材确认”
      var basketBox = [];//脱水篮编号
      MaterialService.getGrossingBasketList(status).then(
        function(result){
          if(result&&result.length){
            confirmMaterial.checkBox = result;
            confirmMaterial.basketStr = result[0].basketNumber;
            confirmMaterial.getDataList() //获取蜡块列表数据
          }

        });
    };
    confirmMaterial.getGrossingBasketList();

    //取材确认按钮
    confirmMaterial.confirmMaterial = function(){
      // 先判断是否上传了图片 根据参数
      if(confirmMaterial.grossingConfirmPhoto==1&&!confirmMaterial.base64){
        toastr.error("请先拍摄脱水篮照片");
        return;
      }

      $rootScope.loading = true;//loading

      // 取材确认图片上传 sprint13 /api/file/grossingConfirm/{basketNum}
      toolService.fileUpload(confirmMaterial.base64,"grossingConfirm",confirmMaterial.basketStr).then(function () {
        // 图片上传成功后取材确认
        MaterialService.confirmMaterial(confirmMaterial.basketStr).then(
          function(result){
            toastr.success("取材确认成功！");
            $state.go('app.confirmMaterial',{},{reload:true});
          },
          function(error){
            toastr.error(error);
          }
        ).finally(function () {
          confirmMaterial.inConfirm=false;
          $rootScope.loading = false;
        })
      },function () {
        $rootScope.loading = false;
        toastr.error("照片上传失败")
      })



    };


  }
})();

// 拍照时判断是否只选择一个脱水栏 拿出选择的脱水篮号码
// if(!confirmMaterial.basketStr){toastr.error("只能选择一个脱水篮拍照"); return;}
// if(confirmMaterial.basketStr.length > 15||confirmMaterial.basketStr.length < 0){
//   toastr.error("只能选择一个脱水篮拍照");
//   return;
// }
//判断是否必须要拍照  不判断给后台判断
// if(confirmMaterial.grossingConfirmPhoto == 1){
//   if(!confirmMaterial.base64){
//     toastr.error("请给脱水篮拍一张照片");
//     return
//   }
// }

/*if(result){

 for(var i=0;i<result.length;i++){
 confirmMaterial.checkBox[i] = {
 basketNumber: result[i].basketNumber,
 blockCount:result[i].blockCount,
 // isChecked: true
 };
 if(confirmMaterial.grossingConfirmPhoto == 2){
 confirmMaterial.checkBox[i].isChecked = true //判断是否必须拍照 必拍照的话就不勾  可选才勾上
 }
 basketBox[i] = confirmMaterial.checkBox[i].basketNumber;
 }
 var basketStr = "";
 for(var k=0;k<basketBox.length-1;k++){
 basketStr += basketBox[k] + ',';
 }
 basketStr += basketBox[basketBox.length-1];
 console.log("脱水篮编号str：------",basketStr);
 confirmMaterial.searchData.basketNumbers = basketStr;
 console.log("获取脱水篮checkBox数据：-----",confirmMaterial.checkBox);
 confirmMaterial.basketList = confirmMaterial.checkBox; //判断选中的脱水篮用 -huyu
 confirmMaterial.basketStr = basketStr;
 // confirmMaterial.basketStr = "true";
 }else{
 confirmMaterial.total = 0;
 confirmMaterial.totalPathology = 0;
 }*/
/*
 //checkbox选中与否事件  打钩并获取信息
 confirmMaterial.IsChecked = function(index){
 console.log("checkbox的index",index);
 confirmMaterial.basketBox = [];//存放脱水篮编号
 var basketBox = [];//脱水篮编号
 var j = 0;
 var basketStr = "";

 for(var i=0;i<confirmMaterial.checkBox.length;i++){
 if(confirmMaterial.checkBox[i].isChecked){
 basketBox[j] = confirmMaterial.checkBox[i].basketNumber;
 j++;
 }
 }

 console.log("更改后脱水篮信息列表数据：------",basketBox);
 confirmMaterial.basketList = basketBox; //判断选中的脱水篮用 -huyu

 for(var k=0;k<basketBox.length-1;k++){
 basketStr += basketBox[k] + ',';
 }
 basketStr += basketBox[basketBox.length-1];

 console.log("脱水篮编号str：------",basketStr);
 if(basketBox.length===0){
 basketStr = "";
 }
 confirmMaterial.searchData.basketNumbers = basketStr;
 confirmMaterial.basketStr = basketStr;
 confirmMaterial.data = basketStr;

 if(basketStr){
 confirmMaterial.getConfirmGrossingList();
 }else{
 confirmMaterial.grossingData = [];
 confirmMaterial.total = 0;
 confirmMaterial.totalPathology = 0;
 }

 };

//获取蜡块列表  默认显示当前记录员未确认的取材记录
confirmMaterial.getConfirmGrossingList = function(){
  console.log("searchData======",confirmMaterial.searchData);
  MaterialService.getConfirmGrossingList(confirmMaterial.searchData).then(
    function(result){
      confirmMaterial.grossingData = result.data;
      confirmMaterial.total = result.total;
      confirmMaterial.totalPathology = result.totalPathology;
      console.log("获取当前记录员未确认的取材记录列表数据：-----",result);
    })
};
confirmMaterial.getConfirmGrossingList();*/
