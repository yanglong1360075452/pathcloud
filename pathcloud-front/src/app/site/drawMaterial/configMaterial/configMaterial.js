/**
 * Created by Administrator on 2016/12/5.
 */
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('ConfigMaterialController', ConfigMaterialController);

  /** @ngInject */
  function ConfigMaterialController(MaterialService,toastr,toolService,IhcService){
    var configMaterial=this;

    
    configMaterial.activeMenu=[true,false,false];
    configMaterial.showPart=0;

    configMaterial.choseMenu=function (index) {
      configMaterial.activeMenu.forEach(function (boolean,i) {
        configMaterial.activeMenu[i]=false;
      });
      configMaterial.activeMenu[index]=true;
      configMaterial.showPart=index;
    };
    
    /*
    *    设置页面
    * */
    function initFrozenSetting(){
      IhcService.get("/paramSetting/frozenCountType").then(function (result) {
        configMaterial.frozenCountType = result;
      });
    }
    initFrozenSetting();
  

    configMaterial.changeFrozenCountType = function (code) {
      IhcService.put("/paramSetting/frozenCountType/"+code).then(function (result) {
        toastr.success("修改成功")
        initFrozenSetting()
      })
    };
  
  
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
