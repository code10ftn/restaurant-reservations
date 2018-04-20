'use strict';

angular.module('app')
    .controller('EmployeeSignupController', function ($rootScope, $scope, $state, employeeService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'MANAGER') {
            $state.go("signin");
        }

        $scope.signup = function () {

            var onSuccess = function () {
                $scope.manager = {};
                $state.go("employees");
            };

            var onError = function (res) {
                toastr.error(res.data.error);
            };

            if ($scope.role === "CHEF") {
                employeeService.signupChef(
                    $scope.employee,
                    onSuccess, onError);
            } else if ($scope.role === "WAITER") {
                employeeService.signupWaiter(
                    $scope.employee,
                    onSuccess, onError);
            } else if ($scope.role === "BARTENDER") {
                employeeService.signupBartender(
                    $scope.employee,
                    onSuccess, onError);
            }
        };
    });