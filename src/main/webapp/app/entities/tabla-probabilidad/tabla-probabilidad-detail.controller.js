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
        $scope.$on('$destroy', unsubscribe);
    }
})();
