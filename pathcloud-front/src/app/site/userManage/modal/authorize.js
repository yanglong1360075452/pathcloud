/**
 * Created by lenovo on 2016/5/13.
 */
(function(){
    'use strict';
    angular
        .module('pathcloud')
        .controller('AuthorizeController', AuthorizeController);

    /** @ngInject */
    function AuthorizeController($scope,$uibModalInstance,UserService,toastr,$log){
      var authorize=this;
      var inventedId=1000;

      var delList,delIdList,addList,updateList;

      authorize.chosePerission="";
      UserService.getRoleAuthList().then(
        function(result){
          authorize.roleList=result.data;
          authorize.roleListBefore=angular.copy(result.data);
        },
        function(err){
          $log.warn(err)
        }
      );

      UserService.getAuthList().then(
        function(result){
          authorize.permissionsList=result;
          console.log(authorize.permissionsList)
        },
        function(err){
          $log.warn(err)
        }
      );

      authorize.authList=[];

      authorize.roleListBefore=angular.copy(authorize.roleList);

      authorize.chosedRole;

      authorize.delRole=function(index){
        authorize.roleList.splice(index,1);
      };

      authorize.delPerission=function(id){
        console.log(_.remove(authorize.chosedRole.permissions,{id:id}))
      };

      authorize.addRole=function(name){
        var newRole={id:++inventedId,name:name,permissions:[]};
        authorize.roleList.push(newRole);
        //console.log("inventedId"+inventedId);
        authorize.chooseRole(newRole);
      };

      authorize.addAuth=function(perission){
        if(authorize.chosedRole){
          if(authorize.chosedRole.permissions){
            if(_.findIndex(authorize.chosedRole.permissions,{id:perission.id})===-1)
              authorize.chosedRole.permissions.push(perission);

          }else{
            authorize.chosedRole.permissions=[];
            authorize.chosedRole.permissions.push(perission);
          }
          //console.log("inventedId"+perission);
        }
      };

      authorize.chooseRole=function(roleItem) {
        roleItem.permissions||(roleItem.permissions=[]);
        authorize.roleList.forEach(function (item) {
          if (item.id === roleItem.id) {
            item.active = true;
            authorize.chosedRole=item;
            authorize.authList=item.permissions;
          } else {
            item.active = false;
          }
        })
      };

      function compare(){
        //获得已删除的数组
        delList=_.differenceBy(authorize.roleListBefore,authorize.roleList,'id');
        delIdList=[];
        delList.forEach(function(item){
          delIdList.push(item.id);
        });
        addList=_.differenceBy(authorize.roleList,authorize.roleListBefore,'id');
        addList.forEach(function(item){
          delete item.active;
          addPermissionsId(item);
        });
        updateList= [];

        authorize.roleList.forEach(function(nowItem){
          authorize.roleListBefore.forEach(function(beforeItem){
            if(nowItem.id===beforeItem.id){
              var difference1=_.differenceBy(nowItem.permissions,beforeItem.permissions,'id');
              var difference2=_.differenceBy(beforeItem.permissions,nowItem.permissions,'id');
              if(difference1.length>0||difference2.length>0){
                delete nowItem.active;

                addPermissionsId(nowItem);

                updateList.push(nowItem);
              }
            }
          })
        });
      }

      function addPermissionsId(elem){
        var permissionIdList=[];

        elem.permissions.forEach(function(permission){
          permissionIdList.push(permission.id);
        });
        elem.permissionsId=permissionIdList;
      }

      authorize.submit=function(){
        compare();
        var subData={
          roleAdd:addList,
          roleDelete:delIdList,
          roleUpdate:updateList
        };
        console.log(subData);

        UserService.updateRoleList(subData).then(
          function(){
            toastr.success("修改成功")
          }
        )

      };
      //toastr.success("修改成功");
      authorize.ok=function(){
          authorize.submit();
          //$uibModalInstance.close();
      };
      authorize.cancel=function(){
          $uibModalInstance.dismiss();
      };

    }
})();
