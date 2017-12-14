(function() {
    'use strict';
    angular
        .module('shellApp')
        .factory('RangoRiesgo', RangoRiesgo);

    RangoRiesgo.$inject = ['$resource'];

    function RangoRiesgo ($resource) {
        var resourceUrl =  'api/rango-riesgos/:id';

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
