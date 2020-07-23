app.controller('checkInController',
    function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $rootScope) {
        console.log("aaaaaaaaaaaaa");
        $scope.hotelServerURL = "/hotel";
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.customerId = 1;
        $scope.userRights = [];
        $scope.operation = 'Create';
        $scope.customer = 1;
        $scope.today = new Date();
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.selectedIndex=null;
        $scope.selectedItem=null;


        $scope.reloadPage = function () {
            $window.location.reload();
        };

        // $scope.getRoomsList = function (searchText) {
        //     if(angular.isUndefined(searchText)){
        //         $scope.searchText="";
        //     }
        //     $http.post($scope.hotelServerURL + '/getRoomsList').then(function (response) {
        //         var data = response.data.object;
        //         $scope.roomsList = data;
        //     }, function (error) {
        //         Notification.error({
        //             message: 'Something went wrong, please try again',
        //             positionX: 'center',
        //             delay: 2000
        //         });
        //     })
        // };
        $scope.getPaginatedItemList = function (page) {
            switch (page) {
                case 'firstPage':
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.pageNo = 0;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    break;
                case 'lastPage':
                    $scope.lastPage = true;
                    $scope.firstPage = false;
                    $scope.pageNo = 0;
                    $scope.isNext = false;
                    $scope.isPrev = false;
                    break;
                case 'nextPage':
                    $scope.isNext = true;
                    $scope.isPrev = false;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.pageNo = $scope.pageNo + 1;
                    break;
                case 'prevPage':
                    $scope.isPrev = true;
                    $scope.lastPage = false;
                    $scope.firstPage = false;
                    $scope.isNext = false;
                    $scope.pageNo = $scope.pageNo - 1;
                    break;
                default:
                    $scope.firstPage = true;
                    $scope.lastPage = false;
                    $scope.pageNo = 0;
                    $scope.isNext = false;
                    $scope.isPrev = false;
            }
            var paginationDetails;
            paginationDetails={
                firstPage: $scope.firstPage,
                lastPage: $scope.lastPage,
                pageNo: $scope.pageNo,
                prevPage: $scope.isPrev,
                nextPage: $scope.isNext
            }
            if(angular.isUndefined($scope.searchText)){
                $scope.searchText="";
            }
            $http.post($scope.hotelServerURL + '/getPaginatedItemList?searchText='+$scope.searchText,angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.itemList = data.list;
                $scope.first = data.firstPage;
                $scope.last = data.lastPage;
                $scope.prev = data.prevPage;
                $scope.next = data.nextPage;
                $scope.pageNo = data.pageNo;
            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            })
        };

        $scope.getPaginatedItemList();

        $scope.ListItem=function(item, index)
        {
            $scope.selectedIndex=index;
            $scope.selectedItem=item;
            $window.location.href='/home#!/checkIn?id'
        };
        // $scope.getRoomTypesList = function () {
        //     $http.post($scope.hotelServerURL + '/getRoomTypesList').then(function (response) {
        //         var data = response.data.object;
        //         $scope.roomtypesList = data;
        //     }, function (error) {
        //         Notification.error({
        //             message: 'Something went wrong, please try again',
        //             positionX: 'center',
        //             delay: 2000
        //         });
        //     })
        // };

        $scope.imageUpload = function (event) {
            var files = event.target.files;
            var file = files[0];
            var srcString;
            var imageCompressor = new ImageCompressor;
            var compressorSettings = {
                toWidth: 200,
                toHeight: 200,
                mimeType: 'image/png',
                mode: 'strict',
                quality: 1,
                grayScale: false,
                sepia: false,
                threshold: false,
                speed: 'low'
            };
            if (files && file) {
                var reader = new FileReader();
                reader.onload = function (readerEvt) {
                    binaryString = readerEvt.target.result;
                    $('.image-preview').attr('src', binaryString);
                };
                reader.readAsDataURL(file);
                reader.onloadend = function () {
                    srcString = $('.image-preview').attr("src");
                    imageCompressor.run(srcString, compressorSettings, proceedCompressedImage);
                };
            }
        };
        function proceedCompressedImage(compressedSrc) {
            $('#image-preview').attr('src', compressedSrc);
            $scope.fileToUpload = compressedSrc;
        }



        $scope.backconfiguration=function(){
            $window.location.href='/home#!/configuration'
        };

        $scope.EditItem = function (data) {
            $scope.id=data.id;
            $scope.itemName = data.itemName;
            $scope.description = data.description;
            $scope.price = data.price;
            $scope.fileName=data.image;
            $scope.operation = 'Edit';
            $("#submit").text("Update");
            $('#item-title').text("Edit Rooms");
            $("#add_new_item").modal('show');

        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };


        // $scope.EditItem=function(){
        //     $window.location.href='/home#!/checkIn'
        // }

        $scope.addItems = function () {
            $scope.id=0;
            $scope.itemName="";
            $scope.price="";
            $scope.description="";
            $scope.getPaginatedItemList();
            $("#submit").text("Save");
            $('#item-title').text("Add Item");
            $("#add_new_item").modal('show');

        };
        $scope.saveItems = function () {
            if($scope.itemName == ''||$scope.itemName==null||angular.isUndefined($scope.itemName)){
                Notification.error({message:'Please Enter Item Name',positionX:'center',delay:2000})
            }
            else if ($scope.price == ''||$scope.price==null||angular.isUndefined($scope.price)) {
                Notification.error({message: 'Please Enter Price ', positionX: 'center', delay: 2000});
            }
            else {
                var saveRoomsDetails;
                saveRoomsDetails = {
                    id: $scope.id,
                    itemName: $scope.itemName,
                    description: $scope.description,
                    image: $scope.fileToUpload,
                    price: $scope.price,
                    // room_typeId: $scope.roomsNameText

                };
                $http.post($scope.hotelServerURL + "/saveItem", angular.toJson(saveRoomsDetails)).then(function (response) {
                    var data = response.data;
                    if (data == "" || data == 'undefined') {
                        Notification.error({message: ' Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $("#add_new_item").modal('hide');
                        $scope.getPaginatedItemList();
                        if ($scope.operation == 'Edit') {
                            Notification.success({
                                message: 'Rooms is Updated successfully',
                                positionX: 'center',
                                delay: 2000
                            });

                        } else {
                            Notification.success({
                                message: 'Rooms is Created  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                        }

                    }
                }, function (error) {
                    Notification.error({
                        message: 'Something went wrong, please try again',
                        positionX: 'center',
                        delay: 2000
                    });
                });
            }
        };

        // $scope.EditItem = function (data) {
        //     $scope.id=data.id;
        //     $scope.itemName = data.itemName;
        //     $scope.description = data.description;
        //     $scope.price = data.price;
        //     $scope.fileName=data.image;
        //     $scope.operation = 'Edit';
        //     $("#submit").text("Update");
        //     $('#item-title').text("Edit Rooms");
        //     $("#add_new_item").modal('show');
        //
        // }, function (error) {
        //     Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        // };


        $scope.DeleteItem=function(data){
            bootbox.confirm({
                title: "Alert",
                message: "Do you want to Continue ?",
                buttons: {
                    confirm: {
                        label: 'OK'
                    },
                    cancel: {
                        label: 'Cancel'
                    }
                },
                callback: function (result) {
                    if (result == true) {
                        $http.post($scope.hotelServerURL + '/getDeleteItem?id=' + data.id).then(function (response) {
                            var data = response.data;
                            Notification.success({
                                message: 'Item deleted  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.getPaginatedItemList();
                        }, function (error) {
                            Notification.error({
                                message: 'Something went wrong, please try again',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.isDisabled = false;
                        });
                    }
                }
            });
        };

    });
