(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('TablaProbabilidadDeleteController',TablaProbabilidadDeleteController);

    TablaProbabilidadDeleteController.$inject = ['$uibModalInstance', 'entity', 'TablaProbabilidad'];

    function TablaProbabilidadDeleteController($uibModalInstance, entity, TablaProbabilidad) {
        var vm = this;

        vm.tablaProbabilidad = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TablaProbabilidad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
