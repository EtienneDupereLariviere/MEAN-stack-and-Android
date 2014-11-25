'use strict';

//Setting up route
angular.module('housetosells').config(['$stateProvider',
	function($stateProvider) {
		// Housetosells state routing
		$stateProvider.
		state('listHousetosells', {
			url: '/housetosells',
			templateUrl: 'modules/housetosells/views/list-housetosells.client.view.html'
		}).
		state('createHousetosell', {
			url: '/housetosells/create',
			templateUrl: 'modules/housetosells/views/create-housetosell.client.view.html'
		}).
		state('viewHousetosell', {
			url: '/housetosells/:housetosellId',
			templateUrl: 'modules/housetosells/views/view-housetosell.client.view.html'
		}).
		state('editHousetosell', {
			url: '/housetosells/:housetosellId/edit',
			templateUrl: 'modules/housetosells/views/edit-housetosell.client.view.html'
		});
	}
]);