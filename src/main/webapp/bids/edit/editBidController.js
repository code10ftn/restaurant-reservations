'use strict';

angular.module('app')
    .controller('EditBidController', function ($rootScope, $scope, $stateParams, $state, bidService) {
        if ($rootScope.USER == null || $rootScope.USER.role !== 'SUPPLIER') {
            $state.go('signin');
        } else {
            $scope.bid = {};
            $scope.notChanged = true;
            bidService.findMyBid($stateParams.id, function (res) {
                $scope.bid = res.data;
                $scope.bid.dateOfDelivery = new Date($scope.bid.dateOfDelivery);

                $scope.disabled = $scope.bid.status !== 'ACTIVE';

                $scope.disabled = $scope.bid.offer.endDate < new Date().getTime();

            }, function () {
                $scope.bid = {};
                $state.go("signin");
            });

            $scope.change = function () {
                $scope.notChanged = false;
            };
            $scope.update = function () {
                bidService.update($scope.bid, function (res) {
                    $scope.bid = res.data;
                    $scope.bid.dateOfDelivery = new Date($scope.bid.dateOfDelivery);
                    toastr.success(res.data);
                }, function (res) {
                    toastr.error(res.data.error);
                })
            };
        }
    });
