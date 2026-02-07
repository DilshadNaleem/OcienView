package org.Ocean_View.Admin.Services.Implementation;

import org.Ocean_View.Admin.Services.Interfaces.BookingManagementService;
import org.Ocean_View.Connection.DatabaseConnection;
import org.Ocean_View.Customer.Entity.Booking;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BookingManagementServiceImpl implements BookingManagementService
{
    @Override
    public List<Booking> getAllBookings()
    {
        List<Booking> bookingList = new ArrayList<>();
        String sql = "SELECT * FROM reservations";
        try (Connection con = DatabaseConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery())
        {
            while (rs.next())
            {
                Booking booking = new Booking();
                booking.setUniqueId(rs.getString("uniqueId"));
                booking.setCustomerFirstName(rs.getString("customerFirstName"));
                booking.setCustomerLastName(rs.getString("customerLastName"));
                booking.setCustomerEmail(rs.getString("customerEmail"));
                booking.setFine(rs.getString("fine"));
                booking.setOutDate(rs.getDate("outDate"));
                booking.setInDate(rs.getDate("inDate"));
                booking.setPrice(rs.getDouble("price"));
                booking.setNoOfDays(rs.getString("noOfDays"));
                booking.setPaymentUniqueId(rs.getString("paymentUniqueId"));
                booking.setPaymentMethod(rs.getString("paymentMethod"));
                booking.setBookingDate(rs.getTimestamp("bookingDate").toLocalDateTime());
                booking.setRoomId(rs.getString("roomId"));
                booking.setBookingStatus(rs.getString("bookingStatus"));

                bookingList.add(booking);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return bookingList;
    }
}
