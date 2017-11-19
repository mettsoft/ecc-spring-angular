angular.module('roleManagement', [])
  .component('roleManagement', {
    templateUrl: './components/role_management/role_management.template.html',
    controller: ['$scope', '$rootScope', function($scope, $rootScope) {
      $scope.__proto__ = $rootScope;
      this.headerTitle = 'Header Title';
      this.errorMessages = ['Foo', 'Bar'];
      this.successMessage = 'Hello World';
      this.writeEndpoint = '/create';
    }]
  });