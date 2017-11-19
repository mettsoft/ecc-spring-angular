angular.module('roleManagement', [])
  .component('roleManagement', {
    templateUrl: './components/role_management/role_management.template.html',
    controller: [function() {
        this.errorMessages = ['Foo', 'Bar'];
        this.successMessage = 'Hello World';
    }]
  });