package org.Ocean_View.Customer.Services.Implementations;

import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Services.Interfaces.OTPService;

public class OTPServiceImpl implements OTPService
{
    private static final String otp_attribute = "otp";
    private static final String otp_email_attribute = "otpemail";

    @Override
    public String generateOTP() {
        int otp = (int) (Math.random() * 900000) + 100000;
        return String.valueOf(otp);
    }

    @Override
    public void storeOtp(HttpSession session, String email, String otp) {
        session.setAttribute(otp_attribute, otp);
        session.setAttribute(otp_email_attribute, email);
        System.out.println("Sessioned OTP: " + otp);
        System.out.println("Sessioned Email " + email);
        session.setMaxInactiveInterval(300);
    }


    @Override
    public boolean validateOtp(HttpSession session, String email, String otp) {
        String sessionEmail = (String) session.getAttribute(otp_email_attribute);
        String sessionOtp = (String) session.getAttribute(otp_attribute);

        return email.equals(sessionEmail) && otp.equals(sessionOtp);
    }
}
