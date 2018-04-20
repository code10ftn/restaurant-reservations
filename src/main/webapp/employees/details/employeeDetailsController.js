'use strict';

angular.module('app')
    .controller('EmployeeDetailsController', function ($rootScope, $scope, $state, userService, employeeService) {
        if ($rootScope.USER == null ||
            ($rootScope.USER.role != 'BARTENDER' && $rootScope.USER.role != 'CHEF' && $rootScope.USER.role != 'WAITER')) {
            $state.go("signin");
        } else {
            $scope.notChanged = true;
            $scope.name = "";

            userService.findById($rootScope.USER.id,
                function (res) {
                    $scope.user = res.data;
                    $scope.name = $scope.user.firstName;
                }, function (res) {
                    $scope.user = {};
                    toastr.error(res.data.error);
                });
        }

        $scope.save = function () {
            employeeService.update($scope.user.id, $scope.user,
                function (res) {
                    $scope.user = res.data;
                    $scope.name = $scope.user.firstName;
                    $scope.notChanged = true;
                }, function (res) {
                    toastr.error(res.data.error);
                });
        };

        $scope.change = function () {
            $scope.notChanged = false;
        };

        $scope.updatePassword = function () {
            userService.updatePassword($scope.passwords, -100,
                function () {
                    toastr.success("Password changed!");
                }, function (res) {
                    toastr.error(res.data.error);
                });
        };
    });