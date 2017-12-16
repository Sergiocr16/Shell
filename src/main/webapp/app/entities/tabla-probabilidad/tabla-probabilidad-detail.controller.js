(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('TablaProbabilidadDetailController', TablaProbabilidadDetailController);

    TablaProbabilidadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TablaProbabilidad', 'Probabilidad'];

    function TablaProbabilidadDetailController($scope, $rootScope, $stateParams, previousState, entity, TablaProbabilidad, Probabilidad) {
        var vm = this;

        vm.tablaProbabilidad = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shellApp:tablaProbabilidadUpdate', function(event, result) {
            vm.tablaProbabilidad = result;
        });
                  vm.showTablaProbabilidad = function(){

                       Probabilidad.query({
                                    tablaProbabilidadId: vm.tablaProbabilidad.id,
                                }, onSuccess, onError);
                                function sort() {
                                    var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                                    if (vm.predicate !== 'id') {
                                        result.push('id');
                                    }
                                    return result;
                                }
                                function onSuccess(data, headers) {

                                    vm.totalItems = headers('X-Total-Count');
                                    vm.queryCount = vm.totalItems;
                                    vm.probabilidades = data;
                                    vm.probabilidades = vm.probabilidades.reverse();


                                }
                                function onError(error) {
                                    AlertService.error(error.data.message);
                                }
                    }
                      vm.showTablaProbabilidad();
        $scope.$on('$destroy', unsubscribe);
    }
})();
