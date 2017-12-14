(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('ProyectoDetailController', ProyectoDetailController);

    ProyectoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Proyecto', 'Lanzamiento', 'Riesgo', 'RangoRiesgo'];

    function ProyectoDetailController($scope, $rootScope, $stateParams, previousState, entity, Proyecto, Lanzamiento, Riesgo, RangoRiesgo) {
        var vm = this;

        vm.proyecto = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shellApp:proyectoUpdate', function(event, result) {
            vm.proyecto = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
