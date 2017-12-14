'use strict';

describe('Controller Tests', function() {

    describe('Impacto Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockImpacto, MockTablaImpacto;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockImpacto = jasmine.createSpy('MockImpacto');
            MockTablaImpacto = jasmine.createSpy('MockTablaImpacto');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'Impacto': MockImpacto,
                'TablaImpacto': MockTablaImpacto
            };
            createController = function() {
                $injector.get('$controller')("ImpactoDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'shellApp:impactoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
