package org.Ocean_View.Customer.Services.Implementations;

import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Connection.DatabaseConnection;
import org.Ocean_View.Customer.Entity.Booking;
import org.Ocean_View.Customer.Services.Interfaces.HistoryService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class HistoryServiceImpl implements HistoryService {
    @Override
    public List<Booking> fetchHistoryByEmail(String email, HttpSession session) {
        List<Booking> historyList = new ArrayList<>();
        String sql = "SELECT * FROM reservations WHERE customerEmail = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Booking booking = new Booking();
                    booking.setUniqueId(rs.getString("uniqueId"));
                    booking.setCustomerFirstName(rs.getString("customerFirstName"));
                    booking.setCustomerLastName(rs.getString("customerLastName"));
                    booking.setCustomerEmail(rs.getString("customerEmail"));
                    booking.setInDate(rs.getDate("inDate"));
                    booking.setOutDate(rs.getDate("outDate"));
                    booking.setPrice(rs.getDouble("price"));
                    booking.setRoomCategory(rs.getString("roomCategory"));
                    booking.setBookingStatus(rs.getString("bookingStatus"));
                    booking.setNoOfDays(rs.getString("noOfDays"));
                    booking.setPaymentUniqueId(rs.getString("paymentUniqueId"));
                    booking.setPaymentMethod(rs.getString("paymentMethod"));
                    booking.setFine(rs.getString("fine"));
                    // Add other fields as necessary based on your DB columns
                    historyList.add(booking);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historyList;
    }
}