'use strict';

angular.module('app')
    .controller('OfferDetailsController', function ($rootScope, $scope, $stateParams, $state, offerService, bidService) {
        if ($rootScope.USER == null || $rootScope.USER.role !== 'SUPPLIER' && $rootScope.USER.role !== 'MANAGER') {
            $state.go('signin');
        } else {

            bidService.findMyBid($stateParams.offerId,
                function (res) {

                    $scope.BidId = res.data.id;
                    $scope.bidPlaced = true;
                },
                function (res) {
                    $scope.bidPlaced = false;
                });

            offerService.findById($stateParams.offerId,
                function (res) {
                    $scope.offer = res.data;
                    $scope.expired = res.data.endDate < new Date().getTime();
                }, function (res) {
                    toastr.error(res.data.error);
                    $state.go('offers');
                });

            $scope.newBid = function (offer) {
                $state.go('newBid', {id: offer.id});
            }

            $scope.goToBid = function () {
                $state.go('editBid', {id: $stateParams.offerId});
            }
        }
    });