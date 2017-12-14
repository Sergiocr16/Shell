'use strict';

describe('Controller Tests', function() {

    describe('Proyecto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockProyecto, MockLanzamiento, MockRiesgo, MockRangoRiesgo;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockProyecto = jasmine.createSpy('MockProyecto');
            MockLanzamiento = jasmine.createSpy('MockLanzamiento');
            MockRiesgo = jasmine.createSpy('MockRiesgo');
            MockRangoRiesgo = jasmine.createSpy('MockRangoRiesgo');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Proyecto': MockProyecto,
                'Lanzamiento': MockLanzamiento,
                'Riesgo': MockRiesgo,
                'RangoRiesgo': MockRangoRiesgo
            };
            createController = function() {
                $injector.get('$controller')("ProyectoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'shellApp:proyectoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
