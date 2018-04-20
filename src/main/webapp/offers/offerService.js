'use strict';

angular.module('app')
    .service('offerService', function ($http) {
        return {
            create: function (offer, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/offers',
                    data: offer
                };
                $http(req).then(onSuccess, onError);
            },
            findMyOffers: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/offers/my-restaurant'
                };
                $http(req).then(onSuccess, onError);
            },
            findById: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/offers/' + id
                };
                $http(req).then(onSuccess, onError);
            },
            update: function (offer, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/offers/' + offer.id,
                    data: offer
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });