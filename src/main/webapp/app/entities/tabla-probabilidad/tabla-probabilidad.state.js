(function() {
    'use strict';

    angular
        .module('shellApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tabla-probabilidad', {
            parent: 'entity',
            url: '/tabla-probabilidad?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.tablaProbabilidad.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tabla-probabilidad/tabla-probabilidads.html',
                    controller: 'TablaProbabilidadController',
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
                    $translatePartialLoader.addPart('tablaProbabilidad');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tabla-probabilidad-detail', {
            parent: 'tabla-probabilidad',
            url: '/tabla-probabilidad/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.tablaProbabilidad.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tabla-probabilidad/tabla-probabilidad-detail.html',
                    controller: 'TablaProbabilidadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tablaProbabilidad');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TablaProbabilidad', function($stateParams, TablaProbabilidad) {
                    return TablaProbabilidad.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tabla-probabilidad',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tabla-probabilidad-detail.edit', {
            parent: 'tabla-probabilidad-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tabla-probabilidad/tabla-probabilidad-dialog.html',
                    controller: 'TablaProbabilidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TablaProbabilidad', function(TablaProbabilidad) {
                            return TablaProbabilidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tabla-probabilidad.new', {
            parent: 'tabla-probabilidad',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tabla-probabilidad/tabla-probabilidad-dialog.html',
                    controller: 'TablaProbabilidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('tabla-probabilidad', null, { reload: 'tabla-probabilidad' });
                }, function() {
                    $state.go('tabla-probabilidad');
                });
            }]
        })
        .state('tabla-probabilidad.edit', {
            parent: 'tabla-probabilidad',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tabla-probabilidad/tabla-probabilidad-dialog.html',
                    controller: 'TablaProbabilidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TablaProbabilidad', function(TablaProbabilidad) {
                            return TablaProbabilidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tabla-probabilidad', null, { reload: 'tabla-probabilidad' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tabla-probabilidad.delete', {
            parent: 'tabla-probabilidad',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tabla-probabilidad/tabla-probabilidad-delete-dialog.html',
                    controller: 'TablaProbabilidadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TablaProbabilidad', function(TablaProbabilidad) {
                            return TablaProbabilidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tabla-probabilidad', null, { reload: 'tabla-probabilidad' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
