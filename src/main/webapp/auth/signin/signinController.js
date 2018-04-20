'use strict';

angular.module('app')
    .controller('SigninController', function ($rootScope, $scope, $state, authService) {
        $scope.signin = function () {
            authService.signin(
                $scope.user,
                function (res) {
                    $rootScope.USER = {
                        id: res.data.id,
                        role: res.data.role,
                        firstName: res.data.firstName,
                        lastName: res.data.lastName,
                        email: res.data.email,
                        verified: res.data.verified
                    };

                    if ($rootScope.unSubscriber != null) {
                        $rootScope.unSubscriber.unSubscribeAll();
                    }

                    $scope.user = {};
                    toastr.success("Welcome!");

                    var role = $rootScope.USER.role;
                    switch (role) {
                        case 'ADMIN':
                            $state.go('restaurants');
                            break;
                        case 'MANAGER':
                            $rootScope.RESTAURANT = {id: res.data.restaurant.id};
                            $state.go('worksheet');
                            break;
                        case 'GUEST':
                            $state.go('browseRestaurants');
                            break;
                        case 'SUPPLIER':
                            $state.go('offers');
                            break;
                        default: // EMPLOYEE
                            $state.go('employeeDetails');
                    }

                }, function (res) {
                    $rootScope.USER = null;

                    if (res.data.id != null && !res.data.verified && res.data.role != 'GUEST') {
                        $state.go("changePassword", {id: res.data.id});
                    }
                    else {
                        toastr.error("Wrong username or password!");
                    }
                });
        };
    });