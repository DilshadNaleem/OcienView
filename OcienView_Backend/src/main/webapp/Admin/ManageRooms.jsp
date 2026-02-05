<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Manage All Rooms - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        body { background-color: #f8f9fa; }
        .table-container { background: white; border-radius: 15px; padding: 25px; box-shadow: 0 4px 12px rgba(0,0,0,0.05); }
        .attr-label { font-weight: 600; color: #495057; font-size: 0.85rem; text-transform: uppercase; letter-spacing: 0.5px; }
        .scrollable-cell { max-width: 150px; overflow: hidden; text-overflow: ellipsis; white-space: nowrap; cursor: help; }
        .badge-status { font-weight: 500; padding: 0.5em 0.8em; }
    </style>
</head>
<body>
    <div class="container-fluid py-4">

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
            <div class="d-flex justify-content-between align-items-center mb-4">
                <h2 class="mb-0"><i class="bi bi-grid-3x3-gap-fill text-primary"></i> Room Inventory</h2>
                <a href="${pageContext.request.contextPath}/Admin/AddRoom.jsp" class="btn btn-primary">
                    <i class="bi bi-plus-lg"></i> Add New Room
                </a>
            </div>

            <div class="table-responsive">
                <table class="table table-hover align-middle">
                    <thead class="table-dark">
                        <tr>
                            <th>ID</th>
                            <th>Category</th>
                            <th>Type</th>
                            <th>Cap.</th>
                            <th>Price</th>
                            <th>Fine</th>
                            <th>Status</th>
                            <th>Description</th>
                            <th>Facilities</th>
                            <th>Rules</th>
                            <th class="text-center">Actions</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach var="r" items="${room}" varStatus="loop">
                            <tr>
                                <td><code>${r.uniqueId}</code></td>
                                <td>${r.roomCategoryId}</td>
                                <td>${r.roomType}</td>
                                <td>${r.noOfPeople}</td>
                                <td><strong>LKR.${r.price}</strong></td>
                                <td class="text-danger">LKR.${r.fine}</td>
                                <td>
                                    <span class="badge badge-status ${r.roomStatus == 'Available' ? 'bg-success' : 'bg-warning text-dark'}">
                                        ${r.roomStatus}
                                    </span>
                                </td>
                                <td class="scrollable-cell" title="${r.description}">${r.description}</td>
                                <td class="scrollable-cell" title="${r.facilities}">${r.facilities}</td>
                                <td class="scrollable-cell" title="${r.rules}">${r.rules}</td>
                                <td>
                                    <div class="d-flex justify-content-center gap-2">
                                        <button class="btn btn-sm btn-outline-primary" data-bs-toggle="modal" data-bs-target="#editRoom${loop.index}">
                                            <i class="bi bi-pencil"></i>
                                        </button>
                                        <button class="btn btn-sm btn-outline-danger" data-bs-toggle="modal" data-bs-target="#deleteRoom${loop.index}">
                                            <i class="bi bi-trash"></i>
                                        </button>
                                    </div>
                                </td>
                            </tr>

                            <div class="modal fade" id="editRoom${loop.index}" tabindex="-1" aria-hidden="true">
                                <div class="modal-dialog modal-lg modal-dialog-centered">
                                    <div class="modal-content">
                                        <form action="${pageContext.request.contextPath}/Admin/UpdateRoom" method="POST">
                                            <div class="modal-header bg-primary text-white">
                                                <h5 class="modal-title"><i class="bi bi-pencil-square"></i> Edit Room: ${r.uniqueId}</h5>
                                                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body">
                                                <input type="hidden" name="uniqueId" value="${r.uniqueId}">
                                                <div class="row g-3">
                                                    <div class="col-md-4">
                                                        <label class="attr-label">Category ID</label>
                                                        <input type="text" name="roomCategoryId" class="form-control" value="${r.roomCategoryId}" readOnly>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label class="attr-label">Room Type</label>
                                                        <input type="text" name="roomType" class="form-control" value="${r.roomType}">
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label class="attr-label">Current Status</label>
                                                        <select name="status" class="form-select">
                                                            <option value="Available" ${r.roomStatus == 'Available' ? 'selected' : ''}>Available</option>
                                                            <option value="Booked" ${r.roomStatus == 'Booked' ? 'selected' : ''}>Booked</option>
                                                            <option value="Maintenance" ${r.roomStatus == 'Maintenance' ? 'selected' : ''}>Maintenance</option>
                                                        </select>
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label class="attr-label">Capacity (People)</label>
                                                        <input type="number" name="noOfPeople" class="form-control" value="${r.noOfPeople}">
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label class="attr-label">Price ($)</label>
                                                        <input type="text" name="price" class="form-control" value="${r.price}">
                                                    </div>
                                                    <div class="col-md-4">
                                                        <label class="attr-label">Fine ($)</label>
                                                        <input type="text" name="fine" class="form-control" value="${r.fine}">
                                                    </div>
                                                    <div class="col-12">
                                                        <label class="attr-label">Description</label>
                                                        <textarea name="description" class="form-control" rows="2">${r.description}</textarea>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <label class="attr-label">Facilities</label>
                                                        <textarea name="facilities" class="form-control" rows="3">${r.facilities}</textarea>
                                                    </div>
                                                    <div class="col-md-6">
                                                        <label class="attr-label">House Rules</label>
                                                        <textarea name="rules" class="form-control" rows="3">${r.rules}</textarea>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-light" data-bs-dismiss="modal">Cancel</button>
                                                <button type="submit" class="btn btn-primary px-4">Save Changes</button>
                                            </div>
                                        </form>
                                    </div>
                                </div>
                            </div>

                            <div class="modal fade" id="deleteRoom${loop.index}" tabindex="-1" aria-hidden="true">
                                <div class="modal-dialog modal-dialog-centered">
                                    <div class="modal-content">
                                        <form action="${pageContext.request.contextPath}/Admin/DeleteRoom" method="POST">
                                            <div class="modal-header bg-danger text-white">
                                                <h5 class="modal-title"><i class="bi bi-exclamation-triangle"></i> Delete Confirmation</h5>
                                                <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal"></button>
                                            </div>
                                            <div class="modal-body">
                                                <input type="hidden" name="uniqueId" value="${r.uniqueId}">
                                                <p>Are you sure you want to delete Room <strong>${r.uniqueId}</strong>?</p>
                                                <p class="text-muted small">This will permanently remove the room from the database.</p>
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
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        // Automatically hide alerts after 5 seconds
        setTimeout(function() {
            let alerts = document.querySelectorAll('.alert');
            alerts.forEach(function(alert) {
                let bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);
    </script>
</body>
</html>