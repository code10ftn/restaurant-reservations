'use strict';

angular.module('app')
    .controller('OfferListController', function ($rootScope, $scope, $state, offerService) {
        if ($rootScope.USER == null || ($rootScope.USER.role !== 'SUPPLIER' && $rootScope.USER.role !== 'MANAGER')) {
            $state.go("signin");
        }

        offerService.findMyOffers(
            function (res) {
                $scope.offers = res.data;
            },
            function () {
                $scope.offers = [];
            });

        $scope.openOffer = function (offer) {
            if ($rootScope.USER.role === 'SUPPLIER')
                $state.go('offerDetails', {offerId: offer.id});
            else if ($rootScope.USER.role === 'MANAGER')
                $state.go('editOffer', {offerId: offer.id});
        }
    });