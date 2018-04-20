'use strict';

angular.module('app')
    .controller('EmployeeWorksheetController', function ($rootScope, $scope, $compile, $state, managerService, shiftService, restaurantService) {
        if ($rootScope.USER == null ||
            ($rootScope.USER.role != 'BARTENDER' && $rootScope.USER.role != 'CHEF' && $rootScope.USER.role != 'WAITER')) {
            $state.go("signin");
        }

        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();

        var eventColor = '#ed1524';
        if ($rootScope.USER.role == 'WAITER') {
            eventColor = '#5c5c3d';
        } else if ($rootScope.USER.role == 'CHEF') {
            eventColor = '#3366cc';
        }

        $scope.events = {
            color: eventColor,
            textColor: 'white',
            events: []
        };

        restaurantService.findEmployeeRestaurant(function (res) {
            shiftService.getShifts(res.data.id, function (res) {
                $scope.shifts = res.data;
                $scope.shifts.forEach(function (element, index, array) {
                    $scope.addEventToList(element);
                });
            }, function () {
                $scope.events = [];
            });
        }, function (res) {
            toastr.error(res.data.error);
        });

        $scope.alertOnEventClick = function (date, jsEvent, view) {
            $scope.openedShift = date;
            switch (date.startDay) {
                case 0:
                    $scope.openedShift.day = "Sunday";
                    break;
                case 1:
                    $scope.openedShift.day = "Monday";
                    break;
                case 2:
                    $scope.openedShift.day = "Tuesday";
                    break;
                case 3:
                    $scope.openedShift.day = "Wednesday";
                    break;
                case 4:
                    $scope.openedShift.day = "Thursday";
                    break;
                case 5:
                    $scope.openedShift.day = "Friday";
                    break;
                case 6:
                    $scope.openedShift.day = "Saturday";
                    break;
                default:
                    $scope.openedShift.day = "Sunday";
            }
            $("#shiftModal").modal("show");
        };

        $scope.addEventToList = function (shift) {
            var startDate = new Date(shift.startTime);
            var endDate = new Date(shift.endTime);

            var startDay = startDate.getDay();
            if (startDay == 0) {
                startDay = 7;
            }
            var endDay = endDate.getDay();
            if (endDay == 0) {
                endDay = 7;
            }

            var today = date.getDay();
            var startOffset = today - startDay;
            var endOffset = today - endDay;

            var event = {
                id: shift.id,
                title: shift.employee.firstName + ' ' + shift.employee.lastName,
                role: shift.employee.role,
                startDay: startDay,
                start: new Date(y, m, d - startOffset, startDate.getHours(), startDate.getMinutes()),
                end: new Date(y, m, d - endOffset, endDate.getHours(), endDate.getMinutes()),
                area: shift.area
            };

            if (shift.employee.role == $rootScope.USER.role) {
                $scope.events.events.push(event);
            }
        };

        /* Render Tooltip */
        $scope.eventRender = function (event, element) {
            $compile(element)($scope);
        };
        /* config object */
        $scope.uiConfig = {
            calendar: {
                firstDay: 1,
                height: 550,
                editable: false,
                header: {
                    left: '',
                    center: '',
                    right: ''
                },
                eventClick: $scope.alertOnEventClick,
                eventRender: $scope.eventRender,
                defaultView: 'agendaWeek',
                timeFormat: 'H(:mm)'
            }
        };

        $scope.eventSources = [$scope.events];
    });