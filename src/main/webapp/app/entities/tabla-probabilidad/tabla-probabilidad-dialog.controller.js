(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('TablaProbabilidadDialogController', TablaProbabilidadDialogController);

    TablaProbabilidadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TablaProbabilidad', 'Probabilidad'];

    function TablaProbabilidadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TablaProbabilidad, Probabilidad) {
        var vm = this;

        vm.tablaProbabilidad = entity;
        vm.clear = clear;
        vm.save = save;
        vm.probabilidads = Probabilidad.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tablaProbabilidad.id !== null) {
                TablaProbabilidad.update(vm.tablaProbabilidad, onSaveSuccess, onSaveError);
            } else {
                TablaProbabilidad.save(vm.tablaProbabilidad, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shellApp:tablaProbabilidadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
