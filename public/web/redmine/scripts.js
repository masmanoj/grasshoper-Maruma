angular.module('redmine', [])
.config(function($httpProvider) {    
})
.controller('Hello', function($scope, $http) {
	console.log(window.btoa('manoj' + ':' + 'mascom123'));
	$http.defaults.headers.common = {'Access-Control-Allow-Origin': '*'};
	$http.defaults.headers.common['Authorization'] = 'Basic ' + window.btoa('manoj' + ':' + 'mascom123');
    $http.get('https://redmine.novopay.in/issues/8013.json').
        then(function(response) {
            $scope.greeting = response.data;
        });
});


/* {
			headers: { 
					   'Content-Type' : 'application/x-www-form-urlencoded; charset=UTF-8',
					   'Authorization' : 'Basic' + window.btoa('6a5f9359b340a51a1366f383e0bdc38a5a7f2a97' + ':' + 'password'),
					   'Access-Control-Allow-Origin': '*'
					},			
			
		}*/