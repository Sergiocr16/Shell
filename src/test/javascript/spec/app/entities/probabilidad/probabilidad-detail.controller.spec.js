'use strict';

describe('Controller Tests', function() {

    describe('Probabilidad Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProbabilidad, MockTablaProbabilidad;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProbabilidad = jasmine.createSpy('MockProbabilidad');
            MockTablaProbabilidad = jasmine.createSpy('MockTablaProbabilidad');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Probabilidad': MockProbabilidad,
                'TablaProbabilidad': MockTablaProbabilidad
            };
            createController = function() {
                $injector.get('$controller')("ProbabilidadDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'shellApp:probabilidadUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
