package org.Ocean_View.Customer.Services.Implementations;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Connection.DatabaseConnection;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;
import org.Ocean_View.Customer.Services.Interfaces.OTPService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

public class EmailServiceImpl implements EmailService {
    private final String fromEmail = "hypermarket403@gmail.com";
    private final String emailPassword = "jaeh itgd annt fsqr";
    private jakarta.mail.Session session;
    private OTPService otpService;
    private DatabaseConnection databaseConnection;

    // ThreadLocal to store session for current thread
    private static final ThreadLocal<HttpSession> currentSession = new ThreadLocal<>();

    public EmailServiceImpl() {
        initializeSession();
        this.otpService = new OTPServiceImpl();
        this.databaseConnection = new DatabaseConnection();
    }

    // Method to set session before sending email
    public void setCurrentSession(HttpSession session) {
        currentSession.set(session);
    }

    // Method to clear session after use
    public void clearCurrentSession() {
        currentSession.remove();
    }

    private void initializeSession() {
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        try {
            session = jakarta.mail.Session.getInstance(props, new jakarta.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, emailPassword);
                }
            });

        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize email session", e);
        }
    }

    @Override
    public void sendVerificationEmail(String firstName, String lastName, String toEmail, String otp, HttpSession httpsession) {
        try {
            HttpSession httpSession = httpsession;


            // Send email
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "Ocean View"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Email Verification - Ocean View");

            String content = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>"
                    + "<h2 style='color: #2E86C1;'>Welcome to Ocean View!</h2>"
                    + "<p>Dear <strong>" + firstName + " " + lastName + "</strong>,</p>"
                    + "<p>Thank you for registering with Ocean View. Please use the following OTP to verify your email address:</p>"
                    + "<div style='background-color: #f8f9fa; padding: 15px; border-left: 4px solid #2E86C1; margin: 20px 0;'>"
                    + "<h3 style='margin: 0; color: #333;'>Verification Code:</h3>"
                    + "<h1 style='margin: 10px 0; letter-spacing: 5px; color: #2E86C1;'>" + otp + "</h1>"
                    + "</div>"
                    + "<p>This OTP will expire in 10 minutes.</p>"
                    + "<p>If you didn't request this verification, please ignore this email.</p>"
                    + "<hr style='border: none; border-top: 1px solid #eee; margin: 20px 0;'>"
                    + "<p style='color: #666; font-size: 12px;'>© 2024 Ocean View. All rights reserved.</p>"
                    + "</div>";

            message.setContent(content, "text/html");

            // Store OTP in session
            otpService.storeOtp(httpSession, toEmail, otp);

            System.out.println("Attempting to send email to: " + toEmail);
            Transport.send(message);
            System.out.println("Verification email sent successfully to: " + toEmail);

        } catch (Exception e) {
            throw new RuntimeException("Failed to send email", e);
        }
    }

    @Override
    public boolean updateUserStatus(String email, String status) {
        String query = "UPDATE customers SET status = ? WHERE email = ?";
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