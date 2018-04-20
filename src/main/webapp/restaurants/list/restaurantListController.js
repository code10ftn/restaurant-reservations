'use strict';

angular.module('app')
    .controller('RestaurantListController', function ($scope, restaurantService) {
        restaurantService.findAll(
            function (res) {
                $scope.restaurants = res.data;
            },
            function () {
                $scope.restaurants = [];
            });
    });