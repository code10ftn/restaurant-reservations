'use strict';

angular.module('app')
    .controller('ManagerListController', function ($scope, managerService) {
        managerService.findAll(
            function (res) {
                $scope.managers = res.data;
            },
            function () {
                $scope.managers = [];
            });
    });