(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('ManejoRiesgoController', ManejoRiesgoController);

    ManejoRiesgoController.$inject = ['$state', 'Riesgo', 'ParseLinks', 'AlertService', 'paginationConstants', 'pagingParams','entity','Probabilidad','Impacto'];

    function ManejoRiesgoController($state, Riesgo, ParseLinks, AlertService, paginationConstants, pagingParams,entity,Probabilidad,Impacto) {
        var vm = this;
        vm.loadPage = loadPage;
        vm.predicate = pagingParams.predicate;
        vm.reverse = pagingParams.ascending;
        vm.transition = transition;
        vm.itemsPerPage = paginationConstants.itemsPerPage;
        vm.proyecto = entity;
        vm.mostrarTablas = false;
        loadAll();

       vm.ocultar = function(){
       vm.mostrarTablas = !vm.mostrarTablas;
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
                page: pagingParams.page - 1,
                size: vm.itemsPerPage,
                sort: sort()
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
