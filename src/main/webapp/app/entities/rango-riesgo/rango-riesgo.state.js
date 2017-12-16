(function() {
    'use strict';

    angular
        .module('shellApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('rango-riesgo', {
            parent: 'entity',
            url: '/rango-riesgo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.rangoRiesgo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rango-riesgo/rango-riesgos.html',
                    controller: 'RangoRiesgoController',
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
                    $translatePartialLoader.addPart('rangoRiesgo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('rango-riesgo-detail', {
            parent: 'rango-riesgo',
            url: '/rango-riesgo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.rangoRiesgo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/rango-riesgo/rango-riesgo-detail.html',
                    controller: 'RangoRiesgoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('rangoRiesgo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'RangoRiesgo', function($stateParams, RangoRiesgo) {
                    return RangoRiesgo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'rango-riesgo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('rango-riesgo-detail.edit', {
            parent: 'rango-riesgo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rango-riesgo/rango-riesgo-dialog.html',
                    controller: 'RangoRiesgoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RangoRiesgo', function(RangoRiesgo) {
                            return RangoRiesgo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rango-riesgo.new', {
            parent: 'rango-riesgo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rango-riesgo/rango-riesgo-dialog.html',
                    controller: 'RangoRiesgoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                color: null,
                                probabilidad: null,
                                impacto: null,
                                impactoDescription: null,
                                probabilidadDescription: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('rango-riesgo', null, { reload: 'rango-riesgo' });
                }, function() {
                    $state.go('rango-riesgo');
                });
            }]
        })
        .state('rango-riesgo.edit', {
            parent: 'rango-riesgo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rango-riesgo/rango-riesgo-dialog.html',
                    controller: 'RangoRiesgoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['RangoRiesgo', function(RangoRiesgo) {
                            return RangoRiesgo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rango-riesgo', null, { reload: 'rango-riesgo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('rango-riesgo.delete', {
            parent: 'rango-riesgo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/rango-riesgo/rango-riesgo-delete-dialog.html',
                    controller: 'RangoRiesgoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['RangoRiesgo', function(RangoRiesgo) {
                            return RangoRiesgo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('rango-riesgo', null, { reload: 'rango-riesgo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
