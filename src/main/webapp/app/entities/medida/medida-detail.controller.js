(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('MedidaDetailController', MedidaDetailController);

    MedidaDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Medida'];

    function MedidaDetailController($scope, $rootScope, $stateParams, previousState, entity, Medida) {
        var vm = this;

        vm.medida = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shellApp:medidaUpdate', function(event, result) {
            vm.medida = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
