package CustomerTest.BookingTest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Customer.Controller.PayOverDuePaymentController;
import org.Ocean_View.Customer.DTO.PayOverDuePaymentDTO;
import org.Ocean_View.Customer.Services.Interfaces.PayOverDuePaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class PayOverDuePaymentTest
{
    @InjectMocks
    private PayOverDuePaymentController controller;

    @Mock
    private PayOverDuePaymentService payOverDuePaymentService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher requestDispatcher;

    @Mock
    private HttpServletRequest request;

    private StringWriter responseWriter;

    @Mock
    private PayOverDuePaymentDTO paymentDTO;

    private String bookingId = "bookingId";
    private String customerEmail = "dilshadnaleem13@gmail.com";
    private String roomId  = "roomId";
    private Double calculatedFine = 5000.0;
    private Integer noOfDays = 2;
    private String paymentMethod = "paymentMethod";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        paymentDTO = new PayOverDuePaymentDTO();
    }

    @Test
    void successPaymentOverDuePayment() throws Exception {
        when(request.getParameter("bookingId")).thenReturn(bookingId);
        when(request.getParameter("customerEmail")).thenReturn(customerEmail);
        when(request.getParameter("roomId")).thenReturn(roomId);
        when(request.getParameter("calculatedFine")).thenReturn(calculatedFine.toString());
        when(request.getParameter("overdueDays")).thenReturn(noOfDays.toString());
        when(request.getParameter("paymentMethod")).thenReturn(paymentMethod);
        when(request.getContextPath()).thenReturn("");

        when(request.getMethod()).thenReturn("POST");

        when(payOverDuePaymentService.getOverDuePaymentService(any(PayOverDuePaymentDTO.class))).thenReturn("Success");

        controller.service(request, response);
        verify(response).sendRedirect("/Customer/History");

        verify(payOverDuePaymentService).getOverDuePaymentService(any(PayOverDuePaymentDTO.class));
    }

    @Test
    void failurePaymentOverDuePayment() throws Exception {

        when(request.getParameter("customerEmail")).thenReturn(customerEmail);
        when(request.getParameter("roomId")).thenReturn(roomId);
        when(request.getParameter("calculatedFine")).thenReturn(calculatedFine.toString());
        when(request.getParameter("overdueDays")).thenReturn(noOfDays.toString());
        when(request.getParameter("paymentMethod")).thenReturn(paymentMethod);
        when(request.getMethod()).thenReturn("POST");


        when(payOverDuePaymentService.getOverDuePaymentService(any(PayOverDuePaymentDTO.class)))
                .thenReturn("Error: Payment Failed");

        when(request.getRequestDispatcher("/error.jsp")).thenReturn(requestDispatcher);

        controller.service(request, response);

        verify(requestDispatcher).forward(request, response);

        verify(request).setAttribute("errorMessage", "Error: Payment Failed");
    }
}
