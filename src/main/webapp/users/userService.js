angular.module('app')
    .service('userService', function ($http) {
        return {
            findById: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/users/' + id
                };
                $http(req).then(onSuccess, onError);
            },
            findAll: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/users'
                };
                $http(req).then(onSuccess, onError);
            },
            updatePassword: function (passwords, id, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/users/' + id,
                    data: passwords
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });