angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1/products';
    var currentPage = 0;
    var size = 10;
    var totalPages = null;

    $scope.nextPage = function (i) {
        if(currentPage + i == totalPages || currentPage + i == -1){
            return;
        }
        currentPage = currentPage + i;
        $scope.loadProducts(currentPage);
    };

    $scope.loadProducts = function (page) {
        if(page == null){
            page = currentPage;
        }
        console.log("page: " + page);
        var id = null;
        var title = null;
        var min = null;
        var max = null;
        if($scope.newFilterJson != null){
            id = $scope.newFilterJson.id;
            title = $scope.newFilterJson.title;
            min = $scope.newFilterJson.min;
            max = $scope.newFilterJson.max;
        }

        $http({
            url: contextPath,
            method: 'GET',
            params: {
                id: id,
                title: title,
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
            totalPages = response.data.pagesCount;
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
            url: contextPath + '/' + id,
            method: 'DELETE',
        }).then(function (response){
            $scope.loadProducts();
        });
    };

    $scope.createProductJson = function (){
        console.log($scope.newProductJson);
        $http.post(contextPath, $scope.newProductJson)
            .then(function (response) {
                $scope.loadProducts()
            });
    };

    $scope.loadProducts(0);

});