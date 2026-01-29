package org.Ocean_View.Customer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.DTO.EditProfileRequest;
import org.Ocean_View.Customer.Services.Implementations.CustomerAuthServiceImpl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;


@WebServlet("/Customer/EditProfile")
public class EditProfileServlet extends HttpServlet {

    private CustomerAuthServiceImpl customerAuthService;

    @Override
    public void init() throws ServletException {
        super.init();
        customerAuthService = new CustomerAuthServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);

        // Check if user is logged in - be consistent
        if (session == null || session.getAttribute("email") == null) {
            System.out.println("No email in session, redirecting to login");

            out.print("<script>"
                    + "alert('Email not found.');"
                    + "window.history.back();"
                    + "</script>");

            response.sendRedirect(request.getContextPath() + "/Customer/Signing.jsp");
            return;
        }

        String email = (String) session.getAttribute("email");
        System.out.println("Session email: " + email); // Debug log

        // Get profile details
        Map<String, EditProfileRequest> profileDetails = customerAuthService.getProfile(session);

        System.out.println("Profile details size: " + (profileDetails != null ? profileDetails.size() : "null")); // Debug log

        // Set profile details in request attribute
        request.setAttribute("profileDetails", profileDetails);



        request.getRequestDispatcher("/Customer/EditProfile.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);

        // Check if user is logged in - be consistent
        if (session == null || session.getAttribute("email") == null) {
            System.out.println("No email in session (POST), redirecting to login");
            response.sendRedirect(request.getContextPath() + "/Customer/Signing.jsp");
            return;
        }

        String sessionEmail = (String) session.getAttribute("email");
        System.out.println("POST - Session email: " + sessionEmail);

        // Get form parameters
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String contactNumber = request.getParameter("contactNumber");
        String emailParam = request.getParameter("email");

        System.out.println("Form parameters: " + firstName + ", " + lastName + ", " + contactNumber);

        // Validate inputs
        if (firstName == null || firstName.trim().isEmpty() ||
                lastName == null || lastName.trim().isEmpty() ||
                contactNumber == null || contactNumber.trim().isEmpty()) {

            request.setAttribute("errorMessage", "All fields are required!");

            // Refresh profile data and forward
            Map<String, EditProfileRequest> profileDetails = customerAuthService.getProfile(session);
            request.setAttribute("profileDetails", profileDetails);
            request.getRequestDispatcher("/Customer/EditProfile.jsp").forward(request, response);
            return;
        }

        // Create EditProfileRequest object
        EditProfileRequest profileRequest = new EditProfileRequest();
        profileRequest.setFirstName(firstName.trim());
        profileRequest.setLastName(lastName.trim());
        profileRequest.setContactnumber(contactNumber.trim());
        profileRequest.setEmail(sessionEmail); // Use session email, not form email

        // Update profile
        String result = customerAuthService.updateProfile(profileRequest, session);
        System.out.println("Update result: " + result);

        if (result.equals("Profile updated successfully")) {
            request.setAttribute("successMessage", "Profile updated successfully!");
        } else {
            request.setAttribute("errorMessage", result);
        }

        // Refresh profile data
        Map<String, EditProfileRequest> profileDetails = customerAuthService.getProfile(session);
        request.setAttribute("profileDetails", profileDetails);

        // Forward back to JSP
        request.getRequestDispatcher("/Customer/EditProfile.jsp").forward(request, response);
    }
}