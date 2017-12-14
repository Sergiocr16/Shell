(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('RangoRiesgoDetailController', RangoRiesgoDetailController);

    RangoRiesgoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'RangoRiesgo', 'Proyecto'];

    function RangoRiesgoDetailController($scope, $rootScope, $stateParams, previousState, entity, RangoRiesgo, Proyecto) {
        var vm = this;

        vm.rangoRiesgo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shellApp:rangoRiesgoUpdate', function(event, result) {
            vm.rangoRiesgo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
