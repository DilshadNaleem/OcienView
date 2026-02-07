<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Bookings - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #f8f9fa; }
        .table-container { background: white; border-radius: 15px; padding: 25px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }
        .badge-status { font-weight: 500; padding: 0.5em 0.8em; min-width: 90px; text-align: center; }
        .search-box { border-radius: 20px; padding-left: 40px; }
        .search-icon { position: absolute; left: 25px; top: 50%; transform: translateY(-50%); z-index: 5; color: #6c757d; }
        .date-text { font-size: 0.85rem; color: #6c757d; }
    </style>
</head>
<body>
    <div class="container-fluid py-4">

        <%-- Alerts --%>
        <div class="row justify-content-center">
            <div class="col-11">
                <c:if test="${not empty sessionScope.successMessage}">
                    <div class="alert alert-success alert-dismissible fade show" role="alert">
                        <i class="bi bi-check-circle-fill me-2"></i> ${sessionScope.successMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                    </div>
                    <c:remove var="successMessage" scope="session" />
                </c:if>
            </div>
        </div>

        <div class="table-container mx-3">
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <a href="${pageContext.request.contextPath}/Admin/Dashboard.jsp" class="btn btn-sm btn-outline-secondary mb-2">
                        <i class="bi bi-arrow-left"></i> Back to Dashboard
                    </a>
                    <h2 class="mb-0"><i class="bi bi-calendar-check-fill text-primary"></i> Reservation Ledger</h2>
                </div>
            </div>

            <hr>

            <%-- Search and Filters --%>
            <div class="row g-3 mb-4">
                <div class="col-md-7 position-relative">
                    <i class="bi bi-search search-icon"></i>
                    <input type="text" id="bookingSearch" class="form-control search-box" placeholder="Search by ID, Customer Name, Room or Payment ID...">
                </div>
                <div class="col-md-3">
                    <select id="statusFilter" class="form-select">
                        <option value="all">All Booking Statuses</option>
                        <option value="Confirmed">Confirmed</option>
                        <option value="Pending">Pending</option>
                        <option value="Cancelled">Cancelled</option>
                        <option value="Completed">Completed</option>
                    </select>
                </div>
                <div class="col-md-2">
                    <button class="btn btn-light w-100" onclick="window.location.reload()"><i class="bi bi-arrow-clockwise"></i> Refresh</button>
                </div>
            </div>

            <div class="table-responsive">
                <table class="table table-hover align-middle" id="bookingTable">
                    <thead class="table-dark">
                        <tr>
                            <th>Booking ID</th>
                            <th>Customer</th>
                            <th>Room</th>
                            <th>Stay Dates</th>
                            <th>Duration</th>
                            <th>Total Price</th>
                            <th>Status</th>
                            <th class="text-center">Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="b" items="${bookingList}">
                            <tr>
                                <td>
                                    <small class="text-muted d-block">Placed: ${b.bookingDate}</small>
                                    <code>${b.uniqueId}</code>
                                </td>
                                <td>
                                    <strong>${b.customerFirstName} ${b.customerLastName}</strong><br>
                                    <small class="text-muted">${b.customerEmail}</small>
                                </td>
                                <td><span class="badge bg-light text-dark border">${b.roomId}</span></td>
                                <td>
                                    <div class="date-text">
                                        <i class="bi bi-box-arrow-in-right text-success"></i> ${b.inDate}<br>
                                        <i class="bi bi-box-arrow-left text-danger"></i> ${b.outDate}
                                    </div>
                                </td>
                                <td>${b.noOfDays} Days</td>
                                <td>
                                    <div class="fw-bold text-primary">LKR ${b.price}</div>
                                    <c:if test="${not empty b.fine && b.fine != '0'}">
                                        <small class="text-danger">Fine: LKR ${b.fine}</small>
                                    </c:if>
                                </td>
                                <td>
                                    <c:choose>
                                        <c:when test="${b.bookingStatus == 'Confirmed'}"><span class="badge badge-status bg-success">Confirmed</span></c:when>
                                        <c:when test="${b.bookingStatus == 'Booked'}"><span class="badge badge-status bg-warning text-dark">Pending</span></c:when>
                                        <c:when test="${b.bookingStatus == 'Cancelled'}"><span class="badge badge-status bg-danger">Cancelled</span></c:when>
                                        <c:otherwise><span class="badge badge-status bg-info text-dark">${b.bookingStatus}</span></c:otherwise>
                                    </c:choose>
                                </td>
                                <td class="text-center">
                                    <button class="btn btn-sm btn-outline-primary" title="View Payment Details"
                                            onclick="alert('Payment ID: ${b.paymentUniqueId}\nMethod: ${b.paymentMethod}')">
                                        <i class="bi bi-credit-card"></i>
                                    </button>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <div id="noResults" class="text-center py-5 d-none">
                <i class="bi bi-calendar-x display-1 text-muted"></i>
                <p class="mt-3 text-muted">No reservations match your search.</p>
            </div>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const searchInput = document.getElementById('bookingSearch');
            const statusFilter = document.getElementById('statusFilter');
            const tableRows = document.querySelectorAll('#bookingTable tbody tr');
            const noResults = document.getElementById('noResults');
            const tableElement = document.getElementById('bookingTable');

            function filterTable() {
                const searchTerm = searchInput.value.toLowerCase();
                const filterValue = statusFilter.value;
                let visibleCount = 0;

                tableRows.forEach(row => {
                    const text = row.innerText.toLowerCase();
                    const statusBadge = row.querySelector('.badge-status').innerText.trim();

                    const matchesSearch = text.includes(searchTerm);
                    const matchesStatus = (filterValue === 'all' || statusBadge === filterValue);

                    if (matchesSearch && matchesStatus) {
                        row.style.display = '';
                        visibleCount++;
                    } else {
                        row.style.display = 'none';
                    }
                });

                if (visibleCount === 0) {
                    noResults.classList.remove('d-none');
                    tableElement.classList.add('d-none');
                } else {
                    noResults.classList.add('d-none');
                    tableElement.classList.remove('d-none');
                }
            }

            searchInput.addEventListener('keyup', filterTable);
            statusFilter.addEventListener('change', filterTable);
        });
    </script>
</body>
</html>