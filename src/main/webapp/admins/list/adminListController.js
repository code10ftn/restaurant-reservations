'use strict';

angular.module('app')
    .controller('AdminListController', function ($rootScope, $scope, $state, adminService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'ADMIN') {
            $state.go("signin");
        }

        adminService.findAll(
            function (res) {
                $scope.admins = res.data;
            },
            function () {
                $scope.admins = [];
            });
    });