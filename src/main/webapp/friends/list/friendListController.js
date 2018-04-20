'use strict';

angular.module('app')
    .controller('FriendListController', function ($rootScope, $scope, $state, guestService, friendService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'GUEST') {
            $state.go("signin");
        } else {
            guestService.findNonFriends(
                function (res) {
                    $scope.nonFriends = res.data;
                },
                function () {
                    $scope.nonFriends = [];
                });
            guestService.findFriends(
                function (res) {
                    $scope.friends = res.data;
                },
                function () {
                    $scope.friends = [];
                });
            guestService.findPending(
                function (res) {
                    $scope.pendings = res.data;
                },
                function () {
                    $scope.pendings = [];
                });
            guestService.findRequests(
                function (res) {
                    $scope.requests = res.data;
                },
                function () {
                    $scope.requests = [];
                });
        }

        $scope.removeFriend = function (user, index) {
            friendService.remove(user.id,
                function () {
                    $scope.friends.splice(index, 1);
                },
                function (res) {
                    toastr.error(res.data.error);
                });
        };

        $scope.cancelPending = function (user, index) {
            friendService.remove(user.id,
                function () {
                    $scope.pendings.splice(index, 1);
                },
                function (res) {
                    toastr.error(res.data.error);
                });
        };

        $scope.acceptRequest = function (user, index) {
            friendService.accept(user.id,
                function () {
                    $scope.requests.splice(index, 1);
                    $scope.friends.push(user);
                },
                function (res) {
                    toastr.error(res.data.error);
                });
        };

        $scope.declineRequest = function (user, index) {
            friendService.remove(user.id,
                function () {
                    $scope.requests.splice(index, 1);
                },
                function (res) {
                    toastr.error(res.data.error);
                });
        };

        $scope.addFriend = function (user) {
            friendService.create(user,
                function () {
                    $scope.pendings.push(user);
                },
                function (res) {
                    toastr.error(res.data.error);
                });
        };
    });