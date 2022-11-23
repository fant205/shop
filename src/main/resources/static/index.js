angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app';

    $scope.loadProducts = function () {
        $http.get(contextPath + '/product/all')
            .then(function (response) {
                $scope.productList = response.data;
            });
    };

    $scope.changeScore = function (id, cost){
        $http({
            url: contextPath + '/product/change_cost',
            method: 'GET',
            params: {
                id: id,
                cost: cost
            }
        }).then(function (response){
            $scope.loadProducts();
        });
    };

    $scope.delete = function (id){
        $http({
            url: contextPath + '/product/delete',
            method: 'DELETE',
            params: {
                id: id
            }
        }).then(function (response){
            $scope.loadProducts();
        });
    };

    $scope.loadProducts();

});