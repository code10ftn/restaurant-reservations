'use strict';

angular.module('app')
    .controller('AdminSignupController', function ($rootScope, $scope, $state, adminService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'ADMIN') {
            $state.go("signin");
        }

        $scope.signup = function () {
            adminService.signup(
                $scope.admin,
                function () {
                    $scope.admin = {};
                    $state.go("signin");
                }, function (res) {
                    toastr.error(res.data.error);
                });
        };
    });