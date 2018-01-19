(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('ChangePwdModalController',ChangePwdModalController);

  /** @ngInject */
  function ChangePwdModalController(UserService,$uibModalInstance,toastr,modalTitle,$state){

    var changePassword = this;
    changePassword.Title = modalTitle;
    changePassword.data = {};//存放旧密码、新密码
    changePassword.check = {};//存放确认密码

    //确定按钮
    changePassword.ok = function (){
      //console.log("changePassword.data ",changePassword.data);
      UserService.changePwd(changePassword.data).then(
        function(result){
          //console.log(result);
          $uibModalInstance.dismiss();
          toastr.success("密码修改成功！");
          //$state.go('login');
        },
        function(err){
          changePassword.Err = err;
          //console.log("changePassword.Err : ",changePassword.Err);
        }
      )
    }

    //取消按钮
    changePassword.cancel = function(){
      $uibModalInstance.dismiss();
    }

    //检查两次密码是否一致
    changePassword.checkPassword = function(){
        changePassword.Err='';
        if(changePassword.check.password2 && changePassword.data.newPassword && changePassword.check.password2!==changePassword.data.newPassword){
          changePassword.checkpasswordErr="两次输入的密码不一致";
        } else{
          changePassword.checkpasswordErr="";
        }
    };



  }//end ChangePwdController
})();
