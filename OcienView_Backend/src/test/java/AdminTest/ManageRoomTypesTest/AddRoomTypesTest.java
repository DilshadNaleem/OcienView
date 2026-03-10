package AdminTest.ManageRoomTypesTest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Admin.Controller.RoomTypesManagement.RoomTypesController;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoom;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoomType;
import org.Ocean_View.Admin.Services.RoomTypeIdGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.PrintWriter;
import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

public class AddRoomTypesTest
{
    @InjectMocks
    private RoomTypesController controller;

    @Mock
    private ManageRoomType manageRoomType;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private StringWriter responseWriter;

    private String uniqueId = "ROOMTYPE_02";
    private String lastUniqueId = "ROOMTYPE_01";
    private String roomType = "category";
    private String description = "description";
    private String image = "image";

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);

        responseWriter = new StringWriter();
        when(response.getWriter()).thenReturn(new PrintWriter(responseWriter));

        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("category")).thenReturn(roomType);
        when(request.getParameter("description")).thenReturn(description);
        when(request.getParameter("image")).thenReturn(image);

    }


    @Test
    void testAddingNewRoomTypesSuccess() throws Exception {
        when(manageRoomType.getLastUniqueId()).thenReturn(lastUniqueId);

        controller.service(request, response);

        verify(manageRoomType).saveRoomType(eq(uniqueId), eq(roomType), eq(description), eq("Room_Categories/" + image));

        String result = responseWriter.toString();
       assertTrue(result.contains("Room added successfully!"));

    }

    @Test
    void testAddingNewRoomTypeFailure() throws Exception {
        // 1. Arrange - Setup the Mock behavior
        String errorMessage = "Database Error";
        when(manageRoomType.getLastUniqueId()).thenReturn(lastUniqueId);

        // Use doThrow for void methods
        doThrow(new RuntimeException(errorMessage))
                .when(manageRoomType)
                .saveRoomType(anyString(), anyString(), anyString(), anyString());

        // 2. Act
        controller.service(request, response); // Call doPost directly to bypass init() logic

        // 3. Assert
        String result = responseWriter.toString();

        // Check for the error message that your CATCH block writes
        assertTrue(result.contains("Error adding vehicle: " + errorMessage),
                "Expected alert message not found in response. Found: " + result);
    }
}
