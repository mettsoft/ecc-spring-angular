angular.module('personRegistrationSystem')
  .config(['$stateProvider', $stateProvider => {
    $stateProvider.state({
      name: 'login',
      url: '/',
      component: 'login'
    });
    $stateProvider.state({
      name: 'roleManagement',
      url: '/roles',
      component: 'roleManagement'
    });
  }]);