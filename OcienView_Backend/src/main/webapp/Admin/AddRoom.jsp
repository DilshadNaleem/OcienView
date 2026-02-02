<%@page import = "org.Ocean_View.Connection.DatabaseConnection" %>
<%@page import = "java.sql.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Vehicle</title>
    <!-- Bootstrap CSS link -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .container {
            width: 40%;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #ffffff;
        }
        h1 {
            text-align: center;
            color: #0073bb;
        }
        label {
            font-weight: bold;
            font-size: 1rem;  /* Standard size for labels */
        }
        input, select, button {
            width: 100%;
            padding: 12px;  /* Increased padding for better visibility */
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1rem; /* Standard text size */
        }
        input::placeholder {
            font-size: 1rem; /* Ensures placeholder text follows standard size */
        }
        button {
            background-color: #0073bb;
            color: white;
            font-size: 16px;
            cursor: pointer;
            width: 150px;
            height: 40px;
            margin-top: 20px;
        }
        button:hover {
            background-color: #5196c1;
        }
        .back-btn {
            background-color: #0073bb;
            color: white;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            width: 150px;
            height: 40px;
            font-size: 14px;
            display: block;
            margin-top: 20px;
            text-align: center;
        }
        .back-btn:hover {
            background-color: #5196c1;
        }
        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #0073bb;
        }
        a:hover {
            color: #5196c1;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Add New Vehicle</h1>

        <form action="/Mega_City/VehicleSaveServlet" method="post" >
            <label for="vehicle_name">Vehicle Name:</label>
            <input type="text" id="vehicle_name" name="vehicle_name" placeholder="Enter Vehicle Name" required><br><br>

            <label for="vehicle_year">Vehicle Year:</label>
            <input type="text" id="vehicle_year" name="vehicle_year" placeholder="Enter Vehicle Year" required><br><br>

            <label for="brand_name">Brand Name:</label>
            <input type="text" id="brand_name" name="brand_name" placeholder="Enter Brand Name" required><br><br>

            <label for="condition">Condition:</label>
            <input type="text" id="condition" name="condition" placeholder="Enter Condition" required><br><br>

            <label for="mileage">Mileage:</label>
            <input type="text" id="mileage" name="mileage" placeholder="Enter Mileage" required><br><br>

            <label for="color">Color:</label>
            <input type="text" id="color" name="color" placeholder="Enter Vehicle Color" required><br><br>

            <label for="type">Vehicle Type:</label>
            <select id="type" name="type" required>
                <%
                Connection conn = null;
                PreparedStatement stmt = null;
                ResultSet rs = null;

                try {
                    conn = DatabaseConnection.getConnection(); // Get the database connection
                    String query = "SELECT roomCategory FROM room_type"; // Query to fetch vehicle categories
                    stmt = conn.prepareStatement(query);
                    rs = stmt.executeQuery();

                    while (rs.next()) {
                        String categoryName = rs.getString("roomCategory");
                %>
                    <option value="<%= categoryName %>"><%= categoryName %></option>
                <%
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        if (rs != null) rs.close();
                        if (stmt != null) stmt.close();
                        if (conn != null) conn.close();
                    } catch (SQLException ignore) {}
                }
                %>
            </select>
            <br><br>

            <label for="rent_per_day">Rent per Day:</label>
            <input type="text" id="rent_per_day" name="rent_per_day" placeholder="Enter Rent per Day" required><br><br>

            <label for="vehicle_image">Vehicle Image:</label>
            <input type="file" id="vehicle_image" name="vehicle_image" accept="image/*" required><br><br>

            <button type="submit">Submit</button>
        </form>

        <a href="/Mega_City/VehicleRetrieveServlet">View Added Vehicles</a>

        <!-- Back button -->
        <button class="back-btn" onclick="window.history.back();">Back</button>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>