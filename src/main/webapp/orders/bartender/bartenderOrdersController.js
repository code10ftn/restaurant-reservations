'use strict';

angular.module('app')
    .controller('BartenderOrdersController', function ($rootScope, $scope, $state, orderService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'BARTENDER') {
            $state.go("signin");
        }

        orderService.findBartenderOrderItems(function (res) {
            $scope.myItems = res.data;
        }, function (res) {
            $scope.myItems = [];
            toastr.error(res.data.error);
            $state.go("employeeWorksheet");
        });

        orderService.findOrderItems(function (res) {
            $scope.items = res.data;
        }, function (res) {
            $scope.items = [];
            toastr.error(res.data.error);
            $state.go("employeeWorksheet");
        });

        $scope.setPreparingStatus = function (index) {
            var orderItemDto = {
                orderItemStatus: 'PREPARING'
            };
            orderService.updateOrderItemBartender($scope.items[index].id, orderItemDto, function (res) {
                $scope.items.splice(index, 1);
                $scope.myItems.push(res.data);
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.setPreparedStatus = function (index) {
            var orderItemDto = {
                orderItemStatus: 'PREPARED'
            };
            orderService.updateOrderItemBartender($scope.myItems[index].id, orderItemDto, function (res) {
                $scope.myItems.splice(index, 1);
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

    });