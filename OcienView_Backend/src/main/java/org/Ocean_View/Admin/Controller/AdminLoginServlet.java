package org.Ocean_View.Admin.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Admin.Services.Implementation.AdminAuthServiceImpl;
import org.Ocean_View.Admin.Services.Interfaces.AdminAuthService;
import org.Ocean_View.Customer.DTO.LoginRequest;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Admin/Login")
public class AdminLoginServlet extends HttpServlet
{
 private  AdminAuthService adminAuthService;

 public AdminLoginServlet()
 {
     this.adminAuthService = new AdminAuthServiceImpl();
 }

    public void setAdminAuthService(AdminAuthService adminAuthService) {
        this.adminAuthService = adminAuthService;
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

            String result = adminAuthService.loginAdmin(loginRequest,session);

            if ("Login successful".equals(result))
            {
                out.print("<script>"
                        + "alert('Login Successful');"
                        + "window.location.href = '/Admin/Dashboard.jsp';"
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
