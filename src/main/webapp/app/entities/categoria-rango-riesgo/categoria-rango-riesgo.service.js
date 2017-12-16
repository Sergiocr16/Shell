(function() {
    'use strict';
    angular
        .module('shellApp')
        .factory('CategoriaRangoRiesgo', CategoriaRangoRiesgo);

    CategoriaRangoRiesgo.$inject = ['$resource'];

    function CategoriaRangoRiesgo ($resource) {
        var resourceUrl =  'api/categoria-rango-riesgos/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' }
        });
    }
})();
