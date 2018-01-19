/**
 * Created by lenovo on 2016/5/13.
 */
(function(){
  'use strict';
  angular
    .module('pathcloud')
    .controller('permissionModuleController',permissionModuleController);

  /** @ngInject */
  function permissionModuleController($uibModal,UserService,toastr,toolService,$log){
    var permissionModule=this;

    permissionModule.tableHeaders=[{name:"角色名",order:1},
      {name:"描述"},{name:"权限",style:{'min-width':'40%'}},
      {name:"创建时间",order:2},{name:"操作",style:{'text-align':'center'}}];

    permissionModule.totalItems=0;
    permissionModule.searchData={
      filter:null,
      sort:null,
      order:null,
      page:1,
      length:10,
    };

    permissionModule.getUserList=function () {
      UserService.getRoleAuthList(permissionModule.searchData).then(
        function(result){
        permissionModule.permissionModuleList=result.data;
        permissionModule.totalItems=result.total;}
      );
    };
    permissionModule.getUserList();

    //search filter
    permissionModule.getFilterList=function (e) {
      var keycode = window.event?e.keyCode:e.which;
      if(keycode==13){
        permissionModule.getUserList();
      }
    };

    permissionModule.remove=function(id){
      toolService.getModalResult({modalTitle:"删除提示",modalContent:"确认删除角色？"}).then(
        function(){
          UserService.deleteRole(id).then(function(){
            toastr.success("删除成功");
            permissionModule.getUserList();
          });
        }
      );
    };

    //sort
    permissionModule.getSortList=function (item) {
      item.sort==='asc'?item.sort='desc':item.sort='asc';
      permissionModule.searchData.sort = item.sort;
      permissionModule.searchData.order = item.order;
      permissionModule.getUserList();
    };

    permissionModule.openPermissionModal=function (permissionModuleItem) {
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/userManage/modal/permission.html',
        controller: 'PermissionController',
        controllerAs: 'permission',
        resolve: {
          role: function () {
            return angular.copy(permissionModuleItem);
          }
        }

      });
      modalInstance.result.then(function () {
        permissionModule.getUserList();
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };
  }
})();
