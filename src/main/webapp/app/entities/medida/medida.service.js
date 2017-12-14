(function() {
    'use strict';
    angular
        .module('shellApp')
        .factory('Medida', Medida);

    Medida.$inject = ['$resource'];

    function Medida ($resource) {
        var resourceUrl =  'api/medidas/:id';

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
