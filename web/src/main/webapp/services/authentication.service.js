angular.module('Authentication', [])
  .service('Authentication', ['$http', '$httpParamSerializer',
    function($http, $httpParamSerializer) {
      let httpConfig = {
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
      };
      this.login = (command) => $http.post('/login', $httpParamSerializer(command), httpConfig)
        .then(response => {
          window.sessionStorage.authUser = {
            username: response.data.username,
            authorities: response.data.authorities.map(t => t.authority)
          };
          return response;
      });
      this.logout = () => delete window.sessionStorage.authUser;
    }]
  );