(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('LanzamientoDialogController', LanzamientoDialogController);

    LanzamientoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Lanzamiento', 'Proyecto', 'Sprint'];

    function LanzamientoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Lanzamiento, Proyecto, Sprint) {
        var vm = this;

        vm.lanzamiento = entity;
        vm.clear = clear;
        vm.save = save;
        vm.proyectos = Proyecto.query();
        vm.sprints = Sprint.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.lanzamiento.id !== null) {
                Lanzamiento.update(vm.lanzamiento, onSaveSuccess, onSaveError);
            } else {
                Lanzamiento.save(vm.lanzamiento, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shellApp:lanzamientoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
