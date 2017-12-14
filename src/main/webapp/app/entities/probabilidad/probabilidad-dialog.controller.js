(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('ProbabilidadDialogController', ProbabilidadDialogController);

    ProbabilidadDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Probabilidad', 'TablaProbabilidad'];

    function ProbabilidadDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Probabilidad, TablaProbabilidad) {
        var vm = this;

        vm.probabilidad = entity;
        vm.clear = clear;
        vm.save = save;
        vm.tablaprobabilidads = TablaProbabilidad.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.probabilidad.id !== null) {
                Probabilidad.update(vm.probabilidad, onSaveSuccess, onSaveError);
            } else {
                Probabilidad.save(vm.probabilidad, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shellApp:probabilidadUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
