app.controller('taskController',
    function($scope, $http, $location, $filter, Notification) {
        console.log("aaaaaaaaaaaaa");
        $scope.taskServerURL = "/task";
        $scope.today = new Date();
        $scope.firstPage = true;
        $scope.lastPage = false;
        $scope.pageNo = 0;
        $scope.prevPage = false;
        $scope.isPrev = false;
        $scope.isNext = false;
        $scope.selectedIndex=null;
        $scope.selectedItem=null;


        $scope.format = 'dd/MM/yyyy';
        $scope.openDate = function () {
            $scope.popup8.opened = true;
        };

        $scope.popup8 = {
            opened: false
        };

        $scope.getPaginatedTaskList = function (page) {
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
            $http.post($scope.taskServerURL + '/getPaginatedTaskList?searchText='+$scope.searchText,angular.toJson(paginationDetails)).then(function (response) {
                var data = response.data;
                console.log(data);
                $scope.taskList = data.list;
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

        $scope.getPaginatedTaskList();



        $scope.EditTask = function (data) {
            $scope.task_Id=data.taskId;
            $scope.task_Name = data.taskName;
            $scope.task_Frequency = data.taskFrequency;
            $scope.task_Business_Segment = data.taskBusinessSegment;
            $scope.task_Category=data.taskCategory;
            $scope.task_Provenance_Flag=data.taskProvenanceFlag;
            $scope.task_Nature=data.taskNature;
            $scope.task_Weightage_Score=data.taskWeightageScore;
            $scope.task_Created_By=data.taskCreatedBy;
            $scope.task_Creation_Timestamp=new Date(data.taskCreationTimestamp);
            $scope.task_Approver=data.taskApprover;
            $scope.status=data.status;
            $scope.operation = 'Edit';
            $("#submit").text("Update");
            $('#item-title').text("Edit Task");
            $("#add_new_task").modal('show');

        }, function (error) {
            Notification.error({message: 'Something went wrong, please try again', positionX: 'center', delay: 2000});
        };

        $scope.addTask = function () {
            $scope.getPaginatedTaskList();
            $("#submit").text("Save");
            $('#item-title').text("Add Task");
            $("#add_new_task").modal('show');

        };
        $scope.savetask = function () {
            if($scope.task_Name == ''||$scope.task_Name==null||angular.isUndefined($scope.task_Name)){
                Notification.error({message:'Please Enter Task Name',positionX:'center',delay:2000})
            }
            else if ($scope.task_Category == ''||$scope.task_Category==null||angular.isUndefined($scope.task_Category)) {
                Notification.error({message: 'Please Enter Task Category ', positionX: 'center', delay: 2000});
            }
            else {
                var saveRoomsDetails;
                saveRoomsDetails = {
                    taskId: $scope.task_Id,
                    taskName: $scope.task_Name,
                    taskFrequency: $scope.task_Frequency,
                    taskBusinessSegment: $scope.task_Business_Segment,
                    taskCategory: $scope.task_Category,
                    taskProvenanceFlag: $scope.task_Provenance_Flag,
                    taskNature: $scope.task_Nature,
                    taskWeightageScore: $scope.task_Weightage_Score,
                    taskCreatedBy: $scope.task_Created_By,
                    taskCreationTimestamp: $scope.task_Creation_Timestamp,
                    taskApprover: $scope.task_Approver,
                    status: $scope.status,


                };
                $http.post($scope.taskServerURL + "/savetask", angular.toJson(saveRoomsDetails)).then(function (response) {
                    var data = response.data;
                    if (data == "" || data == 'undefined') {
                        Notification.error({message: ' Already exists', positionX: 'center', delay: 2000});
                    }
                    else {
                        $("#add_new_task").modal('hide');
                        $scope.getPaginatedTaskList();
                        if ($scope.operation == 'Edit') {
                            Notification.success({
                                message: 'Task is Updated successfully',
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



        $scope.DeleteTask=function(data){
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
                        $http.post($scope.taskServerURL + '/getDeleteTask?taskId=' + data.taskId).then(function (response) {
                            var data = response.data;
                            Notification.success({
                                message: 'Task deleted  successfully',
                                positionX: 'center',
                                delay: 2000
                            });
                            $scope.getPaginatedTaskList();
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
