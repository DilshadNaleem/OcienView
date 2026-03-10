<%@page import="java.net.URLEncoder"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%> <!-- -->
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Ocean View</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.2/css/all.min.css">
     <link rel="stylesheet" href="./css/Dashboard.css">

</head>
<body>
    <header>
        <div class="top-bar">
            <div class="auth-links">
                <a class="auth-link dropdown-toggle">
                    <span class="account-icon"><i class="fas fa-user"></i> My Account</span>
                    <div class="dropdown-content">
                        <a href="#">Welcome, User</a>
                        <a href="/Customer/EditProfile">Profile</a>
                        <a href="/Customer/History">My Orders</a>
                        <a href="/Customer/Guidelines.html">Guidelines</a>
                        <a href="/Customer/Signing.jsp">Logout</a>
                    </div>
                </a>
                <a href="/Customer/Guidelines.html" class="auth-link"><span>Guidelines</span><i class="fas fa-tag"></i></a>
                <a href="/Customer/Contact.html" class="auth-link"><span>Help & Contact</span><i class="fas fa-question-circle"></i></a>
            </div>
        </div>

        <div class="logo-search">
            <a href="#" class="logo">Ocean View Resort</a>
                    </div>

        <nav class="main-menu" id="navbar">
            <ul>
                <li><a href="/Customer/About.html">About Us</a></li>

                <!-- Categories should be dynamically generated here -->
            </ul>
        </nav>

        <div class="menu-icon" onclick="toggleSidebar()">?</div>
        <div class="sidebar" id="sidebar">
            <nav>
                <ul>
                    <a href="javascript:void(0)" class="closebtn" onclick="closeSidebar()">×</a>
                    <li><a href="about.php">About Us</a></li>
                    <li><a href="#">Saved</a></li>
                    <!-- Categories should be dynamically generated here -->
                </ul>
            </nav>
        </div>
    </header>




 <!-- Image slider container -->
    <div class="image-slider">
        <img src="/Slider/slider.jpg" alt="Image 1" class="slider-image">
        <div class="slide-content" id="slide1"></div>

        <img src="/Slider/sliders2.jpg" alt="Image 2" class="slider-image">
        <div class="slide-content" id="slide2"></div>

        <img src="/Slider/slider3.jpg" alt="Image 2" class="slider-image">
         <div class="slide-content" id="slide2"></div>

        <!-- Slider buttons -->
        <button class="slider-button prev-button">&#10094;</button>
        <button class="slider-button next-button">&#10095;</button>
    </div>


<div class="category-section">
    <h2>Explore Popular Room Categories</h2>
    <div class="categories">
        <%
        // Get the vehicles list from the request attribute
        List<Map<String, String>> vehicles = (List<Map<String, String>>) request.getAttribute("rooms");

        // Check if vehicles list is null or empty
        if (vehicles == null || vehicles.isEmpty()) {
        %>
            <p>No Room Categories found in the database.</p>
        <%
        } else {
            // Loop through each vehicle and display its information
            for (Map<String, String> vehicle : vehicles) {
        %>
            <!-- The link redirects to Vehicle_Category.jsp with the vehicle name as a query parameter -->
            <a href="/Customer/RoomProducts?roomType=<%= URLEncoder.encode(vehicle.get("roomName"), "UTF-8") %>" class="category-item">
                <div>
                    <h3><%= vehicle.get("roomName") %></h3>
                    <img src="http://localhost:8080/<%= vehicle.get("image") %>" alt="<%= vehicle.get("roomName") %>">
                </div>
            </a>
        <%
            } // End of for loop
        }
        %>
    </div>
</div>



</body>


<script>
    let currentIndex = 0;
    const images = document.querySelectorAll('.slider-image');
    const slideContents = document.querySelectorAll('.slide-content');
    const prevButton = document.querySelector('.prev-button');
    const nextButton = document.querySelector('.next-button');

    function changeSlide(direction) {
        // Hide the current image by moving it out of view
        images[currentIndex].style.left = direction === 'next' ? '-100%' : '100%';
        images[currentIndex].style.zIndex = 1;  // Move the current image behind
        slideContents[currentIndex].classList.remove('active'); // Hide the current content

        // Move the next image into view
        const nextIndex = (currentIndex + (direction === 'next' ? 1 : -1) + images.length) % images.length;
        images[nextIndex].style.left = '0';
        images[nextIndex].style.zIndex = 2;  // Bring the next image to the front
        slideContents[nextIndex].classList.add('active'); // Show the next content

        // Update current index
        currentIndex = nextIndex;
    }

    // Button click listeners
    prevButton.addEventListener('click', () => changeSlide('prev'));
    nextButton.addEventListener('click', () => changeSlide('next'));

    // Initially show the first image and content
    images[currentIndex].style.left = '0';
    images[currentIndex].style.zIndex = 2;
    slideContents[currentIndex].classList.add('active');

    // Auto change slide every 5 seconds
    setInterval(() => changeSlide('next'), 5000);
</script>
</html>
