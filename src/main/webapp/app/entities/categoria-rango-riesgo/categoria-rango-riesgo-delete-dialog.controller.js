(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('CategoriaRangoRiesgoDeleteController',CategoriaRangoRiesgoDeleteController);

    CategoriaRangoRiesgoDeleteController.$inject = ['$uibModalInstance', 'entity', 'CategoriaRangoRiesgo'];

    function CategoriaRangoRiesgoDeleteController($uibModalInstance, entity, CategoriaRangoRiesgo) {
        var vm = this;

        vm.categoriaRangoRiesgo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            CategoriaRangoRiesgo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
