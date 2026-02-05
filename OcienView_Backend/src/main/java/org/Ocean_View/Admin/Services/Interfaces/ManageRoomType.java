package org.Ocean_View.Admin.Services.Interfaces;

import jakarta.servlet.http.Part;
import org.Ocean_View.Admin.DTO.RoomType;
import org.Ocean_View.Customer.Entity.Booking;

import java.io.IOException;
import java.util.List;

public interface ManageRoomType
{
    void saveRoomType(String uniqueId, String category, String description, String imagePath);
    String getLastUniqueId();
    String saveRoomImage(Part filePart) throws IOException;
    List<RoomType> getAllRoomTypes();
    boolean getRoomTypeById(String id);
    boolean updateRoomType(RoomType roomType);
}
