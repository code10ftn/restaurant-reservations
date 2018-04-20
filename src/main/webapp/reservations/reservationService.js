'use strict';

angular.module('app')
    .service('reservationService', function ($http) {
        return {
            create: function (reservation, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/reservations',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: reservation
                };
                $http(req).then(onSuccess, onError);
            },
            findById: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/reservations/' + id,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            findUserReservations: function (userId, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/reservations/user/' + userId,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            cancelReservation: function (reservationGuestId, onSuccess, onError) {
                var req = {
                    method: 'DELETE',
                    url: '/api/reservationGuests/' + reservationGuestId,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            rateRestaurant: function (reservationId, rating, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/rating/reservation/' + reservationId + '/restaurant',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    params: {
                        rate: rating
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            rateWaiter: function (reservationId, rating, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/rating/reservation/' + reservationId + '/waiter',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    params: {
                        rate: rating
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            rateOrder: function (reservationId, rating, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/rating/reservation/' + reservationId + '/order',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    params: {
                        rate: rating
                    }
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });