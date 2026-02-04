<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="org.Ocean_View.Customer.Entity.Booking" %>
<%
    // Retrieve the booking object stored by the servlet
    Booking booking = (Booking) session.getAttribute("lastBooking");

    // Redirect to home if someone tries to access this page directly without a booking
    if (booking == null) {
        response.sendRedirect("/Customer/Signing.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Booking Confirmed - Ocean View</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <style>
        :root { --primary: #2c3e50; --success: #27ae60; --light: #f8f9fa; }
        body { font-family: 'Segoe UI', sans-serif; background: #eef2f3; display: flex; justify-content: center; padding: 40px 20px; }

        .receipt-card {
            background: white; width: 100%; max-width: 600px;
            border-radius: 15px; box-shadow: 0 15px 35px rgba(0,0,0,0.1); overflow: hidden;
        }

        .header { background: var(--success); color: white; padding: 30px; text-align: center; }
        .header i { font-size: 50px; margin-bottom: 10px; }

        .content { padding: 30px; }
        .section-title { border-bottom: 2px solid var(--light); padding-bottom: 10px; margin-bottom: 20px; color: var(--primary); font-size: 1.2rem; }

        .info-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 20px; margin-bottom: 30px; }
        .info-item label { display: block; color: #888; font-size: 0.85rem; text-transform: uppercase; margin-bottom: 5px; }
        .info-item span { font-weight: 600; color: var(--primary); }

        .total-banner { background: var(--light); padding: 20px; border-radius: 10px; text-align: center; margin-top: 20px; }
        .total-banner h2 { margin: 0; color: var(--success); }

        .actions { display: flex; gap: 10px; padding: 0 30px 30px; }
        .btn { flex: 1; padding: 12px; border-radius: 8px; text-align: center; text-decoration: none; font-weight: bold; transition: 0.3s; }
        .btn-print { background: var(--primary); color: white; border: none; cursor: pointer; }
        .btn-home { background: #dcdde1; color: var(--primary); }

        @media print { .actions, .header i { display: none; } .receipt-card { box-shadow: none; border: 1px solid #ccc; } }
    </style>
</head>
<body>

<div class="receipt-card">
    <div class="header">
        <i class="fas fa-check-circle"></i>
        <h1>Booking Confirmed!</h1>
        <p>Thank you, <%= booking.getCustomerFirstName() + " " + booking.getCustomerLastName()%></p>
    </div>

    <div class="content">
        <div class="section-title">Reservation Details</div>
        <div class="info-grid">
            <div class="info-item">
                <label>Booking ID</label>
                <span><%= booking.getUniqueId() %></span>
            </div>
            <div class="info-item">
                <label>Room ID</label>
                <span><%= booking.getRoomId() %></span>
            </div>
            <div class="info-item">
                <label>Category</label>
                <span><%= booking.getRoomCategory() %></span>
            </div>
            <div class="info-item">
                <label>Stay Duration</label>
                <span><%= booking.getNoOfDays() %> Night(s)</span>
            </div>
            <div class="info-item">
                <label>Check-In</label>
                <span><%= booking.getInDate() %></span>
            </div>
            <div class="info-item">
                <label>Check-Out</label>
                <span><%= booking.getOutDate() %></span>
            </div>
        </div>

        <div class="section-title">Payment Information</div>
        <div class="info-grid">
            <div class="info-item">
                <label>Payment ID</label>
                <span><%= booking.getPaymentUniqueId() %></span>
            </div>
            <div class="info-item">
                <label>Method</label>
                <span style="text-transform: capitalize;"><%= booking.getPaymentMethod() %></span>
            </div>
        </div>

        <div class="total-banner">
            <label>Total Amount Paid</label>
            <h2>LKR <%= String.format("%.2f", booking.getPrice()) %></h2>
        </div>
    </div>

    <div class="actions">
        <a href="/Customer/Dashboard" class="btn btn-home">Back to Home</a>
        <button onclick="window.print()" class="btn btn-print">
            <i class="fas fa-print"></i> Print Receipt
        </button>
    </div>
</div>

<%
    // Optional: Clear the session attribute so it doesn't show up again on refresh
    // session.removeAttribute("lastBooking");
%>

</body>
</html>