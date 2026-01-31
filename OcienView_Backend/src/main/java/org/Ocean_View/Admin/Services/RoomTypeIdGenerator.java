package org.Ocean_View.Admin.Services;

public class RoomTypeIdGenerator
{
    public static String generateUniqueId(String lastUniqueId)
    {
        if (lastUniqueId != null)
        {
            int lastNumber = Integer.parseInt(lastUniqueId.substring(9));
            return "ROOMTYPE_" + String.format("%02d", lastNumber + 1);
        }
        return "ROOMTYPE_01";
    }
}
