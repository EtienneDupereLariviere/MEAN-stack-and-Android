'use strict';

var should = require('should'),
	request = require('supertest'),
	app = require('../../server'),
	mongoose = require('mongoose'),
	User = mongoose.model('User'),
	Housetosell = mongoose.model('Housetosell'),
	agent = request.agent(app);

/**
 * Globals
 */
var credentials, user, housetosell;

/**
 * Housetosell routes tests
 */
describe('Housetosell CRUD tests', function() {
	beforeEach(function(done) {
		// Create user credentials
		credentials = {
			username: 'username',
			password: 'password'
		};

		// Create a new user
		user = new User({
			firstName: 'Full',
			lastName: 'Name',
			displayName: 'Full Name',
			email: 'test@test.com',
			username: credentials.username,
			password: credentials.password,
			provider: 'local'
		});

		// Save a user to the test db and create new Housetosell
		user.save(function() {
			housetosell = {
				name: 'Housetosell Name'
			};

			done();
		});
	});

	it('should be able to save Housetosell instance if logged in', function(done) {
		agent.post('/auth/signin')
			.send(credentials)
			.expect(200)
			.end(function(signinErr, signinRes) {
				// Handle signin error
				if (signinErr) done(signinErr);

				// Get the userId
				var userId = user.id;

				// Save a new Housetosell
				agent.post('/housetosells')
					.send(housetosell)
					.expect(200)
					.end(function(housetosellSaveErr, housetosellSaveRes) {
						// Handle Housetosell save error
						if (housetosellSaveErr) done(housetosellSaveErr);

						// Get a list of Housetosells
						agent.get('/housetosells')
							.end(function(housetosellsGetErr, housetosellsGetRes) {
								// Handle Housetosell save error
								if (housetosellsGetErr) done(housetosellsGetErr);

								// Get Housetosells list
								var housetosells = housetosellsGetRes.body;

								// Set assertions
								(housetosells[0].user._id).should.equal(userId);
								(housetosells[0].name).should.match('Housetosell Name');

								// Call the assertion callback
								done();
							});
					});
			});
	});

	it('should not be able to save Housetosell instance if not logged in', function(done) {
		agent.post('/housetosells')
			.send(housetosell)
			.expect(401)
			.end(function(housetosellSaveErr, housetosellSaveRes) {
				// Call the assertion callback
				done(housetosellSaveErr);
			});
	});

	it('should not be able to save Housetosell instance if no name is provided', function(done) {
		// Invalidate name field
		housetosell.name = '';

		agent.post('/auth/signin')
			.send(credentials)
			.expect(200)
			.end(function(signinErr, signinRes) {
				// Handle signin error
				if (signinErr) done(signinErr);

				// Get the userId
				var userId = user.id;

				// Save a new Housetosell
				agent.post('/housetosells')
					.send(housetosell)
					.expect(400)
					.end(function(housetosellSaveErr, housetosellSaveRes) {
						// Set message assertion
						(housetosellSaveRes.body.message).should.match('Please fill Housetosell name');
						
						// Handle Housetosell save error
						done(housetosellSaveErr);
					});
			});
	});

	it('should be able to update Housetosell instance if signed in', function(done) {
		agent.post('/auth/signin')
			.send(credentials)
			.expect(200)
			.end(function(signinErr, signinRes) {
				// Handle signin error
				if (signinErr) done(signinErr);

				// Get the userId
				var userId = user.id;

				// Save a new Housetosell
				agent.post('/housetosells')
					.send(housetosell)
					.expect(200)
					.end(function(housetosellSaveErr, housetosellSaveRes) {
						// Handle Housetosell save error
						if (housetosellSaveErr) done(housetosellSaveErr);

						// Update Housetosell name
						housetosell.name = 'WHY YOU GOTTA BE SO MEAN?';

						// Update existing Housetosell
						agent.put('/housetosells/' + housetosellSaveRes.body._id)
							.send(housetosell)
							.expect(200)
							.end(function(housetosellUpdateErr, housetosellUpdateRes) {
								// Handle Housetosell update error
								if (housetosellUpdateErr) done(housetosellUpdateErr);

								// Set assertions
								(housetosellUpdateRes.body._id).should.equal(housetosellSaveRes.body._id);
								(housetosellUpdateRes.body.name).should.match('WHY YOU GOTTA BE SO MEAN?');

								// Call the assertion callback
								done();
							});
					});
			});
	});

	it('should be able to get a list of Housetosells if not signed in', function(done) {
		// Create new Housetosell model instance
		var housetosellObj = new Housetosell(housetosell);

		// Save the Housetosell
		housetosellObj.save(function() {
			// Request Housetosells
			request(app).get('/housetosells')
				.end(function(req, res) {
					// Set assertion
					res.body.should.be.an.Array.with.lengthOf(1);

					// Call the assertion callback
					done();
				});

		});
	});


	it('should be able to get a single Housetosell if not signed in', function(done) {
		// Create new Housetosell model instance
		var housetosellObj = new Housetosell(housetosell);

		// Save the Housetosell
		housetosellObj.save(function() {
			request(app).get('/housetosells/' + housetosellObj._id)
				.end(function(req, res) {
					// Set assertion
					res.body.should.be.an.Object.with.property('name', housetosell.name);

					// Call the assertion callback
					done();
				});
		});
	});

	it('should be able to delete Housetosell instance if signed in', function(done) {
		agent.post('/auth/signin')
			.send(credentials)
			.expect(200)
			.end(function(signinErr, signinRes) {
				// Handle signin error
				if (signinErr) done(signinErr);

				// Get the userId
				var userId = user.id;

				// Save a new Housetosell
				agent.post('/housetosells')
					.send(housetosell)
					.expect(200)
					.end(function(housetosellSaveErr, housetosellSaveRes) {
						// Handle Housetosell save error
						if (housetosellSaveErr) done(housetosellSaveErr);

						// Delete existing Housetosell
						agent.delete('/housetosells/' + housetosellSaveRes.body._id)
							.send(housetosell)
							.expect(200)
							.end(function(housetosellDeleteErr, housetosellDeleteRes) {
								// Handle Housetosell error error
								if (housetosellDeleteErr) done(housetosellDeleteErr);

								// Set assertions
								(housetosellDeleteRes.body._id).should.equal(housetosellSaveRes.body._id);

								// Call the assertion callback
								done();
							});
					});
			});
	});

	it('should not be able to delete Housetosell instance if not signed in', function(done) {
		// Set Housetosell user 
		housetosell.user = user;

		// Create new Housetosell model instance
		var housetosellObj = new Housetosell(housetosell);

		// Save the Housetosell
		housetosellObj.save(function() {
			// Try deleting Housetosell
			request(app).delete('/housetosells/' + housetosellObj._id)
			.expect(401)
			.end(function(housetosellDeleteErr, housetosellDeleteRes) {
				// Set message assertion
				(housetosellDeleteRes.body.message).should.match('User is not logged in');

				// Handle Housetosell error error
				done(housetosellDeleteErr);
			});

		});
	});

	afterEach(function(done) {
		User.remove().exec();
		Housetosell.remove().exec();
		done();
	});
});