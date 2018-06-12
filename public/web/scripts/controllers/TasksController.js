angular.module('dashboard.controllers').controller('TasksController', ['$scope',  '$rootScope', '$http', 'Restangular','$location', '$route', 'NotificationService',
	function(scope, $http,  $rootScope, Restangular, location, route, NotificationService){
		scope.tasks = [];

		Restangular.all("task").getList()
		    .then(function(data) {
			  	scope.tasks = data.plain();

			});

		scope.unlockTask = function(taskId){
			Restangular.all("task/unlock/" + taskId).customPUT()
				.then(function(data){
					NotificationService.showSuccess();
					route.reload();
			});
		};


		scope.closeTask = function(taskId){
			Restangular.all("task/close/" + taskId).customPUT()
				.then(function(data){
					NotificationService.showSuccess();
					route.reload();
			});
		};
	}
]);