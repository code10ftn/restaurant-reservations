'use strict';

angular.module('app')
    .controller('EditOfferController', function ($rootScope, $scope, $uibModal, $stateParams, $state, offerService, bidService) {
        if ($rootScope.USER == null || $rootScope.USER.role !== 'MANAGER') {
            $state.go('signin');
        } else {
            $scope.notChanged = true;
            offerService.findById($stateParams.offerId,
                function (res) {
                    $scope.offer = res.data;
                    $scope.offer.startDate = new Date(res.data.startDate);
                    $scope.offer.endDate = new Date(res.data.endDate);
                }, function (res) {
                    toastr.error(res.data.error);
                    $state.go('offers');
                });

            $scope.save = function () {
                offerService.update($scope.offer, function (res) {
                    $scope.offer = res.data;
                    $scope.offer.startDate = new Date(res.data.startDate);
                    $scope.offer.endDate = new Date(res.data.endDate);
                    $scope.notChanged = true;
                }, function (res) {
                    toastr.error(res.data.error);
                })
            };

            $scope.change = function () {
                $scope.notChanged = false;
            };

            $scope.addItem = function () {
                $scope.offer.offerItems.push({"name": $scope.offerItem.name, "amount": $scope.offerItem.amount});
                $scope.offerItem = {};
                $scope.notChanged = false;
                $('#modal').modal('hide');
            };

            $scope.bids = function () {
                var now = new Date().getTime();
                if (now < $scope.offer.startDate.getTime() || now > $scope.offer.endDate.getTime()) {
                    toastr.error("Offer has expired!");
                    return;
                }
                $state.go('bids', {id: $scope.offer.id});
            };

            $scope.editItem = function (item) {
                $scope.offerItem = angular.copy(item);
                $uibModal.open({
                    templateUrl: 'dialogs/editOfferDialog.html',
                    controller: 'EditOfferDialogController',
                    scope: $scope
                })
                    .result.then(function () {
                    item.name = $scope.offerItem.name;
                    item.amount = $scope.offerItem.amount;
                    $scope.offerItem = {};
                    $scope.notChanged = false;
                }, function () {
                    $scope.offerItem = {};
                });
            };

            $scope.deleteItem = function (item) {
                var index = $scope.offer.offerItems.indexOf(item);
                $scope.offer.offerItems.splice(index, 1);
                $scope.notChanged = false;
            };
        }
    });