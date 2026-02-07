package org.Ocean_View.Admin.Controller.RoomManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Admin.Entity.Room;
import org.Ocean_View.Admin.Services.Implementation.ManageRoomImpl;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoom;

import java.io.IOException;
import java.util.List;

@WebServlet("/Admin/ManageRooms")
public class ManageRoomsServlet extends HttpServlet
{
    private ManageRoom manageRoom;
    @Override
    public void init() throws ServletException {
        manageRoom = new ManageRoomImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Room> rooms = manageRoom.getAllRooms();
        req.setAttribute("room", rooms);
        req.getRequestDispatcher("/Admin/ManageRooms.jsp").forward(req, resp);
    }
}
