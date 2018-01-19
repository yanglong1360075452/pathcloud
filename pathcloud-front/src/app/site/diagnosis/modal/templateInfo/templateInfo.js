//诊断模板内容弹窗
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('templateInfoController', templateInfoController);

  /** @ngInject */
  function templateInfoController($uibModalInstance,content,toastr){
    var templateInfo = this;
    templateInfo.content = content;//从诊断页面传过来的选中的模板对象
    templateInfo.data = [];//创建一个数组长度与templateInfo.content一样
    templateInfo.checkValue = [];//设置多选时checkbox的值


    //初始化操作
    function init(){
      for(var i=0;i<templateInfo.content.length;i++){
        var obj = {};
        obj.key = templateInfo.content[i].projectName;
        obj.value = "";
        obj.array = [];
        templateInfo.data.push(obj);
      }
      console.log("templateInfo.data",templateInfo.data);
    };
    init();

    //checkbox选中与否拼接字符串
    templateInfo.checkString = function(cItem,item,index){
      if(cItem.check){//添加指定元素
        templateInfo.data[index].array.push(cItem.name);
      }
      if(!cItem.check){//移除指定元素
         _.remove(templateInfo.data[index].array,function(name){
           return cItem.name == name;
         });
      }
      item.projectContentValue = "";
      item.projectContentValue = _.join(templateInfo.data[index].array,',');//将数组拼接成字符串
    };

    //确定
    templateInfo.ok = function(){
      var array = [];// 存储必填项
      var htmlStr = "";//拼接字符串
      //为对象数组templateInfo.data配置key-value值
      for(var i=0;i<templateInfo.content.length;i++){
        if(templateInfo.content[i].projectContentValue){
          templateInfo.data[i].value = templateInfo.content[i].projectContentValue
        }else{
          templateInfo.data[i].value = templateInfo.content[i].other
        }
      }
      //console.log("templateInfo.data",templateInfo.data);//输出结果
      //判断是否必填项都已填写
      for(var j=0;j<templateInfo.data.length;j++){
        if(templateInfo.content[j].projectNameCheck){
          array.push(templateInfo.data[j].value);
        }
      }
      //console.log("array",array);//输出结果
      if((_.compact(array)).length!==array.length){
        toastr.error("有选项未完善，请填写完善后再确定！");
      }else{
        for(var k=0;k<templateInfo.data.length;k++){
          if(templateInfo.data[k].value){
            htmlStr += '<p class="temKey">'+ templateInfo.data[k].key + '：<span class="temValue">'+ templateInfo.data[k].value + '</span>' + '</p>';
          }
        }
        $uibModalInstance.close(htmlStr);
        //console.log("htmlStr",htmlStr);//输出结果
        //console.log("templateInfo.data",templateInfo.data);//输出结果
      }
    };

    //取消
    templateInfo.cancel = function(){
      $uibModalInstance.dismiss();
    };

  }
})();
