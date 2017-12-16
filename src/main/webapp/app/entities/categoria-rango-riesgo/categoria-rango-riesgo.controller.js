(function() {
    'use strict';

    angular
        .module('shellApp')
        .controller('CategoriaRangoRiesgoController', CategoriaRangoRiesgoController);

    CategoriaRangoRiesgoController.$inject = ['CategoriaRangoRiesgo'];

    function CategoriaRangoRiesgoController(CategoriaRangoRiesgo) {

        var vm = this;

        vm.categoriaRangoRiesgos = [];

        loadAll();

        function loadAll() {
            CategoriaRangoRiesgo.query(function(result) {
                vm.categoriaRangoRiesgos = result;
                vm.searchQuery = null;
            });
        }
    }
})();
