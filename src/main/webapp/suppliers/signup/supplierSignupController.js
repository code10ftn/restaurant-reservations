'use strict';

angular.module('app')
    .controller('SupplierSignupController', function ($rootScope, $scope, $state, supplierService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'MANAGER') {
            $state.go("signin");
        }

        $scope.signup = function () {
            supplierService.signup(
                $scope.supplier,
                function () {
                    $scope.supplier = {};
                    $state.go("signin");
                }, function (res) {
                    toastr.error(res.data.error);
                });
        };
    });