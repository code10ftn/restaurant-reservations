angular.module('app')
    .service('orderService', function ($http) {
        return {
            findAll: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/orders/waiter'
                };
                $http(req).then(onSuccess, onError);
            },
            findAllUnaccepted: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/orders/unaccepted'
                };
                $http(req).then(onSuccess, onError);
            },
            findById: function (id, onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/orders/' + id
                };
                $http(req).then(onSuccess, onError);
            },
            create: function (order, onSuccess, onError) {
                var req = {
                    method: 'POST',
                    url: '/api/orders',
                    data: order
                };
                $http(req).then(onSuccess, onError);
            },
            update: function (id, order, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/orders/' + id,
                    data: order
                };
                $http(req).then(onSuccess, onError);
            },
            accept: function (id, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/orders/' + id + '/accept'
                };
                $http(req).then(onSuccess, onError);
            },
            close: function (id, waiterId, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/orders/' + id + '/close',
                    params: {
                        waiterId: waiterId
                    }
                };
                $http(req).then(onSuccess, onError);
            },
            findOrderItems: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/orderItems'
                };
                $http(req).then(onSuccess, onError);
            },
            findChefOrderItems: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/orderItems/chef'
                };
                $http(req).then(onSuccess, onError);
            },
            findBartenderOrderItems: function (onSuccess, onError) {
                var req = {
                    method: 'GET',
                    url: '/api/orderItems/bartender'
                };
                $http(req).then(onSuccess, onError);
            },
            updateOrderItemChef: function (id, status, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/orderItems/' + id + '/chef',
                    data: status
                };
                $http(req).then(onSuccess, onError);
            },
            updateOrderItemBartender: function (id, status, onSuccess, onError) {
                var req = {
                    method: 'PUT',
                    url: '/api/orderItems/' + id + '/bartender',
                    data: status
                };
                $http(req).then(onSuccess, onError);
            }
        }
    });