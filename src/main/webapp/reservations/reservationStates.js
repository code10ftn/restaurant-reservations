'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('reservationDetails', {
                url: '/reservationDetails/:id',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'notifications': {
                        templateUrl: 'notifications/notifications.html',
                        controller: 'NotificationsController'
                    },
                    'content': {
                        templateUrl: 'reservations/details/reservationDetails.html',
                        controller: 'ReservationDetailsController'
                    }
                }
            })
            .state('reservations', {
                url: '/reservationList',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'notifications': {
                        templateUrl: 'notifications/notifications.html',
                        controller: 'NotificationsController'
                    },
                    'content': {
                        templateUrl: 'reservations/list/reservationList.html',
                        controller: 'ReservationListController'
                    }
                }
            })
            .state('newReservation', {
                url: '/newReservation/:restaurantId',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'notifications': {
                        templateUrl: 'notifications/notifications.html',
                        controller: 'NotificationsController'
                    },
                    'content': {
                        templateUrl: 'reservations/new/newReservation.html',
                        controller: 'NewReservationController'
                    }
                }
            })
    });