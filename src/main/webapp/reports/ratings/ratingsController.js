'use strict';

angular.module('app')
    .controller('RatingsController', function ($rootScope, $scope, $stateParams, $state, reportService, restaurantService) {
        if ($rootScope.USER == null || $rootScope.USER.role !== 'MANAGER') {
            $state.go("signin");
        }
        reportService.getRestaurantRating(function (res) {
                $scope.restaurantRating = res.data;
            },
            function (res) {
                toastr.error(res.data.error);
            });

        reportService.findWaiters(function (res) {
            $scope.waiters = res.data;
        });

        $scope.getWaiterRating = function (waiter) {
            reportService.getWaiterRating(waiter, function (res) {
                $scope.waiterRating = res.data;
            })
        };

        $scope.getItemRating = function (item) {
            reportService.getItemRating(item, function (res) {
                $scope.itemRating = res.data;
            })
        };

        restaurantService.findCurrent(function (res) {
                $scope.items = res.data.menu.menuItems;
            },
            function (res) {
                toastr.error(res.data.error);
            }
        );
    });