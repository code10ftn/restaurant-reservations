'use strict';

angular.module('app')
    .controller('VisitsController', function ($rootScope, $scope, $state, reportService) {
        if ($rootScope.USER == null || $rootScope.USER.role !== 'MANAGER') {
            $state.go("signin");
        }

        $scope.type = "DAILY";

        $scope.showGraph = function () {
            switch ($scope.type) {
                case "DAILY":
                    reportService.getDailyVisits($scope.date, function (res) {
                        $scope.labels = res.data.hours;
                        $scope.series = ['Series A'];

                        $scope.data = res.data.visits;
                    });
                    break;
                case "WEEKLY":
                    reportService.getWeeklyVisits($scope.date, function (res) {
                        $scope.labels = res.data.days;
                        $scope.series = ['Series A'];

                        $scope.data = res.data.visits;
                    });
                    break;
            }

        };
    });