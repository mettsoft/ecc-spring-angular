angular.module('Authentication', [])
  .service('Authentication', ['$http', '$httpParamSerializer',
    function($http, $httpParamSerializer) {
      let httpConfig = {
        headers: {'Content-Type': 'application/x-www-form-urlencoded'}
      };

      this.login = (command) => $http.post('/login', $httpParamSerializer(command), httpConfig)
        .then(response => {
          this.authUser = {
            username: response.data.username,
            authorities: response.data.authorities.reduce((obj, t) => Object.assign(obj, {
              [t.authority]: true
            }), {})
          };
          window.sessionStorage.authUser = JSON.stringify(this.authUser);
          return response;
      });
      this.logout = () => {
        delete window.sessionStorage.authUser;
        delete this.authUser;
      };
      this.isAuthenticated = () => !!window.sessionStorage.authUser;
      this.access = authority => this.isAuthenticated() && 
        !!this.authUser.authorities[authority];
      this.reload = () => { 
        if (this.isAuthenticated()) {
          this.authUser = JSON.parse(window.sessionStorage.authUser);
        }
      };
    }]
  );