(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('LanzamientoDetailController', LanzamientoDetailController);

    LanzamientoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Lanzamiento', 'Proyecto', 'Sprint'];

    function LanzamientoDetailController($scope, $rootScope, $stateParams, previousState, entity, Lanzamiento, Proyecto, Sprint) {
        var vm = this;

        vm.lanzamiento = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shellApp:lanzamientoUpdate', function(event, result) {
            vm.lanzamiento = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
