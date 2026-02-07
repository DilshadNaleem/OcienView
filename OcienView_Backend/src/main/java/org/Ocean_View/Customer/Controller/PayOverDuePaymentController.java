package org.Ocean_View.Customer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Customer.DTO.PayOverDuePaymentDTO;
import org.Ocean_View.Customer.Services.Implementations.PayOverDuePaymentServiceImpl;
import org.Ocean_View.Customer.Services.Interfaces.PayOverDuePaymentService;

import java.io.IOException;

@WebServlet("/Customer/PayOverdueFine")
public class PayOverDuePaymentController extends HttpServlet
{
        private PayOverDuePaymentService payOverDuePaymentService;

    @Override
    public void init() throws ServletException {
        payOverDuePaymentService = new PayOverDuePaymentServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try
        {
            String bookingId = req.getParameter("bookingId");
            String customerEmail = req.getParameter("customerEmail");
            String roomId = req.getParameter("roomId");
            Double calculatedFine = Double.valueOf(req.getParameter("calculatedFine"));
            Integer noOfDays = Integer.valueOf(req.getParameter("overdueDays"));
            String paymentMethod = req.getParameter("paymentMethod");

            System.out.println("Booking Id: " + bookingId + " " + "roomId " + roomId + " " + "calculatedFine " + calculatedFine + " " + "Overdue Days: " + noOfDays);

           PayOverDuePaymentDTO paymentDTO = new PayOverDuePaymentDTO();
           paymentDTO.setUniqueId(bookingId);
            paymentDTO.setBookingId(bookingId);
            paymentDTO.setCustomerEmail(customerEmail);
            paymentDTO.setRoomId(roomId);
            paymentDTO.setCalculatedFine(calculatedFine);
            paymentDTO.setOverdueDays(noOfDays);
            paymentDTO.setPaymentMethod(paymentMethod);

            String result = payOverDuePaymentService.getOverDuePaymentService(paymentDTO);

            if ("Success".equals(result)) {
                // Redirect to a success page or back to the dashboard with a success message
                resp.sendRedirect(req.getContextPath() + "/Customer/History");
                System.out.println("Success" + paymentDTO);
            } else {
                // Stay on the page or redirect to an error page
                req.setAttribute("errorMessage", result);
                req.getRequestDispatcher("/error.jsp").forward(req, resp);
                System.out.println("Error: " + result);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while processing the payment.");
        }
    }
}
