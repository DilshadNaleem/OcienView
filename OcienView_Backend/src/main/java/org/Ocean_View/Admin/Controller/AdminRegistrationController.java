package org.Ocean_View.Admin.Controller;

import org.Ocean_View.Admin.Entity.Admin;
import org.Ocean_View.Customer.DTO.RegisterRequest;
import org.Ocean_View.Customer.Entity.Customer;
import org.Ocean_View.Factory.Controller.BaseRegistrationController;

public class AdminRegistrationController extends BaseRegistrationController
{
    public AdminRegistrationController()
    {
        super("ADMIN");
    }

    public String registerAdmin(String firstName, String lastName, String email,
                                   String password, String nic, String contactNumber) {

        // Validate inputs
        if (registrationService.isEmailExists(email)) {
            return "Email already exists!";
        }

        if (registrationService.isNICExists(nic)) {
            return "NIC already registered!";
        }

        // Create request DTO
        RegisterRequest request = new RegisterRequest(firstName,lastName,email,password,nic,contactNumber);

        // Process registration
        Customer admin = adminRegistrationService.register(request);

        if (admin != null) {
            return "Registration successful! Customer ID: " + admin.getUnique_id();
        } else {
            return "Registration failed!";
        }
    }
}
