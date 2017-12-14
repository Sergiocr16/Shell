(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('ImpactoDeleteController',ImpactoDeleteController);

    ImpactoDeleteController.$inject = ['$uibModalInstance', 'entity', 'Impacto'];

    function ImpactoDeleteController($uibModalInstance, entity, Impacto) {
        var vm = this;

        vm.impacto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Impacto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
