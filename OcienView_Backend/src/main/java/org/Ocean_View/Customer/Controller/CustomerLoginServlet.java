package org.Ocean_View.Customer.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Customer.DTO.LoginRequest;
import org.Ocean_View.Customer.Services.Implementations.CustomerAuthServiceImpl;
import org.Ocean_View.Customer.Services.Interfaces.CustomerAuthService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Customer/Login")
public class CustomerLoginServlet extends HttpServlet
{
    private final CustomerAuthService customerAuthService;

    public CustomerLoginServlet() {
        this.customerAuthService = new CustomerAuthServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();

        try
        {
            String email = req.getParameter("email");
            String password = req.getParameter("password");

            if (email == null || email.trim().isEmpty() || password == null || password.trim().isEmpty())
            {
                out.println("<script> alert ('Email is not Found');" +
                        "window.history.back();" +
                        "</script>");
                return;
            }

            LoginRequest loginRequest = new LoginRequest(email,password);

            HttpSession session = req.getSession();

            String result = customerAuthService.loginCustomer(loginRequest,session);

            if ("Login successful".equals(result))
            {
                out.print("<script>"
                        + "alert('Login Successful');"
                        + "window.location.href = '/Customer/Dashboard.jsp';"
                        + "</script>");
            }
            else
            {
                out.print("<script>"
                        + "alert('Login Failed! Please Try Again with valid Credentials');"
                        + "window.history.back();"
                        + "</script>");
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
