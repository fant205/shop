angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app';
    var currentPage = 0;
    var size = 10;

    $scope.nextPage = function (i) {
        currentPage = currentPage + i;
        $scope.loadProducts(currentPage);
    };

    $scope.loadProducts = function (page) {
        if(page == null){
            page = currentPage;
        }
        console.log("page: " + page);
        var min = 0;
        var max = 100;
        if($scope.newFilterJson != null){
            min = $scope.newFilterJson.min;
            max = $scope.newFilterJson.max;
        }

        $http({
            url: contextPath + '/products/all',
            method: 'GET',
            params: {
                min: min,
                max: max,
                page: page,
                size: size,
            }
        }).then(function (response){
            $scope.productList = response.data.list;
            var array = [];
            for (let index = 0; index < response.data.pagesCount; index++) {
                array.push({
                    id: index + 1
                })
            }
            $scope.pagesCount = array;
            console.log($scope.pagesCount);
            $scope.recordsTotal = response.data.recordsTotal;
        });

    };

    $scope.changeScore = function (id, cost){
        $http({
            url: contextPath + '/products/change_cost',
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
            url: contextPath + '/products/delete/' + id,
            method: 'DELETE',
        }).then(function (response){
            $scope.loadProducts();
        });
    };

    $scope.createProductJson = function (){
        console.log($scope.newProductJson);
        $http.post(contextPath + '/products', $scope.newProductJson)
            .then(function (response) {
                $scope.loadProducts()
            });
    };

    $scope.loadProducts(0);

});