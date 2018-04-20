'use strict';

angular.module('app')
    .controller('RestaurantDetailsController', function ($rootScope, $scope, $state, menuService, restaurantService, $stateParams) {
        function initMenus(restaurant) {
            $scope.foodMenu = [];
            $scope.drinkMenu = [];
            restaurant.menu.menuItems.forEach(function (item) {
                if (item.type === "FOOD") {
                    $scope.foodMenu.push(item);
                } else if (item.type === "DRINK") {
                    $scope.drinkMenu.push(item);
                }
            })
        }

        if ($rootScope.USER == null || $rootScope.USER.role != 'MANAGER') {
            $state.go("signin");
        } else {
            $scope.notChanged = true;

            restaurantService.findCurrent(
                function (res) {
                    $scope.restaurant = res.data;
                    $scope.name = $scope.restaurant.name;
                    initMenus(res.data);
                }, function (res) {
                    $scope.restaurant = {};
                    toastr.error(res.data.error);
                });
        }

        $scope.save = function () {
            restaurantService.update($scope.restaurant,
                function (res) {
                    $scope.restaurant = res.data;
                    $scope.name = $scope.restaurant.name;
                    $scope.notChanged = true;
                }, function (res) {
                    toastr.error(res.data.error);
                });
        };

        $scope.change = function () {
            $scope.notChanged = false;
        };

        $scope.menuItem = {};
        $scope.setItemType = function (type) {
            $scope.menuItem.type = type;
        };

        $scope.addItem = function () {
            // $scope.menuItem.menu = $scope.restaurant.menu;
            $scope.restaurant.menu.menuItems.push($scope.menuItem);
            if ($scope.menuItem.type === "FOOD")
                $scope.foodMenu.push($scope.menuItem);
            else if ($scope.menuItem.type === "DRINK")
                $scope.drinkMenu.push($scope.menuItem);
            menuService.update($scope.restaurant.menu, function (res) {
                toastr.success("updated");
            }, function (res) {
                toastr.error(res.data.error);
            });
            //             $scope.drinkMenu.push(res.data);
            // menuService.createItem($scope.menuItem,
            //     function (res) {
            //         if (res.data.type === "FOOD")
            //             $scope.foodMenu.push(res.data);
            //         else if (res.data.type === "DRINK")
            //             $scope.drinkMenu.push(res.data);
            //         $scope.menuItem = {};
            //     }, function (res) {
            //         toastr.error(res.data.error);
            //     }
            // );
            $('#modal').modal('hide');
            $scope.menuItem = {};
        };

        $scope.deleteDrink = function (drink) {
            var index = $scope.restaurant.menu.menuItems.indexOf(drink);
            $scope.restaurant.menu.menuItems.splice(index, 1);

            index = $scope.drinkMenu.indexOf(drink);
            $scope.drinkMenu.splice(index, 1);

            menuService.update($scope.restaurant.menu, function (res) {
                toastr.success("updated");
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.deleteFood = function (food) {
            var index = $scope.restaurant.menu.menuItems.indexOf(food);
            $scope.restaurant.menu.menuItems.splice(index, 1);

            index = $scope.foodMenu.indexOf(food);
            $scope.foodMenu.splice(index, 1);

            menuService.update($scope.restaurant.menu, function (res) {
                toastr.success("updated");
            }, function (res) {
                toastr.error(res.data.error);
            });
        };
    });