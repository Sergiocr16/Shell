(function() {
    'use strict';

    angular
        .module('shellApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('proyecto', {
            parent: 'entity',
            url: '/proyecto?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.proyecto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proyecto/proyectos.html',
                    controller: 'ProyectoController',
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
                    $translatePartialLoader.addPart('proyecto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('proyecto-detail', {
            parent: 'proyecto',
            url: '/proyecto/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.proyecto.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/proyecto/proyecto-detail.html',
                    controller: 'ProyectoDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('proyecto');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Proyecto', function($stateParams, Proyecto) {
                    return Proyecto.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'proyecto',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('proyecto-detail.edit', {
            parent: 'proyecto-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proyecto/proyecto-dialog.html',
                    controller: 'ProyectoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Proyecto', function(Proyecto) {
                            return Proyecto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('proyecto.new', {
            parent: 'proyecto',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proyecto/proyecto-dialog.html',
                    controller: 'ProyectoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                nombre: null,
                                tablaImpactoId: null,
                                tablaProbabilidadId: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('proyecto', null, { reload: 'proyecto' });
                }, function() {
                    $state.go('proyecto');
                });
            }]
        })
        .state('proyecto.edit', {
            parent: 'proyecto',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proyecto/proyecto-dialog.html',
                    controller: 'ProyectoDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Proyecto', function(Proyecto) {
                            return Proyecto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proyecto', null, { reload: 'proyecto' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('proyecto.delete', {
            parent: 'proyecto',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/proyecto/proyecto-delete-dialog.html',
                    controller: 'ProyectoDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Proyecto', function(Proyecto) {
                            return Proyecto.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('proyecto', null, { reload: 'proyecto' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
