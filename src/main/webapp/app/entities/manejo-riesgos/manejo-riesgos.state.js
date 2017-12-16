(function() {
    'use strict';

    angular
        .module('shellApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('manejo-riesgos', {
            parent: 'entity',
            url: '/manejo-riesgos/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'shellApp.proyecto.home.title'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/manejo-riesgos/manejo-riesgos.html',
                    controller: 'ManejoRiesgoController',
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
                 entity: ['$stateParams', 'Proyecto', function($stateParams, Proyecto) {
                                    return Proyecto.get({id : $stateParams.id}).$promise;
                 }],
                translatePartialLoader: ['$translate', '$translatePartialLoader', function ($translate, $translatePartialLoader) {
                    $translatePartialLoader.addPart('proyecto');
                    $translatePartialLoader.addPart('global');
                    return $translate.refresh();
                }]
            }
        })
        .state('manejo-riesgos.rango-riesgos', {
            parent: 'manejo-riesgos',
            url: '/rango-riesgos',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/manejo-riesgos/rango-riesgos.html',
                    controller: 'RangoRiesgosController',
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
    }

})();
