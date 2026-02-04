package org.Ocean_View.Customer.Services.Implementations;

import org.Ocean_View.Connection.DatabaseConnection;
import org.Ocean_View.Customer.Entity.Payment;
import org.Ocean_View.Customer.Services.Interfaces.PaymentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PaymentServiceImpl implements PaymentService {

    @Override
    public String savePayment(Payment payment) {
        Connection conn = null;
        PreparedStatement psPayment = null;
        PreparedStatement psBooking = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // 1. Save payment to payment table
            String paymentSQL = "INSERT INTO payment (uniqueId, paymentMethod, amount, status, customerEmail, " +
                    "customerName, customerPhoneNumber, roomNumber, roomCategory, " +
                    "bookingId, paymentTime) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,NOW())";

            psPayment = conn.prepareStatement(paymentSQL);
            psPayment.setString(1, payment.getPaymentId());
            psPayment.setString(2, payment.getPaymentMethod());
            psPayment.setString(3, payment.getAmount());
            psPayment.setString(4, "Paid");
            psPayment.setString(5, payment.getCustomerEmail());
            psPayment.setString(6, payment.getCustomerName());
            psPayment.setString(7, payment.getCustomerPhoneNumber());
            psPayment.setString(8, payment.getRoomNumber());
            psPayment.setString(9, payment.getRoomCategory());
            psPayment.setString(10, payment.getBookingId());

            int paymentRowsAffected = psPayment.executeUpdate();

            if (paymentRowsAffected > 0) {
                // 2. Update booking table with payment information
                String bookingSQL = "UPDATE booking SET paymentUniqueId = ?, paymentMethod = ?, bookingStatus = ? WHERE uniqueId = ?";

                psBooking = conn.prepareStatement(bookingSQL);
                psBooking.setString(1, payment.getPaymentId()); // Use payment ID as paymentUniqueId
                psBooking.setString(2, payment.getPaymentMethod());
                psBooking.setString(3, "Confirmed"); // Or "Paid", depending on your business logic
                psBooking.setString(4, payment.getBookingId());

                int bookingRowsAffected = psBooking.executeUpdate();

                if (bookingRowsAffected > 0) {
                    conn.commit(); // Commit transaction
                    System.out.println("Payment Saved Successfully and Booking Updated");
                    return "Payment Successful";
                } else {
                    conn.rollback(); // Rollback if booking update fails
                    System.out.println("Payment successful but booking update failed");
                    return "Payment Successful but Booking Update Failed";
                }
            } else {
                conn.rollback();
                System.out.println("Payment insertion failed");
            }

        } catch (SQLException e) {
            try {
                if (conn != null) {
                    conn.rollback(); // Rollback on any error
                }
            } catch (SQLException rollbackEx) {
                rollbackEx.printStackTrace();
            }
            e.printStackTrace();
            System.out.println("Payment unsuccessful: " + e.getMessage());

        } finally {
            // Close resources
            try {
                if (psPayment != null) psPayment.close();
                if (psBooking != null) psBooking.close();
                if (conn != null) {
                    conn.setAutoCommit(true); // Reset auto-commit
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return "Payment Unsuccessful";
    }

    @Override
    public String getLastUniqueId() {
        String SQL = "SELECT uniqueId FROM payment ORDER BY uniqueId DESC LIMIT 1";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(SQL);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getString("uniqueId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}