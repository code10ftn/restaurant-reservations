'use strict';

angular.module('app')
    .service('tableService', function ($http) {
        return {
            getRestaurantTables: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/tables/restaurant/' + id,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            getAvailableRestaurantTables: function (id, date, length, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/tables/restaurant/' + id + '/available',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    params: {reservationDate: date, reservationLength: length}
                };
                $http(req).then(onSuccess, onError);
            },
            getByArea: function (restaurant, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/tables/restaurant',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: restaurant
                };
                $http(req).then(onSuccess, onError);
            },
            update: function (table, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/tables/' + table.id,
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: table
                };
                $http(req).then(onSuccess, onError);
            },
            create: function (table, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/tables/',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: table
                };
                $http(req).then(onSuccess, onError);
            },
            delete: function (table, onSuccess, onError) {
                var req = {
                    method: 'DELETE',
                    url: '/api/tables/' + table.id,
                    headers: {
                        'Content-Type': 'application/json'
                    }
                };
                $http(req).then(onSuccess, onError);
            }
        };
    });
