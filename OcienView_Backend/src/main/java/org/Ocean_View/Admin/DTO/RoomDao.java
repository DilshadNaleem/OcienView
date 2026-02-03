package org.Ocean_View.Admin.DTO;

import org.Ocean_View.Admin.Entity.Room;
import org.Ocean_View.Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomDao
{
    public List<Room> getRoomsByType(String roomType) throws SQLException {
        List<Room> rooms = new ArrayList<>();
        String sql = "SELECT * FROM rooms WHERE roomType = ? AND status = 'Active'";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, roomType);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Room room = new Room();
                room.setUniqueId(rs.getString("uniqueID"));
                room.setDescription(rs.getString("description"));
                room.setNoOfPeople(rs.getString("noOfPeople"));
                room.setFacilities(rs.getString("facilities"));
                room.setFine(rs.getString("fine"));
                room.setRules(rs.getString("rules"));
                room.setImages(rs.getString("images"));
                room.setStatus(rs.getString("status"));
                room.setPrice(rs.getString("price"));
                room.setCreated_at(rs.getTimestamp("created_at").toLocalDateTime());
                room.setRoomCategoryId(rs.getString("roomCategoryID"));
                room.setRoomType(rs.getString("roomType"));
                room.setRoomStatus(rs.getString("roomStatus"));

                rooms.add(room);
            }
        }
        return rooms;
    }
}
