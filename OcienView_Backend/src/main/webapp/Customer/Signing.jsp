<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <title>Customer Login Form</title>
  <link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.14.0/css/all.min.css'>
  <link rel="stylesheet" href="./css/style.css">
  <style>
    /* Add styles for password field containers */
    .password-container {
      position: relative;
      width: 100%;
    }

    .password-container input {
      width: 100%;
      padding-right: 40px; /* Make room for the eye icon */
    }

    .toggle-password {
      position: absolute;
      right: 10px;
      top: 50%;
      transform: translateY(-50%);
      cursor: pointer;
      color: #777;
    }

    .toggle-password:hover {
      color: #333;
    }
  </style>
</head>
<body>

<div class="container" id="container">
	<!-- Sign-Up Container -->
	<div class="form-container sign-up-container">
		<form id="signupForm" action="/Customer/Register" method="POST">
			<h1>Create Account</h1><br>
			<div class="social-container">
				<a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
				<a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
				<a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
			</div>
			<span>or use your email for registration</span>
			<input type="text" id="firstName" name="firstname" placeholder="First Name" required />
			<input type="text" id="lastName" name="lastname" placeholder="Last Name" required />
			<input type="email" id="email" name="email" placeholder="Email" required />

			<!-- Password field with eye icon -->
			<div class="password-container">
				<input type="password" id="password" name="password" placeholder="Password" required />
				<i class="fas fa-eye toggle-password" onclick="togglePasswordVisibility('password', this)"></i>
			</div>

			<!-- Confirm Password field with eye icon -->
			<div class="password-container">
				<input type="password" id="confirmPassword" name="confirmpassword" placeholder="Confirm Password" required />
				<i class="fas fa-eye toggle-password" onclick="togglePasswordVisibility('confirmPassword', this)"></i>
			</div>

			<input type="text" id="contactNumber" name="contact_number" placeholder="Contact Number" required />
      <input type="text" id="nic" name="nic" placeholder="NIC" required />
			<button type="submit" name="register" value="register">Sign Up</button>
		</form>
	</div>

	<!-- Sign-In Container -->
	<div class="form-container sign-in-container">
		<form id="signinForm" action="/Customer/Login" method="post">
			<h1>Sign in</h1><br>
			<div class="social-container">
				<a href="#" class="social"><i class="fab fa-facebook-f"></i></a>
				<a href="#" class="social"><i class="fab fa-google-plus-g"></i></a>
				<a href="#" class="social"><i class="fab fa-linkedin-in"></i></a>
			</div>
			<span>or use your account</span>
			<input type="email" id="signinEmail" name="email" placeholder="Email" required />

			<!-- Sign-in Password field with eye icon -->
			<div class="password-container">
				<input type="password" id="signinPassword" name="password" placeholder="Password" required />
				<i class="fas fa-eye toggle-password" onclick="togglePasswordVisibility('signinPassword', this)"></i>
			</div>

			<a href="recover_psw.html">Forgot your password?</a>
			<button type="submit">Sign In</button>
		</form>
	</div>

	<!-- Overlay Container -->
	<div class="overlay-container">
		<div class="overlay">
			<div class="overlay-panel overlay-left">
				<h1>Welcome Back!</h1><br>
				<p>To keep connected with us please login with your personal info</p><br>
				<button class="ghost" id="signIn">Sign In</button>
			</div>
			<div class="overlay-panel overlay-right">
				<h1>Hello, Friend!</h1><br>
				<p>Enter your personal details and start journey with us</p><br>
				<button class="ghost" id="signUp">Sign Up</button>
			</div>
		</div>
	</div>
</div>

<footer>
    <p>
        Brought to you by <a href="https://Mega-City.com" target="_blank">Ocean View Resort</a> - Your go-to Ocean View for all your
        Resort Room needs.
    </p>
</footer>

<!-- Scripts -->
<script>
// Function to toggle password visibility
function togglePasswordVisibility(inputId, iconElement) {
    const passwordInput = document.getElementById(inputId);

    // Toggle input type
    if (passwordInput.type === 'password') {
        passwordInput.type = 'text';
        iconElement.classList.remove('fa-eye');
        iconElement.classList.add('fa-eye-slash');
    } else {
        passwordInput.type = 'password';
        iconElement.classList.remove('fa-eye-slash');
        iconElement.classList.add('fa-eye');
    }
}

const signUpButton = document.getElementById('signUp');
const signInButton = document.getElementById('signIn');
const container = document.getElementById('container');

signUpButton.addEventListener('click', () => {
	container.classList.add("right-panel-active");
});

signInButton.addEventListener('click', () => {
	container.classList.remove("right-panel-active");
});

document.getElementById("signupForm").addEventListener("submit", function (event) {
    let isValid = true;

    // Validate first name
    const firstName = document.getElementById("firstName").value.trim();
    if (!/^[A-Za-z]{2,}$/.test(firstName)) {
        alert("First name must be at least 2 characters and contain only letters.");
        isValid = false;
    }

    // Validate last name
    const lastName = document.getElementById("lastName").value.trim();
    if (!/^[A-Za-z]{2,}$/.test(lastName)) {
        alert("Last name must be at least 2 characters and contain only letters.");
        isValid = false;
    }

    // Validate email
    const email = document.getElementById("email").value.trim();
    if (!/^[^\s@]+@[^\s@]+\.[^\s@]+$/.test(email)) {
        alert("Please enter a valid email address.");
        isValid = false;
    }

    // Validate contact number
    const contactNumber = document.getElementById("contactNumber").value.trim();
    if (!/^\d{10}$/.test(contactNumber)) {
        alert("Contact number must be 10 digits.");
        isValid = false;
    }

    // Validate password and confirm password
    const password = document.getElementById("password").value;
    const confirmPassword = document.getElementById("confirmPassword").value;
    if (password.length < 6) {
        alert("Password must be at least 6 characters long.");
        isValid = false;
    }
    if (password !== confirmPassword) {
        alert("Passwords do not match.");
        isValid = false;
    }

    // Prevent form submission if any validation fails
    if (!isValid) {
        event.preventDefault();
    }
});

</script>

</body>
</html>