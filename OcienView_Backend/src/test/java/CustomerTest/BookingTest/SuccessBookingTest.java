package CustomerTest.BookingTest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Controller.SuccessBooking;
import org.Ocean_View.Customer.Services.Interfaces.Reservations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class SuccessBookingTest
{
    @InjectMocks
    private SuccessBooking controller;

    @Mock
    private Reservations reservations;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher dispatcher;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testSuccessBooking() throws Exception {
        String uniqueId = "uniqueId";
        String contextPath = "";

        when(request.getParameter("uniqueId")).thenReturn(uniqueId);
        when(request.getContextPath()).thenReturn(contextPath);

        when(reservations.updateSuccessStatusForSuccess(uniqueId)).thenReturn("Successfully Updated");

        controller.service(request, response);

        verify(reservations).updateSuccessStatusForSuccess(uniqueId);

        String result = responseWriter.toString();
        assert(result.contains("Booking status updated successfully!"));
        verify(response).sendRedirect(contextPath + "/Customer/History");
    }


    @Test
    void testFailing() throws Exception {
        String uniqueId = "uniqueID";
        String errorMessage = "Database Connection Failed";

        when(request.getParameter("uniqueId")).thenReturn(uniqueId);
        when(reservations.updateSuccessStatusForSuccess(uniqueId)).thenReturn(errorMessage);
        when(request.getRequestDispatcher("/error.jsp")).thenReturn(dispatcher);

        controller.service(request, response);

        verify(reservations).updateSuccessStatusForSuccess(uniqueId);
        verify(request).setAttribute("errorMessage", errorMessage);

        verify(dispatcher).forward(request, response);
    }
}
