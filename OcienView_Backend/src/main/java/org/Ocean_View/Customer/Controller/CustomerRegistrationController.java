package org.Ocean_View.Customer.Controller;

import org.Ocean_View.Customer.DTO.RegisterRequest;
import org.Ocean_View.Customer.Entity.Customer;
import org.Ocean_View.Factory.Controller.BaseRegistrationController;

public class CustomerRegistrationController extends BaseRegistrationController {

    public CustomerRegistrationController() {
        super("CUSTOMER"); // Or use default constructor
    }

    public String registerCustomer(String firstName, String lastName, String email,
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
        Customer customer = registrationService.register(request);

        if (customer != null) {
            return "Registration successful! Customer ID: " + customer.getUnique_id();
        } else {
            return "Registration failed!";
        }
    }
}