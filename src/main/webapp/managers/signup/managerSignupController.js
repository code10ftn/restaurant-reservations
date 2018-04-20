'use strict';

angular.module('app')
    .controller('ManagerSignupController', function ($rootScope, $scope, $state, managerService, restaurantService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'ADMIN') {
            $state.go("signin");
        }

        restaurantService.findAll(
            function (res) {
                $scope.restaurants = res.data;
            },
            function () {
                $scope.restaurants = [];
            });


        $scope.signup = function () {
            managerService.signup(
                $scope.manager,
                function () {
                    $scope.manager = {};
                    $state.go("signin");
                }, function (res) {
                    toastr.error(res.data.error);
                });
        };
    });