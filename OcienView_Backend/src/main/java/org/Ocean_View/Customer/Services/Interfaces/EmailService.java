package org.Ocean_View.Customer.Services.Interfaces;

import jakarta.servlet.http.HttpSession;

public interface EmailService
{
    void sendVerificationEmail (String firstName, String lastName, String toEmail, String otp, HttpSession session);
    boolean updateUserStatus(String email, String status);
}
