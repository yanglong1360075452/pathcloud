//查看历史诊断弹窗
(function () {
  'use strict';
  angular
    .module('pathcloud')
    .controller('drawPhotoController', drawPhotoController);

  /** @ngInject */
  function drawPhotoController($uibModalInstance, imgList, $timeout, toolService, toastr) {
    var drawPhoto = this;
    var canvas, width, height, context, arr;
    var x, y, w, h, textX, textY;
    $('#text-input').hide();

    drawPhoto.imgList = imgList;
    drawPhoto.perItemNum = 4; //每页图片数
    drawPhoto.imgUrlHeader = toolService.getImgHeader();

    $timeout(function () {
      drawPhoto.containerWidth = $('#img-container').width();
      drawPhoto.imgStyle = {
        width: drawPhoto.containerWidth / drawPhoto.perItemNum + 'px',
        padding: '0 5px',
        float: 'left'
      };
      drawFun();
      drawPhoto.changeBg(0)
    });

    //确定
    drawPhoto.ok = function () {
      $uibModalInstance.close();
    };

    //取消
    drawPhoto.cancel = function () {
      $uibModalInstance.dismiss();
    };


    function init() {
      arr = [];
      drawPhoto.text = '';
      $('#text-input').hide();
      canvas = document.querySelector("canvas");
      width = $('.canvas-container').width();
      $('.canvas-toolbar').width($('.canvas-container').width());
      //console.log("width",width)
      height = 389;
      canvas.width = width;
      canvas.height = height;
      context = canvas.getContext("2d");
    }

    drawPhoto.changeBg = function (index) {
      drawPhoto.chosedPic = index;
      var img = new Image(width, height);
      img.crossOrigin = "anonymous";
      img.src = drawPhoto.imgUrlHeader + imgList[index].url;
      img.width = width;
      img.height = height;
      // console.log(img)
      img.onload = function () {
        context.clearRect(0, 0, width, height);
        var ptrn = context.createPattern(img, 'no-repeat');
        context.fillStyle = ptrn;
        context.fillRect(0, 0, width, height);
        arr = [];
        arr.push(context.getImageData(0, 0, width, height));

      }
    }

    //画图相关
    function drawFun() {
      init();

      var typechoose = $(".tool");
      var writing = false;

      var back = $(".back");
      var clear = $(".clear");
      var save = $(".save");
      var shezhi = $(".shezhi");

      var color = "red";
      var type = "line";
      var linewidth = "2";
      var style = "stroke";
      var writeable = true;
      context.font = "italic bold 16px 黑体";

      var draw = new Draw(context, {type: style, color: color, width: linewidth}); //实例化构造函数

      // 绘制形状
      typechoose.each(function (index, ele) {
        $(ele).click(function () {
          type = $(this).attr("data");

        })
      });


      //添加文字
      $('#text-input').blur(function (e) {
        var textHtml = $(this).html();
        var text = _.compact($.trim(textHtml.replace(/<div>/g, '<br>').replace(/<\/div>/g, '')).split('<br>'));
        //console.warn(text)

        //绘制图形
        draw.text(text, textX - 10, textY + 10);
        arr.push(context.getImageData(0, 0, width, height));

        $(this).hide();
          $(this).html("");
          $timeout(function () {
            writeable = true;
          },500)
      });

      // 设置
      // shezhi.each(function(index,ele){
      //   $(ele).click(function(){
      //     $(this).css({color:"#5bd219",backgroundColor:"#fff"}).animate({opacity:0.99},200,function(){
      //       $(this).css({color:"#fff",backgroundColor:"#5bd219",opacity:1});
      //     });
      //   })
      // })

      // 撤销
      back.click(function () {
        if (arr.length > 1) {
          arr.pop();
          context.clearRect(0, 0, width, height);
          context.putImageData(arr[arr.length - 1], 0, 0, 0, 0, width, height);
        }
        // arr.pop();
        // context.clearRect(0,0,width,height);
        // if(arr.length>0){
        //   context.putImageData(arr[arr.length-1],0,0,0,0,width,height);
        // }
      });
      // 清除
      clear.click(function () {
        arr = [];
        // context.clearRect(0,0,width,height);
        drawPhoto.changeBg(drawPhoto.chosedPic)
      });
      // 保存
      save.click(function () {
        var reg = canvas.toDataURL("image/png"); //跳转页面手动保存
        //        var reg=canvas.toDataURL("image/png").replace("image/png","image/octet-stream");//直接自动保存下载
        //         location.href=reg;
        //console.log('reg',reg)

        imgList[drawPhoto.chosedPic].url = reg;
        toolService.fileEditor(reg.split(',')[1], imgList[drawPhoto.chosedPic].id).then(
          function (result) {
            toastr.success('保存成功');
            // console.warn("pic data",result)
          }
        )
      });


      canvas.onmousedown = function (e) {
        console.log("type",type)
        x = e.offsetX;
        y = e.offsetY;
        if (type == "pen") {
          context.beginPath();
          context.moveTo(x, y);
        }
        if (type == "text") {
          console.warn("text", x, y);
          if(writeable){
            if ($('#text-input').css('display') === 'none') {
              textX = x;
              textY = y;
              $('#text-input').show();
              $('#text-input').css({'top': y, 'left': x});
              writeable = false;
              $timeout(function () {
                $('#text-input').focus();
              });
            }
          }else{
            return
          }

        }


        canvas.onmousemove = function (e) {
            w = e.offsetX;
            h = e.offsetY;
            if (arr.length != 0) {
              context.putImageData(arr[arr.length - 1], 0, 0, 0, 0, width, height);
            } else {
              arr.push(context.getImageData(0, 0, width, height));
            }

            if (type !== "text")
              draw[type](x, y, w, h);

            // if(type!="eraser"){
            //   // context.clearRect(0,0,width,height);
            //   if(arr.length!=0){
            //     context.putImageData(arr[arr.length-1],0,0,0,0,width,height);
            //   }
            // }
            // if(cutflag&&type=="cut"){
            //   if(iscut){
            //     context.clearRect(lx,ly,lw-lx,lh-ly);
            //   }
            //   var nx=lx+(w-x);
            //   var ny=ly+(h-y);
            //   context.putImageData(cutdata,nx,ny);
            // }else if(type=="poly"){
            //   draw[type](x,y,w,h,n);
            // }else{
            //   draw[type](x,y,w,h);
            // }

          };

          document.onmouseup = function () {
            canvas.onmousemove = null;
            document.onmouseup = null;

            arr.push(context.getImageData(0, 0, width, height));
          }


      }

    }


  }
})();
