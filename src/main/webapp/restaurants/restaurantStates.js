'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('browseRestaurants', {
                url: '/browse',
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
                        templateUrl: 'restaurants/browse/browseRestaurants.html',
                        controller: 'BrowseRestaurantsController'
                    }
                }
            })
            .state('restaurantDetails', {
                url: '/restaurantDetails',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'restaurants/details/restaurantDetails.html',
                        controller: 'RestaurantDetailsController'
                    }
                }
            })
            .state('restaurants', {
                url: '/restaurants',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'restaurants/list/restaurantList.html',
                        controller: 'RestaurantListController'
                    }
                }
            })
            .state('newRestaurant', {
                url: '/newRestaurant',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'restaurants/new/newRestaurant.html',
                        controller: 'NewRestaurantController'
                    }
                }
            })
            .state('visitedRestaurants', {
                url: '/visited',
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
                        templateUrl: 'restaurants/visited/visitedRestaurants.html',
                        controller: 'VisitedRestaurantsController'
                    }
                }
            })
            .state('restaurantSeating', {
                url: '/seating',
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
                        templateUrl: 'restaurants/seating/restaurantSeating.html',
                        controller: 'RestaurantSeatingController'
                    }
                }
            })
    });