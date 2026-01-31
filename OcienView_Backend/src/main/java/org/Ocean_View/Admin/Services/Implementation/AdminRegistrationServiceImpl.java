package org.Ocean_View.Admin.Services.Implementation;

import org.Ocean_View.Admin.Entity.Admin;
import org.Ocean_View.Admin.Services.AdminUniqueId;
import org.Ocean_View.Admin.Services.Interfaces.AdminRegistrationService;
import org.Ocean_View.Connection.DatabaseConnection;
import org.Ocean_View.Customer.DTO.RegisterRequest;
import org.Ocean_View.Customer.Entity.Customer;
import org.Ocean_View.Customer.Services.HashPassword;

import java.sql.*;
import java.time.LocalDateTime;

public class AdminRegistrationServiceImpl implements AdminRegistrationService
{
    private final AdminUniqueId idGenerator;
    private final HashPassword hashPassword;

    public AdminRegistrationServiceImpl() {
        this.idGenerator = new AdminUniqueId();
        this.hashPassword = new HashPassword();
    }

    public AdminRegistrationServiceImpl(AdminUniqueId idGenerator, HashPassword hashPassword)
    {
        this.idGenerator = idGenerator;
        this.hashPassword = hashPassword;
    }

    @Override
    public Customer register(RegisterRequest request) {
        Connection conn = null;
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            conn = DatabaseConnection.getConnection();
            conn.setAutoCommit(false); // Start transaction

            // Generate unique ID
            String uniqueId = idGenerator.generateCustomerUniqueId();

            // Insert into customers table
            String sql = "INSERT INTO admin (unique_id, firstName, lastName, email, password, nic, contactNumber, status, created_at,image_path) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,?)";

            stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            String newPassword = hashPassword.hashPassword(request.getPassword());

            stmt.setString(1, uniqueId);
            stmt.setString(2, request.getFirstName());
            stmt.setString(3, request.getLastName());
            stmt.setString(4, request.getEmail());
            stmt.setString(5, newPassword); // In production, hash this password
            stmt.setString(6, request.getNic());
            stmt.setString(7, request.getContactNumber());
            stmt.setString(8, "0"); // Default status
            stmt.setTimestamp(9, Timestamp.valueOf(LocalDateTime.now()));
            stmt.setString(10, "Sample");

            int affectedRows = stmt.executeUpdate();

            if (affectedRows > 0) {
                rs = stmt.getGeneratedKeys();
                if (rs.next()) {
                    // Retrieve and return the customer object
                    String selectSql = "SELECT * FROM admin WHERE unique_id = ?";
                    PreparedStatement selectStmt = conn.prepareStatement(selectSql);
                    selectStmt.setString(1, uniqueId);
                    ResultSet customerRs = selectStmt.executeQuery();

                    if (customerRs.next()) {
                        Customer customer = mapResultSetToCustomer(customerRs);
                        conn.commit(); // Commit transaction

                        // Show success alert (this would be handled in the frontend)
                        System.out.println("Registration successful for: " + customer.getEmail());
                        return customer;
                    }
                }
            }

            conn.rollback(); // Rollback if something went wrong
            return null;

        } catch (SQLException e) {
            if (conn != null) {
                try {
                    conn.rollback();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (rs != null) rs.close();
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public boolean isEmailExists(String email) {
        String sql = "SELECT COUNT(*) FROM admin WHERE email = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean isNICExists(String nic) {
        String sql = "SELECT COUNT(*) FROM admin WHERE nic = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nic);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt(1) > 0;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private Customer mapResultSetToCustomer(ResultSet rs) throws SQLException {
        return new Customer(
                rs.getString("unique_id"),
                rs.getString("firstName"),
                rs.getString("lastName"),
                rs.getString("nic"),
                rs.getString("email"),
                rs.getString("password"),
                rs.getString("image_path"),
                rs.getString("contactNumber"),
                rs.getTimestamp("created_at").toLocalDateTime(),
                rs.getString("status")
        );
    }

    @Override
    public boolean updateUserStatus(String email, String status) {
        String query = "UPDATE admin SET status = ? WHERE email = ?";
        try
        {
            Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, "1");
            ps.setString(2,email);

            int rowsAffected = ps.executeUpdate();
            return rowsAffected > 0;
        }

        catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}
