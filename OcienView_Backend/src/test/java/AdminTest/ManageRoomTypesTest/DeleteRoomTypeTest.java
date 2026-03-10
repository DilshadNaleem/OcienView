package AdminTest.ManageRoomTypesTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Admin.Controller.RoomTypesManagement.DeleteRoomTypeServlet;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteRoomTypeTest
{
    @InjectMocks
    private DeleteRoomTypeServlet controller;

    @Mock
    private ManageRoomType manageRoomType;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private String uniqueId = "uniqueId";


    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("uniqueId")).thenReturn(uniqueId);
        when(request.getSession()).thenReturn(session);
    }


    @Test
    void testDeletingRoomTypeSuccess() throws  Exception{
        when(manageRoomType.getRoomTypeById(uniqueId)).thenReturn(true);

        controller.service(request, response);
        verify(manageRoomType).getRoomTypeById(uniqueId);

        verify(session).setAttribute(eq("successMessage"), anyString());
        verify(response).sendRedirect(anyString());
    }

    @Test
    void testDeletingRoomTypeFailure() throws Exception {
        when(manageRoomType.getRoomTypeById(uniqueId)).thenReturn(false);

        controller.service(request, response);
        verify(manageRoomType).getRoomTypeById(uniqueId);

        verify(session).setAttribute(eq("errorMessage"), anyString());
        verify(response).sendRedirect(anyString());
    }
}
