(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('TablaImpactoDeleteController',TablaImpactoDeleteController);

    TablaImpactoDeleteController.$inject = ['$uibModalInstance', 'entity', 'TablaImpacto'];

    function TablaImpactoDeleteController($uibModalInstance, entity, TablaImpacto) {
        var vm = this;

        vm.tablaImpacto = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            TablaImpacto.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
