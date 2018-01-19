       /* global malarkey:false, moment:false */
       (function() {
           'use strict';

           angular
               .module('pathcloud')
               //.constant('malarkey', malarkey)
               //.constant('moment', moment)
               .constant('printUrl', 'http://localhost:4000')
               .constant('webServerUrl', 'http://localhost:8887')
               .constant('apiUrl', 'api')

         // .constant('apiUrl', 'https://114.115.211.205/psts/api') //赛默飞测试环境新地址
          // .constant('apiUrl', 'https://114.115.140.214/psts/api') //协和测试环境新地址

         //.constant('apiUrl', 'http://10.68.179.21:8089/psts/api')//刘梅tmf号 "黑色电脑"
         //.constant('apiUrl', 'http://10.68.170.47:8089/psts/api')//刘梅wizzion "新黑色电脑"
         //.constant('apiUrl', 'http://luomo111.free.ngrok.cc/psts/api')//刘梅电脑"
         // .constant('apiUrl', 'http://192.168.137.1:8089/psts/api')//刘梅电脑wifi"

              // .constant('apiUrl', 'http://10.68.178.62:8080/pathcloud/api')//杨龙tmf
              // .constant('apiUrl', 'http://10.68.170.25:8080/pathcloud/api')//杨龙wizzion
              // .constant('apiUrl', 'http://192.168.137.181:8080/pathcloud/api')//杨龙wifi
       })();
