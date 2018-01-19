/**
 * Created by lenovo on 2016/2/20.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('CsvService',function($window){


      return{
        download:function(header,data,name){

          var aLink = document.createElement('a');
          var csvData=new CSV(data, { header: header }).encode();
          var blob = new Blob(["\uFEFF"+csvData],{type:'csv/ANSI'});
          //兼容Chrome,Firefox
          var evt = document.createEvent("MouseEvents");
          evt.initMouseEvent("click", true, false, window, 0, 0, 0, 0, 0, false, false, false, false, 0, null);

          aLink.download = name+".csv";
          aLink.href = URL.createObjectURL(blob);

          //兼容IE
          if (navigator.msSaveBlob) {
            navigator.msSaveBlob(blob, name+".csv");
            aLink.click();
          } else {
            aLink.dispatchEvent(evt);
          }
        },
        tableToExcel:function (tableId,worksheetName) {

          var uri = 'data:application/vnd.ms-excel;base64,',
            template = '<html xmlns:o="urn:schemas-microsoft-com:office:office" xmlns:x="urn:schemas-microsoft-com:office:excel" xmlns="http://www.w3.org/TR/REC-html40"><head><!--[if gte mso 9]><xml><x:ExcelWorkbook><x:ExcelWorksheets><x:ExcelWorksheet><x:Name>{worksheet}</x:Name><x:WorksheetOptions><x:DisplayGridlines/></x:WorksheetOptions></x:ExcelWorksheet></x:ExcelWorksheets></x:ExcelWorkbook></xml><![endif]--></head><body><table>{table}</table></body></html>',
            base64 = function (s) { return $window.btoa(unescape(encodeURIComponent(s))); },
            format = function (s, c) { return s.replace(/{(\w+)}/g, function (m, p) { return c[p]; }) };

          var table = $(tableId),
            ctx = { worksheet: worksheetName, table: table.html() },
            href = uri + base64(format(template, ctx));
          // return href;


          var aLink = document.createElement('a');
          aLink.href = uri + base64(format(template, ctx));
          aLink.download = worksheetName + ".xls";
          aLink.click();

        }

      }

    });
})();

