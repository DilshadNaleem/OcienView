package org.Ocean_View.Customer.Services.Implementations;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Connection.DatabaseConnection;
import org.Ocean_View.Customer.DTO.PayOverDuePaymentDTO;
import org.Ocean_View.Customer.Entity.Booking;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;
import org.Ocean_View.Customer.Services.Interfaces.OTPService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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

    @Override
    public void sendPasswordResetEmail(String toEmail, String resetLink)
    {
        try
        {
            // Check if user account is activated (status = 1)
            if (!isUserActivated(toEmail)) {
                throw new RuntimeException("Account not activated. Please activate your account first.");
            }

            String subject = "Reset Your Password";
            String content = "<html>" +
                    "<head>" +
                    "<style>" +
                    "body { font-family: Arial, sans-serif; background-color: #f9f9f9; padding: 20px; }" +
                    ".container { background-color: #fff; padding: 30px; border-radius: 10px; box-shadow: 0 0 10px rgba(0,0,0,0.1); }" +
                    ".btn { background-color: #28a745; color: white; padding: 10px 20px; text-decoration: none; border-radius: 5px; display: inline-block; margin-top: 15px; }" +
                    ".footer { margin-top: 20px; font-size: 12px; color: #888; }" +
                    "</style>" +
                    "</head>" +
                    "<body>" +
                    "<div class='container'>" +
                    "<h2>Password Reset Request</h2>" +
                    "<p>To reset your password, click the button below:</p>" +
                    "<a class='btn' href='" + resetLink + "'>Reset Password</a>" +
                    "<div class='footer'>If you did not request this, please ignore this email.</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";
            String setSubject = "Password Reset Link";
            sendHtmlEmail(toEmail, subject, content, setSubject);
            System.out.println("Confirmation email Send " + toEmail);
        }
        catch (RuntimeException e) {
            // Re-throw custom exceptions
            throw e;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("Failed to send password reset email", e);
        }
    }

    @Override
    public void sendBookingConfirmation(String toEmail, Booking booking) {
        try {
            String subject = "Reservation Confirmed - " + booking.getUniqueId();

            String content = "<div style='font-family: \"Segoe UI\", Tahoma, Geneva, Verdana, sans-serif; max-width: 600px; margin: 0 auto; border: 1px solid #e0e0e0; border-radius: 8px; overflow: hidden;'>"
                    + "  <div style='background-color: #2E86C1; padding: 25px; text-align: center; color: white;'>"
                    + "    <h1 style='margin: 0; font-size: 24px;'>Booking Confirmed!</h1>"
                    + "    <p style='margin: 5px 0 0; opacity: 0.9;'>Thank you for choosing Ocean View</p>"
                    + "  </div>"
                    + "  <div style='padding: 30px; background-color: #ffffff;'>"
                    + "    <p style='font-size: 16px; color: #333;'>Dear <strong>" + booking.getCustomerFirstName() + " " + booking.getCustomerLastName() + "</strong>,</p>"
                    + "    <p style='color: #666; line-height: 1.6;'>Your reservation has been successfully processed. We are looking forward to welcoming you! Below are your booking details:</p>"
                    + "    "
                    + "    <div style='margin-top: 25px; border-top: 2px solid #f2f2f2; padding-top: 20px;'>"
                    + "      <table style='width: 100%; border-collapse: collapse;'>"
                    + "        <tr>"
                    + "          <td style='padding: 8px 0; color: #888;'>Reservation ID:</td>"
                    + "          <td style='padding: 8px 0; text-align: right; font-weight: bold; color: #2E86C1;'>" + booking.getUniqueId() + "</td>"
                    + "        </tr>"
                    + "        <tr>"
                    + "          <td style='padding: 8px 0; color: #888;'>Room / Category:</td>"
                    + "          <td style='padding: 8px 0; text-align: right;'>" + booking.getRoomId() + " (" + booking.getRoomCategory() + ")</td>"
                    + "        </tr>"
                    + "        <tr>"
                    + "          <td style='padding: 8px 0; color: #888;'>Check-in Date:</td>"
                    + "          <td style='padding: 8px 0; text-align: right;'>" + booking.getInDate() + "</td>"
                    + "        </tr>"
                    + "        <tr>"
                    + "          <td style='padding: 8px 0; color: #888;'>Check-out Date:</td>"
                    + "          <td style='padding: 8px 0; text-align: right;'>" + booking.getOutDate() + "</td>"
                    + "        </tr>"
                    + "        <tr>"
                    + "          <td style='padding: 8px 0; color: #888;'>Duration:</td>"
                    + "          <td style='padding: 8px 0; text-align: right;'>" + booking.getNoOfDays() + " Night(s)</td>"
                    + "        </tr>"
                    + "      </table>"
                    + "    </div>"
                    + ""
                    + "    <div style='margin-top: 20px; background-color: #f9f9f9; padding: 20px; border-radius: 5px;'>"
                    + "      <h3 style='margin: 0 0 10px; font-size: 14px; color: #333; text-transform: uppercase;'>Payment Summary</h3>"
                    + "      <table style='width: 100%;'>"
                    + "        <tr>"
                    + "          <td style='color: #666;'>Method: " + booking.getPaymentMethod().toUpperCase() + "</td>"
                    + "          <td style='text-align: right; font-size: 18px; font-weight: bold; color: #333;'>LKR " + String.format("%.2f", booking.getPrice()) + "</td>"
                    + "        </tr>"
                    + "        <tr>"
                    + "          <td colspan='2' style='font-size: 11px; color: #999; padding-top: 5px;'>Transaction ID: " + booking.getPaymentUniqueId() + "</td>"
                    + "        </tr>"
                    + "      </table>"
                    + "    </div>"
                    + ""
                    + "    <div style='margin-top: 30px; text-align: center;'>"
                    + "      <p style='font-size: 14px; color: #888;'>Need help? Reply to this email or call our support.</p>"
                    + "    </div>"
                    + "  </div>"
                    + "  <div style='background-color: #f4f4f4; padding: 20px; text-align: center; color: #999; font-size: 12px;'>"
                    + "    © 2024 Ocean View Hotel. All rights reserved.<br>"
                    + "    123 Coastal Road, Seaside City"
                    + "  </div>"
                    + "</div>";

            String setSubject = "Booking Confirmation";
            sendHtmlEmail(toEmail, subject, content, setSubject);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send Confirmation Email: " + e.getMessage());
        }
    }

    // Add this method to check if user is activated
    private boolean isUserActivated(String email) {
        String query = "SELECT status FROM customers WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int status = rs.getInt("status");
                return status == 1; // Return true if status is 1 (activated)
            }
            return false; // User not found

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void sendHtmlEmail(String toEmail, String subject, String htmlContent,String setSubject)
    {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, subject));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject(setSubject);

            // FIX: Use setContent() instead of setText() for HTML emails
            message.setContent(htmlContent, "text/html; charset=utf-8");

            Transport.send(message);
            System.out.println("Password reset email sent successfully to: " + toEmail);
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to send password reset email", e);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Unexpected error while sending email", e);
        }
    }

    @Override
    public void sendFineConfirmation(String toEmail, PayOverDuePaymentDTO payment)
    {
        try{
            String subject = "Fine Completion - " + payment.getBookingId();

            String content = "<html>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "</head>" +
                    "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;'>" +
                    "<div style='max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);'>" +

                    "<!-- Header Section -->" +
                    "<div style='background: linear-gradient(135deg, #667eea 0%, #764ba2 100%); padding: 30px; text-align: center; border-radius: 10px 10px 0 0;'>" +
                    "<h1 style='color: #ffffff; margin: 0; font-size: 28px; font-weight: 600;'>Payment Confirmation</h1>" +
                    "<p style='color: #e0e0e0; margin: 10px 0 0 0; font-size: 16px;'>Fine Payment Received Successfully</p>" +
                    "</div>" +

                    "<!-- Content Section -->" +
                    "<div style='padding: 40px 30px;'>" +

                    "<!-- Booking Details -->" +
                    "<div style='background-color: #f8f9fa; border-left: 4px solid #667eea; padding: 20px; margin-bottom: 30px; border-radius: 5px;'>" +
                    "<h3 style='color: #333333; margin: 0 0 15px 0; font-size: 18px; font-weight: 600; border-bottom: 2px solid #e0e0e0; padding-bottom: 10px;'>Booking Details</h3>" +

                    "<p style='color: #555555; margin: 10px 0; font-size: 15px; line-height: 1.6;'>" +
                    "<span style='font-weight: 600; color: #667eea; width: 140px; display: inline-block;'>Booking ID:</span> " + payment.getBookingId() + "</p>" +

                    "<p style='color: #555555; margin: 10px 0; font-size: 15px; line-height: 1.6;'>" +
                    "<span style='font-weight: 600; color: #667eea; width: 140px; display: inline-block;'>Room ID:</span> " + payment.getRoomId() + "</p>" +

                    "<p style='color: #555555; margin: 10px 0; font-size: 15px; line-height: 1.6;'>" +
                    "<span style='font-weight: 600; color: #667eea; width: 140px; display: inline-block;'>Fine Amount:</span> " +
                    "<span style='color: #dc3545; font-weight: 600;'>₱" + payment.getCalculatedFine() + "</span></p>" +

                    "<p style='color: #555555; margin: 10px 0; font-size: 15px; line-height: 1.6;'>" +
                    "<span style='font-weight: 600; color: #667eea; width: 140px; display: inline-block;'>Payment Method:</span> " +
                    "<span style='background-color: #e8f5e9; color: #28a745; padding: 3px 10px; border-radius: 15px; font-weight: 600;'>" + payment.getPaymentMethod() + "</span></p>" +
                    "</div>" +

                    "<!-- Status Box -->" +
                    "<div style='text-align: center; background-color: #d4edda; border: 1px solid #c3e6cb; border-radius: 5px; padding: 20px; margin-bottom: 30px;'>" +
                    "<p style='color: #155724; margin: 0; font-size: 16px; font-weight: 600;'>" +
                    "✓ Your fine payment has been successfully processed</p>" +
                    "</div>" +

                    "<!-- Footer Note -->" +
                    "<p style='color: #777777; margin: 20px 0 0 0; font-size: 14px; line-height: 1.6; text-align: center; border-top: 1px solid #e0e0e0; padding-top: 20px;'>" +
                    "Thank you for your prompt payment. If you have any questions, please contact our support team.</p>" +
                    "</div>" +

                    "<!-- Footer Section -->" +
                    "<div style='background-color: #f8f9fa; padding: 20px; text-align: center; border-radius: 0 0 10px 10px; border-top: 1px solid #e0e0e0;'>" +
                    "<p style='color: #666666; margin: 0; font-size: 13px;'>This is an automated message, please do not reply to this email.</p>" +
                    "<p style='color: #999999; margin: 10px 0 0 0; font-size: 12px;'>&copy; 2024 Ocean View. All rights reserved.</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            String setSubject = "Fine Completion";
            sendHtmlEmail(toEmail, subject, content, setSubject);

        }
        catch (Exception e)
        {
            e.printStackTrace();
            throw new RuntimeException("Failed to send confirmation Email: " + e.getMessage());
        }
    }

    @Override
    public void sendCancellationEmail(String toEmail, String roomId) {
        try {
            String subject = "Cancellation Completion - " + roomId;

            String content = "<html>" +
                    "<head>" +
                    "<meta charset='UTF-8'>" +
                    "</head>" +
                    "<body style='margin: 0; padding: 0; font-family: Arial, sans-serif; background-color: #f4f4f4;'>" +
                    "<div style='max-width: 600px; margin: 20px auto; background-color: #ffffff; border-radius: 10px; box-shadow: 0 2px 10px rgba(0,0,0,0.1);'>" +

                    "<!-- Header Section -->" +
                    "<div style='background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%); padding: 30px; text-align: center; border-radius: 10px 10px 0 0;'>" +
                    "<h1 style='color: #ffffff; margin: 0; font-size: 28px; font-weight: 600;'>Booking Cancellation</h1>" +
                    "<p style='color: #e0e0e0; margin: 10px 0 0 0; font-size: 16px;'>Your booking has been cancelled</p>" +
                    "</div>" +

                    "<!-- Content Section -->" +
                    "<div style='padding: 40px 30px;'>" +

                    "<!-- Cancellation Details -->" +
                    "<div style='background-color: #f8f9fa; border-left: 4px solid #f5576c; padding: 20px; margin-bottom: 30px; border-radius: 5px;'>" +
                    "<h3 style='color: #333333; margin: 0 0 15px 0; font-size: 18px; font-weight: 600; border-bottom: 2px solid #e0e0e0; padding-bottom: 10px;'>Cancellation Details</h3>" +

                    "<p style='color: #555555; margin: 10px 0; font-size: 15px; line-height: 1.6;'>" +
                    "<span style='font-weight: 600; color: #f5576c; width: 140px; display: inline-block;'>Room ID:</span> " + roomId + "</p>" +

                    "<p style='color: #555555; margin: 10px 0; font-size: 15px; line-height: 1.6;'>" +
                    "<span style='font-weight: 600; color: #f5576c; width: 140px; display: inline-block;'>Status:</span> " +
                    "<span style='background-color: #f8d7da; color: #721c24; padding: 3px 10px; border-radius: 15px; font-weight: 600;'>CANCELLED</span></p>" +
                    "</div>" +

                    "<!-- Important Information Box -->" +
                    "<div style='background-color: #fff3cd; border: 1px solid #ffeeba; border-radius: 5px; padding: 20px; margin-bottom: 30px;'>" +
                    "<p style='color: #856404; margin: 0 0 10px 0; font-weight: 600;'>⚠️ Important Information:</p>" +
                    "<ul style='color: #856404; margin: 0; padding-left: 20px; font-size: 14px; line-height: 1.6;'>" +
                    "<li>Your booking has been successfully cancelled</li>" +
                    "<li>Any applicable cancellation fees may apply</li>" +
                    "<li>Refunds will be processed within 5-7 business days</li>" +
                    "</ul>" +
                    "</div>" +

                    "<!-- Footer Note -->" +
                    "<p style='color: #777777; margin: 20px 0 0 0; font-size: 14px; line-height: 1.6; text-align: center; border-top: 1px solid #e0e0e0; padding-top: 20px;'>" +
                    "We're sorry to see you go. We hope to serve you again in the future!</p>" +
                    "</div>" +

                    "<!-- Footer Section -->" +
                    "<div style='background-color: #f8f9fa; padding: 20px; text-align: center; border-radius: 0 0 10px 10px; border-top: 1px solid #e0e0e0;'>" +
                    "<p style='color: #666666; margin: 0; font-size: 13px;'>This is an automated message, please do not reply to this email.</p>" +
                    "<p style='color: #999999; margin: 10px 0 0 0; font-size: 12px;'>&copy; 2024 Ocean View. All rights reserved.</p>" +
                    "</div>" +
                    "</div>" +
                    "</body>" +
                    "</html>";

            String setSubject = "Cancellation Completion";
            sendHtmlEmail(toEmail, subject, content, setSubject);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

}