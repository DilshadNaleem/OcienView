package org.Ocean_View.Admin.Services.Interfaces;

import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.DTO.EditProfileRequest;
import org.Ocean_View.Customer.DTO.LoginRequest;

import java.util.Map;

public interface AdminAuthService
{
    String loginAdmin (LoginRequest request, HttpSession session);
    String updateProfile(EditProfileRequest request, HttpSession session);
    Map<String, EditProfileRequest> getProfile(HttpSession session);
    String updateNewPassword(HttpSession session, String newPassword, String email);
}
