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
        vm.probabilidades = [];
       function getProbabilidad(){
        return {id:null,categoria:null,descripcion:null,rangoMayor:null,rangoMenor:null,tablaProbabilidadId:entity.id}
        }

        vm.probabilidades.push(getProbabilidad())
        vm.agregarProbabilidad = function(){
         vm.probabilidades.push(getProbabilidad())
        }
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
            angular.forEach(vm.probabilidades,function(proba,i){

            proba.tablaProbabilidadId = result.id
            proba.categoria = proba.categoria.toUpperCase();
                   Probabilidad.save(proba,function(){

                   });
               })

            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
