'use strict';

angular.module('app')
    .service('bidService', function ($http) {
        return {
            create: function (bid, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/bids',
                    data: bid
                };
                $http(req).then(onSuccess, onError);
            },
            findMyBid: function (offerId, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/bids/offer/' + offerId + "/me"
                };
                $http(req).then(onSuccess, onError);
            },
            findBids: function (offerId, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/bids/offer/' + offerId
                };
                $http(req).then(onSuccess, onError);
            },
            acceptBid: function (bid, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/bids/' + bid.id + '/accept'
                };
                $http(req).then(onSuccess, onError);
            },
            update: function (bid, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/bids/' + bid.id,
                    data: bid
                };
                $http(req).then(onSuccess, onError);
            },
            findMyBids: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/bids/me'
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });