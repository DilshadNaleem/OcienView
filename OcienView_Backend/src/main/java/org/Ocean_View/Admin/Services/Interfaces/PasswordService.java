package org.Ocean_View.Admin.Services.Interfaces;

import jakarta.servlet.http.HttpSession;

public interface PasswordService
{

    void sendPasswordResetEmail (String toEmail, String resetLink);
}
