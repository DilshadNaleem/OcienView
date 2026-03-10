package AdminTest.RoomManagementTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Admin.Controller.RoomManagement.EditRoomServlet;
import org.Ocean_View.Admin.Entity.Room;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoom;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class EditRoomTest
{
    @InjectMocks
    private EditRoomServlet controller;

    @Mock
    private ManageRoom manageRoom;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpSession session;

    @Mock
    private Room room;

    private String uniqueId = "uniqueID";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);


        when(request.getSession()).thenReturn(session);
        when(request.getMethod()).thenReturn("POST");
        when(request.getContextPath()).thenReturn("");

        when(request.getParameter("uniqueId")).thenReturn(uniqueId);
    }

    @Test
    void testEditRoomSuccess() throws Exception {


        when(manageRoom.updateRoom(any(Room.class))).thenReturn(true);

        controller.service(request, response);

        verify(session).setAttribute(eq("successMessage"), anyString());
        verify(response).sendRedirect(anyString());
        verify(manageRoom).updateRoom(any(Room.class));
    }


    @Test
    void testEditRoomFailing() throws  Exception {
        when(manageRoom.updateRoom(any(Room.class))).thenReturn(false);

        controller.service(request, response);

        verify(session).setAttribute(eq("errorMessage"), anyString());
        verify(response).sendRedirect(anyString());

    }
}
