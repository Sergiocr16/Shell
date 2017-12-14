(function() {
    'use strict';

    angular
        .module('shellApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('impacto', {
            parent: 'entity',
            url: '/impacto?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.impacto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/impacto/impactos.html',
                    controller: 'ImpactoController',
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
                    $translatePartialLoader.addPart('impacto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('impacto-detail', {
            parent: 'impacto',
            url: '/impacto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.impacto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/impacto/impacto-detail.html',
                    controller: 'ImpactoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('impacto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Impacto', function($stateParams, Impacto) {
                    return Impacto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'impacto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('impacto-detail.edit', {
            parent: 'impacto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/impacto/impacto-dialog.html',
                    controller: 'ImpactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Impacto', function(Impacto) {
                            return Impacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('impacto.new', {
            parent: 'impacto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/impacto/impacto-dialog.html',
                    controller: 'ImpactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                categoria: null,
                                valor: null,
                                descripcion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('impacto', null, { reload: 'impacto' });
                }, function() {
                    $state.go('impacto');
                });
            }]
        })
        .state('impacto.edit', {
            parent: 'impacto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/impacto/impacto-dialog.html',
                    controller: 'ImpactoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Impacto', function(Impacto) {
                            return Impacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('impacto', null, { reload: 'impacto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('impacto.delete', {
            parent: 'impacto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/impacto/impacto-delete-dialog.html',
                    controller: 'ImpactoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Impacto', function(Impacto) {
                            return Impacto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('impacto', null, { reload: 'impacto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
