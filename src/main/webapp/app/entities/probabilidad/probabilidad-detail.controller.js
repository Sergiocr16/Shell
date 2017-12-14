(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('ProbabilidadDetailController', ProbabilidadDetailController);

    ProbabilidadDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Probabilidad', 'TablaProbabilidad'];

    function ProbabilidadDetailController($scope, $rootScope, $stateParams, previousState, entity, Probabilidad, TablaProbabilidad) {
        var vm = this;

        vm.probabilidad = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shellApp:probabilidadUpdate', function(event, result) {
            vm.probabilidad = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
