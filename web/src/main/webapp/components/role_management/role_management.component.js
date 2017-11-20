angular.module('roleManagement', [])
  .component('roleManagement', {
    templateUrl: './components/role_management/role_management.template.html',
    controller: ['$scope', '$rootScope', '$http', function($scope, $rootScope, $http) {
      $http.get('/roles').then(response => this.data = response.data);
      $scope.__proto__ = $rootScope;
      this.command = {};
      $rootScope.onLocaleChange = locale => this.successMessage = this.errorMessages = null;

      this.editRow = index => this.command = Object.assign({index: index}, this.data[index]);

      this.deleteRow = index => {
        let role = this.data[index];
        if (confirm($scope.tr('role.data.form.button.deleteConfirmation', role.name))) {
          $http.delete(`/roles/${role.id}`).then(response => {
            this.data.splice(index, 1);
            this.errorMessages = null;
            this.successMessage = $scope.tr('role.successMessage.delete', role.name);
          }).catch(response => {
            this.errorMessages = response.data.errors;
            this.successMessage = null;
          });
        }
      };

      this.onCommandSubmit = command => {
        let method = command.id? 'PUT': 'POST'; 
        $http({
          method: method,
          url: '/roles',
          data: command
        }).then(response => {
          this.command = {};
          this.errorMessages = null;
          if (!command.id) {
            this.successMessage = $scope.tr('role.successMessage.create', command.name);
            this.data.push(response.data);
          }
          else {
            this.data[command.index] = response.data;
            this.successMessage = $scope.tr('role.successMessage.update', command.name);
            if (command.persons.length > 0) {
              let personNames = command.persons.map(p => [p.name.title, p.name.lastName + ',', p.name.firstName, p.name.middleName, p.name.suffix].join(' ').trim());
              this.successMessage += ` ${$scope.tr('role.successMessage.affectedPersons', personNames.join(';'))}`;
            }
          }
        }).catch(response => {
          this.errorMessages = response.data.errors;
          this.successMessage = null;
        });
      };
    }]
  });