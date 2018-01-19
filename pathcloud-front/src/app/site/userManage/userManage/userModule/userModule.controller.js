/**
 * Created by Administrator on 2016/11/9.
 */
(function() {
  'use strict';

  angular
    .module('pathcloud')
    .controller('userModuleController', userModuleController);

  /** @ngInject */
  function userModuleController(UserService,$uibModal,$log,T) {
    var userModule=this;

    function init(){
      UserService.getRoleAuthList().then(
        function(result){
          userModule.rolesList=result.data;
        }
      );
      UserService.getAuthList().then(
        function(result){
          userModule.authList=result;
        });
    }
    init();

    userModule.tableHeaders=[
      {name:"用户名",order:1},{name:"姓名",order:2},
      {name:"联系电话",order:3},{name:"电子邮箱",order:4},
      {name:"角色",order:5},{name:"创建时间",order:6},
      {name:"状态",order:7},{name:"编辑",class:"text-center"}
    ];

    userModule.searchData={
      filter:null,
      sort:null,
      order:null,
      page:1,
      length:10
    };

    userModule.status=[{value:false,name:"禁用"},{value:true,name:"正常"}];

    // 角色{管理员|临床医生|取材医生} Method GET URL:/api/role
    userModule.getUserList=function(){
      userModule.searchData.length = 10;
      UserService.getUserList(userModule.searchData).then(
        function(result){
          userModule.usersList = result.data;
          userModule.total = result.total;
        });
    };
    userModule.getUserList();


    //sort
    userModule.getSortList=function(item){
      item.sort==='asc'?item.sort='desc':item.sort='asc';
      userModule.searchData.sort = item.sort;
      userModule.searchData.order = item.order;
      userModule.getUserList();
    };

    userModule.search = function () {

      userModule.searchData={
        filter:userModule.searchStr
      };

      userModule.getUserList();
    };

    userModule.query = function () {
      delete userModule.searchData.filter;
      userModule.getUserList();
    };

    //tooltip filter authorization by permissions
    userModule.filterAuth=function(arr){
      if(arr){
        var auth=[];
        for(var i=0;i<arr.length;i++){
          auth.push(arr[i].name)
        }
      }
      return auth;
    };

    //设置权限
    userModule.setAuthorize=function(){
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/userManage/modal/authorize.html',
        controller: 'AuthorizeController',
        controllerAs: 'authorize',
        size: "lg",

      });
      modalInstance.result.then(function () {
        userModule.getUserList();
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };

    //新增用户信息
    userModule.addUser=function(){
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/userManage/modal/user.html',
        controller: 'UserModalController',
        controllerAs: 'userMod',
        size: "lg",
        resolve: {
          modalTitle: function () {
            return T.T("新增用户");
          },
          method: function () {
            return "post";
          },
          userData: function () {
            return "";
          }
        }
      });
      modalInstance.result.then(function(){
        userModule.getUserList();
      }, function () {
        $log.info('Modal dismissed at: ' + new Date());
      });
    };

    //编辑用户信息
    userModule.editUser=function(userData){
      var modalInstance = $uibModal.open({
        ariaLabelledBy: 'modal-title',
        ariaDescribedBy: 'modal-body',
        templateUrl: 'app/site/userManage/modal/user.html',
        controller: 'UserModalController',
        controllerAs: 'userMod',
        size: "lg",
        resolve: {
          modalTitle: function () {
            return "编辑用户";
          },
          method: function () {
            return "put";
          },
          userData: function () {
            return angular.copy(userData);
          }
        }
      });
      modalInstance.result.then(
        function(result){
          userModule.getUserList();
        },
        function(){
          $log.info('Modal dismissed at: ' + new Date());
        }
      );


    };


  }
})();
