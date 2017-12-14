(function() {
    'use strict';
    angular
        .module('shellApp')
        .factory('TablaImpacto', TablaImpacto);

    TablaImpacto.$inject = ['$resource'];

    function TablaImpacto ($resource) {
        var resourceUrl =  'api/tabla-impactos/:id';

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
