/**
 * 2017-5-10
 */
(function(){
  'use strict';
  angular
  .module('pathcloud')
  .controller('ProfileController', ProfileController);

  /** @ngInject */
  function ProfileController($rootScope,$scope,$state,UserService,toastr,MaterialService){
    var profile=this;

    function init() {
      profile.formDisabled=true;

      UserService.getUser($rootScope.user.id).then(function (result) {
        profile.data = result;
      }); //获取用户信息

      UserService.getRoleAuthList().then(function (result) {
        console.log("rolesList=======",result)
        profile.rolesList=result.data;//新增编辑用户时获取角色权限列表
      });

      profile.getDepartments = function (filter) {
        MaterialService.getDepartments(filter).then(function(data) {
          profile.departmentList = data;
        }); //科室列表
      };
      profile.getDepartments();
      profile.taskTypeList = [ { name: "国家自然科学基金", value: 0 }, { name: "国家科技部课题", value: 1 }, { name: "省部级计划课题", value: 2 }, { name: "横向课题", value: 3 }, { name: "其他", value: 4 }, ];
      profile.identityList = [ { name: "本科生", value: 0 }, { name: "硕士研究生", value: 1 }, { name: "博士研究生", value: 2 }, { name: "博士后", value: 3 }, { name: "临床医生", value: 4 }, { name: "研究人员", value: 5 },{ name: "技术人员", value: 6 },{ name: "其他", value: 7 }, ];

    }
    init();

    profile.check={};

    // console.log("userData====",userData)
    // profile.permission=$rootScope.user.permissionCodes['0']

    //check userName
    profile.checkFn=function (userName) {
      // UserService.check({username:userName}).then(function (ok) {
      UserService.check(userName).then(function (ok) {
        profile.check.nameErr=ok;
        // console.log("=====profile.check.OK=====",ok,profile.check.nameErr)
      },function (err) {
        profile.check.nameErr=err;
        // console.log("=====profile.check.err=====",err,profile.check.nameErr)
        //console.log("profile.check.nameErr=====",err)
      })
    };


    profile.ok=function(){

        // if(profile.data.password==="qwe!@#15316073015") profile.data.password=null;
        // profile.placeholder="*****************"

      var userINFO={
        "id":profile.data.id, //用户ID
        userName:profile.data.userName,
        password:null,
        firstName:profile.data.firstName,
        phone:profile.data.phone,
        email:profile.data.email,
        unit:profile.data.unit,
        departments:profile.data.departments,
        roleId:profile.data.roleId,
        status:profile.data.status,

        wno:profile.data.wno,
        identity:profile.data.identity,
        tutor:profile.data.tutor,
        faculty:profile.data.faculty,
        specialty:profile.data.specialty,
        studentNumber:profile.data.studentNumber,
        taskName:profile.data.taskName,
        taskType:profile.data.taskType,
        projectCode:profile.data.projectCode,
      };
        // var userINFO={
        //   userName:profile.data.userName,
        //   password:profile.data.password,
        //   firstName:profile.data.firstName,
        //   phone:profile.data.phone,
        //   email:profile.data.email,
        //   roleId:profile.data.roleId,
        //   status:profile.data.status,
        //   id:profile.data.id,
        // };

        UserService.editUser(userINFO,userINFO.id).then(function () {
          toastr.success("修改成功")
          $state.go('app.profile',{},{reload:true})
        },function (err) {
          //console.log("修改用户信息",err)
          profile.messageErr=err;
          profile.data.password==="qwe!@#15316073015"
        })


    };


  }
})();
