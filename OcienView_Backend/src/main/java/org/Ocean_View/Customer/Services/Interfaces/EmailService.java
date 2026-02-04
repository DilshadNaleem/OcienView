package org.Ocean_View.Customer.Services.Interfaces;

import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Entity.Booking;

public interface EmailService
{
    void sendVerificationEmail (String firstName, String lastName, String toEmail, String otp, HttpSession session);
    boolean updateUserStatus(String email, String status);
    void sendPasswordResetEmail (String toEmail, String resetLink);
    void sendBookingConfirmation(String toEmail, Booking booking);
}
