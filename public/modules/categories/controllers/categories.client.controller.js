'use strict';

//Housetosells service used to communicate Housetosells REST endpoints
angular.module('categories').controller('CategoriesController', ['$scope', 'Categories',
	function($scope, $stateParams, Categories) {

        // Find a list of Categories
        $scope.find = function() {
            $scope.categories = Categories.query();
        };

        // Find existing Category
        $scope.findOne = function() {
            $scope.categories = Categories.get({
                categoryId: $stateParams.categoryId
            });
        };
	}
]);