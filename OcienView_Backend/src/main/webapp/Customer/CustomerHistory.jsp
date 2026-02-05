<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>My Stay History | Ocean View</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css">
    <style>
        :root {
            --light-blue-1: rgb(214, 235, 239);
            --light-blue-2: rgb(232, 244, 246);
            --medium-blue: rgb(106, 160, 171);
            --darker-blue: rgb(90, 130, 140);
            --font-color: #1e4d50;
            --button-color: #0073bb;
            --button-hover-color: #5196c1;
            --border-radius: 15px;
        }

        @keyframes gradientAnimation {
            0% { background-position: 0% 50%; }
            50% { background-position: 100% 50%; }
            100% { background-position: 0% 50%; }
        }

        body {
            font-family: 'Poppins', sans-serif;
            background: linear-gradient(135deg, var(--light-blue-1), var(--light-blue-2), var(--medium-blue), var(--darker-blue));
            background-size: 400% 400%;
            background-attachment: fixed;
            animation: gradientAnimation 12s ease infinite;
            color: var(--font-color);
            min-height: 100vh;
            padding: 20px;
        }

        /* Search & Filter Section */
        .controls-container {
            max-width: 1300px;
            margin: 40px auto 20px;
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            background: rgba(255, 255, 255, 0.4);
            backdrop-filter: blur(10px);
            padding: 20px;
            border-radius: var(--border-radius);
            border: 1px solid rgba(255, 255, 255, 0.3);
        }

        .search-box {
            flex: 2;
            min-width: 50px;
            position: relative;
        }

        .search-box input {
            width: 80%;
            padding: 12px 40px;
            border-radius: 25px;
            border: none;
            outline: none;
            background: white;
            box-shadow: 0 4px 6px rgba(0,0,0,0.05);
        }

        .search-box i {
            position: absolute;
            left: 15px;
            top: 50%;
            transform: translateY(-50%);
            color: var(--medium-blue);
        }

        .filter-select {
            flex: 1;
            min-width: 150px;
            padding: 10px;
            border-radius: 25px;
            border: none;
            background: white;
            color: var(--font-color);
            cursor: pointer;
        }

        /* Grid */
        .history-grid {
            display: grid;
            grid-template-columns: repeat(auto-fill, minmax(350px, 1fr));
            gap: 25px;
            max-width: 1300px;
            margin: 0 auto;
        }

        /* Enhanced Card */
        .booking-card {
            background: rgba(255, 255, 255, 0.7);
            backdrop-filter: blur(15px);
            border-radius: var(--border-radius);
            padding: 25px;
            border: 1px solid rgba(255, 255, 255, 0.5);
            transition: transform 0.3s ease;
            display: flex;
            flex-direction: column;
        }

        .booking-card:hover { transform: translateY(-5px); }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 20px;
        }

        .room-info h3 { color: var(--darker-blue); margin: 0; }
        .room-info small { color: #666; font-size: 0.8rem; }

        .data-grid {
            display: grid;
            grid-template-columns: 1fr 1fr;
            gap: 15px;
            margin-bottom: 20px;
        }

        .data-item label {
            display: block;
            font-size: 0.75rem;
            text-transform: uppercase;
            color: var(--medium-blue);
            font-weight: 700;
        }

        .data-item span { font-size: 0.95rem; font-weight: 500; }

        .payment-info {
            background: rgba(255, 255, 255, 0.5);
            padding: 10px;
            border-radius: 8px;
            margin-top: auto;
            border-left: 4px solid var(--button-color);
        }

        .status-pill {
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.75rem;
            font-weight: 700;
        }

        .confirmed { background: #dcfce7; color: #166534; }
        .pending { background: #fef9c3; color: #854d0e; }

        @media (max-width: 768px) {
            .controls-container { flex-direction: column; }
        }
    </style>
</head>
<body>

<div style="text-align: center; margin: 40px 0;">
    <h1>Ocean View History</h1>
    <p>Welcome, ${bookingHistory[0].customerFirstName}  ${bookingHistory[00].customerLastName}</p>
</div>

<div class="controls-container">
    <div class="search-box">
        <i class="fas fa-search"></i>
        <input type="text" id="searchInput" placeholder="Search by Room, ID, or Status..." onkeyup="filterHistory()">
    </div>
    <select class="filter-select" id="statusFilter" onchange="filterHistory()">
        <option value="all">All Statuses</option>
        <option value="confirmed">Confirmed</option>
        <option value="pending">Pending</option>
    </select>
</div>

<div class="history-grid" id="historyGrid">
    <c:forEach var="booking" items="${bookingHistory}">
        <div class="booking-card" data-status="${booking.bookingStatus.toLowerCase()}" data-searchable="${booking.roomCategory} ${booking.uniqueId} ${booking.bookingStatus}">

            <div class="card-header">
                <div class="room-info">
                    <h3>${booking.roomCategory}</h3>
                    <small>ID: #${booking.uniqueId}</small>
                </div>
                <span class="status-pill ${booking.bookingStatus.toLowerCase()}">
                    ${booking.bookingStatus}
                </span>
            </div>

            <div class="data-grid">
                <div class="data-item">
                    <label>Guest Name</label>
                    <span>${booking.customerFirstName} ${booking.customerLastName}</span>
                </div>
                <div class="data-item">
                    <label>Phone</label>
                    <span>${booking.phoneNumber}</span>
                </div>
                <div class="data-item">
                    <label>Check-In</label>
                    <span><i class="far fa-calendar-check"></i> ${booking.inDate}</span>
                </div>
                <div class="data-item">
                    <label>Check-Out</label>
                    <span><i class="far fa-calendar-times"></i> ${booking.outDate}</span>
                </div>
                <div class="data-item">
                    <label>Duration</label>
                    <span>${booking.noOfDays} Days</span>
                </div>
                <div class="data-item">
                    <label>Total Price</label>
                    <span style="font-weight: 700; color: var(--darker-blue);">LKR ${booking.price}</span>
                </div>
            </div>

            <div class="payment-info">
                <div class="data-item" style="display: flex; justify-content: space-between; align-items: center;">
                    <div>
                        <label> </label>
                        <label>Payment via ${booking.paymentMethod}</label>
                        <span style="font-size: 0.8rem; opacity: 0.7;">Ref: ${booking.paymentUniqueId}</span>
                    </div>
                    <c:if test="${not empty booking.fine}">
                        <div style="text-align: right;">
                            <label style="color: #b91c1c;">Fine/Extras</label>
                            <span style="color: #b91c1c;">LKR ${booking.fine}</span>
                        </div>
                    </c:if>
                </div>
            </div>
        </div>
    </c:forEach>
</div>

<script>
    function filterHistory() {
        const query = document.getElementById('searchInput').value.toLowerCase();
        const status = document.getElementById('statusFilter').value.toLowerCase();
        const cards = document.getElementsByClassName('booking-card');

        for (let card of cards) {
            const content = card.getAttribute('data-searchable').toLowerCase();
            const cardStatus = card.getAttribute('data-status');

            const matchesSearch = content.includes(query);
            const matchesStatus = (status === 'all' || cardStatus === status);

            if (matchesSearch && matchesStatus) {
                card.style.display = "flex";
            } else {
                card.style.display = "none";
            }
        }
    }
</script>

</body>
</html>