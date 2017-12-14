(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('RangoRiesgoDeleteController',RangoRiesgoDeleteController);

    RangoRiesgoDeleteController.$inject = ['$uibModalInstance', 'entity', 'RangoRiesgo'];

    function RangoRiesgoDeleteController($uibModalInstance, entity, RangoRiesgo) {
        var vm = this;

        vm.rangoRiesgo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            RangoRiesgo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
