/**
 * Created by lenovo on 2016/8/2.
 */
(function() {
    'use strict';

    angular
        .module('pathcloud')
        .factory('printerService', ['$http', '$q', '$uibModal', '$log', 'toolService','IhcService', function($http, $q, $timeout, $log, toolService, IhcService) {
            var LABEL_PRINTER_NAME = 'PathTraq';
            var LODOP;
            var printUrl = '[printUrl]';
            var Config;//定义一个打印配置

            // 获取医院打印配置的json文件
            IhcService.get("/systemSetting/hospital").then(function (result) {
              // console.info("获取医院信息");
              if(!result) return;
              IhcService.getLocal("assets/printConfig/"+result+".json").then(function (res) {
                Config = res;
                // console.info("json文件",res.printData)
              });
            });

            //在PRINT_INIT之后调用
            function setPrinter(name) {
                var iCount = LODOP.GET_PRINTER_COUNT();
                var flag;
                // console.log("iCount", iCount);
                for (var i = 0; i < iCount; i++) {
                    console.log("i", i, LODOP.GET_PRINTER_NAME(i));
                    if ((LODOP.GET_PRINTER_NAME(i)).indexOf(name) >= 0) {
                        LODOP.SET_PRINTER_INDEX(i);
                        flag = true;
                        break;
                    }
                }

                if (!flag) {
                    alert("未找到 " + name + " 打印机")
                }

            }

            return {

                init: function() {
                    return LODOP = LODOP || (getLodop && getLodop());

                },
                  printData: function(data) {
                    if (!this.init()) {
                        return;
                    }
                    console.log(data)

                    LODOP.PRINT_INIT("打印登记页二维码标签");
                    setPrinter(LABEL_PRINTER_NAME);

                  try {
                    var config = Config.printData; //获取json配置文件

                    LODOP.SET_PRINT_PAGESIZE(config.PAGESIZE.intOrient, config.PAGESIZE.PageWidth, config.PageHeight, ""); //SET_PRINT_PAGESIZE(intOrient, PageWidth,PageHeight,strPageName)
                    for(var k in config.STYLE){
                      LODOP.SET_PRINT_STYLE(k, config.STYLE[k]);//设置fontsize fontweight
                    }
                    LODOP.NewPage();
                    LODOP.SET_PRINT_STYLEA(0, "QRCodeVersion", 5); //设置二维码QRCode版本值，其决定容量
                    LODOP.SET_PRINT_STYLEA(0, "QRCodeErrorLevel", "H"); //设置二维码QRCode纠错等级

                    LODOP.ADD_PRINT_BARCODE(config.BARCODE.top, config.BARCODE.left, config.BARCODE.width, config.BARCODE.height, config.BARCODE.type, data.pathNo); //打印二维码
                    for(var i=0; i<config.TEXT.length; i++){
                      var item = config.TEXT[i];
                      LODOP.ADD_PRINT_TEXT(item.top, item.left, item.width, item.height, item.strContent); //Top,Left,Width,Height,strContent //打印字符串
                    }
                    LODOP.ADD_PRINT_TEXT(config.DATA.top, config.DATA.left, config.DATA.width, config.DATA.height, data.pathNo); //Top,Left,Width,Height,strContent //todo 打印变量

                  } catch(err) {
                    // console.error("错误名称: " + err.name+" ---> ");
                    // console.error("错误信息: " + err.message+" ---> ");
                    LODOP.SET_PRINT_PAGESIZE(1, 600, 425, "");
                    LODOP.SET_PRINT_STYLE("FontSize", 12);
                    LODOP.SET_PRINT_STYLE("Bold", 2);
                    LODOP.NewPage();
                    LODOP.SET_PRINT_STYLEA(0, "QRCodeVersion", 5);
                    LODOP.SET_PRINT_STYLEA(0, "QRCodeErrorLevel", "H");
                    LODOP.ADD_PRINT_BARCODE(10, 10, 100, 100, "QRCode", data.pathNo);
                    LODOP.ADD_PRINT_TEXT(15, 105, 130, 30, data.patientName);
                    LODOP.ADD_PRINT_TEXT(38, 105, 130, 30, data.pathNo);
                    if(data.frozenNumber){
                      LODOP.ADD_PRINT_TEXT(61, 105, 130, 30, "冰冻号");
                      LODOP.ADD_PRINT_TEXT(84, 105, 130, 30, data.frozenNumber);
                    }
                  } finally {

                  }

                    // LODOP.PREVIEW();

                    LODOP.PRINT();
                },
                printSlide: function(data) {
                  if (!this.init()) {
                    return;
                  }
                  console.log(data,"打印玻片标签"); //22mm * 22mm
                  LODOP.PRINT_INIT("打印玻片标签");
                  setPrinter(LABEL_PRINTER_NAME);
                  try {
                    var config = Config.printSlide; //获取json配置文件

                    LODOP.SET_PRINT_PAGESIZE(config.PAGESIZE.intOrient, config.PAGESIZE.PageWidth, config.PageHeight, ""); //SET_PRINT_PAGESIZE(intOrient, PageWidth,PageHeight,strPageName)
                    for(var k in config.STYLE){
                      LODOP.SET_PRINT_STYLE(k, config.STYLE[k]);//设置fontsize fontweight
                    }
                    LODOP.NewPage();
                    LODOP.SET_PRINT_STYLEA(0, "QRCodeVersion", 5); //设置二维码QRCode版本值，其决定容量
                    LODOP.SET_PRINT_STYLEA(0, "QRCodeErrorLevel", "H"); //设置二维码QRCode纠错等级

                    LODOP.ADD_PRINT_BARCODE(config.code.top, config.code.left, config.code.width, config.code.height, config.code.type, data.code); //todo 打印二维码
                    LODOP.ADD_PRINT_TEXT(config.serialNumber.top, config.serialNumber.left, config.serialNumber.width, config.serialNumber.height, data.pathologySerialNumber); //Top,Left,Width,Height,strContent //todo 打印病理号
                    LODOP.ADD_PRINT_TEXT(config.subId.top, config.subId.left, config.subId.width, config.subId.height, data.subId); //Top,Left,Width,Height,strContent //todo 打印蜡块号
                    LODOP.ADD_PRINT_TEXT(config.marker.top, config.marker.left, config.marker.width, config.marker.height, data.marker); //Top,Left,Width,Height,strContent //todo 打印标记物
                    LODOP.ADD_PRINT_TEXT(config.bodyPart.top, config.bodyPart.left, config.bodyPart.width, config.bodyPart.height, data.bodyPart); //Top,Left,Width,Height,strContent //todo 打印取材部位
                    LODOP.ADD_PRINT_TEXT(config.logo.top, config.logo.left, config.logo.width, config.logo.height, config.logo.text); //Top,Left,Width,Height,strContent //todo 打印标识 pumch


                  } catch(err) {
                    // console.error("错误名称: " + err.name+" ---> ");
                    // console.error("错误信息: " + err.message+" ---> ");
                    ////显示病理号 蜡块号 二维码 p.u.m.c.h
                    LODOP.SET_PRINT_PAGESIZE(1, '2.2cm', '2.2cm', "");
                    LODOP.SET_PRINT_STYLE("FontSize", '10');
                    // LODOP.SET_PRINT_STYLE("Bold", 3);
                    LODOP.NewPage();
                    LODOP.SET_PRINT_STYLEA(0, "QRCodeVersion", 5);
                    LODOP.SET_PRINT_STYLEA(0, "QRCodeErrorLevel", "H");

                    LODOP.ADD_PRINT_TEXT('0', '0.1cm', '3cm', '0.3cm', data.pathologySerialNumber); //Top,Left,Width,Height,strContent  病理号
                    LODOP.ADD_PRINT_TEXT('0.3cm', '1cm', '3cm', '0.3cm', data.subId); //Top,Left,Width,Height,strContent  蜡块号
                    LODOP.ADD_PRINT_BARCODE('0.6cm', '0.5cm', '1.0cm', '1.0cm', "QRCode", data.code);
                    LODOP.ADD_PRINT_TEXT('1.6cm', '0.5cm', '1.6cm', '0.3cm', ""); //Top,Left,Width,Height,strContent 【协和医院自定义的标识 PUMCH 】
                  }

                  // LODOP.PREVIEW();

                  LODOP.PRINT();
                },
                printLabel: function(dataList) {

                    if (!this.init()) {
                        return;
                    }
                    console.log("开始打印二维码标签");
                    try {
                      dataList.forEach(function(data) {
                        var config = Config.printLabel; //获取json配置文件

                        LODOP.PRINT_INIT("打印二维码标签");
                        LODOP.SET_PRINT_PAGESIZE(config.PAGESIZE.intOrient, config.PAGESIZE.PageWidth, config.PageHeight, ""); //SET_PRINT_PAGESIZE(intOrient, PageWidth,PageHeight,strPageName)
                        for(var k in config.STYLE){
                          LODOP.SET_PRINT_STYLE(k, config.STYLE[k]);//设置fontsize fontweight
                        }
                        LODOP.NewPage();
                        LODOP.SET_PRINT_STYLEA(0, "QRCodeVersion", 5); //设置二维码QRCode版本值，其决定容量
                        LODOP.SET_PRINT_STYLEA(0, "QRCodeErrorLevel", "H"); //设置二维码QRCode纠错等级

                        LODOP.ADD_PRINT_BARCODE(config.code.top, config.code.left, config.code.width, config.code.height, config.code.type, data.serialNumber); //todo 打印二维码
                        LODOP.ADD_PRINT_TEXT(config.sampleNameText.top, config.sampleNameText.left, config.sampleNameText.width, config.sampleNameText.height, config.sampleNameText.text); //Top,Left,Width,Height,strContent //todo打印样本名称
                        LODOP.ADD_PRINT_TEXT(config.sampleName.top, config.sampleName.left, config.sampleName.width, config.sampleName.height, data.name); //Top,Left,Width,Height,strContent //todo 打印样本名称
                        LODOP.ADD_PRINT_TEXT(config.sampleCodeText.top, config.sampleCodeText.left, config.sampleCodeText.width, config.sampleCodeText.height, config.sampleCodeText.text); //Top,Left,Width,Height,strContent //todo打印样本编号
                        LODOP.ADD_PRINT_TEXT(config.sampleCode.top, config.sampleCode.left, config.sampleCode.width, config.sampleCode.height, data.serialNumber); //Top,Left,Width,Height,strContent //todo 打印样本编号

                        // LODOP.PREVIEW();
                        LODOP.PRINT();
                      })

                    } catch(err) {
                      console.error("错误名称: " + err.name+" ---> ");
                      console.error("错误信息: " + err.message+" ---> ");
                      dataList.forEach(function(data) {
                       // console.log(data)
                       LODOP.PRINT_INIT("打印二维码标签");
                       setPrinter(LABEL_PRINTER_NAME);

                       LODOP.SET_PRINT_PAGESIZE(1, 600, 425, "");
                       LODOP.SET_PRINT_STYLE("FontSize", 8);
                       LODOP.SET_PRINT_STYLE("Bold", 2);
                       LODOP.NewPage();
                       LODOP.SET_PRINT_STYLEA(0, "QRCodeVersion", 5);
                       LODOP.SET_PRINT_STYLEA(0, "QRCodeErrorLevel", "H");
                       LODOP.ADD_PRINT_BARCODE(10, 10, 100, 100, "QRCode", data.serialNumber)
                       LODOP.ADD_PRINT_TEXT(15, 105, 130, 30, "样本名称");
                       LODOP.ADD_PRINT_TEXT(38, 105, 130, 30, data.name);
                       LODOP.ADD_PRINT_TEXT(61, 105, 130, 30, "样本编号");
                       LODOP.ADD_PRINT_TEXT(84, 105, 130, 30, data.serialNumber);
                       //LODOP.ADD_PRINT_LINE(110,0,110,200,0,1);
                       // LODOP.PREVIEW();
                       LODOP.PRINT();
                       })
                    }


                    //setPrinter("PDF");

                },

                printDomByImg: function(imgData, w, h) {

                    if (!this.init()) {
                        return;
                    }

                    var img = "<img src=" + imgData + "/>";
                    print(img);
                    //print(strStyleCSS+dom.html());

                    function print(dom) {

                        console.log("开始打印");

                        LODOP.PRINT_INIT("打印A4");
                        // setPrinter("PDF");

                        LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
                        //LODOP.NewPage();
                        var imgEle = $(img);
                        var printHeight = (h / w) * 750
                        LODOP.ADD_PRINT_IMAGE("10", "10", "750", printHeight, dom);
                        LODOP.SET_PRINT_STYLEA(0, "Stretch", 1);

                        //LODOP.ADD_PRINT_LINE(110,0,110,200,0,1);
                        //LODOP.PREVIEW();
                        LODOP.PRINT();
                    }
                },

                printTable: function(table, height) {

                    // console.log(table, height)
                    if (!this.init()) {
                        return;
                    }

                    print(table);

                    function print(dom) {

                        // console.log("开始打印");

                        var strStyleCSS = "<style>*{font-size:14px;font-family: '微软雅黑';}h3{font-size:16px}.text-right{text-align:right}.text-center{text-align:center}table{width:100%;border:solid 1px black;border-collapse:collapse} th,td{border:1px solid #000;text-align:left;padding:3px}</style>";
                        var strFormHtml = strStyleCSS + "<body>" + dom + "</body>"

                        LODOP.PRINT_INIT("打印A4");

                        LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
                        LODOP.ADD_PRINT_HTM("10", "10", "750", height, strFormHtml);

                        // LODOP.PREVIEW();
                        LODOP.PRINT();
                    }
                },

                printDomByHtml: function(dom) {

                    if (!this.init()) {
                        return;
                    }


                    //print(strStyleCSS+dom.html());

                    function print(dom) {

                        console.log("开始打印");

                        LODOP.SET_SHOW_MODE("SETUP_ENABLESS", "11111111110001");

                        LODOP.PRINT_INIT("打印A4");
                        // setPrinter("PDF");

                        LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
                        //LODOP.NewPage();
                        var imgEle = $(img);
                        var printHeight = (h / w) * 750
                        LODOP.ADD_PRINT_IMAGE("10", "10", "750", printHeight, dom);
                        LODOP.SET_PRINT_STYLEA(0, "Stretch", 1);

                        //LODOP.ADD_PRINT_LINE(110,0,110,200,0,1);
                        //  LODOP.PREVIEW();
                        LODOP.PRINT();
                    }
                },

                printPathologicalReport: function(html, cb) {
                    if (!this.init()) {
                        cb && cb();

                        return;
                    }


                    if ($('.modal:first-child')[0]) {
                        $('.modal:first-child')[0].scrollTop = 0;
                    } else {
                        window.scrollTop = 0;
                    }
                    // $timeout(function () {
                    //通过css打印
                    //var strStyleCSS="<link type='text/css' rel='stylesheet' href= '"+$('link:last-child')[0].href+"'>";


                    var strStyleCSS = "<style>" +
                      ".report-logo{ position: absolute;top: 5px;left: 5px;height: 70px; } .reportBigName{ margin: 1px 0;font-size: 18px; } .report-subheading{ margin: 2px 0;font-size: 18px; } .reportSmallName{ font-size: 16px; font-weight: 700;margin-top: 0px; } .report-line{ border-bottom: 1px solid; margin-bottom: 5px; } .report-line-2{ border-top: 1px solid; padding-top: 4px; margin-top: 4px; margin-bottom:10px; } .report-margin-bottom{ margin-bottom: 5px; } .info-item{ width:25%; float: left; margin-bottom: 5px; } .diagnose-content { margin: 15px 0; } .content-name { margin-bottom: 5px; } .text-center{ text-align: center; } .pull-right{ float: right; } .pull-left{ float: left; } .col-sm-4 { width: 33.3333333333%; float: left; } .no-padding{ padding:0; } .clearfix { *zoom: 1; } .clearfix:before, .clearfix:after { display: table; line-height: 0; content: ''; } .clearfix:after { clear: both; }"+

                  "* {font-size: 12px;font-family: '微软雅黑';}body{position:relative;width:780px}.col-sm-4{width:33.3%}.pull-right{float:right} #report-body{padding:20px 0;} h3 {font-size: 24px;font-weight: 200}br {padding: 10px}fieldset {border: none;padding: 0}.pull-left {float: left !important;}.text-center {text-align: center}.clearfix:before,.clearfix:after {content: ' ';display: table;}.clearfix:after {clear: both;}p {margin-bottom: 0;}.logo {position: absolute;top: 5px;left: 5px;height: 100px;height: 70px;}pre.hospital-info {background-color: transparent;border: none;font-family: '微软雅黑';}.report-template {overflow-y: auto;height: auto;position: relative;width: 780px;}.report-template .patient-id {border-bottom: 1px solid;}.report-template .patient-id .div-table {width: 25%;border: none;}.report-template .patient-id .div-table .name {width: 40%;float: left;border: none;padding: 3px 0;}.report-template .patient-id .div-table .content {padding: 3px 0;float: right;width: 55%;border: none;}.report-template .patientInfo-container {border-bottom: 1px solid;border-right: none;}.report-template .patientInfo-container .div-table {width: 25%;float: left;}.report-template .patientInfo-container .div-table .name {float: left;border: none;padding: 3px 0;}.report-template .patientInfo-container .div-table .content {padding: 3px 0;float: left;border: none;}.report-template .jujianImg {width: 100px;height: 70px;}.report-template .xianwei {width: 75px;height: 50px;padding-right: 10px;float: left;}" +
                        "</style>"
                        //console.log(strStyleCSS)

                    //通过转canvas转img打印

                    //var width = $('#pathologicalForm').width();
                    //var height = $('#pathologicalForm').height();
                    //console.log(width,height)

                    var dom = $(html);
                    $("body").append('<div id="test" style="opacity:0"></div>');
                    $("#test").append(dom);

                    var header = dom.find('#report-header').html();
                    var logoContainer = dom.find('#logo-container').html();

                    var footer = dom.find('#report-footer').html();
                    var keepHtml = dom.find('#keepHtml').html();
                    var newPageDomList = [];
                    var newPageDom = $('<div style="width:780px">' + logoContainer + header + '<div id=" report-body">' + keepHtml + '<div id="diagnosis"><div>诊断内容：</div></div></div></div>');
                    var maxTop = 1000;
                    var divNum = dom.find('.diagnosis div').length;

                    console.info("诊断内容的高度",dom.position())

                    if (dom.offsetHeight > maxTop && divNum){
                      //如果诊断内容起始高度大于maxTop则需要进入下一页
                      dom.find('.diagnosis div').each(function(i, e) {

                        var jqEle = $(e);
                        var domStr = "";
                        console.log("jqEle.position().top", jqEle.position().top);

                        //判断如果诊断内容多了，且没有新建过页面则新建
                        if (jqEle.position().top > maxTop) {
                          if (newPageDomList.length === 0) {
                            createNewPage();
                          }
                          $("#test").append(newPageDom).css("dispaly", "none");
                          newPageDom.find("#diagnosis").append(e.innerHTML);

                          //如果一页放不下则另起一页
                          if (newPageDom.height() > maxTop) {
                            createNewPage();
                          }
                        } else {
                          newPageDom.find("#diagnosis").append(e.innerHTML);
                        }

                        if (i === divNum - 1) {
                          createNewPage(true)
                        }

                      });
                    }else {
                      printReport(strStyleCSS + "<body>" + html + "</body>", 0)
                      $('#test').remove();
                    }


                    function createNewPage(ifEnd) {
                        if (ifEnd) {
                            newPageDomList.push('<div class="report-template">' + newPageDom.html() + footer + '</div>')

                        } else {
                            newPageDomList.push('<div class="report-template">' + newPageDom.html() + '</div>');
                            newPageDom = $('<div>' + header + '<div id="report-body"><div id="diagnosis"></div></div></div>');

                        }
                    }

                    console.log("newPageDomList", newPageDomList)


                    newPageDomList.forEach(function(html, i) {
                        printReport(strStyleCSS + "<body>" + html + "</body>", i)
                        $('#test').remove();

                    });


                    function printReport(dom, i) {

                        //console.log(dom,code)

                        console.log("开始打印报告");

                        LODOP.PRINT_INIT("打印报告");
                        // setPrinter("PDF");

                        LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
                        LODOP.SET_PRINT_STYLE("FontSize", 12);
                        //LODOP.ADD_PRINT_IMAGE("90", "0%", "RightMargin:1%", "BottomMargin:10px", dom);
                        LODOP.ADD_PRINT_HTM("5", "5", "780", "98%", dom);
                        LODOP.ADD_PRINT_HTM("290mm", "0", "100%", "20", "<div style='text-align:center;color:#ccc;font-size:12px'>第" + (i + 1) + "页/共" + newPageDomList.length + "页<div>");

                        //LODOP.ADD_PRINT_LINE(110,0,110,200,0,1);
                        // LODOP.PREVIEW();
                        LODOP.PRINT();
                        cb && cb();
                    }

                },

                printPathologicalForm: function(selector, serialNumber, cb) {
                    if (!this.init()) {
                        cb && cb();

                        return;
                    }


                    if ($('.modal:first-child')[0]) {
                        $('.modal:first-child')[0].scrollTop = 0;
                    } else {
                        window.scrollTop = 0;
                    }
                    // $timeout(function () {
                    //通过css打印
                    //var strStyleCSS="<link type='text/css' rel='stylesheet' href= '"+$('link:last-child')[0].href+"'>";
                    var strStyleCSS = "<style>" +
                        "* {font-size:12px;font-family: '微软雅黑';}" +
                        "table td,th{text-align: center;vertical-align: middle;border-collapse: collapse;border-spacing: 0;line-height:24px}" +
                        ".table {width: 100%;max-width: 100%;margin-bottom: 20px;border-collapse: collapse;border-spacing: 0;}" +
                        ".table-responsive {width: 100%;margin-bottom: 15px;overflow-y: hidden;-ms-overflow-style: -ms-autohiding-scrollbar;overflow-x: auto;min-height: 0.01%;}" +
                        ".table-responsive > .table-bordered {border: 0;}" +
                        "#pathologicalForm tbody{padding :5px}" +
                        ".table-bordered > thead > tr > th{white-space: nowrap;border: 1px solid #555;}" +
                        ".table-bordered > tbody > tr > th{white-space: nowrap;border: 1px solid #555;}" +
                        ".table-bordered > tbody > tr > td{white-space: nowrap;border: 1px solid #555;}" +

                        "input{border:none;outline: none;border-bottom: 1px solid #000;background: red;}" +
                        "table input,select,textarea{border:none;outline: none;width:100%;height:100%;padding:0;margin:0;}" +
                        "table textarea{resize: none; width:100%;height:100%;vertical-align: middle;overflow-y:visible;}" +

                        "</style>"
                        //console.log(strStyleCSS)

                    //通过转canvas转img打印

                    //var width = $('#pathologicalForm').width();
                    //var height = $('#pathologicalForm').height();
                    //console.log(width,height)

                    var dom = $(selector).clone();
                    $(dom).find(selector + ' form').css('width', 750)
                    $(dom).find(selector + ' form').removeClass('disabled')
                    $(dom).find('.hideWhenPrint').remove()
                    $(dom).find('.showWhenPrint').show()

                    $(dom).find('input').each(function(i, e) {
                        var text = e.value;
                        $(e).before('<div>' + text || " " + '</div>');
                        $(e).remove();
                    })

                    /*$(dom).find('select').each(function(i,e){
                     var text=$(e).find("option:checked").text()==="请选择"?"":$(e).find("option:checked").text();
                     console.log("text",text)
                     $(e).before('<div>'+text||""+'</div>');
                     $(e).remove();
                     })*/

                    //console.log(dom.html())

                    printTable(strStyleCSS + dom.html(), serialNumber)

                    /*html2canvas($('#pathologicalForm'), {
                     width: width * 2,
                     height: height * 2,
                     background: "rgba(255,255,255,1)",
                     onrendered: function (canvas) {
                     var imgSrc = canvas.toDataURL("image/jpg");
                     //console.log(imgSrc);
                     //applicationMod.img=imgSrc;
                     var img = "<img width='800' src='" + canvas.toDataURL("image/png") + "'>"
                     //console.log(img)
                     printTable(img, serialNumber);
                     cb && cb();
                     }
                     });*/
                    function printTable(dom, code) {

                        //console.log(dom,code)

                        console.log("开始打印表格");

                        LODOP.PRINT_INIT("打印表格");
                        // setPrinter("PDF");

                        LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
                        LODOP.SET_PRINT_STYLE("FontSize", 12);
                        //LODOP.NewPage();
                        LODOP.ADD_PRINT_BARCODE(10, 550, 80, 80, "QRCode", code)
                        LODOP.ADD_PRINT_TEXT(20, 640, 100, 30, "病理申请号");
                        LODOP.ADD_PRINT_TEXT(45, 640, 100, 30, code);
                        //LODOP.ADD_PRINT_IMAGE("90", "0%", "RightMargin:1%", "BottomMargin:10px", dom);
                        LODOP.ADD_PRINT_HTM("90", "10", "740", "270mm", dom);
                        //LODOP.ADD_PRINT_LINE(110,0,110,200,0,1);
                        // LODOP.PREVIEW();
                        LODOP.PRINT();
                        cb && cb();
                    }

                },

                printDom: function (domStr,title) {
                  if (!this.init()) {
                    return;
                  }

                  LODOP.PRINT_INIT(title);
                  // setPrinter("PDF");

                  LODOP.SET_PRINT_PAGESIZE(3, 0, 0, "A4");
                  LODOP.SET_PRINT_STYLE("FontSize", 12);

                  //LODOP.ADD_PRINT_IMAGE("90", "0%", "RightMargin:1%", "BottomMargin:10px", dom);
                  LODOP.ADD_PRINT_HTM("90", "10", "740", "270mm", domStr);
                  //LODOP.ADD_PRINT_LINE(110,0,110,200,0,1);
                  // LODOP.PREVIEW();
                  LODOP.PRINT();
                },

                labWrite: function(id, type) {
                    var deferred = $q.defer();
                    $http({
                        method: 'get',
                        url: printUrl,
                        params: { id: id, type: type }
                    }).then(function(result) {
                        // console.log(result);

                    });
                    return deferred.promise;
                }
            }

        }]);
})();
