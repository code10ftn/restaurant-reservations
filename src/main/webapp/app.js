'use strict';

angular.module('app', ['ui.router', 'ui.calendar', 'ui.bootstrap', 'chart.js', 'star-rating', 'AngularStompDK'])
    .config(function ($urlRouterProvider) {
        $urlRouterProvider.otherwise('/signin');
    })
    .run(function ($rootScope, authService) {
        authService.authenticate(
            function (res) {
                $rootScope.USER = {
                    id: res.data.id,
                    role: res.data.role,
                    firstName: res.data.firstName,
                    lastName: res.data.lastName,
                    email: res.data.email,
                    verified: res.data.verified
                }
            }, function () {
                $rootScope.USER = null;
            });
    })
    .config(function (ngstompProvider) {
        ngstompProvider
            .url('/api/notifications')
            .class(SockJS); // <-- Will be used by StompJS to do the connection
    });
