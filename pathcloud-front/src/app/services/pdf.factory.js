/**
 * Created by lenovo on 2016/8/2.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('pdfService',function($http,$q,$timeout,$log,toolService){
     

      return{
        buildByHtml:function(imgData,w,h,fileName,id) {
            var doc = new jsPDF();

            doc.addImage(imgData, 'JPEG', 10, 10, 190, h/w*180,undefined,'FAST');
            //console.log(imgData)
            toolService.fileUpload(doc.output('dataurlstring'),13,id).then(function(){
              // doc.save(fileName||"诊断报告"); //本地下载
            })

          /*var cssString = "";
          function getCssString(json){
            for(var i in json){
              cssString += json[i].cssText || "";
            }
          }
          getCssString(document.styleSheets[document.styleSheets.length-1].cssRules)
          getCssString(document.styleSheets[document.styleSheets.length-2].cssRules)
          
          var html = "<!DOCTYPE html><head><style>"+cssString+"</style></head><body>"+html+"</body>"


          console.log(html)*/
        }

      }

    });
})();

