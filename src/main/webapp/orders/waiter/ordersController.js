'use strict';

angular.module('app')
    .controller('OrdersController', function ($rootScope, $scope, $state, orderService, shiftService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'WAITER') {
            $state.go("signin");
        }

        shiftService.findCurrentShift(function (res) {
        }, function (res) {
            toastr.error(res.data.error);
            $state.go("employeeWorksheet");
        });

        orderService.findAll(function (res) {
            $scope.myOrders = res.data;
            orderService.findAllUnaccepted(function (res) {
                $scope.allOrders = res.data;
            }, function (res) {
                $scope.allOrders = [];
                toastr.error(res.data.error);
            });
        }, function (res) {
            $scope.myOrders = [];
            toastr.error(res.data.error);
            orderService.findAllUnaccepted(function (res) {
                $scope.allOrders = res.data;
            }, function (res) {
                $scope.allOrders = [];
                toastr.error(res.data.error);
            });
        });

        $scope.addOrder = function () {
            $state.go('newOrder');
        };

        $scope.editOrder = function (index) {
            $state.go('editOrder', {id: $scope.myOrders[index].id});
        };

        $scope.acceptOrder = function (index) {
            orderService.accept($scope.allOrders[index].id, function () {
                var order = $scope.allOrders[index];
                order.waiter = $rootScope.USER;
                order.accepted = new Date();
                $scope.myOrders.push(order);
                $scope.allOrders.splice(index, 1);
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.closeOrder = function (index) {
            orderService.close($scope.myOrders[index].id, $scope.USER.id, function () {
                $scope.myOrders.splice(index, 1);
            }, function (res) {
                toastr.error(res.data.error);
            });
        };
    });