(function() {
    'use strict';

    angular
        .module('shellApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('probabilidad', {
            parent: 'entity',
            url: '/probabilidad?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.probabilidad.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/probabilidad/probabilidads.html',
                    controller: 'ProbabilidadController',
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
                    $translatePartialLoader.addPart('probabilidad');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('probabilidad-detail', {
            parent: 'probabilidad',
            url: '/probabilidad/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.probabilidad.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/probabilidad/probabilidad-detail.html',
                    controller: 'ProbabilidadDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('probabilidad');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Probabilidad', function($stateParams, Probabilidad) {
                    return Probabilidad.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'probabilidad',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('probabilidad-detail.edit', {
            parent: 'probabilidad-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/probabilidad/probabilidad-dialog.html',
                    controller: 'ProbabilidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Probabilidad', function(Probabilidad) {
                            return Probabilidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('probabilidad.new', {
            parent: 'probabilidad',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/probabilidad/probabilidad-dialog.html',
                    controller: 'ProbabilidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                categoria: null,
                                valor: null,
                                descripcion: null,
                                rangoMayor: null,
                                rangoMenor: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('probabilidad', null, { reload: 'probabilidad' });
                }, function() {
                    $state.go('probabilidad');
                });
            }]
        })
        .state('probabilidad.edit', {
            parent: 'probabilidad',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/probabilidad/probabilidad-dialog.html',
                    controller: 'ProbabilidadDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Probabilidad', function(Probabilidad) {
                            return Probabilidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('probabilidad', null, { reload: 'probabilidad' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('probabilidad.delete', {
            parent: 'probabilidad',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/probabilidad/probabilidad-delete-dialog.html',
                    controller: 'ProbabilidadDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Probabilidad', function(Probabilidad) {
                            return Probabilidad.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('probabilidad', null, { reload: 'probabilidad' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
