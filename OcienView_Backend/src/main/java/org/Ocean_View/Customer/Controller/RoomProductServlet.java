package org.Ocean_View.Customer.Controller;

import java.io.IOException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Admin.Entity.Room;
import org.Ocean_View.Admin.DTO.RoomDao;

@WebServlet("/Customer/RoomProducts")
public class RoomProductServlet extends HttpServlet {
    private RoomDao roomDao;

    @Override
    public void init() throws ServletException {
        roomDao = new RoomDao(); // Make sure you have RoomDao class
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String roomType = request.getParameter("roomType");

        if (roomType != null && !roomType.trim().isEmpty()) {
            try {
                // Get rooms by type from database
                List<Room> rooms = roomDao.getRoomsByType(roomType);

                // Set the rooms list as request attribute
                request.setAttribute("rooms", rooms);

                // Forward to JSP
                request.getRequestDispatcher("/Customer/RoomCategory.jsp").forward(request, response);

            } catch (Exception e) {
                e.printStackTrace();
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                        "Error retrieving room details");
            }
        } else {
            // If no room type specified, redirect to home
            response.sendRedirect("index.jsp");
        }
    }
}