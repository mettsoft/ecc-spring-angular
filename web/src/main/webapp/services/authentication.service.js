function createAuthenticationHeader(authToken) {
  return {'Authorization': ` Basic ${authToken}`};
}

angular.module('Authentication', [])
  .service('Authentication', ['$http', '$cookies', '$base64',
    function($http, $cookies, $base64) {  

      this.login = (command) => $http({
        url: '/users/permissions',
        headers: createAuthenticationHeader($base64.encode(`${command.username}:${command.password}`))
        }).then(response => {
          $cookies.putObject("authUser", {
            username: command.username,
            authorities: response.data
          });
          return response;
        });

      this.logout = () => $cookies.remove("authUser");

      this.isAuthenticated = () => !!$cookies.get('authUser');

      this.access = authority => this.isAuthenticated() && 
        !!$cookies.get('authUser').authorities[authority];
    }]
  );