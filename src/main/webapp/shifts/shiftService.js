'use strict';

angular.module('app')
    .service('shiftService', function ($http) {
        return {
            getShifts: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/shifts/restaurant/' + id,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            addShift: function (shift, area, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/shifts',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: shift,
                    params: {area: area}
                };
                $http(req).then(onSuccess, onError);
            },
            updateShift: function (id, shift, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/shifts/' + id,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: shift
                };
                $http(req).then(onSuccess, onError);
            },
            updateAllShifts: function (shifts, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/shifts',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: shifts
                };
                $http(req).then(onSuccess, onError);
            },
            deleteShift: function (id, onSuccess, onError) {
                var req = {
                    method: 'DELETE',
                    url: '/api/shifts/' + id,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            findCurrentShift: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/shifts/me',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });