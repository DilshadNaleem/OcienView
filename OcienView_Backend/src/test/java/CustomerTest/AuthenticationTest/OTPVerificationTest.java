package CustomerTest.AuthenticationTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Controller.OTPVerificationServlet;
import org.Ocean_View.Customer.Services.Implementations.OTPServiceImpl;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class OTPVerificationTest
{
    @InjectMocks
    private OTPVerificationServlet servlet;

    @Mock
    private OTPServiceImpl otpService;

    @Mock
    private EmailService emailService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private StringWriter responseWriter;


    @BeforeEach
    void setUp() throws  Exception {
        MockitoAnnotations.openMocks(this);

        responseWriter = new StringWriter();

        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testValidOTP () throws Exception {
      String email = "sample@gmail.com";
      String code = "123456";

      when(session.getAttribute("otpemail")).thenReturn(email);
      when(request.getParameter("otp_code")).thenReturn(code);

      when(otpService.validateOtp(session, email, code)).thenReturn(true);
      when(emailService.updateUserStatus(email, "1")).thenReturn(true);

      servlet.doPost(request, response);

        String result = responseWriter.toString();
        assertTrue(result.contains("OTP verified successfully!"), "Response should indicate success");
        verify(session).setAttribute("emailVerified", "true");
    }

    @Test
    void testInvalidateOTP() throws Exception {
        String email = "sample@gmail.com";
        String code = "000000";

        when(session.getAttribute("otpemail")).thenReturn(email);
        when(request.getParameter("otp_code")).thenReturn(code);

        when(otpService.validateOtp(session, email, code)).thenReturn(false);

        servlet.doPost(request, response);

        String result = responseWriter.toString();
        assertTrue(result.contains("Invalid OTP!"), "Response should indicate failure");
        verify(session, never()).setAttribute("emailVerified", "true");
    }
}
