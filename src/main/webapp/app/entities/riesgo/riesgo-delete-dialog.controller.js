(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('RiesgoDeleteController',RiesgoDeleteController);

    RiesgoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Riesgo'];

    function RiesgoDeleteController($uibModalInstance, entity, Riesgo) {
        var vm = this;

        vm.riesgo = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Riesgo.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
