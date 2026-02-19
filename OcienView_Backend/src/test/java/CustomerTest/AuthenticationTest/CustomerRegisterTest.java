package CustomerTest.AuthenticationTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Controller.CustomerRegisterServlet;
import org.Ocean_View.Customer.Controller.CustomerRegistrationController;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;
import org.Ocean_View.Customer.Services.Interfaces.OTPService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class CustomerRegisterTest {

    @InjectMocks
    private CustomerRegisterServlet servlet;

    @Mock
    private CustomerRegistrationController mockController;

    @Mock
    private EmailService mockEmailService;

    @Mock
    private OTPService mockOtpService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpSession session;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Setup for capturing the output sent to the browser
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getMethod()).thenReturn("POST");
        // Setup session mocking
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testRegisterSuccess() throws Exception {
        // 1. Arrange: Setup parameters and mock behaviors
        when(request.getParameter("firstname")).thenReturn("John");
        when(request.getParameter("lastname")).thenReturn("Doe");
        when(request.getParameter("email")).thenReturn("john@example.com");
        when(request.getParameter("password")).thenReturn("pass123");
        when(request.getParameter("nic")).thenReturn("123456789V");
        when(request.getParameter("contact_number")).thenReturn("0771234567");

        when(mockController.registerCustomer(anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenReturn("Registration successful");
        when(mockOtpService.generateOTP()).thenReturn("123456");

        // 2. Act: Call the doPost method
        // Note: You might need to make doPost 'public' or use reflection if it's strictly 'protected'
        servlet.service(request, response);

        // 3. Assert: Verify the outcome
        String output = responseWriter.toString();
        assertTrue(output.contains("Registration Successful"));
        assertTrue(output.contains("/Customer/OTPVerification.jsp"));

        // Verify session interactions
        verify(session).setAttribute("otp", "123456");
        verify(mockEmailService).sendVerificationEmail(eq("John"), eq("Doe"), eq("john@example.com"), eq("123456"), eq(session));
    }

    @Test
    void testEmailAlreadyExists() throws Exception {
        // 1. Arrange
        when(request.getParameter("email")).thenReturn("duplicate@example.com");
        when(mockController.registerCustomer(any(), any(), any(), any(), any(), any()))
                .thenReturn("Email already exists");

        // 2. Act
        servlet.service(request, response);

        // 3. Assert
        String output = responseWriter.toString();
        assertTrue(output.contains("Email Already Exists"));
        verify(mockEmailService, never()).sendVerificationEmail(any(), any(), any(), any(), any());
    }

    @Test
    void testNicAlreadyExists() throws Exception {
        // 1. Arrange
        when(mockController.registerCustomer(any(), any(), any(), any(), any(), any()))
                .thenReturn("NIC already registered");

        // 2. Act
        servlet.service(request, response);

        // 3. Assert
        String output = responseWriter.toString();
        assertTrue(output.contains("NIC Already Exists"));
    }
}