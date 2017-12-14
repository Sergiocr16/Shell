(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('LanzamientoDeleteController',LanzamientoDeleteController);

    LanzamientoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Lanzamiento'];

    function LanzamientoDeleteController($uibModalInstance, entity, Lanzamiento) {
        var vm = this;

        vm.lanzamiento = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Lanzamiento.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
