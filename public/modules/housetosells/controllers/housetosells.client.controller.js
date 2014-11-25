'use strict';

// Housetosells controller
angular.module('housetosells').controller('HousetosellsController', ['$scope', '$stateParams', '$location', 'Authentication', 'Housetosells', 'Categories',
	function($scope, $stateParams, $location, Authentication, Housetosells, Categories) {
        var markers = [];
        $scope.authentication = Authentication;
        $scope.minFilterPrice = 0;
        $scope.maxFilterPrice = 999999999;
        $scope.filterCategory = "";
        $scope.city = "";
        $scope.image = "";

        $scope.defaultBounds = new google.maps.LatLngBounds(new google.maps.LatLng(45.47659, -71,592448), new google.maps.LatLng(45.32052, -72,245098));
        $scope.map = {
            center: {
                latitude: 45.39860,
                longitude: -71.91877
            },
            zoom: 12,
            bounds: {
                northeast: {
                    latitude: $scope.defaultBounds.getNorthEast().lat(),
                    longitude: $scope.defaultBounds.getNorthEast().lng()
                },
                southwest: {
                    latitude: $scope.defaultBounds.getSouthWest().lat(),
                    longitude: -$scope.defaultBounds.getSouthWest().lng()

                }
            },
            markers: [],
            idkey: 'place_id',
            events: {
                bounds_changed: function (map) {
                    var bounds = map.getBounds();
                    $scope.searchbox.options.bounds = bounds;
                }
            }
        };

        $scope.searchbox = {
            template:'searchbox.tpl.html',
            parentdiv:'searchBoxParent',
            options: {
                bounds: {
                    northeast: {
                        latitude: $scope.defaultBounds.getNorthEast().lat(),
                        longitude: $scope.defaultBounds.getNorthEast().lng()
                    },
                    southwest: {
                        latitude: $scope.defaultBounds.getSouthWest().lat(),
                        longitude: -$scope.defaultBounds.getSouthWest().lng()

                    }
                }
            },
            events: {
                places_changed: function (searchBox) {
                    var places = searchBox.getPlaces();

                    if (places.length == 0) {
                        return;
                    }

                    // For each place, get the icon, place name, and location.
                    markers = [];
                    var bounds = new google.maps.LatLngBounds();
                    for (var i = 0, place; place = places[i]; i++) {
                        // Create a marker for each place.
                        var marker = {
                            id:i,
                            place_id: place.place_id,
                            name: place.name,
                            latitude: place.geometry.location.lat(),
                            longitude: place.geometry.location.lng()
                        };
                        markers.push(marker);

                        bounds.extend(place.geometry.location);

                        $scope.latitude = place.geometry.location.lat();
                        $scope.longitude = place.geometry.location.lng();
                        $scope.address = place.formatted_address;
                    }

                    $scope.map.bounds = {
                        northeast: {
                            latitude: bounds.getNorthEast().lat(),
                            longitude: bounds.getNorthEast().lng()
                        },
                        southwest: {
                            latitude: bounds.getSouthWest().lat(),
                            longitude: bounds.getSouthWest().lng()
                        }
                    };

                    $scope.map.markers = markers;
                }
            }
        };

		// Create new Housetosell
		$scope.create = function() {
			// Create new Housetosell object
			var housetosell = new Housetosells ({
                latitude: this.latitude,
                longitude: this.longitude,
                category : this.category,
                address: this.address,
                description: this.description,
                characteristics: this.characteristics,
                nbRoom: this.nbRoom,
                price: this.price,
                image: this.image
			});

			// Redirect after save
			housetosell.$save(function(response) {
				$location.path('housetosells/' + response._id);

				// Clear form fields
                $scope.latitude = 0;
                $scope.longitude = 0;
                $scope.category = '';
                $scope.address = '';
                $scope.description = '';
                $scope.characteristics = '';
				$scope.nbRoom = 0;
                $scope.price = 0;
			}, function(errorResponse) {
				$scope.error = errorResponse.data.message;
			});
		};

		// Remove existing Housetosell
		$scope.remove = function(housetosell) {
			if ( housetosell ) { 
				housetosell.$remove();

				for (var i in $scope.housetosells) {
					if ($scope.housetosells [i] === housetosell) {
						$scope.housetosells.splice(i, 1);
					}
				}
			} else {
				$scope.housetosell.$remove(function() {
					$location.path('housetosells');
				});
			}
		};

		// Update existing Housetosell
		$scope.update = function() {
			var housetosell = $scope.housetosell;

			housetosell.$update(function() {
				$location.path('housetosells/' + housetosell._id);
			}, function(errorResponse) {
				$scope.error = errorResponse.data.message;
			});
		};

		// Find a list of Housetosells
		$scope.find = function() {
            if($scope.minFilterPrice >= $scope.maxFilterPrice) {
                $scope.error = "minimum price must be smaller then maximum price";
                return;
            }
            else
                $scope.error = "";

            var res = $scope.city.split(',');
			$scope.housetosells = Housetosells.query({
                minPrice: $scope.minFilterPrice,
                maxPrice: $scope.maxFilterPrice,
                category: $scope.filterCategory,
                city: res[0]
            },function() {
                $scope.totalItems = $scope.housetosells.length;
                $scope.pageSize = 5;
                $scope.currentPage = 1;
            });
		};

		// Find existing Housetosell
		$scope.findOne = function() {
            $scope.init();
			$scope.housetosell = Housetosells.get({ 
				housetosellId: $stateParams.housetosellId
			},function() {
                $scope.map = {
                    center: {
                        latitude: $scope.housetosell.latitude,
                        longitude: $scope.housetosell.longitude
                    },
                    zoom: 18
                };

                var marker = {
                    id:0,
                    latitude: $scope.housetosell.latitude,
                    longitude: $scope.housetosell.longitude
                };
                $scope.map.markers = [marker];
            });
		};

        var onSuccess = function(position) {
                $scope.map.center = {
                    latitude: position.coords.latitude,
                    longitude: position.coords.longitude
                };
                $scope.$apply();
        };

        function onError(error) {
            console.log('code: ' + error.code + '\n' + 'message: ' + error.message + '\n');
        }

        $scope.getGeolocation = function() {
            $scope.init();
            navigator.geolocation.getCurrentPosition(onSuccess, onError);
        };

        $scope.findOneCategory = function(category) {
            for (var index = 0; index < $scope.categories.length; ++index) {
                if(category == $scope.categories[index]._id)
                    return $scope.categories[index].categoryName;
            }
        };

        $scope.init = function() {
            $scope.categories = Categories.query();
        };

        function readImage(file) {
            var reader = new FileReader();

            reader.readAsDataURL(file);
            reader.onload = function(_file) {
                $("#imagePreview").attr('src', _file.target.result);
                $scope.image = _file.target.result;
                if($scope.housetosell)
                    $scope.housetosell.image = _file.target.result;
                $scope.$apply();
            };
        }

        $("#choose").change(function (e) {
            if(this.disabled) return alert('File upload not supported!');
            var F = this.files;
            if(F && F[0]) for(var i=0; i<F.length; i++) readImage( F[i] );
        });
	}
]).filter('startFrom', function() {
    return function(input, start) {
        start = +start; //parse to int
        if(start) {
            start = start - 5;
            return input.slice(start);
        }
    };
});