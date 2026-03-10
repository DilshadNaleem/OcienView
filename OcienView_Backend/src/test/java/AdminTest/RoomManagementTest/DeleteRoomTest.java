package AdminTest.RoomManagementTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Admin.Controller.RoomManagement.DeleteRoomServlet;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class DeleteRoomTest
{
    @InjectMocks
    private DeleteRoomServlet controller;

    @Mock
    private ManageRoom manageRoom;

    @Mock
    private HttpSession session;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private String uniqueId = "uniqueID";

    @BeforeEach
    void setUp()
    {
        MockitoAnnotations.openMocks(this);
        when(request.getMethod()).thenReturn("POST");
        when(request.getSession()).thenReturn(session);
    }


    @Test
    void testDeletingSuccessfully() throws Exception {

        when(request.getParameter("uniqueId")).thenReturn(uniqueId);
        when(manageRoom.getRoomById(uniqueId)).thenReturn(true);

        controller.service(request, response);
        verify(manageRoom).getRoomById(uniqueId);

        verify(session).setAttribute("successMessage", "Room type deleted Successfully!");
        verify(response).sendRedirect(anyString());
    }

    @Test
    void testFailingDeletingRooms() throws Exception {
        when(request.getParameter("uniqueId")).thenReturn(uniqueId);
        when(manageRoom.getRoomById(uniqueId)).thenReturn(false);

        controller.service(request, response);
        verify(manageRoom).getRoomById(uniqueId);

        verify(session).setAttribute("errorMessage", "Failed to delete room type.");
        verify(response).sendRedirect(anyString());
    }
}
