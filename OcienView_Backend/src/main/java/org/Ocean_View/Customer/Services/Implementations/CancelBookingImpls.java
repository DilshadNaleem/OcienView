package org.Ocean_View.Customer.Services.Implementations;

import org.Ocean_View.Connection.DatabaseConnection;
import org.Ocean_View.Customer.Services.Interfaces.CancelBooking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class CancelBookingImpls implements CancelBooking
{
    @Override
    public String cancelBookingId(String bookingId) {
        String sql = "UPDATE reservations SET bookingStatus =? WHERE uniqueId = ?";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, "Cancelled");
            ps.setString(2, bookingId);

            int rowsAffected = ps.executeUpdate();


            System.out.println("Rows affected: " + rowsAffected);

            if (rowsAffected > 0) {
                System.out.println("Database Updated set to Canceled for booking: " + bookingId);
                return "Success";
            } else {
                System.out.println("No booking found with ID: " + bookingId);
                return "Booking not found";
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}