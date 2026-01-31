package org.Ocean_View.Admin.Services.Implementation;

import jakarta.servlet.http.Part;
import org.Ocean_View.Admin.Services.FileUploader;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoom;
import org.Ocean_View.Connection.DatabaseConnection;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;

public class ManageRoomImpl implements ManageRoom
{
    private FileUploader fileService;

    public ManageRoomImpl(FileUploader fileService)
    {
        this.fileService = fileService;
    }

    @Override
    public void saveRoomType(String uniqueId, String category, String description, String imagePath) {

        String sql = "INSERT INTO room_type (unique_id, roomCategory,description, image, created_at) VALUES (?, ?, ?,?, NOW())";
        try(Connection connection = DatabaseConnection.getConnection();
            PreparedStatement ps = connection.prepareStatement(sql))
        {
            ps.setString(1,uniqueId);
            ps.setString(2,category);
            ps.setString(3,description);
            ps.setString(4,imagePath);
            ps.executeUpdate();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    public String getLastUniqueId() {
        String query = "SELECT unique_id FROM room_type ORDER BY unique_id DESC LIMIT 1";
        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery())
        {
            if (rs.next())
            {
                return rs.getString("unique_id");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public String saveRoomImage(Part filePart) throws IOException {
        return fileService.saveFile(filePart);
    }
}
