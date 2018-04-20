'use strict';

angular.module('app')
    .controller('WorksheetController', function ($rootScope, $scope, $compile, $state, managerService, shiftService, employeeService, restaurantService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'MANAGER') {
            $state.go("signin");
        }

        $scope.shiftArea = {};
        restaurantService.findCurrent(
            function (res) {
                employeeService.findAll(res.data.id, function (res) {
                    $scope.employees = res.data;
                }, function (res) {
                    $scope.employees = [];
                });

                shiftService.getShifts(res.data.id, function (res) {
                    $scope.shifts = res.data;
                    $scope.shifts.forEach(function (element, index, array) {
                        $scope.addEventToList(element);
                    });
                }, function () {
                    $scope.eventsBartenders.events = [];
                    $scope.eventsWaiters.events = [];
                    $scope.eventsChefs.events = [];
                });

            }, function (res) {
                toastr.error(res.data.error);
            });

        $scope.notChanged = true;

        var date = new Date();
        var d = date.getDate();
        var m = date.getMonth();
        var y = date.getFullYear();
        $scope.employeesSelect = [];

        $scope.eventsBartenders = {
            color: '#ed1524',
            textColor: 'white',
            events: []
        };

        $scope.eventsWaiters = {
            color: '#5c5c3d',
            textColor: 'white',
            events: []
        };

        $scope.eventsChefs = {
            color: '#3366cc',
            textColor: 'white',
            events: []
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
            if (today == 0) {
                today = 7;
            }
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

            if (shift.employee.role == 'WAITER') {
                $scope.eventsWaiters.events.push(event);
            } else if (shift.employee.role == 'CHEF') {
                $scope.eventsChefs.events.push(event);
            } else {
                $scope.eventsBartenders.events.push(event);
            }
        };

        $scope.typeSelectChanged = function () {
            $scope.selectedEmployee = undefined;
            $scope.employeesSelect = [];
            $scope.employees.forEach(function (element, index, array) {
                if (element.role == $scope.role) {
                    $scope.employeesSelect.push(element);
                }
            });
        };

        /* alert on eventClick */
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
        /* alert on Drop */
        $scope.alertOnDrop = function (event, delta, revertFunc, jsEvent, ui, view) {
            $scope.notChanged = false;
            $scope.shifts.forEach(function (element, index, array) {
                if (element.id == event.id) {
                    $scope.shifts[index].startTime += delta;
                    $scope.shifts[index].endTime += delta;
                    return;
                }
            });
        };
        /* alert on Resize */
        $scope.alertOnResize = function (event, delta, revertFunc, jsEvent, ui, view) {
            $scope.notChanged = false;
            $scope.shifts.forEach(function (element, index) {
                if (element.id == event.id) {
                    $scope.shifts[index].endTime += delta;
                }
            });
        };

        /* add custom event*/
        $scope.addEvent = function () {
            var startDate = new Date();
            startDate.setDate(startDate.getDate() - startDate.getDay() + $scope.day - 1);
            startDate.setHours($scope.startHr);
            startDate.setMinutes($scope.startMin);

            var endDate = new Date();
            endDate.setDate(endDate.getDate() - endDate.getDay() + $scope.day - 1);
            endDate.setHours($scope.endHr);
            endDate.setMinutes($scope.endMin);

            employeeService.findById($scope.selectedEmployee, function (res) {
                var shift = {};
                shift.startTime = startDate;
                shift.endTime = endDate;
                shift.employee = res.data;
                shiftService.addShift(shift, $scope.shiftArea.area, function (res) {
                    $scope.addEventToList(res.data);
                }, function (res) {
                    toastr.error(res.data.error);
                });
            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.saveChanges = function () {
            $scope.notChanged = true;
            shiftService.updateAllShifts($scope.shifts, function () {

            }, function (res) {
                toastr.error(res.data.error);
            });
        };

        $scope.deleteShift = function () {
            if ($scope.openedShift != null) {
                shiftService.deleteShift($scope.openedShift.id, function () {
                    switch ($scope.openedShift.role) {
                        case "BARTENDER":
                            $scope.removeFromList($scope.openedShift.id, $scope.eventsBartenders.events);
                            break;
                        case "CHEF":
                            $scope.removeFromList($scope.openedShift.id, $scope.eventsChefs.events);
                            break;
                        default:
                            $scope.removeFromList($scope.openedShift.id, $scope.eventsWaiters.events);
                    }
                }, function (res) {
                    toastr.error(res.data.error);
                });
            }
        };

        $scope.removeFromList = function (id, list) {
            list.forEach(function (element, index, array) {
                if (element.id == id) {
                    list.splice(index, 1);
                }
            });

            $scope.shifts.forEach(function (element, index) {
                if (element.id == id) {
                    $scope.shifts.splice(index, 1);
                }
            });
        };

        /* Render Tooltip */
        $scope.eventRender = function (event, element) {
            $compile(element)($scope);
        };
        /* config object */
        $scope.uiConfig = {
            calendar: {
                firstDay: 1,
                height: 450,
                editable: true,
                header: {
                    left: '',
                    center: '',
                    right: ''
                },
                eventClick: $scope.alertOnEventClick,
                eventDrop: $scope.alertOnDrop,
                eventResize: $scope.alertOnResize,
                eventRender: $scope.eventRender,
                defaultView: 'agendaWeek',
                timeFormat: 'H(:mm)'
            }
        };

        $scope.eventSources = [$scope.eventsBartenders, $scope.eventsWaiters, $scope.eventsChefs];
    });