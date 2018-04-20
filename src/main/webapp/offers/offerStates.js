'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('newOffer', {
                url: '/newOffer',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'offers/new/newOffer.html',
                        controller: 'NewOfferController'
                    }
                }
            })
            .state('offerDetails', {
                url: '/offerDetails/:offerId',
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
                        templateUrl: 'offers/details/offerDetails.html',
                        controller: 'OfferDetailsController'
                    }
                }
            })
            .state('editOffer', {
                url: '/editOffer/:offerId',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'offers/edit/editOffer.html',
                        controller: 'EditOfferController'
                    }
                }
            })
            .state('offers', {
                url: '/offers',
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
                        templateUrl: 'offers/list/offerList.html',
                        controller: 'OfferListController'
                    }
                }
            })
    });