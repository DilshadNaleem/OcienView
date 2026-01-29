<%@page import="java.util.Map" %>
<%@page import="java.util.HashMap" %>
<%@page import="org.Ocean_View.Customer.DTO.EditProfileRequest" %>
<%@page import="org.Ocean_View.Customer.Services.Implementations.CustomerAuthServiceImpl" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Profile</title>

    <!-- Bootstrap 4 CSS (or you can use the latest Bootstrap version) -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">

    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f4f4f9;
        }
        .container {
            max-width: 600px;
            margin: auto;
            padding: 30px;
            border-radius: 10px;
            background-color: white;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }
        h1 {
            text-align: center;
            color: #0073bb;
        }
        .error {
            color: red;
            text-align: center;
        }
        label {
            font-weight: bold;
            display: block;
            margin-top: 10px;
        }
        .form-control {
            width: 100%;
            padding: 10px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
        }
        button {
            background-color: #0073bb;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border: none;
            margin-top: 15px;
            padding: 10px;
            width: 100%;
            border-radius: 5px;
        }
        button:hover {
            background-color: #5196c1;
        }
        .alert {
            background-color: #0073bb;
            color: white;
            text-align: center;
            margin-bottom: 20px;
        }
        .back-button {
            background-color: #5196c1;
            color: white;
            font-size: 16px;
            cursor: pointer;
            border: none;
            padding: 10px;
            width: 100%;
            border-radius: 5px;
            margin-bottom: 20px;
        }
        .back-button:hover {
            background-color: #0073bb;
        }
    </style>
</head>

<body>
    <div class="container">
        <h1>Edit Profile</h1>

        <%-- Display error message --%>
        <% String errorMessage = (String) request.getAttribute("errorMessage"); %>
        <% if (errorMessage != null) { %>
            <p class="error"><%= errorMessage %></p>
        <% } %>

        <%-- Display success message as an alert --%>
        <% String successMessage = (String) request.getAttribute("successMessage"); %>
        <% if (successMessage != null) { %>
            <div class="alert alert-success">
                <%= successMessage %>
            </div>
        <% } %>

        <%
            Map<String, EditProfileRequest> profileDetails = (HashMap<String, EditProfileRequest>) request.getAttribute("profileDetails");
            EditProfileRequest profile = null;

            if (profileDetails != null && !profileDetails.isEmpty()) {
                profile = profileDetails.values().iterator().next(); // Get the first profile object
            }

            if (profile == null) {
        %>
            <p class="error">No profile details available. Please make sure you are logged in.</p>
        <% } else { %>

        <form action="EditProfile" method="post">
            <label>First Name:</label>
            <input type="text" class="form-control" name="firstName" value="<%= profile.getFirstName() %>" required>

            <label>Last Name:</label>
            <input type="text" class="form-control" name="lastName" value="<%= profile.getLastName() %>" required>

            <label>Email:</label>
            <input type="email" class="form-control" name="email" value="<%= profile.getEmail() %>" readonly>

            <label>Contact Number:</label>
            <input type="text" class="form-control" name="contactNumber" value="<%= profile.getContactnumber() %>" required pattern="[0-9]{10}" title="Please enter a valid 10-digit contact number">

            <label>NIC:</label>
            <input type="text" class="form-control" name="nic" value="<%= profile.getNic() %>" readonly>

            <label>Status:</label>
            <input type="text" class="form-control" name="status" value="<%= profile.getStatus() %>" readonly>

            <button type="submit" class="btn btn-primary">Update Profile</button>
        </form>

        <% } %>

        <!-- Back Button -->
        <button class="back-button" onclick="history.back()">Back</button>

    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.5.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>