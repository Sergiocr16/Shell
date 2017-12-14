(function() {
    'use strict';
    angular
        .module('shellApp')
        .factory('Sprint', Sprint);

    Sprint.$inject = ['$resource'];

    function Sprint ($resource) {
        var resourceUrl =  'api/sprints/:id';

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
