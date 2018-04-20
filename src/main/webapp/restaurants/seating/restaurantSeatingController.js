'use strict';

angular.module('app')
    .controller('RestaurantSeatingController', function ($rootScope, $scope, $state, restaurantService, tableService) {
        if ($rootScope.USER == null || $rootScope.USER.role != 'MANAGER') {
            $state.go("signin");
        } else {
            $scope.deleted = [];
            restaurantService.findCurrent(function (res) {
                $scope.restaurantId = res.data.id;
                $scope.getTables();
            }, function (res) {
                $scope.restaurantId = [];
                toastr.error(res.data.error);
            });
        }

        $scope.canvasPos = $('#canvas').offset();

        $scope.saveChanges = function () {
            var height = document.getElementById('canvas').offsetHeight;
            var width = document.getElementById('canvas').offsetWidth;
            jQuery.each($scope.tables, function (i, val) {
                val.horizontalPosition = val.horizontalPosition / width * 100;
                val.verticalPosition = val.verticalPosition / height * 100;
                if (val.status === "CHANGED") {
                    tableService.update(
                        val,
                        function (res) {
                            val = res.data;
                            val.status = "OLD";
                        },
                        function (res) {
                            toastr.error(res.data.error);
                        })
                } else if (val.status === "NEW") {
                    tableService.create(
                        val,
                        function (res) {
                            val.status = "OLD";
                        },
                        function (res) {
                            toastr.error(res.data.error);
                        }
                    );
                }
            });
            jQuery.each($scope.deleted, function (i, val) {
                tableService.delete(
                    val,
                    function (res) {
                    },
                    function (res) {
                        $scope.tables.push(val);
                        toastr.error(res.data.error);
                    })
            });
            $scope.deleted = [];
            $state.reload();
        };

        $scope.addTable = function () {
            $scope.newTable.horizontalPosition = 0;
            $scope.newTable.verticalPosition = 0;
            $scope.newTable.restaurant = {id: $scope.restaurantId};
            $scope.newTable.id = new Date().getMilliseconds();
            $scope.newTable.status = "NEW";

            var height = document.getElementById('canvas').offsetHeight;
            var width = document.getElementById('canvas').offsetWidth;
            $scope.setTablePos($scope.newTable, height, width);
            $scope.setColor($scope.newTable);
            $scope.tables.push(JSON.parse(JSON.stringify($scope.newTable)));

        };

        $scope.removeTable = function (index) {
            if ($scope.tables[index].status !== "NEW")
                $scope.deleted.push($scope.tables[index]);
            $scope.tables.splice(index, 1);
        };

        $scope.isDisabled = function (table) {
        };

        $scope.setColor = function (table) {
            var color = "blue";

            switch (table.area) {
                case "INSIDE_NONSMOKING":
                    color = "blue";
                    break;
                case "INSIDE_SMOKING":
                    color = "orange";
                    break;
                case "GARDEN_OPEN":
                    color = "green";
                    break;
                case "GARDEN_CLOSED":
                    color = "aqua";
                    break;
            }

            table.color = color;
        };

        $scope.setTablePos = function (table, height, width) {
            table.horizontalPosition = table.horizontalPosition / 100 * width;
            table.verticalPosition = table.verticalPosition / 100 * height;
            table.x = table.horizontalPosition;
            table.y = table.verticalPosition;
            table.w = width / 10;
            table.h = height / 10;
        };

        $scope.getTables = function () {
            var height = document.getElementById('canvas').clientHeight;
            var width = document.getElementById('canvas').clientWidth;
            tableService.getRestaurantTables(
                $scope.restaurantId,
                function (res) {
                    $scope.tables = res.data;
                    jQuery.each(res.data, function (i, val) {
                        val.status = "OLD";

                        $scope.setTablePos(val, height, width);
                        $scope.setColor(val);

                    });
                },
                function (res) {
                    $scope.tables = [];
                    toastr.error(res.data.error);
                });
        };

        // target elements with the "draggable" class
        interact('.draggable')
            .draggable({
                // enable inertial throwing
                inertia: false,
                // keep the element within the area of it's parent
                restrict: {
                    restriction: "parent",
                    endOnly: true,
                    elementRect: {top: 0, left: 0, bottom: 1, right: 1}
                },
                // enable autoScroll
                autoScroll: true,

                // call this function on every dragmove event
                onmove: dragMoveListener,
                // call this function on every dragend event
                onend: function (event) {
                    var textEl = event.target.querySelector('p');

                }
            });

        function dragMoveListener(event) {
            var target = event.target,
                // keep the dragged position in the data-x/data-y attributes
                x = (parseFloat(target.getAttribute('data-x')) || 0) + event.dx,
                y = (parseFloat(target.getAttribute('data-y')) || 0) + event.dy;

            // translate the element
            target.style.webkitTransform =
                target.style.transform =
                    'translate(' + x + 'px, ' + y + 'px)';

            // update the posiion attributes
            target.setAttribute('data-x', x);
            target.setAttribute('data-y', y);

            var id = target.getAttribute('id');

            var result = $.grep($scope.tables, function (e) {
                return e.id == id;
            });
            var table = result[0];


            var obj = $('#' + id);

            var childPos = obj.offset();
            var parentPos = obj.parent().offset();
            var childOffset = {
                top: childPos.top - parentPos.top,
                left: childPos.left - parentPos.left
            }

            var canvasWidth = parseFloat(obj.parent().css('width').split('px')[0]);
            var canvasHeight = parseFloat(obj.parent().css('height').split('px')[0]);

            var tableWidth = 50;
            var tableHeight = 50;

            table.horizontalPosition = x;// childOffset.left / (canvasWidth - tableWidth) * 100;
            table.verticalPosition = y;// childOffset.top / (canvasHeight - tableHeight) * 100;

            if (table.status === "OLD")
                table.status = "CHANGED";

        }

        // this is used later in the resizing and gesture demos
        window.dragMoveListener = dragMoveListener;
    });