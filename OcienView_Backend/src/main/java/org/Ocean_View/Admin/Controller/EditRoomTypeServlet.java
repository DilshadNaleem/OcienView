package org.Ocean_View.Admin.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Admin.DTO.RoomType;
import org.Ocean_View.Admin.Services.FileUploader;
import org.Ocean_View.Admin.Services.Implementation.ManageRoomTypeImpl;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoomType;

import java.io.IOException;

@WebServlet("/Admin/UpdateRoomType")
public class EditRoomTypeServlet extends HttpServlet {
    private ManageRoomType manageRoomType;

    @Override
    public void init() throws ServletException {
        // Assuming your FileUploader and Implementation handle the logic
        FileUploader fileUploader = new FileUploader();
        manageRoomType = new ManageRoomTypeImpl(fileUploader);
    }

    /**
     * Handles the POST request sent by the Modal Form in ManageRoomTypes.jsp
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // 1. Extract data from the modal form fields (name attributes)
        String uniqueId = req.getParameter("uniqueId");
        String roomCategory = req.getParameter("roomCategory");
        String description = req.getParameter("description");
        String image = req.getParameter("image");

        // 2. Create a DTO object with the updated details
        RoomType updatedRoom = new RoomType();
        updatedRoom.setUniqueId(uniqueId);
        updatedRoom.setRoomCategory(roomCategory);
        updatedRoom.setDescription(description);
        updatedRoom.setImage(image);

        // 3. Call service to update the database
        boolean isUpdated = manageRoomType.updateRoomType(updatedRoom);

        // 4. Redirect back to the list page with status messages
        if (isUpdated) {
            req.getSession().setAttribute("successMessage", "Room type updated successfully!");
        } else {
            req.getSession().setAttribute("errorMessage", "Failed to update room type.");
        }

        // Redirect back to the controller that loads the ManageRoomTypes page
        resp.sendRedirect(req.getContextPath() + "/Admin/ManageRoomTypes");
    }

    /**
     * Keep doGet only if you still want to support direct URL access,
     * but usually, with Modals, everything happens via POST.
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // If someone hits this URL directly, just send them back to the list
        resp.sendRedirect(req.getContextPath() + "/Admin/ManageRoomTypes");
    }
}