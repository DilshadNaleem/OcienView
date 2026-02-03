<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="org.Ocean_View.Admin.Entity.Room"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.HashSet"%>
<%@page import="java.util.LinkedHashSet"%>
<%@page import="java.net.URLEncoder"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Room Details - <%= request.getParameter("roomType") %></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
    <link rel="stylesheet" href="./css/RoomCategory.css">
</head>
<body>
    <div class="container">
        <div class="header">
            <h1><i class="fas fa-hotel"></i>
                <%= request.getParameter("roomType") != null ?
                   request.getParameter("roomType") : "All" %> Rooms
            </h1>
            <p>Find the perfect accommodation for your stay</p>
            <a href="javascript:history.back()" class="back-button">
                <i class="fas fa-arrow-left"></i> Back to Categories
            </a>
        </div>

        <%
            // Get the list of rooms from request attribute
            List<Room> rooms = (List<Room>) request.getAttribute("rooms");
            String roomType = request.getParameter("roomType");

            if (rooms == null || rooms.isEmpty()) {
        %>
            <div class="no-rooms">
                <i class="fas fa-door-closed"></i>
                <h2>No <%= roomType != null ? roomType : "" %> Rooms Available</h2>
                <p>We're sorry, but there are currently no <%= roomType != null ? roomType : "" %> rooms available for booking.</p>
                <p>Please check back later or explore other room categories.</p>
                <a href="index.jsp" class="btn btn-book" style="margin-top: 20px; display: inline-block;">
                    <i class="fas fa-home"></i> Back to Home
                </a>
            </div>
        <%
            } else {
        %>
            <div class="rooms-grid">
                <%
                    for (Room room : rooms) {
                        // FIX: Sanitize ID so JavaScript can find the correct data div for every unique room
                        String safeId = room.getUniqueId().replaceAll("[^a-zA-Z0-9]", "");

                        List<String> images = room.getImageList();
                        List<String> uniqueImages = new ArrayList<>();
                        HashSet<String> seen = new HashSet<>();

                        if (images != null) {
                            for (String img : images) {
                                String trimmedImg = img.trim();
                                if (!trimmedImg.isEmpty() && !seen.contains(trimmedImg)) {
                                    seen.add(trimmedImg);
                                    uniqueImages.add(trimmedImg);
                                }
                            }
                        }

                        String mainImage = uniqueImages.isEmpty() ? "/Customer/default-room.jpg" : uniqueImages.get(0);
                        String mainImageUrl = buildImageUrl(mainImage, request);
                %>
                    <div class="room-card" id="room-card-<%= safeId %>">
                        <div class="room-image-container">
                            <div class="main-image-wrapper">
                                <img src="<%= mainImageUrl %>"
                                     alt="<%= room.getRoomType() %>"
                                     class="room-main-image"
                                     onclick="openImageModal('<%= safeId %>', 0)"
                                     onerror="this.onerror=null; this.src='<%= request.getContextPath() %>/Customer/default-room.jpg';"
                                     loading="lazy">

                                <% if (uniqueImages.size() > 1) { %>
                                    <div class="thumbnail-preview">
                                        <%
                                            // FIX: Corrected loop and ID reference for thumbnails
                                            int thumbnailsToShow = Math.min(uniqueImages.size() - 1, 3);
                                            for (int i = 1; i <= thumbnailsToShow; i++) {
                                                String thumbImageUrl = buildImageUrl(uniqueImages.get(i), request);
                                        %>
                                            <div class="thumbnail-item" onclick="openImageModal('<%= safeId %>', <%= i %>)">
                                                <img src="<%= thumbImageUrl %>"
                                                     alt="Thumbnail <%= i %>"
                                                     class="thumbnail-image"
                                                     onerror="this.onerror=null; this.src='<%= request.getContextPath() %>/Customer/default-room.jpg';"
                                                     loading="lazy">
                                                <%
                                                    if (i == thumbnailsToShow && uniqueImages.size() > thumbnailsToShow + 1) {
                                                        int remainingImages = uniqueImages.size() - (thumbnailsToShow + 1);
                                                %>
                                                    <div class="more-images-overlay">
                                                        +<%= remainingImages %>
                                                    </div>
                                                <% } %>
                                            </div>
                                        <% } %>
                                    </div>
                                <% } %>
                            </div>
                        </div>

                        <div class="room-details">
                            <h2 class="room-title">
                                <%= room.getRoomType() %>
                                <span class="status-badge <%= room.getStatus().equalsIgnoreCase("Active") ? "status-available" : "status-booked" %>">
                                    <i class="fas fa-<%= room.getStatus().equalsIgnoreCase("Active") ? "check-circle" : "times-circle" %>"></i>
                                    <%= room.getStatus() %>
                                </span>
                            </h2>

                            <div class="room-info">
                                <span class="info-label"><i class="fas fa-users"></i> Capacity:</span>
                                <span class="info-value"><%= room.getNoOfPeople() %> People</span>
                            </div>

                            <div class="room-info">
                                <span class="info-label"><i class="fas fa-calendar-alt"></i> Created:</span>
                                <span class="info-value">
                                    <%= room.getCreated_at() != null ? room.getCreated_at().toLocalDate() : "N/A" %>
                                </span>
                            </div>

                            <% if (room.getDescription() != null && !room.getDescription().isEmpty()) { %>
                            <div class="room-info">
                                <span class="info-label"><i class="fas fa-align-left"></i> Description:</span>
                                <span class="info-value"><%= room.getDescription() %></span>
                            </div>
                            <% } %>

                            <div class="price-tag">
                                <i class="fas fa-tag"></i> LKR.<%= room.getPrice() %> / night
                            </div>

                            <% if (room.getFacilities() != null && !room.getFacilities().isEmpty()) { %>
                            <div class="facilities-section">
                                <h3 class="section-title"><i class="fas fa-concierge-bell"></i> Facilities</h3>
                                <ul class="facilities-list"><%= room.getFacilities() %></ul>
                            </div>
                            <% } %>

                              <% if (room.getRules() != null && !room.getRules().isEmpty()) { %>
                                                         <div class="rules-section">
                                                             <h3 class="section-title">
                                                                 <i class="fas fa-clipboard-list"></i> Rules
                                                             </h3>
                                                             <ul class="rules-list">
                                                                <%= room.getRules() %>
                                                             </ul>
                                                         </div>
                                                         <% } %>

                                                         <% if (room.getFine() != null && !room.getFine().isEmpty()) { %>
                                                         <div class="fine-section">
                                                             <h3 class="section-title">
                                                                 <i class="fas fa-exclamation-triangle"></i> Fine Policy
                                                             </h3>
                                                             <p><%= room.getFine() %></p>
                                                         </div>
                                                         <% } %>


                            <div id="room-images-<%= safeId %>" style="display: none;">
                                <%= String.join(",", uniqueImages) %>
                            </div>

                            <div class="action-buttons">
                                <% if (room.getRoomStatus().equalsIgnoreCase("Available")) { %>
                                    <a href="<%= request.getContextPath() %>/Customer/Booking.jsp?roomId=<%= room.getUniqueId() %>&roomType=<%= URLEncoder.encode(room.getRoomType(), "UTF-8") %>"
                                       class="btn btn-book">
                                        <i class="fas fa-calendar-check"></i> Book Now
                                    </a>
                                <% } else { %>
                                    <button class="btn btn-book btn-disabled" disabled>
                                        <i class="fas fa-times-circle"></i> Already Booked
                                    </button>
                                <% } %>
                                <a href="<%= request.getContextPath() %>/Customer/Enquiry.jsp?roomId=<%= room.getUniqueId() %>&roomType=<%= URLEncoder.encode(room.getRoomType(), "UTF-8") %>"
                                   class="btn btn-enquiry">
                                    <i class="fas fa-question-circle"></i> Enquire
                                </a>
                            </div>
                        </div>
                    </div>
                <% } %>
            </div>
        <% } %>
    </div>

    <div id="imageModal" class="modal">
        <span class="close" onclick="closeModal()">&times;</span>
        <a class="prev" onclick="changeImage(-1)">&#10094;</a>
        <a class="next" onclick="changeImage(1)">&#10095;</a>
        <img class="modal-content" id="modalImage">
        <div id="caption" class="caption"></div>
        <div id="imageCounter" style="color: white; text-align: center; margin-top: 10px;"></div>
    </div>

    <script>
        let currentRoomId = '';
        let currentImages = [];
        let currentImageIndex = 0;
        const contextPath = '<%= request.getContextPath() %>';

        function buildJsImageUrl(imagePath) {
            imagePath = imagePath.trim();
            if (imagePath.startsWith('http')) return imagePath;
            const cleanPath = imagePath.startsWith('/') ? imagePath : '/' + imagePath;
            // Ensure we don't double the context path
            return cleanPath.startsWith(contextPath) ? window.location.origin + cleanPath : window.location.origin + contextPath + cleanPath;
        }

        function openImageModal(roomId, imageIndex) {
            const imageDataDiv = document.getElementById('room-images-' + roomId);
            if (!imageDataDiv) return;

            const imagePaths = imageDataDiv.textContent.split(',');
            currentImages = imagePaths.map(img => buildJsImageUrl(img));
            currentRoomId = roomId;
            currentImageIndex = parseInt(imageIndex) || 0;

            document.getElementById('imageModal').style.display = "block";
            document.body.style.overflow = "hidden";
            updateModalImage();
        }

        function closeModal() {
            document.getElementById('imageModal').style.display = "none";
            document.body.style.overflow = "auto";
        }

        function changeImage(direction) {
            if (currentImages.length === 0) return;
            currentImageIndex = (currentImageIndex + direction + currentImages.length) % currentImages.length;
            updateModalImage();
        }

        function updateModalImage() {
            if (currentImages.length === 0) return;
            const modalImage = document.getElementById('modalImage');
            const caption = document.getElementById('caption');
            const counter = document.getElementById('imageCounter');

            modalImage.src = currentImages[currentImageIndex];
            caption.innerHTML = `Room: ${currentRoomId} | Image ${currentImageIndex + 1} of ${currentImages.length}`;
            counter.innerHTML = `${currentImageIndex + 1} / ${currentImages.length}`;
        }

        window.onclick = function(event) {
            if (event.target == document.getElementById('imageModal')) closeModal();
        }

        document.addEventListener('keydown', function(event) {
            if (document.getElementById('imageModal').style.display === 'block') {
                if (event.key === 'Escape') closeModal();
                if (event.key === 'ArrowLeft') changeImage(-1);
                if (event.key === 'ArrowRight') changeImage(1);
            }
        });
    </script>
</body>
</html>

<%!
    private String buildImageUrl(String imagePath, HttpServletRequest request) {
        if (imagePath == null || imagePath.trim().isEmpty()) {
            return request.getContextPath() + "/Customer/default-room.jpg";
        }
        imagePath = imagePath.trim();
        if (imagePath.startsWith("http")) return imagePath;
        return request.getContextPath() + (imagePath.startsWith("/") ? "" : "/") + imagePath;
    }
%>