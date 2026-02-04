package org.Ocean_View.Customer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Entity.Booking;
import org.Ocean_View.Customer.Services.Implementations.EmailServiceImpl;
import org.Ocean_View.Customer.Services.Implementations.ReservationsImpl;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;
import org.Ocean_View.Customer.Services.Interfaces.Reservations;
import org.Ocean_View.Customer.Services.PaymentUniqueID;
import org.Ocean_View.Customer.Services.ReservationUniqueID;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@WebServlet("/Customer/ProcessBooking")
public class ProcessBookingServlet extends HttpServlet {
    private Reservations reservations;
    private EmailService emailService;

    public ProcessBookingServlet() {
        this.reservations = new ReservationsImpl();
        this.emailService = new EmailServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            // 1. Retrieve Form Data
            String roomId = req.getParameter("roomId");
            String roomPriceStr = req.getParameter("roomPrice");
            String checkInStr = req.getParameter("checkIn");
            String checkOutStr = req.getParameter("checkOut");
            String totalDays = req.getParameter("totalDays");
            String totalPriceStr = req.getParameter("totalPrice");
            String paymentMethod = req.getParameter("paymentMethod");
            String roomCategory = req.getParameter("roomCategory");
            // Debugging log
            System.out.println("Processing Booking for Room: " + roomId);
            System.out.println("RoomCategory: " + roomCategory);

            // Validation: Ensure roomId exists
            if (roomId == null || roomId.isEmpty()) {
                out.println("<script>alert('Error: Room ID is missing. Please try again.'); history.back();</script>");
                return;
            }

            // 2. Session Check
            HttpSession session = req.getSession();
            String email = (String) session.getAttribute("email");
            if (email == null || email.isEmpty()) {
                out.println("<script>alert('Session expired. Please log in again.');" +
                        "window.location.href = 'Signing.jsp';</script>");
                return;
            }

            // 3. Date Parsing (Convert String to java.sql.Date for the DB)
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            java.sql.Date sqlInDate = new java.sql.Date(sdf.parse(checkInStr).getTime());
            java.sql.Date sqlOutDate = new java.sql.Date(sdf.parse(checkOutStr).getTime());

            String PaymentLastUniqueId = reservations.getPaymentLastUniqueId();
            String paymentId = PaymentUniqueID.generatePaymentUniqueID(PaymentLastUniqueId);

            String ReservationID = reservations.getLastUniqueId();
            String bookingId = ReservationUniqueID.generateReservationUniqueId(ReservationID);
            // 5. Populate the Booking Entity
            Booking booking = new Booking();
            booking.setUniqueId(bookingId);
            booking.setCustomerEmail(email); // Service will use this to fetch Name/Phone
            booking.setRoomId(roomId);
            booking.setRoomCategory(roomCategory);
            booking.setInDate(sqlInDate);
            booking.setOutDate(sqlOutDate);
            booking.setNoOfDays(totalDays);
            booking.setPrice(Double.parseDouble(totalPriceStr));
            booking.setPaymentUniqueId(paymentId);
            booking.setPaymentMethod(paymentMethod);

            // You might want to fetch these from the DB or a hidden field if available
            booking.setRoomCategory(roomCategory); // Ensure this is sent from JSP
            booking.setFine("0.00"); // Set default or fetch from room table

            // 6. Call the Service Layer
            String result = reservations.saveReservations(booking);
            emailService.sendBookingConfirmation(email,booking);


            // 7. Handle Response
            if ("Reservation Successful".equals(result)) {
                out.println("<script>");
                out.println("alert('Booking Confirmed! Your Booking ID is: " + bookingId + "');");
                out.println("window.location.href = 'BookingSuccess.jsp?id=" + bookingId + "';");
                out.println("</script>");
            } else {
                out.println("<script>");
                out.println("alert('Booking Failed: " + result + "');");
                out.println("history.back();");
                out.println("</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.println("<script>alert('An internal error occurred: " + e.getMessage() + "'); history.back();</script>");
        }
    }


}