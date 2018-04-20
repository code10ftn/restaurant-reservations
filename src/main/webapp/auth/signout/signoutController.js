'use strict';

angular.module('app')
    .controller('SignoutController', function ($rootScope, $scope, $state, authService) {
        authService.signout(
            function () {
                $rootScope.USER = null;
                $state.go("signin");
                if ($rootScope.unSubscriber != null) {
                    $rootScope.unSubscriber.unSubscribeAll();
                }
            },
            function () {
                $rootScope.USER = null;
                $state.go("signin");
            });
    });