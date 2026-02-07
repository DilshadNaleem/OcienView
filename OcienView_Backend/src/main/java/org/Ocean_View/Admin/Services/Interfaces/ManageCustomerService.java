package org.Ocean_View.Admin.Services.Interfaces;

import org.Ocean_View.Admin.DTO.CustomerDTO;
import org.Ocean_View.Customer.Entity.Customer;

import java.util.List;

public interface ManageCustomerService
{
    List<CustomerDTO> getAllCustomers();
    boolean getCustomerById(String id);
}
