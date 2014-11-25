'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	errorHandler = require('./errors.server.controller'),
	Housetosell = mongoose.model('Housetosell'),
	_ = require('lodash');

/**
 * Create a Housetosell
 */
exports.create = function(req, res) {
	var housetosell = new Housetosell(req.body);
	housetosell.user = req.user;

	housetosell.save(function(err) {
		if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		} else {
			res.jsonp(housetosell);
		}
	});
};

/**
 * Show the current Housetosell
 */
exports.read = function(req, res) {
	res.jsonp(req.housetosell);
};

/**
 * Update a Housetosell
 */
exports.update = function(req, res) {
	var housetosell = req.housetosell ;

	housetosell = _.extend(housetosell , req.body);

	housetosell.save(function(err) {
		if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		} else {
			res.jsonp(housetosell);
		}
	});
};

/**
 * Delete an Housetosell
 */
exports.delete = function(req, res) {
	var housetosell = req.housetosell ;

	housetosell.remove(function(err) {
		if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		} else {
			res.jsonp(housetosell);
		}
	});
};

/**
 * List of Housetosells
 */
exports.list = function(req, res) {
    var minPrice = req.query.minPrice;
    var maxPrice = req.query.maxPrice;
    var category = req.query.category;
    var city = req.query.city;

    if(minPrice < 0 && maxPrice < 0 && minPrice >= maxPrice)
        return res.status(400).send({ message: "Invalid criteria 'minPrice maxPrice'" });

    var query = Housetosell.find({ price: { $gt: minPrice, $lt: maxPrice } });

    if(category)
        query = query.where('category').equals(category);

    if(city)
        query = query.where('address').regex(".*" + city + ".*");

    query.sort('-created').populate('user', 'displayName').exec(function(err, housetosells) {
		if (err) {
			return res.status(400).send({
				message: errorHandler.getErrorMessage(err)
			});
		} else {
			res.jsonp(housetosells);
		}
	});
};

/**
 * Housetosell middleware
 */
exports.housetosellByID = function(req, res, next, id) { 
	Housetosell.findById(id).populate('user', 'displayName').exec(function(err, housetosell) {
		if (err) return next(err);
		if (! housetosell) return next(new Error('Failed to load Housetosell ' + id));
		req.housetosell = housetosell ;
		next();
	});
};

/**
 * Housetosell authorization middleware
 */
exports.hasAuthorization = function(req, res, next) {
	if (req.housetosell.user.id !== req.user.id) {
		return res.status(403).send('User is not authorized');
	}
	next();
};
