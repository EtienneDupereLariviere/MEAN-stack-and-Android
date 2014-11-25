'use strict';

(function() {
	// Housetosells Controller Spec
	describe('Housetosells Controller Tests', function() {
		// Initialize global variables
		var HousetosellsController,
		scope,
		$httpBackend,
		$stateParams,
		$location;

		// The $resource service augments the response object with methods for updating and deleting the resource.
		// If we were to use the standard toEqual matcher, our tests would fail because the test values would not match
		// the responses exactly. To solve the problem, we define a new toEqualData Jasmine matcher.
		// When the toEqualData matcher compares two objects, it takes only object properties into
		// account and ignores methods.
		beforeEach(function() {
			jasmine.addMatchers({
				toEqualData: function(util, customEqualityTesters) {
					return {
						compare: function(actual, expected) {
							return {
								pass: angular.equals(actual, expected)
							};
						}
					};
				}
			});
		});

		// Then we can start by loading the main application module
		beforeEach(module(ApplicationConfiguration.applicationModuleName));

		// The injector ignores leading and trailing underscores here (i.e. _$httpBackend_).
		// This allows us to inject a service but then attach it to a variable
		// with the same name as the service.
		beforeEach(inject(function($controller, $rootScope, _$location_, _$stateParams_, _$httpBackend_) {
			// Set a new global scope
			scope = $rootScope.$new();

			// Point global variables to injected services
			$stateParams = _$stateParams_;
			$httpBackend = _$httpBackend_;
			$location = _$location_;

			// Initialize the Housetosells controller.
			HousetosellsController = $controller('HousetosellsController', {
				$scope: scope
			});
		}));

		it('$scope.find() should create an array with at least one Housetosell object fetched from XHR', inject(function(Housetosells) {
			// Create sample Housetosell using the Housetosells service
			var sampleHousetosell = new Housetosells({
				name: 'New Housetosell'
			});

			// Create a sample Housetosells array that includes the new Housetosell
			var sampleHousetosells = [sampleHousetosell];

			// Set GET response
			$httpBackend.expectGET('housetosells').respond(sampleHousetosells);

			// Run controller functionality
			scope.find();
			$httpBackend.flush();

			// Test scope value
			expect(scope.housetosells).toEqualData(sampleHousetosells);
		}));

		it('$scope.findOne() should create an array with one Housetosell object fetched from XHR using a housetosellId URL parameter', inject(function(Housetosells) {
			// Define a sample Housetosell object
			var sampleHousetosell = new Housetosells({
				name: 'New Housetosell'
			});

			// Set the URL parameter
			$stateParams.housetosellId = '525a8422f6d0f87f0e407a33';

			// Set GET response
			$httpBackend.expectGET(/housetosells\/([0-9a-fA-F]{24})$/).respond(sampleHousetosell);

			// Run controller functionality
			scope.findOne();
			$httpBackend.flush();

			// Test scope value
			expect(scope.housetosell).toEqualData(sampleHousetosell);
		}));

		it('$scope.create() with valid form data should send a POST request with the form input values and then locate to new object URL', inject(function(Housetosells) {
			// Create a sample Housetosell object
			var sampleHousetosellPostData = new Housetosells({
				name: 'New Housetosell'
			});

			// Create a sample Housetosell response
			var sampleHousetosellResponse = new Housetosells({
				_id: '525cf20451979dea2c000001',
				name: 'New Housetosell'
			});

			// Fixture mock form input values
			scope.name = 'New Housetosell';

			// Set POST response
			$httpBackend.expectPOST('housetosells', sampleHousetosellPostData).respond(sampleHousetosellResponse);

			// Run controller functionality
			scope.create();
			$httpBackend.flush();

			// Test form inputs are reset
			expect(scope.name).toEqual('');

			// Test URL redirection after the Housetosell was created
			expect($location.path()).toBe('/housetosells/' + sampleHousetosellResponse._id);
		}));

		it('$scope.update() should update a valid Housetosell', inject(function(Housetosells) {
			// Define a sample Housetosell put data
			var sampleHousetosellPutData = new Housetosells({
				_id: '525cf20451979dea2c000001',
				name: 'New Housetosell'
			});

			// Mock Housetosell in scope
			scope.housetosell = sampleHousetosellPutData;

			// Set PUT response
			$httpBackend.expectPUT(/housetosells\/([0-9a-fA-F]{24})$/).respond();

			// Run controller functionality
			scope.update();
			$httpBackend.flush();

			// Test URL location to new object
			expect($location.path()).toBe('/housetosells/' + sampleHousetosellPutData._id);
		}));

		it('$scope.remove() should send a DELETE request with a valid housetosellId and remove the Housetosell from the scope', inject(function(Housetosells) {
			// Create new Housetosell object
			var sampleHousetosell = new Housetosells({
				_id: '525a8422f6d0f87f0e407a33'
			});

			// Create new Housetosells array and include the Housetosell
			scope.housetosells = [sampleHousetosell];

			// Set expected DELETE response
			$httpBackend.expectDELETE(/housetosells\/([0-9a-fA-F]{24})$/).respond(204);

			// Run controller functionality
			scope.remove(sampleHousetosell);
			$httpBackend.flush();

			// Test array after successful delete
			expect(scope.housetosells.length).toBe(0);
		}));
	});
}());