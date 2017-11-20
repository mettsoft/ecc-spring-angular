angular.module('login', ['ui.router'])
  .component('login', {
    templateUrl: './components/login/login.template.html',
    controller: ['$scope', '$rootScope', '$http', '$httpParamSerializer', '$state', 
      function($scope, $rootScope, $http, $httpParamSerializer, $state) {
      $scope.tr = $rootScope.tr;
      $rootScope.onLocaleChange = locale => this.error = this.logout = null;

      this.onCommandSubmit = command => {
        $http.post('/login', $httpParamSerializer(command), {
          headers: {'Content-Type': 'application/x-www-form-urlencoded'}
        }).then(response => {
          $rootScope.authUser = {
            username: response.data.username,
            authorities: response.data.authorities.map(t => t.authority)
          };
          $state.go('roleManagement');
        }).catch(response => this.error = true);
      };
    }]
  });