package org.Ocean_View.Customer.Services.Implementations;

import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Connection.DatabaseConnection;
import org.Ocean_View.Customer.DTO.EditProfileRequest;
import org.Ocean_View.Customer.DTO.LoginRequest;
import org.Ocean_View.Customer.DTO.RegisterRequest;
import org.Ocean_View.Customer.Services.HashPassword;
import org.Ocean_View.Customer.Services.Interfaces.CustomerAuthService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class CustomerAuthServiceImpl implements CustomerAuthService {

    private final HashPassword hashPassword;

    public CustomerAuthServiceImpl() {
        this.hashPassword = new HashPassword();
    }

    @Override
    public String loginCustomer(LoginRequest request, HttpSession session) {
        String normalizedEmail = request.getEmail().trim();
        System.out.println("CustomerAuthServiceImpl : Received Email " + normalizedEmail);
        String normalizedPassword = request.getPassword().trim();
        System.out.println("CustomerAuthServiceImpl : Received Password " + normalizedPassword);

        String query = "SELECT * FROM customers WHERE email = ? AND password = ? AND status = 1";
        try (Connection connection = DatabaseConnection.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {


            String hashedPassword = hashPassword.hashPassword(normalizedPassword);

            stmt.setString(1, normalizedEmail);
            stmt.setString(2, hashedPassword);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                // Customer authenticated successfully

                // Set session attributes
                session.setAttribute("email", normalizedEmail);
                session.setAttribute("loggedIn", true);

                // Set session timeout (optional)
                session.setMaxInactiveInterval(30 * 60); // 30 minutes
                System.out.println("CustomerAuthServiceImpl : Login successful");
                return "Login successful";
            } else {
                return "Invalid email or password";
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error occurred";
        }
    }

    @Override
    public String updateProfile(EditProfileRequest request, HttpSession session) {
        String sessionEmail = (String) session.getAttribute("email");

        if (sessionEmail == null || sessionEmail.trim().isEmpty()) {
            return "Please login first";
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                return "Database connection failed";
            }

            // Update query
            String query = "UPDATE customers SET firstName = ?, lastName = ?, contactNumber = ? WHERE email = ?";

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, request.getFirstName());
                ps.setString(2, request.getLastName());
                ps.setString(3, request.getContactnumber());
                ps.setString(4, sessionEmail);

                int rowsUpdated = ps.executeUpdate();

                if (rowsUpdated > 0) {
                    System.out.println("Profile updated successfully for: " + sessionEmail);
                    return "Profile updated successfully";
                } else {
                    return "Profile update failed. User not found.";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return "Database error: " + e.getMessage();
        }
    }

    @Override
    public Map<String, EditProfileRequest> getProfile(HttpSession session) {
        String sessionEmail = (String) session.getAttribute("email");
        Map<String, EditProfileRequest> editProfile = new HashMap<>();

        if (sessionEmail == null || sessionEmail.trim().isEmpty()) {
            return editProfile; // Return empty map
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            if (connection == null) {
                return editProfile;
            }

            // Fixed the query - removed "WHERE" typo
            String query = "SELECT * FROM customers WHERE email = ?";

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                ps.setString(1, sessionEmail);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        EditProfileRequest profileRequest = new EditProfileRequest();
                        profileRequest.setId(rs.getString("unique_id"));
                        profileRequest.setFirstName(rs.getString("firstName"));
                        profileRequest.setLastName(rs.getString("lastName"));
                        profileRequest.setEmail(rs.getString("email"));
                        profileRequest.setContactnumber(rs.getString("contactNumber"));
                        profileRequest.setNic(rs.getString("nic"));
                        int status = rs.getInt("status");
                        profileRequest.setStatus(status == 1 ? "Active" : "Inactive");

                        editProfile.put(profileRequest.getId(), profileRequest);
                        System.out.println("Profile Retrieved: " + profileRequest.getEmail());
                    } else {
                        System.out.println("No Profile Details Available");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return editProfile;
    }


}