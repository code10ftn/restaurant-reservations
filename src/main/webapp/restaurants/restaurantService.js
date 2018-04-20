'use strict';

angular.module('app')
    .service('restaurantService', function ($http) {
        return {
            findAll: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/restaurants'
                };
                $http(req).then(onSuccess, onError);
            },
            findVisited: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/restaurants/visited'
                };
                $http(req).then(onSuccess, onError);
            },
            findById: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/restaurants/' + id
                };
                $http(req).then(onSuccess, onError);
            },
            create: function (restaurant, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/restaurants',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: restaurant
                };
                $http(req).then(onSuccess, onError);
            },
            update: function (restaurant, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/restaurants',
                    data: restaurant
                };
                $http(req).then(onSuccess, onError);
            },
            findCurrent: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/restaurants/me'
                };
                $http(req).then(onSuccess, onError);
            },
            findEmployeeRestaurant: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/restaurants/employee'
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });