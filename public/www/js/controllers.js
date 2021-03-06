//services
angular.module('bluefin.controllers', [])


//controllers
.controller('MainController', [ '$scope', '$ionicModal', 'localStorageService', 'Restangular', '$rootScope', 'SessionMgr', '$state', '$ionicSideMenuDelegate',
  function (scope, $ionicModal, localStorageService, Restangular, $rootScope, SessionMgr, $state, $ionicSideMenuDelegate) { 
    scope.goHome = function(){
      console.log("go Home");
     // $state.go("home");
    }
    scope.toggleLeft = function() {
      $ionicSideMenuDelegate.toggleLeft();
    };
  }
])
.controller('HomeController', [ '$scope', 'Restangular',
  function (scope, Restangular){
    scope.moreDataCanBeLoaded = true;
    scope.limit =10;
    scope.offset =0
    scope.products=[];
    scope.loadProducts = function(){
      Restangular.all("stage/search-items").getList({l:scope.limit+1,o:scope.offset})
        .then(function(data) {
          var products = data.plain();
          if( products.length <= scope.limit){
            scope.moreDataCanBeLoaded = false;
          }else{
            products.splice(products.length-1, 1);
          }
          Array.prototype.push.apply(scope.products,products);
          scope.offset += scope.limit;
          scope.$broadcast('scroll.infiniteScrollComplete');

      });
    }
    
    scope.$on('$stateChangeSuccess', function() {
      console.log("at Home");
      //scope.loadProducts();
    });
  }
])
.controller('LoginController', [ '$scope', '$ionicModal', 'localStorageService', 'Restangular', '$rootScope', 'SessionMgr',
  function (scope, $ionicModal, localStorageService, Restangular, $rootScope, SessionMgr) { 
  scope.authCredentials = {}

  if (localStorageService.get("userCred")) {
     var authCredentials = localStorageService.get("userCred");
     console.log("authCredentials",authCredentials);

     localStorageService.set("userCred", null);
     Restangular.all("authentication").post(authCredentials)
          .then(function(data) {
            if(data.authenticated){
              if (SessionMgr.get(data)) {
                scope.currentSession = SessionMgr.get(data);
                $rootScope.isAutherised = true;
                $rootScope.loginNeeded = false;
                $rootScope.loginCheckFinished = true;
              }
            }else{
              localStorageService.set("userCred", null);
              localStorageService.remove("userCred");


              $rootScope.loginCheckFinished = true;
              $rootScope.loginNeeded = true;
            }
        });

  }else{
    $rootScope.loginCheckFinished = true;
    $rootScope.loginNeeded = true;

  }


  scope.authenticate = function(){
    //console.log(scope.authCredentials);
    Restangular.all("authentication").post(scope.authCredentials)
        .then(function(data) {
          if(data.authenticated){
            if (SessionMgr.get(data)) {
              scope.currentSession = SessionMgr.get(data);
              $rootScope.isAutherised = true;
              scope.authCredentials = {};
              }
          }else{
            localStorageService.set("userCred", null);
            localStorageService.remove("userCred");
          }
      });
  };

  scope.logout = function(){
    scope.currentSession = null;
    $rootScope.isAutherised = false;
    Restangular.all("authentication/logout").post()
        .then(function(data) {
          scope.currentSession = null;
        SessionMgr.get(data);
        localStorageService.set("userCred", null);
        localStorageService.remove("userCred");
      });
    
  }

  /*localStorageService.set("userSession", "YES");

  if (localStorageService.get("userSession")) {
     alert(localStorageService.get("userSession"));
  }*/

}])