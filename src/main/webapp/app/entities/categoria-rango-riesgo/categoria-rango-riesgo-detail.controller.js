(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('CategoriaRangoRiesgoDetailController', CategoriaRangoRiesgoDetailController);

    CategoriaRangoRiesgoDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'CategoriaRangoRiesgo'];

    function CategoriaRangoRiesgoDetailController($scope, $rootScope, $stateParams, previousState, entity, CategoriaRangoRiesgo) {
        var vm = this;

        vm.categoriaRangoRiesgo = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('shellApp:categoriaRangoRiesgoUpdate', function(event, result) {
            vm.categoriaRangoRiesgo = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
