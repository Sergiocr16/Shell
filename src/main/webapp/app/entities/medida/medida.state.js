(function() {
    'use strict';

    angular
        .module('shellApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('medida', {
            parent: 'entity',
            url: '/medida?page&sort&search',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.medida.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medida/medidas.html',
                    controller: 'MedidaController',
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
                    $translatePartialLoader.addPart('medida');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('medida-detail', {
            parent: 'medida',
            url: '/medida/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.medida.detail.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/medida/medida-detail.html',
                    controller: 'MedidaDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('medida');
                    return $translate.refresh();
                }],
                entity: ['$stateParams', 'Medida', function($stateParams, Medida) {
                    return Medida.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'medida',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('medida-detail.edit', {
            parent: 'medida-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medida/medida-dialog.html',
                    controller: 'MedidaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medida', function(Medida) {
                            return Medida.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medida.new', {
            parent: 'medida',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medida/medida-dialog.html',
                    controller: 'MedidaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                contencion: null,
                                mitigacion: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('medida', null, { reload: 'medida' });
                }, function() {
                    $state.go('medida');
                });
            }]
        })
        .state('medida.edit', {
            parent: 'medida',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medida/medida-dialog.html',
                    controller: 'MedidaDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['Medida', function(Medida) {
                            return Medida.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medida', null, { reload: 'medida' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('medida.delete', {
            parent: 'medida',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/medida/medida-delete-dialog.html',
                    controller: 'MedidaDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['Medida', function(Medida) {
                            return Medida.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('medida', null, { reload: 'medida' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
