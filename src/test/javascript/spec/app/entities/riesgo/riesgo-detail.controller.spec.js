'use strict';

describe('Controller Tests', function() {

    describe('Riesgo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockRiesgo, MockProyecto, MockMedida;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockRiesgo = jasmine.createSpy('MockRiesgo');
            MockProyecto = jasmine.createSpy('MockProyecto');
            MockMedida = jasmine.createSpy('MockMedida');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Riesgo': MockRiesgo,
                'Proyecto': MockProyecto,
                'Medida': MockMedida
            };
            createController = function() {
                $injector.get('$controller')("RiesgoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'shellApp:riesgoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
