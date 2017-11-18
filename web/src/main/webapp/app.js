angular
	.module('person-registration-system', [])
	.run(['$rootScope', '$http', function($rootScope, $http) {
		$rootScope.locale = 'en';
		$http({
			method: 'GET',
			url: '/resources'
		}).then(response => {
			$rootScope.dictionary = response.data;
			$rootScope.tr = key => $rootScope.dictionary[$rootScope.locale][key];
		});
	}]);