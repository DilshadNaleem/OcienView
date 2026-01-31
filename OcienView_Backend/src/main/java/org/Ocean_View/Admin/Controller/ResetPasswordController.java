package org.Ocean_View.Admin.Controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.Ocean_View.Admin.Services.Implementation.AdminAuthServiceImpl;
import org.Ocean_View.Admin.Services.Interfaces.AdminAuthService;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/Admin/ResetPassword")
public class ResetPasswordController extends HttpServlet
{
    private AdminAuthService adminAuthService;

    public ResetPasswordController()
    {
        this.adminAuthService = new AdminAuthServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        HttpSession session = req.getSession();

        try
        {
            String email = (String) session.getAttribute("resetEmail");
            String newPassword = req.getParameter("newPassword");

            if (req.getParameter("newPassword") == null )
            {
                out.println("<script>alert('Parameter is missing'); window.history.back();</script>");
                return;
            }

            adminAuthService.updateNewPassword(session,newPassword,email);
            out.println("<script>alert('Password Changed'); " +
                    "window.location.href='/Admin/Signing.jsp';" +
                    "</script>");
            return;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
