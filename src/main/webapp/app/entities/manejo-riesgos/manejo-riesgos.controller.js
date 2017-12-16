(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('ManejoRiesgoController', ManejoRiesgoController);

    ManejoRiesgoController.$inject = ['$state', 'Riesgo', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','entity','Probabilidad','Impacto','$uibModal','RangoRiesgo'];

    function ManejoRiesgoController($state, Riesgo, ParseLinks, AlertService, paginationConstants, pagingParams,entity,Probabilidad,Impacto,$uibModal,RangoRiesgo) {
        var vm = this;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.proyecto = entity;
        vm.agregarRiesgo = false;
        vm.mostrarTablas = false;
        vm.editando=false;
        loadAll();
       vm.agregarRiesgo = function(valor){
        vm.mostrarAgregarRiesgo = valor;
        if(valor==false){
        vm.editando=false;
        }
       }
       vm.ocultar = function(){
       vm.mostrarTablas = !vm.mostrarTablas;
       }
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
                vm.rangoRiesgos = data;

            }
            function onError(error) {

               AlertService.error(error.data.message);
            }
        }
       vm.cargarRangoRiesgos();

      vm.isCalcular = function(){
       var rangoCompleted = 0;
       if(vm.rangoRiesgos!=undefined){
       if(vm.rangoRiesgos.length==0){
       return true;
       }}else{
       return true;
       }
       angular.forEach(vm.rangoRiesgos,function(a,i){
       if(a.nombre == null){
       rangoCompleted = rangoCompleted+1;
       }
       })
      if(rangoCompleted>0){
      return true;
      }
      }






      vm.editar =function(riesgo){
      Riesgo.get({id :riesgo.id},function(result){
      console.log(result)
      vm.editando=true;
      result.impacto = parseInt(result.impacto)
      vm.riesgo = result;
      })
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


      vm.eliminar =  function(riesgo){
                     $uibModal.open({
                          templateUrl: 'app/entities/riesgo/riesgo-delete-dialog.html',
                          controller: 'RiesgoDeleteController',
                          controllerAs: 'vm',
                          size: 'md',
                          resolve: {
                              entity: ['Riesgo', function(Riesgo) {
                                  return Riesgo.get({id : riesgo.id}).$promise;
                              }]
                          }
                      }).result.then(function() {
                      loadAll();
                          $state.go('^', null, { reload: '^' });
                      }, function() {
                       loadAll();
                          $state.go('^');
                      });
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
        vm.saveRiesgo = function () {
           vm.isSaving = true;
           vm.riesgo.proyectoId = vm.proyecto.id;
           if (vm.riesgo.id !== null) {
               Riesgo.update(vm.riesgo, onSaveSuccess, onSaveError);
           } else {
               Riesgo.save(vm.riesgo, onSaveSuccess, onSaveError);
           }
           function onSaveSuccess (result) {
                loadAll();
               vm.isSaving = false;
               vm.riesgo={}
               vm.mostrarAgregarRiesgo = false;
                vm.editando=false;
           }

           function onSaveError () {
            loadAll();
             vm.riesgo={};
              vm.mostrarAgregarRiesgo = false;
               vm.isSaving = false;
           }
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
                            vm.calcularValores();

                        }
                        function onError(error) {
                            AlertService.error(error.data.message);
                        }
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
                vm.riesgos = data;
                vm.page = pagingParams.page;
                  vm.showTablaImpacto();
                  vm.showTablaProbabilidad();
            }
            function onError(error) {
                AlertService.error(error.data.message);
            }
        }


      vm.calcularValores = function(){

      angular.forEach(vm.impactos,function(impacto,index){
        angular.forEach(vm.probabilidades,function(probabilidad,index){
            probabilidad.valoreSumados = impacto.valor* probabilidad.valor;
         })
      })
      }
        function loadPage(page) {
            vm.page = page;
            vm.transition();
        }

        function transition() {
            $state.transitionTo($state.$current, {
                page: vm.page,
                sort: vm.predicate + ',' + (vm.reverse ? 'asc' : 'desc'),
                search: vm.currentSearch
            });
        }


    }
})();
