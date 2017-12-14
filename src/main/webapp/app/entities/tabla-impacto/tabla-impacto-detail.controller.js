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

        var unsubscribe = $rootScope.$on('shellApp:tablaImpactoUpdate', function(event, result) {
            vm.tablaImpacto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
