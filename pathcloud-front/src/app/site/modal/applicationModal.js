(function() {
    'use strict';

    angular
        .module('pathcloud')
        .controller('ApplicationModalController', ApplicationModalController);

    /** @ngInject */
    function ApplicationModalController($scope, printerService, $uibModalInstance, modalTitle, ApplicationData, $filter, $timeout) {

        //现在有了科研申请 跟普通申请两个表，展示的时候 判断 applyTYpe===2 的是科研 科研在research 字段中 区分开
        /*
         *  科研的applyType、samples 不在research 字段中
         *
         * */

        var applicationMod = this;
        applicationMod.Title = modalTitle;
        $scope.readonly = true;

        $scope.pathological = {}; //在两个申请指令的this applicationData , pathologicalFreeze ；

        if (ApplicationData.applyType === 1) {
            applicationMod.applyType = 1;
            $scope.applicationData = ApplicationData;
        } else if (ApplicationData.applyType === 2) {

            //科研申请指令 form 数据绑定在 pathologicalFreeze.data 下
            applicationMod.applyType = 2;


            $timeout(function() {

                $scope.pathologicalFreeze.data = ApplicationData.research;
                $scope.pathologicalFreeze.data.departmentsDesc = ApplicationData.departmentsDesc;
                // console.info($scope.pathologicalFreeze,ApplicationData.applyType)
                $scope.pathologicalFreeze.data.samples = ApplicationData.samples;
                $scope.pathologicalFreeze.data.id = ApplicationData.id;
                applicationMod.serialNumber = $scope.pathologicalFreeze.data.serialNumber = ApplicationData.serialNumber;
                $scope.pathologicalFreeze.data.applyType = 2;
            }, 100)

        }else if(ApplicationData.applyType === 3){
          applicationMod.applyType = 3;
          applicationMod.data = ApplicationData;
          applicationMod.data.origins = JSON.parse(ApplicationData.medicalHistory)
        }


        ApplicationData.menopauseEnd = $filter('date')(ApplicationData.menopauseEnd, 'yyyy-MM-dd')

        // console.log("病理申请表数据ApplicationData",ApplicationData)


        // $scope.position=null;
        // $scope.pathological.position = false;
        // $scope.sampleRegistration=false;



        //确定按钮
        applicationMod.ok = function() {
            // printTable($scope.applicationData.serialNumber,$uibModalInstance.close);


            var serialNumber;
            if ($scope.applicationData) {
                serialNumber = $scope.applicationData.serialNumber;
            } else {
                serialNumber = applicationMod.serialNumber;
            };

            if (applicationMod.applyType === 2) { //2科研
                printerService.printPathologicalForm("#pathologicalFreezeForm", serialNumber, $uibModalInstance.close);

            } else {
                printerService.printPathologicalForm("#pathologicalForm", serialNumber, $uibModalInstance.close);

            }

            //var mobileDialogElement = strStyleCSS+"<body>"+$('#pathologicalForm').html()+"</body>";

            //$uibModalInstance.close();
        };

        //取消按钮
        applicationMod.cancel = function() {
            // printTable();
            $uibModalInstance.dismiss();
        }
    } //end CommonModalController
})();
