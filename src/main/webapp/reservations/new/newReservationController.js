'use strict';

angular.module('app')
    .controller('NewReservationController', function ($rootScope, $scope, $stateParams, $state, reservationService, tableService, restaurantService, guestService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'GUEST') {
            $state.go('signin');
        } else {
            $scope.invitedFriends = [];
            $scope.items = [];
            $scope.readyAtArrival = false;
            guestService.findFriends(
                function (res) {
                    $scope.uninvitedFriends = res.data;
                },
                function () {
                    $scope.uninvitedFriends = [];
                });

            restaurantService.findById($stateParams.restaurantId,
                function (res) {
                    $scope.reservation = {};
                    $scope.reservation.hoursLength = 1;
                    $scope.reservation.restaurant = res.data;
                    $scope.menu = res.data.menu;
                    $scope.initMap();
                }, function (res) {
                    toastr.error(res.data.error);
                    $state.go('browseRestaurants');
                });
        }

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

        $scope.orderFormInvalid = function () {
            return $scope.items.length == 0 || $scope.selectedTable == -1;
        };

        $scope.showTables = function () {
            var height = document.getElementById('canvas').clientHeight;
            var width = document.getElementById('canvas').clientWidth;
            tableService.getAvailableRestaurantTables(
                $stateParams.restaurantId,
                $("#datetimepicker").data("DateTimePicker").date()._d.toUTCString(),
                $scope.reservation.hoursLength,
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

            $scope.socket = new SockJS('/api/notifications');
            $scope.stompClient = Stomp.over($scope.socket);
            $scope.stompClient.connect({}, function () {
                $scope.stompClient.subscribe("/topic/tables" + $stateParams.restaurantId, function (data) {
                    var notification = JSON.parse(data.body);
                    var tableId = notification.message;
                    jQuery.each($scope.tables, function (i, val) {
                        if (val.id == tableId) {
                            $scope.tables[i].available = false;
                        }
                    });
                    //$scope.$apply();
                });
            });
        };

        $('a[data-toggle=tab]').click(function () {
            if (this.id === "tableTab") {
                window.setTimeout(function () {
                    $scope.showTables();
                }, 500)
            }
        });

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
                    color = "orange";
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

        $scope.selectTable = function (index) {
            if (!$scope.tables[index].available) return;
            $scope.tables[index].selected = !$scope.tables[index].selected;
        };

        $scope.inviteFriend = function (user, index) {
            $scope.uninvitedFriends.splice(index, 1);
            $scope.invitedFriends.push(user);
        };

        $scope.uninviteFriend = function (user, index) {
            $scope.invitedFriends.splice(index, 1);
            $scope.uninvitedFriends.push(user);
        };

        $scope.finishReservation = function () {
            $scope.reservation.date = $("#datetimepicker").data("DateTimePicker").date();
            $scope.reservation.tables = [];

            jQuery.each($scope.tables, function (i, val) {
                if (val.selected) {
                    $scope.reservation.tables.push(val);
                }
            });

            var order = {
                orderItems: $scope.items,
                readyAtArrival: $scope.readyAtArrival
            };

            var reservationDto = {
                reservation: $scope.reservation,
                order: order,
                guests: $scope.invitedFriends
            };

            reservationService.create(reservationDto,
                function () {
                    toastr.success('Reservation created!');
                    $state.go('browseRestaurants');
                },
                function (res) {
                    toastr.error(res.data.error);
                }
            )
        };

        $scope.initMap = function () {
            var map = new google.maps.Map(document.getElementById('map'), {
                zoom: 15,
                center: {lat: 0, lng: 0}
            });
            var geocoder = new google.maps.Geocoder();
            $scope.geocodeAddress(geocoder, map);
        };

        $scope.geocodeAddress = function (geocoder, resultsMap) {
            var address = $scope.reservation.restaurant.address;
            geocoder.geocode({'address': address}, function (results, status) {
                if (status === 'OK') {
                    resultsMap.setCenter(results[0].geometry.location);
                    var marker = new google.maps.Marker({
                        map: resultsMap,
                        position: results[0].geometry.location
                    });
                }
            });
        };
    });