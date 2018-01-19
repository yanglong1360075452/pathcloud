/**
 * Created by Administrator on 2016/11/10.
 */
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('UserModalController', UserModalController);

  /** @ngInject */
  function UserModalController($rootScope,$scope,$uibModalInstance,modalTitle,method,UserService,userData,toastr,MaterialService){
    var userMod=this;
    userMod.Title=modalTitle;
    userMod.method=method;
    userMod.check={};
    // userMod.check.staticPassword="qwe123!@#000";
    userMod.data=userData||{};
    if(userMod.method==='get') {userMod.formDisabled=true; }
    if(userMod.method==='post') {userMod.data.status=true; getRolePermission ()}
    if(userMod.method==='put') {userMod.data.password="qwe!@#15316073015"; getRolePermission ()}

    function init() {
      userMod.taskTypeList = [ { name: "国家自然科学基金", value: 0 }, { name: "国家科技部课题", value: 1 }, { name: "省部级计划课题", value: 2 }, { name: "横向课题", value: 3 }, { name: "其他", value: 4 }, ];
      userMod.identityList = [ { name: "本科生", value: 0 }, { name: "硕士研究生", value: 1 }, { name: "博士研究生", value: 2 }, { name: "博士后", value: 3 }, { name: "临床医生", value: 4 }, { name: "研究人员", value: 5 },{ name: "技术人员", value: 6 },{ name: "其他", value: 7 }, ];
      userMod.getDepartments = function (filter) {
        MaterialService.getDepartments(filter).then(function(data) {
          userMod.departmentList = data;
        }); //科室列表
      }
      userMod.getDepartments();
    }
    init();

    userMod.permission=$rootScope.user.permissionCodes['0']


    //check userName
    userMod.checkFn=function (userName) {
      // UserService.check({username:userName}).then(function (ok) {
      UserService.check(userName).then(function (ok) {
        userMod.check.nameErr=ok;
        console.log("=====userMod.check.OK=====",ok,userMod.check.nameErr)
      },function (err) {
        userMod.check.nameErr=err;
        console.log("=====userMod.check.err=====",err,userMod.check.nameErr)
        //console.log("userMod.check.nameErr=====",err)
      })
    }


    function getRolePermission () {
      UserService.getRoleAuthList().then(function (result) {
        //console.log("rolesList=======",result)
        userMod.rolesList=result.data;//新增编辑用户时获取角色权限列表
      })
    };

    userMod.edit=function () {
      userMod.formDisabled=false;
    }
    userMod.ok=function(){
      console.log(method)
      if(method==="post"){
        //console.log("post")
        UserService.createUser(userMod.data).then(function () {
          toastr.success("创建成功")
          $uibModalInstance.close();
        },function (err) {
          userMod.messageErr=err;
        })
      }else if(method==="put"||method==="get"){
        //console.log(method)
        if(userMod.data.password==="qwe!@#15316073015") userMod.data.password=null;
        userMod.placeholder="*****************";
        var userINFO={
          "id":userMod.data.id, //用户ID
          userName:userMod.data.userName,
          password:userMod.data.password,
          firstName:userMod.data.firstName,
          phone:userMod.data.phone,
          email:userMod.data.email,
          unit:userMod.data.unit,
          departments:userMod.data.departments,
          roleId:userMod.data.roleId,
          status:userMod.data.status,
          lockStatus:userMod.data.lockStatus,

          wno:userMod.data.wno,
          identity:userMod.data.identity,
          tutor:userMod.data.tutor,
          faculty:userMod.data.faculty,
          specialty:userMod.data.specialty,
          studentNumber:userMod.data.studentNumber,
          taskName:userMod.data.taskName,
          taskType:userMod.data.taskType,
          projectCode:userMod.data.projectCode,
        }

        UserService.editUser(userINFO,userMod.data.id).then(function () {
          toastr.success("修改成功")
          $uibModalInstance.close(userINFO);
        },function (err) {
          //console.log("修改用户信息",err)
          toastr.error(err)
          // userMod.messageErr=err;
          userMod.data.password==="qwe!@#15316073015"
        })

        // $uibModalInstance.close(userINFO);
        // toastr.success("修改成功")

      };

    };
    userMod.cancel=function(){
      $uibModalInstance.dismiss();
    };

  }
})();
