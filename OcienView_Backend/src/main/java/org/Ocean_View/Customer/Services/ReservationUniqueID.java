package org.Ocean_View.Customer.Services;

public class ReservationUniqueID
{
    public static String generateReservationUniqueId(String lastUniqueID)
    {
        if (lastUniqueID != null)
        {
            int number = Integer.parseInt(lastUniqueID.substring(8));
            return "BOOKING_" + String.format("%02d", lastUniqueID + 1);
        }
        return "BOOKING_01";
    }
}
