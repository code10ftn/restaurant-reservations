'use strict';

angular.module('app')
    .controller('SignupController', function ($scope, $state, authService) {
        $scope.signup = function () {
            authService.signup(
                $scope.user,
                function () {
                    toastr.success("Verification email sent to " + $scope.user.email + "!");
                    $scope.user = {};
                    $state.go("signin");
                }, function (res) {
                    toastr.error(res.data.error);
                });
        };
    });