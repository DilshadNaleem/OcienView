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
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/flatpickr/dist/flatpickr.min.css">
    <script src="https://cdn.jsdelivr.net/npm/flatpickr"></script>

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
                                <%= room.getUniqueId() %>
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
                                                                 <i class="fas fa-exclamation-triangle"></i> Fine Price
                                                             </h3>
                                                             <p>LKR. <%= room.getFine() %></p>
                                                         </div>
                                                         <% } %>


                            <div id="room-images-<%= safeId %>" style="display: none;">
                                <%= String.join(",", uniqueImages) %>
                            </div>

                            <div class="action-buttons">

                                   <button class="btn btn-book"
                                           onclick="openBookingModal('<%= room.getUniqueId() %>', '<%= room.getPrice() %>', '<%= room.getRoomType() %>')">
                                       <i class="fas fa-calendar-check"></i> Book Now
                                   </button>

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
        let checkInPicker = null;
        let checkOutPicker = null;

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


       let unavailableDates = [];

       async function openBookingModal(roomId, price, category) {
           unavailableDates = [];

           document.getElementById('modalRoomId').value = roomId;
           document.getElementById('modalRoomPrice').value = price;
           document.getElementById('modalRoomCategory').value = category;
           document.getElementById('bookingModal').style.display = "block";

           // Set min date to Today
           const today = new Date().toISOString().split('T')[0];
           const checkInInput = document.getElementById('checkIn');
           const checkOutInput = document.getElementById('checkOut');

           checkInInput.min = today;
           checkOutInput.min = today;
           checkInInput.value = '';
           checkOutInput.value = '';
           resetTotals();

           try {
               const response = await fetch(`${contextPath}/Customer/GetBookedDates?roomId=\${encodeURIComponent(roomId)}`);
               unavailableDates = await response.json();
               console.log("Booked dates for this room:", unavailableDates);

               // Initialize Flatpickr with disabled dates
               initializeDatePickers();

           } catch (err) {
               console.error("Failed to fetch booked dates", err);
           }
       }

      function initializeDatePickers() {
          const checkInInput = document.getElementById('checkIn');
          const checkOutInput = document.getElementById('checkOut');

          // Prepare disabled date ranges from your database
          const disabledRanges = unavailableDates.map(range => ({
              from: range.checkIn,
              to: range.checkOut
          }));

          // Initialize check-in date picker
          checkInPicker = flatpickr(checkInInput, {
              minDate: "today",
              dateFormat: "Y-m-d",
              disable: disabledRanges,
              onChange: function(selectedDates, dateStr) {
                  if (selectedDates.length > 0) {
                      // 1. Set the minimum check-out date to the day after check-in
                      const nextDay = new Date(selectedDates[0]);
                      nextDay.setDate(nextDay.getDate() + 1);

                      checkOutPicker.set('minDate', nextDay);

                      // 2. Clear the check-out value to force a new selection
                      checkOutPicker.clear();
                      resetTotals();

                      // 3. Open the check-out picker automatically for better UX
                      setTimeout(() => checkOutPicker.open(), 100);
                  }
              }
          });

          // Initialize check-out date picker
          checkOutPicker = flatpickr(checkOutInput, {
              // Initially, check-out cannot be before tomorrow
              minDate: new Date().fp_incr(1),
              dateFormat: "Y-m-d",
              disable: disabledRanges,
              onChange: function(selectedDates, dateStr) {
                  calculateTotal();
              }
          });
      }

      function calculateTotal() {
          const checkInVal = document.getElementById('checkIn').value;
          const checkOutVal = document.getElementById('checkOut').value;
          const pricePerNight = parseFloat(document.getElementById('modalRoomPrice').value) || 0;

          if (checkInVal && checkOutVal) {
              const userIn = new Date(checkInVal);
              const userOut = new Date(checkOutVal);

              if (userOut > userIn) {
                  const diffTime = Math.abs(userOut - userIn);
                  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));

                  document.getElementById('displayDays').value = diffDays;
                  document.getElementById('displayTotal').value = (diffDays * pricePerNight).toFixed(2);
              } else {
                  resetTotals();
              }
          }
      }

        function closeBookingModal() {
            document.getElementById('bookingModal').style.display = "none";
            document.getElementById('bookingForm').reset();
            toggleCardDetails(false);
        }

        function resetTotals() {
            document.getElementById('displayDays').value = "0";
            document.getElementById('displayTotal').value = "0.00";
        }

        function toggleCardDetails(show) {
            document.getElementById('cardDetails').style.display = show ? "block" : "none";
        }
    </script>

    <div id="bookingModal" class="modal">
        <div class="modal-content booking-form-card">
            <span class="close" onclick="closeBookingModal()">&times;</span>
            <h2>Confirm Your Booking</h2>
            <form id="bookingForm" action="ProcessBooking" method="POST">
                <input type="hidden" name="roomId" id="modalRoomId">
                <input type="hidden" name="roomPrice" id="modalRoomPrice">
                <input type="hidden" name="roomCategory" id="modalRoomCategory">


                <div class="form-group">
                    <label>Check-in Date:</label>
                    <input type="date" name="checkIn" id="checkIn" required onchange="calculateTotal()">
                </div>

                <div class="form-group">
                    <label>Check-out Date:</label>
                    <input type="date" name="checkOut" id="checkOut" required onchange="calculateTotal()">
                </div>

               <div class="booking-summary">
                   <div class="summary-item">
                       <label>Total Days</label>
                       <div class="input-wrapper">
                           <input type="text" name="totalDays" id="displayDays" value="0" readonly>
                           <span>Days</span>
                       </div>
                   </div>

                   <div class="summary-item">
                       <label>Total Price</label>
                       <div class="input-wrapper">
                           <span class="currency">LKR</span>
                           <input type="text" name="totalPrice" id="displayTotal" value="0.00" readonly class="total-amount">
                       </div>
                   </div>
               </div>

                <div class="payment-section">
                    <h3>Payment Method</h3>
                    <div class="payment-options">
                        <label><input type="radio" name="paymentMethod" value="cash" checked onclick="toggleCardDetails(false)"> Cash</label>
                        <label><input type="radio" name="paymentMethod" value="card" onclick="toggleCardDetails(true)"> Debit/Credit Card</label>
                    </div>

                    <div id="cardDetails" style="display: none; margin-top: 15px;">
                        <input type="text" placeholder="Card Number" class="form-input">
                        <div style="display: flex; gap: 10px; margin-top: 10px;">
                            <input type="text" placeholder="MM/YY" style="flex: 1;" class="form-input">
                            <input type="text" placeholder="CVV" style="flex: 1;" class="form-input">
                        </div>
                    </div>
                </div>

                <button type="submit" class="btn btn-book" style="width: 100%; margin-top: 20px;">
                    Confirm & Pay
                </button>
            </form>
        </div>
    </div>
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