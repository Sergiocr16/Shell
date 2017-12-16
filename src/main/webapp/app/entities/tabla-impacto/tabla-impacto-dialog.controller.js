(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('TablaImpactoDialogController', TablaImpactoDialogController);

    TablaImpactoDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'TablaImpacto', 'Impacto'];

    function TablaImpactoDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, TablaImpacto, Impacto) {
        var vm = this;

        vm.tablaImpacto = entity;
        vm.clear = clear;
        vm.save = save;
        vm.impactos = [];
       function getImpacto(){
        return {id:null,categoria:null,descripcion:null,valor:null,tablaImpactoId:entity.id}
        }

        vm.impactos.push(getImpacto())
        vm.agregarImpacto = function(){
         vm.impactos.push(getImpacto())
        }
        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.tablaImpacto.id !== null) {
                TablaImpacto.update(vm.tablaImpacto, onSaveSuccess, onSaveError);
            } else {
                TablaImpacto.save(vm.tablaImpacto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shellApp:tablaImpactoUpdate', result);
            $uibModalInstance.close(result);
            angular.forEach(vm.impactos,function(impacto,i){

            impacto.tablaImpactoId = result.id
            impacto.categoria = impacto.categoria.toUpperCase();
                   Impacto.save(impacto,function(){

                   });
               })
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
