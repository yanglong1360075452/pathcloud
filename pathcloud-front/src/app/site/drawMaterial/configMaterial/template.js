/**
 * Created by Administrator on 2016/12/5.
 */
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('ConfigMaterialTemplateController', ConfigMaterialTemplateController);

  /** @ngInject */
  function ConfigMaterialTemplateController(MaterialService,toastr,toolService,IhcService){
    var configMaterial=this;

    configMaterial.readFlag = true;
    configMaterial.activeMenu=[true,false,false];
    configMaterial.showPart=0;

    configMaterial.choseMenu=function (index) {
      configMaterial.activeMenu.forEach(function (boolean,i) {
        configMaterial.activeMenu[i]=false;
      });
      configMaterial.activeMenu[index]=true;
      configMaterial.showPart=index;
    };

    //获取科室
    MaterialService.getDepartments().then(function (departments) {
      configMaterial.departmentList=departments;
      configMaterial.choseMaterial(departments[0].code);
    });

    //选择科室
    configMaterial.choseMaterial=function (code) {
      configMaterial.chosedMaterial=code;
      MaterialService.getTemplate(code,1).then(function (templates) {
        templates.forEach(function (item) {
          item.parent=code;
        })
        configMaterial.templateList=templates;
      })
    };

    //选择模板
    configMaterial.choseTemplate=function (index) {
      configMaterial.chosedTemIndex=index;
      // configMaterial.content=configMaterial.templateList[index]
    };

    //增加模板test
    configMaterial.addTemplate=function () {
      configMaterial.templateList.push(
        {name:"",content:"", parent:configMaterial.chosedMaterial,level:1,newItem:true,position:1});
      configMaterial.templateList[configMaterial.templateList.length-1].focus=true;
      configMaterial.choseTemplate(configMaterial.templateList.length-1);
      console.log("输出当前滚动条位置",window.x,window.y);
      window.scrollTo(window.x,window.y+25);
    };

    //保存模板
    configMaterial.saveTemplate=function (item) {
      if(item.name){
        delete item.focus;
        if(item.newItem){
          delete item.newItem;

          MaterialService.createTemplate(item).then(function (template) {
            console.log(" createTemplate success")
            item.id=template.id;
            toastr.success("添加模板成功");

          },function (err) {
            toastr.error(err);
            item.newItem=true;
            item.focus=true;
          });
        }else{
          MaterialService.renameTemplate(item.id,item).then(function () {
            console.log(" updateTemplate success")
            toastr.success("更新模板成功");

          },function (err) {
            toastr.error(err);
            item.focus=true;

          });
        }
      }else{
        if(item.newItem){
          configMaterial.templateList.pop();
        }else{
          toastr.error('模板名称不能为空');
          item.focus=true;
        }
      }

    };

    configMaterial.saveTemplateContent=function (item) {
      console.log("configMaterial.chosedTemIndex",configMaterial.chosedTemIndex)
      MaterialService.updateTemplate(item.id,item.content)
        .then(function () {
          toastr.success("修改成功");
          console.log("修改成功")
        })
    };

    //删除模板
    configMaterial.deleteTemplate=function () {
      if(configMaterial.templateList[configMaterial.chosedTemIndex]){
        toolService.getModalResult({modalTitle:"删除确认",modalContent:"是否确定删除该模板？"})
          .then(function () {
            MaterialService.deleteTemplate(configMaterial.templateList[configMaterial.chosedTemIndex].id)
              .then(function () {
                toastr.success("删除模板成功");

                configMaterial.templateList.splice(configMaterial.chosedTemIndex,1)
              })
          })
      }
    };

    configMaterial.changeText=function (){
      if(configMaterial.focusText&&configMaterial.chosedTemIndex>=0){
        configMaterial.saveTemplateContent(configMaterial.templateList[configMaterial.chosedTemIndex])
      }
      configMaterial.focusText=!configMaterial.focusText;
    };

    /*
    *
    *
    *    设置页面
    *
    * */
    function initFrozenSetting(){
      
      
      IhcService.get("/paramSetting/frozenCountType").then(function (result) {
        configMaterial.frozenCountType = result;
      });
  
      /*IhcService.get("/systemSetting/usingFrozen").then(function (result) {
        configMaterial.usingFrozen = result;
      });
      
      IhcService.get("/systemSetting/printFrozen").then(function (result) {
        configMaterial.printFrozen = result;
      });*/
      
      
    }
    initFrozenSetting();
  

    configMaterial.changeFrozenCountType = function (code) {
      IhcService.put("/paramSetting/frozenCountType/"+code).then(function (result) {
        toastr.success("修改成功")
        initFrozenSetting()
      })
    };
/*    configMaterial.changeUsingFrozen = function (code) {
      IhcService.put("/systemSetting/usingFrozen/"+code).then(function (result) {
      
        toastr.success("修改成功")
        initFrozenSetting()
      })
    };
    configMaterial.changeFrozenPrint = function (code) {
      IhcService.put("/systemSetting/printFrozen/"+code).then(function (result) {
      
        toastr.success("修改成功")
        initFrozenSetting()
      })
    };*/
  
  
  
    // 获取取材备注
    IhcService.get("/paramSetting/grossingNote").then(function (result) {
      configMaterial.noteList = result;
    });
    // 添加取材备注
    configMaterial.addNote=function () {
      IhcService.post("/paramSetting",{name:configMaterial.newNote,param:"grossingNote"}).then(function (data) {
        configMaterial.noteList.push(data)
        configMaterial.addNewNote=false;
        configMaterial.newNote="";
        toastr.success("添加取材备注成功");
      },function (err) {
        toastr.error(err);

      })
    };
    // 删除取材备注
    configMaterial.delNote=function (code,index) {
      if(code){
        toolService.getModalResult({modalTitle:"删除确认",modalContent:"是否确定删除？"}).then(
          function () {
            IhcService.delete("/paramSetting/grossingNote/"+code).then(function (data) {
              console.log("删除成功");
              configMaterial.noteList.splice(index,1)

            },function (err) {
              toastr.error(err);

            })
          }
        )

      }

    };

    //获取取材确认拍照设置
    IhcService.get("/paramSetting/grossingConfirmPhoto").then(function (res) {
      configMaterial.grossingConfirmPhoto = res;
    },function (err) {
      toastr.error(err.reason)
    });
    // sprint_13 2.2 修改取材确认拍照设置 /api/paramSetting/grossingConfirmPhoto/{code}   Param : #1-必须 2-可选
    configMaterial.photoSwitch = function (code) {
      IhcService.put("/paramSetting/grossingConfirmPhoto/"+code).then(function () {
        toastr.success("操作成功")
        configMaterial.grossingConfirmPhoto = code;
      },function (err) {
        toastr.error(err.reason)
      })
    };

    function biaoShiInit() {
      MaterialService.getBiaoshi().then(function (biaoShi) {
        configMaterial.biaoShiList=biaoShi;
      });

      MaterialService.getCountType().then(function (countType) {
        configMaterial.countType=countType;
      });

      MaterialService.getBlockUnit().then(function (blockUnit) {
        configMaterial.blockUnitList=blockUnit;
      });
    }
    biaoShiInit();

    configMaterial.setCountType=function (code) {
      MaterialService.setCountType(code).then(function () {
        toastr.success("设置编码方式成功");
      })
    };

    configMaterial.addBiaoshi=function () {
      MaterialService.addBiaoshi({name:configMaterial.newBisaoshi}).then(function (data) {
        configMaterial.biaoShiList.push(data)
        configMaterial.addNewBiaoshi=false;
        configMaterial.newBisaoshi="";
        toastr.success("添加标识成功");
      },function (err) {
        toastr.error(err);

      })
    };

    configMaterial.addBlockUnit=function () {
      MaterialService.addBlockUnit({name:configMaterial.newBlockUnit}).then(function (data) {
        configMaterial.blockUnitList.push(data)
        configMaterial.addNewBlockUnit=false;
        configMaterial.newBlockUnit="";
        toastr.success("添加组织数单位成功");
      },function (err) {
        toastr.error(err);

      })
    };

    configMaterial.delBiaoshi=function (code,index) {
      if(code){
        toolService.getModalResult({modalTitle:"删除确认",modalContent:"是否确定删除该模板？"}).then(
          function () {
            MaterialService.delBiaoshi(code).then(function (data) {
              console.log("删除标识成功");
              configMaterial.biaoShiList.splice(index,1)

            },function (err) {
              toastr.error(err);

            })
          }
        )

      }

    };

    configMaterial.delBlockUnit=function (code,index) {
      if(code){
        toolService.getModalResult({modalTitle:"删除确认",modalContent:"是否确定删除组织单位？"}).then(
          function () {
            MaterialService.delBlockUnit(code).then(function (data) {
              console.log("删除组织数单位成功");
              configMaterial.blockUnitList.splice(index,1)
            },function (err) {
              toastr.error(err);

            })
          }
        )

      }

    };


  }

})();
