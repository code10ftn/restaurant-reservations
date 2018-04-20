'use strict';

angular.module('app')
    .controller('EmployeeListController', function ($rootScope, $scope, $state, employeeService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'MANAGER') {
            $state.go("signin");
        }

        employeeService.findRestaurantEmployees(
            function (res) {
                $scope.employees = res.data;
            },
            function () {
                $scope.employees = [];
            });
    });