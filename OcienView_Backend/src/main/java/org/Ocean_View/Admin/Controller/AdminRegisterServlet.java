package org.Ocean_View.Admin.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.Services.Implementations.EmailServiceImpl;
import org.Ocean_View.Customer.Services.Implementations.OTPServiceImpl;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;
import org.Ocean_View.Customer.Services.Interfaces.OTPService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Admin/Register")
public class AdminRegisterServlet extends HttpServlet
{
    private AdminRegistrationController controller;
    private EmailService emailService;
    private OTPService otpService;

    @Override
    public void init() throws ServletException {
        super.init();
        controller = new AdminRegistrationController();
        emailService = new EmailServiceImpl();
        otpService = new OTPServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html"); // Changed to text/html for script tags
        response.setCharacterEncoding("UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Get parameters from form
            String firstName = request.getParameter("firstname");
            String lastName = request.getParameter("lastname");
            String email = request.getParameter("email");
            String password = request.getParameter("password");
            String nic = request.getParameter("nic");
            String contactNumber = request.getParameter("contact_number");

            // Register customer
            String result = controller.registerAdmin(
                    firstName, lastName, email, password, nic, contactNumber
            );

            // Handle different registration results
            if (result.startsWith("Registration successful")) {
                // Generate OTP (you need to implement OTP generation)
                String otp =  otpService.generateOTP();
                HttpSession session = request.getSession();
                // Store OTP in session for verification
                request.getSession().setAttribute("otp", otp);
                request.getSession().setAttribute("userEmail", email);
                request.getSession().setAttribute("firstName", firstName);
                request.getSession().setAttribute("lastName", lastName);

                // Send verification email
                emailService.sendVerificationEmail(firstName, lastName, email, otp, session);

                out.print("<script>"
                        + "alert('Registration Successful! Please check your email for verification OTP.');"
                        + "window.location.href='/Admin/OTPVerification.jsp';"
                        + "</script>");

            } else if (result.startsWith("Email already exists")) {
                out.print("<script>"
                        + "alert('Email Already Exists! Please use a different email.');"
                        + "window.history.back();"
                        + "</script>");

            } else if (result.startsWith("NIC already registered")) {
                out.print("<script>"
                        + "alert('NIC Already Exists! Please check your details.');"
                        + "window.history.back();"
                        + "</script>");

            } else {
                out.print("<script>"
                        + "alert('Registration Failed: " + result + "');"
                        + "window.history.back();"
                        + "</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("<script>"
                    + "alert('An error occurred during registration. Please try again.');"
                    + "window.history.back();"
                    + "</script>");
        } finally {
            out.flush();
            out.close();
        }

    }
}
