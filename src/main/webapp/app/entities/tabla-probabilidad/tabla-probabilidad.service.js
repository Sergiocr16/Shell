(function() {
    'use strict';
    angular
        .module('shellApp')
        .factory('TablaProbabilidad', TablaProbabilidad);

    TablaProbabilidad.$inject = ['$resource'];

    function TablaProbabilidad ($resource) {
        var resourceUrl =  'api/tabla-probabilidads/:id';

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
