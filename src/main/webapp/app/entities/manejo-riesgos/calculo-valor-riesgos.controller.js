(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('CalculoValorController', CalculoValorController);

    CalculoValorController.$inject = ['$state', 'Riesgo', 'ParseLinks', 'AlertService','entity','Probabilidad','Impacto','$uibModalInstance','RangoRiesgo'];

    function CalculoValorController($state, Riesgo, ParseLinks, AlertService,entity,Probabilidad,Impacto,$uibModalInstance,RangoRiesgo) {
        var vm = this;

        vm.proyecto = entity;

        vm.clear = clear;
        function clear () {
            $uibModalInstance.dismiss('cancel');
        }
        loadAll();
        vm.cargarRangoRiesgos = function() {
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
                vm.getrangoRiesgos = data;
                vm.rangoRiesgos = [];
                 angular.forEach(vm.getrangoRiesgos,function(rangoRiesgo,i){

                  rangoRiesgo.valor = rangoRiesgo.impacto*rangoRiesgo.probabilidad;
                  vm.rangoRiesgos.push(rangoRiesgo)
                 })

                 angular.forEach(vm.riesgos,function(riesgo,i){
                     angular.forEach(vm.rangoRiesgos,function(rango,i){
                      var nivel = {};
                      var valorRiesgo = riesgo.probabilidad* riesgo.impacto;
                      var valorRango = rango.probabilidad* rango.impacto;
                      if(valorRiesgo==valorRango)
                      riesgo.nivel = {nombre:rango.nombre,color:rango.color}


                      })
                 })
                  console.log(vm.riesgos)
            }
            function onError(error) {

//                AlertService.error(error.data.message);
            }
        }


      vm.verImpacto = function(valor){
      var result;
      angular.forEach(vm.impactos,function(impacto,i){
      if(impacto.valor==valor){
      result = impacto.categoria;
      }
      })
      return result;
      }

       vm.verProbabilidad = function(valor){
            var result;
            angular.forEach(vm.probabilidades,function(probabilidad,i){
            if(probabilidad.valor==valor){
            result = probabilidad.categoria;
            }
            })
            return result;
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
                             vm.showTablaProbabilidad();
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
                            vm.probabilidades = vm.probabilidades.reverse();
                            vm.cargarRangoRiesgos();

                        }
                        function onError(error) {
                            AlertService.error(error.data.message);
                        }
            }

        vm.calcularNivel = function(riesgo){

        }
        function loadAll () {
            Riesgo.query({
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
                vm.links = ParseLinks.parse(headers('link'));
                vm.totalItems = headers('X-Total-Count');
                vm.queryCount = vm.totalItems;
                vm.getriesgos = data;
                vm.riesgos = [];
                 angular.forEach(vm.getriesgos,function(riesgo,i){

                  riesgo.valor = riesgo.impacto*riesgo.probabilidad;
                  vm.riesgos.push(riesgo)
                 })
                vm.showTablaImpacto();

            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }


    }
})();
