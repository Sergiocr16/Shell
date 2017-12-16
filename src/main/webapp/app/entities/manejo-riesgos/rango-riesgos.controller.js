(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('RangoRiesgosController', RangoRiesgosController);

    RangoRiesgosController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'Probabilidad', 'RangoRiesgo','Impacto','CategoriaRangoRiesgo'];

    function RangoRiesgosController ($timeout, $scope, $stateParams, $uibModalInstance, entity, Probabilidad, RangoRiesgo, Impacto,CategoriaRangoRiesgo) {
        var vm = this;

        vm.proyecto = entity;
        vm.clear = clear;
        vm.rangoRiesgos = [];
        vm.save = save;
//         vm.riesgos = [{id:1,nombre:'Bajo',color:'#28a745'},{id:2,nombre:'Medio',color:'#ffc107'},{id:3,nombre:'Alto',color:'#dc3545'}]
      vm.cambiarColor = function(rangoRiesgo,riesgo){
       angular.forEach(vm.riesgos,function(riesgo,i){
       console.log(riesgo.nombre)
       console.log("EL NOMBRE" + rangoRiesgo.nombre)
        if(riesgo.nombre == rangoRiesgo.nombre){
        rangoRiesgo.color = riesgo.color;
        }
       })

      }
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
             CategoriaRangoRiesgo.query(function(result) {
                            vm.riesgos = result;
                            vm.searchQuery = null;
                        });
                vm.totalItems = headers('X-Total-Count');
                vm.hayRangos = vm.totalItems;
                vm.getrangoRiesgos = data;

                 angular.forEach(vm.getrangoRiesgos,function(rangoRiesgo,i){

                  rangoRiesgo.valor = rangoRiesgo.impacto*rangoRiesgo.probabilidad;
                  vm.rangoRiesgos.push(rangoRiesgo)

                 })
                  if(vm.rangoRiesgos.length==0){
                                                vm.crearRangoRiesgos();
                                              }

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

                        }
                        function onError(error) {
                            AlertService.error(error.data.message);
                        }
            }

         function save(){
         angular.forEach(vm.rangoRiesgos,function(rangoRiesgo,i){

         vm.saveRangoRiesgo(rangoRiesgo);
         })

         }
        vm.saveRangoRiesgo = function (rangoRiesgo) {
            vm.isSaving = true;
            if (rangoRiesgo.id !== null) {
                RangoRiesgo.update(rangoRiesgo, onSaveSuccess, onSaveError);
            } else {
                RangoRiesgo.save(rangoRiesgo, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {


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
            vm.saveRangoRiesgo(rangoRiesgo);
           })
          })
           vm.loadAll();
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

//        function save () {
//            vm.isSaving = true;
//            if (vm.probabilidad.id !== null) {
//                Probabilidad.update(vm.probabilidad, onSaveSuccess, onSaveError);
//            } else {
//                Probabilidad.save(vm.probabilidad, onSaveSuccess, onSaveError);
//            }
//        }

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
