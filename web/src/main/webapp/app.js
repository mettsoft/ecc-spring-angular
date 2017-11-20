angular.module('personRegistrationSystem', ['ui.router', 'roleManagement', 'login'])
	.run(['$rootScope', '$http', '$state', '$transitions', 
		function($rootScope, $http, $state, $transitions) {
			$rootScope.locale = 'en';
			$http.get('/resources').then(response => {
				$rootScope.dictionary = response.data;
				$rootScope.tr = (key, ...args) => {
					let text = $rootScope.dictionary[$rootScope.locale][key];
					args.forEach((value, index) => text = text.replace(`{${index}}`, value));
					return text;
				};
			});

			let authenticationFilter = (function authenticationFilter() {
				if (!window.sessionStorage.authUser) {
					$state.go('login');
				}
				return authenticationFilter;
			})();
			$transitions.onSuccess({}, authenticationFilter);

			$rootScope.$state = $state;			
			$rootScope.logout = () => $state.go('login');
	}])
	.config(['$stateProvider', $stateProvider => {
		$stateProvider.state({
			name: 'login',
			url: '/',
			component: 'login'
		});
		$stateProvider.state({
			name: 'roleManagement',
			url: '/roles',
			component: 'roleManagement'
		});
	}]);