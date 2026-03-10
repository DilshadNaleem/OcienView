package AdminTest.AuthenticationTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Admin.Controller.AdminLoginServlet;
import org.Ocean_View.Admin.Services.Interfaces.AdminAuthService;
import org.Ocean_View.Customer.DTO.LoginRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class AdminLoginTest
{
    @InjectMocks
    private AdminLoginServlet servlet;

    @Mock
    private AdminAuthService adminAuthService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private StringWriter responseWriter;


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when (request.getMethod()).thenReturn("POST");
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getSession()).thenReturn(session);
    }

    @Test
    void testLoginSuccess() throws Exception {
        when(request.getParameter("email")).thenReturn("sample@gmail.com");
        when(request.getParameter("password")).thenReturn("123456");

        // Mock the service to return success when any LoginRequest is passed
        when(adminAuthService.loginAdmin(any(LoginRequest.class), eq(session)))
                .thenReturn("Login successful");

        // 2. Act
        servlet.service(request, response);

        // 3. Assert
        String output = responseWriter.toString();
        assertTrue(output.contains("Login Successful"), "Output should contain success message");
        assertTrue(output.contains("/Admin/Dashboard.jsp"), "Should redirect to Dashboard");
    }


    @Test
    void testLoginFailure() throws Exception {
        // 1. Arrange
        when(request.getParameter("email")).thenReturn("wrong@gmail.com");
        when(request.getParameter("password")).thenReturn("wrongpass");

        when(adminAuthService.loginAdmin(any(LoginRequest.class), eq(session)))
                .thenReturn("Invalid credentials");

        // 2. Act
        servlet.service(request, response);

        // 3. Assert
        String output = responseWriter.toString();
        assertTrue(output.contains("Login Failed"), "Output should contain failure message");
        assertTrue(output.contains("window.history.back()"), "Should go back on failure");
    }

    @Test
    void testEmptyFields() throws Exception {
        // 1. Arrange: Leave email empty
        when(request.getParameter("email")).thenReturn("");
        when(request.getParameter("password")).thenReturn("123456");

        // 2. Act
        servlet.service(request, response);

        // 3. Assert
        String output = responseWriter.toString();
        assertTrue(output.contains("Email is not Found"), "Should trigger validation alert");
        // Verify service was NEVER called because validation failed early
        verify(adminAuthService, never()).loginAdmin(any(), any());
    }
}
