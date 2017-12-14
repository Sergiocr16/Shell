(function() {
    'use strict';
    angular
        .module('shellApp')
        .factory('Probabilidad', Probabilidad);

    Probabilidad.$inject = ['$resource'];

    function Probabilidad ($resource) {
        var resourceUrl =  'api/probabilidads/:id';

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
