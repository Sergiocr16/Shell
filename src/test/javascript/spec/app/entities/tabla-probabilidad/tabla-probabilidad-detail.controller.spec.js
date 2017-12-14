'use strict';

describe('Controller Tests', function() {

    describe('TablaProbabilidad Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockTablaProbabilidad, MockProbabilidad;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockTablaProbabilidad = jasmine.createSpy('MockTablaProbabilidad');
            MockProbabilidad = jasmine.createSpy('MockProbabilidad');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'TablaProbabilidad': MockTablaProbabilidad,
                'Probabilidad': MockProbabilidad
            };
            createController = function() {
                $injector.get('$controller')("TablaProbabilidadDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'shellApp:tablaProbabilidadUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
