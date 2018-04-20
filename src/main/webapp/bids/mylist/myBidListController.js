'use strict';

angular.module('app')
    .controller('MyBidListController', function ($rootScope, $scope, $state, $stateParams, bidService) {
        if ($rootScope.USER == null || $rootScope.USER.role !== 'SUPPLIER') {
            $state.go("signin");
        }

        bidService.findMyBids(
            function (res) {
                $scope.bids = res.data;
            },
            function () {
                $scope.bids = [];
            });

        $scope.openBid = function (bid) {
            $state.go('editBid', {id: bid.id});
        };
    });