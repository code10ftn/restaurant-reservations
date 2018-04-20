angular.module('app')
    .service('guestService', function ($http) {
        return {
            findAll: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/guests'
                };
                $http(req).then(onSuccess, onError);
            },
            update: function (id, user, onSucces, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/guests/' + id,
                    data: user
                };
                $http(req).then(onSucces, onError);
            },
            findNonFriends: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/guests/nonFriends'
                };
                $http(req).then(onSuccess, onError);
            },
            findFriends: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/guests/friends'
                };
                $http(req).then(onSuccess, onError);
            },
            findPending: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/guests/pending'
                };
                $http(req).then(onSuccess, onError);
            },
            findRequests: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/guests/requests'
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });