package CustomerTest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Controller.EditProfileServlet;
import org.Ocean_View.Customer.DTO.EditProfileRequest;
import org.Ocean_View.Customer.Services.Implementations.CustomerAuthServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class EditProfileTest {

    private EditProfileServlet servlet;

    @Mock
    private CustomerAuthServiceImpl customerAuthService;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private RequestDispatcher requestDispatcher;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Manual injection to ensure the mock is used
        servlet = new EditProfileServlet(customerAuthService);

        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        // Mock the RequestDispatcher to avoid NullPointerException during forward()
        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
    }

    @Test
    void testGetProfileSuccess() throws Exception {
        // 1. Arrange
        when(request.getSession(false)).thenReturn(session);
        when(session.getAttribute("email")).thenReturn("sample@gmail.com");

        // Create dummy profile data
        Map<String, EditProfileRequest> mockProfile = new HashMap<>();
        EditProfileRequest detail = new EditProfileRequest();
        detail.setFirstName("John");
        mockProfile.put("profile", detail);

        when(customerAuthService.getProfile(session)).thenReturn(mockProfile);

        // 2. Act
        servlet.doGet(request, response);

        // 3. Assert
        // Verify that the profile details were set as a request attribute
        verify(request).setAttribute(eq("profileDetails"), any());
        // Verify we forwarded to the correct JSP
        verify(request).getRequestDispatcher("/Customer/EditProfile.jsp");
        verify(requestDispatcher).forward(request, response);
    }

    @Test
    void testGetProfileNoSession() throws Exception {
        // 1. Arrange: Return null for session to simulate logged-out state
        when(request.getSession(false)).thenReturn(null);

        // 2. Act
        servlet.doGet(request, response);

        // 3. Assert
        // Check that the JavaScript alert was written to the response
        String output = responseWriter.toString();
        assertTrue(output.contains("Email not found."));

        // Verify that sendRedirect was called
        verify(response).sendRedirect(request.getContextPath() + "/Customer/Signing.jsp");

        // Verify that we never try to forward to JSP when session is null
        verify(request, never()).getRequestDispatcher(anyString());
    }
}