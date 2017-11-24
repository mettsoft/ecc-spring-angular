angular.module('userManagement', ['Authentication'])
  .component('userManagement', {
    templateUrl: './components/user_management/user_management.template.html',
    controller: ['$scope', '$rootScope', '$http', 'Authentication', 
      function($scope, $rootScope, $http, Authentication) {
        // Functions
        const DEFAULT_PERMISSIONS = {
          'ROLE_CREATE_PERSON': false,
          'ROLE_UPDATE_PERSON': false,
          'ROLE_DELETE_PERSON': false,
          'ROLE_CREATE_ROLE': false,
          'ROLE_UPDATE_ROLE': false,
          'ROLE_DELETE_ROLE': false,
        };
        let resetPermissions = () => this.permissions = Object.assign({}, DEFAULT_PERMISSIONS);

        // Import desired methods from $rootScope to $scope.
        $scope.tr = $rootScope.tr;

        // Bind listener to a global event.
        $rootScope.onLocaleChange = locale => this.successMessage = this.errorMessages = null;

        // Bind listeners to local events.
        this.editRow = index => {
          this.command = Object.assign({}, this.data[index], {
            index: index, 
            allowEmptyPassword: true,
            password: ''
          });
          resetPermissions();
          this.command.permissions.forEach(value => this.permissions[value] = true);
        };

        this.deleteRow = index => {
          let user = this.data[index];
          if (confirm($scope.tr('user.data.form.button.deleteConfirmation', user.username))) {
            $http.delete(`/users/${user.id}`).then(response => {
              this.command = {};
              this.data.splice(index, 1);
              this.errorMessages = null;
              this.successMessage = $scope.tr('user.successMessage.delete', user.username);
            }).catch(response => {
              this.errorMessages = response.data;
              this.successMessage = null;
            });
          }
        };

        this.onCommandSubmit = command => {
          let method = command.id? 'PUT': 'POST';
          command.permissions = [];
          for (let key in this.permissions) {
            if (this.permissions.hasOwnProperty(key) && this.permissions[key]) {
              command.permissions.push(key);
            }
          }
          $http({
            method: method,
            url: '/users',
            data: command
          }).then(response => {
            this.command = {};
            resetPermissions();
            this.errorMessages = null;
            if (!command.id) {
              this.successMessage = $scope.tr('user.successMessage.create', command.username);
              this.data.push(response.data);
            }
            else {
              this.data[command.index] = response.data;
              this.successMessage = $scope.tr('user.successMessage.update', command.username);
            }
          }).catch(response => {
            this.errorMessages = response.data;
            this.successMessage = null;
          });
        };

        // Initialization
        this.command = {};
        resetPermissions();
        $http.get('/users').then(response => this.data = response.data);
      }]
  });