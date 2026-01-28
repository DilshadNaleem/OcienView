package org.Ocean_View.Customer.Services.Interfaces;

import com.mysql.cj.Session;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.DTO.RegisterRequest;


public interface CustomerAuthService
{
    String verifyOtp (String otp, HttpSession session);

}
