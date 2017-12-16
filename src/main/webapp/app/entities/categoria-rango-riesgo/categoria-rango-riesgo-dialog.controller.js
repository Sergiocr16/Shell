(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('CategoriaRangoRiesgoDialogController', CategoriaRangoRiesgoDialogController);

    CategoriaRangoRiesgoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'CategoriaRangoRiesgo'];

    function CategoriaRangoRiesgoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, CategoriaRangoRiesgo) {
        var vm = this;

        vm.categoriaRangoRiesgo = entity;
        vm.clear = clear;
        vm.save = save;
vm.customOptions = {
    size: 30,
    roundCorners: true,
    required: true
}
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.categoriaRangoRiesgo.id !== null) {
                CategoriaRangoRiesgo.update(vm.categoriaRangoRiesgo, onSaveSuccess, onSaveError);
            } else {
                CategoriaRangoRiesgo.save(vm.categoriaRangoRiesgo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shellApp:categoriaRangoRiesgoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
