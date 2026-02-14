package org.Ocean_View.Customer.Services.Implementations;

import org.Ocean_View.Connection.DatabaseConnection;
import org.Ocean_View.Customer.Entity.Booking;
import org.Ocean_View.Customer.Services.Interfaces.Reservations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReservationsImpl implements Reservations {

    @Override
    public String saveReservations(Booking booking) {
        String customerSQL = "SELECT firstName, lastName, contactNumber FROM customers WHERE email = ?";

        String bookingSQL = "INSERT INTO reservations " +
                "(uniqueId, customerEmail, customerFirstName, customerLastName, phoneNumber, " +
                "fine, inDate, outDate, price, noOfDays, paymentUniqueId, paymentMethod, " +
                "roomId, roomCategory, bookingStatus, bookingDate, paidFine) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,NOW(),?)";

        // CHANGED: From UPDATE to INSERT
        String paymentInsertSQL = "INSERT INTO payment (uniqueId, paymentMethod, amount, status, " +
                "customerEmail, customerName, customerPhoneNumber, roomNumber, roomCategory, " +
                "bookingId, paymentTime) VALUES (?,?,?,?,?,?,?,?,?,?,NOW())";

        try (Connection conn = DatabaseConnection.getConnection()) {
            conn.setAutoCommit(false);

            // 1. FETCH CUSTOMER DATA
            try (PreparedStatement psCust = conn.prepareStatement(customerSQL)) {
                psCust.setString(1, booking.getCustomerEmail());
                try (ResultSet rs = psCust.executeQuery()) {
                    if (rs.next()) {
                        booking.setCustomerFirstName(rs.getString("firstName"));
                        booking.setCustomerLastName(rs.getString("lastName"));
                        booking.setPhoneNumber(rs.getString("contactNumber"));
                    } else {
                        return "Error: Customer profile not found.";
                    }
                }
            }

            // 2. EXECUTE INSERTS
            try (PreparedStatement psBooking = conn.prepareStatement(bookingSQL);
                 PreparedStatement psPayment = conn.prepareStatement(paymentInsertSQL)) {

                // --- Set Booking Values ---
                psBooking.setString(1, booking.getUniqueId());
                psBooking.setString(2, booking.getCustomerEmail());
                psBooking.setString(3, booking.getCustomerFirstName());
                psBooking.setString(4, booking.getCustomerLastName());
                psBooking.setString(5, booking.getPhoneNumber());
                psBooking.setString(6, booking.getFine());
                psBooking.setString(7, booking.getInDate().toString());
                psBooking.setString(8, booking.getOutDate().toString());
                psBooking.setDouble(9, booking.getPrice());
                psBooking.setString(10, booking.getNoOfDays());
                psBooking.setString(11, booking.getPaymentUniqueId());
                psBooking.setString(12, booking.getPaymentMethod());
                psBooking.setString(13, booking.getRoomId());
                psBooking.setString(14, booking.getRoomCategory());
                psBooking.setString(15, "Booked");
                psBooking.setDouble(16, 0.0);

                // --- Set Payment Values (The New Insert) ---
                psPayment.setString(1, booking.getPaymentUniqueId());
                psPayment.setString(2, booking.getPaymentMethod());
                psPayment.setDouble(3, booking.getPrice()); // Total amount
                psPayment.setString(4, "Paid"); // Default status
                psPayment.setString(5, booking.getCustomerEmail());
                psPayment.setString(6, booking.getCustomerFirstName() + " " + booking.getCustomerLastName());
                psPayment.setString(7, booking.getPhoneNumber());
                psPayment.setString(8, booking.getRoomId());
                psPayment.setString(9, booking.getRoomCategory());
                psPayment.setString(10, booking.getUniqueId()); // Reference to the reservation ID

                int bookingRows = psBooking.executeUpdate();
                int paymentRows = psPayment.executeUpdate();

                if (bookingRows > 0 && paymentRows > 0) {
                    conn.commit();
                    return "Reservation Successful";
                } else {
                    conn.rollback();
                    return "Reservation Failed: Could not process records.";
                }

            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                return "Database Error: " + e.getMessage();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Connection Error";
        }
    }

    @Override
    public String getLastUniqueId() {
        String query = "SELECT uniqueId FROM reservations ORDER BY uniqueId DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("uniqueId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public String getPaymentLastUniqueId() {
        String query = "SELECT uniqueId FROM payment ORDER BY uniqueId DESC LIMIT 1";
        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery())
        {
            if (rs.next())
            {
                return rs.getString("uniqueId");
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public List<Map<String, String>> getBookedDates(String roomId) {
        List<Map<String, String>> bookedDates = new ArrayList<>();
        // Get ALL reservations (not just 'Booked') to be safe
        String query = "SELECT inDate, outDate FROM reservations WHERE roomId = ? AND (bookingStatus = 'Booked' OR bookingStatus = 'Confirmed')";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, roomId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Map<String, String> range = new HashMap<>();
                // Format dates as yyyy-MM-dd
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                range.put("checkIn", sdf.format(rs.getDate("inDate")));
                range.put("checkOut", sdf.format(rs.getDate("outDate")));
                bookedDates.add(range);
                System.out.println("Booked Dates - From: " + range.get("checkIn") + " To: " + range.get("checkOut"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return bookedDates;
    }

    @Override
    public String updateSuccessStatusForSuccess(String uniqueId)
    {
        String sql = "UPDATE reservations SET bookingStatus = ? WHERE uniqueId = ?";
        try (Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1, "Completed");
            ps.setString(2, uniqueId);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                return "Successfully Updated";
            } else
            {
                return "No Reservations found";
            }

        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return "Error Updating";
    }
}