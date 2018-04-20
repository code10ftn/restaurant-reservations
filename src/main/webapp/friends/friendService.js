angular.module('app')
    .service('friendService', function ($http) {
        return {
            create: function (receiver, onSucces, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/friendships',
                    data: receiver
                };
                $http(req).then(onSucces, onError);
            },
            accept: function (id, onSucces, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/friendships/' + id
                };
                $http(req).then(onSucces, onError);
            },
            remove: function (id, onSucces, onError) {
                var req = {
                    method: 'DELETE',
                    url: '/api/friendships/' + id
                };
                $http(req).then(onSucces, onError);
            }
        }
    });