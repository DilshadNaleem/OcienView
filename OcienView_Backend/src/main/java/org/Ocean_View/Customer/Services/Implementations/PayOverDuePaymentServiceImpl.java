package org.Ocean_View.Customer.Services.Implementations;

import org.Ocean_View.Connection.DatabaseConnection;
import org.Ocean_View.Customer.DTO.PayOverDuePaymentDTO;
import org.Ocean_View.Customer.Services.Interfaces.PayOverDuePaymentService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class PayOverDuePaymentServiceImpl implements PayOverDuePaymentService {

    @Override
    public String getOverDuePaymentService(PayOverDuePaymentDTO payOverDuePaymentDTO) {
        System.out.println("=== STARTING PAY OVERDUE PAYMENT SERVICE ===");
        System.out.println("Input DTO: " + payOverDuePaymentDTO);
        System.out.println("Unique ID: " + payOverDuePaymentDTO.getUniqueId());
        System.out.println("Overdue Days: " + payOverDuePaymentDTO.getOverdueDays());
        System.out.println("Calculated Fine: " + payOverDuePaymentDTO.getCalculatedFine());

        // Step 1: SQL to get existing days
        String selectSql = "SELECT noOfDays, fine, bookingStatus FROM reservations WHERE uniqueId = ?";
        // Step 2: SQL to update record
        String updateSql = "UPDATE reservations SET fine = ?, noOfDays = ?, bookingStatus = ? WHERE uniqueId = ?";

        try (Connection conn = DatabaseConnection.getConnection()) {
            System.out.println("Database connection established: " + (conn != null));
            System.out.println("Connection is closed: " + conn.isClosed());

            int currentDays = 0;
            double currentFine = 0;
            String currentStatus = "";

            // Fetch current days
            System.out.println("\n=== FETCHING EXISTING RECORD ===");
            System.out.println("Executing SELECT query: " + selectSql);
            System.out.println("With uniqueId: " + payOverDuePaymentDTO.getUniqueId());

            try (PreparedStatement psSelect = conn.prepareStatement(selectSql)) {
                psSelect.setString(1, payOverDuePaymentDTO.getUniqueId());
                System.out.println("Parameter set for SELECT: " + payOverDuePaymentDTO.getUniqueId());

                try (ResultSet rs = psSelect.executeQuery()) {
                    System.out.println("SELECT query executed");

                    if (rs.next()) {
                        currentDays = rs.getInt("noOfDays");
                        currentFine = rs.getDouble("fine");
                        currentStatus = rs.getString("bookingStatus");

                        System.out.println("Record found!");
                        System.out.println("Current noOfDays: " + currentDays);
                        System.out.println("Current fine: " + currentFine);
                        System.out.println("Current bookingStatus: " + currentStatus);

                        // Verify data types
                        System.out.println("Data type check - noOfDays: " + rs.getMetaData().getColumnTypeName(1));
                        System.out.println("Data type check - fine: " + rs.getMetaData().getColumnTypeName(2));
                    } else {
                        System.out.println("WARNING: No record found with uniqueId: " + payOverDuePaymentDTO.getUniqueId());
                        return "ERROR: No reservation found with ID: " + payOverDuePaymentDTO.getUniqueId();
                    }
                }
            } catch (Exception e) {
                System.out.println("ERROR during SELECT execution: " + e.getMessage());
                e.printStackTrace();
                return "SELECT Error: " + e.getMessage();
            }

            // Calculate new totals
            System.out.println("\n=== CALCULATING UPDATED VALUES ===");
            int updatedTotalDays = currentDays + payOverDuePaymentDTO.getOverdueDays();
            double newFine = payOverDuePaymentDTO.getCalculatedFine();

            System.out.println("Current days: " + currentDays);
            System.out.println("Overdue days to add: " + payOverDuePaymentDTO.getOverdueDays());
            System.out.println("Updated total days: " + updatedTotalDays);
            System.out.println("New fine amount: " + newFine);
            System.out.println("Setting bookingStatus to: Completed");

            // Perform the update
            System.out.println("\n=== PERFORMING UPDATE ===");
            System.out.println("Executing UPDATE query: " + updateSql);
            System.out.println("Update parameters:");
            System.out.println("  fine = " + newFine);
            System.out.println("  noOfDays = " + updatedTotalDays);
            System.out.println("  bookingStatus = Completed");
            System.out.println("  uniqueId = " + payOverDuePaymentDTO.getUniqueId());

            try (PreparedStatement psUpdate = conn.prepareStatement(updateSql)) {
                psUpdate.setDouble(1, newFine);
                psUpdate.setInt(2, updatedTotalDays);
                psUpdate.setString(3, "Completed");
                psUpdate.setString(4, payOverDuePaymentDTO.getUniqueId());

                System.out.println("All parameters set for UPDATE");
                System.out.println("Executing update...");

                int rowsAffected = psUpdate.executeUpdate();
                System.out.println("UPDATE executed, rows affected: " + rowsAffected);

                // Verify the update by selecting the record again
                if (rowsAffected > 0) {
                    System.out.println("\n=== VERIFYING UPDATE ===");
                    try (PreparedStatement psVerify = conn.prepareStatement(selectSql)) {
                        psVerify.setString(1, payOverDuePaymentDTO.getUniqueId());
                        try (ResultSet rsVerify = psVerify.executeQuery()) {
                            if (rsVerify.next()) {
                                System.out.println("VERIFIED - Updated values:");
                                System.out.println("  noOfDays: " + rsVerify.getInt("noOfDays"));
                                System.out.println("  fine: " + rsVerify.getDouble("fine"));
                                System.out.println("  bookingStatus: " + rsVerify.getString("bookingStatus"));
                            }
                        }
                    }
                    System.out.println("\nSUCCESS: Payment processed and record updated!");
                    return "SUCCESS: Payment processed successfully";
                } else {
                    System.out.println("WARNING: UPDATE executed but 0 rows affected!");
                    System.out.println("Possible causes:");
                    System.out.println("  1. uniqueId doesn't exist");
                    System.out.println("  2. Data validation failed");
                    System.out.println("  3. No changes needed (values already set)");

                    // Check if the record already has the same values
                    System.out.println("\nChecking if values are already set...");
                    System.out.println("Current fine in DB: " + currentFine + " vs New fine: " + newFine);
                    System.out.println("Current days in DB: " + currentDays + " vs Updated days: " + updatedTotalDays);
                    System.out.println("Current status in DB: " + currentStatus + " vs New status: Completed");

                    return "WARNING: No changes made to the record";
                }
            } catch (Exception e) {
                System.out.println("ERROR during UPDATE execution: " + e.getMessage());
                System.out.println("Update SQL: " + updateSql);
                System.out.println("Parameters: " + newFine + ", " + updatedTotalDays + ", Completed, " + payOverDuePaymentDTO.getUniqueId());
                e.printStackTrace();
                return "UPDATE Error: " + e.getMessage();
            }

        } catch (Exception e) {
            System.out.println("ERROR: Database connection or overall execution failed");
            System.out.println("Error Message: " + e.getMessage());
            System.out.println("Error Type: " + e.getClass().getName());
            e.printStackTrace();

            // Check specific common issues
            if (e.getMessage().contains("connection")) {
                System.out.println("CHECK: Database connection configuration");
            }
            if (e.getMessage().contains("table")) {
                System.out.println("CHECK: Table 'reservations' exists");
            }
            if (e.getMessage().contains("column")) {
                System.out.println("CHECK: Column names are correct");
            }

            return "ERROR: " + e.getMessage();
        } finally {
            System.out.println("=== PAY OVERDUE PAYMENT SERVICE COMPLETED ===");
        }
    }
}