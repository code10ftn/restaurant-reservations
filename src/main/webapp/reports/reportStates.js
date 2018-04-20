angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('revenue', {
                url: '/revenue',
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
                        templateUrl: 'reports/revenue/revenue.html',
                        controller: 'RevenueController'
                    }
                }
            })
            .state('visits', {
                url: '/visits',
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
                        templateUrl: 'reports/visits/visits.html',
                        controller: 'VisitsController'
                    }
                }
            })
            .state('ratings', {
                url: '/ratings',
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
                        templateUrl: 'reports/ratings/ratings.html',
                        controller: 'RatingsController'
                    }
                }
            })
    });