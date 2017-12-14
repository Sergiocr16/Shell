(function() {
    'use strict';
    angular
        .module('shellApp')
        .factory('Lanzamiento', Lanzamiento);

    Lanzamiento.$inject = ['$resource'];

    function Lanzamiento ($resource) {
        var resourceUrl =  'api/lanzamientos/:id';

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
