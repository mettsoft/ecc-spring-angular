angular.module('personRegistrationSystem', ['ui.router', 'roleManagement', 'login', 'Authentication'])
	.run(['$rootScope', '$http', '$state', '$transitions', 'Authentication',
		function($rootScope, $http, $state, $transitions, Authentication) {
			// Expose convenience methods and objects to $rootScope.
			$rootScope.locale = 'en';
			$http.get('/resources').then(response => {
				let dictionary = response.data;
				$rootScope.tr = (key, ...args) => {
					let text = dictionary[$rootScope.locale][key];
					args.forEach((value, index) => text = text.replace(`{${index}}`, value));
					return text;
				};
			});
			$rootScope.access = Authentication.access;
			$rootScope.$state = $state;			

			// Register authentication filter.
			let authenticationFilter = (function authenticationFilter() {
				if (!Authentication.isAuthenticated()) {
					$state.go('login');
				}
				return authenticationFilter;
			})();
			$transitions.onSuccess({}, authenticationFilter);

			// Register global events.
			$rootScope.logout = () => $state.go('login');

			// Initialization.
			Authentication.reload();
	}]);