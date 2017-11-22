var TRANSLATIONS;

$.ajax({
  dataType: "json",
  url: "/resources",
  async: false,
  success: data => TRANSLATIONS = data
});

const externalDependencies = ['ui.router', 'base64', 'ngCookies'];
const internalDependencies = ['personManagement', 'roleManagement', 'userManagement', 'login', 'Authentication'];

angular.module('personRegistrationSystem', [...externalDependencies, ...internalDependencies])
	.run(['$rootScope', '$http', '$state', '$transitions', 'Authentication',
		function($rootScope, $http, $state, $transitions, Authentication) {
			// Expose convenience methods and objects to $rootScope.
			$rootScope.locale = 'en';
			$rootScope.tr = (key, ...args) => {
				let text = TRANSLATIONS[$rootScope.locale][key];
				args.forEach((value, index) => text = text.replace(`{${index}}`, value));
				return text;
			};

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
			Authentication.reload();
	}]);