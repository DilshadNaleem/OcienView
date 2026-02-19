package CustomerTest.AuthenticationTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Controller.ForgotPasswordController;
import org.Ocean_View.Customer.Controller.OTPVerificationServlet;
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

public class ForgotPasswordTest
{
    @InjectMocks
    private ForgotPasswordController controller;

    @Mock
    private EmailService emailService;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws  Exception {
        MockitoAnnotations.openMocks(this);
        responseWriter = new StringWriter();
        when (response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testSuccessLinkToResetPassword() throws Exception {
        String email = "sample@gmail.com";
        when(request.getParameter("email")).thenReturn(email);


        when(request.getMethod()).thenReturn("POST");
        controller.service(request, response);

        verify(session).setAttribute("resetEmail", email);

        verify(emailService, times(1)).sendPasswordResetEmail(eq(email), anyString());

        String result = responseWriter.toString();
        assertTrue(result.contains("Password reset link has been sent"));
    }


    @Test
    void testFailureLinkToResetPassword() throws Exception {
        String email = "sample@gmail.com";
        when(request.getParameter("email")).thenReturn(email);

        doThrow(new RuntimeException("Account not activated"))
                .when(emailService).sendPasswordResetEmail(eq(email), anyString());


        when(request.getMethod()).thenReturn("POST");
        controller.service(request, response);

        String result = responseWriter.toString();
        assertTrue(result.contains("Account not activated"));
    }
}
