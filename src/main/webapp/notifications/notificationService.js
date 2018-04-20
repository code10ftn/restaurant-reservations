'use strict';

angular.module('app')
    .service('notificationService', function ($http) {
        return {
            findByTopic: function (topic, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/notifications/' + topic
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });