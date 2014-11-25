'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Schema = mongoose.Schema;

/**
 * Categories Schema
 */
var CategoriesSchema = new Schema({
    categoryName : {
        type: String,
        unique: 'This category name exist',
        required: 'Please fill the categoryName',
        trim: true
    },
    categoryDescription : {
        type: String,
        required: 'Please fill the categoryDescription',
        trim: true
    }
});

mongoose.model('Categories', CategoriesSchema);