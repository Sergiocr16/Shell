(function() {
    'use strict';

    angular
        .module('shellApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('categoria-rango-riesgo', {
            parent: 'entity',
            url: '/categoria-rango-riesgo',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.categoriaRangoRiesgo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/categoria-rango-riesgo/categoria-rango-riesgos.html',
                    controller: 'CategoriaRangoRiesgoController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('categoriaRangoRiesgo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('categoria-rango-riesgo-detail', {
            parent: 'categoria-rango-riesgo',
            url: '/categoria-rango-riesgo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.categoriaRangoRiesgo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/categoria-rango-riesgo/categoria-rango-riesgo-detail.html',
                    controller: 'CategoriaRangoRiesgoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('categoriaRangoRiesgo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'CategoriaRangoRiesgo', function($stateParams, CategoriaRangoRiesgo) {
                    return CategoriaRangoRiesgo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'categoria-rango-riesgo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('categoria-rango-riesgo-detail.edit', {
            parent: 'categoria-rango-riesgo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/categoria-rango-riesgo/categoria-rango-riesgo-dialog.html',
                    controller: 'CategoriaRangoRiesgoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CategoriaRangoRiesgo', function(CategoriaRangoRiesgo) {
                            return CategoriaRangoRiesgo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('categoria-rango-riesgo.new', {
            parent: 'categoria-rango-riesgo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/categoria-rango-riesgo/categoria-rango-riesgo-dialog.html',
                    controller: 'CategoriaRangoRiesgoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                color: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('categoria-rango-riesgo', null, { reload: 'categoria-rango-riesgo' });
                }, function() {
                    $state.go('categoria-rango-riesgo');
                });
            }]
        })
        .state('categoria-rango-riesgo.edit', {
            parent: 'categoria-rango-riesgo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/categoria-rango-riesgo/categoria-rango-riesgo-dialog.html',
                    controller: 'CategoriaRangoRiesgoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['CategoriaRangoRiesgo', function(CategoriaRangoRiesgo) {
                            return CategoriaRangoRiesgo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('categoria-rango-riesgo', null, { reload: 'categoria-rango-riesgo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('categoria-rango-riesgo.delete', {
            parent: 'categoria-rango-riesgo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/categoria-rango-riesgo/categoria-rango-riesgo-delete-dialog.html',
                    controller: 'CategoriaRangoRiesgoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['CategoriaRangoRiesgo', function(CategoriaRangoRiesgo) {
                            return CategoriaRangoRiesgo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('categoria-rango-riesgo', null, { reload: 'categoria-rango-riesgo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
