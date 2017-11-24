const DEFAULT_COMMAND = {
  contacts: [],
  roles: []
};

function dateToString(object, ...keys) {
  const command = Object.assign({}, object);
  keys.forEach(key => command[key] = command[key]? command[key].toISOString().split('T')[0]: undefined);
  return command;
}

function stringToDate(object, ...keys) {
  const command = Object.assign({}, object);
  keys.forEach(key => command[key] = command[key]? new Date(command[key]): undefined);
  return command;  
}

angular.module('personManagement', ['Authentication', 'ngFileUpload'])
  .component('personManagement', {
    templateUrl: './components/person_management/person_management.template.html',
    controller: ['$scope', '$rootScope', '$http', '$httpParamSerializer', 'Authentication', 'Upload',
      function($scope, $rootScope, $http, $httpParamSerializer, Authentication, Upload) {
        // Import desired methods from $rootScope to $scope.
        $scope.tr = $rootScope.tr;
        $scope.access = Authentication.access;

        // Bind listener to a global event.
        $rootScope.onLocaleChange = locale => this.successMessage = this.errorMessages = null;

        // Convenience methods.
        this.serializeName = name => [
          name.title, 
          name.lastName + ',', 
          name.firstName, 
          name.middleName, 
          name.suffix
        ].join(' ').trim();

        this.serializeAddress = address => [
          address.streetNumber, 
          'Barangay ' + address.barangay, 
          address.municipality, 
          address.zipCode, 
          address.suffix
        ].join(' ').trim();

        // Bind listeners to local events.
        this.editRow = index => this.command = Object.assign({index: index}, stringToDate(this.data[index], 'birthday', 'dateHired'));

        this.deleteRow = index => {
          const person = this.data[index];
          if (confirm($scope.tr('person.data.form.button.deleteConfirmation', this.serializeName(person.name)))) {
            $http.delete(`/persons/${person.id}`).then(response => {
              this.command = Object.assign({}, DEFAULT_COMMAND);
              this.data.splice(index, 1);
              this.errorMessages = null;
              this.successMessage = $scope.tr('person.successMessage.delete', this.serializeName(person.name));
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
            url: '/persons',
            data: dateToString(command, 'birthday', 'dateHired')
          }).then(response => {
            this.command = Object.assign({}, DEFAULT_COMMAND);
            this.errorMessages = null;
            if (!command.id) {
              this.successMessage = $scope.tr('person.successMessage.create', this.serializeName(command.name));
              this.data.push(response.data);
            }
            else {
              this.data[command.index] = response.data;
              this.successMessage = $scope.tr('person.successMessage.update', this.serializeName(command.name));
            }
          }).catch(response => {
            this.errorMessages = response.data.errors;
            this.successMessage = null;
          });
        };

        this.upload = file => {
          Upload.upload({
            url: '/persons/upload',
            data: {file: file}
          }).then(response => {
            this.file = null;
            this.successMessage = $scope.tr('person.successMessage.create', this.serializeName(response.data.name));
            this.data.push(response.data);
          }).catch(response => {
            this.errorMessages = response.data.errors;
            this.successMessage = null;
          });
        };

        // Initialization
        this.command = Object.assign({}, DEFAULT_COMMAND);
        this.preload = {};
        this.query = {
          searchType: '0',
          orderBy: 'name.lastName',
          orderType: 'ASC'
        };

        this.search = query => { 
          $http.get('/persons?' + $httpParamSerializer(dateToString(query, 'birthday')))
            .then(response => this.data = response.data);
        };
        this.search(this.query);
        $http.get('/roles').then(response => this.preload.roleItems = response.data);
      }]
  });