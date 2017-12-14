(function() {
    'use strict';
    angular
        .module('shellApp')
        .factory('Impacto', Impacto);

    Impacto.$inject = ['$resource'];

    function Impacto ($resource) {
        var resourceUrl =  'api/impactos/:id';

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
