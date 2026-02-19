package CustomerTest.AuthenticationTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Controller.ResetPasswordController;
import org.Ocean_View.Customer.Services.Interfaces.CustomerAuthService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class ResetPasswordTest
{
    @InjectMocks
    private ResetPasswordController controller;

    @Mock
    private CustomerAuthService customerAuthService;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    private StringWriter responseWriter;
    private String email = "sample@gmail.com";
    private String password = "123456";
    private String confirmPassword = "123456";
    private String failConfirmPassword = "654321";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testSuccessResetPassword() throws Exception {

        when(session.getAttribute("resetEmail")).thenReturn(email);
        when(request.getParameter("newPassword")).thenReturn(password);

        when(request.getMethod()).thenReturn("POST");
        controller.service(request, response);

        verify(customerAuthService).updateNewPassword(session, password, email);

        String result = responseWriter.toString();
        assertTrue(result.contains("Password Changed"));
    }

    @Test
    void testEmptyParameter() throws Exception {

        when(session.getAttribute("resetEmail")).thenReturn(email);
        when(request.getParameter("newPassword")).thenReturn(null);

        when(request.getMethod()).thenReturn("POST");
        controller.service(request, response);

        String result = responseWriter.toString();
        assertTrue(result.contains("Parameter is missing"), "Should show missing parameter alert");

        verify(customerAuthService, times(0)).updateNewPassword(any(), any(),any());
    }
}
