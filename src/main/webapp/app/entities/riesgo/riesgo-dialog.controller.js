(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('RiesgoDialogController', RiesgoDialogController);

    RiesgoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'Riesgo', 'Proyecto', 'Medida'];

    function RiesgoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, Riesgo, Proyecto, Medida) {
        var vm = this;

        vm.riesgo = entity;
        vm.clear = clear;
        vm.save = save;
        vm.proyectos = Proyecto.query();
        vm.medidas = Medida.query({filter: 'riesgo-is-null'});
        $q.all([vm.riesgo.$promise, vm.medidas.$promise]).then(function() {
            if (!vm.riesgo.medidaId) {
                return $q.reject();
            }
            return Medida.get({id : vm.riesgo.medidaId}).$promise;
        }).then(function(medida) {
            vm.medidas.push(medida);
        });

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.riesgo.id !== null) {
                Riesgo.update(vm.riesgo, onSaveSuccess, onSaveError);
            } else {
                Riesgo.save(vm.riesgo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shellApp:riesgoUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
