<%@page import = "org.Ocean_View.Connection.DatabaseConnection" %>
<%@page import = "java.sql.*" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Room</title>
    <!-- Bootstrap CSS link -->
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome for icons -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body {
            font-family: Arial, sans-serif;
            background-color: #f8f9fa;
        }
        .container {
            width: 40%;
            margin: auto;
            padding: 20px;
            border: 1px solid #ccc;
            border-radius: 5px;
            background-color: #ffffff;
        }
        h1 {
            text-align: center;
            color: #0073bb;
            margin-bottom: 30px;
        }
        label {
            font-weight: bold;
            font-size: 1rem;
        }
        input, select, textarea, button {
            width: 100%;
            padding: 12px;
            margin-top: 5px;
            border: 1px solid #ccc;
            border-radius: 5px;
            font-size: 1rem;
        }
        input::placeholder, textarea::placeholder {
            font-size: 1rem;
        }
        textarea {
            resize: vertical;
            min-height: 100px;
        }
        .form-group {
            margin-bottom: 20px;
        }

        /* Drag and Drop Styling */
        .drop-zone {
            border: 2px dashed #0073bb;
            border-radius: 10px;
            padding: 30px;
            text-align: center;
            cursor: pointer;
            transition: all 0.3s ease;
            background-color: #f8f9fa;
            margin-bottom: 20px;
        }

        .drop-zone:hover, .drop-zone.dragover {
            background-color: #e9f7fe;
            border-color: #0056b3;
        }

        .drop-zone i {
            font-size: 48px;
            color: #0073bb;
            margin-bottom: 15px;
        }

        .drop-zone p {
            margin: 10px 0;
            color: #666;
        }

        .file-input-label {
            display: inline-block;
            padding: 10px 20px;
            background-color: #0073bb;
            color: white;
            border-radius: 5px;
            cursor: pointer;
            transition: background-color 0.3s;
        }

        .file-input-label:hover {
            background-color: #0056b3;
        }

        .file-input {
            display: none;
        }

        /* Preview Container */
        .preview-container {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
            margin-top: 20px;
        }

        .image-preview {
            position: relative;
            width: 120px;
            height: 120px;
            border-radius: 8px;
            overflow: hidden;
            box-shadow: 0 2px 5px rgba(0,0,0,0.1);
        }

        .image-preview img {
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .remove-image {
            position: absolute;
            top: 5px;
            right: 5px;
            background-color: rgba(255,0,0,0.7);
            color: white;
            border: none;
            border-radius: 50%;
            width: 24px;
            height: 24px;
            cursor: pointer;
            display: flex;
            align-items: center;
            justify-content: center;
            font-size: 12px;
        }

        .remove-image:hover {
            background-color: rgba(255,0,0,0.9);
        }

        /* Selected files info */
        .selected-files-info {
            background-color: #e9f7fe;
            padding: 10px 15px;
            border-radius: 5px;
            margin-top: 10px;
            border-left: 4px solid #0073bb;
        }

        /* Buttons */
        .btn-primary {
            background-color: #0073bb;
            border: none;
            padding: 12px 30px;
            font-size: 16px;
            width: auto;
            height: auto;
            display: inline-block;
        }

        .btn-primary:hover {
            background-color: #0056b3;
        }

        .back-btn {
            background-color: #6c757d;
            border: none;
            padding: 10px 20px;
            cursor: pointer;
            font-size: 14px;
            display: inline-block;
            width: auto;
            margin-top: 20px;
            text-align: center;
        }

        .back-btn:hover {
            background-color: #545b62;
        }

        .button-container {
            display: flex;
            justify-content: space-between;
            margin-top: 30px;
        }

        a {
            display: block;
            text-align: center;
            margin-top: 20px;
            color: #0073bb;
        }

        a:hover {
            color: #5196c1;
        }

        /* Progress bar */
        .progress-container {
            margin-top: 20px;
            display: none;
        }

        .progress {
            height: 20px;
            border-radius: 10px;
            overflow: hidden;
        }

        .progress-bar {
            background-color: #0073bb;
            transition: width 0.3s;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1><i class="fas fa-plus-circle"></i> Add New Room</h1>

        <form id="addRoomForm" action="/Admin/AddRoom" method="post" enctype="multipart/form-data">

            <div class="form-group">
                <label for="description">Description:</label>
                <textarea id="description" name="description" placeholder="Enter Room Description" required></textarea>
            </div>

            <div class="form-group">
                <label for="noOfPeople">No of People:</label>
                <input type="text" id="noOfPeople" name="noOfPeople" placeholder="Enter No of People" required min="1">
            </div>

            <div class="form-group">
                <label for="facilities">Facilities:</label>
                <input type="text" id="facilities" name="facilities" placeholder="Enter Facilities (comma separated)" required>
            </div>

            <div class="form-group">
                <label for="fine">Fine:</label>
                <input type="number" id="fine" name="fine" placeholder="Enter Fine" step="0.01" required>
            </div>

            <div class="form-group">
                <label for="rules">Rules:</label>
                <textarea id="rules" name="rules" placeholder="Enter Rules" required></textarea>
            </div>

            <div class="form-group">
                <label for="roomType">Room Type:</label>
                <select id="roomType" name="roomType" required>
                    <%
                    Connection conn = null;
                    PreparedStatement stmt = null;
                    ResultSet rs = null;

                    try {
                        conn = DatabaseConnection.getConnection();
                        String query = "SELECT roomCategory FROM room_type";
                        stmt = conn.prepareStatement(query);
                        rs = stmt.executeQuery();

                        while (rs.next()) {
                            String categoryName = rs.getString("roomCategory");
                    %>
                        <option value="<%= categoryName %>"><%= categoryName %></option>
                    <%
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        try {
                            if (rs != null) rs.close();
                            if (stmt != null) stmt.close();
                            if (conn != null) conn.close();
                        } catch (SQLException ignore) {}
                    }
                    %>
                </select>
            </div>

            <div class="form-group">
                <label for="price">Rent per Day:</label>
                <input type="number" id="price" name="price" placeholder="Enter Rent per Day" step="0.01" required>
            </div>

            <!-- Multiple Image Upload with Drag & Drop -->
            <div class="form-group">
                <label>Room Images (Multiple):</label>

                <div class="drop-zone" id="dropZone">
                    <i class="fas fa-cloud-upload-alt"></i>
                    <p>Drag & drop images here</p>
                    <p>or</p>
                    <label class="file-input-label">
                        <i class="fas fa-folder-open"></i> Browse Files
                        <input type="file" id="roomImages" name="roomImages" class="file-input" accept="image/*" multiple>
                    </label>
                    <p class="text-muted" style="margin-top: 10px;">
                        <small>Supported formats: JPG, PNG, GIF. Max file size: 5MB</small>
                    </p>
                </div>

                <div id="selectedFilesInfo" class="selected-files-info" style="display: none;">
                    <p id="filesCount">0 files selected</p>
                </div>

                <!-- Image Preview Container -->
                <div id="imagePreview" class="preview-container"></div>

                <!-- Progress Bar (Optional) -->
                <div class="progress-container" id="progressContainer">
                    <div class="progress">
                        <div class="progress-bar" id="progressBar" role="progressbar" style="width: 0%;"></div>
                    </div>
                    <p id="progressText" class="text-center mt-2">Uploading...</p>
                </div>
            </div>

            <div class="button-container">
                <button type="submit" class="btn-primary">
                    <i class="fas fa-save"></i> Add Room
                </button>

                <button type="button" class="back-btn" onclick="window.history.back();">
                    <i class="fas fa-arrow-left"></i> Back
                </button>
            </div>
        </form>

        <a href="/Admin/ManageRooms">
            <i class="fas fa-eye"></i> View Added Rooms
        </a>
    </div>

    <!-- Bootstrap JS and dependencies -->
    <script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>

    <!-- Custom JavaScript for Drag & Drop -->
    <script>
        $(document).ready(function() {
            const dropZone = document.getElementById('dropZone');
            const fileInput = document.getElementById('roomImages');
            const imagePreview = document.getElementById('imagePreview');
            const selectedFilesInfo = document.getElementById('selectedFilesInfo');
            const filesCount = document.getElementById('filesCount');
            const progressContainer = document.getElementById('progressContainer');
            const progressBar = document.getElementById('progressBar');
            const progressText = document.getElementById('progressText');

            let uploadedFiles = [];

            // Prevent default drag behaviors
            ['dragenter', 'dragover', 'dragleave', 'drop'].forEach(eventName => {
                dropZone.addEventListener(eventName, preventDefaults, false);
                document.body.addEventListener(eventName, preventDefaults, false);
            });

            function preventDefaults(e) {
                e.preventDefault();
                e.stopPropagation();
            }

            // Highlight drop zone when item is dragged over it
            ['dragenter', 'dragover'].forEach(eventName => {
                dropZone.addEventListener(eventName, highlight, false);
            });

            ['dragleave', 'drop'].forEach(eventName => {
                dropZone.addEventListener(eventName, unhighlight, false);
            });

            function highlight(e) {
                dropZone.classList.add('dragover');
            }

            function unhighlight(e) {
                dropZone.classList.remove('dragover');
            }

            // Handle dropped files
            dropZone.addEventListener('drop', handleDrop, false);

            function handleDrop(e) {
                const dt = e.dataTransfer;
                const files = dt.files;
                handleFiles(files);
            }

            // Handle file input change
            fileInput.addEventListener('change', function() {
                handleFiles(this.files);
            });

            // Handle files from both drag & drop and file input
            function handleFiles(files) {
                [...files].forEach(file => {
                    if (file.type.startsWith('image/')) {
                        uploadedFiles.push(file);
                        previewImage(file);
                    }
                });

                updateFilesInfo();
                // Clear file input to allow selecting same files again
                fileInput.value = '';
            }

            // Create image preview
            function previewImage(file) {
                const reader = new FileReader();

                reader.onload = function(e) {
                    const previewDiv = document.createElement('div');
                    previewDiv.className = 'image-preview';

                    const img = document.createElement('img');
                    img.src = e.target.result;
                    img.alt = file.name;

                    const removeBtn = document.createElement('button');
                    removeBtn.type = 'button';
                    removeBtn.className = 'remove-image';
                    removeBtn.innerHTML = '<i class="fas fa-times"></i>';
                    removeBtn.title = 'Remove image';

                    removeBtn.onclick = function() {
                        // Remove file from array
                        const index = uploadedFiles.indexOf(file);
                        if (index > -1) {
                            uploadedFiles.splice(index, 1);
                        }

                        // Remove preview
                        previewDiv.remove();
                        updateFilesInfo();
                    };

                    previewDiv.appendChild(img);
                    previewDiv.appendChild(removeBtn);
                    imagePreview.appendChild(previewDiv);
                };

                reader.readAsDataURL(file);
            }

            // Update selected files information
            function updateFilesInfo() {
                const count = uploadedFiles.length;

                // FIXED: Removed JSP EL syntax and used pure JavaScript
                const pluralSuffix = count !== 1 ? 's' : '';
                filesCount.textContent = count + ' file' + pluralSuffix + ' selected';

                selectedFilesInfo.style.display = count > 0 ? 'block' : 'none';

                // Update form data before submission
                updateFormData();
            }

            // Update form data with files
            function updateFormData() {
                // Create a new FormData object
                const form = document.getElementById('addRoomForm');
                const formData = new FormData(form);

                // Clear existing files from form data
                formData.delete('roomImages');

                // Add all selected files
                uploadedFiles.forEach((file, index) => {
                    formData.append('roomImages', file);
                });

                // Update form submission
                form.dataset.formData = JSON.stringify(Array.from(formData.entries()));
            }

            // Handle form submission with AJAX for progress tracking
            const form = document.getElementById('addRoomForm');
            form.addEventListener('submit', function(e) {
                e.preventDefault();

                if (uploadedFiles.length === 0) {
                    alert('Please select at least one image for the room.');
                    return;
                }

                // Show progress bar
                progressContainer.style.display = 'block';
                progressBar.style.width = '0%';
                progressText.textContent = 'Uploading...';

                // Create FormData
                const formData = new FormData(this);

                // Add all uploaded files
                uploadedFiles.forEach(file => {
                    formData.append('roomImages', file);
                });

                // Send AJAX request
                const xhr = new XMLHttpRequest();

                xhr.upload.addEventListener('progress', function(e) {
                    if (e.lengthComputable) {
                        const percentComplete = (e.loaded / e.total) * 100;
                        progressBar.style.width = percentComplete + '%';
                        progressText.textContent = 'Uploading: ' + Math.round(percentComplete) + '%';
                    }
                });

                xhr.addEventListener('load', function() {
                    if (xhr.status === 200) {
                        progressBar.style.width = '100%';
                        progressText.textContent = 'Upload complete!';

                        // Handle success response
                        try {
                            const response = JSON.parse(xhr.responseText);
                            if (response.success) {
                                alert('Room added successfully with ID: ' + response.roomId);
                                window.location.href = '/Admin/AddRoom.jsp';
                            } else {
                                alert('Error: ' + response.message);
                            }
                        } catch (e) {
                            // If response is not JSON, assume it's HTML with script tag
                            document.open();
                            document.write(xhr.responseText);
                            document.close();
                        }
                    } else {
                        alert('Error uploading files. Please try again.');
                    }

                    // Hide progress bar after 2 seconds
                    setTimeout(() => {
                        progressContainer.style.display = 'none';
                    }, 2000);
                });

                xhr.addEventListener('error', function() {
                    alert('Error uploading files. Please check your connection.');
                    progressContainer.style.display = 'none';
                });

                xhr.open('POST', this.action);
                xhr.send(formData);
            });

            // Initialize
            updateFilesInfo();
        });
    </script>
</body>
</html>