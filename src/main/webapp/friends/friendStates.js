'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider.state('friends', {
            url: '/friends',
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
                    templateUrl: 'friends/list/friendList.html',
                    controller: 'FriendListController'
                }
            }
        });
    });