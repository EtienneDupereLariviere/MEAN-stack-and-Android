'use strict';

// Configuring the Articles module
angular.module('housetosells').run(['Menus',
	function(Menus) {
		// Set top bar menu items
		Menus.addMenuItem('topbar', 'Real estate', 'Real estate', 'dropdown', '/housetosells(/create)?');
		Menus.addSubMenuItem('topbar', 'Real estate', 'List home for sale', 'housetosells');
		Menus.addSubMenuItem('topbar', 'Real estate', 'Add home for sale', 'housetosells/create');
	}
]);