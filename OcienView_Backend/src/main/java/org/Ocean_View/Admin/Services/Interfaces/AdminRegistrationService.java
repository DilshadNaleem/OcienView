package org.Ocean_View.Admin.Services.Interfaces;

import org.Ocean_View.Admin.Entity.Admin;
import org.Ocean_View.Customer.DTO.RegisterRequest;
import org.Ocean_View.Customer.Entity.Customer;
import org.Ocean_View.Customer.Services.Interfaces.RegistrationService;

public interface AdminRegistrationService extends RegistrationService
{
        Customer register(RegisterRequest request);
        boolean isEmailExists(String email);
        boolean isNICExists(String nic);
        boolean updateUserStatus(String email, String status);
}
