angular.module('app')
    .service('supplierService', function ($http) {
        return {
            signup: function (user, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/suppliers',
                    headers: {
                        'Content-Type': 'application/json'
                    },
                    data: user
                };
                $http(req).then(onSuccess, onError);
            },
            update: function (id, user, onSucces, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/suppliers/' + id,
                    data: user
                };
                $http(req).then(onSucces, onError);
            },
            findAllRestaurant: function (onSucces, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/suppliers/me'
                };
                $http(req).then(onSucces, onError);
            }
        }
    });
