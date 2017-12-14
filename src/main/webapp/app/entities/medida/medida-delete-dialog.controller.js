(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('MedidaDeleteController',MedidaDeleteController);

    MedidaDeleteController.$inject = ['$uibModalInstance', 'entity', 'Medida'];

    function MedidaDeleteController($uibModalInstance, entity, Medida) {
        var vm = this;

        vm.medida = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Medida.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
