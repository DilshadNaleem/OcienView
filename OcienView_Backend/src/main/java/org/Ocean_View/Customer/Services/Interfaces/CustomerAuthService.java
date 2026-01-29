package org.Ocean_View.Customer.Services.Interfaces;

import com.mysql.cj.Session;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.DTO.EditProfileRequest;
import org.Ocean_View.Customer.DTO.LoginRequest;
import org.Ocean_View.Customer.DTO.RegisterRequest;

import java.util.Map;


public interface CustomerAuthService
{
    String loginCustomer (LoginRequest request, HttpSession session);
    public String updateProfile(EditProfileRequest request, HttpSession session);
    public Map<String, EditProfileRequest> getProfile(HttpSession session);
}
