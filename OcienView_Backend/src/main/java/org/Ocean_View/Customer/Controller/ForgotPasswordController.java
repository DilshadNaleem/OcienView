package org.Ocean_View.Customer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Services.Implementations.EmailServiceImpl;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Customer/ForgotPassword")
public class ForgotPasswordController extends HttpServlet
{
    private EmailService emailService;

    public ForgotPasswordController()
    {
        this.emailService = new EmailServiceImpl();
    } 

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();

        try
        {
            String normalizedEmail = req.getParameter("email");
            System.out.println("Reset Password Controller : received Email : " + normalizedEmail);

            if (normalizedEmail == null || normalizedEmail.isEmpty())
            {
                out.println("<script>alert('Email is required'); window.history.back();</script>");
                return;
            }

            session.setAttribute("resetEmail", normalizedEmail);

            // Generate a proper reset link (you should implement this)
            // For local testing only
            String passwordResetLink = "http://localhost:8080" + req.getContextPath() + "/Customer/ResetPassword.html";

            emailService.sendPasswordResetEmail(normalizedEmail, passwordResetLink);

            // Success message
            out.println("<script>alert('Password reset link has been sent to your email.'); window.location.href='/Customer/Signing.jsp';</script>");

        }
        catch (RuntimeException e) {
            // Handle specific exceptions (like account not activated)
            if (e.getMessage() != null && e.getMessage().contains("Account not activated")) {
                out.println("<script>alert('Account not activated. Please activate your account first.'); window.history.back();</script>");
            } else {
                out.println("<script>alert('Failed to send reset email: " + e.getMessage() + "'); window.history.back();</script>");
            }
            e.printStackTrace();
        }
        catch (Exception e)
        {
            out.println("<script>alert('An error occurred. Please try again.'); window.history.back();</script>");
            e.printStackTrace();
        }
    }



}