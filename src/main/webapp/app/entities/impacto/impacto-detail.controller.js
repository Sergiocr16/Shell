(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('ImpactoDetailController', ImpactoDetailController);

    ImpactoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Impacto', 'TablaImpacto'];

    function ImpactoDetailController($scope, $rootScope, $stateParams, previousState, entity, Impacto, TablaImpacto) {
        var vm = this;

        vm.impacto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shellApp:impactoUpdate', function(event, result) {
            vm.impacto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
