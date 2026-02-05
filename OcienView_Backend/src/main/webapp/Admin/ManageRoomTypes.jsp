<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Room Types - Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.11.0/font/bootstrap-icons.css">
    <style>
        .table-responsive { margin-top: 20px; }
        .action-buttons { white-space: nowrap; }
        .room-image { width: 100px; height: 70px; object-fit: cover; border-radius: 5px; }
        /* Preview image specifically for the modal */
        .modal-preview-img { width: 100%; max-height: 200px; object-fit: contain; border-radius: 8px; margin-bottom: 15px; border: 1px solid #dee2e6; }
        .page-header { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; padding: 20px; border-radius: 10px; margin-bottom: 30px; }
        .btn-add-room { background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); color: white; border: none; padding: 10px 20px; border-radius: 5px; text-decoration: none; display: inline-flex; align-items: center; gap: 5px; }
        .btn-add-room:hover { background: linear-gradient(135deg, #5a6fd8 0%, #6a4190 100%); color: white; }
        .empty-state { text-align: center; padding: 50px; color: #6c757d; }
    </style>
</head>
<body>
    <div class="container-fluid">
        <div class="container mt-4">
            <div class="page-header">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h1><i class="bi bi-house-door"></i> Manage Room Types</h1>
                        <p class="mb-0">Add, edit or delete room types in your system</p>
                    </div>

                    <a href="${pageContext.request.contextPath}/Admin/AddNewRoomTypes.html" class="btn btn-add-room">
                        <i class="bi bi-plus-circle"></i> Add New Room Type
                    </a>
                </div>
            </div>

            <div class="card shadow">
                <div class="card-header bg-white">
                    <h5 class="mb-0"><i class="bi bi-list-ul"></i> All Room Types</h5>
                </div>
                <div class="card-body">
                    <c:choose>
                        <c:when test="${not empty roomList and roomList.size() > 0}">
                            <div class="table-responsive">
                                <table class="table table-hover table-striped">
                                    <thead class="table-dark">
                                        <tr>
                                            <th>#</th>
                                            <th>Image</th>
                                            <th>Room Category</th>
                                            <th>Description</th>
                                            <th>ID</th>
                                            <th>Actions</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:forEach var="roomType" items="${roomList}" varStatus="loop">
                                            <tr>
                                                <td>${loop.index + 1}</td>
                                                <td>
                                                    <c:choose>
                                                        <c:when test="${not empty roomType.image}">
                                                           <img src="${pageContext.request.contextPath}/${roomType.image}" alt="${roomType.roomCategory}" class="room-image img-thumbnail">
                                                        </c:when>
                                                        <c:otherwise>
                                                            <div class="room-image bg-light d-flex align-items-center justify-content-center">
                                                                <i class="bi bi-image text-muted"></i>
                                                            </div>
                                                        </c:otherwise>
                                                    </c:choose>
                                                </td>
                                                <td><strong>${roomType.roomCategory}</strong></td>
                                                <td style="max-width: 300px;">${roomType.description}</td>
                                                <td><code>${roomType.uniqueId}</code></td>
                                                <td class="action-buttons">
                                                    <button type="button" class="btn btn-warning btn-sm me-2" data-bs-toggle="modal" data-bs-target="#editModal${loop.index}">
                                                        <i class="bi bi-pencil-square"></i> Edit
                                                    </button>

                                                    <button type="button" class="btn btn-danger btn-sm" data-bs-toggle="modal" data-bs-target="#deleteModal${loop.index}">
                                                        <i class="bi bi-trash"></i> Delete
                                                    </button>

                                                    <div class="modal fade" id="editModal${loop.index}" tabindex="-1" aria-hidden="true">
                                                        <div class="modal-dialog modal-lg">
                                                            <div class="modal-content text-start">
                                                                <form action="${pageContext.request.contextPath}/Admin/UpdateRoomType" method="POST">
                                                                    <div class="modal-header bg-warning">
                                                                        <h5 class="modal-title text-dark"><i class="bi bi-pencil-square"></i> Edit Room Type</h5>
                                                                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                    </div>
                                                                    <div class="modal-body">
                                                                        <input type="hidden" name="uniqueId" value="${roomType.uniqueId}">

                                                                        <div class="row">
                                                                            <div class="col-md-4 text-center border-end">
                                                                                <label class="form-label d-block fw-bold">Current Image</label>
                                                                                <c:choose>
                                                                                    <c:when test="${not empty roomType.image}">
                                                                                        <img src="${pageContext.request.contextPath}/${roomType.image}" class="modal-preview-img shadow-sm" alt="Room Preview">
                                                                                    </c:when>
                                                                                    <c:otherwise>
                                                                                        <div class="modal-preview-img bg-light d-flex align-items-center justify-content-center">
                                                                                            <span class="text-muted">No Image</span>
                                                                                        </div>
                                                                                    </c:otherwise>
                                                                                </c:choose>
                                                                            </div>

                                                                            <div class="col-md-8">
                                                                                <div class="mb-3">
                                                                                    <label class="form-label">Room Category Name</label>
                                                                                    <input type="text" name="roomCategory" class="form-control" value="${roomType.roomCategory}" required>
                                                                                </div>
                                                                                <div class="mb-3">
                                                                                    <label class="form-label">Image Path</label>
                                                                                    <input type="text" name="image" class="form-control" value="${roomType.image}" readonly>
                                                                                    <div class="form-text">Update the path above to change the image.</div>
                                                                                </div>
                                                                            </div>
                                                                        </div>

                                                                        <div class="mt-3">
                                                                            <label class="form-label">Description</label>
                                                                            <textarea name="description" class="form-control" rows="4" required>${roomType.description}</textarea>
                                                                        </div>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                                        <button type="submit" class="btn btn-primary">Update Changes</button>
                                                                    </div>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>

                                                    <div class="modal fade" id="deleteModal${loop.index}" tabindex="-1" aria-hidden="true">
                                                        <div class="modal-dialog">
                                                            <div class="modal-content">
                                                                <form action="${pageContext.request.contextPath}/Admin/DeleteRoomType" method="POST">
                                                                    <div class="modal-header bg-danger text-white">
                                                                        <h5 class="modal-title">
                                                                            <i class="bi bi-exclamation-triangle"></i> Confirm Deletion
                                                                        </h5>
                                                                        <button type="button" class="btn-close btn-close-white" data-bs-dismiss="modal" aria-label="Close"></button>
                                                                    </div>
                                                                    <div class="modal-body text-start">
                                                                        <input type="hidden" name="uniqueId" value="${roomType.uniqueId}">

                                                                        <p>Are you sure you want to delete the room type: <strong>${roomType.roomCategory}</strong>?</p>

                                                                        <div class="alert alert-light border">
                                                                            <small class="text-muted">Room ID:</small> <code>${roomType.uniqueId}</code>
                                                                        </div>

                                                                        <p class="text-danger mb-0">
                                                                            <i class="bi bi-exclamation-circle"></i> This action cannot be undone!
                                                                        </p>
                                                                    </div>
                                                                    <div class="modal-footer">
                                                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Cancel</button>
                                                                        <button type="submit" class="btn btn-danger">
                                                                            <i class="bi bi-trash"></i> Confirm Delete
                                                                        </button>
                                                                    </div>
                                                                </form>
                                                            </div>
                                                        </div>
                                                    </div>

                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </tbody>
                                </table>
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="empty-state">
                                <i class="bi bi-inbox display-1"></i>
                                <h3 class="mt-3">No Room Types Found</h3>
                                <a href="${pageContext.request.contextPath}/Admin/AddRoomType.jsp" class="btn btn-primary mt-2">Add First Room Type</a>
                            </div>
                        </c:otherwise>
                    </c:choose>
                </div>
            </div>

           <%-- Update this section in your ManageRoomTypes.jsp --%>
           <c:if test="${not empty sessionScope.successMessage}">
               <div class="alert alert-success alert-dismissible fade show mt-3" role="alert">
                   <i class="bi bi-check-circle"></i> ${sessionScope.successMessage}
                   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
               </div>
               <c:remove var="successMessage" scope="session" />
           </c:if>

           <c:if test="${not empty sessionScope.errorMessage}">
               <div class="alert alert-danger alert-dismissible fade show mt-3" role="alert">
                   <i class="bi bi-exclamation-triangle"></i> ${sessionScope.errorMessage}
                   <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
               </div>
               <c:remove var="errorMessage" scope="session" />
           </c:if>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
    <script>
        setTimeout(function() {
            const alerts = document.querySelectorAll('.alert');
            alerts.forEach(alert => {
                const bsAlert = new bootstrap.Alert(alert);
                bsAlert.close();
            });
        }, 5000);
    </script>
</body>
</html>