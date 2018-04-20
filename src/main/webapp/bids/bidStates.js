'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('newBid', {
                url: '/newBid/:id',
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
                        templateUrl: 'bids/new/newBid.html',
                        controller: 'NewBidController'
                    }
                }
            })
            .state('editBid', {
                url: '/editBid/:id',
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
                        templateUrl: 'bids/edit/editBid.html',
                        controller: 'EditBidController'
                    }
                }
            })
            .state('bids', {
                url: '/bids/:id',
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
                        templateUrl: 'bids/list/bidList.html',
                        controller: 'BidListController'
                    }
                }
            })
            .state('myBids', {
                url: '/myBids/',
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
                        templateUrl: 'bids/mylist/myBidList.html',
                        controller: 'MyBidListController'
                    }
                }
            })
    });