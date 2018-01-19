/**
 * Created by lenovo on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .factory('DiagnosisService',['$http','$q',function($http,$q,$cookieStore){

      var url = '[api]';

      return{
        //检查特检申请标记物是否重复
        checkSpecialApplyMarker: function (blockId, marker) {
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/specialDye/check/'+blockId +"/"+ marker,
            // params:params,
          }).then(function (result){

            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        // /api/diagnose/deadline
        //   1.1 获取权限内病理报告延期数量
        getDelayTotal:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/diagnose/deadline',
            params:params,
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 2.1 获取待诊断列表
        getDiagnosisList:function(params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/diagnose',
            params:params,
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data);
          });
          return deferred.promise;
        },

        //### 5.1 根据病理号或玻片号获取信息 GET /api/diagnose/{serialNumber}
        getDataByPathology:function(serialNumber, params){
            var deferred = $q.defer();
            $http({
              method: 'get',
              url: url + '/diagnose/' + serialNumber,
              params:params
            }).then(function (result){
              console.log(result);
              if (result.data.code === 0)
                deferred.resolve(result.data.data);
              else
                deferred.reject(result.data);
            });
            return deferred.promise;
          },

        //### 5.2 玻片评分 PUT /api/diagnose/score/{slideId}
        slideScore:function(slideId,data){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/diagnose/score/' + slideId,
            data:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.3 病理诊断 POST /api/diagnose/confirm/{pathId}
        confirmDiagnosis:function(pathId,data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/diagnose/confirm/' + pathId,
            data:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.4 根据病理ID获取蜡块信息 GET /api/diagnose/{pathId}/block
        getBlockDataByPathology:function(pathId){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/diagnose/' + pathId + '/block'
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.5 申请深切 POST /api/diagnose/deep/{blockId}
        applyHeavySection:function(blockId,data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/diagnose/deep/' + blockId,
            data:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.6 病理信息查询 GET /api/diagnose
        getPathologyData:function(data){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/diagnose/query',
            params:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.7 获取诊断医生列表 GET /api/user/diagnose
        getDiagnosisDoctor:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/user/diagnose'
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        getFirstDiagnosisDoctor:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/user/firstDiagnose'
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.8 申请重补取 POST /api/diagnose/regrossing/{pathologyId}
        applyAgainMaterial:function(pathologyId,data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/diagnose/regrossing/' + pathologyId,
            data:data
          }).then(function(result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.9 根据病理ID获取病理显微图像 GET /api/diagnose/file/{pathId}
        getMicroImages:function(pathId){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/diagnose/file/' + pathId
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.10 根据文件ID删除图像 DELETE /api/file/{fileId}
        deleteImages:function(fileId){
          var deferred = $q.defer();
          $http({
            method: 'delete',
            url: url + '/file/' + fileId
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.11 获取报告医生列表 GET /api/user/report
        getReportDoctor:function(){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/user/report'
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.12 根据病理ID获取报告信息 GET /api/report/{pathId}
        getReportInfo:function(pathId,params){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/diagnose/report/'+pathId,
            params: params
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.14 发报告 POST /api/report/{pathId}
        sendReport:function(pathId,data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/diagnose/report/'+pathId,
            data:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.13 根据病理ID获取历史诊断信息 GET /api/diagnose/history/{pathId}
        getDiagnosisInfo:function(pathId){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/diagnose/history/'+pathId
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //###sprint6 2.6 获取具有二级、三级诊断权限的医生  GET /api/user/superDiagnose
          getSuperDiagnoseUser:function(){
            var deferred=$q.defer();
            $http({
              method:'get',
              url:url + '/user/superDiagnose'
            }).then(
              function(result){
                if(result.data.code === 0){
                  deferred.resolve(result.data.data);
                }else{
                  deferred.reject(result.data.reason);
                }
              }
            );
            return deferred.promise;
          },



        // todo 4.1 诊断模板部分开始
        //### 4.1 获取诊断模板  GET  /api/diagnoseTemplate
        //   * __Request__
        //     parent | Integer | 父类别
        //    position | String | 模板位置 1是取材模板 2是诊断模板
        getDiagnoseTemplate:function(parent){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/diagnoseTemplate',
            params:{parent:parent,position:2}
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

       /* ### 4.2 创建诊断模板    POST  /api/diagnoseTemplate
        {
          "name": "wqe",#模板名字
          "parent":1,#父ID
          "templateContentVO": [{
          "projectName":"xxx",,#项目名称
          "projectContent":["xxx","xxx"], #项目内容
          "other":"xxx" #其他
          "projectNameCheck":1 #项目名勾选  0代表项目没勾选 1代表勾选
          "projectContentCheck":1 #1代表复选 0代表单选
        }],
          "level":3
        }*/
        createDiagnoseTemplate:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/diagnoseTemplate',
            data:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ### 4.3 重命名模板 PUT /api/diagnoseTemplate/rename/{id} 模板ID
        // > 请求参数Json格式
        // {
        //   "name":"",#模板名字
        //   "parent":"",#父类ID
        // }
        renameDiagnoseTemplate:function(templateId,data){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/diagnoseTemplate/rename/'+templateId,
            data:{
              "name":data.name,//#模板名字
              "parent":data.parent,//#父类ID
              "position":"2"
            }
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ### 4.4 编辑模板  PUT   /api/diagnoseTemplate/edit/{id}
        //     Param | Type | Description
        // id | Integer | 模板ID
        //         > 请求参数Json格式
        //
        //           ```
        // {
        //     "templateContentVO": [{
        //             "projectName":"xxx",,#项目名称
        //             "projectContent":["xxx","xxx"], #项目内容
        //             "other":"xxx" #其他
        //             "projectNameCheck":1 #项目名勾选  0代表项目没勾选 1代表勾选
        //             "projectContentCheck":1 #1代表复选 0代表单选
        //         }]
        //
        // }
        updateDiagnoseTemplate:function(templateId,data){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/diagnoseTemplate/edit/'+templateId,
            data:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ### 4.5 删除模板  /api/diagnoseTemplate/{id}
        deleteDiagnoseTemplate:function(templateId){
          var deferred = $q.defer();
          $http({
            method: 'delete',
            url: url + '/diagnoseTemplate/'+ templateId
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },//诊断模板部分结束
        //自定义模板

        //### 4.1 获取诊断自定义模板  GET  /api/diagnoseTemplate
        getCustomTemplate:function(parent,position,category){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/template',
            params:{parent:parent,position:position,category:category}
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        //添加模板
        customDiagnoseTemplate:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/template',
            data:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        //重命名模板
        renameCustomTemplate:function(templateId,data){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/template/rename/'+templateId,
            data:{
              "name":data.name,//#模板名字
              "parent":data.parent,//#父类ID
              "position":"5"
            }
          }).then(function (result){
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        //删除模板
        deleteCustomTemplate:function(templateId){
          var deferred = $q.defer();
          $http({
            method: 'delete',
            url: url + '/template/'+ templateId
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
        // todo 7 报告模板部分部分开始
        //### 7.1 获取诊断模板  GET  /api/reportTemplate
        //   * __Request__
        //     parent | Integer | 父类别
        //    position | String | 模板位置 1是取材模板 2是诊断模板 3是特然模板，4是报告摸板
        getReportTemplate:function(parent){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/reportTemplate',
            params:{parent:0,position:4}
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ### 7.2 创建诊断模板    POST  /api/reportTemplate
        createReportTemplate:function(data){
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/reportTemplate',
            data:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ### 7.3 重命名模板 PUT /api/diagnoseTemplate/rename/{id} 模板ID
        // > 请求参数Json格式
        // {
        //   "name":"",#模板名字
        //   "parent":"",#父类ID
        // }
        renameReportTemplate:function(templateId,data){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/reportTemplate/rename/'+templateId,
            data:{
              "name":data.name,//#模板名字
              "parent":data.parent,//#父类ID
            }
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },


        updateReportTemplate:function(templateId,data){
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/reportTemplate/edit/'+templateId,
            data:data
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ### 4.5 删除模板  /api/reportTemplate/{id}
        deleteReportTemplate:function(templateId){
          var deferred = $q.defer();
          $http({
            method: 'delete',
            url: url + '/reportTemplate/'+ templateId
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },//报告模板部分结束

        // /api/diagnose/deadline
        //   4.1 获取诊断页辅助检查功能 申请特染蜡块号的接口
        getSpecialDyeBlocks:function(pathId){
          var deferred = $q.defer();
          $http({
            method: 'get',
            url: url + '/diagnose/'+pathId+'/dye',
            // params:params,
          }).then(function (result){
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },
      }
    }]);
})();
