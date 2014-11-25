'use strict';

module.exports = function(app) {
	var users = require('../../app/controllers/users.server.controller');
	var housetosells = require('../../app/controllers/housetosells.server.controller');

	// Housetosells Routes
	app.route('/housetosells')
        .get(housetosells.list)
		.post(users.requiresLogin, housetosells.create);

	app.route('/housetosells/:housetosellId')
		.get(housetosells.read)
		.put(users.requiresLogin, housetosells.hasAuthorization, housetosells.update)
		.delete(users.requiresLogin, housetosells.hasAuthorization, housetosells.delete);

	// Finish by binding the Housetosell middleware
	app.param('housetosellId', housetosells.housetosellByID);
};
