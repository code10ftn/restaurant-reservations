angular.module('app')
    .service('employeeService', function ($http) {
        return {
            findAll: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/employees/restaurant/' + id
                };
                $http(req).then(onSuccess, onError);
            },
            findRestaurantEmployees: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/employees/'
                };
                $http(req).then(onSuccess, onError);
            },
            findById: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/employees/' + id
                };
                $http(req).then(onSuccess, onError);
            },
            update: function (id, user, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/employees/' + id,
                    data: user
                };
                $http(req).then(onSuccess, onError);
            },
            signupChef: function (user, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/chefs',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: user
                };
                $http(req).then(onSuccess, onError);
            },
            signupWaiter: function (user, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/waiters',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: user
                };
                $http(req).then(onSuccess, onError);
            },
            signupBartender: function (user, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/bartenders',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: user
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });