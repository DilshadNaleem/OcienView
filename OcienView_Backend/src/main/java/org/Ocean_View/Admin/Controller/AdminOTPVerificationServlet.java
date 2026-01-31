package org.Ocean_View.Admin.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Admin.Services.Implementation.AdminRegistrationServiceImpl;
import org.Ocean_View.Admin.Services.Interfaces.AdminRegistrationService;
import org.Ocean_View.Customer.Services.Implementations.EmailServiceImpl;
import org.Ocean_View.Customer.Services.Implementations.OTPServiceImpl;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Admin/VerifyOtp")
public class AdminOTPVerificationServlet extends HttpServlet
{
    private static final String otp_attribute = "otp";
    private static final String otp_email_attribute = "otpemail";
    private OTPServiceImpl otpService;
    private AdminRegistrationService adminRegistrationService;

    public AdminOTPVerificationServlet()
    {
        this.otpService = new OTPServiceImpl();
        this.adminRegistrationService = new AdminRegistrationServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();

        try
        {
            String email = (String) session.getAttribute(otp_email_attribute);
            System.out.println("Recieved Email: " + email);
            String otp = req.getParameter("otp_code");

            if (email == null || email.trim().isEmpty() ||
                    otp == null || otp.trim().isEmpty())
            {
                out.println("<script>alert('Email and OTP are required!'); window.history.back();</script>");
                return;
            }

            boolean isValid = otpService.validateOtp(session, email,otp);

            if (isValid)
            {
                session.removeAttribute(otp_attribute);
                session.removeAttribute(otp_email_attribute);

                session.setAttribute("emailVerified", "true");
                session.setAttribute("verifiedEmail", email);
                boolean isUpdated = adminRegistrationService.updateUserStatus(email,"1");


                out.println("<script>alert('OTP verified successfully!'); window.location='/Admin/Signing.jsp';</script>");
            }
            else
            {
                out.println("<script>alert('Invalid OTP! Please try again.'); window.history.back();</script>");
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
            out.println("<script>alert('An error occurred during OTP verification.'); window.history.back();</script>");
        } finally {
            out.close();
        }
    }
}
