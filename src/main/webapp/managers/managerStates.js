'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('managers', {
                url: '/managers',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'managers/list/managerList.html',
                        controller: 'ManagerListController'
                    }
                }
            })
            .state('managerSignup', {
                url: '/managerSignup',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'managers/signup/managerSignup.html',
                        controller: 'ManagerSignupController'
                    }
                }
            })
            .state('worksheet', {
                url: '/worksheet',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'managers/worksheet/worksheet.html',
                        controller: 'WorksheetController'
                    }
                }
            });
    });