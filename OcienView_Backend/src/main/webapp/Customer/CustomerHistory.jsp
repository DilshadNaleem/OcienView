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
    <link rel="stylesheet" href="./css/CustomerHistory.css">
    <style>
        /* Cancel Button Styles */
        .cancel-btn {
            background: linear-gradient(135deg, #ff6b6b, #ff4757);
            color: white;
            border: none;
            padding: 8px 16px;
            border-radius: 6px;
            font-weight: 600;
            font-size: 0.9rem;
            cursor: pointer;
            transition: all 0.3s ease;
            display: flex;
            align-items: center;
            gap: 6px;
            margin-top: 10px;
            box-shadow: 0 2px 4px rgba(255, 107, 107, 0.2);
        }

        .cancel-btn:hover {
            background: linear-gradient(135deg, #ff4757, #ff3838);
            transform: translateY(-2px);
            box-shadow: 0 4px 8px rgba(255, 107, 107, 0.3);
        }

        .cancel-btn:active {
            transform: translateY(0);
            box-shadow: 0 1px 2px rgba(255, 107, 107, 0.2);
        }

        .cancel-btn:disabled {
            background: #cccccc;
            cursor: not-allowed;
            transform: none;
            box-shadow: none;
        }

        .cancel-btn i {
            font-size: 0.9rem;
        }
    </style>
</head>
<body>

<!-- Cancel Booking Modal -->
<div class="modal-overlay" id="cancelModal" style="display: none;">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Cancel Booking</h3>
            <button class="close-modal" onclick="closeCancelModal()">&times;</button>
        </div>
        <form id="cancelForm" action="${pageContext.request.contextPath}/Customer/CancelRooms" method="post">
            <input type="hidden" id="cancelBookingId" name="bookingId">
            <input type="hidden" id="cancelRoomId" name="roomId">
            <input type="hidden" id="cancelCustomerEmail" name="customerEmail">

            <div style="padding: 20px 0;">
                <div style="text-align: center; margin-bottom: 20px;">
                    <i class="fas fa-exclamation-triangle" style="font-size: 3rem; color: #ff6b6b;"></i>
                </div>
                <h4 style="text-align: center; color: var(--darker-blue); margin-bottom: 10px;">
                    Confirm Booking Cancellation
                </h4>
                <p style="color: var(--font-color); text-align: center; line-height: 1.5;">
                    Are you sure you want to cancel this booking?
                    This action cannot be undone. Any applicable cancellation fees may apply.
                </p>
            </div>

            <div style="display: flex; gap: 10px; margin-top: 20px;">
                <button type="button" class="cancel-btn" onclick="closeCancelModal()"
                        style="background: #666; flex: 1;">
                    <i class="fas fa-times"></i> No, Keep Booking
                </button>
                <button type="submit" class="cancel-btn" style="flex: 1;">
                    <i class="fas fa-check"></i> Yes, Cancel Booking
                </button>
            </div>
        </form>
    </div>
</div>

<!-- Payment Modal -->
<div class="modal-overlay" id="paymentModal">
    <div class="modal-content">
        <div class="modal-header">
            <h3>Pay Overdue Fine</h3>
            <button class="close-modal" onclick="closeModal()">&times;</button>
        </div>

        <form id="paymentForm" action="${pageContext.request.contextPath}/Customer/PayOverdueFine" method="post">
            <input type="hidden" id="bookingId" name="bookingId">
            <input type="hidden" id="customerEmail" name="customerEmail">
            <input type="hidden" id="roomId" name="roomId">
            <input type="hidden" id="calculatedFine" name="calculatedFine">
            <input type="hidden" id="overdueDays" name="overdueDays">
            <input type="hidden" id="paymentMethod" name="paymentMethod" value="credit_card">

            <div class="fine-summary">
                <h4 style="color: var(--fine-color); margin-top: 0;">Fine Summary</h4>
                <div class="fine-item">
                    <span>Overdue Days:</span>
                    <span id="displayOverdueDays">0</span>
                </div>
                <div class="fine-item">
                    <span>Daily Fine Rate:</span>
                    <span id="displayDailyFine">LKR 0.00</span>
                </div>
                <div class="fine-item">
                    <span>Calculated Fine:</span>
                    <span id="displayCalculatedFine">LKR 0.00</span>
                </div>
                <div class="fine-item fine-total">
                    <span>Total Amount to Pay:</span>
                    <span id="displayTotalFine">LKR 0.00</span>
                </div>
            </div>

            <div class="form-group">
                <label>Select Payment Method</label>
                <div class="payment-methods">
                    <div class="payment-method-option" onclick="selectPaymentMethod('credit_card')">
                        <i class="fas fa-credit-card"></i>
                        <div>Credit Card</div>
                    </div>
                    <div class="payment-method-option" onclick="selectPaymentMethod('debit_card')">
                        <i class="fas fa-credit-card"></i>
                        <div>Debit Card</div>
                    </div>
                    <div class="payment-method-option" onclick="selectPaymentMethod('paypal')">
                        <i class="fab fa-paypal"></i>
                        <div>PayPal</div>
                    </div>
                </div>
            </div>

            <div class="form-group" id="cardDetails" style="display: none;">
                <label>Card Number</label>
                <input type="text" id="cardNumber" name="cardNumber" placeholder="1234 5678 9012 3456" maxlength="19">

                <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 15px; margin-top: 15px;">
                    <div>
                        <label>Expiry Date</label>
                        <input type="text" id="expiryDate" name="expiryDate" placeholder="MM/YY" maxlength="5">
                    </div>
                    <div>
                        <label>CVV</label>
                        <input type="password" id="cvv" name="cvv" placeholder="123" maxlength="4">
                    </div>
                </div>

                <div style="margin-top: 15px;">
                    <label>Name on Card</label>
                    <input type="text" id="cardName" name="cardName" placeholder="John Doe">
                </div>
            </div>

            <div class="form-group" id="paypalDetails" style="display: none;">
                <p style="color: var(--medium-blue);">
                    <i class="fab fa-paypal"></i> You will be redirected to PayPal to complete your payment.
                </p>
            </div>

            <button type="submit" class="pay-button" id="submitPayment">Pay Now</button>
        </form>
    </div>
</div>

<div style="text-align: center; margin: 40px 0;">
    <h1 style="color: var(--darker-blue); margin-bottom: 10px;">Ocean View Booking History</h1>
    <c:if test="${not empty bookingHistory && bookingHistory.size() > 0}">
        <p style="font-size: 1.1rem; color: var(--font-color);">
            Welcome, ${bookingHistory[0].customerFirstName} ${bookingHistory[0].customerLastName}
        </p>
    </c:if>
</div>

<!-- Payment Message Display -->
<c:if test="${not empty paymentMessage}">
    <div style="max-width: 1300px; margin: 20px auto; padding: 15px;
                background: ${paymentSuccess ? '#dcfce7' : '#fee2e2'};
                border-radius: var(--border-radius); border: 1px solid ${paymentSuccess ? '#166534' : '#991b1b'};">
        <div style="display: flex; align-items: center; gap: 10px;">
            <i class="fas ${paymentSuccess ? 'fa-check-circle' : 'fa-exclamation-circle'}"
               style="color: ${paymentSuccess ? '#166534' : '#991b1b'};"></i>
            <span style="color: ${paymentSuccess ? '#166534' : '#991b1b'}; font-weight: 600;">
                ${paymentMessage}
            </span>
        </div>
    </div>
</c:if>

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
                        <c:if test="${booking.overdueDays != null && booking.overdueDays > 0 && booking.bookingStatus != 'Completed'}">
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

                            <!-- Show calculated fine and pay button -->
                            <c:if test="${booking.calculatedFine != null && booking.calculatedFine > 0 && booking.bookingStatus != 'Completed'}">
                                <div style="text-align: right;">
                                    <label style="color: var(--fine-color); font-weight: 700;">Overdue Fine</label>
                                    <span style="color: var(--fine-color); font-weight: 800; font-size: 1.1rem; display: block;">
                                        <fmt:formatNumber value="${booking.calculatedFine}" type="currency" currencyCode="LKR" />
                                    </span>
                                    <div class="fine-breakdown" style="margin-bottom: 10px;">
                                        (${booking.overdueDays} days ×
                                        <fmt:formatNumber value="${booking.dailyFine}" type="currency" currencyCode="LKR" />)
                                    </div>

                                    <!-- Pay Overdue Button -->
                                    <button class="pay-overdue-btn"
                                            onclick="openPaymentModal(
                                                '${booking.uniqueId}',
                                                '${booking.customerEmail}',
                                                '${booking.roomId}',
                                                ${booking.calculatedFine},
                                                ${booking.overdueDays},
                                                '${booking.dailyFine}'
                                            )">
                                        <i class="fas fa-money-check-alt"></i> Pay Overdue Fine
                                    </button>
                                </div>
                            </c:if>
                        </div>

                        <!-- Show existing fine information -->
                        <c:if test="${not empty booking.fine && booking.fine != '0' && booking.fine != '0.0'}">
                            <fmt:parseNumber var="fineValue" value="${booking.fine}" type="number" />
                            <div style="margin-top: 8px; padding-top: 8px; border-top: 1px dashed rgba(0,0,0,0.1);">
                                <div style="display: flex; justify-content: space-between; font-size: 0.85rem;">
                                    <span style="color: #666;">Existing Fine:</span>
                                    <span style="color: var(--fine-color); font-weight: 600;">
                                        <fmt:formatNumber value="${booking.savedFine}" type="currency" currencyCode="LKR" />
                                    </span>
                                </div>
                            </div>
                        </c:if>

                        <!-- Cancel Button - Hidden for:
                             1. Completed/Cancelled status
                             2. If inDate is less than 7 days from today
                             3. If outDate has already passed -->
                        <c:if test="${booking.bookingStatus != 'Completed' && booking.bookingStatus != 'Cancelled'}">
                            <c:set var="now" value="<%= new java.util.Date() %>" />
                            <fmt:parseDate value="${booking.inDate}" pattern="yyyy-MM-dd" var="parsedInDate" />
                            <fmt:parseDate value="${booking.outDate}" pattern="yyyy-MM-dd" var="parsedOutDate" />

                            <c:set var="oneWeekFromNow" value="<%= new java.util.Date(new java.util.Date().getTime() + 7 * 24 * 60 * 60 * 1000) %>" />

                            <!-- Check conditions:
                                 1. inDate should be MORE than 1 week away (parsedInDate > oneWeekFromNow)
                                 2. outDate should be in the FUTURE (parsedOutDate > now) -->
                            <c:if test="${parsedInDate.time > oneWeekFromNow.time && parsedOutDate.time > now.time}">
                                <div style="margin-top: 15px; display: flex; justify-content: flex-end;">
                                    <button class="cancel-btn"
                                            onclick="openCancelModal(
                                                '${booking.uniqueId}',
                                                '${booking.roomId}',
                                                '${booking.customerEmail}'
                                            )">
                                        <i class="fas fa-times-circle"></i> Cancel Booking
                                    </button>
                                </div>
                            </c:if>
                        </c:if>
                    </div>
                </div>
            </c:forEach>
        </c:otherwise>
    </c:choose>
