package org.Ocean_View.Customer.Services.Interfaces;

import jakarta.servlet.http.HttpSession;

public interface OTPService
{
    String generateOTP();
    void storeOtp(HttpSession session, String email, String otp);
    boolean validateOtp (HttpSession session, String email, String otp);
}
