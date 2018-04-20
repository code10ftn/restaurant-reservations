'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('employeeDetails', {
                url: '/employeeDetails',
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
                        templateUrl: 'employees/details/employeeDetails.html',
                        controller: 'EmployeeDetailsController'
                    }
                }
            })
            .state('employees', {
                url: '/employees',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'employees/list/employeeList.html',
                        controller: 'EmployeeListController'
                    }
                }
            })
            .state('employeeSignup', {
                url: '/employeeSignup',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'employees/signup/employeeSignup.html',
                        controller: 'EmployeeSignupController'
                    }
                }
            })
            .state('employeeWorksheet', {
                url: '/employeeWorksheet',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'content': {
                        templateUrl: 'employees/worksheet/employeeWorksheet.html',
                        controller: 'EmployeeWorksheetController'
                    }
                }
            });
    });