package org.Ocean_View.Customer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Customer.Services.Implementations.CancelBookingImpls;
import org.Ocean_View.Customer.Services.Implementations.EmailServiceImpl;
import org.Ocean_View.Customer.Services.Interfaces.CancelBooking;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Customer/CancelRooms")
public class CancelBookingsController extends HttpServlet
{
    private CancelBooking cancelBooking;
    private EmailService emailService;

    @Override
    public void init() throws ServletException {
        this.cancelBooking = new CancelBookingImpls();
        this.emailService = new EmailServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try
        {
            PrintWriter writer = resp.getWriter();
            String  bookingId = req.getParameter("bookingId");
            String roomId = req.getParameter("roomId");
            String customerEmail = req.getParameter("customerEmail");

            System.out.println("Booking Id: " + bookingId + "Room ID: " + roomId +
                    "Customer Email: " + customerEmail);


            String result = cancelBooking.cancelBookingId(bookingId);

            if ("Success".equals(result))
            {
                emailService.sendCancellationEmail(customerEmail, bookingId);
                writer.println("<script type='text/javascript'>");
                writer.println("alert('Booking status updated successfully!');");
                writer.println("window.location.href='" + req.getContextPath() + "/Customer/History';");
                writer.println("</script>");
                return;
            }
            else
            {
                writer.println("<script type='text/javascript'>");
                writer.println("alert('Booking not Found!');");
                writer.println("window.location.href='" + req.getContextPath() + "/Customer/History';");
                writer.println("</script>");
            }
        }
        catch (Exception e)
        {
            e.getMessage();
        }
    }
}