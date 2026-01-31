package org.Ocean_View.Customer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Connection.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet("/Customer/Dashboard")
public class DashboardServlet extends HttpServlet
{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");

        try(Connection conn = DatabaseConnection.getConnection())
        {
            if (conn == null)
            {
                req.setAttribute("error", "Coudlnt connect to the database");
                return;
            }

            String query = "SELECT roomCategory,image FROM room_type";

            List<Map<String,String>> rooms = new ArrayList<>();

            try(PreparedStatement pstmt = conn.prepareStatement(query);
                ResultSet rs = pstmt.executeQuery())
            {
                while (rs.next()) {
                    Map<String,String> room = new HashMap<>();
                    room.put("roomName", rs.getString("roomCategory"));
                    room.put("image", rs.getString("image"));
                    System.out.println( "Image Path " + rs.getString("image"));
                    rooms.add(room);
                }
            }

            req.setAttribute("rooms", rooms);
            req.getRequestDispatcher("/Customer/Dashboard.jsp").forward(req,resp);
        }

        catch (SQLException e)
        {
            req.setAttribute("error", "Could not fetch room data.");
            e.printStackTrace();
        }

        catch (Exception e)
        {
            req.setAttribute("error", "An unexpected error occurred: " + e.getMessage());
        }

    }
}
