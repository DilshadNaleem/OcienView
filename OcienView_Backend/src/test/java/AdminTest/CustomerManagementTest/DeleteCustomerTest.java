package AdminTest.CustomerManagementTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Admin.Controller.CustomerManagement.DeleteCustomerServlet;
import org.Ocean_View.Admin.Services.Interfaces.ManageCustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteCustomerTest
{
    @InjectMocks
    private DeleteCustomerServlet servlet;

    @Mock
    private ManageCustomerService manageCustomerService;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;
    private StringWriter responseWriter;

    private String uniqueId = "uniqueID";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("POST");
    }

    @Test
    void testSuccessDelete() throws Exception {

        when(request.getParameter("unique_id")).thenReturn(uniqueId);

        when(manageCustomerService.getCustomerById(uniqueId)).thenReturn(true);

        servlet.service(request, response);

        verify(manageCustomerService).getCustomerById(uniqueId);
        verify(session).setAttribute("successMessage", "Customer Deleted successfully!");

        verify(response).sendRedirect(anyString());
    }

    @Test
    void failingCustomerDelete() throws Exception {
        when(request.getParameter("unique_id")).thenReturn(uniqueId);

        when(manageCustomerService.getCustomerById(uniqueId)).thenReturn(false);

        servlet.service(request, response);

        verify(manageCustomerService).getCustomerById(uniqueId);
        verify(session).setAttribute("errorMessage","Failed to Delete the Customer");

        verify(response).sendRedirect(anyString());
    }
}
