angular.module('personRegistrationSystem', ['roleManagement'])
	.run(['$rootScope', '$http', function($rootScope, $http) {
		$rootScope.locale = 'en';
		$http.get('/resources').then(response => {
			$rootScope.dictionary = response.data;
			$rootScope.tr = (key, ...args) => {
				let text = $rootScope.dictionary[$rootScope.locale][key];
				args.forEach((value, index) => text = text.replace(`{${index}}`, value));
				return text;
			};
		});
	}]);