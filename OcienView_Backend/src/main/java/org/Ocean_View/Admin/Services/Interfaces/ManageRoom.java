package org.Ocean_View.Admin.Services.Interfaces;

import org.Ocean_View.Admin.Entity.Room;

public interface ManageRoom
{
    String getLastUniqueId();
    void saveRoom(Room room);


}
