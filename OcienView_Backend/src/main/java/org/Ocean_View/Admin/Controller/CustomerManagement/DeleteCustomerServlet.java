package org.Ocean_View.Admin.Controller.CustomerManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Admin.Services.Implementation.ManageCustomerServiceImpl;
import org.Ocean_View.Admin.Services.Interfaces.ManageCustomerService;

import java.io.IOException;

@WebServlet("/Admin/DeleteCustomer")
public class DeleteCustomerServlet extends HttpServlet
{
    private ManageCustomerService manageCustomerService;

    @Override
    public void init() throws ServletException {
        manageCustomerService = new ManageCustomerServiceImpl();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            resp.setContentType("text/html");
           String uniqueId =  req.getParameter("unique_id");
            System.out.println("Deleted Customer ID: " + uniqueId);
           boolean isDeleted = manageCustomerService.getCustomerById(uniqueId);


           if (isDeleted)
           {
               req.getSession().setAttribute("successMessage", "Customer Deleted successfully!");
           }
           else
           {
               req.getSession().setAttribute("errorMessage", "Failed to Delete the Customer");
           }

           resp.sendRedirect(req.getContextPath() + "/Admin/ManageCustomers");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
}
