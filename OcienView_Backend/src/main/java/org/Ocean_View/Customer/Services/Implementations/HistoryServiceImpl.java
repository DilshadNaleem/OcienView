package org.Ocean_View.Customer.Services.Implementations;

import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Connection.DatabaseConnection;
import org.Ocean_View.Customer.Entity.Booking;
import org.Ocean_View.Customer.Services.Interfaces.HistoryService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

public class HistoryServiceImpl implements HistoryService {
    @Override
    public List<Booking> fetchHistoryByEmail(String email, HttpSession session) {
        List<Booking> historyList = new ArrayList<>();

        String sql = "SELECT r.*, rooms.fine AS room_daily_fine FROM reservations r " +
                "LEFT JOIN rooms ON r.roomId = rooms.uniqueId " +
                "WHERE r.customerEmail = ?";

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
                    booking.setSavedFine(rs.getString("fine"));
                    booking.setRoomCategory(rs.getString("roomCategory"));
                    booking.setBookingStatus(rs.getString("bookingStatus"));
                    booking.setNoOfDays(rs.getString("noOfDays"));
                    booking.setPaymentUniqueId(rs.getString("paymentUniqueId"));
                    booking.setPaymentMethod(rs.getString("paymentMethod"));
                    booking.setFine(rs.getString("fine")); // Base fine from DB
                    booking.setRoomId(rs.getString("roomId"));
                    booking.setDailyFine(rs.getDouble("room_daily_fine"));

                    // Calculate fine if the booking is not cancelled and checkout date has passed
                    if (!"Cancelled".equalsIgnoreCase(booking.getBookingStatus())) {
                        calculateOverdueFine(booking);
                    }

                    historyList.add(booking);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return historyList;
    }

    private void calculateOverdueFine(Booking booking) {
        if (booking.getOutDate() == null) return;

        try {
            // Convert java.util.Date to LocalDate
            LocalDate checkoutDate = new java.sql.Date(booking.getOutDate().getTime()).toLocalDate();
            LocalDate today = LocalDate.now();

            if (today.isAfter(checkoutDate)) {
                long overdueDays = ChronoUnit.DAYS.between(checkoutDate, today);

                if (overdueDays > 0 && booking.getDailyFine() != null) {
                    double calculatedOverdueFine = overdueDays * booking.getDailyFine();

                    booking.setOverdueDays((int) overdueDays);
                    booking.setCalculatedFine(calculatedOverdueFine);

                    // Logic: Get existing fine from DB and add the newly calculated overdue amount
                    double existingFine = 0.0;
                    if (booking.getFine() != null && !booking.getFine().trim().isEmpty()) {
                        try {
                            existingFine = Double.parseDouble(booking.getFine());
                        } catch (NumberFormatException e) {
                            existingFine = 0.0;
                        }
                    }

                    double totalFine = existingFine + calculatedOverdueFine;
                    // Format back to string for the DTO

                }
            }
        } catch (Exception e) {
            System.err.println("Error calculating fine for ID " + booking.getUniqueId() + ": " + e.getMessage());
        }
    }
}