package CustomerTest.BookingTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Customer.Controller.CancelBookingsController;
import org.Ocean_View.Customer.Services.Interfaces.CancelBooking;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;


import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CancelBookingTest
{
    @InjectMocks
    private CancelBookingsController controller;

    @Mock
    private CancelBooking cancelBooking;

    @Mock
    private EmailService emailService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private StringWriter responseWriter;
    private String bookingId = "booking_Id";
    private String roomId = "room_id";
    private String customerEmail = "customerEmail";


    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.openMocks(this);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
    }


    @Test
    void testSuccessCancelBooking() throws Exception{
        when(request.getParameter("bookingId")).thenReturn(bookingId);
        when(request.getParameter("roomId")).thenReturn(roomId);
        when(request.getParameter("customerEmail")).thenReturn(customerEmail);

        when(request.getMethod()).thenReturn("POST");

        when(cancelBooking.cancelBookingId(bookingId)).thenReturn("Success");

        controller.service(request, response);

        verify(cancelBooking).cancelBookingId(bookingId);
        verify(emailService).sendCancellationEmail(customerEmail, bookingId);

        String result = responseWriter.toString();
        assertTrue(result.contains("Booking status updated successfully!"),
                "Expected success message in response but got: " + result);
    }

    @Test
    void testFailureCancelBooking() throws Exception {

        when(request.getParameter("bookingId")).thenReturn(bookingId);
        when(request.getParameter("roomId")).thenReturn(roomId);
        when(request.getParameter("customerEmail")).thenReturn(customerEmail);

        when(request.getMethod()).thenReturn("POST");

        when(cancelBooking.cancelBookingId(bookingId)).thenReturn("Booking not found");

        controller.service(request, response);

        String result = responseWriter.toString();
        assertTrue(result.contains("Booking not Found!"));
    }



}
