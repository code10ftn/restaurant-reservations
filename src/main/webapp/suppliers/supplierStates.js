'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('supplierSignup', {
                url: '/supplierSignup',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'suppliers/signup/supplierSignup.html',
                        controller: 'SupplierSignupController'
                    }
                }
            })
            .state('supplierDetails', {
                url: '/supplierDetails',
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
                        templateUrl: 'suppliers/details/supplierDetails.html',
                        controller: 'SupplierDetailsController'
                    }
                }
            })
            .state('suppliers', {
                url: '/suppliers',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'suppliers/list/supplierList.html',
                        controller: 'SupplierListController'
                    }
                }
            })

    });