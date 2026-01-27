package org.Ocean_View.Customer.Services.Interfaces;

import com.mysql.cj.Session;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.DTO.RegisterRequest;


public interface CustomerAuthService
{
    String registerCustomer(RegisterRequest request, HttpSession session);
    String verifyOtp(String otp, HttpSession session);
    void updatePassword(String email, String newPassword);

}
