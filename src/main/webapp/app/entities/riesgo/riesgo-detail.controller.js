(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('RiesgoDetailController', RiesgoDetailController);

    RiesgoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Riesgo', 'Proyecto', 'Medida'];

    function RiesgoDetailController($scope, $rootScope, $stateParams, previousState, entity, Riesgo, Proyecto, Medida) {
        var vm = this;

        vm.riesgo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shellApp:riesgoUpdate', function(event, result) {
            vm.riesgo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
