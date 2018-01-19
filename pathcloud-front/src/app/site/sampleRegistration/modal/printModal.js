(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('PrintModalController',PrintModalController);

  /** @ngInject */
  function PrintModalController($uibModalInstance,$timeout){

    var printModal = this;
    var LODOP;

    //在PRINT_INIT之后调用
    function setPrinter(name){
      var iCount=LODOP. GET_PRINTER_COUNT ();
      console.log("iCount",iCount);
      for(var i=0;i<iCount;i++){
        console.log("i",i,LODOP. GET_PRINTER_NAME(i));
        if((LODOP. GET_PRINTER_NAME(i)).indexOf(name)>=0){
          LODOP. SET_PRINTER_INDEX (i);
          break;
        }
      }
    }

    printModal.printQrCodeLabel= function(code){
      LODOP=getLodop();
      LODOP.PRINT_INIT("打印二维码标签");
      LODOP.SET_PRINT_PAGESIZE (1,600,423,"");

      setPrinter('GT800');
      LODOP.SET_PRINT_STYLE("FontSize",12);
      LODOP.SET_PRINT_STYLE("Bold",2);
      LODOP.SET_PRINT_STYLEA('PRINT_INIT','Top','20mm');//设置整体偏移
      LODOP.SET_PRINT_STYLEA('PRINT_INIT','Left','20mm');
      LODOP.SET_PRINT_STYLEA(0,"QRCodeVersion",5);
      LODOP.SET_PRINT_STYLEA(0,"QRCodeErrorLevel","H");
      LODOP.ADD_PRINT_BARCODE(10,10,100,100,"QRCode",code)
      LODOP.ADD_PRINT_TEXT(20,105,120,30,"样本编号");
      LODOP.ADD_PRINT_TEXT(70,105,120,30,code);
      //LODOP.ADD_PRINT_LINE(110,0,110,200,0,1);
      //LODOP.PREVIEW();
      LODOP.PRINT();
    };

    printModal.prn1_preview=function (){
      //CreateOneFormPage();
      //console.log(convertCanvasToImage(angular.element("canvas").html()))
      //LODOP.PREVIEW();
      LODOP=getLodop();
      LODOP.PRINT_INIT("打印二维码标签");
      LODOP.SET_PRINT_PAGESIZE (1,600,425,"")
      var iCount=LODOP. GET_PRINTER_COUNT ();
      console.log("iCount",iCount);
      for(var i=0;i<iCount;i++){
        console.log("i",i,LODOP. GET_PRINTER_NAME(i));
        if((LODOP. GET_PRINTER_NAME(i)).indexOf('GT800')>=0){
          LODOP. SET_PRINTER_INDEX (i);
          break;
        }
      }
      LODOP.SET_PRINT_STYLE("FontSize",12);
      LODOP.SET_PRINT_STYLE("Bold",2);
      LODOP.SET_PRINT_STYLEA('PRINT_INIT','Top','20mm');//设置整体偏移
      LODOP.SET_PRINT_STYLEA('PRINT_INIT','Left','20mm');
      LODOP.SET_PRINT_STYLEA(0,"QRCodeVersion",5);
      LODOP.SET_PRINT_STYLEA(0,"QRCodeErrorLevel","H");
      LODOP.ADD_PRINT_BARCODE(10,10,100,100,"QRCode","14567890123123")
      LODOP.ADD_PRINT_TEXT(20,110,120,30,"样本编号");
      LODOP.ADD_PRINT_TEXT(70,110,120,30,"1231241221421");
      LODOP.ADD_PRINT_LINE(110,0,110,200,0,1);
      //LODOP.PREVIEW();
      LODOP.PRINT();

    };

    printModal.prn1_print=function (){
      CreateOneFormPage();
      LODOP.PRINT();
    };
    printModal.prn1_printA=function (){
      CreateOneFormPage();
      LODOP.PRINTA();
    };

    function CreateOneFormPage(){
      LODOP=getLodop();

      LODOP.PRINT_INIT("打印控件功能演示_Lodop功能_表单一");
      LODOP.SET_PRINT_STYLE("FontSize",18);
      LODOP.SET_PRINT_STYLE("Bold",1);
      LODOP.ADD_PRINT_BARCODE(20,20,100,100,"QRCode","1234567890版本7的最大值是122个字符123123")
      //LODOP.ADD_PRINT_HTM(88,200,350,600,document.getElementById("printContent").innerHTML);
    };

    function convertCanvasToImage(canvas) {
      // canvas.toDataURL 返回的是一串Base64编码的URL，当然,浏览器自己肯定支持
      // 指定格式 PNG
      printModal.qrImg = canvas.toDataURL("image/png");
    };

    $timeout(function () {
      convertCanvasToImage($('.qrcode')[0]);
      console.log(printModal.qrImg)
    });
    printModal.resistData="test";

    //确定按钮
    printModal.ok = function (){
      $uibModalInstance.close();
    };

    //取消按钮
    printModal.cancel = function(){
      $uibModalInstance.dismiss();
    };
  }//end CommonModalController
})();
