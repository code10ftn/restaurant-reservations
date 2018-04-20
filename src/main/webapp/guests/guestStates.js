'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider.state('guestDetails', {
            url: '/guestDetails',
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
                    templateUrl: 'guests/details/guestDetails.html',
                    controller: 'GuestDetailsController'
                }
            }
        });
    });