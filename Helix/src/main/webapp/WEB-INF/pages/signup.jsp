 <html>
 <head>
    <link rel="shortcut icon" type="image/ico" href="/static/images/favicon.ico"/>
    <script src="https://code.jquery.com/jquery-3.5.0.min.js" integrity="sha256-xNzN2a4ltkB44Mc/Jz3pT4iU1cmeR0FkXs4pru/JxaQ=" crossorigin="anonymous"></script>
    <link rel="stylesheet" href="/static/css/signup.css">
 </head>
 <body>
     <section id="signup-box">
          <h1>Create Your Account</h1>
          <p>Sign Up</p>
          <form id="signup-form" method = "POST">
                <label for="name">Name</label><br>
                <input id="signup-name" type="text" name="name" placeholder="Enter Your Name"><br>
                <label for="email">Email</label><br>
                <input id="signup-email" type="email" name="email" placeholder="Enter Your Email"><br>
                <label for="password">Password</label><br>
                <input id="signup-password" type="password" name="name" placeholder="Enter Your Password"><br>
                <p style="color:red; display:none" id="signup-error"></p>
                <button type="button" id="btn-sign">Sign Up</button>
          </form>
     </section>
     <script type="text/javascript">
          function validateSignUpForm() {
                var name = $("#signup-name").val();
                var email = $("#signup-email").val();
                var password = $("#signup-password").val();
                var error = "";

                if(!name) {
                    error += "Name is empty!!";
                }
                if(!password) {
                    error += "Password is empty!!";
                }
                if(!email) {
                    error += "Email is empty!!\n";
                }
                if(password.length < 8) {
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
                        "name" : $("#signup-name").val(),
                        "email" : $("#signup-email").val(),
                        "password": $("#signup-password").val()
                    };
                    $.ajax({
                      type: "POST",
                      url: "/signup",
                      data: JSON.stringify(user),
                      success: function(response) {
                           if(!!response) {
                               if(response.user_created === true) {
                                    alert(response.message);
                               } else {
                                    alert(response.message);
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