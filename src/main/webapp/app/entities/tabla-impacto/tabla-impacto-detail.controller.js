(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('TablaImpactoDetailController', TablaImpactoDetailController);

    TablaImpactoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'TablaImpacto', 'Impacto'];

    function TablaImpactoDetailController($scope, $rootScope, $stateParams, previousState, entity, TablaImpacto, Impacto) {
        var vm = this;

        vm.tablaImpacto = entity;
        vm.previousState = previousState.name;

     vm.showTablaImpacto = function(){
               Impacto.query({
                            tablaImpactoId:  vm.tablaImpacto.id,
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
                            vm.impactos = data;

                        }
                        function onError(error) {
                            AlertService.error(error.data.message);
                        }
            }
             vm.showTablaImpacto ();
        var unsubscribe = $rootScope.$on('shellApp:tablaImpactoUpdate', function(event, result) {
            vm.tablaImpacto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
