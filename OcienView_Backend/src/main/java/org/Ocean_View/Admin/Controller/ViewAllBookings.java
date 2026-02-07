package org.Ocean_View.Admin.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Admin.Services.Implementation.BookingManagementServiceImpl;
import org.Ocean_View.Admin.Services.Interfaces.BookingManagementService;
import org.Ocean_View.Customer.Entity.Booking;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/Admin/ViewAllBookings")
public class ViewAllBookings extends HttpServlet
{
    private BookingManagementService bookingManagementService;

    @Override
    public void init() throws ServletException {
        bookingManagementService = new BookingManagementServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        List<Booking> bookingList = new ArrayList<>();

        try
        {
            bookingList = bookingManagementService.getAllBookings();
            req.setAttribute("bookingList", bookingList);
            req.getRequestDispatcher("/Admin/ViewAllBookings.jsp").forward(req,resp);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
