package AdminTest.ManageRoomTypesTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Admin.Controller.RoomTypesManagement.EditRoomTypeServlet;
import org.Ocean_View.Admin.DTO.RoomType;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoomType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditRoomTypeTest
{
    @InjectMocks
    private EditRoomTypeServlet controller;

    @Mock
    private ManageRoomType manageRoomType;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    private String uniqueId = "uniqueId";
    private String roomCategory = "roomCategory";
    private String description = "description";
    private String image = "image";


    @BeforeEach
    void setUp() throws Exception
    {
        MockitoAnnotations.openMocks(this);

        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);

        when(request.getParameter("uniqueId")).thenReturn(uniqueId);
        when(request.getParameter("roomCategory")).thenReturn(roomCategory);
        when(request.getParameter("description")).thenReturn(description);
        when(request.getParameter("image")).thenReturn(image);
    }



    @Test
    void testEditRoomTypeSuccess() throws Exception {
        when(manageRoomType.updateRoomType(any(RoomType.class))).thenReturn(true);

        controller.service(request, response);

        verify(session).setAttribute(eq("successMessage"), anyString());
        verify(response).sendRedirect(anyString());
    }


    @Test
    void testEditRoomTypeFailure() throws Exception {
        when(manageRoomType.updateRoomType(any(RoomType.class))).thenReturn(false);
        controller.service(request, response);
        verify(session).setAttribute(eq("errorMessage"),anyString());
        verify(response).sendRedirect(anyString());
    }
}
