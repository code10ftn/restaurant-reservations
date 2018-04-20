'use strict';

angular.module('app')
    .controller('BidListController', function ($rootScope, $scope, $state, $stateParams, bidService) {
        if ($rootScope.USER == null || $rootScope.USER.role !== 'MANAGER') {
            $state.go("signin");
        }

        bidService.findBids($stateParams.id,
            function (res) {
                $scope.bids = res.data;
            },
            function () {
                $scope.bids = [];
            });

        $scope.openBid = function (bid) {
            if ($rootScope.USER.role === 'MANAGER')
                $state.go('bidDetails', {id: bid.id});
        };

        $scope.accept = function (bid) {
            bidService.acceptBid(bid, function (res) {
                    toastr.success(res.data.status);
                    $state.reload();
                },
                function (res) {
                    toastr.error(res.data.error);
                })
        }
    });