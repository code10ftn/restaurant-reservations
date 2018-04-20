'use strict';

angular.module('app')
    .controller('BrowseRestaurantsController', function ($rootScope, $scope, $state, restaurantService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'GUEST') {
            $state.go("signin");
        }

        restaurantService.findAll(
            function (res) {
                $scope.restaurants = res.data;
                $('#table').bootstrapTable({
                    data: $scope.restaurants,
                    onClickRow: function (row, $element) {
                        $scope.startReservation(row.id);
                    }
                });
            },
            function () {
                $scope.restaurants = [];
                $('#table').bootstrapTable({
                    data: $scope.restaurants,
                    onClickRow: function (row, $element) {
                        $scope.startReservation($element);
                    }
                });
            });

        $scope.startReservation = function (restaurant) {
            $state.go('newReservation', {restaurantId: restaurant});
        }
    });