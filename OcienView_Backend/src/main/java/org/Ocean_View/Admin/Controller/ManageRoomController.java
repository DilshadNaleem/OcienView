package org.Ocean_View.Admin.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.Ocean_View.Admin.Entity.Room;
import org.Ocean_View.Admin.Services.Implementation.ManageRoomImpl;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoom;
import org.Ocean_View.Connection.DatabaseConnection;
import org.json.JSONObject;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Admin/AddRoom")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1, // 1 MB
        maxFileSize = 1024 * 1024 * 10,      // 10 MB
        maxRequestSize = 1024 * 1024 * 50    // 50 MB
)
public class ManageRoomController extends HttpServlet {
    private ManageRoom manageRoom;

    @Override
    public void init() throws ServletException {
        manageRoom = new ManageRoomImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        JSONObject jsonResponse = new JSONObject();

        try {
            String roomType = req.getParameter("roomType");
            String roomId = null;

            // Get room type ID
            String query = "SELECT unique_id, roomCategory FROM room_type WHERE roomCategory = ?";
            try (Connection conn = DatabaseConnection.getConnection();
                 PreparedStatement ps = conn.prepareStatement(query)) {
                ps.setString(1, roomType);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        roomId = rs.getString("unique_id");
                    }
                }
            }

            // Generate new room unique ID
            String lastUniqueId = manageRoom.getLastUniqueId();
            String newUniqueId = generateNewUniqueId(lastUniqueId);

            // Handle multiple image uploads
            List<String> imagePaths = new ArrayList<>();
            String roomImageDirectory = "C:\\Users\\HP\\Desktop\\Shimer\\OcienView\\OcienView_Backend\\src\\main\\webapp\\Room_Images\\" + newUniqueId;



            // Create room-specific directory
            File roomDir = new File(roomImageDirectory);
            if (!roomDir.exists()) {
                roomDir.mkdirs();
            }

            // Process all uploaded images
            for (Part part : req.getParts()) {
                if (part.getName().equals("roomImages") && part.getSize() > 0) {
                    String fileName = getFileName(part);
                    if (fileName != null && !fileName.isEmpty()) {
                        String filePath = roomImageDirectory + File.separator + fileName;

                        // Save file to room-specific directory
                        try (InputStream inputStream = part.getInputStream();
                             OutputStream outputStream = new FileOutputStream(filePath)) {

                            byte[] buffer = new byte[4096];
                            int bytesRead;
                            while ((bytesRead = inputStream.read(buffer)) != -1) {
                                outputStream.write(buffer, 0, bytesRead);
                            }
                        }

                        // Store relative path
                        String relativePath = "/Room_Images/" + newUniqueId + "/" + fileName;
                        imagePaths.add(relativePath);
                    }
                }
            }

            // Create Room object
            Room room = new Room();
            room.setUniqueId(newUniqueId);
            room.setDescription(req.getParameter("description"));
            room.setNoOfPeople(req.getParameter("noOfPeople"));
            room.setFacilities(req.getParameter("facilities"));
            room.setRoomCategoryId(roomId);
            room.setFine(req.getParameter("fine"));
            room.setRules(req.getParameter("rules"));

            // Join image paths with comma separator
            String imagesString = String.join(",", imagePaths);
            room.setImages(imagesString);

            room.setRoomType(roomType);
            room.setPrice(req.getParameter("price"));

            // Save room to database
            manageRoom.saveRoom(room);

            // Return success response
            jsonResponse.put("success", true);
            jsonResponse.put("roomId", newUniqueId);
            jsonResponse.put("message", "Room added successfully with " + imagePaths.size() + " images");

        } catch (Exception e) {
            e.printStackTrace();
            jsonResponse.put("success", false);
            jsonResponse.put("message", "Error adding room: " + e.getMessage());
        }

        out.print(jsonResponse.toString());
        out.flush();
    }

    private String generateNewUniqueId(String lastUniqueId) {
        if (lastUniqueId == null) {
            return "RM001";
        }

        try {
            String prefix = "RM";
            int number = Integer.parseInt(lastUniqueId.substring(2));
            number++;
            return prefix + String.format("%03d", number);
        } catch (Exception e) {
            return "RM001";
        }
    }

    private String getFileName(Part part) {
        String contentDisposition = part.getHeader("content-disposition");
        for (String cd : contentDisposition.split(";")) {
            if (cd.trim().startsWith("filename")) {
                return cd.substring(cd.indexOf('=') + 1).trim().replace("\"", "");
            }
        }
        return null;
    }
}