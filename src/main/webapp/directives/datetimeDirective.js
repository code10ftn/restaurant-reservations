'use strict';

angular.module('app')
    .directive('datetime', function () {
        return {
            require: 'ngModel',
            link: function (scope, elem, attrs, ngModelCtrl) {
                var updateModel = function () {
                    scope.$apply(function () {
                        ngModelCtrl.$modelValue = elem.val();
                    });
                };
                elem.datetimepicker({
                    useCurrent: false,
                    inline: true,
                    sideBySide: true
                });
                elem.on("change", function (e) {
                    updateModel();
                });
            }
        }
    });