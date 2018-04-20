'use strict';

angular.module('app')
    .service('reportService', function ($http) {
        return {
            getRestaurantRevenue: function (period, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/report/revenue/restaurant',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    params: {startDate: period.startDate.getTime(), endDate: period.endDate.getTime()}
                };
                $http(req).then(onSuccess, onError);
            },
            findWaiters: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/waiters/myWaiters'
                };
                $http(req).then(onSuccess, onError);
            },
            getWaitersRevenue: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/report/revenue/waiters'
                };
                $http(req).then(onSuccess, onError);
            },
            getDailyVisits: function (date, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/report/visits/daily',
                    params: {date: date.getTime()}
                };
                $http(req).then(onSuccess, onError);
            },
            getWeeklyVisits: function (date, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/report/visits/weekly',
                    params: {date: date.getTime()}
                };
                $http(req).then(onSuccess, onError);
            },
            getRestaurantRating: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/report/ratings/restaurant/me'
                };
                $http(req).then(onSuccess, onError);
            },
            getWaiterRating: function (waiter, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/report/ratings/waiter/' + waiter.id
                };
                $http(req).then(onSuccess, onError);
            },
            getItemRating: function (item, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/report/ratings/menu-item/' + item.id
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });