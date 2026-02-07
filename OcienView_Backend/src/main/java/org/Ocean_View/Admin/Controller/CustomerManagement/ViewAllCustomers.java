package org.Ocean_View.Admin.Controller.CustomerManagement;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.Ocean_View.Admin.DTO.CustomerDTO;
import org.Ocean_View.Admin.Services.Implementation.ManageCustomerServiceImpl;
import org.Ocean_View.Admin.Services.Interfaces.ManageCustomerService;

import java.io.IOException;
import java.util.List;

@WebServlet("/Admin/ManageCustomers")
public class ViewAllCustomers extends HttpServlet
{
    private ManageCustomerService manageCustomerService;

    @Override
    public void init() throws ServletException {
        manageCustomerService = new ManageCustomerServiceImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<CustomerDTO> customerList = manageCustomerService.getAllCustomers();
        req.setAttribute("customerList", customerList);
        req.getRequestDispatcher("/Admin/ManageCustomers.jsp").forward(req,resp);
    }
}
