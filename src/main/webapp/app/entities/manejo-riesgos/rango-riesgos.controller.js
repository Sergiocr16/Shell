(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('RangoRiesgosController', RangoRiesgosController);

    RangoRiesgosController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Probabilidad', 'RangoRiesgo','Impacto'];

    function RangoRiesgosController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Probabilidad, RangoRiesgo, Impacto) {
        var vm = this;

        vm.proyecto = entity;
        vm.clear = clear;
        vm.save = save;
        vm.loadAll = function() {
            RangoRiesgo.query({

                proyectoId : vm.proyecto.id
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
                vm.hayRangos = vm.totalItems;
                vm.getrangoRiesgos = data;
                vm.rangoRiesgos = [];
                 angular.forEach(vm.getrangoRiesgos,function(rangoRiesgo,i){

                  rangoRiesgo.valor = rangoRiesgo.impacto*rangoRiesgo.probabilidad;
                  vm.rangoRiesgos.push(rangoRiesgo)

                 })
                 console.log(vm.rangoRiesgos)
            }
            function onError(error) {

//                AlertService.error(error.data.message);
            }
        }


     vm.getImpacto = function(){
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
                            vm.getProbabilidad();
                        }
                        function onError(error) {
                            AlertService.error(error.data.message);
                            vm.getProbabilidad();
                        }
            }
     vm.getImpacto();
          vm.getProbabilidad = function(){

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
                            vm.probabilidades = vm.probabilidades.reverse();
                             vm.loadAll();
                             if(vm.hayRangos==0){
                             vm.crearRangoRiesgos();
                             }
                        }
                        function onError(error) {
                            AlertService.error(error.data.message);
                        }
            }
        vm.save = function (rangoRiesgo) {
            vm.isSaving = true;
            if (rangoRiesgo.id !== null) {
                RangoRiesgo.update(rangoRiesgo, onSaveSuccess, onSaveError);
            } else {
                RangoRiesgo.save(rangoRiesgo, onSaveSuccess, onSaveError);
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
        vm.crearRangoRiesgos = function(){
          angular.forEach(vm.impactos,function(impacto,i){
           angular.forEach(vm.probabilidades,function(probabilidad,i){
            var rangoRiesgo = {};
            rangoRiesgo.probabilidad = probabilidad.valor;
            rangoRiesgo.impacto = impacto.valor;
            rangoRiesgo.proyectoId = vm.proyecto.id;
            rangoRiesgo.impactoDescription = impacto.categoria;
            rangoRiesgo.probabilidadDescription = probabilidad.categoria;
            vm.save(rangoRiesgo);
           })
          })
           loadAll();
        }

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
