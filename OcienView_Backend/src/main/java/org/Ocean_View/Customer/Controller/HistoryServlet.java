package org.Ocean_View.Customer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Entity.Booking;
import org.Ocean_View.Customer.Services.Implementations.HistoryServiceImpl;
import org.Ocean_View.Customer.Services.Interfaces.HistoryService;

import java.io.IOException;
import java.util.List;

@WebServlet("/Customer/History")
public class HistoryServlet extends HttpServlet {
    private final HistoryService historyService = new HistoryServiceImpl();


    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        // Assuming email is stored in session after login
        String email = (String) session.getAttribute("email");

        if (email != null) {
            List<Booking> bookings = historyService.fetchHistoryByEmail(email, session);
            request.setAttribute("bookingHistory", bookings);
            System.out.println("booking first name : " + bookings.toString());
            request.getRequestDispatcher("/Customer/CustomerHistory.jsp").forward(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/Customer/Signing.jsp");
        }
    }
}