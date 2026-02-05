package org.Ocean_View.Admin.Services.Implementation;

import jakarta.servlet.http.Part;
import org.Ocean_View.Admin.DTO.RoomType;
import org.Ocean_View.Admin.Services.FileUploader;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoomType;
import org.Ocean_View.Admin.Services.RoomTypeIdGenerator;
import org.Ocean_View.Connection.DatabaseConnection;
import org.eclipse.jdt.internal.compiler.tool.EclipseBatchRequestor;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageRoomTypeImpl implements ManageRoomType
{
    private FileUploader fileService;

    public ManageRoomTypeImpl(FileUploader fileService)
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

    @Override
    public List<RoomType> getAllRoomTypes() {
        List<RoomType> roomList = new ArrayList<>();

        String sql = "SELECT * FROM room_type";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                RoomType roomType = new RoomType();
                roomType.setUniqueId(rs.getString("unique_Id"));
                roomType.setRoomCategory(rs.getString("roomCategory"));
                roomType.setDescription(rs.getString("description"));
                roomType.setImage(rs.getString("image"));
                roomList.add(roomType);
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return roomList;
    }

    @Override
    public boolean getRoomTypeById(String id) {
        // 1. Correct SQL syntax for deletion
        String sql = "DELETE FROM room_type WHERE unique_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, id);

            // 2. Use executeUpdate() for DELETE/UPDATE/INSERT
            int rowsAffected = ps.executeUpdate();

            // 3. If rowsAffected > 0, the record was successfully deleted
            if (rowsAffected > 0) {
                System.out.println("ManageRoomTypeImpl Deleted: " + id);
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean updateRoomType(RoomType roomType)
    {
        String sql = "UPDATE room_type SET roomCategory = ?, description =?, image = ? WHERE unique_id =?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, roomType.getRoomCategory());
            ps.setString(2,roomType.getDescription());
            ps.setString(3, roomType.getImage());
            ps.setString(4, roomType.getUniqueId());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;
        }
        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }

    }
}
