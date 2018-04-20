'use strict';

angular.module('app')
    .controller('ReservationListController', function ($rootScope, $scope, $state, reservationService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'GUEST') {
            $state.go('signin');
        } else {
            reservationService.findUserReservations($rootScope.USER.id,
                function (res) {
                    $scope.reservations = res.data;
                }, function () {
                    $scope.reservations = [];
                });
        }

        $scope.openReservation = function (reservation) {
            $state.go('reservationDetails', {id: reservation.id});
        }
    });