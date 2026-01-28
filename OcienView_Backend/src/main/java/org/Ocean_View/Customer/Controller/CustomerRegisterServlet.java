package org.Ocean_View.Customer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Customer.Services.Implementations.EmailServiceImpl;
import org.Ocean_View.Customer.Services.Interfaces.EmailService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Customer/Register")
public class CustomerRegisterServlet extends HttpServlet {

    private CustomerRegistrationController controller;
    private EmailService emailService;

    @Override
    public void init() throws ServletException {
        super.init();
        controller = new CustomerRegistrationController();
        emailService = new EmailServiceImpl(); // Initialize the email service
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

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
            String result = controller.registerCustomer(
                    firstName, lastName, email, password, nic, contactNumber
            );

            // Handle different registration results
            if (result.startsWith("Registration successful")) {
                // Generate OTP (you need to implement OTP generation)
                String otp = generateOTP(); // Implement this method

                // Store OTP in session for verification
                request.getSession().setAttribute("otp", otp);
                request.getSession().setAttribute("userEmail", email);
                request.getSession().setAttribute("firstName", firstName);
                request.getSession().setAttribute("lastName", lastName);

                // Send verification email
                emailService.sendVerificationEmail(firstName, lastName, email, otp);

                out.print("<script>"
                        + "alert('Registration Successful! Please check your email for verification OTP.');"
                        + "window.location.href='/Customer/OTPVerification.jsp';"
                        + "</script>");

            } else if (result.startsWith("Email already exists")) {
                out.print("<script>"
                        + "alert('Email Already Exists! Please use a different email.');"
                        + "window.location.href='/Customer/Signing.jsp';"
                        + "</script>");

            } else if (result.startsWith("NIC already registered")) {
                out.print("<script>"
                        + "alert('NIC Already Exists! Please check your details.');"
                        + "window.location.href='/Customer/Signing.jsp';"
                        + "</script>");

            } else {
                out.print("<script>"
                        + "alert('Registration Failed: " + result + "');"
                        + "window.location.href='/Customer/Signing.jsp';"
                        + "</script>");
            }

        } catch (Exception e) {
            e.printStackTrace();
            out.print("<script>"
                    + "alert('An error occurred during registration. Please try again.');"
                    + "window.location.href='/Customer/Signing.jsp';"
                    + "</script>");
        } finally {
            out.flush();
            out.close();
        }
    }

    // Method to generate OTP
    private String generateOTP() {
        // Generate a 6-digit OTP
        int otpNumber = (int)(Math.random() * 900000) + 100000;
        return String.valueOf(otpNumber);
    }
}