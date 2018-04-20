'use strict';

angular.module('app')
    .controller('NotificationsController', function ($rootScope, $scope, notificationService, ngstomp) {
        $scope.notifications = [];

        if ($rootScope.USER != null) {
            // $scope.socket = new SockJS('/api/notifications');
            // $rootScope.stompClient = Stomp.over($scope.socket);
            $scope.topic = '';

            var role = $rootScope.USER.role;
            switch (role) {
                case 'ADMIN':
                    break;
                case 'MANAGER':
                    break;
                case 'GUEST':
                    $scope.topic = $rootScope.USER.id;
                    break;
                case 'CHEF':
                    $scope.topic = "ordersFood";
                    break;
                case 'BARTENDER':
                    $scope.topic = "ordersDrinks";
                    break;
                case 'WAITER':
                    $scope.topic = $rootScope.USER.id;
                    break;
                case 'SUPPLIER':
                    $scope.topic = $rootScope.USER.id;
                    break;
            }

            notificationService.findByTopic($scope.topic,
                function (res) {
                    $scope.notifications = res.data;
                },
                function () {
                    $scope.notifications = [];
                });

            ngstomp
                .subscribeTo("/topic/" + $scope.topic)
                .callback(function (data) {
                    // $scope.notifications.push(JSON.parse(data.body));
                    // $scope.$apply();
                    // toastr.info("New notification!");
                })
                .connect();

            var items = [];

            $rootScope.unSubscriber = ngstomp
                .subscribeTo("/topic/" + $scope.topic)
                .callback(function (data) {
                    $scope.notifications.push(JSON.parse(data.body));
                    //$scope.$apply();
                    toastr.info("New notification!");
                })
                .bindTo($scope)
                .connect();

            // $rootScope.stompClient.connect({}, function () {
            //     $rootScope.stompClient.subscribe("/topic/" + $scope.topic, function (data) {
            //         $scope.notifications.push(JSON.parse(data.body));
            //         $scope.$apply();
            //         toastr.info("New notification!");
            //     });
            // });
        }
    });