(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('ProyectoDialogController', ProyectoDialogController);

    ProyectoDialogController.$inject = ['$timeout', '$scope', '$stateParams','$state', 'Proyecto', 'Lanzamiento', 'Riesgo', 'RangoRiesgo','TablaImpacto','Impacto','TablaProbabilidad','Probabilidad'];

    function ProyectoDialogController ($timeout, $scope, $stateParams,$state, Proyecto, Lanzamiento, Riesgo, RangoRiesgo,TablaImpacto,Impacto,TablaProbabilidad,Probabilidad) {
        var vm = this;
        vm.clear = clear;
        vm.save = save;
        vm.lanzamientos = Lanzamiento.query();
        vm.riesgos = Riesgo.query();
        vm.rangoriesgos = RangoRiesgo.query();
        vm.tablaimpactos = TablaImpacto.query();
        vm.tablaprobabilidades = TablaProbabilidad.query();
        console.log(vm.tablaprobabilidades)
        vm.proyecto = {};

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $state.go('home')
            $uibModalInstance.dismiss('cancel');

        }

        vm.showTablaImpacto = function(){
           Impacto.query({
                        tablaImpactoId: vm.proyecto.tablaImpactoId,
                    }, onSuccess, onError);
                    function sort() {
                        var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                        if (vm.predicate !== 'id') {
                            result.push('id');
                        }
                        return result;
                    }
                    function onSuccess(data, headers) {

                        vm.totalItems = headers('X-Total-Count');
                        vm.queryCount = vm.totalItems;
                        vm.impactos = data;
                        console.log(vm.impactos)
                    }
                    function onError(error) {
                        AlertService.error(error.data.message);
                    }
        }

      vm.showTablaProbabilidad = function(){

           Probabilidad.query({
                        tablaProbabilidadId: vm.proyecto.tablaProbabilidadId,
                    }, onSuccess, onError);
                    function sort() {
                        var result = [vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc')];
                        if (vm.predicate !== 'id') {
                            result.push('id');
                        }
                        return result;
                    }
                    function onSuccess(data, headers) {

                        vm.totalItems = headers('X-Total-Count');
                        vm.queryCount = vm.totalItems;
                        vm.probabilidades = data;
                        console.log(vm.probabilidades)
                    }
                    function onError(error) {
                        AlertService.error(error.data.message);
                    }
        }

        function save () {
            vm.isSaving = true;
            if (vm.proyecto.id !== null) {
                Proyecto.update(vm.proyecto, onSaveSuccess, onSaveError);
            } else {
                Proyecto.save(vm.proyecto, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('shellApp:proyectoUpdate', result);
           $state.go("proyecto-detail({id:"+result.id+"})")
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
