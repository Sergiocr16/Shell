'use strict';

describe('Controller Tests', function() {

    describe('Lanzamiento Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockLanzamiento, MockProyecto, MockSprint;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockLanzamiento = jasmine.createSpy('MockLanzamiento');
            MockProyecto = jasmine.createSpy('MockProyecto');
            MockSprint = jasmine.createSpy('MockSprint');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Lanzamiento': MockLanzamiento,
                'Proyecto': MockProyecto,
                'Sprint': MockSprint
            };
            createController = function() {
                $injector.get('$controller')("LanzamientoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'shellApp:lanzamientoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
