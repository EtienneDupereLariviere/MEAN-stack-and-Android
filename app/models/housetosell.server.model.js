'use strict';

/**
 * Module dependencies.
 */
var mongoose = require('mongoose'),
	Schema = mongoose.Schema;

/**
 * Housetosell Schema
 */
var HousetosellSchema = new Schema({
    latitude : {
        type: Number,
        default: 0,
        required: 'Please fill the latitude'
    },
    longitude : {
        type: Number,
        default: 0,
        required: 'Please fill the longitude'
    },
    category : {
        type: Schema.ObjectId,
        ref: 'Categories'
	},
    address : {
        type: String,
        unique: 'This address already have a house to sell',
        required: 'Please fill an address',
        trim: true
    },
	description: {
		type: String,
		default: '',
        trim: true
	},
    characteristics: {
        type: String,
        default: '',
        trim: true
    },
    nbRoom: {
        type: Number,
        default: 0
    },
    price: {
        type: Number,
        default: 0,
        required: 'Please fill the price'
    },
    image: {
        type: String,
        default: ''
    },
	user: {
		type: Schema.ObjectId,
		ref: 'User'
	}
});

mongoose.model('Housetosell', HousetosellSchema);