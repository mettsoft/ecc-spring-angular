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
    $stateProvider.state({
      name: 'userManagement',
      url: '/users',
      component: 'userManagement'
    });
    $stateProvider.state({
      name: 'personManagement',
      url: '/persons',
      component: 'personManagement'
    });
  }]);