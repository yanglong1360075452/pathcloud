/**
 * Created by lenovo on 2016/5/13.
 */
(function(){
    'use strict';
    angular
        .module('pathcloud')
        .controller('PermissionController', PermissionController);

    /** @ngInject */
    function PermissionController(role,$uibModalInstance,UserService,toastr,$log){
      var permission=this;
      var type=0;//0创建 1修改

      if(role){
        type=1;
        permission.data=role;
      }
      UserService.getAuthList().then(function(data){
        permission.permissionList=data;
        if(type){
          permission.permissionList.forEach(function(listItem){
            listItem.checked=false;
            permission.data.permissions.forEach(function(item){
              if(listItem.id===item.id){
                listItem.checked=true;
              }
            })
          })

        }
      });


      permission.submit=function(){
        permission.data.permissionsId=[];
        permission.permissionList.forEach(function(listItem){
          if(listItem.checked){
            permission.data.permissionsId.push(listItem.id);
          }
        });
        if(!type){

          UserService.createRole(permission.data).then(
            function(){
              toastr.success("角色创建成功");
              $uibModalInstance.close();
            },function (err) {
              toastr.error(err);
            }
          )
        }else{
          UserService.updateRole(permission.data.id,permission.data).then(
            function(){
              toastr.success("角色修改成功");
              $uibModalInstance.close();
            }
          )
        }
      };


      permission.ok=function(){
        permission.submit();
        //$uibModalInstance.close();
      };
      permission.cancel=function(){
        $uibModalInstance.dismiss();
      };
    }
})();