</div>

<script>
    // Cancel Modal Functions
    function openCancelModal(bookingId, roomId, customerEmail) {
        document.getElementById('cancelBookingId').value = bookingId;
        document.getElementById('cancelRoomId').value = roomId;
        document.getElementById('cancelCustomerEmail').value = customerEmail;
        document.getElementById('cancelModal').style.display = 'flex';
    }

    function closeCancelModal() {
        document.getElementById('cancelModal').style.display = 'none';
    }

    // Payment Modal Functions
    function openPaymentModal(bookingId, customerEmail, roomId, calculatedFine, overdueDays, dailyFine) {
        // Set form values
        document.getElementById('bookingId').value = bookingId;
        document.getElementById('customerEmail').value = customerEmail;
        document.getElementById('roomId').value = roomId;
        document.getElementById('calculatedFine').value = calculatedFine;
        document.getElementById('overdueDays').value = overdueDays;

        // Display values
        document.getElementById('displayOverdueDays').textContent = overdueDays;
        document.getElementById('displayDailyFine').textContent = formatCurrency(dailyFine);
        document.getElementById('displayCalculatedFine').textContent = formatCurrency(calculatedFine);
        document.getElementById('displayTotalFine').textContent = formatCurrency(calculatedFine);

        // Show modal
        document.getElementById('paymentModal').style.display = 'flex';

        // Auto-select credit card by default
        setTimeout(() => {
            selectPaymentMethod('credit_card');
        }, 100);
    }

    function closeModal() {
        document.getElementById('paymentModal').style.display = 'none';
        resetPaymentForm();
    }

    function selectPaymentMethod(method) {
        // Remove selected class from all options
        document.querySelectorAll('.payment-method-option').forEach(option => {
            option.classList.remove('selected');
        });

        // Add selected class to clicked option
        const options = document.querySelectorAll('.payment-method-option');
        let selectedOption;

        if (method === 'credit_card') selectedOption = options[0];
        else if (method === 'debit_card') selectedOption = options[1];
        else if (method === 'paypal') selectedOption = options[2];

        if (selectedOption) {
            selectedOption.classList.add('selected');
        }

        // Set hidden field value
        document.getElementById('paymentMethod').value = method;

        // Show/hide details based on method
        const cardDetails = document.getElementById('cardDetails');
        const paypalDetails = document.getElementById('paypalDetails');

        if (method === 'credit_card' || method === 'debit_card') {
            cardDetails.style.display = 'block';
            paypalDetails.style.display = 'none';
        } else if (method === 'paypal') {
            cardDetails.style.display = 'none';
            paypalDetails.style.display = 'block';
        }
    }

    function resetPaymentForm() {
        document.getElementById('paymentForm').reset();
        document.querySelectorAll('.payment-method-option').forEach(option => {
            option.classList.remove('selected');
        });
        document.getElementById('cardDetails').style.display = 'none';
        document.getElementById('paypalDetails').style.display = 'none';
        document.getElementById('paymentMethod').value = 'credit_card';
    }

    function formatCurrency(amount) {
        return new Intl.NumberFormat('en-LK', {
            style: 'currency',
            currency: 'LKR',
            minimumFractionDigits: 2
        }).format(amount);
    }

    // Card number formatting
    document.getElementById('cardNumber')?.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\s+/g, '').replace(/[^0-9]/gi, '');
        let formatted = value.replace(/(.{4})/g, '$1 ').trim();
        e.target.value = formatted.substring(0, 19);
    });

    // Expiry date formatting
    document.getElementById('expiryDate')?.addEventListener('input', function(e) {
        let value = e.target.value.replace(/\s+/g, '').replace(/[^0-9]/gi, '');
        if (value.length >= 2) {
            value = value.substring(0, 2) + '/' + value.substring(2, 4);
        }
        e.target.value = value.substring(0, 5);
    });

    // Filter functions
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

    // Close modal when clicking outside
    window.onclick = function(event) {
        const paymentModal = document.getElementById('paymentModal');
        const cancelModal = document.getElementById('cancelModal');

        if (event.target === paymentModal) {
            closeModal();
        }
        if (event.target === cancelModal) {
            closeCancelModal();
        }
    }

    // Payment Form validation
    document.getElementById('paymentForm').addEventListener('submit', function(e) {
        e.preventDefault();

        const paymentMethod = document.getElementById('paymentMethod').value;
        let isValid = true;
        let errorMessage = '';

        if (paymentMethod === 'credit_card' || paymentMethod === 'debit_card') {
            const cardNumber = document.getElementById('cardNumber').value.replace(/\s+/g, '');
            const expiryDate = document.getElementById('expiryDate').value;
            const cvv = document.getElementById('cvv').value;
            const cardName = document.getElementById('cardName').value;

            if (!cardNumber || cardNumber.length < 16) {
                errorMessage = 'Please enter a valid 16-digit card number';
                isValid = false;
            } else if (!expiryDate || expiryDate.length < 5) {
                errorMessage = 'Please enter a valid expiry date (MM/YY)';
                isValid = false;
            } else if (!cvv || cvv.length < 3) {
                errorMessage = 'Please enter a valid CVV';
                isValid = false;
            } else if (!cardName) {
                errorMessage = 'Please enter the name on card';
                isValid = false;
            }
        }

        if (!isValid) {
            alert(errorMessage);
            return;
        }

        // Show loading state
        const submitBtn = document.getElementById('submitPayment');
        const originalText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Processing...';
        submitBtn.disabled = true;

        // Submit form after a short delay for UX
        setTimeout(() => {
            this.submit();
        }, 1000);
    });

    // Cancel Form submission
    document.getElementById('cancelForm').addEventListener('submit', function(e) {
        // Show loading state
        const submitBtn = this.querySelector('.cancel-btn[type="submit"]');
        const originalText = submitBtn.innerHTML;
        submitBtn.innerHTML = '<i class="fas fa-spinner fa-spin"></i> Cancelling...';
        submitBtn.disabled = true;

        // Form will submit normally
    });

    // Initialize filter on page load
    document.addEventListener('DOMContentLoaded', function() {
        filterHistory();
    });
</script>

</body>
</html>