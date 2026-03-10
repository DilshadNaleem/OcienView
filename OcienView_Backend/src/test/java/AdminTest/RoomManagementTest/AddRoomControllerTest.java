package AdminTest.RoomManagementTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Admin.Controller.RoomManagement.ManageRoomController;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoom;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AddRoomControllerTest {

    @InjectMocks
    private ManageRoomController controller;

    @Mock
    private ManageRoom manageRoom;

    @Mock
    private HttpServletResponse response;

    @Mock
    private HttpServletRequest request;

    private StringWriter responseWriter;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        // Setup writer to capture JSON response
        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));
        when(request.getMethod()).thenReturn("POST");
    }

    @Test
    void testAddRoomSuccess() throws Exception {
        // 1. Mock Parameters
        when(request.getParameter("roomType")).thenReturn("Deluxe");
        when(request.getParameter("description")).thenReturn("Sea View Room");
        when(request.getParameter("noOfPeople")).thenReturn("2");
        when(request.getParameter("price")).thenReturn("5000");

        // 2. Mock ManageRoom Service
        when(manageRoom.getLastUniqueId()).thenReturn("RM001");


        when(request.getParts()).thenReturn(Collections.emptyList());

        controller.service(request, response);

        // Verify JSON Response
        JSONObject result = new JSONObject(responseWriter.toString());
        assertTrue(result.getBoolean("success"), "Response should indicate success");
        assertTrue(result.getString("roomId").contains("RM002"));
    }

    @Test
    void testAddRoomFailure() throws Exception {
        // Force an exception by making a parameter call throw an error
        when(request.getParameter("roomType")).thenThrow(new RuntimeException("Database Connection Failed"));

        // Execute
        controller.service(request, response);

        // Verify JSON Response
        JSONObject result = new JSONObject(responseWriter.toString());
        assertFalse(result.getBoolean("success"), "Response should indicate failure");
        assertTrue(result.getString("message").contains("Error adding room"));
    }
}