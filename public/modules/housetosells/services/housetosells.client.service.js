'use strict';

//Housetosells service used to communicate Housetosells REST endpoints
angular.module('housetosells').factory('Housetosells', ['$resource',
	function($resource) {
		return $resource('housetosells/:housetosellId', { housetosellId: '@_id'
		}, {
			update: {
				method: 'PUT'
			}
		});
	}
]);