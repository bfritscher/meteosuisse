'use strict';

function pad(n, width, z) {
  z = z || '0';
  n = n + '';
  return n.length >= width ? n : new Array(width - n.length + 1).join(z) + n;
}

angular.module('meteosuisseApp')
  .controller('MainCtrl', function ($scope) {
    $scope.pics = pics;
	$scope.currentPosition = Math.floor($scope.pics.length/2)-2;
	$scope.currentDate = function(){
		var d = new Date(parseInt($scope.pics[$scope.currentPosition].split('.')[0]))
		return  pad(d.getDate(),2) + '.' +   pad((1+ d.getMonth()), 2) + '.' + d.getFullYear() + ' ' + d.getHours() + ':' + pad(d.getMinutes(), 2);
	};
  });
