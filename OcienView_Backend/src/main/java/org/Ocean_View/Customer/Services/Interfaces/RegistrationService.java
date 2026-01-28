package org.Ocean_View.Customer.Services.Interfaces;

import org.Ocean_View.Customer.DTO.RegisterRequest;
import org.Ocean_View.Customer.Entity.Customer;

public interface RegistrationService
{
    Customer register(RegisterRequest request);
    boolean isEmailExists(String email);
    boolean isNICExists(String nic);
}
