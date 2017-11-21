let externalDependencies = ['ui.router', 'base64', 'ngCookies'];
let internalDependencies = ['roleManagement', 'userManagement', 'login', 'Authentication'];

angular.module('personRegistrationSystem', [...externalDependencies, ...internalDependencies])
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

			// Register security filter.
			let securityFilter = transition => {
				if (!Authentication.isAuthenticated()) {
					$state.go('login');
				}
				else if (transition && transition.to().name === 'userManagement' && !Authentication.access('ROLE_ADMIN')) {
					$state.go(transition.from().name);
				}
			};
			$transitions.onEnter({}, securityFilter);

			// Register global events.
			$rootScope.logout = () => $state.go('login');

			// Initialization.
			securityFilter();
	}]);