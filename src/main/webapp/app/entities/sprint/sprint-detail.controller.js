(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('SprintDetailController', SprintDetailController);

    SprintDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Sprint', 'Lanzamiento'];

    function SprintDetailController($scope, $rootScope, $stateParams, previousState, entity, Sprint, Lanzamiento) {
        var vm = this;

        vm.sprint = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shellApp:sprintUpdate', function(event, result) {
            vm.sprint = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
