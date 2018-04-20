'use strict';

angular.module('app')
    .controller('NewOrderController', function ($rootScope, $scope, $state, shiftService, orderService, menuService, restaurantService, tableService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'WAITER') {
            $state.go("signin");
        }

        $scope.items = [];
        $scope.selectedTable = -1;

        restaurantService.findEmployeeRestaurant(function (res) {
            $scope.restaurant = res.data;
            $scope.menu = res.data.menu;
            $scope.showTables();

        }, function (res) {
            $scope.restaurant = {};
            $scope.menu = [];
            toastr.error(res.data.error);
        });

        shiftService.findCurrentShift(function (res) {
            $scope.shift = res.data;
        }, function (res) {
            toastr.error(res.data.error);
            $state.go("employeeWorksheet");
        });

        $scope.addItem = function () {
            angular.element('#addItemModal').modal('hide');
            var newItem = {
                menuItem: $scope.selectedItem,
                amount: $scope.amount
            };
            $scope.items.push(newItem);
        };

        $scope.removeItem = function (index) {
            $scope.items.splice(index, 1);
        };

        $scope.save = function () {
            var order = {
                orderItems: $scope.items,
                table: $scope.tables[$scope.selectedTable]
            };

            orderService.create(order, function () {
                $state.go("myOrders");
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.orderFormInvalid = function () {
            return $scope.items.length == 0 || $scope.selectedTable == -1;
        };

        $scope.showTables = function () {
            var height = document.getElementById('canvas').clientHeight;
            var width = document.getElementById('canvas').clientWidth;
            tableService.getByArea($scope.restaurant,
                function (res) {
                    $scope.tables = res.data;
                    jQuery.each(res.data, function (i, val) {
                        $scope.setTablePos(val, height, width);
                        $scope.setTableColor(val);
                    });
                },
                function (res) {
                    $scope.tables = [];
                    toastr.error(res.data.error);
                });
        };

        $scope.setTablePos = function (table, height, width) {
            table.horizontalPosition = table.horizontalPosition / 100 * width;
            table.verticalPosition = table.verticalPosition / 100 * height;
            table.x = table.horizontalPosition;
            table.y = table.verticalPosition;
            table.w = width / 10;
            table.h = height / 10;
        };

        $scope.setTableColor = function (table) {
            var color = "blue";
            switch (table.area) {
                case "INSIDE_NONSMOKING":
                    color = "blue";
                    break;
                case "INSIDE_SMOKING":
                    color = "brown";
                    break;
                case "GARDEN_OPEN":
                    color = "green";
                    break;
                case "GARDEN_CLOSED":
                    color = "aqua";
                    break;
            }
            table.color = color;
        };

        $scope.selectTable = function (ind) {
            $scope.tables[ind].selected = !$scope.tables[ind].selected;
            if ($scope.tables[ind].selected) {
                $scope.selectedTable = ind;
            } else {
                $scope.selectedTable = -1;
            }

            $scope.tables.forEach(function (element, index) {
                if (ind != index) {
                    $scope.tables[index].selected = false;
                }
            });
        };
    });