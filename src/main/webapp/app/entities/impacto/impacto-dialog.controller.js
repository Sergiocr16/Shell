(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('ImpactoDialogController', ImpactoDialogController);

    ImpactoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Impacto', 'TablaImpacto'];

    function ImpactoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Impacto, TablaImpacto) {
        var vm = this;

        vm.impacto = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tablaimpactos = TablaImpacto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.impacto.id !== null) {
                Impacto.update(vm.impacto, onSaveSuccess, onSaveError);
            } else {
                Impacto.save(vm.impacto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shellApp:impactoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
