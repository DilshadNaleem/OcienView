<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
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
            --fine-color: #dc2626;
            --warning-color: #f59e0b;
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
            position: relative;
        }

        .booking-card:hover { transform: translateY(-5px); }

        .card-header {
            display: flex;
            justify-content: space-between;
            align-items: flex-start;
            margin-bottom: 20px;
        }

        .room-info h3 {
            color: var(--darker-blue);
            margin: 0;
            margin-bottom: 5px;
        }

        .room-id {
            font-size: 0.9rem;
            color: var(--medium-blue);
            font-weight: 600;
            margin: 5px 0;
        }

        .room-info small {
            color: #666;
            font-size: 0.8rem;
            display: block;
            margin-top: 3px;
        }

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

        .data-item span {
            font-size: 0.95rem;
            font-weight: 500;
            word-break: break-word;
        }

        .payment-info {
            background: rgba(255, 255, 255, 0.5);
            padding: 15px;
            border-radius: 8px;
            margin-top: auto;
            border-left: 4px solid var(--button-color);
        }

        .status-pill {
            padding: 5px 12px;
            border-radius: 20px;
            font-size: 0.75rem;
            font-weight: 700;
            min-width: 80px;
            text-align: center;
        }

        .confirmed { background: #dcfce7; color: #166534; }
        .pending { background: #fef9c3; color: #854d0e; }
        .completed { background: #dbeafe; color: #1e40af; }
        .cancelled { background: #fee2e2; color: #991b1b; }

        .fine-badge {
            display: inline-block;
            background: rgba(220, 38, 38, 0.1);
            color: var(--fine-color);
            padding: 3px 8px;
            border-radius: 12px;
            font-size: 0.7rem;
            font-weight: 600;
            margin-top: 5px;
            border: 1px solid rgba(220, 38, 38, 0.2);
        }

        .overdue-section {
            background: rgba(220, 38, 38, 0.05);
            padding: 12px;
            border-radius: 8px;
            margin-top: 10px;
            border-left: 3px solid var(--fine-color);
        }

        .overdue-section label {
            color: var(--fine-color);
        }

        .overdue-section span {
            color: var(--fine-color);
            font-weight: 700;
        }

        .fine-breakdown {
            font-size: 0.75rem;
            color: #666;
            margin-top: 3px;
            font-style: italic;
        }

        @media (max-width: 768px) {
            .controls-container { flex-direction: column; }
            .history-grid { grid-template-columns: 1fr; }
        }
    </style>
</head>
<body>

<div style="text-align: center; margin: 40px 0;">
    <h1 style="color: var(--darker-blue); margin-bottom: 10px;">Ocean View Booking History</h1>
    <c:if test="${not empty bookingHistory && bookingHistory.size() > 0}">
        <p style="font-size: 1.1rem; color: var(--font-color);">
            Welcome, ${bookingHistory[0].customerFirstName} ${bookingHistory[0].customerLastName}
        </p>
    </c:if>
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
        <option value="completed">Completed</option>
        <option value="cancelled">Cancelled</option>
    </select>
</div>

<div class="history-grid" id="historyGrid">
    <c:choose>
        <c:when test="${empty bookingHistory || bookingHistory.size() == 0}">
            <div style="grid-column: 1 / -1; text-align: center; padding: 40px; background: rgba(255, 255, 255, 0.7); border-radius: var(--border-radius);">
                <i class="fas fa-calendar-times" style="font-size: 3rem; color: var(--medium-blue); margin-bottom: 20px;"></i>
                <h3 style="color: var(--darker-blue);">No Booking History Found</h3>
                <p style="color: var(--font-color);">You haven't made any bookings yet.</p>
            </div>
        </c:when>
        <c:otherwise>
            <c:forEach var="booking" items="${bookingHistory}">
                <div class="booking-card"
                     data-status="${booking.bookingStatus.toLowerCase()}"
                     data-searchable="${booking.roomCategory} ${booking.uniqueId} ${booking.bookingStatus} ${booking.roomId}">

                    <div class="card-header">
                        <div class="room-info">
                            <h3>${booking.roomCategory} Room</h3>
                            <c:if test="${not empty booking.roomId}">
                                <div class="room-id">Room ID: ${booking.roomId}</div>
                            </c:if>
                            <small>Booking ID: #${booking.uniqueId}</small>

                            <!-- Show overdue badge -->
                            <c:if test="${booking.overdueDays != null && booking.overdueDays > 0}">
                                <div class="fine-badge">
                                    <i class="fas fa-clock"></i> Overdue: ${booking.overdueDays} day(s)
                                </div>
                            </c:if>
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
                            <label>Email</label>
                            <span>${booking.customerEmail}</span>
                        </div>
                        <c:if test="${not empty booking.phoneNumber}">
                            <div class="data-item">
                                <label>Phone</label>
                                <span>${booking.phoneNumber}</span>
                            </div>
                        </c:if>
                        <div class="data-item">
                            <label><i class="far fa-calendar-check"></i> Check-In</label>
                            <span>${booking.inDate}</span>
                        </div>
                        <div class="data-item">
                            <label><i class="far fa-calendar-times"></i> Check-Out</label>
                            <span>${booking.outDate}</span>
                        </div>
                        <div class="data-item">
                            <label>Duration</label>
                            <span>${booking.noOfDays} Days</span>
                        </div>
                        <div class="data-item">
                            <label>Total Price</label>
                            <span style="font-weight: 700; color: var(--darker-blue);">
                                <fmt:formatNumber value="${booking.price}" type="currency" currencyCode="LKR" />
                            </span>
                        </div>

                        <!-- Show overdue information if applicable -->
                        <c:if test="${booking.overdueDays != null && booking.overdueDays > 0}">
                            <div class="overdue-section" style="grid-column: span 2;">
                                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 10px;">
                                    <div class="data-item">
                                        <label style="color: var(--fine-color);">Overdue Days</label>
                                        <span style="color: var(--fine-color); font-weight: 700;">
                                            ${booking.overdueDays} day(s)
                                        </span>
                                    </div>
                                    <div class="data-item">
                                        <label style="color: var(--fine-color);">Fine Rate/Day</label>
                                        <span style="color: var(--fine-color); font-weight: 700;">
                                            <fmt:formatNumber value="${booking.dailyFine}" type="currency" currencyCode="LKR" />
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </c:if>
                    </div>

                    <div class="payment-info">
                        <div class="data-item" style="display: flex; justify-content: space-between; align-items: center;">
                            <div>
                                <label>Payment Method</label>
                                <div style="font-weight: 600; color: var(--darker-blue);">
                                    <i class="fas fa-credit-card"></i> ${booking.paymentMethod}
                                </div>
                                <c:if test="${not empty booking.paymentUniqueId}">
                                    <span style="font-size: 0.75rem; color: #666;">
                                        Reference: ${booking.paymentUniqueId}
                                    </span>
                                </c:if>
                            </div>

                            <!-- Show fine information -->
                            <c:if test="${not empty booking.fine && booking.fine != '0' && booking.fine != '0.0'}">
                                <div style="text-align: right;">
                                    <label style="color: var(--fine-color); font-weight: 700;">Total Fine</label>
                                    <span style="color: var(--fine-color); font-weight: 800; font-size: 1.1rem;">
                                        <fmt:formatNumber value="${booking.fine}" type="currency" currencyCode="LKR" />
                                    </span>
                                    <c:if test="${booking.calculatedFine != null && booking.overdueDays > 0}">
                                        <div class="fine-breakdown">
                                            (${booking.overdueDays} days ×
                                            <fmt:formatNumber value="${booking.dailyFine}" type="currency" currencyCode="LKR" />)
                                        </div>
                                    </c:if>
                                </div>
                            </c:if>
                        </div>

                        <!-- Show calculated fine details -->
                        <c:if test="${booking.calculatedFine != null && booking.calculatedFine > 0}">
                            <div style="margin-top: 8px; padding-top: 8px; border-top: 1px dashed rgba(0,0,0,0.1);">
                                <div style="display: flex; justify-content: space-between; font-size: 0.85rem;">
                                    <span style="color: #666;">Calculated Overdue Fine:</span>
                                    <span style="color: var(--fine-color); font-weight: 600;">
                                        <fmt:formatNumber value="${booking.calculatedFine}" type="currency" currencyCode="LKR" />
                                    </span>
                                </div>
                            </div>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
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

    // Initialize filter on page load
    document.addEventListener('DOMContentLoaded', function() {
        filterHistory();
    });
</script>

</body>
</html>