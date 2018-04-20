'use strict';

angular.module('app')
    .controller('ChangePasswordController', function ($rootScope, $scope, $state, $stateParams, userService) {

        if ($stateParams.id) {
            userService.findById($stateParams.id,
                function (res) {
                    $scope.id = res.data.id;
                }, function (res) {
                    $scope.id = null;
                    toastr.error(res.data.error);
                });
        } else {
            $scope.id = null;
        }

        $scope.updatePassword = function () {
            userService.updatePassword($scope.passwords, $scope.id,
                function (res) {
                    toastr.success("Password changed!");
                    if ($rootScope.USER === null) {
                        $rootScope.USER = {
                            id: res.data.id,
                            role: res.data.role,
                            firstName: res.data.firstName,
                            lastName: res.data.lastName,
                            email: res.data.email,
                            verified: res.data.verified
                        };
                        $state.go('employeeDetails');
                    }
                }, function (res) {
                    toastr.error(res.data.error);
                });
        };
    });