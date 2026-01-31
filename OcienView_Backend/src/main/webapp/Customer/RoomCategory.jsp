<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Vehicle Category</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
      /* General Styles */
      body {
            background: #85C1FF; /* Lighter Blue Background */
            font-family: Arial, sans-serif;
            color: #333; /* Darker text for better readability */
            overflow-x: hidden;
        }

        /* Header */
        h1 {
            color: #333; /* Dark text for contrast */
            font-size: 2.5rem;
            font-weight: bold;
            margin-bottom: 30px;
        }

        /* Vehicle Cards */
       /* Vehicle Card */
.vehicle-card {
    border: 1px solid #ddd;
    border-radius: 10px;
    padding: 20px;
    background-color: #ffffff;
    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
    transition: all 0.3s ease;
    margin-bottom: 20px;
    max-width: 300px; /* Adjust the max-width to make the card smaller */
    margin: 0 auto; /* Center the card horizontally */
}

        .vehicle-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 6px 12px rgba(0, 0, 0, 0.15);
        }

        /* Vehicle Image */
        .vehicle-image {
            max-width: 80%;
            height: auto;
            border-radius: 8px;
            margin-bottom: 15px;
            border: 2px solid #ddd;
            transition: all 0.3s ease;
        }

        .vehicle-image:hover {
            transform: scale(1.05);
        }

        /* Vehicle Name and Info */
        .vehicle-card h3 {
            font-size: 1.25rem;
            font-weight: bold;
            color: #007bff;
        }

        .vehicle-card p {
            font-size: 1rem;
            color: #555;
            margin-bottom: 10px;
        }

        /* Rent Price */
        .rentperday {
            font-size: 1.1rem;
            color: #28a745;
            font-weight: bold;
        }

        /* Footer */
        .vehicle-card-footer {
            margin-top: 10px;
        }

        .book-now {
            font-size: 1rem;
            font-weight: bold;
            background-color: #007bff;
            color: #fff;
            border-radius: 5px;
            padding: 10px 20px;
            transition: all 0.3s ease;
            width: 100%;
        }

        .book-now:hover {
            background-color: #0056b3;
            transform: scale(1.05);
        }

        .book-now:focus {
            outline: none;
        }

        /* Modal Styles */
        .modal-content {
            border-radius: 10px;
        }

        .modal-header {
            background-color: #007bff;
            color: #fff;
        }

        .modal-title {
            font-size: 1.5rem;
            font-weight: bold;
        }

        .modal-body {
            padding: 25px;
        }

        .modal-body .form-label {
            font-weight: bold;
        }

        .modal-body input {
            margin-bottom: 15px;
            font-size: 1rem;
            border-radius: 5px;
            border: 1px solid #ccc;
            padding: 10px;
            width: 100%;
        }

        .modal-body input:focus {
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        }

        /* Button Styles */
        .btn-success {
            font-size: 1.1rem;
            font-weight: bold;
            background-color: #28a745;
            color: #fff;
            border-radius: 5px;
            padding: 10px 20px;
        }

        .btn-success:hover {
            background-color: #218838;
        }

        /* Input Fields */
        input[type="date"],
        input[type="time"] {
            width: 100%;
            padding: 10px;
            margin-bottom: 15px;
            border-radius: 5px;
            border: 1px solid #ccc;
            font-size: 1rem;
        }

        input[type="date"]:focus,
        input[type="time"]:focus {
            border-color: #007bff;
            box-shadow: 0 0 5px rgba(0, 123, 255, 0.5);
        }

        /* Container */
        .container {
            padding-top: 50px;
            padding-bottom: 50px;
        }

        /* Search Form Styles */
        .search-form {
            display: flex;
            justify-content: space-between;
            align-items: center;
        }

        .search-form input {
            flex: 1;
            margin-right: 10px;
        }

        .search-form button {
            flex-shrink: 0;
            background-color: #0056b3; /* Blue search button */
            color: #fff;
            padding: 10px 20px;
            border: none;
            border-radius: 5px;
            transition: background-color 0.3s ease;
        }

        .search-form button:hover {
            background-color: #003c82; /* Darker blue on hover */
        }
    </style>
