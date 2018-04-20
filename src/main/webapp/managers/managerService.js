'use strict';

angular.module('app')
    .service('managerService', function ($http) {
        return {
            signup: function (user, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/managers',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: user
                };
                $http(req).then(onSuccess, onError);
            },
            getWorkshifts: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/shifts',
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            addShift: function (shift, waiterArea, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/shifts',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: shift,
                    params: {area: waiterArea}
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
            findAll: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/managers'
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });