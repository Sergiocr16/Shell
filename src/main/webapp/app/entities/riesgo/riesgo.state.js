(function() {
    'use strict';

    angular
        .module('shellApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('riesgo', {
            parent: 'entity',
            url: '/riesgo?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.riesgo.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/riesgo/riesgos.html',
                    controller: 'RiesgoController',
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
                    $translatePartialLoader.addPart('riesgo');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('riesgo-detail', {
            parent: 'riesgo',
            url: '/riesgo/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.riesgo.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/riesgo/riesgo-detail.html',
                    controller: 'RiesgoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('riesgo');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Riesgo', function($stateParams, Riesgo) {
                    return Riesgo.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'riesgo',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('riesgo-detail.edit', {
            parent: 'riesgo-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/riesgo/riesgo-dialog.html',
                    controller: 'RiesgoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Riesgo', function(Riesgo) {
                            return Riesgo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('riesgo.new', {
            parent: 'riesgo',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/riesgo/riesgo-dialog.html',
                    controller: 'RiesgoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                description: null,
                                probabilidad: null,
                                impacto: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('riesgo', null, { reload: 'riesgo' });
                }, function() {
                    $state.go('riesgo');
                });
            }]
        })
        .state('riesgo.edit', {
            parent: 'riesgo',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/riesgo/riesgo-dialog.html',
                    controller: 'RiesgoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Riesgo', function(Riesgo) {
                            return Riesgo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('riesgo', null, { reload: 'riesgo' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('riesgo.delete', {
            parent: 'riesgo',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/riesgo/riesgo-delete-dialog.html',
                    controller: 'RiesgoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Riesgo', function(Riesgo) {
                            return Riesgo.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('riesgo', null, { reload: 'riesgo' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
