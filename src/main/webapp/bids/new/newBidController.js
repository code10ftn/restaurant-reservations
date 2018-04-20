'use strict';

angular.module('app')
    .controller('NewBidController', function ($rootScope, $scope, $stateParams, $state, bidService) {
        $scope.bid = {};

        $scope.createBid = function () {
            $scope.bid.offer = {id: $stateParams.id};
            bidService.create(
                $scope.bid,
                function (res) {
                    $scope.bid = {};
                    $state.go('editBid', {id: $stateParams.id});
                },
                function (res) {
                    toastr.error(res.data.error);
                })
        };
    });
