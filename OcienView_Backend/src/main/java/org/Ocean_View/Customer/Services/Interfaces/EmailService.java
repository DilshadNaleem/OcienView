package org.Ocean_View.Customer.Services.Interfaces;

public interface EmailService
{
    void sendVerificationEmail (String firstName, String lastName, String toEmail, String otp);
}
