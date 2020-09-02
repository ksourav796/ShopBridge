

app.controller('loginController',
    function($scope, $http, $location, $filter, Notification, ngTableParams,  $timeout, $window, $rootScope){
        console.log("aaaaaaaaaaaaa");
        $('#sidebar-menu').hide();
        $scope.word = /^[a-z]+[a-z0-9._]+@[a-z]+\.[a-z.]{2,5}$/;
        $scope.registrationFormData = {};
        $scope.loginFormData = {};
        $scope.forgetPwdFormData = {};




        // function createCookie(name, value, days) {
        //     if (days) {
        //         var date = new Date();
        //         date.setTime(date.getTime() + (days * 24 * 60 * 60 * 1000));
        //         var expires = "; expires=" + date.toGMTString();
        //     }
        //     else var expires = "";
        //     document.cookie = name + "=" + value + expires + ";";
        // }

        $(document).ready(function () {
            $(".btn-login").click(function () {
                $(".bigicon").addClass("fa-spin");
            });
        });



        $scope.removeuserDetails = function () {
            $scope.userEmailText = "";
            $scope.userNameText = "";
            $scope.pswdText = "";
        }


        $('#logoutButton').hide();
        $scope.saveuservalidate = function () {
            var saveUserDetails;
            saveUserDetails = {
                email: $scope.userEmailText,
                userName: $scope.userNameText,
                passwordUser: $scope.pswdText,
            };
            $http.post("/task/userValidate", angular.toJson(saveUserDetails)).then(function (response) {
                var data = response.data;
                $rootScope.userName=data.full_name;
                if(data==""){
                    $('#sidebar-menu').hide();
                    $scope.removeuserDetails();
                    Notification.error({
                        message: 'Invalid Authentication',
                        positionX: 'center',
                        delay: 2000
                    });
                }
                else{
                    $window.location.href = '/home#!/TaskMaster';
                    $('#sidebar-menu').show();
                    $('#logoutButton').show();
                    $scope.removeuserDetails();
                    Notification.success({
                        message: 'Sucess',
                        positionX: 'center',
                        delay: 2000
                    });
                }

            }, function (error) {
                Notification.error({
                    message: 'Something went wrong, please try again',
                    positionX: 'center',
                    delay: 2000
                });
            });
        }
        $scope.addUserDetails = function () {
            $scope.useraccount_id="";
            $scope.user_loginId="";
            $scope.userText="";
            $scope.AddressText="";
            $scope.FullNameText="";
            $scope.SecurityQuestionText="";
            $scope.AnswerText="";
            $scope.TelephoneNumberText="";
            $scope.EmailAddressText="";
            $scope.PasswordText="";
            $("#title").text("Add");
            $("#add_user_modal").modal('show');
        };


        $scope.removeuserDetails = function () {
            $scope.userEmailText = "";
            $scope.userNameText = "";
            $scope.pswdText = "";
            $scope.userText="";
            $scope.AddressText="";
            $scope.FullNameText="";
            $scope.SecurityQuestionText="";
            $scope.AnswerText="";
            $scope.TelephoneNumberText="";
            $scope.EmailAddressText="";
            $scope.PasswordText="";
        }
        $scope.isDisabled=true;




    });