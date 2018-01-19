/**
 * Created by lenovo on 2016/8/2.
 */
(function () {
  'use strict';

  angular
    .module('pathcloud')
    .factory('toolService', ['$http', '$q', '$uibModal', 'apiUrl', '$window', function ($http, $q, $uibModal, apiUrl, $window) {
      var url = '[api]';
      var funs;
      return funs = {
        getHtmlImgByTagName: function (tagName, scale, cb) {
          if ($('.modal-content') && $('.modal-content').length) {
            $(".modal-body").scrollTop(0);
            window.scrollTo(0, 0)
          } else {
            window.scrollTo(0, 0)
            // window.scrollTop = 0;
          }

          //清晰度问题

          var dom = $(tagName);

          var width = dom.width();
          var height = dom.height();
          var offsetLeft = dom.offset().left;
          var offsetTop = dom.offset().top;
          console.log("width", width, "height", height)
          var scaleBy = scale || 2;
          var canvas = document.createElement('canvas');
          //canvas.width = (width+offsetLeft) * scaleBy ;
          canvas.width = width * scaleBy;
          canvas.height = height * scaleBy;
          canvas.style.width = width + 'px';
          canvas.style.height = height + 'px';
          //canvas.style.transform = "translate(-"+offsetLeft*2+"px,0)";
          //canvas.style.transform = "translateX("+"-"+offsetLeft/2+"px,0)";
          var context = canvas.getContext('2d');
          context.scale(scaleBy, scaleBy);
          context.translate(-offsetLeft, -offsetTop);

          context.font = 'Microsoft YaHei';


          html2canvas(dom, {
            canvas: canvas,
            //allowTaint: true,
            //taintTest: false,
            onrendered: function (canvas) {
              cb(canvas.toDataURL("image/png"), width, height);
            }
          });
        },
        getImgHeader: function () {
          return apiUrl.substr(0, apiUrl.length - 3)
        },
        //压缩图片返回Base64
        convertImgToSamllBase64: function (img, width, height) {
          function getBase64(url) {
            var defer = $q.defer();
            var img = new Image();
            img.crossOrigin = 'anonymous';
            width = width || 100;
            height = height || 70;
            img.onload = function () {
              var canvas = document.createElement('CANVAS'),
                ctx = canvas.getContext('2d');
              var w = img.naturalWidth,
                h = img.naturalHeight;
              canvas.width = width;
              canvas.height = height;

              console.warn(w, h, width, height)
              ctx.drawImage(img, 0, 0, w, h, 0, 0, width, height);
              var dataURL = canvas.toDataURL('image/jpeg')
              if (dataURL)
                defer.resolve(dataURL);
              else
                defer.reject(null);
            };
            img.src = funs.getImgHeader() + url;
            //img.src = "http://i.imgur.com/eekEotAb.jpg";
            return defer.promise;
          }

          if (!img) {
            return $q.all();
          } else if (typeof img === "string") {
            return getBase64(img);
          } else {
            return (function () {
              var promiseList = [];
              img.forEach(function (item) {
                promiseList.push(getBase64((typeof item === "string" ? item : item.url)))
              })
              return $q.all(promiseList);
            })()
          }
        },

        getOrderByList: function (length) {
          var item = {order: 0, sort: "desc"};
          var list = [];
          for (var i = 0; i < length; i++) {
            item.order = i;
            list.push(item);
          }
          return list;
        },
        getLocalStorageInfo: function (name) {

          try {
            return JSON.parse($window.localStorage.getItem(name))
          } catch (e) {
            return false;
          }

        },
        setLocalStorageInfo: function (name, data) {
          $window.localStorage.setItem(name, JSON.stringify(data));
        },
        // 通用弹出窗
        getModalResult: function (modal) {
          var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'app/site/modal/commonModal.html',
            controller: 'CommonModalController',
            controllerAs: 'commonModal',
            size: modal.size,
            resolve: {
              modalTitle: function () {
                return modal.modalTitle;
              },
              modalContent: function () {
                return modal.modalContent;
              }
            }
          });
          return modalInstance.result;
        },
        getTipResult: function (modal) {
          var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'app/site/modal/tipModal.html',
            controller: 'TipModalController',
            controllerAs: 'tipModal',
            size: modal.size,
            resolve: {
              modalTitle: function () {
                return modal.modalTitle;
              },
              modalContent: function () {
                return modal.modalContent;
              }
            }
          });

          return modalInstance.result;
        },
        getInputModal: function (modal) {
          var modalInstance = $uibModal.open({
            ariaLabelledBy: 'modal-title',
            ariaDescribedBy: 'modal-body',
            templateUrl: 'app/site/modal/inputModal.html',
            controller: 'InputModalController',
            controllerAs: 'inputMod',
            size: modal.size,
            resolve: {
              modalTitle: function () {
                return modal.modalTitle;
              },
            }
          });

          return modalInstance.result;
        },

        // ### 4.1 取材文件上传 /api/file/{operation}/{pathologyId}
        fileUpload: function (data, operation, id, tag) {
          var deferred = $q.defer();
          $http({
            method: 'post',
            url: url + '/file/' + operation + '/' + id,
            data: {
              file: data,
              tag: tag
            },
            headers: {'Content-Type': undefined},
            transformRequest: function (data) {
              var formData = new FormData();
              formData.append('file', data.file);
              formData.append('tag', data.tag);
              // formData.append('filename', data.filename);

              return formData;
            },

          }).then(function (result) {
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        // ### 4.1 取材文件上传 /api/file/{operation}/{pathologyId}
        fileEditor: function (data, id) {
          var deferred = $q.defer();
          $http({
            method: 'put',
            url: url + '/file/' + id,
            data: {
              file: data
            },
            headers: {'Content-Type': undefined},
            transformRequest: function (data) {
              var formData = new FormData();
              formData.append('file', data.file);
              // formData.append('filename', data.filename);

              return formData;
            },

          }).then(function (result) {
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //### 5.10 根据文件ID删除图像 DELETE /api/file/{fileId}
        fileDelete: function (fileId) {
          var deferred = $q.defer();
          $http({
            method: 'delete',
            url: url + '/file/' + fileId
          }).then(function (result) {
            console.log(result);
            if (result.data.code === 0)
              deferred.resolve(result.data.data);
            else
              deferred.reject(result.data.reason);
          });
          return deferred.promise;
        },

        //通用格式化导出excel的参数
        export: function (filter, exportUrl) {
          if (!filter) return;
          var param = apiUrl + exportUrl + "?";

          function formatParams(filter) {
            for (var c in filter) {
              if (filter[c] != null) {
                param = param + c + "=" + filter[c] + "&";
              }
            }
          }

          formatParams(filter);
          window.open(param, "_self");
        },
        isExistInArrObj: function (arr, key) {
          //根据数组中对象的属性判断是否重复
          // console.log("根据数组中对象的属性判断是否重复")
          var hash = {}, len = arr.length;
          while (len) {
            len--;
            if (hash[arr[len][key]]) {
              //  返回重复的值
              return {
                item: arr[len],
                value: arr[len][key],
                index: len
              }
              // arr[len][key];
            } else {
              hash[arr[len][key]] = arr[len][key];

            }
          }

          return false;
        }


      }// end functions

    }]);
})();
