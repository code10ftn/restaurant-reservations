'use strict';

angular.module('app')
    .controller('VisitedRestaurantsController', function ($rootScope, $scope, $state, restaurantService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'GUEST') {
            $state.go("signin");
        }

        restaurantService.findVisited(
            function (res) {
                $scope.restaurants = res.data;
            },
            function () {
                $scope.restaurants = [];
            });
    });