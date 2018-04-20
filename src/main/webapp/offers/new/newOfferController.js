'use strict';

angular.module('app')
    .controller('NewOfferController', function ($rootScope, $scope, $stateParams, $state, offerService) {
        $scope.offer = {};
        $scope.offer.offerItems = [];

        $scope.addItem = function () {
            $scope.offer.offerItems.push({"name": $scope.offerItem.name, "amount": $scope.offerItem.amount});
            $('#modal').modal('hide');
            $scope.offerItem = {};

        };

        $scope.createOffer = function () {
            offerService.create(
                $scope.offer,
                function (res) {
                    $scope.offer = {};
                    $state.go('editOffer', {offerId: res.data.id});
                },
                function (res) {
                    toastr.error(res.data.error);
                })
        };
    });
