'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('changePassword', {
                url: '/changePassword/:id',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'users/changePassword/changePassword.html',
                        controller: 'ChangePasswordController'
                    }
                }
            })
    });