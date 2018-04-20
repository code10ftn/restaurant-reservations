'use strict';

angular.module('app')
    .controller('ReservationDetailsController', function ($rootScope, $scope, $stateParams, $state, reservationService, orderService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'GUEST') {
            $state.go('signin');
        } else {
            $scope.items = [];

            $scope.orderDto = {
                readyAtArrival: false,
                orderItemDtos: []
            };

            reservationService.findById($stateParams.id,
                function (res) {
                    $scope.reservation = res.data;
                    $scope.order = $scope.reservation.order;
                    $scope.items = $scope.order.orderItems;
                    $scope.restaurant = $scope.reservation.restaurant;
                    $scope.menu = $scope.restaurant.menu;
                    $scope.tables = "";
                    jQuery.each(res.data.tables, function (i, val) {
                        $scope.tables = val.id + " " + $scope.tables;
                    });

                    jQuery.each(res.data.reservationGuests, function (i, val) {
                        if (val.guest.id == $rootScope.USER.id) {
                            $scope.reservationGuest = val;
                        }
                    });

                    $scope.indexOld = $scope.items.length;
                }, function (res) {
                    toastr.error(res.data.error);
                    $state.go('reservations');
                });
        }

        $scope.addItem = function () {
            angular.element('#addItemModal').modal('hide');
            var newItem = {
                menuItem: $scope.selectedItem,
                amount: $scope.amount
            };
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
            orderService.update($scope.order.id, $scope.orderDto, function () {
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.cancelReservation = function () {
            reservationService.cancelReservation($scope.reservationGuest.id, function () {
                $state.go('reservations');
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.cancellable = function () {
            if ($scope.reservation == null) return false;
            var now = new Date();
            var diffMs = $scope.reservation.date - now;
            var diffMins = Math.round(((diffMs % 86400000) % 3600000) / 60000);
            return diffMins > 30;
        };


        $scope.reservationStarted = function () {
            var currentDate = new Date();
            if ($scope.reservation == null) return false;
            return $scope.reservation.date <= currentDate;
        };

        $scope.onRestaurantRatingChange = function ($event) {
            console.log($event.rating);
            reservationService.rateRestaurant($stateParams.id, $event.rating, function (res) {
                $scope.reservationGuest.rating = res.data;
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.getRestaurantRating = function () {
            if ($scope.reservationGuest != null && $scope.reservationGuest.rating != null) {
                return $scope.reservationGuest.rating.restaurantRating;
            }
            return "";
        };

        $scope.onWaiterRatingChange = function ($event) {
            console.log($event.rating);
            reservationService.rateWaiter($stateParams.id, $event.rating, function (res) {
                $scope.reservationGuest.rating = res.data;
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.getWaiterRating = function () {
            if ($scope.reservationGuest != null && $scope.reservationGuest.rating != null) {
                return $scope.reservationGuest.rating.waiterRating;
            }
            return "";
        };

        $scope.onOrderRatingChange = function ($event) {
            console.log($event.rating);
            reservationService.rateOrder($stateParams.id, $event.rating, function (res) {
                $scope.reservationGuest.rating = res.data;
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.getOrderRating = function () {
            if ($scope.reservationGuest != null && $scope.reservationGuest.rating != null) {
                return $scope.reservationGuest.rating.orderRating;
            }
            return "";
        };
    });