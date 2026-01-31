package org.Ocean_View.Admin.Services.Implementation;

import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.Ocean_View.Admin.Services.Interfaces.PasswordService;
import org.Ocean_View.Connection.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class PasswordServiceImpl implements PasswordService
{    private final String fromEmail = "hypermarket403@gmail.com";
    private final String emailPassword = "jaeh itgd annt fsqr";
    private jakarta.mail.Session session;


    public PasswordServiceImpl()
    {
        initializeSession();

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

            sendHtmlEmail(toEmail, subject, content);
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

    // Add this method to check if user is activated
    private boolean isUserActivated(String email) {
        String query = "SELECT status FROM admin WHERE email = ?";
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


    private void sendHtmlEmail(String toEmail, String subject, String htmlContent)
    {
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(fromEmail, "Ocean View Reset Password"));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Password Reset Link ");

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
}
