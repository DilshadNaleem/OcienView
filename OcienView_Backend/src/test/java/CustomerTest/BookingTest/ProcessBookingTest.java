package CustomerTest.BookingTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Controller.ProcessBookingServlet;
import org.Ocean_View.Customer.Entity.Booking;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;
import org.Ocean_View.Customer.Services.Interfaces.Reservations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.awt.print.Book;
import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class ProcessBookingTest
{
    @InjectMocks
    private ProcessBookingServlet controller;

    @Mock
    private Reservations reservations;

    @Mock
    private EmailService emailService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private Booking booking;
    @Mock
    private HttpSession session;

    private StringWriter responseWriter;

    private String roomId = "RM007";
    private String roomPrice = "roomPrice";
    private String checkIn = "checkIn";
    private String checkOut = "checkOut";
    private String totalDays = "totalDays";
    private String totalPrice = "totalPrice";
    private String paymentMethod = "paymentMethod";
    private String roomCategory = "roomCategory";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);

        booking = new Booking();

        // Setting the inputs
        when(request.getParameter("roomId")).thenReturn(roomId);
        when(request.getParameter("roomPrice")).thenReturn(roomPrice);
        when(request.getParameter("checkIn")).thenReturn(checkIn);
        when(request.getParameter("checkOut")).thenReturn(checkOut);
        when(request.getParameter("totalDays")).thenReturn("totalDays");
        when(request.getParameter("totalPrice")).thenReturn(totalPrice);
        when(request.getParameter("paymentMethod")).thenReturn(paymentMethod);
        when(request.getParameter("roomCategory")).thenReturn(roomCategory);

    }

    @Test
    void testSuccessBooking() throws Exception {
        // 1. Arrange - Must mock session email
        when(session.getAttribute("email")).thenReturn("test@example.com");

        // 2. Arrange - Must provide valid date strings (to avoid ParseException)
        when(request.getParameter("checkIn")).thenReturn("2026-05-01");
        when(request.getParameter("checkOut")).thenReturn("2026-05-05");
        when(request.getParameter("totalPrice")).thenReturn("500.0");

        // 3. Arrange - Mock Service calls
        when(reservations.getPaymentLastUniqueId()).thenReturn("PAYMENT-001");
        when(reservations.getLastUniqueId()).thenReturn("BOOKING-001");
        when(reservations.saveReservations(any(Booking.class))).thenReturn("Reservation Successful");

        // 4. Act
        controller.service(request, response);

        // 5. Assert - Check if the JavaScript redirect was written to the output
        String resultOutput = responseWriter.toString();
        assertTrue(resultOutput.contains("window.location.href = 'BookingSuccess.jsp?id="));

        // Verify interactions
        verify(reservations).saveReservations(any(Booking.class));
        verify(emailService).sendBookingConfirmation(any(), any());
    }

    @Test
    void testFailureBooking() throws Exception {
        // Arrange
        when(session.getAttribute("email")).thenReturn("test@example.com");
        when(request.getParameter("checkIn")).thenReturn("2026-05-01");
        when(request.getParameter("checkOut")).thenReturn("2026-05-05");
        when(request.getParameter("totalPrice")).thenReturn("500.0");

        // Mock a failure response
        when(reservations.saveReservations(any(Booking.class))).thenReturn("Database Error");

        // Act
        controller.service(request, response);

        // Assert
        String resultOutput = responseWriter.toString();
        assertTrue(resultOutput.contains("alert('Booking Failed: Database Error');"));
        assertTrue(resultOutput.contains("history.back();"));
    }
}
