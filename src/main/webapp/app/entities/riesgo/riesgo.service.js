(function() {
    'use strict';
    angular
        .module('shellApp')
        .factory('Riesgo', Riesgo);

    Riesgo.$inject = ['$resource'];

    function Riesgo ($resource) {
        var resourceUrl =  'api/riesgos/:id';

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
