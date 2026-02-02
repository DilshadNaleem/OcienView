package org.Ocean_View.Customer.Services.Interfaces;

import java.util.List;
import java.util.Map;

public interface RoomServices
{
    List<Map <String,String>> getRoomType (String roomType);
    List<Map <String,String>> searchRoomInType(String roomType, String searchQuery);
    List <Map <String,String>> searchRoom(String searchQuery);
}
