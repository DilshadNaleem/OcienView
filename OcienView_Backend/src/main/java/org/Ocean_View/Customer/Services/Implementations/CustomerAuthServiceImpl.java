package org.Ocean_View.Customer.Services.Implementations;

import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Services.Interfaces.CustomerAuthService;

public class CustomerAuthServiceImpl implements CustomerAuthService
{

    private static final String otp_attribute = "otp";
    private static final String otp_email_attribute = "otpemail";

    @Override
    public String verifyOtp(String otp, HttpSession session) {
        String email = (String) session.getAttribute(otp_email_attribute);

        if (email == null)
        {
            return "Email is Expired or Invalid";
        }

        return null;
    }
}
