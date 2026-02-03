package org.Ocean_View.Admin.Services.Implementation;

import org.Ocean_View.Admin.Entity.Room;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoom;
import org.Ocean_View.Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;

public class ManageRoomImpl implements ManageRoom
{
    @Override
    public String getLastUniqueId() {
        String query = "SELECT uniqueID FROM rooms ORDER BY uniqueID DESC LIMIT 1";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery())
        {
            if (rs.next())
            {
                return rs.getString("uniqueID");
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }


    @Override
    public void saveRoom(Room room)
    {
        String sql = "INSERT INTO rooms (uniqueID, description, noOfPeople, facilities,  " +
                "fine, status, rules, images, roomCategoryID, roomType ,price, created_at) VALUES (?,?,?,?,?,?,?,?,?,?,?, NOW())";
        try(Connection conn = DatabaseConnection.getConnection();
        PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1,room.getUniqueId());
            ps.setString(2,room.getDescription());
            ps.setString(3,room.getNoOfPeople());
            ps.setString(4,room.getFacilities());
            ps.setString(5,room.getFine());
            ps.setString(6,"Active");
            ps.setString(7,room.getRules());
            ps.setString(8,room.getImages());
            ps.setString(9,room.getRoomCategoryId());
            ps.setString(10,room.getRoomType());
            ps.setString(11, room.getPrice());

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                System.out.println("Room Saved Successfully");
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private java.sql.Date convertToSQLDate(DateFormat dateFormat) {
        if (dateFormat == null) return null;
        try {
            java.util.Date utilDate = dateFormat.parse(dateFormat.format(new java.util.Date()));
            return new java.sql.Date(utilDate.getTime());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
