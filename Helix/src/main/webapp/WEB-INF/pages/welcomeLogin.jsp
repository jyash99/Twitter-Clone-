 <html>
 <head>
    <link rel="shortcut icon" type="image/ico" href="/static/images/favicon.ico"/>
    <script src="https://code.jquery.com/jquery-3.5.0.min.js" integrity="sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="/static/css/signup.css">
 </head>
 <body>
     <section id="signup-box">
          <h1>Login Your Account</h1>
          <p>Sign In</p>
          <form id="signup-form" method = "POST">
                <label for="email">Email</label><br>
                <input id="signup-email" type="email" name="email" placeholder="Enter Your Email"><br>
                <label for="password">Password</label><br>
                <input id="signup-password" type="password" name="name" placeholder="Enter Your Password"><br>
                <p style="color:red; display:none; text-align: center;" id="signup-error"></p>
                <button type="button" id="btn-sign">Login</button>
          </form>
     </section>
     <script type="text/javascript">
          function validateSignUpForm() {
                var email = $("#signup-email").val();
                var password = $("#signup-password").val();
                var error = "";

                if(!password) {
                    error += "Password is empty!!";
                    error += "<br>";
                }
                if(!email) {
                    error += "Email is empty!!";
                }
                if(password.length < 8 && password.length != 0) {
                    error += "Password must be of atleast 8 characters";
                }
                $("#signup-error").html(error);

                if(error.length > 0) {
                    return false;
                }
                return true;

          }
          $("#btn-sign").click(function(){
                var isFormValid = validateSignUpForm();
                if(isFormValid) {
                    $("#signup-error").hide();
                    var user = {
                        "email" : $("#signup-email").val(),
                        "password": $("#signup-password").val()
                    };
                    $.ajax({
                      type: "POST",
                      url: "/login/welcome",
                      data: JSON.stringify(user),
                      success: function(response) {
                           if(!!response) {
                               if(response.is_Login === true) {
                                    location.href = "/welcome";
                               } else {
                                    var email = $("#signup-email").val("");
                                    var password = $("#signup-password").val("");
                                    $("#signup-error").html("Wrong Combination");
                                    $("#signup-error").show();
                               }
                           }
                      },
                      contentType: "application/json"
                    });
                } else {
                    $("#signup-error").show();
                }
          });
     </script>
</body>
</html>