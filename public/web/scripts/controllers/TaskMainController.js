
angular.module('dashboard.controllers').controller('TaskMainController', ['$scope',  '$rootScope', '$http', 'Restangular', 'SessionMgr', '$translate','$location',  '$interval', '$route',
	function(scope, $http,  $rootScope, Restangular, SessionMgr, $translate, location,  $interval, route){
		$rootScope.locale= "en"
		scope.newOrders = 0;
		$rootScope.silentAjaxs = [];
		scope.userRingerOn = true;
		scope.domReady = true;
		scope.isTestMode = false;
		scope.isAutherised = false;
		scope.taskAvailable = false;

		/*if(SessionMgr.get(data)){
			scope.isAutherised = true;
		}else{
			scope.isAutherised = false;
		}*/


		scope.authenticate = function(){
			//console.log(scope.authCredentials);
			Restangular.all("authentication").post(scope.authCredentials)
			    .then(function(data) {
			  		if(data.authenticated){
			  			if (SessionMgr.get(data)) {
                        	scope.currentSession = SessionMgr.get(data);
                        	scope.isAutherised = true;
                        	scope.authCredentials = {};
                        	scope.fetchNextTask();
                        }
			  		}
				  	//console.log(data);
				  	//console.log(scope.currentSession);
				  	//Restangular.one("order/ordernoti").get().then(function(data){
					//	scope.newOrders = data;
					//});
				  	//scope.fetchOrderNoti();
			});
		};

		scope.fetchNextTask = function(){
			Restangular.one("task/nexttask").get()
		    .then(function(data) {
			  	scope.task = data.plain();
			  	if(scope.task){
			  		route.reload();
			  		scope.taskAvailable = true;
			  	}
			});
		}

		scope.answer = function(taskId){
			Restangular.all("task/answer/" + scope.task.id +"/"+scope.task.answer).customPUT()
				.then(function(data){
					scope.result = data.plain();
					if(scope.result.statusMsg =="Success"){
						alert("Success!!");
						scope.fetchNextTask();

					}
			});
		};
	}
]);