'use strict';

module.exports = function(app) {
    var categories = require('../../app/controllers/categories.server.controller');

    // Categories Routes
    app.route('/categories')
        .get(categories.list)
        .post(categories.create);

    app.route('/categories/:categoryId')
        .get(categories.read);

    // Finish by binding the Categories middleware
    app.param('categoryId', categories.categoryByID);
};