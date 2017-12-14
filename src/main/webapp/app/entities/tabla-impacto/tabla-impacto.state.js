(function() {
    'use strict';

    angular
        .module('shellApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('tabla-impacto', {
            parent: 'entity',
            url: '/tabla-impacto?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.tablaImpacto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tabla-impacto/tabla-impactos.html',
                    controller: 'TablaImpactoController',
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
                    $translatePartialLoader.addPart('tablaImpacto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('tabla-impacto-detail', {
            parent: 'tabla-impacto',
            url: '/tabla-impacto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.tablaImpacto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/tabla-impacto/tabla-impacto-detail.html',
                    controller: 'TablaImpactoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('tablaImpacto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'TablaImpacto', function($stateParams, TablaImpacto) {
                    return TablaImpacto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'tabla-impacto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('tabla-impacto-detail.edit', {
            parent: 'tabla-impacto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tabla-impacto/tabla-impacto-dialog.html',
                    controller: 'TablaImpactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TablaImpacto', function(TablaImpacto) {
                            return TablaImpacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tabla-impacto.new', {
            parent: 'tabla-impacto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tabla-impacto/tabla-impacto-dialog.html',
                    controller: 'TablaImpactoDialogController',
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
                    $state.go('tabla-impacto', null, { reload: 'tabla-impacto' });
                }, function() {
                    $state.go('tabla-impacto');
                });
            }]
        })
        .state('tabla-impacto.edit', {
            parent: 'tabla-impacto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tabla-impacto/tabla-impacto-dialog.html',
                    controller: 'TablaImpactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['TablaImpacto', function(TablaImpacto) {
                            return TablaImpacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tabla-impacto', null, { reload: 'tabla-impacto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('tabla-impacto.delete', {
            parent: 'tabla-impacto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/tabla-impacto/tabla-impacto-delete-dialog.html',
                    controller: 'TablaImpactoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['TablaImpacto', function(TablaImpacto) {
                            return TablaImpacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('tabla-impacto', null, { reload: 'tabla-impacto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
