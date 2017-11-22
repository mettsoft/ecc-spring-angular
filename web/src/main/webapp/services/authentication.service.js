function createAuthenticationHeader(authToken) {
  return {'Authorization': ` Basic ${authToken}`};
}

angular.module('Authentication', [])
  .service('Authentication', ['$http', '$cookies', '$base64',
    function($http, $cookies, $base64) {
      this.login = (command) => {
        command = command || {};
        const authorizationHeader = createAuthenticationHeader($base64.encode(`${command.username}:${command.password}`));
        return $http({
          url: '/users/permissions',
          headers: authorizationHeader
          }).then(response => {
            $cookies.putObject("authUser", {
              username: command.username,
              authorities: response.data,
              Authorization: authorizationHeader.Authorization
            });
            this.reload();
            return response;
        });
      };

      this.logout = () => $cookies.remove("authUser");

      this.isAuthenticated = () => !!$cookies.getObject('authUser');

      this.access = authority => this.isAuthenticated() && 
        !!$cookies.getObject('authUser').authorities[authority];

      this.reload = () => { 
        if (this.isAuthenticated()) {
          $http.defaults.headers.common['Authorization'] = $cookies.getObject('authUser').Authorization;
        }
      }; 
    }]
  );