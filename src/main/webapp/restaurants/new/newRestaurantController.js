'use strict';

angular.module('app')
    .controller('NewRestaurantController', function ($rootScope, $scope, $state, restaurantService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'ADMIN') {
            $state.go("signin");
        }

        $scope.createRestaurant = function () {
            restaurantService.create(
                $scope.restaurant,
                function () {
                    $scope.restaurant = {};
                    $state.go("restaurants");
                }, function (res) {
                    toastr.error(res.data.error);
                });
        };
    });