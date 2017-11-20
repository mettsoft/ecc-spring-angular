angular.module('login', ['ui.router', 'Authentication'])
  .component('login', {
    templateUrl: './components/login/login.template.html',
    controller: ['$scope', '$rootScope', '$state', 'Authentication', 
      function($scope, $rootScope, $state, Authentication) {
        $scope.tr = $rootScope.tr;
        $rootScope.onLocaleChange = locale => this.error = this.logout = null;

        this.onCommandSubmit = command => Authentication.login(command)
          .then(response => $state.go('roleManagement'))
          .catch(response => this.error = true);
        Authentication.logout();
    }]
  });