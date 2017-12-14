(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('MedidaDialogController', MedidaDialogController);

    MedidaDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Medida'];

    function MedidaDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Medida) {
        var vm = this;

        vm.medida = entity;
        vm.clear = clear;
        vm.save = save;

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.medida.id !== null) {
                Medida.update(vm.medida, onSaveSuccess, onSaveError);
            } else {
                Medida.save(vm.medida, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shellApp:medidaUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
