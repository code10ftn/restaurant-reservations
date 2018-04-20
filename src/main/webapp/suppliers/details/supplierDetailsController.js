'use strict';

angular.module('app')
    .controller('SupplierDetailsController', function ($rootScope, $scope, $state, userService, supplierService) {
        if ($rootScope.USER == null || $rootScope.USER.role !== 'SUPPLIER') {
            $state.go("signin");
        } else {
            $scope.notChanged = true;
            $scope.name = "";

            userService.findById($rootScope.USER.id, function (res) {
                $scope.user = res.data;
                $scope.name = $scope.user.firstName;
            }, function (res) {
                $scope.user = {};
                toastr.error(res.data.error);
            });
        }

        $scope.save = function () {
            supplierService.update($scope.user.id, $scope.user, function (res) {
                $scope.user = res.data;
                $scope.name = $scope.user.firstName;
                $scope.notChanged = true;
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.change = function () {
            $scope.notChanged = false;
        };

        $scope.updatePassword = function () {
            userService.updatePassword($scope.passwords, -100,
                function () {
                    toastr.success("Password changed!");
                }, function (res) {
                    toastr.error(res.data.error);
                });
        };
    });