package org.Ocean_View.Admin.Services.Implementation;

import org.Ocean_View.Admin.Entity.Room;
import org.Ocean_View.Admin.Services.Interfaces.ManageRoom;
import org.Ocean_View.Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.List;

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
                "fine, status, rules, images, roomCategoryID, roomType ,price, roomStatus, created_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?, NOW())";
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
            ps.setString(12,"Available");

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

    @Override
    public List<Room> getAllRooms() {
        List<Room> roomList = new ArrayList<>();

        String sql = "SELECT * FROM rooms";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Room room = new Room();
                room.setUniqueId(rs.getString("uniqueID"));
                room.setDescription(rs.getString("description"));
                room.setNoOfPeople(rs.getString("noOfPeople"));
                room.setFacilities(rs.getString("facilities"));
                room.setFine(rs.getString("fine"));
                room.setRules(rs.getString("rules"));
                room.setRoomCategoryId(rs.getString("roomCategoryID"));
                room.setRoomType(rs.getString("roomType"));
                room.setPrice(rs.getString("price"));
                room.setRoomStatus(rs.getString("status"));

                roomList.add(room);
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return roomList;
    }

    @Override
    public boolean updateRoom(Room room) {
        String sql = "UPDATE rooms SET roomCategoryID=?, roomType=?, status=?, noOfPeople=?, " +
                "price=?, fine=?, description=?, facilities=?, rules=? WHERE uniqueID=?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, room.getRoomCategoryId());
            ps.setString(2, room.getRoomType());
            ps.setString(3, room.getRoomStatus());
            ps.setString(4, room.getNoOfPeople());
            ps.setString(5, room.getPrice());
            ps.setString(6, room.getFine());
            ps.setString(7, room.getDescription());
            ps.setString(8, room.getFacilities());
            ps.setString(9, room.getRules());
            ps.setString(10, room.getUniqueId());

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean getRoomById(String id) {
        String sql = "DELETE FROM rooms WHERE uniqueID = ?";

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
}
