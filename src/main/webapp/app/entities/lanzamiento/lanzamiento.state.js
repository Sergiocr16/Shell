(function() {
    'use strict';

    angular
        .module('shellApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('lanzamiento', {
            parent: 'entity',
            url: '/lanzamiento?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.lanzamiento.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lanzamiento/lanzamientos.html',
                    controller: 'LanzamientoController',
                    controllerAs: 'vm'
                }
            },
            params: {
                page: {
                    value: '1',
                    squash: true
                },
                sort: {
                    value: 'id,asc',
                    squash: true
                },
                search: null
            },
            resolve: {
                pagingParams: ['$stateParams', 'PaginationUtil', function ($stateParams, PaginationUtil) {
                    return {
                        page: PaginationUtil.parsePage($stateParams.page),
                        sort: $stateParams.sort,
                        predicate: PaginationUtil.parsePredicate($stateParams.sort),
                        ascending: PaginationUtil.parseAscending($stateParams.sort),
                        search: $stateParams.search
                    };
                }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lanzamiento');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('lanzamiento-detail', {
            parent: 'lanzamiento',
            url: '/lanzamiento/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.lanzamiento.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/lanzamiento/lanzamiento-detail.html',
                    controller: 'LanzamientoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('lanzamiento');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Lanzamiento', function($stateParams, Lanzamiento) {
                    return Lanzamiento.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'lanzamiento',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('lanzamiento-detail.edit', {
            parent: 'lanzamiento-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lanzamiento/lanzamiento-dialog.html',
                    controller: 'LanzamientoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Lanzamiento', function(Lanzamiento) {
                            return Lanzamiento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lanzamiento.new', {
            parent: 'lanzamiento',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lanzamiento/lanzamiento-dialog.html',
                    controller: 'LanzamientoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                numero: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('lanzamiento', null, { reload: 'lanzamiento' });
                }, function() {
                    $state.go('lanzamiento');
                });
            }]
        })
        .state('lanzamiento.edit', {
            parent: 'lanzamiento',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lanzamiento/lanzamiento-dialog.html',
                    controller: 'LanzamientoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Lanzamiento', function(Lanzamiento) {
                            return Lanzamiento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lanzamiento', null, { reload: 'lanzamiento' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('lanzamiento.delete', {
            parent: 'lanzamiento',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/lanzamiento/lanzamiento-delete-dialog.html',
                    controller: 'LanzamientoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Lanzamiento', function(Lanzamiento) {
                            return Lanzamiento.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('lanzamiento', null, { reload: 'lanzamiento' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
