package org.Ocean_View.Admin.Controller.RoomManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Admin.Services.Implementation.ManageRoomImpl;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoom;

import java.io.IOException;

@WebServlet("/Admin/DeleteRoom")
public class DeleteRoomServlet extends HttpServlet
{
    private ManageRoom manageRoom;

    @Override
    public void init() throws ServletException {
        manageRoom = new ManageRoomImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String uniqueId = req.getParameter("uniqueId");

        boolean isDeleted = manageRoom.getRoomById(uniqueId);

        if(isDeleted)
        {
            req.getSession().setAttribute("successMessage", "Room type deleted Successfully!");
        } else
        {
            req.getSession().setAttribute("errorMessage", "Failed to delete room type.");
        }

        resp.sendRedirect(req.getContextPath() + "/Admin/ManageRooms");
    }
}
