(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('ProbabilidadDeleteController',ProbabilidadDeleteController);

    ProbabilidadDeleteController.$inject = ['$uibModalInstance', 'entity', 'Probabilidad'];

    function ProbabilidadDeleteController($uibModalInstance, entity, Probabilidad) {
        var vm = this;

        vm.probabilidad = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Probabilidad.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
