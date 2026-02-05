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

        // Modified SQL to get roomId from reservations
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
                    booking.setRoomCategory(rs.getString("roomCategory"));
                    booking.setBookingStatus(rs.getString("bookingStatus"));
                    booking.setNoOfDays(rs.getString("noOfDays"));
                    booking.setPaymentUniqueId(rs.getString("paymentUniqueId"));
                    booking.setPaymentMethod(rs.getString("paymentMethod"));
                    booking.setFine(rs.getString("fine"));
                    booking.setRoomId(rs.getString("roomId"));

                    // Get daily fine rate from rooms table
                    double dailyFine = rs.getDouble("room_daily_fine");
                    booking.setDailyFine(dailyFine);

                    // Calculate overdue days and fine for completed bookings
                    if ("Completed".equalsIgnoreCase(booking.getBookingStatus())) {
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
        if (booking.getOutDate() == null) {
            return;
        }

        try {
            LocalDate checkoutDate;
            Object outDateObj = booking.getOutDate();

            // Handle different date types
            if (outDateObj instanceof java.sql.Date) {
                checkoutDate = ((java.sql.Date) outDateObj).toLocalDate();
            } else if (outDateObj instanceof java.util.Date) {
                // Convert java.util.Date to LocalDate
                checkoutDate = ((java.util.Date) outDateObj).toInstant()
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate();
            } else if (outDateObj instanceof LocalDate) {
                // If it's already LocalDate
                checkoutDate = (LocalDate) outDateObj;
            } else if (outDateObj instanceof String) {
                // If it's stored as String
                checkoutDate = LocalDate.parse((String) outDateObj);
            } else {
                System.out.println("Unknown date type: " + outDateObj.getClass().getName());
                return;
            }

            LocalDate today = LocalDate.now();

            // Calculate days between checkout date and today
            if (today.isAfter(checkoutDate)) {
                long daysBetween = java.time.temporal.ChronoUnit.DAYS.between(checkoutDate, today);

                if (daysBetween > 0 && booking.getDailyFine() != null && booking.getDailyFine() > 0) {
                    int overdueDays = (int) daysBetween;
                    double calculatedFine = overdueDays * booking.getDailyFine();

                    booking.setOverdueDays(overdueDays);
                    booking.setCalculatedFine(calculatedFine);

                    // Update fine
                    double totalFine = calculatedFine;
                    if (booking.getFine() != null && !booking.getFine().isEmpty()) {
                        try {
                            totalFine += Double.parseDouble(booking.getFine());
                        } catch (NumberFormatException e) {
                            // Ignore parsing error
                        }
                    }
                    booking.setFine(String.format("%.2f", totalFine));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error calculating fine for booking: " + booking.getUniqueId());
        }
    }
}