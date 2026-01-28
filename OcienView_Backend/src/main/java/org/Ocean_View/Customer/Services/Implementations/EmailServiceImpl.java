package org.Ocean_View.Customer.Services.Implementations;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;

import java.util.Properties;

public class EmailServiceImpl implements EmailService {
    private final String fromEmail = "hypermarket403@gmail.com";
    private final String emailPassword = "jaeh itgd annt fsqr";
    private jakarta.mail.Session session; // Use jakarta.mail.Session

    public EmailServiceImpl() {
        initializeSession();
    }

    private void initializeSession() {
        Properties props = new Properties();

        // SMTP Configuration
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

            // Enable debug mode
            session.setDebug(true);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to initialize email session", e);
        }
    }

    @Override
    public void sendVerificationEmail(String firstName, String lastName, String toEmail, String otp) {
        try {
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

            System.out.println("Attempting to send email to: " + toEmail);
            Transport.send(message);
            System.out.println("Verification email sent successfully to: " + toEmail);

        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Failed to send verification email to: " + toEmail);
            throw new RuntimeException("Failed to send email", e);
        }
    }
}