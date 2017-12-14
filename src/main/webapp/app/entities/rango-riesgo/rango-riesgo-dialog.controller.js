(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('RangoRiesgoDialogController', RangoRiesgoDialogController);

    RangoRiesgoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'RangoRiesgo', 'Proyecto'];

    function RangoRiesgoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, RangoRiesgo, Proyecto) {
        var vm = this;

        vm.rangoRiesgo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.proyectos = Proyecto.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.rangoRiesgo.id !== null) {
                RangoRiesgo.update(vm.rangoRiesgo, onSaveSuccess, onSaveError);
            } else {
                RangoRiesgo.save(vm.rangoRiesgo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shellApp:rangoRiesgoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
