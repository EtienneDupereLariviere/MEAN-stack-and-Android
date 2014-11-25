'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
    errorHandler = require('./errors.server.controller'),
    Categories = mongoose.model('Categories'),
    _ = require('lodash');

/**
 * Create a Category
 */
exports.create = function(req, res) {
    var category = new Categories(req.body);

    category.save(function(err) {
        if (err) {
            return res.status(400).send({
                message: errorHandler.getErrorMessage(err)
            });
        } else {
            res.jsonp(category);
        }
    });
};

/**
 * Show the current Category
 */
exports.read = function(req, res) {
    res.jsonp(req.categories);
};

/**
 * List of Categories
 */
exports.list = function(req, res) {
    Categories.find().exec(function(err, categories) {
            if (err) {
                return res.status(400).send({
                    message: errorHandler.getErrorMessage(err)
                });
            } else {
                res.jsonp(categories);
            }
        });
};

/**
 * Categories middleware
 */
exports.categoryByID = function(req, res, next, id) {
    Categories.findById(id).exec(function(err, categories) {
        if (err) return next(err);
        if (! categories) return next(new Error('Failed to load Category ' + id));
        req.categories = categories ;
        next();
    });
};