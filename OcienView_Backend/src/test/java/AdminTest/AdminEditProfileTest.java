package AdminTest;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Admin.Controller.AdminEditProfileServlet;
import org.Ocean_View.Admin.Services.Implementation.AdminAuthServiceImpl;
import org.Ocean_View.Customer.Controller.EditProfileServlet;
import org.Ocean_View.Customer.DTO.EditProfileRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AdminEditProfileTest
{
    @InjectMocks
    private AdminEditProfileServlet controller;

    @Mock
    private AdminAuthServiceImpl adminAuthService;


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

        controller = new AdminEditProfileServlet(adminAuthService);

        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        when(request.getRequestDispatcher(anyString())).thenReturn(requestDispatcher);
        when(request.getMethod()).thenReturn("POST");


    }


    @Test
    void testSuccessProfileEdit() throws Exception {
        // 1. Setup Session data
        when(request.getSession(anyBoolean())).thenReturn(session);
        when(session.getAttribute("email")).thenReturn("sample@gmail.com");
        when(request.getSession()).thenReturn(session);
        // 2. Setup Form parameters (Required by your doPost logic)
        when(request.getParameter("firstName")).thenReturn("John");
        when(request.getParameter("lastName")).thenReturn("Doe");
        when(request.getParameter("contactNumber")).thenReturn("123456789");

        // 3. Setup Service response
        Map<String, EditProfileRequest> profileDetails = new HashMap<>();
        EditProfileRequest detail = new EditProfileRequest();
        detail.setFirstName("John");
        profileDetails.put("profile", detail);

        when(adminAuthService.getProfile(session)).thenReturn(profileDetails);
        when(adminAuthService.updateProfile(any(), any())).thenReturn("Profile updated successfully");

        // 4. Execute
        controller.service(request, response);

        // 5. Verify
        verify(request).setAttribute(eq("profileDetails"), any());
        verify(request).getRequestDispatcher("/Admin/EditProfile.jsp");
        verify(requestDispatcher).forward(request, response);
    }



    @Test
    void testGetProfileNoSession() throws Exception {

        when(request.getSession(false)).thenReturn(null);

        controller.service(request, response);

    }
}
