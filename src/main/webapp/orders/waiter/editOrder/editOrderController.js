'use strict';

angular.module('app')
    .controller('EditOrderController', function ($rootScope, $scope, $state, $stateParams, orderService, restaurantService, tableService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'WAITER') {
            $state.go("signin");
        }

        $scope.items = [];
        $scope.orderDto = {
            readyAtArrival: false,
            orderItemDtos: []
        };
        $scope.deleteIndex = {};

        orderService.findById($stateParams.id, function (res) {
            $scope.order = res.data;
            $scope.items = $scope.order.orderItems;
            $scope.indexOld = $scope.items.length;
        }, function () {
            $toastr.error(res.data.error);
        });

        restaurantService.findEmployeeRestaurant(function (res) {
            $scope.restaurant = res.data;
            $scope.menu = res.data.menu;

        }, function (res) {
            $scope.restaurant = {};
            $scope.menu = [];
            toastr.error(res.data.error);
        });

        $scope.addItem = function () {
            angular.element('#addItemModal').modal('hide');
            var newItem = {
                menuItem: $scope.selectedItem,
                amount: $scope.amount
            };
            newItem.status = 'CREATED';
            $scope.items.push(newItem);
            $scope.orderDto.orderItemDtos.push(
                {
                    orderItem: newItem,
                    adding: true
                }
            );
        };

        $scope.removeItem = function (index) {
            var item = $scope.items[index];
            if (index < $scope.indexOld) {
                $scope.indexOld--;
                $scope.orderDto.orderItemDtos.push(
                    {
                        orderItem: item,
                        adding: false
                    }
                );
            } else {
                $scope.orderDto.orderItemDtos.splice(index - $scope.indexOld, 1);
            }

            $scope.items.splice(index, 1);
        };

        $scope.save = function () {
            orderService.update($stateParams.id, $scope.orderDto, function () {
                $state.go("myOrders");
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.orderFormInvalid = function () {
            return $scope.items.length == 0;
        };

    });