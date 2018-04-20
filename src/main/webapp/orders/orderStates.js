'use strict';

angular.module('app')
    .config(function ($stateProvider) {
        $stateProvider
            .state('myOrders', {
                url: '/orders/me',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'notifications': {
                        templateUrl: 'notifications/notifications.html',
                        controller: 'NotificationsController'
                    },
                    'content': {
                        templateUrl: 'orders/waiter/orders.html',
                        controller: 'OrdersController'
                    }
                }
            })
            .state('newOrder', {
                url: '/orders/new',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'notifications': {
                        templateUrl: 'notifications/notifications.html',
                        controller: 'NotificationsController'
                    },
                    'content': {
                        templateUrl: 'orders/waiter/newOrder/newOrder.html',
                        controller: 'NewOrderController'
                    }
                }
            })
            .state('editOrder', {
                url: '/orders/:id',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'notifications': {
                        templateUrl: 'notifications/notifications.html',
                        controller: 'NotificationsController'
                    },
                    'content': {
                        templateUrl: 'orders/waiter/editOrder/editOrder.html',
                        controller: 'EditOrderController'
                    }
                }
            })
            .state('chefOrders', {
                url: '/chefOrders',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'notifications': {
                        templateUrl: 'notifications/notifications.html',
                        controller: 'NotificationsController'
                    },
                    'content': {
                        templateUrl: 'orders/chef/chefOrders.html',
                        controller: 'ChefOrdersController'
                    }
                }
            })
            .state('bartenderOrders', {
                url: '/bartenderOrders',
                views: {
                    'navbar': {
                        templateUrl: 'navbar/navbar.html',
                        controller: 'NavbarController'
                    },
                    'notifications': {
                        templateUrl: 'notifications/notifications.html',
                        controller: 'NotificationsController'
                    },
                    'content': {
                        templateUrl: 'orders/bartender/bartenderOrders.html',
                        controller: 'BartenderOrdersController'
                    }
                }
            })
    });