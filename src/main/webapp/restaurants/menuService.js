'use strict';

angular.module('app')
    .service('menuService', function ($http) {
            return {
                createItem: function (menuItem, onSuccess, onError) {
                    var req = {
                        method: 'POST',
                        url: '/api/menuItems',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        data: menuItem
                    };
                    $http(req).then(onSuccess, onError);
                },
                findMenu: function (restaurantId, onSuccess, onError) {
                    var req = {
                        method: 'GET',
                        url: '/api/menus/restaurant/' + restaurantId,
                        headers: {
                            'Content-Type': 'application/json'
                        }
                    };
                    $http(req).then(onSuccess, onError);
                },
                update: function (menu, onSuccess, onError) {
                    var req = {
                        method: 'PUT',
                        url: '/api/menus/' + menu.id,
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        data: menu
                    };
                    $http(req).then(onSuccess, onError);
                }
            }
        }
    );
