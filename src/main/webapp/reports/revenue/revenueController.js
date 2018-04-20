'use strict';

angular.module('app')
    .controller('RevenueController', function ($rootScope, $scope, $stateParams, $state, reportService) {
        if ($rootScope.USER == null || $rootScope.USER.role !== 'MANAGER') {
            $state.go("signin");
        }
        $scope.period = {};
        $scope.getRevenue = function () {
            reportService.getRestaurantRevenue($scope.period, function (res) {
                    $scope.restaurantRevenue = res.data.revenue;
                },
                function (res) {
                    toastr.error(res.data.error);
                })
        };
        reportService.findWaiters(function (res) {
            $scope.waiters = res.data;
        });

        reportService.getWaitersRevenue(function (res) {
            $scope.waitersRevenue = res.data;
        })
    });