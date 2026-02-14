package org.Ocean_View.Customer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Services.Implementations.ReservationsImpl;
import org.Ocean_View.Customer.Services.Interfaces.Reservations;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Customer/SuccessRooms")
public class SuccessBooking extends HttpServlet
{
    Reservations reservations = new ReservationsImpl();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try
        {
            HttpSession session = req.getSession();
            PrintWriter writer = resp.getWriter();
            String id = req.getParameter("uniqueId");
            System.out.println("Print " + id);

            String result = reservations.updateSuccessStatusForSuccess(id);

            if ("Successfully Updated".equals(result))
            {
                writer.println("<script type='text/javascript'>");
                writer.println("alert('Booking status updated successfully!');");
                writer.println("window.location.href='" + req.getContextPath() + "/Customer/History';");
                writer.println("</script>");
                resp.sendRedirect(req.getContextPath() + "/Customer/History");
                return;
            }
            else
            {
                req.setAttribute("errorMessage", result);
                req.getRequestDispatcher("/error.jsp").forward(req,resp);
                System.out.println("Error: " + result);
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }

    }
}