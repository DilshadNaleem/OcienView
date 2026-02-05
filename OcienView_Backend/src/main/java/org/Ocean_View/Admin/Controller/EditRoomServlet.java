package org.Ocean_View.Admin.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.Ocean_View.Admin.Entity.Room;
import org.Ocean_View.Admin.Services.Implementation.ManageRoomImpl; // Adjust based on your actual package
import org.Ocean_View.Admin.Services.Interfaces.ManageRoom;

import java.io.IOException;

@WebServlet("/Admin/UpdateRoom")
public class EditRoomServlet extends HttpServlet {

    private ManageRoom manageRoom;

    @Override
    public void init() throws ServletException {
        // Initialize your service implementation
        manageRoom = new ManageRoomImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        // 1. Capture all parameters from the JSP Modal
        String uniqueId = req.getParameter("uniqueId");
        String categoryId = req.getParameter("roomCategoryId");
        String type = req.getParameter("roomType");
        String status = req.getParameter("status");
        String noOfPeople = req.getParameter("noOfPeople");
        String price = req.getParameter("price");
        String fine = req.getParameter("fine");
        String description = req.getParameter("description");
        String facilities = req.getParameter("facilities");
        String rules = req.getParameter("rules");

        // 2. Populate the Room DTO
        Room room = new Room();
        room.setUniqueId(uniqueId);
        room.setRoomCategoryId(categoryId);
        room.setRoomType(type);
        room.setRoomStatus(status);
        room.setNoOfPeople(noOfPeople);
        room.setPrice(price);
        room.setFine(fine);
        room.setDescription(description);
        room.setFacilities(facilities);
        room.setRules(rules);

        // 3. Call the service to update the database
        boolean isUpdated = manageRoom.updateRoom(room);

        // 4. Handle response and feedback
        if (isUpdated) {
            req.getSession().setAttribute("successMessage", "Room " + uniqueId + " updated successfully!");
        } else {
            req.getSession().setAttribute("errorMessage", "Failed to update room " + uniqueId + ".");
        }

        // 5. Redirect back to the management page
        resp.sendRedirect(req.getContextPath() + "/Admin/ManageRooms");
    }
}