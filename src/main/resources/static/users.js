angular.module('app', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/app/api/v1/users';
    var currentPage = 0;
    var size = 5;
    var totalPages = null;

    $scope.nextPage = function (i) {
        if(currentPage + i == totalPages || currentPage + i == -1){
            return;
        }
        currentPage = currentPage + i;
        $scope.loadProducts(currentPage);
    };

    $scope.loadUsers = function (page) {
        if(page == null){
            page = currentPage;
        }
        console.log("page: " + page);
        var id = null;
        var login = null;

        if($scope.newFilterJson != null){
            id = $scope.newFilterJson.id;
            login = $scope.newFilterJson.login;
        }

        $http({
            url: contextPath,
            method: 'GET',
            params: {
                id: id,
                login: login,
                page: page,
                size: size,
            }
        }).then(function (response){
            console.log("success: " + contextPath)
            var list = response.data.list;
            for (let index = 0; index < list.length; index++) {
                var e = list[index];
                var roles = e.roles;
                var rolesStr = "";
                for (let j = 0; j < roles.length; j++) {
                    rolesStr = rolesStr + roles[j].name + ', ';
                }
                if(rolesStr.length != 0){
                    rolesStr = rolesStr.substring(0, rolesStr.length - 2)
                }
                e.rolesStr = rolesStr;
            }


            $scope.userList = list;
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

    $scope.loadUsers();

});