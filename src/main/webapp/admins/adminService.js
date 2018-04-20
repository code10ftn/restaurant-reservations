'use strict';

angular.module('app')
    .service('adminService', function ($http) {
        return {
            signup: function (user, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/admins',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: user
                };
                $http(req).then(onSuccess, onError);
            },
            findAll: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/admins'
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });