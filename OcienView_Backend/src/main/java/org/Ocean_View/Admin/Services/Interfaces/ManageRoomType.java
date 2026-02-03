package org.Ocean_View.Admin.Services.Interfaces;

import jakarta.servlet.http.Part;

import java.io.IOException;

public interface ManageRoomType
{
    void saveRoomType(String uniqueId, String category, String description, String imagePath);
    String getLastUniqueId();
    String saveRoomImage(Part filePart) throws IOException;
}
