'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('admins', {
                url: '/admins',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'admins/list/adminList.html',
                        controller: 'AdminListController'
                    }
                }
            })
            .state('adminSignup', {
                url: '/adminSignup',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'admins/signup/adminSignup.html',
                        controller: 'AdminSignupController'
                    }
                }
            });
    });