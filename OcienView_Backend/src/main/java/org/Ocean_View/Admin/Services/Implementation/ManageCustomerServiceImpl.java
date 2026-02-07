package org.Ocean_View.Admin.Services.Implementation;

import org.Ocean_View.Admin.DTO.CustomerDTO;
import org.Ocean_View.Admin.Services.Interfaces.ManageCustomerService;
import org.Ocean_View.Connection.DatabaseConnection;
import org.Ocean_View.Customer.Entity.Customer;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ManageCustomerServiceImpl implements ManageCustomerService
{
    @Override
    public List<CustomerDTO> getAllCustomers() {
        List<CustomerDTO> customerList = new ArrayList<>();
        String sql = "SELECT * FROM  customers";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();)
        {
            while (rs.next())
            {
                CustomerDTO customer = new CustomerDTO();
                customer.setUnique_id(rs.getString("unique_id"));
                customer.setFirstName(rs.getString("firstName"));
                customer.setLastName(rs.getString("lastName"));
                customer.setEmail(rs.getString("email"));
                customer.setContactNumber(rs.getString("contactNumber"));
                customer.setNic(rs.getString("nic"));
                customer.setStatus(rs.getString("status"));

                customerList.add(customer);
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return customerList;
    }

    @Override
    public boolean getCustomerById(String id) {
        String sql = "DELETE FROM customers WHERE unique_id = ?";

        try(Connection conn = DatabaseConnection.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql))
        {
            ps.setString(1,id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected > 0)
            {
                System.out.println("ManageCustomerServiceImpl: " + id);
                return true;
            }
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }
}
