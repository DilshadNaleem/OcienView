package org.Ocean_View.Admin.Services.Interfaces;

import org.Ocean_View.Admin.Entity.Room;

import java.util.List;

public interface ManageRoom
{
    String getLastUniqueId();
    void saveRoom(Room room);
    List<Room> getAllRooms();
    boolean updateRoom(Room room);
    boolean getRoomById(String id);
}