</head>
<body>
    <div class="container mt-4">
        <h1>Vehicle Category: <%= request.getParameter("vehicleType") %></h1>

        <form action="ProductServlet" method="get" class="search-form mb-4">
            <input type="hidden" name="vehicleType" value="${vehicleType}" />
            <input type="text" name="searchQuery" placeholder="Search by name, brand, or year" class="form-control" />
            <button type="submit" class="btn btn-primary mt-2">Search</button>
        </form>
        <div class="row">
            <%
                List<Map<String, String>> vehicleDetails = (List<Map<String, String>>) request.getAttribute("vehicleDetails");
                if (vehicleDetails == null || vehicleDetails.isEmpty()) {
            %>
                <p>No vehicles found for this category.</p>
            <%
                } else {
                    for (Map<String, String> vehicle : vehicleDetails) {
                        String vehicleName = vehicle.get("vehicle_name");
                        String vehicleYear = vehicle.get("vehicle_year");
                        String brandName = vehicle.get("brand_name");
                        String fullImagePath = vehicle.get("vehicle_image_path");
                        String vehicleCondition = vehicle.get("vehicle_condition");
                        String mileage = vehicle.get("mileage");
                        String rentperday = vehicle.get("rent_per_day");
                        String color = vehicle.get("color");
            %>
                <div class="col-md-4">
                    <div class="vehicle-card">
                        <% if (fullImagePath != null && !fullImagePath.isEmpty()) { %>
                            <img src="<%= fullImagePath %>" alt="Vehicle Image" class="vehicle-image">
                        <% } else { %>
                            <img src="default-image.png" alt="Default Vehicle Image" class="vehicle-image">
                        <% } %>
                        <h3><%= vehicleName %></h3>
                        <p><strong>Year:</strong> <%= vehicleYear %></p>
                        <p><strong>Brand:</strong> <%= brandName %></p>
                        <p><strong>Condition:</strong> <%= vehicleCondition %></p>
                        <p><strong>Mileage:</strong> <%= mileage %></p>
                        <p><strong>Rent per day:</strong> <span class="rentperday"><%= rentperday %></span></p>
                        <p><strong>Color:</strong> <%= color %></p>
                        <div class="vehicle-card-footer">
                            <button class="btn btn-primary book-now" data-bs-toggle="modal" data-bs-target="#bookingModal"
                                    data-vehicle="<%= vehicleName %>" data-rent="<%= rentperday %>">
                                Book Now
                            </button>
                        </div>
                    </div>
                </div>
            <%
                    }
                }
            %>
        </div>
    </div>

    <!-- Modal for booking -->
    <div class="modal fade" id="bookingModal" tabindex="-1" aria-labelledby="bookingModalLabel" aria-hidden="true">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <h5 class="modal-title" id="bookingModalLabel">Book <span id="vehicleName"></span></h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <form action="bookVehicleServlet" method="POST">
                        <input type="hidden" name="vehicleName" id="hiddenVehicleName">
                        <input type="hidden" name="rentperday" id="hiddenRentPerDay">
                        <div class="mb-3">
                            <label for="bookingDate" class="form-label">Booking Date</label>
                            <input type="date" class="form-control" name="bookingDate" id="bookingDate" required>
                        </div>
                        <div class="mb-3">
                            <label for="bookingTime" class="form-label">Booking Time</label>
                            <input type="time" class="form-control" name="bookingTime" required>
                        </div>
                        <div class="mb-3">
                            <label for="returndate" class="form-label">Return Date</label>
                            <input type="date" class="form-control" name="returndate" id="returndate" required>
                        </div>
                        <div class="mb-3">
                            <label for="totalPrice" class="form-label">Total Price</label>
                            <input type="text" class="form-control" name="totalPrice" id="totalPrice" readonly>
                        </div>
                        <button type="submit" class="btn btn-success">Confirm Booking</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.querySelectorAll('.book-now').forEach(button => {
            button.addEventListener('click', function() {
                var vehicleName = this.getAttribute('data-vehicle');
                var rentPerDay = this.getAttribute('data-rent');

                document.getElementById('vehicleName').textContent = vehicleName;
                document.getElementById('hiddenVehicleName').value = vehicleName;
                document.getElementById('hiddenRentPerDay').value = rentPerDay;
            });
        });

        document.getElementById('bookingDate').addEventListener('change', calculateTotalPrice);
        document.getElementById('returndate').addEventListener('change', calculateTotalPrice);

        function calculateTotalPrice() {
            var bookingDateValue = document.getElementById('bookingDate').value;
            var returnDateValue = document.getElementById('returndate').value;
            var rentPerDay = parseFloat(document.getElementById('hiddenRentPerDay').value);

            if (bookingDateValue && returnDateValue) {
                var bookingDate = new Date(bookingDateValue);
                var returnDate = new Date(returnDateValue);

                if (returnDate > bookingDate) {
                    var timeDiff = returnDate.getTime() - bookingDate.getTime();
                    var dayDiff = Math.ceil(timeDiff / (1000 * 3600 * 24));
                    var totalPrice = dayDiff * rentPerDay;
                    document.getElementById('totalPrice').value = totalPrice.toFixed(2);
                } else {
                    alert('Return date must be after the booking date.');
document.getElementById('returndate').value = ''; // Clear the return date field
document.getElementById('totalPrice').value = ''; // Clear total price
document.getElementById('returndate').focus(); // Focus on return date field

                }
            }
        }
    </script>
</body>
</html>
