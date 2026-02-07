<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage Customers - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #f8f9fa; }
        .table-container { background: white; border-radius: 15px; padding: 25px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }
        .attr-label { font-weight: 600; color: #495057; font-size: 0.85rem; text-transform: uppercase; letter-spacing: 0.5px; }
        .badge-status { font-weight: 500; padding: 0.5em 0.8em; min-width: 80px; text-align: center; }
        .search-box { border-radius: 20px; padding-left: 40px; }
        .search-icon { position: absolute; left: 25px; top: 50%; transform: translateY(-50%); z-index: 5; color: #6c757d; }
    </style>
</head>
<body>
    <div class="container-fluid py-4">

        <%-- Message Notifications --%>
        <div class="row justify-content-center">
            <div class="col-11">
                <c:if test="${not empty sessionScope.successMessage}">
                    <div class="alert alert-success alert-dismissible fade show shadow-sm" role="alert">
                        <i class="bi bi-check-circle-fill me-2"></i> ${sessionScope.successMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <c:remove var="successMessage" scope="session" />
                </c:if>

                <c:if test="${not empty sessionScope.errorMessage}">
                    <div class="alert alert-danger alert-dismissible fade show shadow-sm" role="alert">
                        <i class="bi bi-exclamation-triangle-fill me-2"></i> ${sessionScope.errorMessage}
                        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
                    </div>
                    <c:remove var="errorMessage" scope="session" />
                </c:if>
            </div>
        </div>

        <div class="table-container mx-3">

            <%-- Top Header & Back Button --%>
            <div class="d-flex justify-content-between align-items-center mb-4">
                <div>
                    <a href="${pageContext.request.contextPath}/Admin/Dashboard.jsp" class="btn btn-sm btn-outline-secondary mb-2">
                        <i class="bi bi-arrow-left"></i> Back to Dashboard
                    </a>
                    <h2 class="mb-0"><i class="bi bi-people-fill text-primary"></i> Customer Management</h2>
                </div>
            </div>

            <hr>

            <%-- Search and Filters Row --%>
            <div class="row g-3 mb-4">
                <div class="col-md-8 position-relative">
                    <i class="bi bi-search search-icon"></i>
                    <input type="text" id="customerSearch" class="form-control search-box" placeholder="Search by Name, Email, NIC or ID...">
                </div>
                <div class="col-md-4">
                    <select id="statusFilter" class="form-select">
                        <option value="all">All Statuses</option>
                        <option value="Active">Active Only</option>
                        <option value="Inactive">Inactive Only</option>
                    </select>
                </div>
            </div>

            <%-- Table Section --%>
            <div class="table-responsive">
                <table class="table table-hover align-middle" id="customerTable">
                    <thead class="table-dark">
                        <tr>
                            <th>Unique ID</th>
                            <th>Full Name</th>
                            <th>Email</th>
                            <th>Contact</th>
                            <th>NIC</th>
                            <th>Status</th>
                            <th class="text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="cust" items="${customerList}" varStatus="loop">
                            <tr>
                                <td><code>${cust.unique_id}</code></td>
                                <td class="fw-bold">${cust.firstName} ${cust.lastName}</td>
                                <td>${cust.email}</td>
                                <td>${cust.contactNumber}</td>
                                <td>${cust.nic}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${cust.status == '1'}">
                                            <span class="badge badge-status bg-success">Active</span>
                                        </c:when>
                                        <c:otherwise>
                                            <span class="badge badge-status bg-secondary">Inactive</span>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                                <td>
                                    <div class="d-flex justify-content-center gap-2">
                                        <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteCust${loop.index}">
                                            <i class="bi bi-trash"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>

                            <%-- Delete Customer Modal --%>
                            <div class="modal fade" id="deleteCust${loop.index}" tabindex="-1" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <form action="${pageContext.request.contextPath}/Admin/DeleteCustomer" method="POST">
                                            <div class="modal-header bg-danger text-white">
                                                <h5 class="modal-title"><i class="bi bi-exclamation-triangle"></i> Delete Customer</h5>
                                                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body">
                                                <input type="hidden" name="unique_id" value="${cust.unique_id}">
                                                <p>Are you sure you want to delete <strong>${cust.firstName} ${cust.lastName}</strong>?</p>
                                                <p class="text-muted small">This action will remove their record from the management system.</p>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                                                <button type="submit" class="btn btn-danger px-4">Confirm Delete</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>
                        </c:forEach>
                    </tbody>
                </table>
            </div>

            <%-- Empty State --%>
            <div id="noResults" class="text-center py-5 d-none">
                <i class="bi bi-person-exclamation display-1 text-muted"></i>
                <p class="mt-3 text-muted">No customers found matching your criteria.</p>
            </div>

        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>

    <script>
        document.addEventListener('DOMContentLoaded', function() {
            const searchInput = document.getElementById('customerSearch');
            const statusFilter = document.getElementById('statusFilter');
            const tableRows = document.querySelectorAll('#customerTable tbody tr');
            const noResults = document.getElementById('noResults');
            const tableElement = document.getElementById('customerTable');

            function filterTable() {
                const searchTerm = searchInput.value.toLowerCase();
                const filterValue = statusFilter.value;
                let visibleCount = 0;

                tableRows.forEach(row => {
                    // Get text content from the whole row
                    const rowText = row.innerText.toLowerCase();
                    // Get text content specifically from the badge
                    const statusText = row.querySelector('.badge-status').innerText.trim();

                    const matchesSearch = rowText.includes(searchTerm);
                    const matchesStatus = (filterValue === 'all' || statusText === filterValue);

                    if (matchesSearch && matchesStatus) {
                        row.style.display = '';
                        visibleCount++;
                    } else {
                        row.style.display = 'none';
                    }
                });

                // Show "No Results" message if everything is hidden
                if (visibleCount === 0) {
                    noResults.classList.remove('d-none');
                    tableElement.classList.add('d-none');
                } else {
                    noResults.classList.add('d-none');
                    tableElement.classList.remove('d-none');
                }
            }

            // Listen for user input
            searchInput.addEventListener('keyup', filterTable);
            statusFilter.addEventListener('change', filterTable);

            // Automatically hide alerts after 5 seconds
            setTimeout(function() {
                let alerts = document.querySelectorAll('.alert');
                alerts.forEach(function(alert) {
                    let bsAlert = new bootstrap.Alert(alert);
                    bsAlert.close();
                });
            }, 5000);
        });
    </script>
</body>
</html>