'use strict';

angular.module('app')
    .controller('SupplierListController', function ($scope, $rootScope, supplierService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'MANAGER') {
            $state.go("signin");
        }
        supplierService.findAllRestaurant(
            function (res) {
                $scope.suppliers = res.data;
            },
            function () {
                $scope.suppliers = [];
            });
    });