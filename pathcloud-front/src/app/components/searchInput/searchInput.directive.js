/**
 * Created by Administrator on 2016/12/1.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .directive('searchInput', searchInput);

  /** @ngInject */

  function searchInput() {
    var directive = {
      restrict: 'E',
      scope: {
        model: '=',
        getData: '&',
        changeFn: '&',
        clear: '@',
        placeholder: '@'
      },
      template:
        '<div class="input-group full-width"> ' +
          '<input id="searchInput" class="form-control" type="text" ng-model="search.model" ng-keyup="search.getFilterList($event)"  auto-focus="true" placeholder="{{search.placeholder}}"> ' +
          '<span id="searchSpan" class="input-group-btn"> ' +
            '<button class="btn btn-default" type="button" id="searchButton" click-debounce ng-click="search.getSearchData()"><span class="glyphicon glyphicon-search"></span></button> ' +
          '</span> ' +
        '</div>',
      controller: SearchInputController,
      controllerAs: 'search',
      bindToController:true
    };
    return directive;

    /** @ngInject */

    function SearchInputController($timeout,$scope) {
      var search = this;
      var autoSearch = true;
      var markSearchTime = Date.now();
      // console.warn(search.placeholder);

      search.getFilterList=function (e) {
        var keyCode = window.event?e.keyCode:e.which;

        // console.warn(keyCode);
        search.changeFn();
        // console.info(e);
        autoSearch=false;
        if(keyCode==13){

            search.getSearchData();
        }
      };


      search.getSearchData=function () {
        // console.log("Date.now()",Date.now(),"markSearchTime",markSearchTime)

        if(Date.now()-markSearchTime>500){
          search.getData();
          $('#searchInput').focus();
          //搜索完直接清空
          $timeout(function () {
            search.model="";
          },500)

          markSearchTime = Date.now();
        }

      };

      function fireKeyEvent(el, evtType, keyCode){
        var doc = el.ownerDocument,
          win = doc.defaultView || doc.parentWindow,
          evtObj;
        if(doc.createEvent){
          if(win.KeyEvent) {
            evtObj = doc.createEvent('KeyEvents');
            evtObj.initKeyEvent( evtType, true, true, win, false, false, false, false, keyCode, 0 );
          }
          else {
            evtObj = doc.createEvent('UIEvents');
            Object.defineProperty(evtObj, 'keyCode', {
              get : function() { return this.keyCodeVal; }
            });
            Object.defineProperty(evtObj, 'which', {
              get : function() { return this.keyCodeVal; }
            });
            evtObj.initUIEvent( evtType, true, true, win, 1 );
            evtObj.keyCodeVal = keyCode;
            if (evtObj.keyCode !== keyCode) {
              console.log("keyCode " + evtObj.keyCode + " 和 (" + evtObj.which + ") 不匹配");
            }
          }
          el.dispatchEvent(evtObj);
        }
        else if(doc.createEventObject){
          evtObj = doc.createEventObject();
          evtObj.keyCode = keyCode;
          el.fireEvent('on' + evtType, evtObj);
        }
      }
    }

  }

})();
